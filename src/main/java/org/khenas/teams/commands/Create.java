package org.khenas.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.TeamList;


public class Create implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command only available for real players.");
            return true;
        }
        Player player = (Player) commandSender;

        if(strings.length == 0){
            player.sendMessage("Invalid name or null name. Please, write a name valid name for you team.");
        } else {
            String teamName = strings[0];
            TeamList.addTeamToTheList(player, teamName);
        }
        return true;
    }
}
