package me.chistonenn.dota;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClassesChest implements Listener {

    Dota plugin;

    public ClassesChest(Dota plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onOpenChest(PlayerInteractEvent e) {
        if (e.getHand() == null) return;
        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            if (e.getClickedBlock().getX() == 3 || e.getClickedBlock().getX() == 4) {
                if (e.getClickedBlock().getY() == 137 && e.getClickedBlock().getZ() == -13) {
                    if (!e.getClickedBlock().getType().equals(Material.CHEST)) return;
                    if (!p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("§2Ключ от кейса с классами")) return;
                    e.setCancelled(true);
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    // Дроп рандомного класса здесь
                }
            }
        }
    }
}
