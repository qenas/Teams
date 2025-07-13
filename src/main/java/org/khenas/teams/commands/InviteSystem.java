package org.khenas.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.files.InvitationManager;
import org.khenas.teams.files.PlayerManager;
import org.khenas.teams.files.TeamListManager;

public class InviteSystem implements CommandExecutor  {
    private static final String tAccept = "taccept";
    private static final String tDeny = "tdeny";
    private InvitationManager invitationManager;
    private PlayerManager playerManager;
    private TeamListManager teamListManager;

    public InviteSystem(InvitationManager invitationManager, TeamListManager teamListManager, PlayerManager playerManager){
        this.invitationManager = invitationManager;
        this.playerManager = playerManager;
        this.teamListManager = teamListManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for real players.");
            return true;
        }

        //tinvite
        if(command.getName().equalsIgnoreCase("tinvite")){
            Player playerSender = (Player) sender;
            if(teamListManager.isOnTeam(playerSender)){ // checks if the sender has a valid team (!=noteam)
                if(teamListManager.getTeamOfPlayer(playerSender).isLeader(playerSender)){
                    if(args.length > 0){
                        Player playerTarget = Bukkit.getPlayerExact(args[0]);
                        invitationManager.addInvite(playerSender, playerTarget);
                        playerSender.sendMessage("Your invitation has been sent to " + ChatColor.GREEN + playerTarget.getName() + ChatColor.WHITE + ". He must either decline or accept it.");
                        playerTarget.sendMessage(ChatColor.GREEN + playerSender.getName() + " has sent you a invitation for " + ChatColor.RED + playerManager.getMemberByUUID(playerSender).getTeam().getTeamName());
                        playerTarget.sendMessage("Use " + ChatColor.GREEN + tAccept + ChatColor.WHITE + " to accept the invitation or use " + ChatColor.RED + tDeny + ChatColor.WHITE + " to decline the invitation.");
                    } else {
                        playerSender.sendMessage("Empty arguments, please insert a player's name.");
                    }
                } else {
                    playerSender.sendMessage("You do not are the leader of your team, buddy.");
                }
            } else {
                playerSender.sendMessage("You do not have a team, buddy.");
            }
        }

        //taccept
        if(command.getName().equalsIgnoreCase(tAccept) && args.length == 0){
            Player playerTarget = (Player) sender;
            if(invitationManager.hasInvite(playerTarget)){
                Player playerToAccept = invitationManager.getSender(playerTarget);
                invitationManager.removeInvite(playerTarget);
                if(playerToAccept != null){
                    teamListManager.addToTeam(playerManager.getMemberByUUID(playerTarget), playerManager.getMemberByUUID(playerToAccept).getTeam());
                } else {
                    playerTarget.sendMessage("The player does not exist or maybe is disconnected.");
                }
            } else {
                playerTarget.sendMessage("You do not have any pending invitation, mate.");
            }
        } else {
            return true;
        }

        //tdeny
        if(command.getName().equalsIgnoreCase(tDeny) && args.length == 0){
            Player playerTarget = (Player) sender;
            if(invitationManager.hasInvite(playerTarget)){
                Player playerToAccept = invitationManager.getSender(playerTarget);
                invitationManager.removeInvite(playerTarget);
                if(playerToAccept != null){
                    playerToAccept.sendMessage(ChatColor.GREEN + playerTarget.getName() + ChatColor.WHITE + " has declined your invitation, mate.");
                } else {
                    playerTarget.sendMessage("The player does not exist or maybe is disconnected.");
                }
            } else {
                playerTarget.sendMessage("You do not have any pending invitation, mate.");
            }
        } else {
            return true;
        }

        return false;
    }
}
