package org.khenas.teams.manager;


import org.bukkit.entity.Player;
import org.khenas.teams.parts.Member;
import org.khenas.teams.parts.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*public class ChatManager {
    private Map<String, ArrayList<Player>> teamChannels;
    private ArrayList<Player> globalChannel;

    public void loadChannels(ArrayList<Team> teams){
        for (Team team: teams) {
            ArrayList<Player> players = new ArrayList<>();
            for (Member member: team.getOnlineMembers()) {
                players.add((Player) member.getPlayer());
            }
            teamChannels.put(team.getTeamName().toLowerCase(), players);
        }
        globalChannel = new ArrayList<>();
    }

    public void joinTeamChannel (Member member) {
        if (teamChannels.get(member.getTeam().getTeamName().toLowerCase()) != null) {
            ArrayList<Player> players = teamChannels.get(member.getTeam().getTeamName().toLowerCase());
            players.add((Player) member.getPlayer());
            teamChannels.put(member.getTeam().getTeamName().toLowerCase(), players);
        }
    }
}*/

public class ChatManager {
    private Map<String, String>;
    public void setupChatManager(Map<String, Team> teamMap){
        this.teamMap = teamMap;
    }
}
