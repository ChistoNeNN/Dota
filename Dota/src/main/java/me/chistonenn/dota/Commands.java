package me.chistonenn.dota;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Commands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Только игрок может использовать эту команду!");
            return true;
        }
        Player player = (Player) sender;

        if (player.hasPermission("dota.admin")) {
            if (cmd.getName().equalsIgnoreCase("startgame")) {
                if (!Dota.getInstance().gamestarted) {
                    StartGame.startGame();
                    Dota.getInstance().gamestarted = true;
                    Dota.getInstance().radianthp = 60;
                    Dota.getInstance().direhp = 60;

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (Dota.getInstance().teams.containsKey(p) && Dota.getInstance().clas.containsKey(p)) {
                            p.getInventory().clear();
                            p.setGameMode(GameMode.SURVIVAL);

                            ItemStack warsword = new ItemStack(Material.STONE_SWORD);
                            ItemMeta metawarsword = warsword.getItemMeta();
                            metawarsword.setDisplayName("§2Меч");
                            metawarsword.setUnbreakable(true);
                            warsword.setItemMeta(metawarsword);

                            ItemStack minersword = new ItemStack(Material.WOODEN_SWORD);
                            ItemMeta metaminersword = minersword.getItemMeta();
                            metaminersword.setDisplayName("§2Меч");
                            metaminersword.setUnbreakable(true);
                            minersword.setItemMeta(metaminersword);

                            ItemStack minerhelm = new ItemStack(Material.GOLDEN_HELMET);
                            ItemMeta metaminerhelm = minerhelm.getItemMeta();
                            metaminerhelm.setDisplayName("§2Каска шахтёра");
                            metaminerhelm.setUnbreakable(true);
                            minerhelm.setItemMeta(metaminerhelm);

                            ItemStack minerchest = new ItemStack(Material.LEATHER_CHESTPLATE);
                            ItemMeta metaminerchest = minerchest.getItemMeta();
                            metaminerchest.setDisplayName("§2Одежда шахтёра");
                            metaminerchest.setUnbreakable(true);
                            minerchest.setItemMeta(metaminerchest);

                            ItemStack minerlegs = new ItemStack(Material.LEATHER_LEGGINGS);
                            ItemMeta metaminerlegs = minerlegs.getItemMeta();
                            metaminerlegs.setDisplayName("§2Одежда шахтёра");
                            metaminerlegs.setUnbreakable(true);
                            minerlegs.setItemMeta(metaminerlegs);

                            ItemStack minerboots = new ItemStack(Material.LEATHER_BOOTS);
                            ItemMeta metaminerboots = minerboots.getItemMeta();
                            metaminerboots.setDisplayName("§2Одежда шахтёра");
                            metaminerboots.setUnbreakable(true);
                            minerboots.setItemMeta(metaminerboots);

                            ItemStack warhelm = new ItemStack(Material.IRON_HELMET);
                            ItemMeta metawarhelm = warhelm.getItemMeta();
                            metawarhelm.setDisplayName("§2Шлем воина");
                            metawarhelm.setUnbreakable(true);
                            warhelm.setItemMeta(metawarhelm);

                            ItemStack warchest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                            ItemMeta metawarchest = warchest.getItemMeta();
                            metawarchest.setDisplayName("§2Нагрудник воина");
                            metawarchest.setUnbreakable(true);
                            warchest.setItemMeta(metawarchest);

                            ItemStack warlegs = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                            ItemMeta metawarlegs = warlegs.getItemMeta();
                            metawarlegs.setDisplayName("§2Поножи воина");
                            metawarlegs.setUnbreakable(true);
                            warlegs.setItemMeta(metawarlegs);

                            ItemStack warboots = new ItemStack(Material.CHAINMAIL_BOOTS);
                            ItemMeta metawarboots = warboots.getItemMeta();
                            metawarboots.setDisplayName("§2Ботинки воина");
                            metawarboots.setUnbreakable(true);
                            warboots.setItemMeta(metawarboots);

                            ItemStack minerpickaxe = new ItemStack(Material.IRON_PICKAXE);
                            ItemMeta metaminerpickaxe = minerpickaxe.getItemMeta();
                            metaminerpickaxe.setDisplayName("§2Кирка");
                            metaminerpickaxe.setUnbreakable(true);
                            metaminerpickaxe.addEnchant(Enchantment.DIG_SPEED, 3, false);
                            minerpickaxe.setItemMeta(metaminerpickaxe);

                            ItemStack strength = new ItemStack(Material.RED_DYE);
                            ItemMeta metastrength = strength.getItemMeta();
                            metastrength.setDisplayName("§6Перк силы: §eПКМ");
                            strength.setItemMeta(metastrength);

                            ItemStack drowhelm = new ItemStack(Material.LEATHER_HELMET);
                            ItemMeta metadrowhelm = drowhelm.getItemMeta();
                            metadrowhelm.setDisplayName("§2Шлем лучника");
                            metadrowhelm.setUnbreakable(true);
                            drowhelm.setItemMeta(metadrowhelm);

                            ItemStack drowchest = new ItemStack(Material.LEATHER_CHESTPLATE);
                            ItemMeta metadrowchest = drowchest.getItemMeta();
                            metadrowchest.setDisplayName("§2Куртка лучника");
                            metadrowchest.setUnbreakable(true);
                            drowchest.setItemMeta(metadrowchest);

                            ItemStack drowlegs = new ItemStack(Material.LEATHER_LEGGINGS);
                            ItemMeta metadrowlegs = drowlegs.getItemMeta();
                            metadrowlegs.setDisplayName("§2Поножи лучника");
                            metadrowlegs.setUnbreakable(true);
                            drowlegs.setItemMeta(metadrowlegs);

                            ItemStack drowboots = new ItemStack(Material.LEATHER_BOOTS);
                            ItemMeta metadrowboots = drowboots.getItemMeta();
                            metadrowboots.setDisplayName("§2Ботинки лучника");
                            metadrowboots.setUnbreakable(true);
                            drowboots.setItemMeta(metadrowboots);

                            ItemStack drowbow = new ItemStack(Material.BOW);
                            ItemMeta metadrowbow = drowbow.getItemMeta();
                            metadrowbow.setDisplayName("§2Морозный лук");
                            metadrowbow.setUnbreakable(true);
                            metadrowbow.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
                            metadrowbow.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                            metadrowbow.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            drowbow.setItemMeta(metadrowbow);

                            ItemStack zeushelm = new ItemStack(Material.LEATHER_HELMET);
                            ItemMeta metazeushelm = zeushelm.getItemMeta();
                            metazeushelm.setDisplayName("§2Шлем Зевса");
                            metazeushelm.setUnbreakable(true);
                            zeushelm.setItemMeta(metazeushelm);

                            ItemStack zeusboots = new ItemStack(Material.DIAMOND_BOOTS);
                            ItemMeta metazeusboots = zeusboots.getItemMeta();
                            metazeusboots.setDisplayName("§2Ботинки Зевса");
                            metazeusboots.setUnbreakable(true);
                            zeusboots.setItemMeta(metazeusboots);

                            ItemStack zap = new ItemStack(Material.SNOWBALL, 5);
                            ItemMeta metazap = zap.getItemMeta();
                            metazap.setDisplayName("§2Зап");
                            metazap.setUnbreakable(true);
                            metazap.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                            metazap.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            zap.setItemMeta(metazap);

                            ItemStack wrath = new ItemStack(Material.BLAZE_ROD);
                            ItemMeta metawrath = wrath.getItemMeta();
                            metawrath.setDisplayName("§2Гнев Громовержца");
                            metawrath.setUnbreakable(true);
                            metawrath.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                            metawrath.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            wrath.setItemMeta(metawrath);

                            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100, 199));

                            if (Dota.getInstance().clas.get(p).equals(0)) { // Воин
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 140, 1));
                                p.getInventory().setHelmet(warhelm);
                                p.getInventory().setChestplate(warchest);
                                p.getInventory().setLeggings(warlegs);
                                p.getInventory().setBoots(warboots);
                                p.getInventory().setItemInOffHand(strength);
                                p.getInventory().addItem(warsword);
                                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(22);
                                p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 64));
                            } else if (Dota.getInstance().clas.get(p).equals(1)) { // Шахтёр
                                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 140, 0));
                                p.getInventory().setHelmet(minerhelm);
                                p.getInventory().setChestplate(minerchest);
                                p.getInventory().setLeggings(minerlegs);
                                p.getInventory().setBoots(minerboots);
                                p.getInventory().addItem(minersword);
                                p.getInventory().addItem(minerpickaxe);
                                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                                p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 64));
                            } else if (Dota.getInstance().clas.get(p).equals(2)) { // Лучник
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 0));
                                p.getInventory().setHelmet(drowhelm);
                                p.getInventory().setChestplate(drowchest);
                                p.getInventory().setLeggings(drowlegs);
                                p.getInventory().setBoots(drowboots);
                                p.getInventory().addItem(drowbow);
                                p.getInventory().addItem(new ItemStack(Material.ARROW));
                                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(18);
                                p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 64));
                            } else if (Dota.getInstance().clas.get(p).equals(3)) { // Зевс
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 0));
                                p.getInventory().setHelmet(zeushelm);
                                p.getInventory().setBoots(zeusboots);
                                p.getInventory().addItem(zap);
                                p.getInventory().addItem(wrath);
                                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(16);
                                p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 64));
                            }
                        } else {
                            p.setGameMode(GameMode.SPECTATOR);
                            p.sendMessage("§7Вы наблюдаете за игрой");
                        }
                    }
                }
            } else if (cmd.getName().equalsIgnoreCase("setstat")) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("radianthp")) {
                        Dota.getInstance().radiantupgradehp = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("radiantdmg")) {
                        Dota.getInstance().radiantupgradedmg = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("radiantspd")) {
                        Dota.getInstance().radiantupgradespd = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("radiantgold")) {
                        Dota.getInstance().radiantgold = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("radhp")) {
                        Dota.getInstance().radianthp = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("direhp")) {
                        Dota.getInstance().direupgradehp = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("diredmg")) {
                        Dota.getInstance().direupgradedmg = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("direspd")) {
                        Dota.getInstance().direupgradespd = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("diregold")) {
                        Dota.getInstance().diregold = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("dirhp")) {
                        Dota.getInstance().direhp = Integer.parseInt(args[1]);
                    } else if (args[0].equalsIgnoreCase("timer")) {
                        Dota.getInstance().gametimesec = Integer.parseInt(args[1]);
                    }
                } else if (args.length == 3) {
                    Player p = player.getServer().getPlayer(args[0]);
                    int value = Integer.parseInt(args[2]);
                    if (p != null) {
                        PlayerManager playerManager = Dota.playerManagerHashMap.get(p.getUniqueId());
                        if (args[1].equalsIgnoreCase("minerlvl")) {
                            playerManager.setMinerLevel(value);
                        } else if (args[1].equalsIgnoreCase("warlvl")) {
                            playerManager.setWarLevel(value);
                        } else if (args[1].equalsIgnoreCase("minerxp")) {
                            playerManager.setMinerXp(value);
                        } else if (args[1].equalsIgnoreCase("warxp")) {
                            playerManager.setWarXp(value);
                        } else if (args[1].equalsIgnoreCase("rating")) {
                            playerManager.setRating(value);
                        } else if (args[1].equalsIgnoreCase("wins")) {
                            playerManager.setWins(value);
                        } else if (args[1].equalsIgnoreCase("losses")) {
                            playerManager.setLosses(value);
                        }
                    }
                }
            } else if (cmd.getName().equalsIgnoreCase("giveclasschestkey")) {
                ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
                ItemMeta metakey = key.getItemMeta();
                metakey.setDisplayName("§2Ключ от кейса с классами");
                key.setItemMeta(metakey);
                player.getInventory().addItem(key);
            } else if (cmd.getName().equalsIgnoreCase("clearinventories")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.getInventory().clear();
                    p.setGameMode(GameMode.SURVIVAL);
                    p.teleport(new Location(Bukkit.getWorld("world"), 4, 136, -0.5));
                }
            } else if (cmd.getName().equalsIgnoreCase("heal")) {
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                player.setFoodLevel(20);
                player.sendMessage("§e§l(!) §eВаше здоровье было восполнено!");
            } else if (cmd.getName().equalsIgnoreCase("sethp") &&
                    args.length == 2) {
                Player targetplayer = player.getServer().getPlayer(args[0]);
                targetplayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Integer.parseInt(args[1]));
            }
        }
        if (cmd.getName().equalsIgnoreCase("team")) {
            if (!Dota.getInstance().teams.containsKey(player)) {
                player.openInventory(Dota.teamSelection);
            } else player.sendMessage("§cВы уже выбрали команду.");
        } else if (cmd.getName().equalsIgnoreCase("playerinfo")) {
            if (args.length >= 1) {
                Player p = player.getServer().getPlayer(args[0]);
                if (p != null) { // TODO: Доработать команду
                    PlayerManager playerManager = Dota.playerManagerHashMap.get(p.getUniqueId());
                    player.sendMessage("§aИнформация об игроке " + p.getName() + ":");
                    if (p.isOnline()) {
                        player.sendMessage("§aИгрок онлайн!");
                    } else {
                        player.sendMessage("§aИгрок §cоффлайн!");
                    }
                    player.sendMessage("§aРейтинг игрока: " + playerManager.getRating() + ".");
                    if (playerManager.getWins() != 0 || playerManager.getLosses() != 0) {
                        player.sendMessage("§aПобед: " + playerManager.getWins() + ". (" + (playerManager.getWins() * 100 / (playerManager.getWins() + playerManager.getLosses())) + "%)");
                    } else player.sendMessage("§aПобед: " + playerManager.getWins() + ".");
                    player.sendMessage("§aПоражений: " + playerManager.getLosses() + ".");
                    player.sendMessage("§aУровень шахтёра: " + playerManager.getMinerLevel() + ".");
                    player.sendMessage("§aУровень воина: " + playerManager.getWarLevel() + ".");
                    player.sendMessage("§aУбийств: " + p.getStatistic(Statistic.PLAYER_KILLS));
                } else {
                    player.sendMessage("§cТакого игрока не существует.");
                }
            } else {
                player.sendMessage("§cИспользование: /playerinfo <игрок>");
            }


        }
        return true;
    }
}
