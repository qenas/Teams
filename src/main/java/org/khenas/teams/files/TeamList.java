package org.khenas.teams.files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class TeamList {
    private static final String sectionKey = "team-list";
    private static File file;
    private static FileConfiguration customFile;
    private static final Map<String, Team> teamMap = new HashMap<>();

    //finds or generates the configuration file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Teams").getDataFolder(), "teamlist.yml");
        if(!file.exists()){ //if the file 'teamlist.yml' does not exist, this creates a new one.
            try{
                file.createNewFile();
                customFile = YamlConfiguration.loadConfiguration(file);
                getCustomFile().createSection(sectionKey);
                saveCustomFile();
                if(!customFile.contains(sectionKey + ".no-team")){
                    System.out.println("No-team section does not exist. Creating it...");
                    createNoTeam(); //creates a team for the people who have no team
                }
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
                ArrayList<String> memberUUIDs = (ArrayList<String>) teamSection.getStringList("members");
                for(int i = 0; i < memberUUIDs.size(); i++) {
                    UUID uuid = UUID.fromString(memberUUIDs.get(i));
                    OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                    Member newMember = new Member(team, player);
                    team.addMember(newMember);
                }
                String leaderName = teamSection.getString("leader");
                if(leaderName != null){
                    Player leader = Bukkit.getPlayer(leaderName);
                    team.setLeader(leader);
                }
                teamMap.put(teamName, team);
            }
        }
        reloadCustomFile();
    }

    public static Map<String, Team> getTeamMap(){
        return teamMap;
    }

    private static void createNoTeam(){
        try {
            customFile.load(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ConfigurationSection noTeamSection = customFile.createSection(sectionKey + ".no-team");
        noTeamSection.set("leader", "no-body");
        ArrayList<String> membersWithoutTeam = new ArrayList<>();
        noTeamSection.set("members", membersWithoutTeam);
        Team noTeam = new Team("no-team");
        noTeam.setLeader(null);
        teamMap.put("no-team", noTeam); //loads the 'no-team' to the archive
        reloadCustomFile();
    }

    public static void addToTheNoTeam(Player player){
        loadCustomFile();
        if(!customFile.contains(sectionKey + ".no-team")){
            System.out.println("No-team section does not exist. Creating it...");
            createNoTeam();
        }
        ConfigurationSection noTeamSection = customFile.getConfigurationSection(sectionKey + ".no-team");
        ArrayList<String> noTeamMembers = (ArrayList<String>) noTeamSection.getStringList("members");
        String newMemberUUID = player.getUniqueId().toString();
        if(!noTeamMembers.contains(newMemberUUID)){
            noTeamMembers.add(newMemberUUID);
            noTeamSection.set("members", noTeamMembers);
            Member noMember = new Member(getNoTeam(), player);
            getNoTeam().addMember(noMember);
        } else {
            System.out.println("Error: the player already have a team.");
        }
        saveCustomFile();
    }

    public static void removeFromTheNoTeam(Player player){
        loadCustomFile();
        if(!customFile.contains(sectionKey + ".no-team")){
            System.out.println("No-team section does not exist. Creating it...");
            createNoTeam();
        }
        ConfigurationSection noTeamSection = customFile.getConfigurationSection(sectionKey + ".no-team");
        ArrayList<String> noTeamMembers = (ArrayList<String>) noTeamSection.getStringList("members");
        String newMemberUUID = player.getUniqueId().toString();
        if(noTeamMembers.contains(newMemberUUID)){
            noTeamMembers.remove(newMemberUUID);
            noTeamSection.set("members", noTeamMembers);
            Member oldMember = getMemberByUUID(player);
            getNoTeam().removeMember(oldMember);
        }
        saveCustomFile();
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
            teamSection.set("leader", player.getUniqueId().toString()); //create a subsection on "team-list" named leader
            ArrayList<String> members = new ArrayList<>();
            members.add(player.getUniqueId().toString());
            teamSection.set("members", members); //create a subsection on "team-list" named members
            Team team = new Team(teamName);
            team.setLeader(player);
            Member newMember = new Member(team, player);
            team.addMember(newMember);
            teamMap.put(teamName, team);
            player.sendMessage(ChatColor.GREEN + "Team has been created successfully.");
            player.sendMessage("Team created. The leader are -" + ChatColor.AQUA + player.getName());
        } else {
            player.sendMessage(ChatColor.RED + "Unavailable team name. Try with another name.");
        }
        reloadCustomFile();
    }

    public static boolean isOnTeam(Player player){
        String playerUUID = player.getUniqueId().toString();
        if(customFile.contains(sectionKey)){
            ConfigurationSection section = customFile.getConfigurationSection(sectionKey); //read the .yml file -> get the "team-list" section
            for(String teamName : section.getKeys(false)){
                if(!teamName.equals("no-team")){
                    ConfigurationSection teamSection = section.getConfigurationSection(teamName); // team-list -> [team1, team2, ...] (subsections)
                    ArrayList<String> memberUUIDs = (ArrayList<String>) teamSection.getStringList("members");
                    for(int i = 0; i < memberUUIDs.size(); i++) {
                        if(playerUUID.equals(memberUUIDs.get(i))){
                            return true; // true: player have team.
                        }
                    }
                }
            }
        }
        return false; // false: player do not have team.
    }

    public static Member getMemberByUUID(Player player){
        for(Team team: teamMap.values()){
            for(Member member: team.getMembers()){
                if(member.hasTheSamePlayer(player)){
                    return member; // return the object 'member' who is associated with the player.
                }
            }
        }
        return null; // returns null if the player does not have a object member associated.
    }

    public static Team getTeam(String teamName){
        return teamMap.get(teamName);
    }

    public static Team getNoTeam(){
        return teamMap.get("no-team");
    }

    public static FileConfiguration getCustomFile(){
        return customFile;
    }

    private static void loadCustomFile(){
        try {
            customFile.load(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
