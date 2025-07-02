package org.khenas.teams.files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.khenas.teams.parts.Team;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TeamList {
    private static String sectionKey = "team-list";
    private static File file;
    private static FileConfiguration customFile;
    private static Map<String, Team> teamMap = new HashMap<>();

    //finds or generates the configuration file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Teams").getDataFolder(), "teamlist.yml");
        if(!file.exists()){ //if the file 'teamlist.yml' does not exist, this creates a new one.
            try{
                file.createNewFile();
                customFile = YamlConfiguration.loadConfiguration(file);
                getCustomFile().createSection("team-list");
                saveCustomFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
        loadTeams();
    }

    private static void loadTeams(){
        teamMap.clear();
        if(customFile.contains(sectionKey)){
            ConfigurationSection section = customFile.getConfigurationSection(sectionKey); //read the .yml file -> get the "team-list" section
            for(String teamName : section.getKeys(false)){
                ConfigurationSection teamSection = section.getConfigurationSection(teamName); // team-list -> [team1, team2, ...] (subsections)
                Team team = new Team(teamName);
                ArrayList<String> memberName = (ArrayList<String>) teamSection.getStringList("members");
                for(int i = 0; i < memberName.size(); i++) {
                    Player player = Bukkit.getPlayer(memberName.get(i));
                    if (player != null) {
                        team.addMember(player);
                    }
                }
                String leaderName = teamSection.getString("leader");
                if(leaderName != null){
                    Player leader = Bukkit.getPlayer(leaderName);
                    team.setLeader(leader);
                }
                teamMap.put(teamName, team);
            }
        }
    }

    public static Map<String, Team> getTeamMap(){
        return teamMap;
    }

    public static void addTeamToTheList(Player player, String teamName){
        try {
            customFile.load(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //comparing teamname on the list, following case
        ConfigurationSection teamList = customFile.getConfigurationSection(sectionKey);
        boolean teamExists = false;
        for(String existingTeam : teamList.getKeys(false)){
            if(existingTeam.equalsIgnoreCase(teamName)){
                teamExists = true;
                break;
            }
        }
        if(!teamExists){ //if the team does not exist, this will create a new subsection for it.
            ConfigurationSection teamSection = customFile.createSection(sectionKey + "." + teamName); //read the .yml file -> get the "team-list" section
            teamSection.set("leader", player.getName()); //create a subsection on "team-list" named leader
            ArrayList<String> members = new ArrayList<>();
            members.add(player.getName());
            teamSection.set("members", members); //create a subsection on "team-list" named members
            Team team = new Team(teamName);
            team.setLeader(player);
            team.addMember(player);
            teamMap.put(teamName, team);
            player.sendMessage(ChatColor.GREEN + "Team has been created successfully.");
            player.sendMessage("Team created. The leader are -" + ChatColor.AQUA + player.getName());
        } else {
            player.sendMessage(ChatColor.RED + "Unavailable team name. Try with another name.");
        }
        reloadCustomFile();
    }

    public static Team getTeam(String teamName){
        return teamMap.get(teamName);
    }

    public static FileConfiguration getCustomFile(){
        return customFile;
    }

    public static void saveCustomFile(){
        try{
            customFile.save(file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void reloadCustomFile(){
        saveCustomFile();
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
