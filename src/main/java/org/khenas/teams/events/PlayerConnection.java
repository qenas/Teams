package org.khenas.teams.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Team;


public class PlayerConnection implements Listener {
    private TeamListManager teamListManager;
    private PlayerManager playerManager;

    public PlayerConnection(TeamListManager teamListManager, PlayerManager playerManager){
        this.teamListManager = teamListManager;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        //System.out.println(playerManager.getPlayerList().toString());
        if(playerManager.isPlayerListEmpty()){ // empty list, first joined player ever
            playerFirstJoin(player);
        } else {
            if(playerManager.isOnPlayerList(player)) { // the player already join at least one time to the server.
                if(playerManager.getMemberByUUID(player) != null) {
                    playerManager.setMemberOnline(player);
                    Team teamOfPlayer = teamListManager.getTeamOfPlayer(player);
                    if (teamOfPlayer == null) {
                        System.out.println("Error to load the team of this player: " + player.getName() + "/" + player.getUniqueId());
                    } else {
                        if (teamOfPlayer.equals(teamListManager.getNoTeam())) {
                            player.sendMessage("You do not have a team, buddy.");
                        } else {
                            player.sendMessage("Your team is: " + ChatColor.RED + teamOfPlayer.getTeamName());
                        }
                    }
                } else {
                    System.out.println("Error: no object member is associated to this player.");
                }
            } else { // The player joins for the first time at the server.
                playerFirstJoin(player);
            }
        }
    }

    @EventHandler
    public void onPlayerLeaves(PlayerQuitEvent event){
        Player player = event.getPlayer();
        playerManager.setMemberOffline(player);
    }


    private void playerFirstJoin(Player player){
        playerManager.addUUIDtoArchive(player.getUniqueId());
        playerManager.setupMember(TeamListManager.getNoTeam(), player.getUniqueId());
        teamListManager.addToTheNoTeam(playerManager.getMemberByUUID(player));
        System.out.println(player.getName() + " first time joining the server. Added to the 'no-team' list.");
        player.sendMessage("Welcome, " + player.getName() + ". You do not have a team, try with joining a created one or create your own team.");
    }
}
