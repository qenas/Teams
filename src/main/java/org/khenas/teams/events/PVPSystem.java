package org.khenas.teams.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;

public class PVPSystem implements Listener {
    private PlayerManager playerManager;
    private TeamListManager teamListManager;

    public PVPSystem (TeamListManager teamListManager, PlayerManager playerManager) {
        this.teamListManager = teamListManager;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerHit (EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            System.out.println("player hit player");
            Player defender = (Player) event.getEntity();
            Member memberDefender = playerManager.getMemberByUUID(defender);
            Player attacker = (Player) event.getDamager();
            Member memberAttacker = playerManager.getMemberByUUID(attacker);
            if (teamListManager.isOnTeam(defender) && teamListManager.isOnTeam(attacker)) {
                if (memberDefender.isTeammate(memberAttacker)) {
                    event.setCancelled(true);
                    attacker.sendMessage("You can not hit " + ChatColor.DARK_GREEN + defender.getName() + ChatColor.WHITE + " because he is your team mate.");
                }
            } // else they did not have a team but can interact between them anyway.
        } else {
            System.out.println("NO PLAYER INTERACTION.");
        }
    }


}
