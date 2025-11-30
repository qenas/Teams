package org.khenas.teams.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InvitationManager {
    private static long timerCooldown = 60000;
    private Map<UUID, Long> cooldown = new HashMap<>();
    private Map<UUID, UUID> invitations = new HashMap<>();
            //uuid1 = target, uuid2 = sender


    public static long getTimerCooldownInSeconds(){
        return timerCooldown / 1000;
    }

    public boolean isInvitationExpired(Player target) {
        return System.currentTimeMillis() - cooldown.get(target.getUniqueId()) >= 60000;
    }

    public void addInvite(Player sender, Player target){
        invitations.put(target.getUniqueId(), sender.getUniqueId());
        cooldown.put(target.getUniqueId(), System.currentTimeMillis()); // load the cooldown for the player target with the time it was created
    }

    public void removeInvite(Player target){
        invitations.remove(target.getUniqueId());
        cooldown.put(target.getUniqueId(), System.currentTimeMillis());
    }

    public boolean hasInvite(Player target){
        return invitations.containsKey(target.getUniqueId());
    }

    public Player getSender(Player target){
        UUID senderUUID = invitations.get(target.getUniqueId());
        return Bukkit.getPlayer(senderUUID);
    }

}
