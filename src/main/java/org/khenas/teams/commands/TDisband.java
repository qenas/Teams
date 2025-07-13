package org.khenas.teams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;

public class TDisband implements CommandExecutor {
    private TeamListManager teamListManager;
    private PlayerManager playerManager;

    public TDisband(TeamListManager teamListManager, PlayerManager playerManager){
        this.teamListManager = teamListManager;
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return true;
        }

        Player player = (Player) sender;
        Member playerMember = playerManager.getMemberByUUID(player);

        if(playerMember != null) {
            Team teamToRemove = playerMember.getTeam();
            if(!teamToRemove.equals(TeamListManager.getNoTeam())){
                if(teamToRemove.isLeader(player)){
                    player.sendMessage("The team " + ChatColor.RED + teamToRemove.getTeamName() + ChatColor.WHITE + " has been deleted successfully.");
                    teamListManager.removeTeamFromTheList(teamToRemove);
                } else {
                    player.sendMessage("You are not the leader of your team, buddy. You can not use this command.");
                }
            } else {
                player.sendMessage("You can not use this command because you do not have a team, buddy.");
            }
        }







        return true;
    }
}
