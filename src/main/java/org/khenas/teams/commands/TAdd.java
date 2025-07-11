package org.khenas.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;

public class TAdd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        TeamListManager teamListManager = new TeamListManager();
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return true;
        }
        Player player = (Player) sender;
        if(args.length == 1){
            Player playerToAdd = Bukkit.getPlayerExact(args[0]);
            if(playerToAdd != null){
                teamListManager.addToTeam(player, playerToAdd);
            } else {
                player.sendMessage("Invalid player name. Could not find that dude.");
            }
        } else if (args.length > 1){
            player.sendMessage("Invalid syntax. Only an argument available.");
        } else {
            player.sendMessage("Insert a player name as an argument.");
        }


        return true;
    }
}
