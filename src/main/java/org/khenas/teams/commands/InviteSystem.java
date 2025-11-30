package org.khenas.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.khenas.teams.manager.InvitationManager;
import org.khenas.teams.manager.PlayerManager;
import org.khenas.teams.manager.TeamListManager;

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
                if(teamListManager.getTeamOfPlayer(playerSender).isLeader(playerSender)){ // checks if the sender is the leader of the team
                    if(args.length > 0 && args.length == 1){
                        Player playerTarget = Bukkit.getPlayerExact(args[0]);
                        if(!teamListManager.isOnTeam(playerTarget)){ // checks if the target already has a team.
                            invitationManager.addInvite(playerSender, playerTarget);
                            playerSender.sendMessage("Your invitation has been sent to " + ChatColor.GREEN + playerTarget.getName() + ChatColor.WHITE + ". He must either decline or accept it.");
                            playerTarget.sendMessage(ChatColor.GREEN + playerSender.getName() + " has sent you a invitation for " + ChatColor.RED + playerManager.getMemberByUUID(playerSender).getTeam().getTeamName());
                            playerTarget.sendMessage("Use " + ChatColor.GREEN + "/" + tAccept + ChatColor.WHITE + " to accept the invitation or use " + ChatColor.RED + "/" + tDeny + ChatColor.WHITE + " to decline the invitation.");
                            playerTarget.sendMessage("You have " + InvitationManager.getTimerCooldownInSeconds() + " seconds to something with this invite.");
                        } else {
                            playerSender.sendMessage("You can not invite the player " + ChatColor.GREEN + playerTarget.getName() + ChatColor.WHITE + " because he already has a team.");
                        }
                    } else {
                        playerSender.sendMessage("Empty arguments, please insert a player's name.");
                    }
                } else {
                    playerSender.sendMessage("You do not are the leader of your team, buddy.");
                }
            } else {
                playerSender.sendMessage("You do not have a team, buddy.");
            }
            return true;
        }

        //taccept
        if(command.getName().equalsIgnoreCase(tAccept) && args.length == 1){
            Player playerTarget = (Player) sender;
            Player playerSender = Bukkit.getPlayerExact(args[0]);
            if (invitationManager.getSender(playerTarget).equals(playerSender)){
                if(invitationManager.hasInvite(playerTarget)){
                    if (!invitationManager.isInvitationExpired(playerTarget)){
                        Player playerToAccept = invitationManager.getSender(playerTarget);
                        if(playerToAccept != null){
                            teamListManager.addToTeam(playerManager.getMemberByUUID(playerTarget), playerManager.getMemberByUUID(playerToAccept).getTeam());
                            invitationManager.getSender(playerTarget).sendMessage("The player " + ChatColor.GREEN + playerTarget.getName() + ChatColor.WHITE + " accepted your invitation.");
                        } else {
                            playerTarget.sendMessage("The player does not exist or maybe is disconnected.");
                        }
                        invitationManager.removeInvite(playerTarget);
                    } else {
                        playerTarget.sendMessage("The time to accept the invitation has passed away.");
                        invitationManager.getSender(playerTarget).sendMessage("The timer for the player " + playerTarget.getName() + " to accept your invitation has passed away.");
                        invitationManager.removeInvite(playerTarget);
                    }
                } else {
                    playerTarget.sendMessage("You do not have any pending invitation, mate.");
                }
            } else {
                playerTarget.sendMessage("That player does not invite you to his team.");
            }
            return true;
        }

        //tdeny
        if(command.getName().equalsIgnoreCase(tDeny) && args.length == 1){
            Player playerTarget = (Player) sender;
            Player playerSender = Bukkit.getPlayerExact(args[0]);
            if (invitationManager.getSender(playerTarget).equals(playerSender)){
                if(invitationManager.hasInvite(playerTarget)){
                    if (!invitationManager.isInvitationExpired(playerTarget)){
                        Player playerToDeny = invitationManager.getSender(playerTarget);
                        if(playerToDeny != null){
                            playerToDeny.sendMessage(ChatColor.GREEN + playerTarget.getName() + ChatColor.WHITE + " has declined your invitation, mate.");
                            playerTarget.sendMessage("You declined the invitation for " + ChatColor.RED + teamListManager.getTeamOfPlayer(playerToDeny).getTeamName() + ".");
                        } else {
                            playerTarget.sendMessage("The player does not exist or maybe is disconnected.");
                        }
                        invitationManager.removeInvite(playerTarget);
                    } else {
                        playerTarget.sendMessage("The time to accept the invitation has passed away.");
                        invitationManager.getSender(playerTarget).sendMessage("The timer for the player " + playerTarget.getName() + " to accept your invitation has passed away.");
                        invitationManager.removeInvite(playerTarget);
                    }
                } else {
                    playerTarget.sendMessage("You do not have any pending invitation, mate.");
                }
            } else {
                playerTarget.sendMessage("That player does not invite you to his team.");
            }
            return true;
        }

        return false;
    }
}
