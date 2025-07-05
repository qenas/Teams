package org.khenas.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;


public class TCreate implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command only available for real players.");
            return true;
        }
        Player player = (Player) commandSender;
        Member member = TeamListManager.getMemberByUUID(player);
        System.out.println(member.getPlayer().getName());
        if(member.getTeam() != null){ //if a member do not have a team, can create a new one.
            if(TeamListManager.getNoTeam().equals(member.getTeam())){
                if(strings.length == 0){
                    player.sendMessage("Invalid name or null name. Please, write a name valid name for you team.");
                } else {
                    String teamName = strings[0];
                    TeamListManager.addTeamToTheList(player, teamName);
                    TeamListManager.removeFromTheNoTeam(player);
                }
            } else {
                player.sendMessage("You alredy have a team, homie. Leave that one to create a new team.");
            }
        }
        return true;
    }

}
