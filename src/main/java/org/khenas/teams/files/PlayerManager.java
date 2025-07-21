package org.khenas.teams.files;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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

public class PlayerManager {
    private static String sectionKey = "uuids";
    private File file;
    private FileConfiguration customFile;
    private ArrayList<UUID> playerList;
    private Map<UUID, Member> membersMap = new HashMap<>();

    public void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Teams").getDataFolder(), "uuids.yml");
        if(!file.exists()){ //if the file 'uuids.yml' does not exist, this creates a new one.
            try{
                System.out.println("Creating 'uuids.yml' file...");
                file.createNewFile();
                customFile = YamlConfiguration.loadConfiguration(file);
                getCustomFile().createSection(sectionKey + ".list");
                saveCustomFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
        loadPlayerList();
        saveCustomFile();
    }

    public void addUUIDtoArchive(UUID player){
        loadCustomFile();
        ArrayList<String> uuids = (ArrayList<String>) customFile.getStringList(sectionKey + ".list");
        if(!uuids.contains(player.toString())){
            uuids.add(player.toString());
            customFile.set(sectionKey + ".list", uuids);
        } else {
            System.out.println("Error: the player is on the list.");
        }
        loadPlayerList();
        reloadCustomFile();
    }

    private void loadPlayerList(){
        playerList = new ArrayList<>();
        if(customFile.contains(sectionKey + ".list")){
            ArrayList<String> uuids = (ArrayList<String>) customFile.getStringList(sectionKey + ".list");
            for(String uuid: uuids){
                playerList.add(UUID.fromString(uuid));
            }
        }
    }

    public boolean isPlayerListEmpty(){
        if(playerList.size() == 0){
            System.out.println("PlayerManager: PlayerList is empty...");
            return true;
        }
        return false;
    }

    public void setupMember(Team team, UUID player){
        Member newMember = new Member(team, player);
        OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(player);
        System.out.println(offPlayer.getName());
        team.addMember(newMember);
        if(newMember != null){
            if(offPlayer.isOnline()){
                newMember.setOnline(true);
                membersMap.put(player, newMember);
                System.out.println("Adding " + offPlayer.getName() + " from the 'members' map. (ONLINE)");
            } else {
                newMember.setOnline(false);
                membersMap.put(player, newMember);
                System.out.println("Adding " + offPlayer.getName() + " to the 'members' map. (OFFLINE)");
            }
        } else {
            System.out.println("Error: newMember is null");
        }
    }

    public void setMemberOnline(Player player){
        Member member = getMemberByUUID(player);
        if(member != null){
            member.setOnline(true);
            System.out.println("Putting online to the member: " + player.getName());
        } else {
            System.out.println("Error: this player does not have a member object associated.");
        }
    }

    public void setMemberOffline(Player player){
        Member member = getMemberByUUID(player);
        if(member != null){
            member.setOnline(false);
            System.out.println("Putting offline to the member: " + player.getName());
        } else {
            System.out.println("Error: this player does not have a member object associated.");
        }
    }

    public boolean isOnPlayerList(OfflinePlayer player){ // if is on the array of previous joined members
        if(playerList.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public Member getMemberByUUID(OfflinePlayer player){
        UUID uuidMember = player.getUniqueId();
        if(membersMap.containsKey(uuidMember)){
            return membersMap.get(uuidMember);
        }
        return null; // member does not exist.
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
            System.out.println(e);
        }
    }

    public void reloadCustomFile(){
        saveCustomFile();
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
