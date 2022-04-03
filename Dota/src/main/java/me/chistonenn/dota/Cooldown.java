package me.chistonenn.dota;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {

    public static HashMap<UUID, Double> cooldownsStrength;
    public static HashMap<UUID, Double> cooldownsWrath;

    public static void setupCooldownStrength() {
        cooldownsStrength = new HashMap<>();
    }

    public static void setCooldownStrength(Player player, int seconds) {
        double delay = (System.currentTimeMillis() + (seconds * 1000L));
        cooldownsStrength.put(player.getUniqueId(), delay);
    }

    public static boolean checkCooldownStrength(Player player) {
        if (!cooldownsStrength.containsKey(player.getUniqueId()) || (Double) cooldownsStrength.get(player.getUniqueId()) <= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }


    public static void setupCooldownWrath() {
        cooldownsWrath = new HashMap<>();
    }

    public static void setCooldownWrath(Player player, int seconds) {
        double delay = (System.currentTimeMillis() + (seconds * 1000L));
        cooldownsWrath.put(player.getUniqueId(), delay);
    }

    public static boolean checkCooldownWrath(Player player) {
        if (!cooldownsWrath.containsKey(player.getUniqueId()) || (Double) cooldownsWrath.get(player.getUniqueId()) <= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
