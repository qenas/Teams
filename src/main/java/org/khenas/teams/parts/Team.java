package org.khenas.teams.parts;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.UUID;

public class Team {
    private final String teamName;
    private OfflinePlayer leader;
    private final ArrayList<Member> members;

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
        boolean isOnTheTeam = false;
        for(Member member: members){
            if(member.getPlayer().getUniqueId().equals(newMember.getPlayer().getUniqueId())){
                isOnTheTeam = true;
            }
        }
        if(!isOnTheTeam){
            members.add(newMember);
        }
    }

    public void removeMember(Member oldMember){
        boolean isOnTheTeam = true;
        int index = 0;
        for(int i = 0; i < members.size(); i++){
            if(oldMember.getPlayer().equals(members.get(i).getPlayer())){
                isOnTheTeam = false;
                index = i;
            }
        }
        if(isOnTheTeam){
            members.remove(index);
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
                return "All the members are offline right now.";
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
