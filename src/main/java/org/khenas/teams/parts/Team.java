package org.khenas.teams.parts;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Team {
    private String teamName;
    private OfflinePlayer leader;
    private  ArrayList<Member> members;

    //new team
    public Team(String teamName){
        this.teamName = teamName;
        members = new ArrayList<>();
    }

    public int getMembersCount(){
        return members.size();
    }

    public void setLeader(OfflinePlayer leader){
        this.leader = leader;
    }

    public Player getLeader(){
        return leader.getPlayer();
    }

    public void addMember(Member newMember){
        members.add(newMember);
    }

    public void removeMember(Member oldMember){
        for(int i = 0; i < members.size(); i++){
            if(oldMember.getPlayer().equals(members.get(i).getPlayer())){
                members.remove(i);
            }
        }
    }

    public String getTeamName(){
        return teamName;
    }

    public boolean isLeader(Player other){
        String leaderUUID = leader.getUniqueId().toString();
        String otherUUID = other.getUniqueId().toString();
        return leaderUUID.equals(otherUUID);
    }

    public ArrayList<Member> getMembers(){
        return members;
    }

    public String getOnlineMembersStringList(){
        StringBuilder cad = new StringBuilder();
        for(Member member: getMembers()){
            if(member.isOnline()){
                cad.append(member.getPlayer().getName()).append(", ");
            }
        }


        if(!getMembers().isEmpty()){
            if(cad.isEmpty()){
                return "No body is online right now.";
            } else {
                cad.setLength(cad.length() - 2);
            }
        }

        return cad.toString();
    }

    public String getOfflineMembersStringList(){
        StringBuilder cad = new StringBuilder();
        for(Member member: getMembers()){
            if(!member.isOnline()){
                cad.append(member.getPlayer().getName()).append(", ");
            }
        }

        if(!getMembers().isEmpty()){
            if(cad.isEmpty()){
                return "All the members are online right now.";
            } else {
                cad.setLength(cad.length() - 2);
            }
        }

        return cad.toString();
    }

    public boolean equals(Team other){
        return this.getTeamName().equals(other.getTeamName());
    }


}
