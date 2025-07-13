package org.khenas.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;


public class TCreate implements CommandExecutor {
    private TeamListManager teamListManager;
    private PlayerManager playerManager;

    public TCreate(TeamListManager teamListManager, PlayerManager playerManager){
        this.teamListManager = teamListManager;
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command only available for real players.");
            return true;
        }

        Player player = (Player) commandSender;

        if(!teamListManager.isOnTeam(player)){
            Member playerMember = playerManager.getMemberByUUID(player);
            if(strings.length == 0){
                player.sendMessage("Invalid name or null name. Please, write a name valid name for you team.");
            } else {
                String teamName = strings[0];
                teamListManager.addTeamToTheList(playerMember, teamName);
                teamListManager.removeFromTheNoTeam(playerMember);
            }
        } else {
            player.sendMessage("You already have a team, buddy. Leave that one to create a new team.");
        }

        return true;
    }
}
