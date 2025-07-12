package org.khenas.teams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;

public class TMyTeam implements CommandExecutor {
    private PlayerManager playerManager;

    public TMyTeam(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return true;
        }

        Player player = (Player) sender; //cast the CommandSender to a Player
        Member playerMember = playerManager.getMemberByUUID(player); //converts the Player to a Member type by his UUID

        if(playerMember != null) {
            if(!playerMember.getTeam().equals(TeamListManager.getNoTeam())){
                player.sendMessage(ChatColor.WHITE + "You are on the " + ChatColor.BOLD+ ChatColor.RED + playerMember.getTeam().getTeamName());
            } else {
                player.sendMessage("You do not have a team, buddy.");
            }

        } else {
            System.out.println("Error: member does not exist.");
        }
        return true;
    }
}
