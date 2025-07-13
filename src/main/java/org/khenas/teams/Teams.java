package org.khenas.teams;

import org.bukkit.plugin.java.JavaPlugin;
import org.khenas.teams.commands.*;
import org.khenas.teams.events.PlayerConnection;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;


public final class Teams extends JavaPlugin {
    private TeamListManager teamListManager = new TeamListManager();
    private PlayerManager playerManager = new PlayerManager();

    @Override
    public void onEnable() {

        //setup default config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //setup teamlist
        teamListManager.setup();
        playerManager.setup();
        //events
        getServer().getPluginManager().registerEvents(new PlayerConnection(teamListManager, playerManager), this);
        //commands
        //getCommand("tadd").setExecutor(new TAdd());
        getCommand("tlist").setExecutor(new TList(teamListManager));
        getCommand("tcreate").setExecutor(new TCreate(teamListManager, playerManager));
        getCommand("tmyteam").setExecutor(new TMyTeam(playerManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        teamListManager.saveCustomFile();
        playerManager.saveCustomFile();
    }
}
