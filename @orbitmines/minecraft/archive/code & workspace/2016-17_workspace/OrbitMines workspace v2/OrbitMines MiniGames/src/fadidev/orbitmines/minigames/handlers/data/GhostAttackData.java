package fadidev.orbitmines.minigames.handlers.data;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.*;
import fadidev.orbitmines.minigames.handlers.players.ArenaPlayer;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.inventories.KitInv;
import fadidev.orbitmines.minigames.utils.enums.State;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Fadi on 30-9-2016.
 */
public class GhostAttackData extends ArenaData {

    private OrbitMinesMiniGames minigames;
    private int ghostRevealedIn;
    private boolean ghostRevealed;
    private MiniGamesPlayer ghost;
    private MiniGamesPlayer ghostKiller;
    private boolean ghostDead;

    public GhostAttackData(Arena arena) {
        super(arena);

        minigames = OrbitMinesMiniGames.getMiniGames();

        this.ghostRevealedIn = 3;
        this.ghostRevealed = false;
        this.ghostDead = false;
    }

    public int getRevealedIn() {
        return ghostRevealedIn;
    }
    public void setRevealedIn(int ghostRevealedIn) {
        this.ghostRevealedIn = ghostRevealedIn;
    }
    public void tickRevealedIn(){
        ghostRevealedIn--;

        if(ghostRevealedIn == 0){
            ghostRevealedIn = 3;

            if(Utils.RANDOM.nextBoolean())
                revealGhost();
        }
    }

    public boolean isRevealed(){
        return ghostRevealed;
    }

    public void revealGhost(){
        ghostRevealed = true;
        for(MiniGamesPlayer omp : arena.getAllPlayers()){
            Scoreboard b = omp.getPlayer().getScoreboard();

            if(b != null){
                Team t = b.getTeam("PlayersMG");
                t.setCanSeeFriendlyInvisibles(true);
            }
        }

        new BukkitRunnable(){
            public void run(){
                ghostRevealed = false;
                for(MiniGamesPlayer omp : arena.getAllPlayers()){
                    Scoreboard b = omp.getPlayer().getScoreboard();

                    if(b != null){
                        Team t = b.getTeam("PlayersMG");
                        t.setCanSeeFriendlyInvisibles(false);
                    }
                }
            }
        }.runTaskLater(minigames, 10);
    }

    public MiniGamesPlayer getGhost() {
        return ghost;
    }
    
    public void setGhost(MiniGamesPlayer ghost) {
        this.ghost = ghost;
    }
    
    public boolean isGhost(MiniGamesPlayer omp){
        return this.ghost == omp;
    }

    public MiniGamesPlayer getGhostKiller() {
        return ghostKiller;
    }
    public void setGhostKiller(MiniGamesPlayer ghostKiller) {
        this.ghostKiller = ghostKiller;
    }

    public boolean isGhostDead() {
        return ghostDead;
    }

    public void setGhostDead(boolean ghostDead) {
        this.ghostDead = ghostDead;
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

        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
        arena.sendMessage("§6§m--------------------------------------------------");
        arena.sendMessage(new Arena.ArenaMessage() {
            @Override
            public String getMessage(MiniGamesPlayer omp) {
                return " §f§lGhost Attack §7- §6" + MiniGamesMessages.WORD_RESULTS.get(omp);
            }
        });
        arena.sendMessage("");
        if(isGhostDead()){
            if(getGhostKiller() != null){
                arena.sendMessage(MiniGamesMessages.GA_GHOST_KILLED_BY, getGhostKiller().getName());
                arena.sendMessage("");
            }

            if(arena.getPlayers().size() > 1){
                String winners = "";
                for(MiniGamesPlayer omp : arena.getPlayers()){
                    if(!isGhost(omp)){
                        if(winners.equals("")){
                            winners = omp.getName();
                        }
                        else{
                            winners += "§7, " + omp.getName();
                        }
                    }
                }
                final String winnersString = winners;
                arena.sendMessage(new Arena.ArenaMessage() {
                    @Override
                    public String getMessage(MiniGamesPlayer omp) {
                        return " §6" + MiniGamesMessages.WORD_WINNER.get(omp) + "s: " + winnersString;
                    }
                });
            }
            else{
                arena.sendMessage(new Arena.ArenaMessage() {
                    @Override
                    public String getMessage(MiniGamesPlayer omp) {
                        return " §6" + MiniGamesMessages.WORD_WINNER.get(omp) + ": " + arena.getPlayers().get(0).getName();
                    }
                });
            }
        }
        else{
            arena.sendMessage(MiniGamesMessages.GA_GHOST_KILLED_ALL);
            arena.sendMessage("");
            arena.sendMessage(new Arena.ArenaMessage() {
                @Override
                public String getMessage(MiniGamesPlayer omp) {
                    return " §6" + MiniGamesMessages.WORD_WINNER.get(omp) + ": " + getGhost().getName();
                }
            });
        }
        arena.sendMessage("");
        arena.sendMessage("§6§m--------------------------------------------------");

        for(MiniGamesPlayer omp : arena.getDeadPlayers()){
            omp.getPlayer().sendMessage("§c§l+1 " + MiniGamesMessages.WORD_LOSE.get(omp));
            omp.getGhostAttackPlayer().addLose();
        }

        if(isGhostDead()){
            for(MiniGamesPlayer omp : arena.getPlayers()){
                omp.getPlayer().sendMessage("§2§l+1 " + MiniGamesMessages.WORD_WIN.get(omp));
                omp.getPlayer().sendMessage("§f§l+3 Tickets");
                omp.getGhostAttackPlayer().addWin();
                omp.addMiniGameTickets(3);
            }
        }
        else{
            getGhost().getPlayer().sendMessage("§2§l+1 " + MiniGamesMessages.WORD_WIN.get(getGhost()));
            getGhost().getPlayer().sendMessage("§f§l+4 Tickets");
            getGhost().getGhostAttackPlayer().addGhostWin();
            getGhost().addMiniGameTickets(4);
        }

        arena.setState(State.ENDING);
        arena.clearScoreboards();
    }

    @Override
    public void start() {
        arena.setMinutes(15);
        arena.setSeconds(0);

        MiniGamesPlayer ompG = arena.getPlayers().get(Utils.RANDOM.nextInt(arena.getPlayers().size()));
        setGhost(ompG);

        for(MiniGamesPlayer omp : arena.getPlayers()){
            omp.clearInventory();
            omp.getGhostAttackPlayer().setRoundKills(0);

            if(ompG == omp){
                Kit kit = Kit.getKit("GhostKit");
                kit.setItems(omp.getPlayer());
            }
            else{
                Kit kit = omp.getGhostAttackPlayer().getKitSelected();
                if(kit != null){
                    TicketType type = TicketType.valueOf(kit.getName());
                    omp.removeTicket(type);

                    kit.setItems(omp.getPlayer());
                }
            }

            omp.updateInventory();
        }

        arena.sendMessage(MiniGamesMessages.GA_IS_GHOST, ompG.getColorName());

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
        arena.sendMessage(" §f§lGhost Attack §7- §6Info");
        arena.sendMessage("");
        arena.sendMessage(MiniGamesMessages.GA_INFO_1);
        arena.sendMessage(MiniGamesMessages.GA_INFO_2);
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

        this.ghostRevealed = false;
        this.ghost = null;
        this.ghostKiller = null;
        this.ghostDead = false;

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
                p.sendMessage(" §f§lGhost Attack §7- §6" + MiniGamesMessages.WORD_REWARDS.get(omp));
                p.sendMessage("");
                if(isGhost(omp) && !isGhostDead()){
                    p.sendMessage(" §f§l+20 §7(" + MiniGamesMessages.GA_WON_AS_GHOST.get(omp) + ")");
                    amount += 20;
                }
                else if(getGhostKiller() == omp){
                    p.sendMessage(" §f§l+15 §7(" + MiniGamesMessages.GA_KILLED_GHOST.get(omp) + ")");
                    amount += 15;
                }
                else{
                    p.sendMessage(" §f§l+5 §7(" + MiniGamesMessages.WORD_PATIENT.get(omp) + ")");
                    amount += 5;
                }

                if(!isGhost(omp)){
                    int totalseconds = omp.getGhostAttackPlayer().getSecondsSurvived();
                    int seconds = totalseconds % 60;
                    int minutes = 0;
                    int survivereward = totalseconds / 25;

                    if(totalseconds != seconds){
                        minutes = (totalseconds - seconds) / 60;
                    }

                    if(survivereward != 0){
                        p.sendMessage(" §f§l+" + survivereward + " §7(" + MiniGamesMessages.SURVIVED.get(omp, minutes + "", seconds + "")  + ")");
                        amount += survivereward;
                    }
                }
                else{
                    int kills = omp.getGhostAttackPlayer().getRoundKills();
                    if(kills != 0){
                        if(kills == 1){
                            p.sendMessage(" §f§l+" + 3 + " §7(1 Kill)");
                        }
                        else{
                            p.sendMessage(" §f§l+" + 3 * kills + " §7(" + kills + " Kills)");
                        }
                        amount += 3 * kills;
                    }
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

            if(isGhost(omp)){
                setGhost(null);
                setGhostDead(true);
                ending();
            }

            omp.getGhostAttackPlayer().addLose();
        }
    }

    @Override
    public void leaveSpectator(MiniGamesPlayer omp) {
        if(getArena().getDeadPlayers().contains(omp))
            omp.getGhostAttackPlayer().addLose();
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
                if(isGhost(omp) && omp.getPlayer().getInventory().contains(Material.COMPASS))
                    omp.updateTracker(arena.getPlayers());

                omp.getSurvivalGamesPlayer().updateLeatherColor();
                break;
        }
    }

    @Override
    public void run(State state) {
        switch(state){
            case IN_GAME:
                tickRevealedIn();
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
                b.addScore(12, "");
                b.addScore(11, "§6§l" + MiniGamesMessages.WORD_TIME_LEFT.get(omp));
                b.addScore(10, " " + arena.getMinutes() + "m " + arena.getSeconds() + "s");
                b.addScore(9, " ");
                b.addScore(8, "§7§lGhost");
                b.addScore(7, " " + getGhost().getColorName());
                b.addScore(6, "  ");
                b.addScore(5, "§a§l" + MiniGamesMessages.WORD_ALIVE.get(omp));
                b.addScore(4, " " + (arena.getPlayers().size() - (isGhostDead() ? 0 : 1)));
                b.addScore(3, "   ");
                b.addScore(2, "§c§l" + MiniGamesMessages.WORD_DEAD.get(omp));
                b.addScore(1, " " + arena.getDeadPlayers().size() + " ");
                b.addScore(0, "    ");

                break;
            case ENDING:
                b.addScore(3, "");
                b.addScore(2, "§a§l" + MiniGamesMessages.WORD_WINNER.get(omp));
                if(isGhostDead())
                    b.addScore(1, "§7§l" + MiniGamesMessages.WORD_PLAYERS.get(omp));
                else
                    b.addScore(1, getGhost().getPlayer().getName());
                b.addScore(0, " ");
                break;
        }

        b.updateAliveTeams();
    }

    @Override
    public ArenaPlayer getArenaPlayer(MiniGamesPlayer omp) {
        return omp.getGhostAttackPlayer();
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
            NPC npc = new NPC(Mob.ZOMBIE, l1, "§f§lGhost Buster", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new KitInv(TicketType.GHOST_BUSTER_KIT).open(player);
                }
            });
            npc.setItemInHand(new ItemStack(Material.REDSTONE_TORCH_ON));
            getArena().getNPCs().put(TicketType.GHOST_BUSTER_KIT.getKit(), npc);
            
            minigames.getApi().registerNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.ZOMBIE, l2, "§f§lButcher", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new KitInv(TicketType.BUTCHER_KIT).open(player);
                }
            });
            npc.setItemInHand(new ItemStack(Material.PORK));
            getArena().getNPCs().put(TicketType.BUTCHER_KIT.getKit(), npc);

            minigames.getApi().registerNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.ZOMBIE, l3, "§f§lBetrayer", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new KitInv(TicketType.BETRAYER_KIT).open(player);
                }
            });
            npc.setItemInHand(new ItemStack(Material.DRAGONS_BREATH));
            getArena().getNPCs().put(TicketType.BETRAYER_KIT.getKit(), npc);

            minigames.getApi().registerNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.ZOMBIE, l4, "§f§lAssassin", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new KitInv(TicketType.ASSASSIN_KIT).open(player);
                }
            });
            npc.setItemInHand(new ItemStack(Material.SUGAR));
            getArena().getNPCs().put(TicketType.ASSASSIN_KIT.getKit(), npc);

            minigames.getApi().registerNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.ZOMBIE, l5, "§f§lArmorer", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new KitInv(TicketType.ARMORER_KIT).open(player);
                }
            });
            npc.setItemInHand(new ItemStack(Material.IRON_CHESTPLATE));
            getArena().getNPCs().put(TicketType.ARMORER_KIT.getKit(), npc);

            minigames.getApi().registerNpc(npc);
        }
    }
}
