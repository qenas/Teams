package org.khenas.teams.files;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
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
    private Map<UUID, Member> onlineMembers = new HashMap<>();

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

    public void addUUIDtoArchive(Player player){
        loadCustomFile();
        ArrayList<String> uuids = (ArrayList<String>) customFile.getStringList(sectionKey + ".list");
        if(!uuids.contains(player.getUniqueId().toString())){
            uuids.add(player.getUniqueId().toString());
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

    public void setupMember(Team team, Player player){
        Member newMember = new Member(team, player);
        addOnlineMember(newMember);
        team.addMember(newMember);
        System.out.println("Adding " + player.getName() + " from the 'onlineMembers' map.");
    }

    public void addOnlineMember(Member member){
        UUID uuidMember = member.getPlayer().getUniqueId();
        onlineMembers.put(uuidMember, member);
    }

    public void removeOnlineMember(Player player){
        UUID uuidMember = player.getUniqueId();
        onlineMembers.get(uuidMember).setOnline(player.isOnline());
        onlineMembers.remove(uuidMember);
        System.out.println("Removing " + player.getName() + " from the 'onlineMembers' map.");
    }

    public boolean isOnPlayerList(Player player){ // if is on the array of previous joined members
        if(playerList.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public Member getMemberByUUID(Player player){
        for(Member member: onlineMembers.values()){
            UUID playerUUID = player.getUniqueId();
            if(member.getPlayer().getUniqueId().equals(playerUUID)){
                return member;
            }
        }
        return null; // returns null if the player does not have an object member associated.
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
