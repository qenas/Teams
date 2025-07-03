package org.khenas.teams.parts;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import java.util.ArrayList;

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
        String playerUUID = player.getUniqueId().toString(); //obtains the UUID of the player (CommandSender)
        for (Member member : members) {
            OfflinePlayer memberPlayer = member.getPlayer(); //obtains the object memberPlayer
            String memberUUID = memberPlayer.getUniqueId().toString(); //obtains the UUID of the memberPlayer
            if (playerUUID.equals(memberUUID)) {
                return member; //returns a Member object if the player form part of the array 'members'
            }
        }
        return null; //returns null if the player do not form part of the array 'members'
    }

    public ArrayList<Member> getMembers(){
        return members;
    }


}
