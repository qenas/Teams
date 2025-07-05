package org.khenas.teams.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.khenas.teams.files.TeamList;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;


public class PlayerJoin implements Listener {
    //to-do: arreglar el bug de que sea null al ingresar. line: 21
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Member playerMember = TeamList.getMemberByUUID(player);
        if(playerMember == null){ // The player joins for the first time at the server.
            //the player does not have a object member associated.
            TeamList.addToTheNoTeam(player);
            System.out.println(player.getName() + " added to the 'no-team' list.");
        } else {
            if(TeamList.isOnTeam(player)){
                // The player does not is on no-team list, so he has a team.
                Team playerTeam = playerMember.getTeam();
                player.sendMessage("Your team is: " + ChatColor.RED + playerTeam.getTeamName());
            } else {
                // The player already joined to the server al least one time, but does not have a team.
                player.sendMessage("You do not have a team, buddy.");
            }
        }
    }
}
