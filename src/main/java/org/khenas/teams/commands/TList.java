package org.khenas.teams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Team;

import java.util.ArrayList;

public class TList implements CommandExecutor {
    private TeamListManager teamListManager;

    public TList(TeamListManager teamListManager){
        this.teamListManager = teamListManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Command only available for real players.");
            return true;
        }
        Player player = (Player) commandSender;
        ArrayList<Team> teamList = new ArrayList<>(teamListManager.getTeamMap().values());
        player.sendMessage(ChatColor.RED + "-----TEAM LIST-----");
        for(Team team: teamList){
            if(!team.getTeamName().equals("no-team")) {
                player.sendMessage(ChatColor.DARK_GREEN + team.getTeamName() + ChatColor.WHITE + ". Members online: " + team.getOnlineCount());
            }
        }
        return true;
    }
}
