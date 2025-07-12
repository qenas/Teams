package org.khenas.teams.files;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private ArrayList<UUID> playerList;
    private Map<UUID, Member> onlineMembers = new HashMap<>();

    public PlayerManager(ArrayList<UUID> playerList){
        this.playerList = playerList;
    }

    public void loadMembers(Team team, ArrayList<String> uuidList){
        for(String uuidIndex: uuidList){
            UUID uuid = UUID.fromString(uuidIndex);
            OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(uuid);
            Member memberToAdd = new Member(team, offPlayer);
            team.addMember(memberToAdd);
        }
    }

    public void setupMember(Team team, Player player){
        Member newMember = new Member(team, player);
        addOnlineMember(newMember);
    }

    public void addOnlineMember(Member member){
        UUID uuidMember = member.getPlayer().getUniqueId();
        onlineMembers.put(uuidMember, member);
    }

    public void removeOnlineMember(Member member){
        UUID uuidMember = member.getPlayer().getUniqueId();
        onlineMembers.remove(uuidMember, member);
    }

    public boolean isOnPlayerMap(Player player){
        if(playerList.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public Member getMemberByUUID(Player player){
        for(Member member: onlineMembers.values()){
            UUID playerUUID = player.getUniqueId();
            if(member.getPlayer().getUniqueId().equals(playerUUID)){
                return member;
            }
        }
        return null; // returns null if the player does not have a object member associated.
    }

}
