package me.chistonenn.dota;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerRegister implements Listener {

    Dota plugin;

    public PlayerRegister(Dota plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            player.sendMessage("§bДобро пожаловать! Удачной вам игры :)");

            Dota.playerManagerHashMap.put(player.getUniqueId(), new PlayerManager(1, 1, 0, 0, 0, 0, 0));
            plugin.getConfig().set("PlayerInfo." + player.getName() + ".minerlevel", 1);
            plugin.getConfig().set("PlayerInfo." + player.getName() + ".warlevel", 1);
            plugin.getConfig().set("PlayerInfo." + player.getName() + ".minerxp", 0);
            plugin.getConfig().set("PlayerInfo." + player.getName() + ".warxp", 0);
            plugin.getConfig().set("PlayerInfo." + player.getName() + ".rating", 0);
            plugin.getConfig().set("PlayerInfo." + player.getName() + ".wins", 0);
            plugin.getConfig().set("PlayerInfo." + player.getName() + ".losses", 0);
            plugin.saveConfig();
        } else {
            int minerlevel = plugin.getConfig().getInt("PlayerInfo." + player.getName() + ".minerlevel");
            int warlevel = plugin.getConfig().getInt("PlayerInfo." + player.getName() + ".warlevel");
            int minerxp = plugin.getConfig().getInt("PlayerInfo." + player.getName() + ".minerxp");
            int warxp = plugin.getConfig().getInt("PlayerInfo." + player.getName() + ".warxp");
            int rating = plugin.getConfig().getInt("PlayerInfo." + player.getName() + ".rating");
            int wins = plugin.getConfig().getInt("PlayerInfo." + player.getName() + ".wins");
            int losses = plugin.getConfig().getInt("PlayerInfo." + player.getName() + ".losses");
            Dota.playerManagerHashMap.put(player.getUniqueId(), new PlayerManager(minerlevel, warlevel, minerxp, warxp, rating, wins, losses));
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("");
        PlayerManager playerManager = Dota.playerManagerHashMap.get(player.getUniqueId());
        //Bukkit.getScheduler().runTaskLater(Dota.getInstance(), () -> {
            if (Dota.playerManagerHashMap.containsKey(player.getUniqueId())) {
                plugin.getConfig().set("PlayerInfo." + player.getName() + ".minerlevel", playerManager.getMinerLevel());
                plugin.getConfig().set("PlayerInfo." + player.getName() + ".warlevel", playerManager.getWarLevel());
                plugin.getConfig().set("PlayerInfo." + player.getName() + ".minerxp", playerManager.getMinerXp());
                plugin.getConfig().set("PlayerInfo." + player.getName() + ".warxp", playerManager.getWarXp());
                plugin.getConfig().set("PlayerInfo." + player.getName() + ".rating", playerManager.getRating());
                plugin.getConfig().set("PlayerInfo." + player.getName() + ".wins", playerManager.getWins());
                plugin.getConfig().set("PlayerInfo." + player.getName() + ".losses", playerManager.getLosses());
                plugin.saveConfig();
                Dota.playerManagerHashMap.remove(player.getUniqueId());
            }
        //}, 200L);
    }
}
