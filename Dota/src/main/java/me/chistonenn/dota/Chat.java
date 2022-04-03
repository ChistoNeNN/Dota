package me.chistonenn.dota;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {
    Dota plugin;

    public Chat(Dota plugin) {
        this.plugin = plugin;
    }

    public String createHexColour(String rawColour) {
        return net.md_5.bungee.api.ChatColor.of(rawColour).toString();
    }

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("dota.admin")) {
            if (plugin.teams.get(p).equals(0)) {
                if (plugin.clas.get(p).equals(0)) {
                    event.setFormat("§8[§eЛучший§8] §f" + p.getDisplayName() + " §8[§bВоин§8]§f: " + createHexColour("#c10d80") + event.getMessage());
                } else if (plugin.clas.get(p).equals(1)) {
                    event.setFormat("§8[§eЛучший§8] §f" + p.getDisplayName() + " §8[§bШахтёр§8]§f: " + createHexColour("#c10d80") + event.getMessage());
                }
            } else if (plugin.teams.get(p).equals(1)) {
                if (plugin.clas.get(p).equals(0)) {
                    event.setFormat("§8[§eЛучший§8] §f" + p.getDisplayName() + " §8[§cВоин§8]§f: " + createHexColour("#c10d80") + event.getMessage());
                } else if (plugin.clas.get(p).equals(1)) {
                    event.setFormat("§8[§eЛучший§8] §f" + p.getDisplayName() + " §8[§cШахтёр§8]§f: " + createHexColour("#c10d80") + event.getMessage());
                }
            }

        } else if (!p.hasPermission("dota.admin")) {
            if (plugin.teams.get(p).equals(0)) {
                if (plugin.clas.get(p).equals(0)) {
                    event.setFormat("§8[§7Игрок§8] §7" + p.getDisplayName() + " §8[§bВоин§8]§7: " + event.getMessage());
                } else if (plugin.clas.get(p).equals(1)) {
                    event.setFormat("§8[§7Игрок§8] §7" + p.getDisplayName() + " §8[§bШахтёр§8]§7: " + event.getMessage());
                }
            } else if (plugin.teams.get(p).equals(1)) {
                if (plugin.clas.get(p).equals(0)) {
                    event.setFormat("§8[§7Игрок§8] §7" + p.getDisplayName() + " §8[§cВоин§8]§7: " + event.getMessage());
                } else if (plugin.clas.get(p).equals(1)) {
                    event.setFormat("§8[§7Игрок§8] §7" + p.getDisplayName() + " §8[§cШахтёр§8]§7: " + event.getMessage());
                }
            }
        }
    }
}
