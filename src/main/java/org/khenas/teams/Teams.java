package org.khenas.teams;

import org.bukkit.plugin.java.JavaPlugin;
import org.khenas.teams.commands.*;
import org.khenas.teams.events.PVPSystem;
import org.khenas.teams.events.PlayerConnection;
import org.khenas.teams.manager.InvitationManager;
import org.khenas.teams.manager.PlayerManager;
import org.khenas.teams.manager.TeamListManager;
import org.khenas.teams.manager.ChatManager;


public final class Teams extends JavaPlugin {
    private PlayerManager playerManager = new PlayerManager();
    private TeamListManager teamListManager = new TeamListManager();
    private InvitationManager invitationManager = new InvitationManager();
    private ChatManager chatManager = new ChatManager();

    @Override
    public void onEnable() {

        //setup default config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //setup teamlist
        playerManager.setup();
        teamListManager.setPlayerManager(playerManager);
        teamListManager.setup();
        chatManager.setupChatManager(teamListManager.getTeamMap());
        //events
        getServer().getPluginManager().registerEvents(new PlayerConnection(teamListManager, playerManager), this);
        getServer().getPluginManager().registerEvents(new PVPSystem(teamListManager, playerManager), this);
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
