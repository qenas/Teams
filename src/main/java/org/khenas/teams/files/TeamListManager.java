package org.khenas.teams.files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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


public class TeamListManager {
    private static final String sectionKey = "team-list";
    private File file;
    private FileConfiguration customFile;
    private PlayerManager playerManager;
    private static Map<String, Team> teamMap = new HashMap<>();

    public void setPlayerManager(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    //finds or generates the configuration file
    public void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Teams").getDataFolder(), "teamlist.yml");
        if(!file.exists()){ //if the file 'teamlist.yml' does not exist, this creates a new one.
            try{
                System.out.println("Creating 'teamlist.yml' file...");
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
        saveCustomFile();
    }



    private void loadTeams(){
        teamMap.clear();
        if(customFile.contains(sectionKey)){
            ConfigurationSection section = customFile.getConfigurationSection(sectionKey); //read the .yml file -> get the "team-list" section
            for(String teamName : section.getKeys(false)){
                System.out.println("Loading '" + teamName + "' team to the server...");
                ConfigurationSection teamSection = section.getConfigurationSection(teamName); // team-list -> [team1, team2, ...] (subsections)
                Team team = new Team(teamName);
                ArrayList<String> memberUUIDs = (ArrayList<String>) teamSection.getStringList("members");
                for(String uuid: memberUUIDs){ // loads all the existing UUID from the YML archive to the array
                    playerManager.setupMember(team, UUID.fromString(uuid));
                }
                String leaderUUID = teamSection.getString("leader");
                if(!leaderUUID.isEmpty() && !teamName.equals("no-team")){
                    OfflinePlayer leader = Bukkit.getOfflinePlayer(UUID.fromString(leaderUUID));
                    team.setLeader(UUID.fromString(leaderUUID));
                }
                teamMap.put(teamName.toLowerCase(), team);
            }
        }
        reloadCustomFile();
    }

    public Map<String, Team> getTeamMap(){
        return teamMap;
    }


    private void createNoTeam(){
        loadCustomFile();
        ConfigurationSection noTeamSection = customFile.createSection(sectionKey + ".no-team");
        noTeamSection.set("leader", "no-body");
        ArrayList<String> membersWithoutTeam = new ArrayList<>();
        noTeamSection.set("members", membersWithoutTeam);
        Team noTeam = new Team("no-team");
        noTeam.setLeader(null);
        teamMap.put("no-team", noTeam); //loads the 'no-team' to the archive
        reloadCustomFile();
    }

    public void addToTheNoTeam(Member member){
        loadCustomFile();
        if(!customFile.contains(sectionKey + ".no-team")){
            System.out.println("No-team section does not exist. Creating it...");
            createNoTeam();
        }
        ConfigurationSection noTeamSection = customFile.getConfigurationSection(sectionKey + ".no-team");
        ArrayList<String> noTeamMembers = (ArrayList<String>) noTeamSection.getStringList("members");
        String newMemberUUID = member.getPlayer().getUniqueId().toString();
        if(!noTeamMembers.contains(newMemberUUID)){
            noTeamMembers.add(newMemberUUID);
            noTeamSection.set("members", noTeamMembers);
            getNoTeam().addMember(member);
            member.setTeam(getNoTeam());
        } else {
            System.out.println("Error: the player already have a team.");
        }
        saveCustomFile();
    }

    public void removeFromTheNoTeam(Member member){
        loadCustomFile();
        if(!customFile.contains(sectionKey + ".no-team")){
            System.out.println("No-team section does not exist. Creating it...");
            createNoTeam();
        }
        ConfigurationSection noTeamSection = customFile.getConfigurationSection(sectionKey + ".no-team");
        ArrayList<String> noTeamMembers = (ArrayList<String>) noTeamSection.getStringList("members");
        String newMemberUUID = member.getPlayer().getUniqueId().toString();
        if(noTeamMembers.contains(newMemberUUID)){
            noTeamMembers.remove(newMemberUUID);
            noTeamSection.set("members", noTeamMembers);
            getNoTeam().removeMember(member);
        } else {
            System.out.println("Error to delete this player: the player is not on the members list.");
        }
        saveCustomFile();
    }

    public void addTeamToTheList(Member member, String teamName){
        loadCustomFile();
        //comparing teamname on the list, following case
        ConfigurationSection teamList = customFile.getConfigurationSection(sectionKey);
        boolean teamExists = false;
        for(String existingTeam : teamList.getKeys(false)){
            if(existingTeam.equalsIgnoreCase(teamName)){
                teamExists = true;
                break;
            }
        }
        Player leader = (Player) member.getPlayer();
        if(!teamExists){ //if the team does not exist, this will create a new subsection for it.
            ConfigurationSection teamSection = customFile.createSection(sectionKey + "." + teamName); //read the .yml file -> get the "team-list" section
            teamSection.set("leader", leader.getUniqueId().toString()); //create a subsection on "team-list" named leader
            ArrayList<String> members = new ArrayList<>();
            members.add(leader.getUniqueId().toString());
            teamSection.set("members", members); //create a subsection on "team-list" named members
            Team team = new Team(teamName);
            team.addMember(member);
            team.setLeader(leader.getUniqueId());
            member.setTeam(team);
            teamMap.put(teamName, team);
            saveCustomFile();
            removeFromTheNoTeam(member);
            leader.sendMessage(ChatColor.GREEN + "Team has been created successfully.");
            leader.sendMessage("Team created. The leader are -" + ChatColor.AQUA + leader.getName());
        } else {
            leader.sendMessage(ChatColor.RED + "Unavailable team name. Try with another name.");
        }
        System.out.println("The team '" + teamName + " has been add successfully to the list.");
        reloadCustomFile();
    }

    public void removeTeamFromTheList(Team teamToRemove){
        loadCustomFile();
        if(customFile.contains(sectionKey + "." + teamToRemove.getTeamName())){
            ArrayList<Member> membersCopy = new ArrayList<>(teamToRemove.getMembers());
            for(Member member: membersCopy){
                teamToRemove.removeMember(member);
                addToTheNoTeam(member);
                if(member.isOnline()){
                    Player p = (Player) member.getPlayer();
                    p.sendMessage("Your team " + ChatColor.RED + teamToRemove.getTeamName() + ChatColor.WHITE + " has been disbanded.");
                }
            }
            customFile.getConfigurationSection(sectionKey).set(teamToRemove.getTeamName(), null);
            saveCustomFile();
            teamMap.remove(teamToRemove.getTeamName());
        } else {
            System.out.println("Error: the team does not exist on the archive.");
        }
        System.out.println("The team '" + teamToRemove.getTeamName() + " has been remove successfully from the list.");
        reloadCustomFile();
    }

    public void addToTeam(Member member, Team team){
        loadCustomFile();
        if(customFile.contains(sectionKey + "." + team.getTeamName())){
            if(teamMap.containsKey(team.getTeamName().toLowerCase())){
                ConfigurationSection teamSection = customFile.getConfigurationSection(sectionKey + "." + team.getTeamName());
                ArrayList<String> teamMembers = (ArrayList<String>) teamSection.getStringList("members");
                teamMembers.add(member.getPlayer().getUniqueId().toString());
                System.out.println(team.getTeamName() + ": " + teamMembers.toString());
                teamSection.set("members", teamMembers);
                team.addMember(member);
                reloadCustomFile();
                Player playerMember = (Player) member.getPlayer();
                playerMember.sendMessage("You has been added to: " + ChatColor.GREEN + team.getTeamName());
                member.setTeam(team);
                removeFromTheNoTeam(member);
                teamMap.put(team.getTeamName().toLowerCase(), team);
            } else {
                System.out.println("Invalid team or error to load.");
            }
        } else {
            System.out.println("Error: the team does not exist on the archive.");
        }
    }

    public void removeFromTeam(Member member, Team team){
        loadCustomFile();
        if(customFile.contains(sectionKey + "." + team.getTeamName())){
            if(teamMap.containsKey(team.getTeamName().toLowerCase())){
                ConfigurationSection teamSection = customFile.getConfigurationSection(sectionKey + "." + team.getTeamName());
                ArrayList<String> teamMembers = (ArrayList<String>) teamSection.getStringList("members");
                for(String memberUUID: teamMembers){
                    if(memberUUID.equals(member.getUUID().toString())){
                        teamMembers.remove(memberUUID);
                        break;
                    }
                }
                System.out.println(team.getTeamName() + ": " + teamMembers.toString());
                teamSection.set("members", teamMembers);
                team.removeMember(member);
                reloadCustomFile();
                if (member.isOnline()) {
                    Player playerOnline = (Player) member.getPlayer();
                    playerOnline.sendMessage("You has been kick from: " + ChatColor.GREEN + team.getTeamName());
                }
                member.setTeam(getNoTeam());
                addToTheNoTeam(member);
                teamMap.put(team.getTeamName().toLowerCase(), team);
            } else {
                System.out.println("Invalid team or error to load.");
            }
        } else {
            System.out.println("Error: the team does not exist on the archive.");
        }
    }

    public boolean isOnTeam(Player player){
        loadCustomFile();
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

    public Team getTeamOfPlayer(OfflinePlayer player){
        loadCustomFile();
        String playerUUID = player.getUniqueId().toString();
        if(customFile.contains(sectionKey)){
            ConfigurationSection sectionTeam = customFile.getConfigurationSection(sectionKey); //read the .yml file -> get the "team-list" section
            for(String teamName : sectionTeam.getKeys(false)){
                ConfigurationSection teamSection = sectionTeam.getConfigurationSection(teamName); // team-list -> [team1, team2, ...] (subsections)
                ArrayList<String> memberUUIDs = (ArrayList<String>) teamSection.getStringList("members");
                for(int i = 0; i < memberUUIDs.size(); i++) {
                    if(playerUUID.equals(memberUUIDs.get(i))){
                        return getTeam(teamName); // true: player have team.
                    }
                }
            }
        } else {
            System.out.println("Error to load team-list file.");
        }
        return null; // null -> probably error.
    }

    public Team getTeam(String teamName){
        if (teamMap.containsKey(teamName.toLowerCase())){
            return teamMap.get(teamName.toLowerCase());
        } else {
            return null;
        }
    }

    public static Team getNoTeam(){
        return teamMap.get("no-team");
    }

    public FileConfiguration getCustomFile(){
        return customFile;
    }

    private void loadCustomFile(){
        try {
            customFile.load(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCustomFile(){
        try{
            customFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadCustomFile(){
        saveCustomFile();
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
