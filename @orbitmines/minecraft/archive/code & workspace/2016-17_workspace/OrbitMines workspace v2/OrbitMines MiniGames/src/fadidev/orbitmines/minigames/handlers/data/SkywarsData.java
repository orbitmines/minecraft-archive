package fadidev.orbitmines.minigames.handlers.data;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.npc.NPC;
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
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static fadidev.orbitmines.minigames.utils.enums.State.IN_GAME;

/**
 * Created by Fadi on 30-9-2016.
 */
public class SkywarsData extends ArenaData {

    private OrbitMinesMiniGames minigames;
    private List<Chest> lootedChests;
    private List<Block> placedChests;
    private MiniGamesPlayer firstPlace;
    private MiniGamesPlayer secondPlace;
    private MiniGamesPlayer thirdPlace;

    public SkywarsData(Arena arena) {
        super(arena);

        minigames = OrbitMinesMiniGames.getMiniGames();

        this.lootedChests = new ArrayList<>();
        this.placedChests = new ArrayList<>();
    }

    public List<Chest> getLootedChests() {
        return lootedChests;
    }

    public List<Block> getPlacedChests() {
        return placedChests;
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

        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);//TLODO NL
        arena.sendMessage("§6§m--------------------------------------------------");
        arena.sendMessage(" §f§lSkywars §7- §6Results");
        arena.sendMessage("");
        arena.sendMessage(" §61st: " + getFirstPlace().getName());
        arena.sendMessage(" §72nd: " + getSecondPlace().getName());
        arena.sendMessage(" §c3rd: " + getThirdPlace().getName());
        arena.sendMessage("");
        arena.sendMessage("§6§m--------------------------------------------------");

        for(MiniGamesPlayer omp : arena.getDeadPlayers()){
            omp.getPlayer().sendMessage("§c§l+1 Lose");
            omp.getSkywarsPlayer().addLose();
        }

        getFirstPlace().getPlayer().sendMessage("§2§l+1 Win");
        getFirstPlace().getPlayer().sendMessage("§f§l+3 Tickets");
        getFirstPlace().getSkywarsPlayer().addWin();
        getFirstPlace().addMiniGameTickets(3);

        arena.setState(State.ENDING);
        arena.clearScoreboards();
    }

    @Override
    public void start() {
        arena.setMinutes(15);
        arena.setSeconds(0);

        for(MiniGamesPlayer omp : arena.getPlayers()){
            omp.clearInventory();
            omp.getSkywarsPlayer().setRoundKills(0);
            TicketType cage = omp.getSkywarsPlayer().getCage();

            if(cage == TicketType.CACTUS_CAGE || cage == TicketType.HOT_AIR_BALLOON_CAGE || cage == TicketType.DEATH_CAGE || cage == TicketType.WOODEN_CAGE || cage == TicketType.ISLAND_CAGE){
                Block[] blocks = new Block[omp.getSkywarsPlayer().getCageBlocks().size()];
                int index = 1;

                for(Block b : omp.getSkywarsPlayer().getCageBlocks()){
                    blocks[omp.getSkywarsPlayer().getCageBlocks().size() - index] = b;

                    index++;
                }
                for(Block b : blocks){
                    if(b != null && (b.getType() == Material.CACTUS || b.getType() == Material.LADDER || b.getType() == Material.REDSTONE_TORCH_ON || b.getType() == Material.TORCH || b.getType() == Material.LONG_GRASS || b.getType() == Material.RED_ROSE || b.getType() == Material.YELLOW_FLOWER)){
                        b.setType(Material.AIR);
                    }
                }
            }
            for(Block b : omp.getSkywarsPlayer().getCageBlocks()){
                b.setType(Material.AIR);
            }

            Kit kit = omp.getSkywarsPlayer().getKitSelected();
            if(kit != null){
                TicketType type = TicketType.valueOf(kit.getName());
                omp.removeTicket(type);

                kit.setItems(omp.getPlayer());
            }

            omp.updateInventory();
        }

        arena.getMap().getWorld().setTime(0);
        arena.setState(IN_GAME);
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
        arena.sendMessage(" §f§lSkywars §7- §6Info");
        arena.sendMessage("");
        arena.sendMessage(MiniGamesMessages.SW_INFO);
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

        this.lootedChests = new ArrayList<>();
        this.placedChests = new ArrayList<>();
        this.firstPlace = null;
        this.secondPlace = null;
        this.thirdPlace = null;

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
                p.sendMessage(" §f§lSkywars §7- §6" + MiniGamesMessages.WORD_REWARDS.get(omp));
                p.sendMessage("");
                if(getFirstPlace() == omp){
                    p.sendMessage(" §f§l+15 §7(" + MiniGamesMessages.WORD_FIRST_PLACE.get(omp) + ")");
                    amount += 15;
                }
                else if(getSecondPlace() == omp){
                    p.sendMessage(" §f§l+8 §7(" + MiniGamesMessages.WORD_SECOND_PLACE.get(omp) + ")");
                    amount += 8;
                }
                else if(getThirdPlace() == omp){
                    p.sendMessage(" §f§l+4 §7(" + MiniGamesMessages.WORD_THIRD_PLACE.get(omp) + ")");
                    amount += 4;
                }
                else{
                    p.sendMessage(" §f§l+2 §7(" + MiniGamesMessages.WORD_PATIENT.get(omp) + ")");
                    amount += 2;
                }

                int kills = omp.getSkywarsPlayer().getRoundKills();
                if(kills != 0){
                    if(kills == 1){
                        p.sendMessage(" §f§l+" + 3 + " §7(1 Kill)");
                    }
                    else{
                        p.sendMessage(" §f§l+" + 3 * kills + " §7(" + kills + " Kills)");
                    }
                    amount += 3 * kills;
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
            Location l = locations.get(Utils.RANDOM.nextInt(locations.size()));
            arena.getMap().getPlayerSpawns().put(omp, l);

            generateCage(omp, l);

            omp.getPlayer().teleport(l);
        }
    }

    @Override
    public void leave(MiniGamesPlayer omp) {
        if(arena.getState() == IN_GAME || arena.getState() == State.WARMUP){
            if(arena.getState() == State.WARMUP){
                for(Block b : omp.getSkywarsPlayer().getCageBlocks()){
                    b.setType(Material.AIR);
                }
            }

            omp.getPlayer().damage(100D);

            if(arena.getPlayers().size() == 1){
                setSecondPlace(omp);
                ending();
            }
            else if(arena.getPlayers().size() == 2){
                setThirdPlace(omp);
            }

            omp.getSkywarsPlayer().addLose();
        }
    }

    @Override
    public void leaveSpectator(MiniGamesPlayer omp) {
        if(arena.getDeadPlayers().contains(omp))
            omp.getSkywarsPlayer().addLose();
    }

    @Override
    public void starting(MiniGamesPlayer omp) {

    }

    @Override
    public void run(MiniGamesPlayer omp) {
        switch(getArena().getState()){
            case WARMUP:
                TicketType cage = omp.getSkywarsPlayer().getCage();
                if(cage != null){
                    switch(cage){
                        case GRAY_RAINBOW_CAGE:
                            for(Block b : omp.getSkywarsPlayer().getCageBlocks()){
                                if(b.getType() == Material.STAINED_GLASS)
                                    b.setData(Arrays.asList((byte) 0, (byte) 7, (byte) 8, (byte) 15).get(Utils.RANDOM.nextInt(4)));
                            }
                            break;
                        case THE_WOOL_HUT_CAGE:
                            for(Block b : omp.getSkywarsPlayer().getCageBlocks()){
                                if(b.getType() == Material.WOOL)
                                    b.setData(Arrays.asList((byte) 2, (byte) 3, (byte) 4, (byte) 11, (byte) 14).get(Utils.RANDOM.nextInt(4)));
                            }
                            break;
                    }
                }
                break;
            case IN_GAME:
                if(omp.getPlayer().getInventory().contains(Material.COMPASS)){
                    omp.updateTracker(arena.getPlayers());
                }
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
        return omp.getSkywarsPlayer();
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
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l1, "§f§lTank", TicketType.TANK_KIT);
            npc.setHelmet(new ItemStack(Material.LEATHER_HELMET));
            npc.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
            npc.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
            npc.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        }

        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l2, "§f§lSnowGolem", TicketType.SNOWGOLEM_KIT);
            npc.setItemInHand(new ItemStack(Material.SNOW_BALL));
            npc.setHelmet(new ItemStack(Material.PUMPKIN));
        }

        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l3, "§f§lCreeper", TicketType.CREEPER_KIT);
            npc.setItemInHand(new ItemStack(Material.TNT));
        }

        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l4, "§f§lEnchanter", TicketType.ENCHANTER_KIT);
            npc.setItemInHand(new ItemStack(Material.ENCHANTMENT_TABLE));
        }

        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l5, "§f§lEnderman", TicketType.ENDERMAN_KIT);
            npc.setItemInHand(new ItemStack(Material.ENDER_PEARL));
        }
    }

    public void randomChest(Chest c){
        c.getInventory().clear();
        int size = c.getInventory().getSize();

        List<ChestItem> chestItems = minigames.getSwChestItems();
        for(Location l : getArena().getMap().getSWTier2Chests()){
            if(c.getLocation().getBlockX() == l.getBlockX() && c.getLocation().getBlockY() == l.getBlockY() && c.getLocation().getBlockZ() == l.getBlockZ()){
                chestItems = minigames.getSwTier2ChestItems();
            }
        }

        for(int i = 0; i <= new Random().nextInt(7) +1; i++){
            List<ItemStack> items = new ArrayList<>();
            for(ChestItem sgItem : chestItems){
                for(int amount = 0; amount < sgItem.getPercentage(); amount++){
                    items.add(sgItem.getItemStack());
                }
            }
            c.getInventory().setItem(new Random().nextInt(size), items.get(new Random().nextInt(items.size())));
        }

        c.update();
        getLootedChests().add(c);
    }

    public void generateCage(MiniGamesPlayer omp, Location l){
        TicketType cage = omp.getSkywarsPlayer().getCage();
        Block b = l.getBlock();

        List<Block> blocks = new ArrayList<>();

        if(cage != null){
            switch(cage){
                case CACTUS_CAGE:
                    setBlock(blocks, b.getRelative(0, -1, 0), Material.SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.STEP, 9);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.STEP, 9);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.STEP, 9);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.STEP, 9);

                    setBlock(blocks, b.getRelative(1, 0, 0), Material.SAND, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.SAND, 0);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.SAND, 0);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.SAND, 0);
                    setBlock(blocks, b.getRelative(-2, 0, 0), Material.SANDSTONE_STAIRS, 4);
                    setBlock(blocks, b.getRelative(2, 0, 0), Material.SANDSTONE_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, 0, -2), Material.SANDSTONE_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, 0, 2), Material.SANDSTONE_STAIRS, 7);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.STEP, 9);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.STEP, 9);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.STEP, 9);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.STEP, 9);

                    setBlock(blocks, b.getRelative(1, 1, 0), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.CACTUS, 0);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.CACTUS, 0);

                    setBlock(blocks, b.getRelative(1, 4, 0), Material.SAND, 0);
                    setBlock(blocks, b.getRelative(-1, 4, 0), Material.SAND, 0);
                    setBlock(blocks, b.getRelative(0, 4, 1), Material.SAND, 0);
                    setBlock(blocks, b.getRelative(0, 4, -1), Material.SAND, 0);
                    setBlock(blocks, b.getRelative(1, 4, 1), Material.STEP, 1);
                    setBlock(blocks, b.getRelative(-1, 4, 1), Material.STEP, 1);
                    setBlock(blocks, b.getRelative(1, 4, -1), Material.STEP, 1);
                    setBlock(blocks, b.getRelative(-1, 4, -1), Material.STEP, 1);
                    setBlock(blocks, b.getRelative(-2, 4, 0), Material.SANDSTONE_STAIRS, 0);
                    setBlock(blocks, b.getRelative(2, 4, 0), Material.SANDSTONE_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 4, -2), Material.SANDSTONE_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 4, 2), Material.SANDSTONE_STAIRS, 3);

                    setBlock(blocks, b.getRelative(0, 5, 0), Material.SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(1, 5, 0), Material.STEP, 1);
                    setBlock(blocks, b.getRelative(-1, 5, 0), Material.STEP, 1);
                    setBlock(blocks, b.getRelative(0, 5, 1), Material.STEP, 1);
                    setBlock(blocks, b.getRelative(0, 5, -1), Material.STEP, 1);
                    break;
                case CAVE_CAGE:
                    setBlock(blocks, b.getRelative(-1, -2, 0), Material.SMOOTH_STAIRS, 4);
                    setBlock(blocks, b.getRelative(1, -2, 0), Material.SMOOTH_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, -2, -1), Material.SMOOTH_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, -2, 1), Material.SMOOTH_STAIRS, 7);
                    setBlock(blocks, b.getRelative(-2, -2, 0), Material.SMOOTH_STAIRS, 0);
                    setBlock(blocks, b.getRelative(2, -2, 0), Material.SMOOTH_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, -2, -2), Material.SMOOTH_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, -2, 2), Material.SMOOTH_STAIRS, 3);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.DIAMOND_ORE, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-2, -1, 0), Material.SMOOTH_STAIRS, 4);
                    setBlock(blocks, b.getRelative(2, -1, 0), Material.SMOOTH_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, -1, -2), Material.SMOOTH_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, -1, 2), Material.SMOOTH_STAIRS, 7);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.STEP, 13);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.STEP, 13);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.STEP, 13);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.STEP, 13);

                    setBlock(blocks, b.getRelative(-2, 0, 0), Material.SMOOTH_STAIRS, 0);
                    setBlock(blocks, b.getRelative(2, 0, 0), Material.SMOOTH_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 0, -2), Material.SMOOTH_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 0, 2), Material.SMOOTH_STAIRS, 3);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.COBBLE_WALL, 0);

                    setBlock(blocks, b.getRelative(1, 1, 1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.COBBLE_WALL, 0);

                    setBlock(blocks, b.getRelative(1, 2, 1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-2, 2, 0), Material.SMOOTH_STAIRS, 4);
                    setBlock(blocks, b.getRelative(2, 2, 0), Material.SMOOTH_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, 2, -2), Material.SMOOTH_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, 2, 2), Material.SMOOTH_STAIRS, 7);

                    setBlock(blocks, b.getRelative(-2, 3, 0), Material.SMOOTH_STAIRS, 0);
                    setBlock(blocks, b.getRelative(2, 3, 0), Material.SMOOTH_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 3, -2), Material.SMOOTH_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 3, 2), Material.SMOOTH_STAIRS, 3);
                    setBlock(blocks, b.getRelative(1, 3, 1), Material.STEP, 5);
                    setBlock(blocks, b.getRelative(-1, 3, 1), Material.STEP, 5);
                    setBlock(blocks, b.getRelative(1, 3, -1), Material.STEP, 5);
                    setBlock(blocks, b.getRelative(-1, 3, -1), Material.STEP, 5);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.STONE, 0);

                    setBlock(blocks, b.getRelative(0, 4, 0), Material.DIAMOND_ORE, 0);
                    setBlock(blocks, b.getRelative(-2, 4, 0), Material.SMOOTH_STAIRS, 1);
                    setBlock(blocks, b.getRelative(2, 4, 0), Material.SMOOTH_STAIRS, 0);
                    setBlock(blocks, b.getRelative(0, 4, -2), Material.SMOOTH_STAIRS, 3);
                    setBlock(blocks, b.getRelative(0, 4, 2), Material.SMOOTH_STAIRS, 2);
                    setBlock(blocks, b.getRelative(-1, 4, 0), Material.SMOOTH_STAIRS, 0);
                    setBlock(blocks, b.getRelative(1, 4, 0), Material.SMOOTH_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 4, -1), Material.SMOOTH_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 4, 1), Material.SMOOTH_STAIRS, 3);

                    setBlock(blocks, b.getRelative(0, 5, 0), Material.DIAMOND_ORE, 0);
                    setBlock(blocks, b.getRelative(-1, 5, 0), Material.SMOOTH_STAIRS, 0);
                    setBlock(blocks, b.getRelative(1, 5, 0), Material.SMOOTH_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 5, -1), Material.SMOOTH_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 5, 1), Material.SMOOTH_STAIRS, 3);
                    break;
                case DEATH_CAGE:
                    setBlock(blocks, b.getRelative(0, -3, 0), Material.NETHER_FENCE, 0);

                    setBlock(blocks, b.getRelative(0, -2, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, -2, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, -2, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, -2, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, -2, -1), Material.NETHER_FENCE, 0);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.STONE, 6);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.SPRUCE_WOOD_STAIRS, 4);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.SPRUCE_WOOD_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.SPRUCE_WOOD_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.SPRUCE_WOOD_STAIRS, 7);

                    setBlock(blocks, b.getRelative(1, 0, 1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-2, 0, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-2, 0, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, -2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(2, 0, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(2, 0, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, 2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 2), Material.DARK_OAK_FENCE, 0);

                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.REDSTONE_TORCH_ON, 0);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.REDSTONE_TORCH_ON, 0);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.REDSTONE_TORCH_ON, 0);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.REDSTONE_TORCH_ON, 0);
                    setBlock(blocks, b.getRelative(-2, 1, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-2, 1, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 1, -2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 1, -2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(2, 1, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(2, 1, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 1, 2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 2), Material.DARK_OAK_FENCE, 0);
                    setSkull(blocks, b.getRelative(1, 1, 1), Material.SKULL, SkullType.WITHER, BlockFace.NORTH_WEST);
                    setSkull(blocks, b.getRelative(-1, 1, 1), Material.SKULL, SkullType.WITHER, BlockFace.NORTH_EAST);
                    setSkull(blocks, b.getRelative(1, 1, -1), Material.SKULL, SkullType.WITHER, BlockFace.SOUTH_WEST);
                    setSkull(blocks, b.getRelative(-1, 1, -1), Material.SKULL, SkullType.WITHER, BlockFace.SOUTH_EAST);

                    setBlock(blocks, b.getRelative(1, 2, 1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(-2, 2, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-2, 2, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 2, -2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(2, 2, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(2, 2, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 2, 2), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 2), Material.DARK_OAK_FENCE, 0);

                    setBlock(blocks, b.getRelative(0, 3, 0), Material.STONE, 6);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.LOG, 9);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.LOG, 9);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.LOG, 5);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.LOG, 5);
                    setBlock(blocks, b.getRelative(1, 3, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 3, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 3, -1), Material.DARK_OAK_FENCE, 0);

                    setBlock(blocks, b.getRelative(0, 4, 0), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 4, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 4, 1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 4, -1), Material.DARK_OAK_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 4, -1), Material.DARK_OAK_FENCE, 0);

                    setBlock(blocks, b.getRelative(0, 5, 0), Material.DARK_OAK_FENCE, 0);
                    break;
                case ENCHANTER_CAGE:
                    setBlock(blocks, b.getRelative(0, -2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, -2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, -2, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, -2, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, -2, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -2, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, -2, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -2, -1), Material.BARRIER, 0);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.ENCHANTMENT_TABLE, 0);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(2, -1, 0), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(2, -1, 1), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-2, -1, 0), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-2, -1, -1), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(0, -1, -2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(1, -1, -2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(0, -1, 2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 2), Material.BOOKSHELF, 0);

                    setBlock(blocks, b.getRelative(1, 0, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(2, 0, 0), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(2, 0, 1), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-2, 0, 0), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-2, 0, -1), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(0, 0, -2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(1, 0, -2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(0, 0, 2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 2), Material.BOOKSHELF, 0);

                    setBlock(blocks, b.getRelative(1, 1, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(2, 1, 0), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(2, 1, 1), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-2, 1, 0), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-2, 1, -1), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(0, 1, -2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(1, 1, -2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(0, 1, 2), Material.BOOKSHELF, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 2), Material.BOOKSHELF, 0);
                    break;
                case GRAY_RAINBOW_CAGE:
                    for(Block block : Arrays.asList(b.getRelative(0, -1, 0), b.getRelative(1, 0, 0), b.getRelative(-1, 0, 0), b.getRelative(0, 0, 1), b.getRelative(0, 0, -1), b.getRelative(1, 1, 0), b.getRelative(-1, 1, 0), b.getRelative(0, 1, 1), b.getRelative(0, 1, -1), b.getRelative(1, 2, 0), b.getRelative(-1, 2, 0), b.getRelative(0, 2, 1), b.getRelative(0, 2, -1), b.getRelative(1, 3, 0), b.getRelative(-1, 3, 0), b.getRelative(0, 3, 1), b.getRelative(0, 3, -1), b.getRelative(0, 4, 0))){
                        setBlock(blocks, block, Material.STAINED_GLASS, Arrays.asList(0, 7, 8, 15).get(new Random().nextInt(4)));
                    }
                    break;
                case HOT_AIR_BALLOON_CAGE:
                    setBlock(blocks, b.getRelative(0, -2, 0), Material.WOOD_STEP, 8);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.WOOD_STEP, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.WOOD, 0);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.WOOD, 0);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.WOOD, 0);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.WOOD, 0);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.STEP, 15);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.STEP, 15);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.STEP, 15);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.STEP, 15);

                    setBlock(blocks, b.getRelative(1, 0, 0), Material.WOOD, 1);
                    setBlock(blocks, b.getRelative(0, 0, 0), Material.LADDER, 4);
                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.BIRCH_FENCE, 0);

                    setBlock(blocks, b.getRelative(1, 1, 0), Material.WOOD, 1);
                    setBlock(blocks, b.getRelative(0, 1, 0), Material.LADDER, 4);
                    setBlock(blocks, b.getRelative(1, 1, 1), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.BIRCH_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.BIRCH_FENCE, 0);

                    setBlock(blocks, b.getRelative(1, 2, 0), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(0, 2, 0), Material.LADDER, 4);
                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(1, 2, 1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.WOOL, 0);

                    setBlock(blocks, b.getRelative(-2, 3, 0), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-2, 3, -1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-2, 3, 1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(0, 3, -2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-1, 3, -2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(1, 3, -2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(2, 3, 0), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(2, 3, -1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(2, 3, 1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(0, 3, 2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 2), Material.WOOL, 5);
                    setBlock(blocks, b.getRelative(1, 3, 2), Material.WOOL, 5);

                    setBlock(blocks, b.getRelative(-2, 4, 0), Material.STAINED_GLASS, 14);
                    setBlock(blocks, b.getRelative(-2, 4, -1), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(-2, 4, 1), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(0, 4, -2), Material.STAINED_GLASS, 5);
                    setBlock(blocks, b.getRelative(-1, 4, -2), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(1, 4, -2), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(2, 4, 0), Material.STAINED_GLASS, 5);
                    setBlock(blocks, b.getRelative(2, 4, -1), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(2, 4, 1), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(0, 4, 2), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(-1, 4, 2), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(1, 4, 2), Material.STAINED_GLASS, 0);

                    setBlock(blocks, b.getRelative(-2, 5, 0), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(-2, 5, -1), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(-2, 5, 1), Material.STAINED_GLASS, 5);
                    setBlock(blocks, b.getRelative(0, 5, -2), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(-1, 5, -2), Material.STAINED_GLASS, 14);
                    setBlock(blocks, b.getRelative(1, 5, -2), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(2, 5, 0), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(2, 5, -1), Material.STAINED_GLASS, 14);
                    setBlock(blocks, b.getRelative(2, 5, 1), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(0, 5, 2), Material.STAINED_GLASS, 14);
                    setBlock(blocks, b.getRelative(-1, 5, 2), Material.STAINED_GLASS, 0);
                    setBlock(blocks, b.getRelative(1, 5, 2), Material.STAINED_GLASS, 0);

                    setBlock(blocks, b.getRelative(-2, 6, 0), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-2, 6, -1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-2, 6, 1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(0, 6, -2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-1, 6, -2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(1, 6, -2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(2, 6, 0), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(2, 6, -1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(2, 6, 1), Material.WOOL, 14);
                    setBlock(blocks, b.getRelative(0, 6, 2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-1, 6, 2), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(1, 6, 2), Material.WOOL, 0);

                    setBlock(blocks, b.getRelative(0, 7, 0), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(1, 7, 0), Material.WOOL, 5);
                    setBlock(blocks, b.getRelative(-1, 7, 0), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(0, 7, -1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(0, 7, 1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(1, 7, 1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-1, 7, 1), Material.WOOL, 5);
                    setBlock(blocks, b.getRelative(1, 7, -1), Material.WOOL, 0);
                    setBlock(blocks, b.getRelative(-1, 7, -1), Material.WOOL, 14);
                    break;
                case MESA_CAGE:
                    setBlock(blocks, b.getRelative(0, -2, 0), Material.STONE_SLAB2, 8);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.RED_SANDSTONE_STAIRS, 4);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.RED_SANDSTONE_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.RED_SANDSTONE_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.RED_SANDSTONE_STAIRS, 7);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.STONE_SLAB2, 0);

                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.STONE_SLAB2, 8);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.STONE_SLAB2, 8);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.STONE_SLAB2, 8);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.STONE_SLAB2, 8);

                    setBlock(blocks, b.getRelative(1, 1, 1), Material.STAINED_GLASS, 1);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.STAINED_GLASS, 1);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.STAINED_GLASS, 1);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.STAINED_GLASS, 1);

                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.STONE_SLAB2, 0);

                    setBlock(blocks, b.getRelative(0, 3, 0), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.RED_SANDSTONE_STAIRS, 0);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.RED_SANDSTONE_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.RED_SANDSTONE_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.RED_SANDSTONE_STAIRS, 3);
                    setBlock(blocks, b.getRelative(1, 3, 1), Material.STONE_SLAB2, 8);
                    setBlock(blocks, b.getRelative(-1, 3, 1), Material.STONE_SLAB2, 8);
                    setBlock(blocks, b.getRelative(1, 3, -1), Material.STONE_SLAB2, 8);
                    setBlock(blocks, b.getRelative(-1, 3, -1), Material.STONE_SLAB2, 8);

                    setBlock(blocks, b.getRelative(0, 4, 0), Material.STONE_SLAB2, 0);
                    break;
                case NETHER_CAGE:
                    setBlock(blocks, b.getRelative(-1, -2, 0), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(1, -2, 0), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(0, -2, -1), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(0, -2, 1), Material.STEP, 14);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.CAULDRON, 3);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.NETHERRACK, 1);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.NETHERRACK, 1);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.NETHERRACK, 1);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.NETHERRACK, 1);
                    setBlock(blocks, b.getRelative(-2, -1, 0), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(2, -1, 0), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, -1, -2), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, -1, 2), Material.NETHER_BRICK, 0);

                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.FIRE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.FIRE, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.FIRE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.FIRE, 0);
                    setBlock(blocks, b.getRelative(-2, 0, 0), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(2, 0, 0), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, 0, -2), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, 0, 2), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(2, 0, -1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(2, 0, 1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-2, 0, -1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-2, 0, 1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-1, 0, 2), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(1, 0, 2), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-1, 0, -2), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(1, 0, -2), Material.STEP, 6);

                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 1, 1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-2, 1, 0), Material.REDSTONE_TORCH_ON, 0);
                    setBlock(blocks, b.getRelative(2, 1, 0), Material.REDSTONE_TORCH_ON, 0);
                    setBlock(blocks, b.getRelative(0, 1, -2), Material.REDSTONE_TORCH_ON, 0);
                    setBlock(blocks, b.getRelative(0, 1, 2), Material.REDSTONE_TORCH_ON, 0);

                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.NETHER_FENCE, 0);

                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.NETHER_FENCE, 0);

                    setBlock(blocks, b.getRelative(0, 4, 0), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(-1, 4, 0), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 4, 0), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, 4, -1), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, 4, 1), Material.NETHER_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 4, 1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-1, 4, 1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(1, 4, -1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(-1, 4, -1), Material.STEP, 6);

                    setBlock(blocks, b.getRelative(0, 5, 0), Material.NETHER_FENCE, 0);
                    break;
                case SEA_CAGE:
                    setBlock(blocks, b.getRelative(0, -1, 0), Material.SEA_LANTERN, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.PRISMARINE, 1);

                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.PRISMARINE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.PRISMARINE, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.PRISMARINE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.PRISMARINE, 0);

                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(1, 1, 1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.STAINED_GLASS, 3);

                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.STAINED_GLASS, 3);
                    setBlock(blocks, b.getRelative(1, 2, 1), Material.PRISMARINE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.PRISMARINE, 0);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.PRISMARINE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.PRISMARINE, 0);

                    setBlock(blocks, b.getRelative(0, 3, 0), Material.SEA_LANTERN, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.PRISMARINE, 1);
                    break;
                case SLIME_CAGE:
                    setBlock(blocks, b.getRelative(0, -1, 0), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.SLIME_BLOCK, 0);

                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.SLIME_BLOCK, 0);

                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(1, 1, 1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.STAINED_GLASS, 13);

                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.STAINED_GLASS, 13);
                    setBlock(blocks, b.getRelative(1, 2, 1), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.SLIME_BLOCK, 0);

                    setBlock(blocks, b.getRelative(0, 3, 0), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.SLIME_BLOCK, 0);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.SLIME_BLOCK, 0);
                    break;
                case SUN_CAGE:
                    setBlock(blocks, b.getRelative(0, -2, 0), Material.STAINED_GLASS, 1);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.STAINED_GLASS, 1);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.STAINED_GLASS, 4);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.STAINED_GLASS, 4);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.STAINED_GLASS, 4);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.STAINED_GLASS, 4);

                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.STAINED_GLASS_PANE, 14);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.STAINED_GLASS_PANE, 14);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.STAINED_GLASS_PANE, 14);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.STAINED_GLASS_PANE, 14);

                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.STAINED_GLASS_PANE, 1);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.STAINED_GLASS_PANE, 1);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.STAINED_GLASS_PANE, 1);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.STAINED_GLASS_PANE, 1);

                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.STAINED_GLASS_PANE, 14);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.STAINED_GLASS_PANE, 14);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.STAINED_GLASS_PANE, 14);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.STAINED_GLASS_PANE, 14);

                    setBlock(blocks, b.getRelative(0, 3, 0), Material.STAINED_GLASS, 1);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.STAINED_GLASS, 4);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.STAINED_GLASS, 4);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.STAINED_GLASS, 4);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.STAINED_GLASS, 4);

                    setBlock(blocks, b.getRelative(0, 4, 0), Material.STAINED_GLASS, 1);
                    break;
                case THE_END_CAGE:
                    setBlock(blocks, b.getRelative(-1, -2, 0), Material.OBSIDIAN, 0);
                    setBlock(blocks, b.getRelative(1, -2, 0), Material.OBSIDIAN, 0);
                    setBlock(blocks, b.getRelative(0, -2, -1), Material.OBSIDIAN, 0);
                    setBlock(blocks, b.getRelative(0, -2, 1), Material.OBSIDIAN, 0);
                    setBlock(blocks, b.getRelative(1, -2, 1), Material.SMOOTH_STAIRS, 7);
                    setBlock(blocks, b.getRelative(-1, -2, 1), Material.SMOOTH_STAIRS, 7);
                    setBlock(blocks, b.getRelative(1, -2, -1), Material.SMOOTH_STAIRS, 6);
                    setBlock(blocks, b.getRelative(-1, -2, -1), Material.SMOOTH_STAIRS, 6);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.ENDER_STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.ENDER_PORTAL_FRAME, 5);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.ENDER_PORTAL_FRAME, 5);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.ENDER_PORTAL_FRAME, 5);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.ENDER_PORTAL_FRAME, 5);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.ENDER_STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.ENDER_STONE, 0);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.ENDER_STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.ENDER_STONE, 0);

                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.NETHER_FENCE, 0);

                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.NETHER_FENCE, 0);

                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 2, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.NETHER_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.NETHER_FENCE, 0);

                    setBlock(blocks, b.getRelative(0, 3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.SMOOTH_STAIRS, 0);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.SMOOTH_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.SMOOTH_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.SMOOTH_STAIRS, 3);
                    setBlock(blocks, b.getRelative(1, 3, 1), Material.SMOOTH_STAIRS, 3);
                    setBlock(blocks, b.getRelative(-1, 3, 1), Material.SMOOTH_STAIRS, 0);
                    setBlock(blocks, b.getRelative(1, 3, -1), Material.SMOOTH_STAIRS, 2);
                    setBlock(blocks, b.getRelative(-1, 3, -1), Material.SMOOTH_STAIRS, 0);
                    break;
                case THE_WOOL_HUT_CAGE:
                    setBlock(blocks, b.getRelative(0, -1, 0), Material.WOOL, 11);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.SPRUCE_WOOD_STAIRS, 4);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.SPRUCE_WOOD_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.SPRUCE_WOOD_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.SPRUCE_WOOD_STAIRS, 7);

                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.WOOL, 4);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.WOOL, 11);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.WOOL, 14);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.WOOL, 4);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.WOOL, 2);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.WOOL, 11);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.WOOL, 3);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.WOOL, 2);

                    setBlock(blocks, b.getRelative(-2, 1, 0), Material.WOOD_STAIRS, 4);
                    setBlock(blocks, b.getRelative(2, 1, 0), Material.WOOD_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, 1, -2), Material.WOOD_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, 1, 2), Material.WOOD_STAIRS, 7);
                    setBlock(blocks, b.getRelative(-2, 1, -1), Material.WOOL, 11);
                    setBlock(blocks, b.getRelative(-2, 1, 1), Material.WOOL, 4);
                    setBlock(blocks, b.getRelative(2, 1, -1), Material.WOOL, 3);
                    setBlock(blocks, b.getRelative(2, 1, 1), Material.WOOL, 14);
                    setBlock(blocks, b.getRelative(-1, 1, -2), Material.WOOL, 4);
                    setBlock(blocks, b.getRelative(1, 1, -2), Material.WOOL, 14);
                    setBlock(blocks, b.getRelative(-1, 1, 2), Material.WOOL, 2);
                    setBlock(blocks, b.getRelative(1, 1, 2), Material.WOOL, 2);

                    setBlock(blocks, b.getRelative(-2, 2, 0), Material.IRON_FENCE, 0);
                    setBlock(blocks, b.getRelative(2, 2, 0), Material.IRON_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 2, -2), Material.IRON_FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 2, 2), Material.IRON_FENCE, 0);
                    setBlock(blocks, b.getRelative(-2, 2, -1), Material.SPRUCE_WOOD_STAIRS, 0);
                    setBlock(blocks, b.getRelative(-2, 2, 1), Material.SPRUCE_WOOD_STAIRS, 0);
                    setBlock(blocks, b.getRelative(2, 2, -1), Material.SPRUCE_WOOD_STAIRS, 1);
                    setBlock(blocks, b.getRelative(2, 2, 1), Material.SPRUCE_WOOD_STAIRS, 1);
                    setBlock(blocks, b.getRelative(-1, 2, -2), Material.SPRUCE_WOOD_STAIRS, 2);
                    setBlock(blocks, b.getRelative(1, 2, -2), Material.SPRUCE_WOOD_STAIRS, 2);
                    setBlock(blocks, b.getRelative(-1, 2, 2), Material.SPRUCE_WOOD_STAIRS, 3);
                    setBlock(blocks, b.getRelative(1, 2, 2), Material.SPRUCE_WOOD_STAIRS, 3);
                    setBlock(blocks, b.getRelative(1, 2, 1), Material.WOOD_STEP, 13);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.WOOD_STEP, 13);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.WOOD_STEP, 13);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.WOOD_STEP, 13);

                    setBlock(blocks, b.getRelative(1, 3, 1), Material.WOOL, 11);
                    setBlock(blocks, b.getRelative(-1, 3, 1), Material.WOOL, 4);
                    setBlock(blocks, b.getRelative(1, 3, -1), Material.WOOL, 3);
                    setBlock(blocks, b.getRelative(-1, 3, -1), Material.WOOL, 2);

                    setBlock(blocks, b.getRelative(1, 4, 1), Material.WOOL, 2);
                    setBlock(blocks, b.getRelative(-1, 4, 1), Material.WOOL, 11);
                    setBlock(blocks, b.getRelative(1, 4, -1), Material.WOOL, 14);
                    setBlock(blocks, b.getRelative(-1, 4, -1), Material.WOOL, 3);

                    setBlock(blocks, b.getRelative(-2, 4, 0), Material.WOOD_STEP, 8);
                    setBlock(blocks, b.getRelative(2, 4, 0), Material.WOOD_STEP, 8);
                    setBlock(blocks, b.getRelative(0, 4, -2), Material.WOOD_STEP, 8);
                    setBlock(blocks, b.getRelative(0, 4, 2), Material.WOOD_STEP, 8);
                    setBlock(blocks, b.getRelative(-2, 4, -1), Material.WOOD_STEP, 9);
                    setBlock(blocks, b.getRelative(-2, 4, 1), Material.WOOD_STEP, 9);
                    setBlock(blocks, b.getRelative(2, 4, -1), Material.WOOD_STEP, 9);
                    setBlock(blocks, b.getRelative(2, 4, 1), Material.WOOD_STEP, 9);
                    setBlock(blocks, b.getRelative(-1, 4, -2), Material.WOOD_STEP, 9);
                    setBlock(blocks, b.getRelative(1, 4, -2), Material.WOOD_STEP, 9);
                    setBlock(blocks, b.getRelative(-1, 4, 2), Material.WOOD_STEP, 9);
                    setBlock(blocks, b.getRelative(1, 4, 2), Material.WOOD_STEP, 9);

                    setBlock(blocks, b.getRelative(0, 5, 0), Material.WOOD, 0);
                    setBlock(blocks, b.getRelative(1, 5, 1), Material.WOOD_STEP, 0);
                    setBlock(blocks, b.getRelative(-1, 5, 1), Material.WOOD_STEP, 0);
                    setBlock(blocks, b.getRelative(1, 5, -1), Material.WOOD_STEP, 0);
                    setBlock(blocks, b.getRelative(-1, 5, -1), Material.WOOD_STEP, 0);
                    setBlock(blocks, b.getRelative(-1, 5, 0), Material.SPRUCE_WOOD_STAIRS, 0);
                    setBlock(blocks, b.getRelative(1, 5, 0), Material.SPRUCE_WOOD_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 5, -1), Material.SPRUCE_WOOD_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 5, 1), Material.SPRUCE_WOOD_STAIRS, 3);
                    break;
                case WOODEN_CAGE:
                    setBlock(blocks, b.getRelative(0, -1, 0), Material.WOOD, 1);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.DARK_OAK_STAIRS, 4);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.DARK_OAK_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.DARK_OAK_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.DARK_OAK_STAIRS, 7);

                    setBlock(blocks, b.getRelative(1, 0, 1), Material.WOOD_STEP, 5);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.WOOD_STEP, 5);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.WOOD_STEP, 5);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.WOOD_STEP, 5);
                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.LOG, 1);

                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.FENCE, 0);

                    setBlock(blocks, b.getRelative(1, 2, 1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.FENCE, 0);

                    setBlock(blocks, b.getRelative(1, 3, 1), Material.TORCH, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 1), Material.TORCH, 0);
                    setBlock(blocks, b.getRelative(1, 3, -1), Material.TORCH, 0);
                    setBlock(blocks, b.getRelative(-1, 3, -1), Material.TORCH, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.FENCE, 0);

                    setBlock(blocks, b.getRelative(1, 4, 1), Material.WOOD_STEP, 13);
                    setBlock(blocks, b.getRelative(-1, 4, 1), Material.WOOD_STEP, 13);
                    setBlock(blocks, b.getRelative(1, 4, -1), Material.WOOD_STEP, 13);
                    setBlock(blocks, b.getRelative(-1, 4, -1), Material.WOOD_STEP, 13);
                    setBlock(blocks, b.getRelative(-1, 4, 0), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(1, 4, 0), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(0, 4, -1), Material.LOG, 1);
                    setBlock(blocks, b.getRelative(0, 4, 1), Material.LOG, 1);

                    setBlock(blocks, b.getRelative(0, 5, 0), Material.WOOD, 1);
                    setBlock(blocks, b.getRelative(-1, 5, 0), Material.DARK_OAK_STAIRS, 0);
                    setBlock(blocks, b.getRelative(1, 5, 0), Material.DARK_OAK_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, 5, -1), Material.DARK_OAK_STAIRS, 2);
                    setBlock(blocks, b.getRelative(0, 5, 1), Material.DARK_OAK_STAIRS, 3);
                    break;
                case ISLAND_CAGE:
                    setBlock(blocks, b.getRelative(0, -6, 0), Material.COBBLE_WALL, 0);

                    setBlock(blocks, b.getRelative(0, -5, 0), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(1, -5, 1), Material.COBBLE_WALL, 0);

                    setBlock(blocks, b.getRelative(0, -4, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -4, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -4, 0), Material.COBBLE_WALL, 0);

                    setBlock(blocks, b.getRelative(0, -3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -3, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -3, -1), Material.COBBLESTONE_STAIRS, 6);
                    setBlock(blocks, b.getRelative(-1, -3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -3, 1), Material.COBBLESTONE, 0);
                    setBlock(blocks, b.getRelative(1, -3, 0), Material.COBBLESTONE, 0);

                    setBlock(blocks, b.getRelative(0, -2, 0), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(1, -2, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -2, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, 1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(1, -2, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, -1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -2, -1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, -2), Material.COBBLESTONE_STAIRS, 6);
                    setBlock(blocks, b.getRelative(-1, -2, -1), Material.COBBLESTONE_STAIRS, 4);
                    setBlock(blocks, b.getRelative(-2, -2, 0), Material.COBBLESTONE_STAIRS, 4);
                    setBlock(blocks, b.getRelative(-2, -2, 1), Material.COBBLESTONE_STAIRS, 4);
                    setBlock(blocks, b.getRelative(-1, -2, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -2, 2), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, 2), Material.COBBLESTONE, 0);
                    setBlock(blocks, b.getRelative(2, -2, 0), Material.COBBLESTONE_STAIRS, 5);
                    setBlock(blocks, b.getRelative(2, -2, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -2, 2), Material.COBBLESTONE_STAIRS, 5);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(0, -1, -2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-2, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-2, -1, 1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(0, -1, 2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(2, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(2, -1, 1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(1, -1, 2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -1, -2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-2, -1, -1), Material.GRASS, 0);

                    setBlock(blocks, b.getRelative(0, 0, 0), Material.LONG_GRASS, 1);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.LONG_GRASS, 1);
                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.LONG_GRASS, 1);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.LONG_GRASS, 1);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.RED_ROSE, 3);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.LONG_GRASS, 1);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.LONG_GRASS, 1);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.YELLOW_FLOWER, 0);

                    setBlock(blocks, b.getRelative(2, 0, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(2, 0, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 0, 2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 0, 2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(-2, 0, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-2, 0, 0), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(-2, 0, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 0, -2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.FENCE, 0);

                    setBlock(blocks, b.getRelative(2, 1, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(2, 1, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 1, 2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 1, 2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.TORCH, 0);
                    setBlock(blocks, b.getRelative(-2, 1, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-2, 1, 0), Material.TORCH, 0);
                    setBlock(blocks, b.getRelative(-2, 1, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 1, -2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 1, -2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.TORCH, 0);

                    setBlock(blocks, b.getRelative(2, 2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(2, 2, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 2, 2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 2, 2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-2, 2, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-2, 2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-2, 2, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 2, -2), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.BARRIER, 0);
                    break;
                case TROPHEE_CAGE:
                    setBlock(blocks, b.getRelative(0, -5, 0), Material.STONE_SLAB2, 8);

                    setBlock(blocks, b.getRelative(0, -4, 0), Material.PRISMARINE, 2);
                    setBlock(blocks, b.getRelative(1, -4, 0), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(0, -4, 1), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(-1, -4, 0), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(0, -4, -1), Material.PRISMARINE, 1);
                    setBlock(blocks, b.getRelative(1, -4, 1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-1, -4, -1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(1, -4, -1), Material.COBBLE_WALL, 0);
                    setBlock(blocks, b.getRelative(-1, -4, 1), Material.COBBLE_WALL, 0);

                    setBlock(blocks, b.getRelative(0, -3, 0), Material.PRISMARINE, 2);
                    setBlock(blocks, b.getRelative(1, -3, 0), Material.RED_SANDSTONE_STAIRS, 1);
                    setBlock(blocks, b.getRelative(0, -3, 1), Material.RED_SANDSTONE_STAIRS, 3);
                    setBlock(blocks, b.getRelative(-1, -3, 0), Material.RED_SANDSTONE_STAIRS, 0);
                    setBlock(blocks, b.getRelative(0, -3, -1), Material.RED_SANDSTONE_STAIRS, 2);
                    setBlock(blocks, b.getRelative(1, -3, 1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(-1, -3, -1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(1, -3, -1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(-1, -3, 1), Material.STONE_SLAB2, 0);

                    setBlock(blocks, b.getRelative(0, -2, 0), Material.PRISMARINE, 2);

                    setBlock(blocks, b.getRelative(0, -1, 0), Material.PRISMARINE, 2);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.RED_SANDSTONE_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.RED_SANDSTONE_STAIRS, 7);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.RED_SANDSTONE_STAIRS, 4);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.RED_SANDSTONE_STAIRS, 6);

                    setBlock(blocks, b.getRelative(1, 0, 0), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(2, 0, 0), Material.RED_SANDSTONE_STAIRS, 5);
                    setBlock(blocks, b.getRelative(0, 0, 2), Material.RED_SANDSTONE_STAIRS, 7);
                    setBlock(blocks, b.getRelative(-2, 0, 0), Material.RED_SANDSTONE_STAIRS, 6);
                    setBlock(blocks, b.getRelative(0, 0, -2), Material.RED_SANDSTONE_STAIRS, 4);

                    setBlock(blocks, b.getRelative(1, 1, 1), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.RED_SANDSTONE, 0);
                    setBlock(blocks, b.getRelative(2, 1, 0), Material.RED_SANDSTONE, 1);
                    setBlock(blocks, b.getRelative(0, 1, 2), Material.RED_SANDSTONE, 1);
                    setBlock(blocks, b.getRelative(-2, 1, 0), Material.RED_SANDSTONE, 1);
                    setBlock(blocks, b.getRelative(0, 1, -2), Material.RED_SANDSTONE, 1);

                    setBlock(blocks, b.getRelative(2, 2, 0), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(0, 2, 2), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(-2, 2, 0), Material.STONE_SLAB2, 0);
                    setBlock(blocks, b.getRelative(0, 2, -2), Material.STONE_SLAB2, 0);
                    break;
                case OCTOPUS_CAGE:
                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(-1, 0, 3), Material.WALL_SIGN, 4);
                    setBlock(blocks, b.getRelative(-1, 1, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(-1, 2, 6), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, -1, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 3, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, -2, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(0, -1, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(0, 0, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(0, 1, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(0, -3, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(0, -2, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(0, -1, 5), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(0, 0, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 2, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 3, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 2, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 3, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 0, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 1, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(1, 2, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(2, 2, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(2, 3, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(2, 4, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(2, 3, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(2, 4, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(2, 3, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(2, 4, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(2, 5, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 4, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 5, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 6, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 7, 2), Material.COAL_BLOCK, 0);
                    setBlock(blocks, b.getRelative(3, 8, 2), Material.QUARTZ_BLOCK, 0);
                    setBlock(blocks, b.getRelative(3, 9, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 4, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 5, 3), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(3, 6, 3), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(3, 7, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 8, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 9, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 10, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 5, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 6, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(3, 7, 4), Material.COAL_BLOCK, 0);
                    setBlock(blocks, b.getRelative(3, 8, 4), Material.QUARTZ_BLOCK, 0);
                    setBlock(blocks, b.getRelative(3, 9, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 1, -3), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(4, 2, -3), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(4, 1, -2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 1, -1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 2, -1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 2, 0), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 3, 0), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 3, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 4, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 5, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 3, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 4, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 5, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 6, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 7, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 8, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 9, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 10, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 4, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 9, 3), Material.SEA_LANTERN, 0);
                    setBlock(blocks, b.getRelative(4, 10, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 4, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 5, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 6, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 7, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 8, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 9, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 10, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 5, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 6, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 4, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 5, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 2, 7), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 3, 7), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 4, 7), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 0, 8), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 1, 8), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, 2, 8), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, -1, 9), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(4, -1, 10), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(4, 0, 10), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(5, 4, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 5, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 6, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 7, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 8, 2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 0, 3), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(5, 1, 3), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(5, 4, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 5, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 6, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 7, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 8, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 9, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 10, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 3, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 4, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 5, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 6, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 7, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(5, 8, 4), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(6, 3, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(6, 4, 1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(6, 0, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(6, 5, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(6, 6, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(6, 2, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(6, 3, 5), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(6, 5, 6), Material.STEP, 14);
                    setBlock(blocks, b.getRelative(6, 6, 6), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(7, 1, -1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 1, 0), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 2, 0), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 3, 0), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 0, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 1, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 4, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 5, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 2, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(7, 5, 6), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 1, -2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 2, -2), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 3, -2), Material.STEP, 6);
                    setBlock(blocks, b.getRelative(8, 1, -1), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 1, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 2, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 3, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 4, 3), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 2, 7), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 3, 7), Material.STAINED_CLAY, 10);
                    setBlock(blocks, b.getRelative(8, 4, 7), Material.STAINED_CLAY, 10);
                    break;
                case CASTLE_CAGE:
                    setBlock(blocks, b.getRelative(-3, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-3, -2, 1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-3, -1, 1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-2, -1, -2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-2, -2, -1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-2, -1, -1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-2, -4, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-2, -3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-2, -2, 0), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-2, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-2, -3, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-2, -2, 1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-2, -1, 1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-2, -2, 2), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-2, -1, 2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -1, -3), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -2, -2), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-1, -1, -2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -3, -1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -2, -1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-1, -1, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(-1, 0, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(-1, 1, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(-1, 2, -1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 3, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -5, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -4, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 0), Material.GRAVEL, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 0), Material.IRON_FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 0), Material.STEP, 5);
                    setBlock(blocks, b.getRelative(-1, 2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -5, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -4, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -3, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -2, 1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(-1, 0, 1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(-1, 1, 1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(-1, 2, 1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(-1, 3, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(-1, -3, 2), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(-1, -2, 2), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(-1, -1, 3), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(0, -1, -3), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(0, -3, -2), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, -2), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(0, -1, -2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(0, -4, -1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -3, -1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, -1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(0, -1, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, 0, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, 1, -1), Material.STEP, 5);
                    setBlock(blocks, b.getRelative(0, 2, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 3, -1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, -6, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -5, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -4, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, -1, 0), Material.GRAVEL, 0);
                    setBlock(blocks, b.getRelative(0, -4, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -3, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, 1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(0, -1, 1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, 0, 1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(0, 1, 1), Material.STEP, 5);
                    setBlock(blocks, b.getRelative(0, 2, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, 3, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(0, -3, 2), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(0, -2, 2), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(0, -1, 2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(0, -2, 3), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(0, -1, 3), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(1, -2, -2), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(1, -1, -2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(1, -3, -1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -2, -1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(1, -1, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 0, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 1, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 2, -1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 3, -1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(1, -5, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -4, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -2, 0), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(1, -1, 0), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 0, 0), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 1, 0), Material.STEP, 5);
                    setBlock(blocks, b.getRelative(1, 2, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, 3, 0), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, -3, 1), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(1, -2, 1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(1, -1, 1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 0, 1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 1, 1), Material.SMOOTH_BRICK, 0);
                    setBlock(blocks, b.getRelative(1, 2, 1), Material.FENCE, 0);
                    setBlock(blocks, b.getRelative(1, 3, 1), Material.BARRIER, 0);
                    setBlock(blocks, b.getRelative(1, -2, 2), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(1, -1, 2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(1, -1, 3), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(2, -1, -2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(2, -2, -1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(2, -1, -1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(2, -3, 0), Material.STONE, 0);
                    setBlock(blocks, b.getRelative(2, -2, 0), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(2, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(2, -2, 1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(2, -1, 1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(2, -1, 2), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(3, -2, -1), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(3, -1, -1), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(3, -2, 0), Material.DIRT, 0);
                    setBlock(blocks, b.getRelative(3, -1, 0), Material.GRASS, 0);
                    setBlock(blocks, b.getRelative(3, -1, 1), Material.GRASS, 0);
                    break;
                default:
                    for(Block block : Arrays.asList(b.getRelative(0, -1, 0), b.getRelative(1, 0, 0), b.getRelative(-1, 0, 0), b.getRelative(0, 0, 1), b.getRelative(0, 0, -1), b.getRelative(1, 1, 0), b.getRelative(-1, 1, 0), b.getRelative(0, 1, 1), b.getRelative(0, 1, -1), b.getRelative(1, 2, 0), b.getRelative(-1, 2, 0), b.getRelative(0, 2, 1), b.getRelative(0, 2, -1), b.getRelative(0, 3, 0))){
                        setBlock(blocks, block, Material.GLASS, 0);
                    }
                    break;
            }
        }
        else{
            for(Block block : Arrays.asList(b.getRelative(0, -1, 0), b.getRelative(1, 0, 0), b.getRelative(-1, 0, 0), b.getRelative(0, 0, 1), b.getRelative(0, 0, -1), b.getRelative(1, 1, 0), b.getRelative(-1, 1, 0), b.getRelative(0, 1, 1), b.getRelative(0, 1, -1), b.getRelative(1, 2, 0), b.getRelative(-1, 2, 0), b.getRelative(0, 2, 1), b.getRelative(0, 2, -1), b.getRelative(0, 3, 0))){
                setBlock(blocks, block, Material.GLASS, 0);
            }
        }

        omp.getSkywarsPlayer().setCageBlocks(blocks);
    }

    private void setBlock(List<Block> blocks, Block b, Material m, int data){
        blocks.add(b);
        b.setType(m);
        b.setData((byte) data);
    }

    private void setSkull(List<Block> blocks, Block b, Material m, SkullType type, BlockFace blockface){
        blocks.add(b);
        b.setType(m);
        b.setData((byte) 0x1);

        Skull s = (Skull) b.getState();
        s.setSkullType(type);
        s.setRotation(blockface);
        s.update();
    }
}
