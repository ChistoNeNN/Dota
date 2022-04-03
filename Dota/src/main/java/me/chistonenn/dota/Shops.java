package me.chistonenn.dota;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Shops implements Listener {

    Dota plugin;

    public Shops(Dota plugin) {
        this.plugin = plugin;
    }

    static boolean radiantT0hp = false;
    static boolean radiantT0dmg = false;
    static boolean radiantT0agi = false;
    static boolean direT0hp = false;
    static boolean direT0dmg = false;
    static boolean direT0agi = false;


    @EventHandler
    public void onCloseShop(InventoryCloseEvent e) {
        if (e.getInventory().equals(Dota.T0RadiantShop)) {
            Dota.getInstance().radiantshopopened = false;
        } else if (e.getInventory().equals(Dota.T0DireShop)) {
            Dota.getInstance().direshopopened = false;
        }
    }

    @EventHandler
    public void onOpenShop(PlayerInteractEntityEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) return;
        Player p = e.getPlayer();
        if (!Dota.getInstance().teams.containsKey(p)) return;
        if (e.getRightClicked().getCustomName() != null) {
            if (e.getRightClicked().getCustomName().equals("§2Основная казарма") && e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
                if (Dota.getInstance().teams.get(p).equals(0)) {
                    if (!Dota.getInstance().radiantshopopened) {
                        Dota.getInstance().radiantshopopened = true;
                        p.openInventory(Dota.T0RadiantShop);
                    } else p.sendMessage("§cЭтим магазином уже пользуется кто то другой.");
                } else p.sendMessage("§cПошел отсюда! Ничего тебе я не продам!");
            } else if (e.getRightClicked().getCustomName().equals("§2Основная казарма") && e.getRightClicked().getType().equals(EntityType.ZOMBIE)) {
                if (Dota.getInstance().teams.get(p).equals(1)) {
                    if (!Dota.getInstance().direshopopened) {
                        Dota.getInstance().direshopopened = true;
                        p.openInventory(Dota.T0DireShop);
                    } else p.sendMessage("§cЭтим магазином уже пользуется кто то другой.");
                } else p.sendMessage("§cПошел отсюда! Ничего тебе я не продам!");
            } else if (e.getRightClicked().getCustomName().equals("§2Дополнительная казарма") && e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
                if (Dota.getInstance().teams.get(p).equals(0)) {
                    if (!Dota.getInstance().radiantshop2opened) {
                        Dota.getInstance().radiantshop2opened = true;
                        p.openInventory(Dota.T0RadiantShop2);
                    } else p.sendMessage("§cЭтим магазином уже пользуется кто то другой.");
                } else p.sendMessage("§cПошел отсюда! Ничего тебе я не продам!");
            } else if (e.getRightClicked().getCustomName().equals("§2Дополнительная казарма") && e.getRightClicked().getType().equals(EntityType.ZOMBIE)) {
                if (Dota.getInstance().teams.get(p).equals(1)) {
                    if (!Dota.getInstance().direshop2opened) {
                        Dota.getInstance().direshop2opened = true;
                        p.openInventory(Dota.T0DireShop2);
                    } else p.sendMessage("§cЭтим магазином уже пользуется кто то другой.");
                } else p.sendMessage("§cПошел отсюда! Ничего тебе я не продам!");
            } else if (e.getRightClicked().getCustomName().equals("§2Кузнец") && e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
                if (Dota.getInstance().teams.get(p).equals(0)) {
                    createBlacksmith(e.getPlayer());
                    p.openInventory(Dota.Blacksmith);
                } else p.sendMessage("§cПошел отсюда! Ничего тебе я не продам!");
            } else if (e.getRightClicked().getCustomName().equals("§2Кузнец") && e.getRightClicked().getType().equals(EntityType.ZOMBIE)) {
                if (Dota.getInstance().teams.get(p).equals(1)) {
                    createBlacksmith(e.getPlayer());
                    p.openInventory(Dota.Blacksmith);
                } else p.sendMessage("§cПошел отсюда! Ничего тебе я не продам!");
            }
        }
    }

    public static void createT0RadiantShop() {

        ItemStack core = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta metacore = core.getItemMeta();
        metacore.setDisplayName("§2Ваше древо прокачки");
        core.setItemMeta(metacore);

        ItemStack panel = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta metapanel = panel.getItemMeta();
        metapanel.setDisplayName(" ");
        panel.setItemMeta(metapanel);

        ItemStack firsthp = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta metafirsthp = firsthp.getItemMeta();
        if (Dota.getInstance().radiantupgradehp <= 9)
            metafirsthp.setDisplayName("§2Увеличение здоровья крипов: §7" + 4 * (Dota.getInstance().radiantupgradehp + 1) + " золота [" + Dota.getInstance().radiantupgradehp + "/10]");
        if (Dota.getInstance().radiantupgradehp >= 10)
            metafirsthp.setDisplayName("§2Увеличение здоровья крипов: §cВы достигли лимита прокачки");
        firsthp.setItemMeta(metafirsthp);

        ItemStack firstdmg = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta metafirstdmg = firstdmg.getItemMeta();
        if (Dota.getInstance().radiantupgradedmg <= 4)
            metafirstdmg.setDisplayName("§2Увеличение урона крипов: §7" + 6 * (Dota.getInstance().radiantupgradedmg + 1) + " золота [" + Dota.getInstance().radiantupgradedmg + "/5]");
        if (Dota.getInstance().radiantupgradedmg >= 5)
            metafirstdmg.setDisplayName("§2Увеличение урона крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().radiantupgradehp == 0) firstdmg.setType(Material.BLACK_STAINED_GLASS_PANE);
        if (Dota.getInstance().radiantupgradehp == 0) metafirstdmg.setDisplayName("§cЗАБЛОКИРОВАНО");
        firstdmg.setItemMeta(metafirstdmg);

        ItemStack firstspd = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta metafirstspd = firstspd.getItemMeta();
        if (Dota.getInstance().radiantupgradespd <= 4)
            metafirstspd.setDisplayName("§2Увеличение скорости крипов: §7" + 5 * (Dota.getInstance().radiantupgradespd + 1) + " золота [" + Dota.getInstance().radiantupgradespd + "/5]");
        if (Dota.getInstance().radiantupgradespd >= 5)
            metafirstspd.setDisplayName("§2Увеличение скорости крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().radiantupgradedmg == 0) firstspd.setType(Material.BLACK_STAINED_GLASS_PANE);
        if (Dota.getInstance().radiantupgradedmg == 0) metafirstspd.setDisplayName("§cЗАБЛОКИРОВАНО");
        firstspd.setItemMeta(metafirstspd);

        ItemStack firsthpmob = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta metafirsthpmob = firsthpmob.getItemMeta();
        metafirsthpmob.setDisplayName("§2Живучий крип. §eРАЗБЛОКИРОВАНО");
        if (!radiantT0hp) metafirsthpmob.setDisplayName("§2Живучий крип. §eРазблокировать 120 золота");
        if (Dota.getInstance().radiantupgradehp < 10 || Dota.getInstance().radiantupgradedmg < 5
                || Dota.getInstance().radiantupgradespd < 5) {
            firsthpmob.setType(Material.BEDROCK);
            metafirsthpmob.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        firsthpmob.setItemMeta(metafirsthpmob);

        ItemStack secondhp = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta metasecondhp = secondhp.getItemMeta();
        if (radiantT0hp) metasecondhp.setDisplayName("§2Увеличение здоровья крипов: §7" + 6 * (Dota.getInstance().radiantupgradehp - 9) + " золота [" + (Dota.getInstance().radiantupgradehp - 10) + "/5]");
        if (Dota.getInstance().radiantupgradehp >= 15)
            metasecondhp.setDisplayName("§2Увеличение здоровья крипов: §cВы достигли лимита прокачки");
        if (!radiantT0hp) {
            secondhp.setType(Material.BLACK_STAINED_GLASS_PANE);
            metasecondhp.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        secondhp.setItemMeta(metasecondhp);

        ItemStack seconddmg = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta metaseconddmg = seconddmg.getItemMeta();
        if (Dota.getInstance().radiantupgradedmg <= 14)
            metaseconddmg.setDisplayName("§2Увеличение урона крипов: §7" + 8 * (Dota.getInstance().radiantupgradedmg - 4) + " золота [" + (Dota.getInstance().radiantupgradedmg - 5) + "/10]");
        if (Dota.getInstance().radiantupgradedmg >= 15)
            metaseconddmg.setDisplayName("§2Увеличение урона крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().radiantupgradehp <= 10) seconddmg.setType(Material.BLACK_STAINED_GLASS_PANE);
        if (Dota.getInstance().radiantupgradehp <= 10) metaseconddmg.setDisplayName("§cЗАБЛОКИРОВАНО");
        seconddmg.setItemMeta(metaseconddmg);

        ItemStack secondspd = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta metasecondspd = secondspd.getItemMeta();
        if (Dota.getInstance().radiantupgradespd <= 9)
            metasecondspd.setDisplayName("§2Увеличение скорости крипов: §7" + 7 * (Dota.getInstance().radiantupgradespd - 4) + " золота [" + (Dota.getInstance().radiantupgradespd - 5) + "/5]");
        if (Dota.getInstance().radiantupgradespd >= 10)
            metasecondspd.setDisplayName("§2Увеличение скорости крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().radiantupgradedmg <= 5) {
            secondspd.setType(Material.BLACK_STAINED_GLASS_PANE);
            metasecondspd.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        secondspd.setItemMeta(metasecondspd);

        ItemStack firstdmgmob = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta metafirstdmgmob = firstdmgmob.getItemMeta();
        metafirstdmgmob.setDisplayName("§2Сильный крип. §eРАЗБЛОКИРОВАНО");
        if (!radiantT0dmg) metafirstdmgmob.setDisplayName("§2Сильный крип. §eРазблокировать 200 золота");
        if (Dota.getInstance().radiantupgradehp < 15 || Dota.getInstance().radiantupgradedmg < 15 || Dota.getInstance().radiantupgradespd < 10) {
            firstdmgmob.setType(Material.BEDROCK);
            metafirstdmgmob.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        firstdmgmob.setItemMeta(metafirstdmgmob);

        ItemStack thirdhp = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta metathirdhp = thirdhp.getItemMeta();
        if (radiantT0dmg) metathirdhp.setDisplayName("§2Увеличение здоровья крипов: §7" + 9 * (Dota.getInstance().radiantupgradehp - 14) + " золота [" + (Dota.getInstance().radiantupgradehp - 15) + "/5]");
        if (Dota.getInstance().radiantupgradehp >= 20)
            metathirdhp.setDisplayName("§2Увеличение здоровья крипов: §cВы достигли лимита прокачки");
        if (!radiantT0dmg) {
            thirdhp.setType(Material.BLACK_STAINED_GLASS_PANE);
            metathirdhp.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        thirdhp.setItemMeta(metathirdhp);

        ItemStack thirddmg = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta metathirddmg = thirddmg.getItemMeta();
        if (Dota.getInstance().radiantupgradedmg <= 19)
            metathirddmg.setDisplayName("§2Увеличение урона крипов: §7" + 11 * (Dota.getInstance().radiantupgradedmg - 14) + " золота [" + (Dota.getInstance().radiantupgradedmg - 15) + "/5]");
        if (Dota.getInstance().radiantupgradedmg >= 20)
            metathirddmg.setDisplayName("§2Увеличение урона крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().radiantupgradehp <= 15) thirddmg.setType(Material.BLACK_STAINED_GLASS_PANE);
        if (Dota.getInstance().radiantupgradehp <= 15) metathirddmg.setDisplayName("§cЗАБЛОКИРОВАНО");
        thirddmg.setItemMeta(metathirddmg);

        ItemStack thirdspd = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta metathirdspd = thirdspd.getItemMeta();
        if (Dota.getInstance().radiantupgradespd <= 19)
            metathirdspd.setDisplayName("§2Увеличение скорости крипов: §7" + 10 * (Dota.getInstance().radiantupgradespd - 9) + " золота [" + (Dota.getInstance().radiantupgradespd - 10) + "/10]");
        if (Dota.getInstance().radiantupgradespd >= 20)
            metathirdspd.setDisplayName("§2Увеличение скорости крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().radiantupgradedmg <= 15) {
            thirdspd.setType(Material.BLACK_STAINED_GLASS_PANE);
            metathirdspd.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        thirdspd.setItemMeta(metathirdspd);

        ItemStack firstspdmob = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta metafirstspdmob = firstspdmob.getItemMeta();
        metafirstspdmob.setDisplayName("§2Крип-ловкач. §eРАЗБЛОКИРОВАНО");
        if (!radiantT0agi) metafirstspdmob.setDisplayName("§2Крип-ловкач. §eРазблокировать 320 золота");
        if (Dota.getInstance().radiantupgradehp < 20 || Dota.getInstance().radiantupgradespd < 20 || Dota.getInstance().radiantupgradespd < 20) {
            firstspdmob.setType(Material.BEDROCK);
            metafirstspdmob.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        firstspdmob.setItemMeta(metafirstspdmob);

        ItemStack hpdmg = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta metahpdmg = hpdmg.getItemMeta();
        metahpdmg.setDisplayName("§2Увеличение урона и здоровья: §7" + 20 * (Dota.getInstance().radiantupgradehp - 20) + " золота [" + (Dota.getInstance().radiantupgradehp - 20) + "/infinity]");
        if (!radiantT0agi) {
            hpdmg.setType(Material.BLACK_STAINED_GLASS_PANE);
            metahpdmg.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        hpdmg.setItemMeta(metahpdmg);

        Dota.T0RadiantShop = Bukkit.createInventory(null, 27, "§2§lСтартовый уровень прокачки");

        Dota.T0RadiantShop.setItem(0, core);
        Dota.T0RadiantShop.setItem(1, panel);
        Dota.T0RadiantShop.setItem(2, seconddmg);
        Dota.T0RadiantShop.setItem(3, secondspd);
        Dota.T0RadiantShop.setItem(4, firstdmgmob);
        Dota.T0RadiantShop.setItem(5, panel);
        Dota.T0RadiantShop.setItem(9, firsthp);
        Dota.T0RadiantShop.setItem(10, panel);
        Dota.T0RadiantShop.setItem(11, secondhp);
        Dota.T0RadiantShop.setItem(12, panel);
        Dota.T0RadiantShop.setItem(13, thirdhp);
        Dota.T0RadiantShop.setItem(14, panel);
        Dota.T0RadiantShop.setItem(15, hpdmg);
        Dota.T0RadiantShop.setItem(16, panel);
        Dota.T0RadiantShop.setItem(18, firstdmg);
        Dota.T0RadiantShop.setItem(19, firstspd);
        Dota.T0RadiantShop.setItem(20, firsthpmob);
        Dota.T0RadiantShop.setItem(21, panel);
        Dota.T0RadiantShop.setItem(22, thirddmg);
        Dota.T0RadiantShop.setItem(23, thirdspd);
        Dota.T0RadiantShop.setItem(24, firstspdmob);
        Dota.T0RadiantShop.setItem(25, panel);
    }

    @EventHandler
    public void onClickT0RadiantShop(InventoryClickEvent e) {
        if (!e.getInventory().equals(Dota.T0RadiantShop)) return;
        Player p = (Player) e.getWhoClicked();
        if (Dota.getInstance().teams.get(p).equals(1)) return;
        e.setCancelled(true);
        if (e.getSlot() == 9) {
            if (Dota.getInstance().radiantupgradehp <= 9) {
                Dota.getInstance().price = 4 * (Dota.getInstance().radiantupgradehp + 1);
                Dota.getInstance().rewardType = 0;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки");
            }
        } else if (e.getSlot() == 18) {
            if (Dota.getInstance().radiantupgradedmg <= 4 && Dota.getInstance().radiantupgradehp > 0) {
                    Dota.getInstance().price = 6 * (Dota.getInstance().radiantupgradedmg + 1);
                    Dota.getInstance().rewardType = 1;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 19) {
            if (Dota.getInstance().radiantupgradespd <= 4 && Dota.getInstance().radiantupgradedmg > 0) {
                Dota.getInstance().price = 5 * (Dota.getInstance().radiantupgradespd + 1);
                Dota.getInstance().rewardType = 2;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 20) {
            if (Dota.getInstance().radiantupgradehp == 10 && Dota.getInstance().radiantupgradedmg == 5
                    && Dota.getInstance().radiantupgradespd == 5 && !radiantT0hp) {
                Dota.getInstance().price = 120;
                Dota.getInstance().rewardType = 3;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 11) {
            if (Dota.getInstance().radiantupgradehp <= 14 && radiantT0hp) {
                Dota.getInstance().price = 6 * (Dota.getInstance().radiantupgradehp - 9);
                Dota.getInstance().rewardType = 4;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 2) {
            if (Dota.getInstance().radiantupgradedmg <= 14 && Dota.getInstance().radiantupgradehp > 10) {
                Dota.getInstance().price = 8 * (Dota.getInstance().radiantupgradedmg - 4);
                Dota.getInstance().rewardType = 5;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 3) {
            if (Dota.getInstance().radiantupgradespd <= 9 && Dota.getInstance().radiantupgradedmg > 5) {
                Dota.getInstance().price = 7 * (Dota.getInstance().radiantupgradespd - 4);
                Dota.getInstance().rewardType = 6;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 4) {
            if (Dota.getInstance().radiantupgradehp == 15 && Dota.getInstance().radiantupgradedmg == 15
                    && Dota.getInstance().radiantupgradespd == 10 && !radiantT0dmg) {
                Dota.getInstance().price = 200;
                Dota.getInstance().rewardType = 7;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 13) {
            if (radiantT0dmg) {
                Dota.getInstance().price = 9 * (Dota.getInstance().radiantupgradehp - 14);;
                Dota.getInstance().rewardType = 8;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 22) {
            if (Dota.getInstance().radiantupgradedmg <= 19 && Dota.getInstance().radiantupgradehp > 15) {
                Dota.getInstance().price = 11 * (Dota.getInstance().radiantupgradedmg - 14);
                Dota.getInstance().rewardType = 9;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 23) {
            if (Dota.getInstance().radiantupgradespd <= 19 && Dota.getInstance().radiantupgradedmg > 15) {
                Dota.getInstance().price = 10 * (Dota.getInstance().radiantupgradespd - 14);
                Dota.getInstance().rewardType = 10;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 24) {
            if (Dota.getInstance().radiantupgradehp == 20 && Dota.getInstance().radiantupgradedmg == 20
                    && Dota.getInstance().radiantupgradespd == 20 && !radiantT0agi) {
                Dota.getInstance().price = 320;
                Dota.getInstance().rewardType = 11;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 15) {
            if (Dota.getInstance().radiantupgradehp >= 20) {
                Dota.getInstance().price = 20 * (Dota.getInstance().radiantupgradehp - 19);
                Dota.getInstance().rewardType = 12;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки");
            }
        }

        if (Dota.getInstance().radiantgold < Dota.getInstance().price) return;
        if (Dota.getInstance().rewardType == 0) {
            if (Dota.getInstance().radiantupgradehp <= 9) {
                Dota.getInstance().radiantupgradehp++;
            }
        } else if (Dota.getInstance().rewardType == 1) {
            if (Dota.getInstance().radiantupgradedmg <= 4) {
                Dota.getInstance().radiantupgradedmg++;
            }
        } else if (Dota.getInstance().rewardType == 2) {
            if (Dota.getInstance().radiantupgradespd <= 4) {
                Dota.getInstance().radiantupgradespd++;
            }
        } else if (Dota.getInstance().rewardType == 3) {
            if (Dota.getInstance().radiantupgradehp == 10 && Dota.getInstance().radiantupgradedmg == 5
                    && Dota.getInstance().radiantupgradespd == 5 && !radiantT0hp) {
                radiantT0hp = true;
            }
        } else if (Dota.getInstance().rewardType == 4) {
            if (Dota.getInstance().radiantupgradehp <= 14) {
                Dota.getInstance().radiantupgradehp++;
            }
        } else if (Dota.getInstance().rewardType == 5) {
            if (Dota.getInstance().radiantupgradedmg <= 14) {
                Dota.getInstance().radiantupgradedmg++;
            }
        } else if (Dota.getInstance().rewardType == 6) {
            if (Dota.getInstance().radiantupgradespd <= 9) {
                Dota.getInstance().radiantupgradespd++;
            }
        } else if (Dota.getInstance().rewardType == 7) {
            if (Dota.getInstance().radiantupgradehp == 15 && Dota.getInstance().radiantupgradedmg == 15
                    && Dota.getInstance().radiantupgradespd == 10 && !radiantT0dmg) {
                radiantT0dmg = true;
            }
        } else if (Dota.getInstance().rewardType == 8) {
            if (Dota.getInstance().radiantupgradehp <= 19) {
                Dota.getInstance().radiantupgradehp++;
            }
        } else if (Dota.getInstance().rewardType == 9) {
            if (Dota.getInstance().radiantupgradedmg <= 19) {
                Dota.getInstance().radiantupgradedmg++;
            }
        } else if (Dota.getInstance().rewardType == 10) {
            if (Dota.getInstance().radiantupgradespd <= 19) {
                Dota.getInstance().radiantupgradespd++;
            }
        } else if (Dota.getInstance().rewardType == 11) {
            if (Dota.getInstance().radiantupgradehp == 20 && Dota.getInstance().radiantupgradedmg == 20
                    && Dota.getInstance().radiantupgradespd == 20 && !radiantT0agi) {
                radiantT0agi = true;
            }
        } else if (Dota.getInstance().rewardType == 12) {
            if (Dota.getInstance().radiantupgradehp >= 20 && radiantT0agi) {
                Dota.getInstance().radiantupgradehp++;
                Dota.getInstance().radiantupgradedmg++;
            }
        }
        Dota.getInstance().radiantgold = Dota.getInstance().radiantgold - Dota.getInstance().price;
        createT0RadiantShop();
        p.openInventory(Dota.T0RadiantShop);
    }

    public static void createT0DireShop() {

        ItemStack core = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta metacore = core.getItemMeta();
        metacore.setDisplayName("§2Ваше древо прокачки");
        core.setItemMeta(metacore);

        ItemStack panel = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta metapanel = panel.getItemMeta();
        metapanel.setDisplayName(" ");
        panel.setItemMeta(metapanel);

        ItemStack firsthp = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta metafirsthp = firsthp.getItemMeta();
        if (Dota.getInstance().direupgradehp <= 9)
            metafirsthp.setDisplayName("§2Увеличение здоровья крипов: §7" + 4 * (Dota.getInstance().direupgradehp + 1) + " золота [" + Dota.getInstance().direupgradehp + "/10]");
        if (Dota.getInstance().direupgradehp >= 10)
            metafirsthp.setDisplayName("§2Увеличение здоровья крипов: §cВы достигли лимита прокачки");
        firsthp.setItemMeta(metafirsthp);

        ItemStack firstdmg = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta metafirstdmg = firstdmg.getItemMeta();
        if (Dota.getInstance().direupgradedmg <= 4)
            metafirstdmg.setDisplayName("§2Увеличение урона крипов: §7" + 6 * (Dota.getInstance().direupgradedmg + 1) + " золота [" + Dota.getInstance().direupgradedmg + "/5]");
        if (Dota.getInstance().direupgradedmg >= 5)
            metafirstdmg.setDisplayName("§2Увеличение урона крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().direupgradehp == 0) firstdmg.setType(Material.BLACK_STAINED_GLASS_PANE);
        if (Dota.getInstance().direupgradehp == 0) metafirstdmg.setDisplayName("§cЗАБЛОКИРОВАНО");
        firstdmg.setItemMeta(metafirstdmg);

        ItemStack firstspd = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta metafirstspd = firstspd.getItemMeta();
        if (Dota.getInstance().direupgradespd <= 4)
            metafirstspd.setDisplayName("§2Увеличение скорости крипов: §7" + 5 * (Dota.getInstance().direupgradespd + 1) + " золота [" + Dota.getInstance().direupgradespd + "/5]");
        if (Dota.getInstance().direupgradespd >= 5)
            metafirstspd.setDisplayName("§2Увеличение скорости крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().direupgradedmg == 0) firstspd.setType(Material.BLACK_STAINED_GLASS_PANE);
        if (Dota.getInstance().direupgradedmg == 0) metafirstspd.setDisplayName("§cЗАБЛОКИРОВАНО");
        firstspd.setItemMeta(metafirstspd);

        ItemStack firsthpmob = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta metafirsthpmob = firsthpmob.getItemMeta();
        metafirsthpmob.setDisplayName("§2Живучий крип. §eРАЗБЛОКИРОВАНО");
        if (!direT0hp) metafirsthpmob.setDisplayName("§2Живучий крип. §eРазблокировать 120 золота");
        if (Dota.getInstance().direupgradehp < 10 || Dota.getInstance().direupgradedmg < 5
                || Dota.getInstance().direupgradespd < 5) {
            firsthpmob.setType(Material.BEDROCK);
            metafirsthpmob.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        firsthpmob.setItemMeta(metafirsthpmob);

        ItemStack secondhp = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta metasecondhp = secondhp.getItemMeta();
        if (direT0hp) metasecondhp.setDisplayName("§2Увеличение здоровья крипов: §7" + 6 * (Dota.getInstance().direupgradehp - 9) + " золота [" + (Dota.getInstance().direupgradehp - 10) + "/5]");
        if (Dota.getInstance().direupgradehp >= 15)
            metasecondhp.setDisplayName("§2Увеличение здоровья крипов: §cВы достигли лимита прокачки");
        if (!direT0hp) {
            secondhp.setType(Material.BLACK_STAINED_GLASS_PANE);
            metasecondhp.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        secondhp.setItemMeta(metasecondhp);

        ItemStack seconddmg = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta metaseconddmg = seconddmg.getItemMeta();
        if (Dota.getInstance().direupgradedmg <= 14)
            metaseconddmg.setDisplayName("§2Увеличение урона крипов: §7" + 8 * (Dota.getInstance().direupgradedmg - 4) + " золота [" + (Dota.getInstance().direupgradedmg - 5) + "/10]");
        if (Dota.getInstance().direupgradedmg >= 15)
            metaseconddmg.setDisplayName("§2Увеличение урона крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().direupgradehp <= 10) seconddmg.setType(Material.BLACK_STAINED_GLASS_PANE);
        if (Dota.getInstance().direupgradehp <= 10) metaseconddmg.setDisplayName("§cЗАБЛОКИРОВАНО");
        seconddmg.setItemMeta(metaseconddmg);

        ItemStack secondspd = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta metasecondspd = secondspd.getItemMeta();
        if (Dota.getInstance().direupgradespd <= 9)
            metasecondspd.setDisplayName("§2Увеличение скорости крипов: §7" + 7 * (Dota.getInstance().direupgradespd - 4) + " золота [" + (Dota.getInstance().direupgradespd - 5) + "/5]");
        if (Dota.getInstance().direupgradespd >= 10)
            metasecondspd.setDisplayName("§2Увеличение скорости крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().direupgradedmg <= 5) {
            secondspd.setType(Material.BLACK_STAINED_GLASS_PANE);
            metasecondspd.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        secondspd.setItemMeta(metasecondspd);

        ItemStack firstdmgmob = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta metafirstdmgmob = firstdmgmob.getItemMeta();
        metafirstdmgmob.setDisplayName("§2Сильный крип. §eРАЗБЛОКИРОВАНО");
        if (!direT0dmg) metafirstdmgmob.setDisplayName("§2Сильный крип. §eРазблокировать 200 золота");
        if (Dota.getInstance().direupgradehp < 15 || Dota.getInstance().direupgradedmg < 15 || Dota.getInstance().direupgradespd < 10) {
            firstdmgmob.setType(Material.BEDROCK);
            metafirstdmgmob.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        firstdmgmob.setItemMeta(metafirstdmgmob);

        ItemStack thirdhp = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta metathirdhp = thirdhp.getItemMeta();
        if (direT0dmg) metathirdhp.setDisplayName("§2Увеличение здоровья крипов: §7" + 9 * (Dota.getInstance().direupgradehp - 14) + " золота [" + (Dota.getInstance().direupgradehp - 15) + "/5]");
        if (Dota.getInstance().direupgradehp >= 20)
            metathirdhp.setDisplayName("§2Увеличение здоровья крипов: §cВы достигли лимита прокачки");
        if (!direT0dmg) {
            thirdhp.setType(Material.BLACK_STAINED_GLASS_PANE);
            metathirdhp.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        thirdhp.setItemMeta(metathirdhp);

        ItemStack thirddmg = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta metathirddmg = thirddmg.getItemMeta();
        if (Dota.getInstance().direupgradedmg <= 19)
            metathirddmg.setDisplayName("§2Увеличение урона крипов: §7" + 11 * (Dota.getInstance().direupgradedmg - 14) + " золота [" + (Dota.getInstance().direupgradedmg - 15) + "/5]");
        if (Dota.getInstance().direupgradedmg >= 20)
            metathirddmg.setDisplayName("§2Увеличение урона крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().direupgradehp <= 15) thirddmg.setType(Material.BLACK_STAINED_GLASS_PANE);
        if (Dota.getInstance().direupgradehp <= 15) metathirddmg.setDisplayName("§cЗАБЛОКИРОВАНО");
        thirddmg.setItemMeta(metathirddmg);

        ItemStack thirdspd = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta metathirdspd = thirdspd.getItemMeta();
        if (Dota.getInstance().direupgradespd <= 19)
            metathirdspd.setDisplayName("§2Увеличение скорости крипов: §7" + 10 * (Dota.getInstance().direupgradespd - 9) + " золота [" + (Dota.getInstance().direupgradespd - 10) + "/10]");
        if (Dota.getInstance().direupgradespd >= 20)
            metathirdspd.setDisplayName("§2Увеличение скорости крипов: §cВы достигли лимита прокачки");
        if (Dota.getInstance().direupgradedmg <= 15) {
            thirdspd.setType(Material.BLACK_STAINED_GLASS_PANE);
            metathirdspd.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        thirdspd.setItemMeta(metathirdspd);

        ItemStack firstspdmob = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta metafirstspdmob = firstspdmob.getItemMeta();
        metafirstspdmob.setDisplayName("§2Крип-ловкач. §eРАЗБЛОКИРОВАНО");
        if (!direT0agi) metafirstspdmob.setDisplayName("§2Крип-ловкач. §eРазблокировать 320 золота");
        if (Dota.getInstance().direupgradehp < 20 || Dota.getInstance().direupgradespd < 20 || Dota.getInstance().direupgradespd < 20) {
            firstspdmob.setType(Material.BEDROCK);
            metafirstspdmob.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        firstspdmob.setItemMeta(metafirstspdmob);

        ItemStack hpdmg = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta metahpdmg = hpdmg.getItemMeta();
        metahpdmg.setDisplayName("§2Увеличение урона и здоровья: §7" + 20 * (Dota.getInstance().direupgradehp - 19) + " золота [" + (Dota.getInstance().direupgradehp - 20) + "/infinity]");
        if (!direT0agi) {
            hpdmg.setType(Material.BLACK_STAINED_GLASS_PANE);
            metahpdmg.setDisplayName("§cЗАБЛОКИРОВАНО");
        }
        hpdmg.setItemMeta(metahpdmg);

        Dota.T0DireShop = Bukkit.createInventory(null, 27, "§2§lСтартовый уровень прокачки");

        Dota.T0DireShop.setItem(0, core);
        Dota.T0DireShop.setItem(1, panel);
        Dota.T0DireShop.setItem(2, seconddmg);
        Dota.T0DireShop.setItem(3, secondspd);
        Dota.T0DireShop.setItem(4, firstdmgmob);
        Dota.T0DireShop.setItem(5, panel);
        Dota.T0DireShop.setItem(9, firsthp);
        Dota.T0DireShop.setItem(10, panel);
        Dota.T0DireShop.setItem(11, secondhp);
        Dota.T0DireShop.setItem(12, panel);
        Dota.T0DireShop.setItem(13, thirdhp);
        Dota.T0DireShop.setItem(14, panel);
        Dota.T0DireShop.setItem(15, hpdmg);
        Dota.T0DireShop.setItem(16, panel);
        Dota.T0DireShop.setItem(18, firstdmg);
        Dota.T0DireShop.setItem(19, firstspd);
        Dota.T0DireShop.setItem(20, firsthpmob);
        Dota.T0DireShop.setItem(21, panel);
        Dota.T0DireShop.setItem(22, thirddmg);
        Dota.T0DireShop.setItem(23, thirdspd);
        Dota.T0DireShop.setItem(24, firstspdmob);
        Dota.T0DireShop.setItem(25, panel);
    }

    @EventHandler
    public void onClickT0DireShop(InventoryClickEvent e) {
        if (!e.getInventory().equals(Dota.T0DireShop)) return;
        Player p = (Player) e.getWhoClicked();
        if (Dota.getInstance().teams.get(p).equals(0)) return;
        e.setCancelled(true);
        if (e.getSlot() == 9) {
            if (Dota.getInstance().direupgradehp <= 9) {
                Dota.getInstance().price = 4 * (Dota.getInstance().direupgradehp + 1);
                Dota.getInstance().rewardType = 0;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки");
            }
        } else if (e.getSlot() == 18) {
            if (Dota.getInstance().direupgradedmg <= 4 && Dota.getInstance().direupgradehp > 0) {
                Dota.getInstance().price = 6 * (Dota.getInstance().direupgradedmg + 1);
                Dota.getInstance().rewardType = 1;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 19) {
            if (Dota.getInstance().direupgradespd <= 4 && Dota.getInstance().direupgradedmg > 0) {
                Dota.getInstance().price = 5 * (Dota.getInstance().direupgradespd + 1);
                Dota.getInstance().rewardType = 2;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 20) {
            if (Dota.getInstance().direupgradehp == 10 && Dota.getInstance().direupgradedmg == 5
                    && Dota.getInstance().direupgradespd == 5 && !direT0hp) {
                Dota.getInstance().price = 120;
                Dota.getInstance().rewardType = 3;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 11) {
            if (Dota.getInstance().direupgradehp <= 14 && direT0hp) {
                Dota.getInstance().price = 6 * (Dota.getInstance().direupgradehp - 9);
                Dota.getInstance().rewardType = 4;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 2) {
            if (Dota.getInstance().direupgradedmg <= 14 && Dota.getInstance().direupgradehp > 10) {
                Dota.getInstance().price = 8 * (Dota.getInstance().direupgradedmg - 4);
                Dota.getInstance().rewardType = 5;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 3) {
            if (Dota.getInstance().direupgradespd <= 9 && Dota.getInstance().direupgradedmg > 5) {
                Dota.getInstance().price = 7 * (Dota.getInstance().direupgradespd - 4);
                Dota.getInstance().rewardType = 6;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 4) {
            if (Dota.getInstance().direupgradehp == 15 && Dota.getInstance().direupgradedmg == 15
                    && Dota.getInstance().direupgradespd == 10 && !direT0dmg) {
                Dota.getInstance().price = 200;
                Dota.getInstance().rewardType = 7;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 13) {
            if (direT0dmg) {
                Dota.getInstance().price = 9 * (Dota.getInstance().direupgradehp - 14);;
                Dota.getInstance().rewardType = 8;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 22) {
            if (Dota.getInstance().direupgradedmg <= 19 && Dota.getInstance().direupgradehp > 15) {
                Dota.getInstance().price = 11 * (Dota.getInstance().direupgradedmg - 14);
                Dota.getInstance().rewardType = 9;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 23) {
            if (Dota.getInstance().direupgradespd <= 19 && Dota.getInstance().direupgradedmg > 15) {
                Dota.getInstance().price = 10 * (Dota.getInstance().direupgradespd - 14);
                Dota.getInstance().rewardType = 10;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 24) {
            if (Dota.getInstance().direupgradehp == 20 && Dota.getInstance().direupgradedmg == 20
                    && Dota.getInstance().direupgradespd == 20 && !direT0agi) {
                Dota.getInstance().price = 320;
                Dota.getInstance().rewardType = 11;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки или условия не выполнены");
            }
        } else if (e.getSlot() == 15) {
            if (Dota.getInstance().direupgradehp >= 20) {
                Dota.getInstance().price = 20 * (Dota.getInstance().direupgradehp - 19);
                Dota.getInstance().rewardType = 12;
            } else {
                Dota.getInstance().price = 0;
                Dota.getInstance().rewardType = 100;
                p.sendMessage("§cВы достигли лимита прокачки");
            }
        }

        if (Dota.getInstance().diregold < Dota.getInstance().price) return;
        if (Dota.getInstance().rewardType == 0) {
            if (Dota.getInstance().direupgradehp <= 9) {
                Dota.getInstance().direupgradehp++;
            }
        } else if (Dota.getInstance().rewardType == 1) {
            if (Dota.getInstance().direupgradedmg <= 4) {
                Dota.getInstance().direupgradedmg++;
            }
        } else if (Dota.getInstance().rewardType == 2) {
            if (Dota.getInstance().direupgradespd <= 4) {
                Dota.getInstance().direupgradespd++;
            }
        } else if (Dota.getInstance().rewardType == 3) {
            if (Dota.getInstance().direupgradehp == 10 && Dota.getInstance().direupgradedmg == 5
                    && Dota.getInstance().direupgradespd == 5 && !direT0hp) {
                direT0hp = true;
            }
        } else if (Dota.getInstance().rewardType == 4) {
            if (Dota.getInstance().direupgradehp <= 14) {
                Dota.getInstance().direupgradehp++;
            }
        } else if (Dota.getInstance().rewardType == 5) {
            if (Dota.getInstance().direupgradedmg <= 14) {
                Dota.getInstance().direupgradedmg++;
            }
        } else if (Dota.getInstance().rewardType == 6) {
            if (Dota.getInstance().direupgradespd <= 9) {
                Dota.getInstance().direupgradespd++;
            }
        } else if (Dota.getInstance().rewardType == 7) {
            if (Dota.getInstance().direupgradehp == 15 && Dota.getInstance().direupgradedmg == 15
                    && Dota.getInstance().direupgradespd == 10 && !direT0dmg) {
                direT0dmg = true;
            }
        } else if (Dota.getInstance().rewardType == 8) {
            if (Dota.getInstance().direupgradehp <= 19) {
                Dota.getInstance().direupgradehp++;
            }
        } else if (Dota.getInstance().rewardType == 9) {
            if (Dota.getInstance().direupgradedmg <= 19) {
                Dota.getInstance().direupgradedmg++;
            }
        } else if (Dota.getInstance().rewardType == 10) {
            if (Dota.getInstance().direupgradespd <= 19) {
                Dota.getInstance().direupgradespd++;
            }
        } else if (Dota.getInstance().rewardType == 11) {
            if (Dota.getInstance().direupgradehp == 20 && Dota.getInstance().direupgradedmg == 20
                    && Dota.getInstance().direupgradespd == 20 && !direT0agi) {
                direT0agi = true;
            }
        } else if (Dota.getInstance().rewardType == 12) {
            if (Dota.getInstance().direupgradehp >= 20 && direT0agi) {
                Dota.getInstance().direupgradehp++;
                Dota.getInstance().direupgradedmg++;
            }
        }
        Dota.getInstance().diregold = Dota.getInstance().diregold - Dota.getInstance().price;
        createT0DireShop();
        p.openInventory(Dota.T0DireShop);
    }


    public static void createBlacksmith(Player p) {

        Dota.Blacksmith = Bukkit.createInventory(p, 27, "§2§lКузнец");

        if (Dota.getInstance().clas.get(p).equals(0)) {
            if (p.getEquipment() == null || p.getEquipment().getHelmet() == null || p.getEquipment().getHelmet().getItemMeta() == null
                    || p.getEquipment().getChestplate() == null || p.getEquipment().getChestplate().getItemMeta() == null
                    || p.getEquipment().getLeggings() == null || p.getEquipment().getLeggings().getItemMeta() == null
                    || p.getEquipment().getBoots() == null || p.getEquipment().getBoots().getItemMeta() == null) {
                p.closeInventory();
                p.sendMessage("§cНаденьте броню");
                return;
            }

            int swordslot = 100;

            for (int i = 0; i < 36; i++) {
                if (p.getInventory().getItem(i).getType().equals(Material.STONE_SWORD)) {
                    swordslot = i;
                    break;
                }
            }

            ItemStack helmet = new ItemStack(Material.IRON_HELMET);
            ItemMeta metahelmet = helmet.getItemMeta();
            if (p.getEquipment().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                metahelmet.setDisplayName("§2Улучшить шлем: " + (p.getEquipment().getHelmet().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * 50 + 50) + " золота");
            if (!p.getEquipment().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                metahelmet.setDisplayName("§2Улучшить шлем: 50 золота");
            if (p.getEquipment().getHelmet().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 3) {
                metahelmet.setDisplayName("§2Улучшить шлем: §cВы достигли лимита прокачки");
            }
            helmet.setItemMeta(metahelmet);

            ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            ItemMeta metachest = chest.getItemMeta();
            if (p.getEquipment().getChestplate().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                metachest.setDisplayName("§2Улучшить нагрудник: " + (p.getEquipment().getChestplate().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * 75 + 75) + " золота");
            if (!p.getEquipment().getChestplate().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                metachest.setDisplayName("§2Улучшить нагрудник: 75 золота");
            if (p.getEquipment().getChestplate().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 3) {
                metachest.setDisplayName("§2Улучшить нагрудник: §cВы достигли лимита прокачки");
            }
            chest.setItemMeta(metachest);

            ItemStack legs = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            ItemMeta metalegs = legs.getItemMeta();
            if (p.getEquipment().getLeggings().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                metalegs.setDisplayName("§2Улучшить поножи: " + (p.getEquipment().getLeggings().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * 60 + 60) + " золота");
            if (!p.getEquipment().getLeggings().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                metalegs.setDisplayName("§2Улучшить поножи: 60 золота");
            if (p.getEquipment().getLeggings().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 3) {
                metalegs.setDisplayName("§2Улучшить поножи: §cВы достигли лимита прокачки");
            }
            legs.setItemMeta(metalegs);

            ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
            ItemMeta metaboots = boots.getItemMeta();
            if (p.getEquipment().getBoots().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                metaboots.setDisplayName("§2Улучшить ботинки: " + (p.getEquipment().getBoots().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) * 50 + 50) + " золота");
            if (!p.getEquipment().getBoots().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL))
                metaboots.setDisplayName("§2Улучшить ботинки: 50 золота");
            if (p.getEquipment().getBoots().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 3) {
                metaboots.setDisplayName("§2Улучшить ботинки: §cВы достигли лимита прокачки");
            }
            boots.setItemMeta(metaboots);

            ItemStack sword = new ItemStack(Material.STONE_SWORD);
            ItemMeta metasword = sword.getItemMeta();
            if (swordslot == 100) {
                p.sendMessage("§cУ вас нету меча");
                return;
            }

            if (p.getInventory().getItem(swordslot).getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL))
                metasword.setDisplayName("§2Улучшить меч: " + (p.getInventory().getItem(swordslot).getItemMeta().getEnchantLevel(Enchantment.DAMAGE_ALL) * 75 + 75) + " золота");
            if (!p.getInventory().getItem(swordslot).getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL))
                metasword.setDisplayName("§2Улучшить меч: 75 золота");
            if (p.getInventory().getItem(swordslot).getItemMeta().getEnchantLevel(Enchantment.DAMAGE_ALL) > 4) {
                metasword.setDisplayName("§2Улучшить меч: §cВы достигли лимита прокачки");
            }
            sword.setItemMeta(metasword);

            Dota.Blacksmith.setItem(0, helmet);
            Dota.Blacksmith.setItem(1, chest);
            Dota.Blacksmith.setItem(2, legs);
            Dota.Blacksmith.setItem(3, boots);
            Dota.Blacksmith.setItem(4, sword);
        } else if (Dota.getInstance().clas.get(p).equals(1)) {
            int pickaxeslot = 100;

            for (int i = 0; i < 36; i++) {
                if (p.getInventory().getItem(i) != null) {
                    if (p.getInventory().getItem(i).getType().equals(Material.IRON_PICKAXE)) {
                        pickaxeslot = i;
                        break;
                    }
                    if (p.getInventory().getItem(i).getType().equals(Material.DIAMOND_PICKAXE)) {
                        pickaxeslot = 99;
                        break;
                    }
                }
            }

            ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta metapickaxe = pickaxe.getItemMeta();
            if (p.getInventory().getItem(pickaxeslot) != null) {
                if (p.getInventory().getItem(pickaxeslot).getType().equals(Material.IRON_PICKAXE))
                    metapickaxe.setDisplayName("§2Улучшить кирку: 500 золота");
                if (pickaxeslot == 99)
                    metapickaxe.setDisplayName("§2Улучшить кирку: §cВы достигли лимита прокачки");
                if (pickaxeslot == 100)
                    metapickaxe.setDisplayName("§2Улучшить кирку: §cУ вас нету кирки");
            }
            metapickaxe.addEnchant(Enchantment.DIG_SPEED, 4, false);
            pickaxe.setItemMeta(metapickaxe);

            Dota.Blacksmith.setItem(0, pickaxe);
        }
    }

    @EventHandler
    public void onClickBlacksmith(InventoryClickEvent e) {
        if (!e.getInventory().equals(Dota.Blacksmith)) return;
        Player p = (Player) e.getWhoClicked();
        e.setCancelled(true);

        int pickaxeslot = 100;

        int swordslot = 100;

        if (Dota.getInstance().clas.get(p).equals(0)) {
            for (int i = 0; i < 36; i++) {
                if (p.getInventory().getItem(i) != null) {
                    if (p.getInventory().getItem(i).getType().equals(Material.STONE_SWORD)) {
                        swordslot = i;
                        break;
                    }
                }
            }
            if (swordslot == 100) {
                p.sendMessage("§cУ вас нету меча");
                return;
            }

            if (e.getSlot() == 0) {
                if (!p.getEquipment().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    Dota.getInstance().price = 50;
                    Dota.getInstance().rewardType = 0;
                } else if (p.getEquipment().getHelmet().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) <= 3) {
                    Dota.getInstance().price = 50 * p.getEquipment().getHelmet().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 50;
                    Dota.getInstance().rewardType = 0;
                } else {
                    Dota.getInstance().price = 0;
                    Dota.getInstance().rewardType = 100;
                    p.sendMessage("§cВы достигли лимита прокачки");
                }
            } else if (e.getSlot() == 1) {
                if (!p.getEquipment().getChestplate().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    Dota.getInstance().price = 75;
                    Dota.getInstance().rewardType = 1;
                } else if (p.getEquipment().getChestplate().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) <= 3) {
                    Dota.getInstance().price = 75 * p.getEquipment().getChestplate().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 75;
                    Dota.getInstance().rewardType = 1;
                } else {
                    Dota.getInstance().price = 0;
                    Dota.getInstance().rewardType = 100;
                    p.sendMessage("§cВы достигли лимита прокачки");
                }
            } else if (e.getSlot() == 2) {
                if (!p.getEquipment().getLeggings().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    Dota.getInstance().price = 60;
                    Dota.getInstance().rewardType = 2;
                } else if (p.getEquipment().getLeggings().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) <= 3) {
                    Dota.getInstance().price = 60 * p.getEquipment().getLeggings().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 60;
                    Dota.getInstance().rewardType = 2;
                } else {
                    Dota.getInstance().price = 0;
                    Dota.getInstance().rewardType = 100;
                    p.sendMessage("§cВы достигли лимита прокачки");
                }
            } else if (e.getSlot() == 3) {
                if (!p.getEquipment().getBoots().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    Dota.getInstance().price = 50;
                    Dota.getInstance().rewardType = 3;
                } else if (p.getEquipment().getBoots().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) <= 3) {
                    Dota.getInstance().price = 50 * p.getEquipment().getBoots().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 50;
                    Dota.getInstance().rewardType = 3;
                } else {
                    Dota.getInstance().price = 0;
                    Dota.getInstance().rewardType = 100;
                    p.sendMessage("§cВы достигли лимита прокачки");
                }
            } else if (e.getSlot() == 4) {
                if (!p.getInventory().getItem(swordslot).getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) {
                    Dota.getInstance().price = 75;
                    Dota.getInstance().rewardType = 4;
                } else if (p.getInventory().getItem(swordslot).getItemMeta().getEnchantLevel(Enchantment.DAMAGE_ALL) <= 4) {
                    Dota.getInstance().price = 75 * p.getInventory().getItem(swordslot).getItemMeta().getEnchantLevel(Enchantment.DAMAGE_ALL) + 75;
                    Dota.getInstance().rewardType = 4;
                } else {
                    Dota.getInstance().price = 0;
                    Dota.getInstance().rewardType = 100;
                    p.sendMessage("§cВы достигли лимита прокачки");
                }
            }
        } else if (Dota.getInstance().clas.get(p).equals(1)) {
            for (int i = 0; i < 36; i++) {
                if (p.getInventory().getItem(i) != null) {
                    if (p.getInventory().getItem(i).getType().equals(Material.IRON_PICKAXE)) {
                        pickaxeslot = i;
                        break;
                    }
                    if (p.getInventory().getItem(i).getType().equals(Material.DIAMOND_PICKAXE)) {
                        pickaxeslot = 99;
                        break;
                    }
                }
            }
            if (e.getSlot() == 0) {
                if (pickaxeslot == 100) {
                    p.sendMessage("§cУ вас нету кирки");
                    return;
                }
                if (pickaxeslot == 99) {
                    p.sendMessage("§cВы достигли лимита прокачки");
                    return;
                } if (p.getInventory().getItem(pickaxeslot).getType().equals(Material.IRON_PICKAXE)) {
                    Dota.getInstance().price = 500;
                    Dota.getInstance().rewardType = 5;
                }
            }
        }

        if (Dota.getInstance().rewardType == 0) {
            if (Dota.getInstance().teams.get(p).equals(0)) {
                if (Dota.getInstance().radiantgold < Dota.getInstance().price) return;
                Dota.getInstance().radiantgold = Dota.getInstance().radiantgold - Dota.getInstance().price;
            } else if (Dota.getInstance().teams.get(p).equals(1)) {
                if (Dota.getInstance().diregold < Dota.getInstance().price) return;
                Dota.getInstance().diregold = Dota.getInstance().diregold - Dota.getInstance().price;
            }
            if (!p.getEquipment().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                p.getEquipment().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            if (p.getEquipment().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) <= 3) {
                p.getEquipment().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, (p.getEquipment().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1));
            }
        } else if (Dota.getInstance().rewardType == 1) {
            if (Dota.getInstance().teams.get(p).equals(0)) {
                if (Dota.getInstance().radiantgold < Dota.getInstance().price) return;
                Dota.getInstance().radiantgold = Dota.getInstance().radiantgold - Dota.getInstance().price;
            } else if (Dota.getInstance().teams.get(p).equals(1)) {
                if (Dota.getInstance().diregold < Dota.getInstance().price) return;
                Dota.getInstance().diregold = Dota.getInstance().diregold - Dota.getInstance().price;
            }
            if (!p.getEquipment().getChestplate().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                p.getEquipment().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            if (p.getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) <= 3) {
                p.getEquipment().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, (p.getEquipment().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1));
            }
        } else if (Dota.getInstance().rewardType == 2) {
            if (Dota.getInstance().teams.get(p).equals(0)) {
                if (Dota.getInstance().radiantgold < Dota.getInstance().price) return;
                Dota.getInstance().radiantgold = Dota.getInstance().radiantgold - Dota.getInstance().price;
            } else if (Dota.getInstance().teams.get(p).equals(1)) {
                if (Dota.getInstance().diregold < Dota.getInstance().price) return;
                Dota.getInstance().diregold = Dota.getInstance().diregold - Dota.getInstance().price;
            }
            if (!p.getEquipment().getLeggings().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                p.getEquipment().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            if (p.getEquipment().getLeggings().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) <= 3) {
                p.getEquipment().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, (p.getEquipment().getLeggings().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1));
            }
        } else if (Dota.getInstance().rewardType == 3) {
            if (Dota.getInstance().teams.get(p).equals(0)) {
                if (Dota.getInstance().radiantgold < Dota.getInstance().price) return;
                Dota.getInstance().radiantgold = Dota.getInstance().radiantgold - Dota.getInstance().price;
            } else if (Dota.getInstance().teams.get(p).equals(1)) {
                if (Dota.getInstance().diregold < Dota.getInstance().price) return;
                Dota.getInstance().diregold = Dota.getInstance().diregold - Dota.getInstance().price;
            }
            if (!p.getEquipment().getBoots().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                p.getEquipment().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            if (p.getEquipment().getBoots().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) <= 3) {
                p.getEquipment().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, (p.getEquipment().getBoots().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1));
            }
        } else if (Dota.getInstance().rewardType == 4) {
            if (Dota.getInstance().teams.get(p).equals(0)) {
                if (Dota.getInstance().radiantgold < Dota.getInstance().price) return;
                Dota.getInstance().radiantgold = Dota.getInstance().radiantgold - Dota.getInstance().price;
            } else if (Dota.getInstance().teams.get(p).equals(1)) {
                if (Dota.getInstance().diregold < Dota.getInstance().price) return;
                Dota.getInstance().diregold = Dota.getInstance().diregold - Dota.getInstance().price;
            }
            if (!p.getInventory().getItem(swordslot).getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) {
                p.getInventory().getItem(swordslot).addEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
            if (p.getInventory().getItem(swordslot).getEnchantmentLevel(Enchantment.DAMAGE_ALL) <= 4) {
                p.getInventory().getItem(swordslot).addEnchantment(Enchantment.DAMAGE_ALL, (p.getInventory().getItem(swordslot).getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1));
            }
        } else if (Dota.getInstance().rewardType == 5) {
            if (Dota.getInstance().teams.get(p).equals(0)) {
                if (Dota.getInstance().radiantgold < Dota.getInstance().price) return;
                Dota.getInstance().radiantgold = Dota.getInstance().radiantgold - Dota.getInstance().price;
            } else if (Dota.getInstance().teams.get(p).equals(1)) {
                if (Dota.getInstance().diregold < Dota.getInstance().price) return;
                Dota.getInstance().diregold = Dota.getInstance().diregold - Dota.getInstance().price;
            }
            if (p.getInventory().getItem(pickaxeslot) != null && p.getInventory().getItem(pickaxeslot).getType().equals(Material.IRON_PICKAXE)) {
                p.getInventory().getItem(pickaxeslot).setAmount(0);

                ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta metapickaxe = pickaxe.getItemMeta();
                metapickaxe.addEnchant(Enchantment.DIG_SPEED, 4, false);
                metapickaxe.setDisplayName("§2Укреплённая кирка");
                pickaxe.setItemMeta(metapickaxe);

                p.getInventory().addItem(pickaxe);
            }
        }
        createBlacksmith(p);
        p.openInventory(Dota.Blacksmith);
    }
}
