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
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Fadi on 30-9-2016.
 */
public class ChickenFightData extends ArenaData {

    private OrbitMinesMiniGames minigames;
    private MiniGamesPlayer firstPlace;
    private MiniGamesPlayer secondPlace;
    private MiniGamesPlayer thirdPlace;

    private boolean jumpBoost;
    private boolean speedBoost;
    private Map<MiniGamesPlayer, List<Block>> chickenBlocks;

    public ChickenFightData(Arena arena) {
        super(arena);

        minigames = OrbitMinesMiniGames.getMiniGames();

        this.jumpBoost = false;
        this.speedBoost = false;
        this.chickenBlocks = new HashMap<>();
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

    public boolean isJumpBoost() {
        return jumpBoost;
    }

    public void setJumpBoost(boolean jumpBoost) {
        this.jumpBoost = jumpBoost;
    }

    public boolean isSpeedBoost() {
        return speedBoost;
    }

    public void setSpeedBoost(boolean speedBoost) {
        this.speedBoost = speedBoost;
    }

    public Map<MiniGamesPlayer, List<Block>> getChickenBlocks() {
        return chickenBlocks;
    }

    public void setChickenBlock(final MiniGamesPlayer omp){
        final Block b = omp.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);

        if(!b.isEmpty() && !b.isLiquid()){
            if(!getChickenBlocks().containsKey(omp)){
                getChickenBlocks().put(omp, new ArrayList<Block>());
            }

            if(!containsChickenBlock(b)){
                getChickenBlocks().get(omp).add(b);

                for(MiniGamesPlayer omplayer : getArena().getAllPlayers()){
                    omplayer.getPlayer().sendBlockChange(b.getLocation(), Material.WOOL, (byte) 0);
                }

                new BukkitRunnable(){
                    public void run(){
                        if(getChickenBlocks().containsKey(omp) && getChickenBlocks().get(omp).contains(b)){
                            getChickenBlocks().get(omp).remove(b);

                            for(MiniGamesPlayer omplayer : getArena().getAllPlayers()){
                                omplayer.getPlayer().sendBlockChange(b.getLocation(), b.getType(), b.getData());
                            }
                        }
                    }
                }.runTaskLater(minigames, 50);
            }
        }
    }

    public MiniGamesPlayer getChickenBlockPlayer(Block b){
        for(MiniGamesPlayer omp : getChickenBlocks().keySet()){
            if(getChickenBlocks().get(omp).contains(b)){
                return omp;
            }
        }
        return null;
    }

    private boolean containsChickenBlock(Block b){
        for(MiniGamesPlayer omp : getChickenBlocks().keySet()){
            if(getChickenBlocks().get(omp).contains(b)){
                return true;
            }
        }
        return false;
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

        getFirstPlace().getChickenFightPlayer().setSecondsSurvived(900 - (arena.getMinutes() * 60 + arena.getSeconds()));

        arena.setMinutes(0);
        arena.setSeconds(15);

        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
        arena.sendMessage("§6§m--------------------------------------------------");
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §f§lChicken Fight §7- §6" + MiniGamesMessages.WORD_RESULTS.get(omp);
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
            omp.getChickenFightPlayer().addLose();
        }

        getFirstPlace().getPlayer().sendMessage("§2§l+1 " + MiniGamesMessages.WORD_WIN.get(getFirstPlace()));
        getFirstPlace().getPlayer().sendMessage("§f§l+2 Tickets");
        getFirstPlace().getChickenFightPlayer().addWin();
        getFirstPlace().addMiniGameTickets(2);

        arena.setState(State.ENDING);
        arena.clearScoreboards();
    }

    @Override
    public void start() {
        arena.setMinutes(15);
        arena.setSeconds(0);

        for(MiniGamesPlayer omp : arena.getPlayers()){
            omp.clearInventory();
            omp.getChickenFightPlayer().setRoundKills(0);

            Kit kit = omp.getChickenFightPlayer().getKitSelected();
            if(kit != null){
                TicketType type = TicketType.valueOf(kit.getName());
                omp.removeTicket(type);

                kit.setItems(omp.getPlayer());

                if(kit.getName().equals(TicketType.CHICKEN_MAMA_KIT.toString())){
                    omp.disguiseAsMob(EntityType.CHICKEN, false, "§a" + omp.getPlayer().getName(), getArena().getAllPlayerEntities());
                }
                else if(kit.getName().equals(TicketType.BABY_CHICKEN_KIT.toString())){
                    omp.disguiseAsMob(EntityType.CHICKEN, true, "§a" + omp.getPlayer().getName(), getArena().getAllPlayerEntities());
                }
                else if(kit.getName().equals(TicketType.HOT_WING_KIT.toString())){
                    omp.disguiseAsMob(EntityType.CHICKEN, false, "§a" + omp.getPlayer().getName(), getArena().getAllPlayerEntities());
                    omp.getDisguise().setFireTicks(Integer.MAX_VALUE);
                }
                else if(kit.getName().equals(TicketType.CHICKEN_WARLORD_KIT.toString())){
                    omp.disguiseAsMob(EntityType.IRON_GOLEM, false, "§a" + omp.getPlayer().getName(), getArena().getAllPlayerEntities());
                }
                else if(kit.getName().equals(TicketType.CHICKEN_KIT.toString())){
                    omp.disguiseAsMob(EntityType.SHEEP, false, "§a" + omp.getPlayer().getName(), getArena().getAllPlayerEntities());
                }

                if(isJumpBoost())
                    omp.addPotionEffect(PotionEffectType.JUMP, 100000, 4);
                if(isSpeedBoost())
                    omp.addPotionEffect(PotionEffectType.SPEED, 100000, 4);
            }

            omp.updateInventory();
        }

        arena.getMap().getWorld().setTime(20000);
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
        arena.sendMessage(" §f§lChicken Fight §7- §6Info");
        arena.sendMessage("");
        arena.sendMessage(MiniGamesMessages.CF_INFO);
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

        this.firstPlace = null;
        this.secondPlace = null;
        this.thirdPlace = null;
        this.jumpBoost = false;
        this.speedBoost = false;
        this.chickenBlocks = new HashMap<>();

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
                p.sendMessage(" §f§lChicken Fight §7- §6" + MiniGamesMessages.WORD_REWARDS.get(omp));
                p.sendMessage("");
                if(getFirstPlace() == omp){
                    p.sendMessage(" §f§l+10 §7(" + MiniGamesMessages.WORD_FIRST_PLACE.get(omp) + ")");
                    amount += 15;
                }
                else if(getSecondPlace() == omp){
                    p.sendMessage(" §f§l+5 §7(" + MiniGamesMessages.WORD_SECOND_PLACE.get(omp) + ")");
                    amount += 8;
                }
                else if(getThirdPlace() == omp){
                    p.sendMessage(" §f§l+3 §7(" + MiniGamesMessages.WORD_THIRD_PLACE.get(omp) + ")");
                    amount += 4;
                }
                else{
                    p.sendMessage(" §f§l+2 §7(" + MiniGamesMessages.WORD_PATIENT.get(omp) + ")");
                    amount += 2;
                }

                int kills = omp.getChickenFightPlayer().getRoundKills();
                if(kills != 0){
                    if(kills == 1){
                        p.sendMessage(" §f§l+" + 3 + " §7(1 Kill)");
                    }
                    else{
                        p.sendMessage(" §f§l+" + 3 * kills + " §7(" + kills + " Kills)");
                    }
                    amount += 3 * kills;
                }

                int totalseconds = omp.getChickenFightPlayer().getSecondsSurvived();
                int seconds = totalseconds % 60;
                int minutes = 0;
                int survivereward = totalseconds / 25;

                if(totalseconds != seconds){
                    minutes = (totalseconds - seconds) / 60;
                }

                if(survivereward != 0){
                    p.sendMessage(" §f§l+" + survivereward + " §7(" + MiniGamesMessages.SURVIVED.get(omp, minutes + "", seconds + "") + ")");
                    amount += survivereward;
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
        if(getArena().getState() == State.IN_GAME || getArena().getState() == State.WARMUP){
            omp.getPlayer().damage(100D);

            if(getArena().getPlayers().size() == 1){
                setSecondPlace(omp);
                ending();
            }
            else if(getArena().getPlayers().size() == 2){
               setThirdPlace(omp);
            }

            omp.getChickenFightPlayer().addLose();

            if(omp.isDisguised())
                omp.unDisguise();
        }
    }

    @Override
    public void leaveSpectator(MiniGamesPlayer omp) {
        if(getArena().getDeadPlayers().contains(omp))
            omp.getChickenFightPlayer().addLose();
    }

    @Override
    public void starting(MiniGamesPlayer omp) {
        if(omp.getChickenFightPlayer().getKitSelected() == null)
            omp.getChickenFightPlayer().setKitSelected(TicketType.CHICKEN_MAMA_KIT.getKit());
    }

    @Override
    public void run(MiniGamesPlayer omp) {
        if(arena.getState() == State.IN_GAME){
            if(omp.getPlayer().getInventory().contains(Material.COMPASS))
                omp.updateTracker(arena.getPlayers());
        }
    }

    @Override
    public void run(State state) {
        switch(state){
            case IN_GAME:
                for(MiniGamesPlayer omp : arena.getPlayers()){
                    if(!arena.isSpectator(omp) && omp.getChickenFightPlayer().getKitSelected().getName().equals(TicketType.CHICKEN_KIT.toString())){
                        setChickenBlock(omp);
                    }

                    MiniGamesPlayer ompD = getChickenBlockPlayer(omp.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN));
                    if(ompD != null && omp != ompD && !arena.isSpectator(ompD) && !omp.onCooldown(MiniGamesCooldowns.WOOL_TRAIL)){
                        omp.getPlayer().damage(Arrays.asList(0.5D, 1D).get(Utils.RANDOM.nextInt(2)), ompD.getPlayer());
                        omp.addPotionEffect(PotionEffectType.SLOW, 60, 2);

                        omp.resetCooldown(MiniGamesCooldowns.WOOL_TRAIL);
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
        return omp.getChickenFightPlayer();
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
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l1, "§f§lChicken Mama", TicketType.CHICKEN_MAMA_KIT);
            npc.setItemInHand(new ItemStack(Material.FEATHER));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l2, "§f§lBaby Chicken", TicketType.BABY_CHICKEN_KIT);
            npc.setItemInHand(new ItemStack(Material.EGG));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l3, "§f§lHot Wing", TicketType.HOT_WING_KIT);
            npc.setItemInHand(new ItemStack(Material.FIREBALL));
            npc.setFire(true);
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l4, "§f§lChicken Warlord", TicketType.CHICKEN_WARLORD_KIT);
            npc.setHelmet(new ItemStack(Material.IRON_HELMET));
            npc.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            npc.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            npc.setBoots(new ItemStack(Material.IRON_BOOTS));
            npc.setItemInHand(new ItemStack(Material.IRON_INGOT));
        }
        {
            NPC npc = arena.spawnNpc(Mob.ZOMBIE, l5, "§f§l'Chicken'", TicketType.CHICKEN_KIT);
            npc.setItemInHand(new ItemStack(Material.WOOL));
        }
    }
}
