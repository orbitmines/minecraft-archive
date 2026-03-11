package fadidev.orbitmines.minigames.handlers.data;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.handlers.npc.NPC;
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
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fadidev.orbitmines.minigames.utils.enums.State.ENDING;

/**
 * Created by Fadi on 30-9-2016.
 */
public class SurvivalGamesData extends ArenaData {

    private OrbitMinesMiniGames minigames;
    private MiniGamesPlayer firstPlace;
    private MiniGamesPlayer secondPlace;
    private MiniGamesPlayer thirdPlace;

    private List<Chest> lootedChests;
    private boolean inDeathMatch;
    private boolean doubleLoot;
    private boolean enablePotions;

    public SurvivalGamesData(Arena arena) {
        super(arena);

        minigames = OrbitMinesMiniGames.getMiniGames();

        this.lootedChests = new ArrayList<>();
        this.inDeathMatch = false;
        this.doubleLoot = false;
        this.enablePotions = false;
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

    public List<Chest> getLootedChests() {
        return lootedChests;
    }

    public boolean isInDeathMatch() {
        return inDeathMatch;
    }

    public void setInDeathMatch(boolean inDeathMatch) {
        this.inDeathMatch = inDeathMatch;
    }

    public boolean isDoubleLoot() {
        return doubleLoot;
    }

    public void setDoubleLoot(boolean doubleLoot) {
        this.doubleLoot = doubleLoot;
    }

    public boolean isEnablePotions() {
        return enablePotions;
    }

    public void setEnablePotions(boolean enablePotions) {
        this.enablePotions = enablePotions;
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
            List<MiniGamesPlayer> players = new ArrayList<>();
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
                return " §f§lSurvival Games §7- §6" + MiniGamesMessages.WORD_RESULTS.get(omp);
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
            omp.getPlayer().sendMessage("§c§l+1 " + MiniGamesMessages.WORD_LOSE.get(omp));
            omp.getSurvivalGamesPlayer().addLose();
        }

        getFirstPlace().getPlayer().sendMessage("§2§l+1 " + MiniGamesMessages.WORD_WIN.get(getFirstPlace()));
        getFirstPlace().getPlayer().sendMessage("§f§l+3 Tickets");
        getFirstPlace().getSurvivalGamesPlayer().addWin();
        getFirstPlace().addMiniGameTickets(3);

        arena.setState(ENDING);
        arena.clearScoreboards();
    }

    @Override
    public void start() {
        arena.setMinutes(20);
        arena.setSeconds(0);

        for(MiniGamesPlayer omp : arena.getPlayers()){
            omp.clearInventory();
            omp.getSurvivalGamesPlayer().setRoundKills(0);

            Kit kit = omp.getSurvivalGamesPlayer().getKitSelected();
            if(kit != null){
                TicketType type = TicketType.valueOf(kit.getName());
                omp.removeTicket(type);

                kit.setItems(omp.getPlayer());
            }

            omp.updateInventory();
        }

        arena.getMap().getWorld().setTime(0);
        arena.setState(State.IN_GAME);
        arena.clearScoreboards();
    }

    @Override
    public void warmup() {
        arena.setMinutes(0);
        arena.setSeconds(15);

        teleportToArena();
        for(MiniGamesPlayer omp : arena.getPlayers()){
            omp.clearInventory();
            omp.updateInventory();

            for(MiniGamesPlayer omplayer : arena.getPlayers()){
                omp.getPlayer().showPlayer(omplayer.getPlayer());
            }
        }

        arena.getMap().getWorld().setTime(20000);
        WorldUtils.removeEntities(arena.getMap().getWorld());

        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
        arena.sendMessage("§6§m--------------------------------------------------");
        arena.sendMessage(" §f§lSurvival Games §7- §6Info");
        arena.sendMessage("");
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return MiniGamesMessages.SG_INFO.get(omp);
            }
        });
        arena.sendMessage("");
        arena.sendMessage(" §7Map: §6" + arena.getMap().getMapName());
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §7" + MiniGamesMessages.WORD_BUILDERS.get(omp) + ": §6" +  arena.getMap().getBuilder();
            }
        });
        arena.sendMessage("§6§m--------------------------------------------------");

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

        this.lootedChests.clear();
        this.inDeathMatch = false;
        this.firstPlace = null;
        this.secondPlace = null;
        this.thirdPlace = null;
        this.doubleLoot = false;
        this.enablePotions = false;

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
                p.sendMessage(" §f§lSurvival Games §7- §6" + MiniGamesMessages.WORD_REWARDS.get(omp));
                p.sendMessage("");
                if(getFirstPlace() == omp){
                    p.sendMessage(" §f§l+25 §7(" + MiniGamesMessages.WORD_FIRST_PLACE.get(omp) + ")");
                    amount += 25;
                }
                else if(getSecondPlace() == omp){
                    p.sendMessage(" §f§l+15 §7(" + MiniGamesMessages.WORD_SECOND_PLACE.get(omp) + ")");
                    amount += 15;
                }
                else if(getThirdPlace() == omp){
                    p.sendMessage(" §f§l+10 §7(" + MiniGamesMessages.WORD_THIRD_PLACE.get(omp) + ")");
                    amount += 10;
                }
                else{
                    p.sendMessage(" §f§l+5 §7(" + MiniGamesMessages.WORD_PATIENT.get(omp) + ")");
                    amount += 5;
                }

                int kills = omp.getSurvivalGamesPlayer().getRoundKills();
                if(kills != 0){
                    if(kills == 1){
                        p.sendMessage(" §f§l+" + 4 + " §7(1 Kill)");
                    }
                    else{
                        p.sendMessage(" §f§l+" + 4 * kills + " §7(" + kills + " Kills)");
                    }
                    amount += 4 * kills;
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
        MiniGamesMap map = getArena().getMap();

        for(MiniGamesPlayer omp : arena.getPlayers()){
            List<Location> locations = new ArrayList<>();
            for(Location l : map.getSpawns()){
                if(!map.getPlayerSpawns().containsValue(l)){
                    locations.add(l);
                }
            }
            Location l = locations.get(new Random().nextInt(locations.size()));
            arena.getMap().getPlayerSpawns().put(omp, l);

            omp.getPlayer().teleport(l);
        }
    }

    @Override
    public void leave(MiniGamesPlayer omp) {
        if(arena.getState() == State.WAITING || arena.getState() == State.STARTING){
            if(omp.getSurvivalGamesPlayer().isDoubleLoot()){
                omp.getSurvivalGamesPlayer().setDoubleLoot(false);
                omp.addTicketAmount(TicketType.DOUBLE_LOOT_PLAYER, 1);
            }
            if(omp.getSurvivalGamesPlayer().isEnablePotions()){
                omp.getSurvivalGamesPlayer().setEnablePotions(false);
                omp.addTicketAmount(TicketType.ENABLE_POTIONS_PLAYER, 1);
            }
        }
        else if(arena.getState() == State.IN_GAME || arena.getState() == State.WARMUP){
            omp.getPlayer().damage(100D);

            if(arena.getPlayers().size() == 1){
                setSecondPlace(omp);
                ending();
            }
            else if(arena.getPlayers().size() == 2){
                setThirdPlace(omp);
            }

            omp.getSurvivalGamesPlayer().addLose();

            if(!isInDeathMatch() && arena.getMinutes() != 0 && arena.getPlayers().size() != 1 && arena.getPlayers().size() <= 3){
                arena.setMinutes(1);
                arena.setSeconds(0);

                arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
                arena.sendMessage(MiniGamesMessages.SG_DEATHMATCH_STARTING_IN, "1", "0");
            }
        }
    }

    @Override
    public void leaveSpectator(MiniGamesPlayer omp) {
        if(arena.getDeadPlayers().contains(omp))
            omp.getSurvivalGamesPlayer().addLose();
    }

    @Override
    public void starting(MiniGamesPlayer omp) {
       
    }

    @Override
    public void run(MiniGamesPlayer omp) {
        Player p = omp.getPlayer();

        switch(getArena().getState()){
            case WARMUP:
                Location l = arena.getMap().getPlayerSpawns().get(omp);

                if(p.getLocation().distance(l) >= 1.3){
                    p.teleport(l);
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F, 0.1F);

                    Title t = new Title("", MiniGamesMessages.STAY_ON_PLATFORM.get(omp), 0, 30, 0);
                    t.send(p);
                }
                break;
            case IN_GAME:
                if(p.getInventory().contains(Material.COMPASS))
                    omp.updateTracker(arena.getPlayers());

                omp.getSurvivalGamesPlayer().updateLeatherColor();
                break;
        }
    }

    @Override
    public void run(State state) {
        switch(state){
            case WARMUP:
                if(arena.getMinutes() == 0 && arena.getSeconds() <= 10)
                    arena.getMap().getWorld().strikeLightningEffect(arena.getMap().getSpectatorLocation());
                break;
            case IN_GAME:
                if(arena.getMinutes() == 10 && arena.getSeconds() == 0)
                    restockChests();
                else if(arena.getMinutes() == 1 && arena.getSeconds() == 0 || arena.getMinutes() == 0 && (arena.getSeconds() == 30 || arena.getSeconds() <= 10)){
                    arena.playSound(Sound.UI_BUTTON_CLICK, 5, 1);
                    if(!isInDeathMatch()){
                        arena.sendMessage(MiniGamesMessages.SG_DEATHMATCH_STARTING_IN, arena.getMinutes() + "", arena.getSeconds() + "");
                    }
                    else{
                        arena.sendMessage(MiniGamesMessages.SG_DEATHMATCH_ENDING_IN, arena.getMinutes() + "", arena.getSeconds() + "");
                    }
                }
                break;
        }
    }

    @Override
    public void updateScoreboard(MiniGamesScoreboard b) {
        OMPlayer omp = b.getOMPlayer();

        switch(arena.getState()){
            case WARMUP:
                b.addScore(0, "");

                break;
            case IN_GAME:
                b.addScore(9, "");
                if(!isInDeathMatch())
                    b.addScore(8, "§d§lDeathmatch " + MiniGamesMessages.WORD_IN.get(omp));
                else
                    b.addScore(8, "§6§l" + MiniGamesMessages.WORD_TIME_LEFT.get(omp));

                b.addScore(7, " " + arena.getMinutes() + "m " + arena.getSeconds() + "s");
                b.addScore(6, " ");
                b.addScore(5, "§a§l" + MiniGamesMessages.WORD_ALIVE.get(omp));
                b.addScore(4, " " + arena.getPlayers().size());
                b.addScore(3, "  ");
                b.addScore(2, "§c§l" + MiniGamesMessages.WORD_DEAD.get(omp));
                b.addScore(1, " " + arena.getDeadPlayers().size()  + " ");
                b.addScore(0, "   ");

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
        return omp.getSurvivalGamesPlayer();
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
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l1, "§f§lRunner", TicketType.RUNNER_KIT);
            npc.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l2, "§f§lFigher", TicketType.FIGHTER_KIT);
            npc.setItemInHand(new ItemStack(Material.STONE_AXE));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l3, "§f§lArcher", TicketType.ARCHER_KIT);
            npc.setItemInHand(new ItemStack(Material.BOW));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l4, "§f§lWarrior", TicketType.WARRIOR_KIT);
            npc.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
            npc.setBoots(new ItemStack(Material.IRON_BOOTS));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l5, "§f§lBomber", TicketType.BOMBER_KIT);
            npc.setItemInHand(new ItemStack(Material.TNT));
        }
    }

    public void randomChest(MiniGamesPlayer omp, Chest c){
        c.getInventory().clear();
        int size = c.getInventory().getSize();

        int extraItems = isDoubleLoot() || omp.getSurvivalGamesPlayer().isDoubleLoot() ? 6 : 0;

        for(int i = 0; i <= extraItems + new Random().nextInt(6) +1; i++){
            List<ItemStack> items = new ArrayList<>();
            for(ChestItem sgItem : minigames.getSgChestItems()){
                for(int amount = 0; amount < sgItem.getPercentage(); amount++){
                    items.add(sgItem.getItemStack());
                }
            }
            if(isEnablePotions() || omp.getSurvivalGamesPlayer().isEnablePotions()){
                for(ChestItem sgItem : minigames.getSgChestPotions()){
                    for(int amount = 0; amount < sgItem.getPercentage(); amount++){
                        items.add(sgItem.getItemStack());
                    }
                }
            }
            c.getInventory().setItem(new Random().nextInt(size), items.get(new Random().nextInt(items.size())));
        }

        c.update();
        getLootedChests().add(c);
    }

    public void restockChests(){
        arena.sendMessage(MiniGamesMessages.SG_RESTOCK_CHEST);
        arena.playSound(Sound.ENTITY_PLAYER_LEVELUP, 5, 1);

        getLootedChests().clear();
    }

    public void deathmatch(){
        restockChests();
        setInDeathMatch(true);

        arena.setMinutes(3);
        arena.setSeconds(0);

        for(MiniGamesPlayer omp : arena.getPlayers()){
            if(omp.getPlayer().getVehicle() != null){
                omp.getPlayer().leaveVehicle();
            }

            omp.getPlayer().teleport(arena.getMap().getPlayerSpawns().get(omp));
        }
        for(MiniGamesPlayer omp : arena.getSpectators()){
            omp.getPlayer().teleport(arena.getMap().getSpectatorLocation());
        }

        WorldBorder border = arena.getMap().getSpectatorLocation().getWorld().getWorldBorder();
        border.setCenter(arena.getMap().getSpectatorLocation());
        border.setSize(80);
    }
}
