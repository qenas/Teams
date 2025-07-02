package org.khenas.teams.parts;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {
    private String teamName;
    private Player leader;
    private ArrayList<Player> members;

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

    public void addMember(Player newMember){
        members.add(newMember);
    }

    public String getTeamName(){
        return teamName;
    }




}
