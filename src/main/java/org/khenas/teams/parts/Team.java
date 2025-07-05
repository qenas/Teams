package org.khenas.teams.parts;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Team {
    private String teamName;
    private Player leader;
    private  ArrayList<Member> members;

    //new team
    public Team(String teamName){
        this.teamName = teamName;
        members = new ArrayList<>();
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

    public ArrayList<Member> getMembers(){
        return members;
    }

    public boolean equals(Team other){
        return this.getTeamName().equals(other.getTeamName());
    }


}
