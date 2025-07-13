package org.khenas.teams.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;


public class PlayerJoin implements Listener {
    private TeamListManager teamListManager;
    private PlayerManager playerManager;

    public PlayerJoin(TeamListManager teamListManager, PlayerManager playerManager){
        this.teamListManager = teamListManager;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        //System.out.println(playerManager.getPlayerList().toString());
        if(playerManager.isPlayerListEmpty()){
            playerJoining(player);
        } else {
            if(playerManager.isOnPlayerList(player)) { // the player already join at least one time to the server.
                Team teamOfPlayer = teamListManager.getTeamOfPlayer(player);
                if (teamOfPlayer == null) {
                    System.out.println("Error to load the team of this player: " + player.getName() + "/" + player.getUniqueId());
                } else {
                    playerManager.setupMember(teamOfPlayer, player);
                    if (teamOfPlayer.equals(teamListManager.getNoTeam())) {
                        player.sendMessage("You do not have a team, buddy.");
                    } else {
                        player.sendMessage("Your team is: " + ChatColor.RED + teamOfPlayer.getTeamName());
                    }
                }
            } else { // The player joins for the first time at the server.
               playerJoining(player);
            }
        }
    }

    private void playerJoining(Player player){
        playerManager.addUUIDtoArchive(player);
        playerManager.setupMember(teamListManager.getNoTeam(), player);
        teamListManager.addToTheNoTeam(playerManager.getMemberByUUID(player));
        System.out.println(player.getName() + " first time joining the server. Added to the 'no-team' list.");
        player.sendMessage("Welcome, " + player.getName() + ". You do not have a team, try with joining a created one or create your own team.");
    }
}
