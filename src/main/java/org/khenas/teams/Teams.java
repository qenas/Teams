package org.khenas.teams;

import org.bukkit.plugin.java.JavaPlugin;
import org.khenas.teams.commands.Create;
import org.khenas.teams.commands.List;
import org.khenas.teams.files.TeamList;
import org.khenas.teams.parts.Member;

public final class Teams extends JavaPlugin {

    @Override
    public void onEnable() {
        //events
        getServer().getPluginManager().registerEvents(new Member(), this);
        //commands
        getCommand("list").setExecutor(new List());
        getCommand("create").setExecutor(new Create());
        //setup default config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //setup teamlist
        TeamList.setup();
        TeamList.saveCustomFile();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        TeamList.saveCustomFile();
    }
}
