package org.khenas.teams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.TeamList;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;

public class TMyTeam implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return true;
        }
        Player player = (Player) sender; //cast the CommandSender to a Player
        Member playerMember = TeamList.getMemberByUUID(player); //converts the Player to a Member type by his UUID
        if(playerMember == null){
            System.out.println("Error on loading the team of the player");
        } else {
            if(TeamList.isOnTeam(player)){
                Team playerTeam = playerMember.getTeam();
                player.sendMessage(ChatColor.WHITE + "You are on the " + ChatColor.BOLD+ ChatColor.RED + playerTeam.getTeamName());
            } else {
                player.sendMessage("You do not have a team, buddy.");
            }
        }
        return true;
    }
}
