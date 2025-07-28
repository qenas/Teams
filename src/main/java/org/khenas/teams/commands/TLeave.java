package org.khenas.teams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;


public class TLeave implements CommandExecutor {
    private TeamListManager teamListManager;
    private PlayerManager playerManager;

    public TLeave (TeamListManager teamListManager, PlayerManager playerManager) {
        this.teamListManager = teamListManager;
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return false;
        }

        Player player = (Player) sender;
        if(teamListManager.isOnTeam(player)){
            if(teamListManager.getTeamOfPlayer(player).isLeader(player)){
                player.sendMessage("You can not leave that easy, use " + ChatColor.RED + "/tdisband " + ChatColor.WHITE + "instead.");
            } else {
                teamListManager.removeFromTeam(playerManager.getMemberByUUID(player), teamListManager.getTeamOfPlayer(player));
            }
        } else {
            player.sendMessage("You already do not have a team, buddy.");
        }




        return true;
    }
}
