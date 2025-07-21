package org.khenas.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Team;

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
            return true;
        }

        Player player = (Player) sender;
        if(teamListManager.isOnTeam(player)){
            teamListManager.removeFromTeam(playerManager.getMemberByUUID(player), teamListManager.getTeamOfPlayer(player));
        } else {
            player.sendMessage("You already do not have a team, buddy.");
        }




        return false;
    }
}
