package org.khenas.teams.files;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private static final String sectionKey = "invites";
    private Map<UUID, UUID> invitationMap = new HashMap<>(); // UUID 1 = receptor, UUID 2 = inviter
            // receptor - inviter

    public void loadMembers(Team team, ArrayList<String> uuidList){
        for(String uuidIndex: uuidList){
            UUID uuid = UUID.fromString(uuidIndex);
            invitationMap.put(uuid, null);
            OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(uuid);
            Member memberToAdd = new Member(team, offPlayer);
            team.addMember(memberToAdd);
        }
    }

    private boolean hasInvitations(Member member) {
        UUID memberUUID = member.getPlayer().getUniqueId();
        if (invitationMap.get(memberUUID) != null) {
            return true; // has an invitation
        }
        return false;
    }

    public void createInvitation(Member receptor, Member inviter){
        Player receptorPlayer = (Player) receptor.getPlayer();
        Player inviterPlayer = (Player) inviter.getPlayer();
        if(hasInvitations(receptor)){
            inviterPlayer.sendMessage("...");
        } else {
            invitationMap.put(receptorPlayer.getUniqueId(), inviterPlayer.getUniqueId());
        }
    }

    public void removeInvitation(Member receptor){
        Player receptorPlayer = (Player) receptor.getPlayer();
        invitationMap.put(receptorPlayer.getUniqueId(), null);
    }





}
