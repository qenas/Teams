package org.khenas.teams.parts;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.nio.channels.MembershipKey;
import java.util.ArrayList;
import java.util.UUID;

public class Team {
    private String teamName;
    private Player leader;
    private static ArrayList<Member> members;

    //new team
    public Team(String teamName){
        this.teamName = teamName;
        this.members = new ArrayList<>();
    }

    public int getMembersCount(){
        return members.size();
    }

    public void setLeader(Player leader){
        this.leader = leader;
    }

    public void addMember(Member newMember){
        members.add(newMember);
    }

    public String getTeamName(){
        return teamName;
    }

    public static Member getMemberByUUID(Player player){
        String playerUUID = player.getUniqueId().toString();
        for(int i = 0; i < members.size(); i++){
            OfflinePlayer memberPlayer = members.get(i).getPlayer();
            String memberUUID = memberPlayer.getUniqueId().toString();
            if(playerUUID.equals(memberUUID)){
                return members.get(i);
            }
        }
        return null;
    }

    public ArrayList<Member> getMembers(){
        return members;
    }


}
