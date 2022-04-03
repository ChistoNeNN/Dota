package me.chistonenn.dota;

import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public final class Dota extends JavaPlugin implements Listener, CommandExecutor {

    public static Dota getInstance() {
        return getPlugin(Dota.class);
    }

    public static Inventory teamSelection;
    public static Inventory classSelection;
    public static Inventory T0RadiantShop;
    public static Inventory T0DireShop;
    public static Inventory T0RadiantShop2;
    public static Inventory T0DireShop2;
    public static Inventory Blacksmith;

    HashMap<Player, Integer> teams = new HashMap<>();
    HashMap<Player, Integer> clas = new HashMap<>(); // ID классов: 0 - воин, 1 - шахтер, 2 - морозный лучник, 3 - зевс
    HashMap<Player, Player> phoenixbound = new HashMap<>();

    int radiantminers = 0;
    int radiantzeus = 0;
    int direminers = 0;
    int direzeus = 0;

    HashMap<LivingEntity, Boolean> direcreepsliving = new HashMap<>();
    HashMap<LivingEntity, Boolean> radiantcreepsliving = new HashMap<>();

    int radianthp;
    int direhp;

    int radiantupgradehp = 0;
    int radiantupgradedmg = 0;
    int radiantupgradespd = 0;
    int radiantbasehp = 1;
    int radiantbasedmg = 1;
    int radiantbasespd = 1;

    double radiantdamage = 0.05;

    int direupgradehp = 0;
    int direupgradedmg = 0;
    int direupgradespd = 0;
    int direbasehp = 1;
    int direbasedmg = 1;
    int direbasespd = 1;

    double diredamage = 0.05;

    int radiantgold = 0;
    int diregold = 0;

    boolean gamestarted = false;
    boolean radiantshopopened = false;
    boolean direshopopened = false;
    boolean radiantshop2opened = false;
    boolean direshop2opened = false;

    int gametimemin = 0;
    int gametimesec = 0;

    int price;
    int rewardType; // 0 - хп, 1 - урон, 2 - мс, 3 - хпветка, 4 - дмгветка, 5 - агиветка
    ItemStack item;
    Material requireditem;

    public static HashMap<UUID, PlayerManager> playerManagerHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        int pluginId = 14808;
        Metrics metrics = new Metrics(this, pluginId);

        playerManagerHashMap = new HashMap<>();
        Commands commands = new Commands();
        Teams.createCommandSelection();
        Teams.createClassSelection();
        Shops.createT0RadiantShop();
        Shops.createT0DireShop();
        //Shops.createT0RadiantShop2();
        //Shops.createT0DireShop2();
        Cooldown.setupCooldownStrength();
        Cooldown.setupCooldownWrath();
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new PlayerRegister(this), this);
        getServer().getPluginManager().registerEvents(new Chat(this), this);
        getServer().getPluginManager().registerEvents(new Shops(this), this);
        getServer().getPluginManager().registerEvents(new Teams(this), this);
        getServer().getPluginManager().registerEvents(new Events(this), this);
        getServer().getPluginManager().registerEvents(new ClassesChest(this), this);
        getCommand("startgame").setExecutor(commands);
        getCommand("clearinventories").setExecutor(commands);
        getCommand("team").setExecutor(commands);
        getCommand("stopgame").setExecutor(commands);
        getCommand("setstat").setExecutor(commands);
        getCommand("playerinfo").setExecutor(commands);
        getCommand("heal").setExecutor(commands);
        getCommand("sethp").setExecutor(commands);
        getCommand("giveclasschestkey").setExecutor(commands);
    }

    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerManager playerManager = playerManagerHashMap.get(p.getUniqueId());

            if (playerManagerHashMap.containsKey(p.getUniqueId())) {
                getConfig().set("PlayerInfo." + p.getName() + ".minerlevel", playerManager.getMinerLevel());
                getConfig().set("PlayerInfo." + p.getName() + ".warlevel", playerManager.getWarLevel());
                getConfig().set("PlayerInfo." + p.getName() + ".minerxp", playerManager.getMinerXp());
                getConfig().set("PlayerInfo." + p.getName() + ".warxp", playerManager.getWarXp());
                getConfig().set("PlayerInfo." + p.getName() + ".rating", playerManager.getRating());
                getConfig().set("PlayerInfo." + p.getName() + ".wins", playerManager.getWins());
                getConfig().set("PlayerInfo." + p.getName() + ".losses", playerManager.getLosses());
                saveConfig();
                playerManagerHashMap.remove(p.getUniqueId());
            }
            p.kickPlayer("§c§lСервер перезагружается. Перезайдите через 30 секунд.");
        }
        playerManagerHashMap.clear();
    }

    public void setscore(Player player) {
        Scoreboard scoreboard = getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("test", "dummy", "§b§lDefense of the Ancient");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // ⚔☭✇⚓⚠☢☯⚡♨⚒⚛☣⌚⌛⚐☘✌∞©✄✐✑✒☕☏✔✗➤➷❥❤½¼ⒶⒷ₽♲

        Team time = scoreboard.registerNewTeam("time");
        time.addEntry("§1" + "§f");
        time.setPrefix("⌚ Текущее время: §6" + gametimemin + ":" + gametimesec);
        objective.getScore("§1" + "§f").setScore(7);

        Score stats = objective.getScore("§6§lСтатистика");
        stats.setScore(6);

        if (teams.get(player).equals(0)) {
            Score team = objective.getScore("☭ §bСилы Света");
            team.setScore(5);

            Team goldCounter = scoreboard.registerNewTeam("goldCounter");
            goldCounter.addEntry("§0" + "§f");
            goldCounter.setPrefix("₽ Золото вашей команды: §6" + radiantgold);
            objective.getScore("§0" + "§f").setScore(4);
        } else if (teams.get(player).equals(1)) {
            Score team = objective.getScore("☭ §cСилы Тьмы");
            team.setScore(5);

            Team goldCounter = scoreboard.registerNewTeam("goldCounter");
            goldCounter.addEntry("§0" + "§f");
            goldCounter.setPrefix("₽ Золото вашей команды: §6" + diregold);
            objective.getScore("§0" + "§f").setScore(4);
        }

        Score space = objective.getScore("");
        space.setScore(3);

        Score info = objective.getScore("§e§lИнформация");
        info.setScore(2);

        Team onlineCounter = scoreboard.registerNewTeam("onlineCounter");
        onlineCounter.addEntry("§3" + "§f");
        onlineCounter.setPrefix("Онлайн: §b" + Bukkit.getOnlinePlayers().size());
        objective.getScore("§3" + "§f").setScore(1);

        Score site = objective.getScore("Сайт: §bdiamondworld.pro");
        site.setScore(0);

        player.setScoreboard(scoreboard);

        updateScoreBoard(player);
    }

    public void updateScoreBoard(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player == null || !player.isOnline()) {
                    this.cancel();
                } else {
                    Scoreboard scoreboard = player.getScoreboard();

                    scoreboard.getTeam("time").setPrefix("⌚ Текущее время: §6" + gametimemin + ":" + gametimesec);

                    if (teams.get(player).equals(0)) {
                        scoreboard.getTeam("goldCounter").setPrefix("₽ Золото вашей команды: §6" + radiantgold);
                    } else if (teams.get(player).equals(1)) {
                        scoreboard.getTeam("goldCounter").setPrefix("₽ Золото вашей команды: §6" + diregold);
                    }

                    if (Bukkit.getOnlinePlayers().size() == 0) {
                        scoreboard.getTeam("onlineCounter").setPrefix("Онлайн: §b0");
                    } else {
                        scoreboard.getTeam("onlineCounter").setPrefix("Онлайн: §b" + Bukkit.getOnlinePlayers().size());
                    }
                }
            }
        }.runTaskTimer(this, 0, 5);
    }
}
