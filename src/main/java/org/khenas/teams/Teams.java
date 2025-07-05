package org.khenas.teams;

import org.bukkit.plugin.java.JavaPlugin;
import org.khenas.teams.commands.*;
import org.khenas.teams.events.PlayerJoin;
import org.khenas.teams.files.TeamListManager;


public final class Teams extends JavaPlugin {

    @Override
    public void onEnable() {

        //commands
        getCommand("tadd").setExecutor(new TAdd());
        getCommand("tlist").setExecutor(new TList());
        getCommand("tcreate").setExecutor(new TCreate());
        getCommand("tmyteam").setExecutor(new TMyTeam());
        //setup default config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //setup teamlist
        TeamListManager.setup();
        TeamListManager.saveCustomFile();
        //events
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        TeamListManager.saveCustomFile();
    }
}
