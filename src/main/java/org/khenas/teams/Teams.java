package org.khenas.teams;

import org.bukkit.plugin.java.JavaPlugin;
import org.khenas.teams.commands.Create;
import org.khenas.teams.commands.List;
import org.khenas.teams.commands.MyTeam;
import org.khenas.teams.events.PlayerJoin;
import org.khenas.teams.files.TeamList;

public final class Teams extends JavaPlugin {

    @Override
    public void onEnable() {

        //commands
        getCommand("list").setExecutor(new List());
        getCommand("create").setExecutor(new Create());
        getCommand("myteam").setExecutor(new MyTeam());
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
