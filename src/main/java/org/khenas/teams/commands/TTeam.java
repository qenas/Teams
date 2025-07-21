package org.khenas.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;

public class TTeam implements CommandExecutor {
    private PlayerManager playerManager;
    private TeamListManager teamListManager;

    public TTeam(TeamListManager teamListManager, PlayerManager playerManager){
        this.playerManager = playerManager;
        this.teamListManager = teamListManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return true;
        }

        Player player = (Player) sender; //cast the CommandSender to a Player
        Member playerMember = playerManager.getMemberByUUID(player); //converts the Player to a Member type by his UUID

        if (args.length == 0) { // shows the info of team where he is in
            if(playerMember != null) {
                Team playerTeam = playerMember.getTeam();
                if(!playerTeam.equals(TeamListManager.getNoTeam())){
                    playerTeam.showTeamInfoToPlayer(player);
                /*player.sendMessage("-------------------- " + ChatColor.RED + ChatColor.RED + playerTeam.getTeamName() + ChatColor.WHITE + " --------------------");
                player.sendMessage(ChatColor.YELLOW + "Leader: " + ChatColor.WHITE + playerTeam.getLeader().getName());
                player.sendMessage(ChatColor.YELLOW + "Members online: " + ChatColor.GREEN + playerTeam.getOnlineMembersStringList());
                player.sendMessage(ChatColor.YELLOW + "Members offline: " + ChatColor.RED + playerTeam.getOfflineMembersStringList());
                player.sendMessage(ChatColor.YELLOW + "Number of members: " + ChatColor.WHITE + playerTeam.getMembersCount());*/
                } else {
                    player.sendMessage("You do not have a team, buddy.");
                }
            } else {
                System.out.println("Error: member does not exist.");
            }
        } else { // search a team by a his name or a player name and shows that team's info
            String arg = args[0];
            if (teamListManager.getTeam(arg) != null){
                if(teamListManager.getTeam(arg).equals(TeamListManager.getNoTeam())){
                    player.sendMessage("The player does not have a team.");
                } else {
                    teamListManager.getTeam(arg).showTeamInfoToPlayer(player);
                }
            } else if (Bukkit.getOfflinePlayer(arg) != null) {
                if(playerManager.isOnPlayerList(Bukkit.getOfflinePlayer(arg))){
                    if(teamListManager.getTeamOfPlayer(Bukkit.getOfflinePlayer(arg)).equals(TeamListManager.getNoTeam())){
                        player.sendMessage("The player does not have a team.");
                    } else {
                        teamListManager.getTeamOfPlayer(Bukkit.getOfflinePlayer(arg)).showTeamInfoToPlayer(player);
                    }
                } else {
                    player.sendMessage("The player never joined to the server.");
                }
            } else {
                player.sendMessage("No team or player was found with that name.");
            }
        }




        return true;
    }
}
