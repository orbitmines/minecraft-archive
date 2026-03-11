package fadidev.orbitmines.minigames.handlers.data;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.*;
import fadidev.orbitmines.minigames.handlers.players.ArenaPlayer;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.State;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Fadi on 30-9-2016.
 */
public class UHCData extends ArenaData {

    private OrbitMinesMiniGames minigames;
    private MiniGamesPlayer firstPlace;
    private MiniGamesPlayer secondPlace;
    private MiniGamesPlayer thirdPlace;

    private boolean inPvP;
    private double border;
    private boolean doubleIron;
    private List<Block> ironOresPlaced;

    public UHCData(Arena arena) {
        super(arena);

        minigames = OrbitMinesMiniGames.getMiniGames();

        this.inPvP = false;
        this.border = 1000D;
        this.doubleIron = false;
        this.ironOresPlaced = new ArrayList<>();
    }

    public MiniGamesPlayer getFirstPlace() {
        return firstPlace;
    }

    public void setFirstPlace(MiniGamesPlayer firstPlace) {
        this.firstPlace = firstPlace;
    }

    public MiniGamesPlayer getSecondPlace() {
        return secondPlace;
    }

    public void setSecondPlace(MiniGamesPlayer secondPlace) {
        this.secondPlace = secondPlace;
    }

    public MiniGamesPlayer getThirdPlace() {
        return thirdPlace;
    }

    public void setThirdPlace(MiniGamesPlayer thirdPlace) {
        this.thirdPlace = thirdPlace;
    }

    public boolean isInPvP() {
        return inPvP;
    }

    public void setInPvP(boolean inPvP) {
        this.inPvP = inPvP;
    }

    public double getBorder() {
        return border;
    }

    public void setBorder(double border) {
        this.border = border;

        WorldBorder wb = arena.getMap().getSpectatorLocation().getWorld().getWorldBorder();
        wb.setCenter(arena.getMap().getSpectatorLocation());
        wb.setSize(border);
    }

    public void tickBorder(){
        this.border -= 1.5;

        WorldBorder wb = arena.getMap().getSpectatorLocation().getWorld().getWorldBorder();
        wb.setCenter(arena.getMap().getSpectatorLocation());
        wb.setSize(getBorder());
    }

    public boolean isDoubleIron() {
        return doubleIron;
    }

    public void setDoubleIron(boolean doubleIron) {
        this.doubleIron = doubleIron;
    }

    public List<Block> getIronOresPlaced() {
        return ironOresPlaced;
    }

    public void pvp(){
        setInPvP(true);

        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
        arena.sendMessage("§6§m--------------------------------------------------");
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §f§lUHC §7- §cPvP " + Utils.statusString(omp.getLanguage(), true);
            }
        });
        arena.sendMessage("");
        arena.sendMessage(MiniGamesMessages.UHC_INFO_PVP_1);
        arena.sendMessage(MiniGamesMessages.UHC_INFO_PVP_2);
        arena.sendMessage("§6§m--------------------------------------------------");
    }

    @Override
    public void restart() {
        arena.setState(State.RESTARTING);

        MiniGamesMap map = arena.getMap();
        map.setInUse(false);
        map.restoreWorld();
        arena.setRandomMap();

        new BukkitRunnable(){
            public void run(){
                waiting();
            }
        }.runTaskLater(minigames, 100);
    }

    @Override
    public void ending() {
        arena.setMinutes(0);
        arena.setSeconds(15);

        if(arena.getPlayers().size() == 1){
            setFirstPlace(arena.getPlayers().get(0));
        }
        else{
            List<MiniGamesPlayer> players = new ArrayList<MiniGamesPlayer>();
            players.addAll(arena.getPlayers());

            if(getFirstPlace() == null){
                MiniGamesPlayer omp = arena.getMostKills(players);
                setFirstPlace(omp);
                players.remove(omp);
            }
            if(getSecondPlace() == null){
                MiniGamesPlayer omp = arena.getMostKills(players);
                setSecondPlace(omp);
                players.remove(omp);
            }
            if(getThirdPlace() == null){
                MiniGamesPlayer omp = arena.getMostKills(players);
                setThirdPlace(omp);
                players.remove(omp);
            }
        }

        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
        arena.sendMessage("§6§m--------------------------------------------------");
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §f§lUHC §7- §6" + MiniGamesMessages.WORD_RESULTS;
            }
        });
        arena.sendMessage("");
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §6" + MiniGamesMessages.WORD_FIRST.get(omp) + ": " + getFirstPlace().getName();
            }
        });
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §7" + MiniGamesMessages.WORD_SECOND.get(omp) + ": " + getSecondPlace().getName();
            }
        });
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §c" + MiniGamesMessages.WORD_THIRD.get(omp) + ": " + getThirdPlace().getName();
            }
        });
        arena.sendMessage("");
        arena.sendMessage("§6§m--------------------------------------------------");

        for(MiniGamesPlayer omp : arena.getDeadPlayers()){
            omp.getPlayer().sendMessage("§c§l+1 Lose");
            omp.getUhcPlayer().addLose();
        }

        getFirstPlace().getPlayer().sendMessage("§2§l+1 Win");
        getFirstPlace().getPlayer().sendMessage("§f§l+5 Tickets");
        getFirstPlace().getUhcPlayer().addWin();
        getFirstPlace().addMiniGameTickets(3);

        arena.setState(State.ENDING);
        arena.clearScoreboards();
    }

    @Override
    public void start() {
        arena.setMinutes(30);
        arena.setSeconds(0);

        for(MiniGamesPlayer omp : arena.getPlayers()){
            omp.clearInventory();
            omp.getUhcPlayer().setRoundKills(0);

            Kit kit = omp.getUhcPlayer().getKitSelected();
            if(kit != null){
                TicketType type = TicketType.valueOf(kit.getName());
                omp.removeTicket(type);

                kit.setItems(omp.getPlayer());
            }

            omp.updateInventory();
        }

        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return MiniGamesMessages.UHC_PVP_ENABLED_IN.get(omp, (arena.getMinutes() - 15) + "", arena.getSeconds() + "");
            }
        });

        setBorder(getBorder());
        arena.getMap().getWorld().setTime(0);
        arena.setState(State.IN_GAME);
        arena.clearScoreboards();
    }

    @Override
    public void warmup() {
        int totalSeconds = 25 + arena.getPlayers().size() * 5;
        int seconds = totalSeconds % 60;
        int minutes = 0;

        if(seconds == totalSeconds){
            arena.setMinutes(minutes);
            arena.setSeconds(totalSeconds);
        }
        else{
            minutes = (totalSeconds - seconds) / 60;

            arena.setMinutes(minutes);
            arena.setSeconds(seconds);
        }

        teleportToWorldLobby();

        for(MiniGamesPlayer omp : arena.getPlayers()){
            omp.clearInventory();
            omp.updateInventory();

            for(MiniGamesPlayer omplayer : arena.getPlayers()){
                omp.getPlayer().showPlayer(omplayer.getPlayer());
            }
        }

        arena.getMap().getWorld().setTime(0);
        WorldUtils.removeEntities(arena.getMap().getWorld());

        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
        arena.sendMessage("§6§m--------------------------------------------------");
        arena.sendMessage(" §f§lUHC §7- §6Info");
        arena.sendMessage("");
        arena.sendMessage(MiniGamesMessages.UHC_INFO_1);
        arena.sendMessage(MiniGamesMessages.UHC_INFO_2);
        arena.sendMessage(MiniGamesMessages.UHC_INFO_3);
        arena.sendMessage(MiniGamesMessages.UHC_INFO_4);
        arena.sendMessage(MiniGamesMessages.UHC_INFO_5);
        arena.sendMessage("");
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §7Spawn " + MiniGamesMessages.WORD_BUILDERS.get(omp) + ": §6" +  arena.getMap().getBuilder();
            }
        });
        arena.sendMessage("§6§m--------------------------------------------------");
        arena.sendMessage(MiniGamesMessages.UHC_TELEPORTING_ALL_PLAYERS);

        arena.setState(State.WARMUP);
        arena.clearScoreboards();
    }

    @Override
    public void waiting() {
        arena.setMinutes(1);
        arena.setSeconds(0);
        arena.getPlayers().clear();
        arena.getDeadPlayers().clear();
        arena.getSpectators().clear();

        this.inPvP = false;
        this.firstPlace = null;
        this.secondPlace = null;
        this.thirdPlace = null;
        this.border = 1000D;
        this.doubleIron = false;
        this.ironOresPlaced.clear();

        arena.setState(State.WAITING);
    }

    @Override
    public void rewardPlayers() {
        for(MiniGamesPlayer omp : arena.getAllPlayers()){
            Player p = omp.getPlayer();

            if(arena.isPlayer(omp) || arena.isSpectator(omp) && arena.getDeadPlayers().contains(omp)){
                int amount = 0;

                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
                p.sendMessage("§6§m--------------------------------------------------");
                p.sendMessage(" §f§lUHC §7- §6" + MiniGamesMessages.WORD_REWARDS.get(omp));
                p.sendMessage("");
                if(getFirstPlace() == omp){
                    p.sendMessage(" §f§l+200 §7(" + MiniGamesMessages.WORD_FIRST_PLACE.get(omp) + ")");
                    amount += 200;
                }
                else if(getSecondPlace() == omp){
                    p.sendMessage(" §f§l+120 §7(" + MiniGamesMessages.WORD_SECOND_PLACE.get(omp) + ")");
                    amount += 120;
                }
                else if(getThirdPlace() == omp){
                    p.sendMessage(" §f§l+80 §7(" + MiniGamesMessages.WORD_THIRD_PLACE.get(omp) + ")");
                    amount += 80;
                }
                else{
                    p.sendMessage(" §f§l+40 §7(" + MiniGamesMessages.WORD_PATIENT.get(omp) + ")");
                    amount += 40;
                }

                int kills = omp.getUhcPlayer().getRoundKills();
                if(kills != 0){
                    if(kills == 1){
                        p.sendMessage(" §f§l+" + 10 + " §7(1 Kill)");
                    }
                    else{
                        p.sendMessage(" §f§l+" + 10 * kills + " §7(" + kills + " Kills)");
                    }
                    amount += 10 * kills;
                }

                //TODO Boosters

                p.sendMessage(" §7" + MiniGamesMessages.WORD_NEW_BALANCE.get(omp) + ": §f§l" + (omp.getMiniGameCoins() + amount) + " Coins");
                p.sendMessage("§6§m--------------------------------------------------");

                omp.addMiniGameCoins(amount);
            }
        }
    }

    @Override
    public void teleportToArena() {

    }

    public void teleportToArena(MiniGamesPlayer omp){
        MiniGamesMap map = getArena().getMap();

        List<Location> locations = new ArrayList<>();
        for(Location l : map.getSpawns()){
            if(!map.getPlayerSpawns().containsValue(l)){
                locations.add(l);
            }
        }
        Location l = locations.get(new Random().nextInt(locations.size()));
        Location location = new Location(l.getWorld(), l.getX(), getHighestYPos(l), l.getZ());
        arena.getMap().getPlayerSpawns().put(omp, location);
        arena.getMap().getSpawns().remove(l);
        arena.getMap().getSpawns().add(location);

        omp.getPlayer().teleport(location);
        location.getBlock().getRelative(BlockFace.DOWN).setType(Material.BEDROCK);
    }

    @Override
    public void leave(MiniGamesPlayer omp) {
        if(arena.getState() == State.WAITING || arena.getState() == State.STARTING){
            if(omp.getUhcPlayer().isBlueGold()){
                omp.getUhcPlayer().setBlueGold(false);
                omp.addTicketAmount(TicketType.GOLD_FROM_LAPIS, 1);
            }
            if(omp.getUhcPlayer().isRedGold()){
                omp.getUhcPlayer().setRedGold(false);
                omp.addTicketAmount(TicketType.GOLD_FROM_REDSTONE, 1);
            }
        }
        if(arena.getState() == State.IN_GAME || arena.getState() == State.WARMUP){
            omp.getPlayer().damage(100D);

            if(arena.getPlayers().size() == 1){
                setSecondPlace(omp);
                ending();
            }
            else if(arena.getPlayers().size() == 2){
                setThirdPlace(omp);
            }

            omp.getUhcPlayer().addLose();
        }
    }

    @Override
    public void leaveSpectator(MiniGamesPlayer omp) {
        if(arena.getDeadPlayers().contains(omp))
            omp.getUhcPlayer().addLose();
    }

    @Override
    public void starting(MiniGamesPlayer omp) {

    }

    @Override
    public void run(MiniGamesPlayer omp) {
        Player p = omp.getPlayer();

        if(arena.getState() == State.WARMUP){
            Location l = arena.getMap().getPlayerSpawns().get(omp);

            if(l != null){
                if(p.getLocation().distance(l) >= 1.3){
                    p.teleport(l);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F, 0.1F);

                    Title t = new Title("", MiniGamesMessages.STAY_ON_PLATFORM.get(omp), 0, 30, 0);
                    t.send(p);
                }
            }
            else{
                if(p.getLocation().distance(arena.getMap().getSpectatorLocation()) >= 25){
                    p.teleport(arena.getMap().getSpectatorLocation());
                }
            }
        }
    }

    @Override
    public void run(State state) {
        switch(state){
            case WARMUP:
                int totalSeconds = 30 + arena.getPlayers().size() * 5;
                int seconds = totalSeconds % 60;
                int minutes = 0;

                if(totalSeconds != seconds){
                    minutes = (totalSeconds - seconds) / 60;
                }

                if(arena.getMinutes() * 60 + arena.getSeconds() <= (minutes * 60 + seconds) -15 && arena.getSeconds() % 5 == 0){
                    boolean found = false;
                    for(MiniGamesPlayer omp : arena.getPlayers()){
                        if(!found && !arena.getMap().getPlayerSpawns().containsKey(omp)){
                            found = true;
                            teleportToArena(omp);
                        }
                    }
                }
                break;
            case IN_GAME:
                if(isInPvP() && getBorder() > 100D)
                    tickBorder();

                if(arena.getSeconds() == 0){
                    if(arena.getMinutes() != 0 && arena.getMinutes() % 4 == 0){
                        for(MiniGamesPlayer omp : arena.getPlayers()){
                            if(omp.getUhcPlayer().getKitSelected() != null && omp.getUhcPlayer().getKitSelected().getName().equals(TicketType.APPLETREE_KIT.getKit().getName())){
                                String type = TicketType.APPLETREE_KIT.getRarity().getColor() + TicketType.APPLETREE_KIT.getName();
                                omp.getPlayer().getInventory().addItem(ItemUtils.itemstack(Material.APPLE, 1, null, 0, type));
                                omp.getPlayer().playSound(omp.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 5, 1);
                            }
                        }
                    }

                    if(arena.getMinutes() == 25 || arena.getMinutes() == 20 || arena.getMinutes() == 16){
                        arena.playSound(Sound.UI_BUTTON_CLICK, 5, 1);
                        arena.sendMessage(new Arena.ArenaMessage() {
                            @Override
                            public String getMessage(MiniGamesPlayer omp) {
                                return MiniGamesMessages.UHC_PVP_ENABLED_IN.get(omp, (arena.getMinutes() -15) + "", arena.getSeconds() + "");
                            }
                        });
                    }
                    else if(arena.getMinutes() == 15){
                        pvp();
                    }
                    else if(arena.getMinutes() == 10 || arena.getMinutes() == 5 || arena.getMinutes() == 2 || arena.getMinutes() == 1){
                        arena.playSound(Sound.UI_BUTTON_CLICK, 5, 1);
                        arena.sendMessage(new Arena.ArenaMessage() {
                            @Override
                            public String getMessage(MiniGamesPlayer omp) {
                                return MiniGamesMessages.UHC_GAME_ENDING_IN.get(omp, arena.getMinutes() + "", arena.getSeconds() + "");
                            }
                        });
                    }
                }
                break;
        }
    }

    @Override
    public void updateScoreboard(MiniGamesScoreboard b) {
        MiniGamesPlayer omp = (MiniGamesPlayer) b.getOMPlayer();
        switch(arena.getState()){
            case WARMUP:
                b.addScore(3, "");
                b.addScore(2, MiniGamesMessages.UHC_TELEPORTING_PLAYERS.get(omp));
                b.addScore(1, " " + arena.getMinutes() + "m " + arena.getSeconds() + "s");
                b.addScore(0, " ");

                break;
            case IN_GAME:
                b.addScore(14, "");
                b.addScore(13, "§c§lPvP " + Utils.statusString(omp.getLanguage(), isInPvP()));
                b.addScore(12, " ");
                b.addScore(11, "§a§l" + MiniGamesMessages.WORD_ALIVE.get(omp));
                b.addScore(10, " " + arena.getPlayers().size());
                b.addScore(9, "  ");
                b.addScore(8, "§e§lBorder " + MiniGamesMessages.WORD_DISTANCE.get(omp));
                int borderDistance = getBorderDistance(omp);
                b.addScore(7, " " + borderDistance + " Block" + (borderDistance == 1 ? "" : "s"));
                b.addScore(6, "   ");
                int spawnDistance = getSpawnDistance(omp);
                b.addScore(5, "§9§lSpawn " + MiniGamesMessages.WORD_DISTANCE.get(omp));
                b.addScore(4, " " + spawnDistance + " Block" + (spawnDistance == 1 ? "" : "s") + " ");
                b.addScore(3, "    ");
                b.addScore(2, "§b§lSpawn " + MiniGamesMessages.WORD_LOCATION.get(omp));
                b.addScore(1, " XZ: 0 / 0");
                b.addScore(0, "     ");

                break;
            case ENDING:
                b.addScore(3, "");
                b.addScore(2, "§a§l" + MiniGamesMessages.WORD_WINNER.get(omp));
                b.addScore(1, getFirstPlace().getPlayer().getName());
                b.addScore(0, " ");
                break;
        }

        b.updateAliveTeams();
    }

    @Override
    public ArenaPlayer getArenaPlayer(MiniGamesPlayer omp) {
        return omp.getUhcPlayer();
    }

    @Override
    public void spawnNpcs() {
        Location l = getArena().getLobby();
        Location l1 = new Location(l.getWorld(), l.getX() -12, l.getY() +1, l.getZ() -15);
        Location l2 = new Location(l.getWorld(), l.getX() -9, l.getY() +1, l.getZ() -20);
        Location l3 = new Location(l.getWorld(), l.getX() -3, l.getY() +1, l.getZ() -21);
        Location l4 = new Location(l.getWorld(), l.getX() +2, l.getY() +1, l.getZ() -18);
        Location l5 = new Location(l.getWorld(), l.getX() +8, l.getY() +1, l.getZ() -15);

        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l1, "§f§lSurvivor", TicketType.SURVIVOR_KIT);
            npc.setItemInHand(new ItemStack(Material.COOKED_BEEF));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l2, "§f§lStarter", TicketType.STARTER_KIT);
            npc.setItemInHand(new ItemStack(Material.WOOD_AXE));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l3, "§f§lAppletree", TicketType.APPLETREE_KIT);
            npc.setItemInHand(new ItemStack(Material.APPLE));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l4, "§f§lSpeedster", TicketType.SPEEDSTER_KIT);
            npc.setItemInHand(ItemUtils.addEffect(new ItemStack(Material.POTION), PotionEffectType.SPEED, 3600, 0, false));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l5, "§f§lMiner", TicketType.MINER_KIT);
            npc.setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
        }
    }



    public int getHighestYPos(Location l){
        for(int i = 250; i > 0; i--){
            Block b = l.getWorld().getBlockAt(l.getBlockX(), i, l.getBlockZ());

            if(b != null && !b.isEmpty()){
                return i +1;
            }
        }
        return 100;
    }

    public void teleportToWorldLobby(){
        for(MiniGamesPlayer omp : arena.getPlayers()){
            omp.getPlayer().teleport(arena.getMap().getSpectatorLocation());
        }
    }

    public int getBorderDistance(MiniGamesPlayer omp){
        int distance = 0;
        int x = omp.getPlayer().getLocation().getBlockX();
        int z = omp.getPlayer().getLocation().getBlockZ();

        if(x < 0){
            x = -x;
        }
        if(z < 0){
            z = -z;
        }

        if(x <= z){
            distance = z;
        }
        else{
            distance = x;
        }

        distance = (int) (getBorder() / 2 - distance);

        return distance;
    }

    public int getSpawnDistance(MiniGamesPlayer omp){
        int distance = 0;
        int x = omp.getPlayer().getLocation().getBlockX();
        int z = omp.getPlayer().getLocation().getBlockZ();

        if(x < 0){
            x = -x;
        }
        if(z < 0){
            z = -z;
        }

        if(x <= z){
            distance = z;
        }
        else{
            distance = x;
        }

        return distance;
    }
}
