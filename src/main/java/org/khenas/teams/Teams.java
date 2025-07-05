package org.khenas.teams;

import org.bukkit.plugin.java.JavaPlugin;
import org.khenas.teams.commands.TCreate;
import org.khenas.teams.commands.TList;
import org.khenas.teams.commands.TMyTeam;
import org.khenas.teams.events.PlayerJoin;
import org.khenas.teams.files.TeamList;

public final class Teams extends JavaPlugin {

    @Override
    public void onEnable() {

        //commands
        getCommand("tlist").setExecutor(new TList());
        getCommand("tcreate").setExecutor(new TCreate());
        getCommand("tmyteam").setExecutor(new TMyTeam());
        //setup default config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //setup teamlist
        TeamList.setup();
        TeamList.saveCustomFile();
        //events
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        TeamList.saveCustomFile();
    }
}
