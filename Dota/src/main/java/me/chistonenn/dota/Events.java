package me.chistonenn.dota;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class Events implements Listener {

    Dota plugin;

    public Events(Dota plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        e.setDroppedExp(0);
        e.getDrops().clear();
        if (plugin.radiantcreepsliving.containsKey(entity)) {
            (new BukkitRunnable() {
                public void run() {
                    plugin.radiantcreepsliving.remove(entity);
                }
            }).runTaskLater(Dota.getInstance(), 1);
        }
        if (plugin.direcreepsliving.containsKey(entity)) {
            (new BukkitRunnable() {
                public void run() {
                    plugin.direcreepsliving.remove(entity);
                }
            }).runTaskLater(Dota.getInstance(), 1);
        }
    }

    @EventHandler
    public void onPVP(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player terp = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if (plugin.teams.containsKey(terp) && plugin.teams.containsKey(damager) && plugin.gamestarted) {
                if (Objects.equals(plugin.teams.get(terp), plugin.teams.get(damager))) {
                    e.setCancelled(true);
                    damager.sendMessage("Это ваш друг!");
                } else {
                    damager.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            new net.md_5.bungee.api.chat.TextComponent("§c-%dmg%❤".replace("%dmg%", String.format("%.1f", e.getDamage() / 2))));
                }
            } else {
                e.setCancelled(true);
                damager.sendMessage("Не надо драться ;c");
            }
        } else if (e.getEntity().getCustomName() != null) {
            if (e.getDamager() instanceof Player && e.getEntity().getCustomName().equalsIgnoreCase("§2Основная казарма")
                    || e.getEntity().getCustomName().equalsIgnoreCase("§2Кузнец") || e.getEntity().getCustomName().equalsIgnoreCase("§2Шахтёр")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        e.setDeathMessage("");
        int amount = 0;
        int reward;

        for (int i = 0; i < 36; i++) {
            if (p.getInventory().getItem(i) != null) {
                if (p.getInventory().getItem(i).getType().equals(Material.GOLD_NUGGET)) {
                    amount = amount + p.getInventory().getItem(i).getAmount();
                    p.getInventory().getItem(i).setAmount(0);
                }
            }
        }
        p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, amount * 2 / 10));

        if (e.getEntity().getKiller() == null) return;
        Player kp = e.getEntity().getKiller();
        reward = amount * 8 / 10 + plugin.diregold / 15;
        kp.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, reward));
        if (plugin.teams.get(kp).equals(0)) {
            Bukkit.broadcastMessage("§bИгрок " + kp.getName() + " получил награду в " + reward + " за убийство " + p.getName());
            plugin.diregold = plugin.diregold - plugin.diregold / 15;
        } else if (plugin.teams.get(kp).equals(1)) {
            Bukkit.broadcastMessage("§cИгрок " + kp.getName() + " получил награду в " + reward + " за убийство " + p.getName());
            plugin.radiantgold = plugin.radiantgold - plugin.radiantgold / 15;
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage("");
        Bukkit.broadcastMessage("§4" + p.getName() + " §4покинул игру. У него есть 5 минут, чтобы переподключиться.");
        if (!plugin.teams.containsKey(p)) return;
        (new BukkitRunnable() {
            public void run() {
                if (p.isOnline()) return;
                Bukkit.broadcastMessage("§4" + p.getName() + " §4покинул игру!");
                plugin.teams.remove(p);
                p.getInventory().clear();
                p.setGameMode(GameMode.SPECTATOR);
                p.setDisplayName(p.getName());
            }
        }).runTaskLater(Dota.getInstance(), 6000);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage("");
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta metascompass = compass.getItemMeta();
        metascompass.setDisplayName("§2Выбор команды и класса");
        compass.setItemMeta(metascompass);
        p.getInventory().setItem(8, compass);
    }

    @EventHandler
    public void onClickCompass(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getHand() == null) return;
        if (e.getItem() == null) return;
        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (e.getItem().getType().equals(Material.COMPASS) && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Выбор команды и класса")) {
                p.openInventory(Dota.teamSelection);
        }
    }

    @EventHandler
    public void onPickupBounty(EntityPickupItemEvent e) {
        Player p = (Player) e.getEntity();
        if (e.getItem().equals(StartGame.bountyone) || e.getItem().equals(StartGame.bountytwo)) {
            if (plugin.teams.get(p).equals(0)) {
                Bukkit.broadcastMessage("§bИгрок " + p.getName() + " подобрал руну богатства!");
            } else if (plugin.teams.get(p).equals(1)) {
                Bukkit.broadcastMessage("§cИгрок " + p.getName() + " подобрал руну богатства!");
            }
            p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, 180));
            e.setCancelled(true);
            e.getItem().remove();
        }
    }

    @EventHandler
    public void onStrengthUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getHand() == null) return;
        if (!e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (!plugin.clas.containsKey(p)) return;
        if (plugin.clas.get(p).equals(0)) {
            if (p.getInventory().getItemInOffHand().getItemMeta() == null) return;
            if (p.getInventory().getItemInMainHand().getType() == Material.COOKED_CHICKEN) return;
            if (p.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6Перк силы: §eПКМ")) {
                if (p.getName().equalsIgnoreCase("ChistoNeNN")) {
                    if (p.getHealth() >= 22) return;
                    p.setHealth(p.getHealth() + 4);
                } else {
                    if (Cooldown.checkCooldownStrength(e.getPlayer())) {
                        Cooldown.setCooldownStrength(e.getPlayer(), 10);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, 0));
                    } else p.sendMessage("§c§l(!) §cПредмет ещё не готов к использованию!");
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType() != Material.GOLD_NUGGET) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDepositGold(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block == null) return;
        if (!plugin.teams.containsKey(p)) return;
        if (block.getY() >= 105 && block.getY() <= 108 && block.getZ() >= -1 && block.getZ() <= 1) {
            if (plugin.teams.get(p).equals(0)) {
                if (block.getX() >= -41 && block.getX() <= -39) {
                    if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_NUGGET)) {
                        int amountgold = p.getInventory().getItemInMainHand().getAmount();
                        plugin.radiantgold = plugin.radiantgold + amountgold;
                        p.getInventory().getItemInMainHand().setAmount(0);
                    }
                }
            } else if (plugin.teams.get(p).equals(1)) {
                if (block.getX() >= 46 && block.getX() <= 48) {
                    if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_NUGGET)) {
                        int amountgold = p.getInventory().getItemInMainHand().getAmount();
                        plugin.diregold = plugin.diregold + amountgold;
                        p.getInventory().getItemInMainHand().setAmount(0);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreakingMidGold(BlockDamageEvent e) {
        Player p = e.getPlayer();
        if (e.getBlock().getType().equals(Material.GILDED_BLACKSTONE)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 1, true, false));
        } else if (e.getBlock().getX() >= 3 && e.getBlock().getX() <= 4
                && e.getBlock().getY() >= 99 && e.getBlock().getY() <= 101
                && e.getBlock().getZ() >= -64 && e.getBlock().getZ() <= -61
                && e.getBlock().getType().equals(Material.GOLD_BLOCK)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 150, 1, true, false));
        }
    }

    @EventHandler
    public void onDrowShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (!Dota.getInstance().clas.get(p).equals(2)) return;
            if (e.getBow() == null) return;
            if (e.getBow().getItemMeta() == null) return;
            int team = Dota.getInstance().teams.get(p);
            if (e.getBow().getItemMeta().getDisplayName().equalsIgnoreCase("§2Морозный лук")) {
                if (p.getNearbyEntities( 5, 5, 5).isEmpty()) {
                    e.getProjectile().setCustomName("§2Морозная стрела БАФФ " + team);
                } else {
                    e.getProjectile().setCustomName("§2Морозная стрела " + team);
                }
            }
        }
    }

    @EventHandler
    public void onDrowShotHit(ProjectileHitEvent e) {
        if (e.getEntity().getCustomName() == null) return;
        Player terp = (Player) e.getHitEntity();
        if (e.getEntity().getCustomName().contains("0") && Dota.getInstance().teams.get(terp).equals(0)) e.setCancelled(true);
        if (e.getEntity().getCustomName().contains("1") && Dota.getInstance().teams.get(terp).equals(1)) e.setCancelled(true);
        if (e.getEntity().getCustomName().contains("БАФФ")) {
            terp.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1));
            terp.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 50, 1));
        } else if (e.getEntity().getCustomName().contains("§2Морозная стрела")) {
            terp.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1));
        }
    }

    @EventHandler
    public void onZeusZap(ProjectileLaunchEvent e) {
        if (e.getEntity().getType().equals(EntityType.SNOWBALL)) {
            Player p = (Player) e.getEntity().getShooter();
            if (e.getEntity().getCustomName() == null) return;
            if (Dota.getInstance().clas.get(p).equals(3)) {
                int team = Dota.getInstance().teams.get(p);
                e.getEntity().setCustomName("§2Зап " + team);

                ItemStack zap = new ItemStack(Material.SNOWBALL);
                ItemMeta metazap = zap.getItemMeta();
                metazap.setDisplayName("§2Зап");
                metazap.setUnbreakable(true);
                metazap.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                metazap.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                zap.setItemMeta(metazap);

                (new BukkitRunnable() {
                    public void run() {
                        p.getInventory().addItem(zap);
                    }
                }).runTaskLater(Dota.getInstance(), 50);
            }
        }
    }

    @EventHandler
    public void onZapHit(ProjectileHitEvent e) {
        if (e.getEntity().getCustomName() == null) return;
        if (e.getHitEntity() instanceof Player) {
            Player terp = (Player) e.getHitEntity();
            if (e.getEntity().getCustomName().contains("0") && Dota.getInstance().teams.get(terp).equals(0)) return;
            if (e.getEntity().getCustomName().contains("1") && Dota.getInstance().teams.get(terp).equals(1)) return;
            if (e.getEntity().getCustomName().contains("§2Зап")) {
                terp.damage(2);
                if (terp.getHealth() >= 3) {
                    terp.setHealth(terp.getHealth() - 3);
                } else {
                    terp.setHealth(0);
                }
                for (Entity nearby : terp.getNearbyEntities(3, 3, 3)) {
                    if (nearby instanceof Player) {
                        if (Dota.getInstance().teams.get(terp).equals(Dota.getInstance().teams.get(nearby))) {
                            ((Player) nearby).damage(1);
                            if (terp.getHealth() >= 2) {
                                ((Player) nearby).setHealth(((Player) nearby).getHealth() - 2);
                            } else {
                                ((Player) nearby).setHealth(0);
                            }
                        }
                    }
                }
            }
        } else {
            LivingEntity terp = (LivingEntity) e.getHitEntity();
            if (e.getEntity().getCustomName().contains("§2Зап")) {
                terp.damage(5);
                for (Entity nearby : terp.getNearbyEntities(3, 3, 3)) {
                    ((LivingEntity) nearby).damage(5);
                }
            }
        }
    }

    @EventHandler
    public void onWrath(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getHand() == null) return;
        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("§2Гнев Громовержца")) {
                if (Cooldown.checkCooldownWrath(e.getPlayer())) {
                    Cooldown.setCooldownWrath(e.getPlayer(), 25);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (!Dota.getInstance().teams.get(p).equals(Dota.getInstance().teams.get(online))) {
                            online.damage(1);
                            online.setHealth(online.getHealth() - 6);
                            online.getWorld().strikeLightningEffect(online.getLocation());
                        }
                    }
                } else p.sendMessage("§c§l(!) §cПредмет ещё не готов к использованию!");
            }
        }
    }

    @EventHandler
    public void onMobFarming(EntityDeathEvent e) {
        if (e.getEntity().getCustomName() == null) return;
        if (e.getEntity().getKiller() != null) return;
        if (e.getEntity().getCustomName().equalsIgnoreCase("§cПиглин")) {
            int randomgold = (int) (Math.random() * 12) + 19;
            Item gold = Bukkit.getWorld("world").dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.GOLD_NUGGET, randomgold));
        } if (e.getEntity().getCustomName().equalsIgnoreCase("§4Брутальный пиглин")) {
            int randomgold = (int) (Math.random() * 16) + 27;
            Item gold = Bukkit.getWorld("world").dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.GOLD_NUGGET, randomgold));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        if (e.getBlock().getType() == Material.GOLD_ORE) {
            e.setCancelled(true);
            e.getBlock().setType(Material.GRAY_TERRACOTTA);
            int randomgold = (int) (Math.random() * 3) + 1;
            p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, randomgold));
            (new BukkitRunnable() {
                public void run() {
                    e.getBlock().setType(Material.GOLD_ORE);
                }
            }).runTaskLater(Dota.getInstance(), 200);
        } else if (e.getBlock().getType() == Material.GILDED_BLACKSTONE) {
            e.setCancelled(true);
            e.getBlock().setType(Material.BLACKSTONE);
            int randomgold = (int) (Math.random() * 4) + 8;
            p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, randomgold));
            (new BukkitRunnable() {
                public void run() {
                    e.getBlock().setType(Material.GILDED_BLACKSTONE);
                }
            }).runTaskLater(Dota.getInstance(), 600);
        } else if (e.getBlock().getX() >= 3 && e.getBlock().getX() <= 4
                && e.getBlock().getY() >= 99 && e.getBlock().getY() <= 101
                && e.getBlock().getZ() >= -64 && e.getBlock().getZ() <= -61
                && e.getBlock().getType().equals(Material.GOLD_BLOCK)) {
            if (p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                int randomgold = (int) (Math.random() * 20) + 50;
                p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, randomgold));
                (new BukkitRunnable() {
                    public void run() {
                        e.getBlock().setType(Material.GOLD_BLOCK);
                    }
                }).runTaskLater(Dota.getInstance(), 12000);
            } else {
                e.setCancelled(true);
                p.sendMessage("§cВаша кирка слишком слаба");
            }
        } else if (e.getBlock().getType() != Material.GOLD_ORE) {
            p.sendMessage("§cНе ломай карту!");
            e.setCancelled(true);
        }
    }
}
