package org.khenas.teams.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TeamList {

    private static File file;
    private static FileConfiguration customFile;

    //finds or generates the configuration file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Teams").getDataFolder(), "teamslist.yml");
        if(!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);

    }

    public static FileConfiguration getCustomFile(){
        return customFile;
    }

    public static void setCustomFile(FileConfiguration file){
        customFile = file;
    }

    public static void saveCustomFile(){
        try{
            customFile.save(file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void reloadCustomFile(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
