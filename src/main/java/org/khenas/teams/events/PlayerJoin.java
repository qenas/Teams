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
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(TeamList.isOnTeam(player)){
            Member playerMember = Team.getMemberByUUID(player);
            player.sendMessage("Your team is: " + ChatColor.RED + playerMember.getTeam().getTeamName());
        } else {
            Member noMember = new Member(TeamList.getNoTeam(), player);
            TeamList.getNoTeam().addMember(noMember);
        }

    }
}
