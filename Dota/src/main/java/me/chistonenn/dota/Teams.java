package me.chistonenn.dota;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Teams implements Listener {

    Dota plugin;

    public Teams(Dota plugin) {
        this.plugin = plugin;
    }

    public static void createCommandSelection() {

        ItemStack radiant = new ItemStack(Material.LIGHT_BLUE_CONCRETE);
        ItemMeta metaradiant = radiant.getItemMeta();
        metaradiant.setDisplayName("§bВыбрать силы света");
        radiant.setItemMeta(metaradiant);

        ItemStack dire = new ItemStack(Material.RED_CONCRETE);
        ItemMeta metadire = dire.getItemMeta();
        metadire.setDisplayName("§cВыбрать силы тьмы");
        dire.setItemMeta(metadire);

        Dota.teamSelection = Bukkit.createInventory(null, 9, "§2§lВыберите команду");

        Dota.teamSelection.setItem(0, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.teamSelection.setItem(1, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.teamSelection.setItem(2, radiant);
        Dota.teamSelection.setItem(3, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.teamSelection.setItem(4, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.teamSelection.setItem(5, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.teamSelection.setItem(6, dire);
        Dota.teamSelection.setItem(7, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.teamSelection.setItem(8, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    }

    @EventHandler
    public void onClickCommand(InventoryClickEvent e) {
        if (!e.getInventory().equals(Dota.teamSelection)) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null) return;

        Player p = (Player) e.getWhoClicked();

        if (e.getSlot() == 2) {
            plugin.teams.put(p, 0);
            p.setDisplayName("§b" + p.getName());
            p.sendMessage("§bВы сражаетесь за силы света!");
            p.closeInventory();
            p.openInventory(Dota.classSelection);
        } else if (e.getSlot() == 6) {
            plugin.teams.put(p, 1);
            p.setDisplayName("§c" + p.getName());
            p.sendMessage("§cВы сражаетесь за силы тьмы!");
            p.closeInventory();
            p.openInventory(Dota.classSelection);
        }
    }

    public static void createClassSelection() {

        ItemStack warrior = new ItemStack(Material.IRON_SWORD);
        ItemMeta metawarrior = warrior.getItemMeta();
        metawarrior.setDisplayName("§bВыбрать класс \"Воин\"");
        warrior.setItemMeta(metawarrior);

        ItemStack drow = new ItemStack(Material.BOW);
        ItemMeta metadrow = drow.getItemMeta();
        metadrow.setDisplayName("§bВыбрать класс \"Морозный лучник\"");
        drow.setItemMeta(metadrow);

        ItemStack miner = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta metaminer = miner.getItemMeta();
        metaminer.setDisplayName("§bВыбрать класс \"Шахтер\"");
        miner.setItemMeta(metaminer);

        ItemStack zeus = new ItemStack(Material.BLAZE_ROD);
        ItemMeta metazeus = zeus.getItemMeta();
        metazeus.setDisplayName("§bВыбрать класс \"Зевс\"");
        zeus.setItemMeta(metazeus);

        Dota.classSelection = Bukkit.createInventory(null, 9, "§2§lВыберите класс");

        Dota.classSelection.setItem(0, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.classSelection.setItem(1, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.classSelection.setItem(2, warrior);
        Dota.classSelection.setItem(3, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.classSelection.setItem(4, drow);
        Dota.classSelection.setItem(5, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.classSelection.setItem(6, miner);
        Dota.classSelection.setItem(7, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        Dota.classSelection.setItem(8, zeus);
    }

    @EventHandler
    public void onClickClass(InventoryClickEvent e) {
        if (!e.getInventory().equals(Dota.classSelection)) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null) return;

        Player p = (Player) e.getWhoClicked();

        if (!plugin.clas.containsKey(p)) {
            if (plugin.teams.get(p).equals(0)) {
                if (e.getSlot() == 2) {
                    plugin.clas.put(p, 0);
                    p.sendMessage("§2Ваша роль Воин!");
                    p.closeInventory();
                } else if (e.getSlot() == 4) {
                    plugin.clas.put(p, 2);
                    p.sendMessage("§2Ваша роль Морозный лучник!");
                    p.closeInventory();
                } else if (e.getSlot() == 6) {
                    if (plugin.radiantminers < 2) {
                        if (!plugin.clas.get(p).equals(1)) {
                            plugin.clas.put(p, 1);
                            plugin.radiantminers++;
                        }
                        p.sendMessage("§2Ваша роль Шахтер!");
                        p.closeInventory();
                    } else {
                        p.sendMessage("§cПревышен лимит шахтёров! [2/2]");
                    }
                } else if (e.getSlot() == 8) {
                    if (plugin.radiantzeus == 0) {
                        if (!plugin.clas.get(p).equals(3)) {
                            plugin.clas.put(p, 3);
                            plugin.radiantzeus++;
                        }
                        p.sendMessage("§2Ваша роль Зевс!");
                        p.closeInventory();
                    } else {
                        p.sendMessage("§cЭта роль уже занята");
                    }
                }
            } else {
                if (e.getSlot() == 2) {
                    plugin.clas.put(p, 0);
                    p.sendMessage("§2Ваша роль Воин!");
                    p.closeInventory();
                } else if (e.getSlot() == 4) {
                    plugin.clas.put(p, 2);
                    p.sendMessage("§2Ваша роль Морозный лучник!");
                    p.closeInventory();
                } else if (e.getSlot() == 6) {
                    if (plugin.direminers < 2) {
                        if (!plugin.clas.get(p).equals(1)) {
                            plugin.clas.put(p, 1);
                            plugin.direminers++;
                        }
                        p.sendMessage("§2Ваша роль Шахтер!");
                        p.closeInventory();
                    } else {
                        p.sendMessage("§cПревышен лимит шахтёров! [2/2]");
                    }
                } else if (e.getSlot() == 8) {
                    if (plugin.direzeus == 0) {
                        plugin.clas.put(p, 3);
                        p.sendMessage("§2Ваша роль Зевс!");
                        p.closeInventory();
                    } else {
                        p.sendMessage("§cЭта роль уже занята");
                    }
                }
            }


        } else if (plugin.clas.containsKey(p)) {
            if (plugin.teams.get(p).equals(0)) {
                if (e.getSlot() == 2) {
                    if (plugin.clas.get(p).equals(1)) {
                        plugin.radiantminers--;
                    } else if (plugin.clas.get(p).equals(3)) {
                        plugin.radiantzeus--;
                    }
                    plugin.clas.put(p, 0);
                    p.sendMessage("§2Ваша роль Воин!");
                    p.closeInventory();
                } else if (e.getSlot() == 4) {
                    if (plugin.clas.get(p).equals(1)) {
                        plugin.radiantminers--;
                    } else if (plugin.clas.get(p).equals(3)) {
                        plugin.radiantzeus--;
                    }
                    plugin.clas.put(p, 2);
                    p.sendMessage("§2Ваша роль Морозный лучник!");
                    p.closeInventory();
                } else if (e.getSlot() == 6) {
                    if (plugin.clas.get(p).equals(3)) {
                        plugin.radiantzeus--;
                    }
                    if (plugin.radiantminers < 2) {
                        if (!plugin.clas.get(p).equals(1)) {
                            plugin.clas.put(p, 1);
                            plugin.radiantminers++;
                        }
                        p.sendMessage("§2Ваша роль Шахтер!");
                        p.closeInventory();
                    } else {
                        p.sendMessage("§cПревышен лимит шахтёров! [2/2]");
                    }
                } else if (e.getSlot() == 8) {
                    if (plugin.clas.get(p).equals(1)) {
                        plugin.radiantminers--;
                    }
                    if (plugin.radiantzeus == 0) {
                        if (!plugin.clas.get(p).equals(3)) {
                            plugin.clas.put(p, 3);
                            plugin.radiantzeus++;
                        }
                        p.sendMessage("§2Ваша роль Зевс!");
                        p.closeInventory();
                    } else {
                        p.sendMessage("§cЭта роль уже занята");
                    }
                }
            } else {
                if (e.getSlot() == 2) {
                    if (plugin.clas.get(p).equals(1)) {
                        plugin.direminers--;
                    } else if (plugin.clas.get(p).equals(3)) {
                        plugin.direzeus--;
                    }
                    plugin.clas.put(p, 0);
                    p.sendMessage("§2Ваша роль Воин!");
                    p.closeInventory();
                } else if (e.getSlot() == 4) {
                    if (plugin.clas.get(p).equals(1)) {
                        plugin.direminers--;
                    } else if (plugin.clas.get(p).equals(3)) {
                        plugin.direzeus--;
                    }
                    plugin.clas.put(p, 2);
                    p.sendMessage("§2Ваша роль Морозный лучник!");
                    p.closeInventory();
                } else if (e.getSlot() == 6) {
                    if (plugin.clas.get(p).equals(3)) {
                        plugin.direzeus--;
                    }
                    if (plugin.direminers < 2) {
                        if (!plugin.clas.get(p).equals(1)) {
                            plugin.clas.put(p, 1);
                            plugin.direminers++;
                        }
                        p.sendMessage("§2Ваша роль Шахтер!");
                        p.closeInventory();
                    } else {
                        p.sendMessage("§cПревышен лимит шахтёров! [2/2]");
                    }
                } else if (e.getSlot() == 8) {
                    if (plugin.direzeus == 0) {
                        if (plugin.clas.get(p).equals(1)) {
                            plugin.direminers--;
                        }
                        if (!plugin.clas.get(p).equals(3)) {
                            plugin.clas.put(p, 3);
                            plugin.direzeus++;
                        }
                        p.sendMessage("§2Ваша роль Зевс!");
                        p.closeInventory();
                    } else {
                        p.sendMessage("§cЭта роль уже занята");
                    }
                }
            }
        }
    }
}
