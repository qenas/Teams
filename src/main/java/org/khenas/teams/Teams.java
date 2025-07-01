package org.khenas.teams;

import org.bukkit.plugin.java.JavaPlugin;
import org.khenas.teams.commands.Create;

public final class Teams extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("create").setExecutor(new Create());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
