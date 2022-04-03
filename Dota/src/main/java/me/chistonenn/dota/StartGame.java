package me.chistonenn.dota;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class StartGame {

    Dota plugin;

    public StartGame(Dota plugin) {
        this.plugin = plugin;
    }

    static Villager villager;
    static Zombie zombie;
    static Villager radiantblacksmith;
    static Zombie direblacksmith;

    static IronGolem roshan;

    static Villager radiantcreep;
    static Villager direcreep;
    static Slime radiantT0hpCreep;
    static Slime direT0hpCreep;
    static Zombie radiantT0dmgCreep;
    static Zombie direT0dmgCreep;
    static CaveSpider radiantT0agiCreep;
    static CaveSpider direT0agiCreep;

    static Item bountyone;
    static Item bountytwo;

    static Location roshanspawn = new Location(Bukkit.getWorld("world"), 4, 104, -102);

    static Location radiantcreepspawn = new Location(Bukkit.getWorld("world"), -33.5, 97, 0.5);
    static Location direcreepspawn = new Location(Bukkit.getWorld("world"), 41.5, 97, 0.5);

    static Location radiantnpc = new Location(Bukkit.getWorld("world"), -39.5, 105, 5.5);
    static Location direnpc = new Location(Bukkit.getWorld("world"), 47.5, 105, 5.5);
    static Location radiantblacksmithloc = new Location(Bukkit.getWorld("world"), -42.5, 105, -8.5);
    static Location direblacksmithloc = new Location(Bukkit.getWorld("world"), 51.5, 105, -8.5);

    static Location radiantbounty = new Location(Bukkit.getWorld("world"), -59.0, 107, 100.5);
    static Location direbounty = new Location(Bukkit.getWorld("world"), 67.0, 107, 100.5);


    public static void startGame() {

        for (Player p : Dota.getInstance().teams.keySet()) {
            if (Dota.getInstance().teams.get(p) == 0) { // Силы света
                p.teleport(new Location(Bukkit.getWorld("world"), -69.5, 106, 0.5));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " -69 106 0 -90");
                Dota.getInstance().setscore(p);
            } else if (Dota.getInstance().teams.get(p) == 1) { // Силы тьмы
                p.teleport(new Location(Bukkit.getWorld("world"), 76.5, 106, 0.5));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " 76 106 0 90");
                Dota.getInstance().setscore(p);
            }
        }
        roshan = (IronGolem) Bukkit.getWorld("world").spawn(roshanspawn, IronGolem.class);
        setRoshan(roshan);
        villager = (Villager) Bukkit.getWorld("world").spawn(radiantnpc, Villager.class);
        zombie = (Zombie) Bukkit.getWorld("world").spawn(direnpc, Zombie.class);
        radiantblacksmith = (Villager) Bukkit.getWorld("world").spawn(radiantblacksmithloc, Villager.class);
        direblacksmith = (Zombie) Bukkit.getWorld("world").spawn(direblacksmithloc, Zombie.class);
        setVillager(villager);
        setZombie(zombie);
        setRadiantBlacksmith(radiantblacksmith);
        setDireblacksmith(direblacksmith);

        // Таймер
        (new BukkitRunnable() {
            public void run() {
                if (Dota.getInstance().direhp == 0 || Dota.getInstance().radianthp == 0) return;
                Dota.getInstance().gametimesec++;
                if (Dota.getInstance().gametimesec >= 60) {
                    Dota.getInstance().gametimesec -= 60;
                    Dota.getInstance().gametimemin++;
                }
            }
        }).runTaskTimer(Dota.getInstance(), 0, 20);

        // Не завершилась ли игра?
        (new BukkitRunnable() {
            public void run() {
                if (Dota.getInstance().direhp == 0 || Dota.getInstance().radianthp == 0) {
                    Bukkit.getScheduler().cancelTasks(Dota.getInstance());
                    Dota.getInstance().gamestarted = false;
                    Dota.getInstance().direupgradehp = 0;
                    Dota.getInstance().direupgradedmg = 0;
                    Dota.getInstance().direupgradespd = 0;
                    Dota.getInstance().radiantupgradehp = 0;
                    Dota.getInstance().radiantupgradedmg = 0;
                    Dota.getInstance().radiantupgradespd = 0;
                    Dota.getInstance().direbasehp = 0;
                    Dota.getInstance().direbasedmg = 0;
                    Dota.getInstance().direbasespd = 0;
                    Dota.getInstance().radiantbasehp = 0;
                    Dota.getInstance().radiantbasedmg = 0;
                    Dota.getInstance().radiantbasespd = 0;
                    Dota.getInstance().radiantcreepsliving.clear();
                    Dota.getInstance().direcreepsliving.clear();
                    Dota.getInstance().radiantgold = 0;
                    Dota.getInstance().diregold = 0;
                    Dota.getInstance().direhp = 60;
                    Dota.getInstance().radianthp = 60;
                    Dota.getInstance().gametimesec = 0;
                    Dota.getInstance().gametimemin = 0;
                    for (LivingEntity entities : Bukkit.getWorld("world").getLivingEntities()) {
                        if (!entities.getType().equals(EntityType.PLAYER)) {
                            entities.remove();
                        }
                    }
                }
            }
        }).runTaskTimer(Dota.getInstance(), 0, 1);

        // Выдача эффектов
        (new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!Dota.getInstance().teams.containsKey(player) || !Dota.getInstance().clas.containsKey(player)) return;
                    if (Dota.getInstance().clas.get(player).equals(0)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 140, 1, false, false));
                    } else if (Dota.getInstance().clas.get(player).equals(1)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 140, 0, false, false));
                    } else if (Dota.getInstance().clas.get(player).equals(2)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 0, false, false));
                    } else if (Dota.getInstance().clas.get(player).equals(3)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 0, false, false));
                    }

                    if (player.getName().equalsIgnoreCase("ChistoNeNN")) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 140, 0, false, false));
                    }
                }
            }
        }).runTaskTimer(Dota.getInstance(), 0, 100);

        // Спавн крипов сил света
        (new BukkitRunnable() {
            public void run() {
                if (!Shops.radiantT0hp && !Shops.radiantT0dmg && !Shops.radiantT0agi) {
                    radiantcreep = (Villager) Bukkit.getWorld("world").spawnEntity(radiantcreepspawn, EntityType.VILLAGER);
                    radiantcreep.setAI(false);
                    radiantcreep.setCollidable(false);
                    radiantcreep.setRemoveWhenFarAway(false);
                    radiantcreep.setAdult();
                    radiantcreep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1024);
                    radiantcreep.setHealth(1 + Dota.getInstance().radiantupgradehp);
                    radiantcreep.setRotation(-90, 0);
                    radiantcreep.setMaximumNoDamageTicks(1);
                    Dota.getInstance().radiantcreepsliving.put(radiantcreep, true);
                } else if (Shops.radiantT0hp && !Shops.radiantT0dmg && !Shops.radiantT0agi){
                    radiantT0hpCreep = (Slime) Bukkit.getWorld("world").spawnEntity(radiantcreepspawn, EntityType.SLIME);
                    radiantT0hpCreep.setAI(false);
                    radiantT0hpCreep.setCollidable(false);
                    radiantT0hpCreep.setRemoveWhenFarAway(false);
                    radiantT0hpCreep.setSize(1);
                    radiantT0hpCreep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1024);
                    radiantT0hpCreep.setHealth(1 + Dota.getInstance().radiantupgradehp);
                    radiantT0hpCreep.setRotation(-90, 0);
                    radiantT0hpCreep.setMaximumNoDamageTicks(1);
                    Dota.getInstance().radiantcreepsliving.put(radiantT0hpCreep, true);
                } else if (Shops.radiantT0dmg && !Shops.radiantT0agi) {
                    radiantT0dmgCreep = (Zombie) Bukkit.getWorld("world").spawnEntity(radiantcreepspawn, EntityType.ZOMBIE);
                    radiantT0dmgCreep.setAI(false);
                    radiantT0dmgCreep.setCollidable(false);
                    radiantT0dmgCreep.setRemoveWhenFarAway(false);
                    radiantT0dmgCreep.setAdult();
                    radiantT0dmgCreep.getEquipment().setHelmet(new ItemStack(Material.YELLOW_CARPET));
                    radiantT0dmgCreep.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
                    radiantT0dmgCreep.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
                    radiantT0dmgCreep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1024);
                    radiantT0dmgCreep.setHealth(1 + Dota.getInstance().radiantupgradehp);
                    radiantT0dmgCreep.setRotation(-90, 0);
                    radiantT0dmgCreep.setMaximumNoDamageTicks(1);
                    Dota.getInstance().radiantcreepsliving.put(radiantT0dmgCreep, true);
                } else {
                    radiantT0agiCreep = (CaveSpider) Bukkit.getWorld("world").spawnEntity(radiantcreepspawn, EntityType.CAVE_SPIDER);
                    radiantT0agiCreep.setAI(false);
                    radiantT0agiCreep.setCollidable(false);
                    radiantT0agiCreep.setRemoveWhenFarAway(false);
                    radiantT0agiCreep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1024);
                    radiantT0agiCreep.setHealth(1 + Dota.getInstance().radiantupgradehp);
                    radiantT0agiCreep.setRotation(90, 0);
                    radiantT0agiCreep.setMaximumNoDamageTicks(1);
                    Dota.getInstance().radiantcreepsliving.put(radiantT0dmgCreep, true);
                }
            }
        }).runTaskTimer(Dota.getInstance(), 100, 100);

        // Движение крипов сил света
        (new BukkitRunnable() {
            public void run() {
                for (LivingEntity radiantcreepsto : Dota.getInstance().radiantcreepsliving.keySet()) {
                    Location loc = radiantcreepsto.getLocation();
                    Location locTp = loc.clone().add((0.1 + 0.008 * Dota.getInstance().radiantupgradespd), 0, 0);
                    radiantcreepsto.teleport(locTp);
                    if (locTp.getX() >= 42.5 && !radiantcreepsto.isDead()) {
                        radiantcreepsto.remove();
                        Dota.getInstance().direhp--;
                        Bukkit.broadcastMessage("§cУ древнего сил Тьмы " + Dota.getInstance().direhp + " здоровья!");
                        if (Dota.getInstance().direhp == 0) {
                            Bukkit.broadcastMessage("§bИгра окончена победой сил Света!");
                            Bukkit.broadcastMessage("§bИтоги матча:");
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (Dota.getInstance().teams.containsKey(p)) {
                                    if (Dota.getInstance().teams.get(p).equals(0)) {
                                        p.getInventory().clear();
                                        p.setGameMode(GameMode.SPECTATOR);
                                        PlayerManager playerManager = Dota.playerManagerHashMap.get(p.getUniqueId());
                                        Bukkit.broadcastMessage("§2" + p.getName() + " §6ПОБЕДА! §a(" + playerManager.getRating() + " +30 рейтинга)");
                                        playerManager.setRating(playerManager.getRating() + 30);
                                        playerManager.setWins(playerManager.getWins() + 1);
                                    }
                                }
                            }
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (Dota.getInstance().teams.containsKey(p)) {
                                    if (Dota.getInstance().teams.get(p).equals(1)) {
                                        p.getInventory().clear();
                                        p.setGameMode(GameMode.SPECTATOR);
                                        PlayerManager playerManager = Dota.playerManagerHashMap.get(p.getUniqueId());
                                        Bukkit.broadcastMessage("§2" + p.getName() + " §cПОРАЖЕНИЕ! §с(" + playerManager.getRating() + " -30 рейтинга)");
                                        if (playerManager.getRating() >= 30)
                                            playerManager.setRating(playerManager.getRating() - 30);
                                        playerManager.setLosses(playerManager.getLosses() + 1);
                                    }
                                }
                            }
                        }
                    }
                    Dota.getInstance().radiantdamage = 1 + Dota.getInstance().radiantupgradedmg / 20F;
                    for (Entity direcreepsinr : radiantcreepsto.getNearbyEntities(0.7, 1, 0.7)) {
                        if (Dota.getInstance().direcreepsliving.containsKey(direcreepsinr)) {
                            if (((LivingEntity) direcreepsinr).getHealth() >= Dota.getInstance().radiantdamage) {
                                ((LivingEntity) direcreepsinr).setHealth(((LivingEntity) direcreepsinr).getHealth() - Dota.getInstance().radiantdamage);
                            } else ((LivingEntity) direcreepsinr).damage(((LivingEntity) direcreepsinr).getHealth());
                        }
                    }
                }
            }
        }).runTaskTimer(Dota.getInstance(), 100, 1);

        // Спавн крипов сил тьмы
        (new BukkitRunnable() {
            public void run() {
                if (!Shops.direT0hp && !Shops.direT0dmg && !Shops.direT0agi) {
                    direcreep = (Villager) Bukkit.getWorld("world").spawnEntity(direcreepspawn, EntityType.VILLAGER);
                    direcreep.setAI(false);
                    direcreep.setCollidable(false);
                    direcreep.setRemoveWhenFarAway(false);
                    direcreep.setAdult();
                    direcreep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1024);
                    direcreep.setHealth(1 + Dota.getInstance().direupgradehp);
                    direcreep.setRotation(90, 0);
                    direcreep.setMaximumNoDamageTicks(1);
                    Dota.getInstance().direcreepsliving.put(direcreep, true);
                } else if (Shops.direT0hp && !Shops.direT0dmg && !Shops.direT0agi){
                    direT0hpCreep = (Slime) Bukkit.getWorld("world").spawnEntity(direcreepspawn, EntityType.SLIME);
                    direT0hpCreep.setAI(false);
                    direT0hpCreep.setCollidable(false);
                    direT0hpCreep.setRemoveWhenFarAway(false);
                    direT0hpCreep.setSize(1);
                    direT0hpCreep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1024);
                    direT0hpCreep.setHealth(1 + Dota.getInstance().direupgradehp);
                    direT0hpCreep.setRotation(90, 0);
                    direT0hpCreep.setMaximumNoDamageTicks(1);
                    Dota.getInstance().direcreepsliving.put(direT0hpCreep, true);
                } else if (Shops.direT0dmg && !Shops.direT0agi) {
                    direT0dmgCreep = (Zombie) Bukkit.getWorld("world").spawnEntity(direcreepspawn, EntityType.ZOMBIE);
                    direT0dmgCreep.setAI(false);
                    direT0dmgCreep.setCollidable(false);
                    direT0dmgCreep.setRemoveWhenFarAway(false);
                    direT0dmgCreep.setAdult();
                    direT0dmgCreep.getEquipment().setHelmet(new ItemStack(Material.YELLOW_CARPET));
                    direT0dmgCreep.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
                    direT0dmgCreep.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
                    direT0dmgCreep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1024);
                    direT0dmgCreep.setHealth(1 + Dota.getInstance().direupgradehp);
                    direT0dmgCreep.setRotation(90, 0);
                    direT0dmgCreep.setMaximumNoDamageTicks(1);
                    Dota.getInstance().direcreepsliving.put(direT0dmgCreep, true);
                } else {
                    direT0agiCreep = (CaveSpider) Bukkit.getWorld("world").spawnEntity(direcreepspawn, EntityType.CAVE_SPIDER);
                    direT0agiCreep.setAI(false);
                    direT0agiCreep.setCollidable(false);
                    direT0agiCreep.setRemoveWhenFarAway(false);
                    direT0agiCreep.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1024);
                    direT0agiCreep.setHealth(1 + Dota.getInstance().direupgradehp);
                    direT0agiCreep.setRotation(90, 0);
                    direT0agiCreep.setMaximumNoDamageTicks(1);
                    Dota.getInstance().direcreepsliving.put(direT0dmgCreep, true);
                }
            }
        }).runTaskTimer(Dota.getInstance(), 100, 100);

        // Движение крипов сил тьмы
        (new BukkitRunnable() {
            public void run() {
                for (LivingEntity direcreepsto : Dota.getInstance().direcreepsliving.keySet()) {
                    Location loc = direcreepsto.getLocation();
                    Location locTp = loc.clone().add((-0.1 - 0.008 * Dota.getInstance().direupgradespd), 0, 0);
                    direcreepsto.teleport(locTp);
                    if (loc.getX() <= -34.5 && !direcreepsto.isDead()) {
                        direcreepsto.remove();
                        Dota.getInstance().radianthp--;
                        Bukkit.broadcastMessage("§bУ древнего сил Света осталось " + Dota.getInstance().radianthp + " здоровья!");
                        if (Dota.getInstance().radianthp == 0) {
                            Bukkit.broadcastMessage("§cИгра окончена победой сил Тьмы!");
                            Bukkit.broadcastMessage("§bИтоги матча:");
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (!Dota.getInstance().teams.containsKey(p)) return;
                                if (Dota.getInstance().teams.get(p).equals(1)) {
                                    p.getInventory().clear();
                                    p.setGameMode(GameMode.SPECTATOR);
                                    PlayerManager playerManager = Dota.playerManagerHashMap.get(p.getUniqueId());
                                    Bukkit.broadcastMessage("§2" + p.getName() + " §6ПОБЕДА! §a(" + playerManager.getRating() + " +30 рейтинга)");
                                    playerManager.setRating(playerManager.getRating() + 30);
                                    playerManager.setWins(playerManager.getWins() + 1);
                                }
                            }
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (!Dota.getInstance().teams.containsKey(p)) return;
                                if (Dota.getInstance().teams.get(p).equals(0)) {
                                    p.getInventory().clear();
                                    p.setGameMode(GameMode.SPECTATOR);
                                    PlayerManager playerManager = Dota.playerManagerHashMap.get(p.getUniqueId());
                                    Bukkit.broadcastMessage("§2" + p.getName() + " §cПОРАЖЕНИЕ! §с(" + playerManager.getRating() + " -30 рейтинга)");
                                    if (playerManager.getRating() >= 30)
                                        playerManager.setRating(playerManager.getRating() - 30);
                                    playerManager.setLosses(playerManager.getLosses() + 1);
                                }
                            }
                        }
                    }
                    Dota.getInstance().diredamage = 1 + Dota.getInstance().direupgradedmg / 20F;
                    for (Entity radiantcreepsinr : direcreepsto.getNearbyEntities(0.7, 1, 0.7)) {
                        if (Dota.getInstance().radiantcreepsliving.containsKey(radiantcreepsinr)) {
                            if (((LivingEntity) radiantcreepsinr).getHealth() >= Dota.getInstance().diredamage) {
                                ((LivingEntity) radiantcreepsinr).setHealth(((LivingEntity) radiantcreepsinr).getHealth() - Dota.getInstance().diredamage);
                            } else ((LivingEntity) radiantcreepsinr).damage(((LivingEntity) radiantcreepsinr).getHealth());
                        }
                    }
                }
            }
        }).runTaskTimer(Dota.getInstance(), 100, 1);

        // Спавн рун
        (new BukkitRunnable() {
            public void run() {
                bountyone = Bukkit.getWorld("world").dropItemNaturally(radiantbounty, new ItemStack(Material.GOLD_BLOCK));
                bountyone.setCustomName("§6Руна богатства");
                bountyone.setCustomNameVisible(true);

                bountytwo = Bukkit.getWorld("world").dropItemNaturally(direbounty, new ItemStack(Material.GOLD_BLOCK));
                bountytwo.setCustomName("§6Руна богатства");
                bountytwo.setCustomNameVisible(true);
            }
        }).runTaskTimer(Dota.getInstance(), 3600, 3600);
    }
    public static void setRoshan(IronGolem roshan) {
        roshan.setAI(false);
        roshan.setInvulnerable(true);
        roshan.setCustomName("§cРошан");
        roshan.setCustomNameVisible(true);
        roshan.setRemoveWhenFarAway(false);
        roshan.setMaximumNoDamageTicks(40);
    }

    public static void setVillager(Villager villager) {
        villager.setCustomName("§2Основная казарма");
        villager.setAI(false);
        villager.setAdult();
        villager.setCollidable(false);
        villager.setCustomNameVisible(true);
        villager.setInvulnerable(true);
        villager.setRemoveWhenFarAway(false);
    }

    public static void setZombie(Zombie zombie) {
        zombie.setCustomName("§2Основная казарма");
        zombie.setAI(false);
        zombie.setAdult();
        zombie.setSilent(true);
        zombie.setCollidable(false);
        zombie.setCustomNameVisible(true);
        zombie.setInvulnerable(true);
        zombie.setRemoveWhenFarAway(false);
        zombie.getEquipment().setHelmet(new ItemStack(Material.BIRCH_BUTTON));
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999999, 0, false, false));
    }

    public static void setRadiantBlacksmith(Villager radiantblacksmith) {
        radiantblacksmith.setCustomName("§2Кузнец");
        radiantblacksmith.setAI(false);
        radiantblacksmith.setAdult();
        radiantblacksmith.setCollidable(false);
        radiantblacksmith.setCustomNameVisible(true);
        radiantblacksmith.setInvulnerable(true);
        radiantblacksmith.setRemoveWhenFarAway(false);
    }

    public static void setDireblacksmith(Zombie direblacksmith) {
        direblacksmith.setCustomName("§2Кузнец");
        direblacksmith.setAI(false);
        direblacksmith.setAdult();
        direblacksmith.setSilent(true);
        direblacksmith.setCollidable(false);
        direblacksmith.setCustomNameVisible(true);
        direblacksmith.setInvulnerable(true);
        direblacksmith.setRemoveWhenFarAway(false);
        direblacksmith.getEquipment().setHelmet(new ItemStack(Material.BIRCH_BUTTON));
        direblacksmith.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999999, 0, false, false));
    }
}
