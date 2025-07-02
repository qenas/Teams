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
import java.util.HashMap;
import java.util.Map;

public class TeamList {
    private static String sectionKey = "team-list";
    private static File file;
    private static FileConfiguration customFile;
    private Map<String, Team> teamMap = new HashMap<>();

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
    }

    public static void addTeamToTheList(Player player, String teamName){
        try {
            customFile.load(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(!customFile.contains("team-list." + teamName)){
            getCustomFile().createSection("team-list." + teamName);
            player.sendMessage(ChatColor.GREEN + "Team has been created successfully.");
            player.sendMessage("Team created. The leader are -" + ChatColor.AQUA + player.getName());
        } else {
            player.sendMessage(ChatColor.RED + "Unavailable team name. Try with another name.");
        }
        reloadCustomFile();
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
