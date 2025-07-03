package org.khenas.teams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;

public class MyTeam implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return true;
        }
        Player p = (Player) sender; //cast the CommandSender to a Player
        Member player = Team.getMemberByUUID(p); //converts the Player to a Member type by his UUID
        if(player != null){ //if the player form part of a team
            Team playerTeam = player.getTeam();
            p.sendMessage(ChatColor.WHITE + "You are on the " + ChatColor.BOLD+ ChatColor.RED + playerTeam.getTeamName());
        } else {
            p.sendMessage("You do not have a team, homie.");
        }
        return true;
    }
}
