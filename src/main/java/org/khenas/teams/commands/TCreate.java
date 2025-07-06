package org.khenas.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;


public class TCreate implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command only available for real players.");
            return true;
        }
        Player player = (Player) commandSender;
        Member member = TeamListManager.getMemberByUUID(player);
        if(member != null){ //if the player has a object member assosiated
            if(!(TeamListManager.isOnTeam(player))){ // if the player do not have a team, then he can create a new one.
                if(strings.length == 0){
                    player.sendMessage("Invalid name or null name. Please, write a name valid name for you team.");
                } else {
                    String teamName = strings[0];
                    TeamListManager.addTeamToTheList(player, teamName);
                    TeamListManager.removeFromTheNoTeam(player);
                }
            } else {
                player.sendMessage("You already have a team, buddy. Leave that one to create a new team.");
            }
        }
        return true;
    }
}
