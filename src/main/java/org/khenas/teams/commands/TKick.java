package org.khenas.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Team;

public class TKick implements CommandExecutor {
    private TeamListManager teamListManager;
    private PlayerManager playerManager;

    public TKick(TeamListManager teamListManager, PlayerManager playerManager){
        this.teamListManager = teamListManager;
        this.playerManager = playerManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return true;
        }

        Player leader = (Player) sender;
        Team team = teamListManager.getTeamOfPlayer(leader);
        if(team != null){
            if(team.isLeader(leader)){
                if(args.length == 1){
                    OfflinePlayer playerToKick = Bukkit.getOfflinePlayer(args[0]);
                    if (playerToKick != null) {
                        teamListManager.removeFromTeam(playerManager.getMemberByUUID(playerToKick), team);
                        leader.sendMessage("The player " + ChatColor.RED + playerToKick.getName() + ChatColor.WHITE + " has been kicked from your team.");
                    } else {
                        System.out.println("Error to load the member of that player.");
                    }
                } else {
                    leader.sendMessage("Insert a valid argument (player's name).");
                }
            } else {
                leader.sendMessage("You are not the leader of your team. The leader is the only who can use this command, brodie.");
            }
        } else {
            System.out.println("Error to load the team of leader.");
        }


        return true;
    }
}
