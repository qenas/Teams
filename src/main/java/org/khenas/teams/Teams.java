package org.khenas.teams;

import org.bukkit.plugin.java.JavaPlugin;
import org.khenas.teams.commands.*;
import org.khenas.teams.events.PlayerConnection;
import org.khenas.teams.files.InvitationManager;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;


public final class Teams extends JavaPlugin {
    private PlayerManager playerManager = new PlayerManager();
    private TeamListManager teamListManager = new TeamListManager();
    private InvitationManager invitationManager = new InvitationManager();

    @Override
    public void onEnable() {

        //setup default config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //setup teamlist
        playerManager.setup();
        teamListManager.setPlayerManager(playerManager);
        teamListManager.setup();
        //events
        getServer().getPluginManager().registerEvents(new PlayerConnection(teamListManager, playerManager), this);
        //commands
        InviteSystem inviteSystem = new InviteSystem(invitationManager, teamListManager, playerManager);
        getCommand("tinvite").setExecutor(inviteSystem);
        getCommand("taccept").setExecutor(inviteSystem);
        getCommand("tdeny").setExecutor(inviteSystem);
        getCommand("tkick").setExecutor(new TKick(teamListManager, playerManager));
        getCommand("tleave").setExecutor(new TLeave(teamListManager, playerManager));
        getCommand("tlist").setExecutor(new TList(teamListManager));
        getCommand("tcreate").setExecutor(new TCreate(teamListManager, playerManager));
        getCommand("tdisband").setExecutor(new TDisband(teamListManager, playerManager));
        getCommand("tteam").setExecutor(new TTeam(teamListManager, playerManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        teamListManager.saveCustomFile();
        playerManager.saveCustomFile();
    }
}
