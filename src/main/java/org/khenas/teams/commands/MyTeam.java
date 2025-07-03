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
        Member player = Team.getMemberByUUID(((Player) sender).getPlayer());
        Player p = (Player) sender;
        p.sendMessage(ChatColor.WHITE + "You are on the " + ChatColor.BOLD+ ChatColor.RED + player.getTeam().getTeamName());
        return true;
    }
}
