package fadidev.orbitmines.minigames.handlers.players;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.MiniGamesScoreboard;
import fadidev.orbitmines.minigames.handlers.Ticket;
import fadidev.orbitmines.minigames.handlers.data.GhostAttackData;
import fadidev.orbitmines.minigames.handlers.players.minigames.*;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Fadi on 30-9-2016.
 */
public class MiniGamesPlayer extends OMPlayer {

    private static OrbitMinesMiniGames minigames;

    private int miniGameCoins;
    private int miniGameTickets;

    private List<Ticket> tickets;

    private ChickenFightPlayer chickenFightPlayer;
    private GhostAttackPlayer ghostAttackPlayer;
    private SkywarsPlayer skywarsPlayer;
    private SplatcraftPlayer splatcraftPlayer;
    private SpleefPlayer spleefPlayer;
    private SurvivalGamesPlayer survivalGamesPlayer;
    private UHCPlayer uhcPlayer;

    private Arena arena;

    public MiniGamesPlayer(Player player, boolean loaded) {
        super(player, loaded);
    }

    @Override
    public String getPrefix() {
        if(getArena() != null && getArena().isSpectator(this)){
            if(getArena().getDeadPlayers().contains(this)){
                return "§7Dead §8| ";
            }
            return "§eSpec §8| ";
        }

        if(getArena().getType() == MiniGameType.GHOST_ATTACK && ((GhostAttackData) getArena().getData()).getGhost() == this)
            return "§7§lGhost §8| ";
        else
            return "";
    }

    @Override
    public void vote() {
        updateVotes();

        addMiniGameCoins(50);

        Title t = new Title("§b§lVote", "§f50 Coins", 20, 40, 20);
        t.send(getPlayer());
    }

    @Override
    public void unloadPlayerData() {
        if(getArena() != null)
            getArena().leave(this);
    }

    @Override
    public void loadPlayerData() {
        this.tickets = new ArrayList<>();

        minigames = OrbitMinesMiniGames.getMiniGames();
        minigames.getPlayers().put(getPlayer(), this);
        minigames.getMiniGamesPlayers().add(this);


        String uuid = getUUID().toString();
        try{

            loadMiniGameCoins(uuid);
            loadMiniGameTickets(uuid);
            loadTickets(uuid);

            loadChickenFightPlayer(uuid);
            loadGhostAttackPlayer(uuid);
            loadSkywarsPlayer(uuid);
            loadSplatcraftPlayer(uuid);
            loadSpleefPlayer(uuid);
            loadSurvivalGamesPlayer(uuid);
            loadUhcPlayer(uuid);
            
            setScoreboard(new MiniGamesScoreboard(this));

        }catch(NullPointerException ex){
            ex.printStackTrace();
        }

        final MiniGamesPlayer omp = this;

        new BukkitRunnable(){
            public void run(){
                Player p = omp.getPlayer();
                Map<String, Arena> playersToJoin = minigames.getPlayersToJoin();

                if(playersToJoin.containsKey(p.getName())){
                    Arena arena = playersToJoin.get(p.getName());
                    arena.join(omp);
                    playersToJoin.remove(p.getName());
                }
                else{
                    p.sendMessage(MiniGamesMessages.CANNOT_FIND_DATA.get(omp));

                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);

                    try{
                        out.writeUTF("Connect");
                        out.writeUTF("hub");
                    }catch(IOException eee){
                        p.kickPlayer(MiniGamesMessages.CANNOT_FIND_DATA.get(omp));
                    }

                    getPlayer().sendPluginMessage(minigames, "BungeeCord", b.toByteArray());
                }
            }
        }.runTaskLater(minigames, 5);
    }

    @Override
    public boolean canReceiveVelocity() {
        return getArena() == null || getArena().getState() != State.IN_GAME;
    }

    /* Getters & Setters */

    public int getMiniGameCoins() {
        return miniGameCoins;
    }

    public int getMiniGameTickets() {
        return miniGameTickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public ChickenFightPlayer getChickenFightPlayer() {
        return chickenFightPlayer;
    }

    public GhostAttackPlayer getGhostAttackPlayer() {
        return ghostAttackPlayer;
    }

    public SkywarsPlayer getSkywarsPlayer() {
        return skywarsPlayer;
    }

    public SplatcraftPlayer getSplatcraftPlayer() {
        return splatcraftPlayer;
    }

    public SpleefPlayer getSpleefPlayer() {
        return spleefPlayer;
    }

    public SurvivalGamesPlayer getSurvivalGamesPlayer() {
        return survivalGamesPlayer;
    }

    public UHCPlayer getUhcPlayer() {
        return uhcPlayer;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public static MiniGamesPlayer getMiniGamesPlayer(Player player){
        if(minigames == null)
            minigames = OrbitMinesMiniGames.getMiniGames();

        MiniGamesPlayer omp = minigames.getPlayers().get(player);
        if(omp != null)
            return omp;

        return new MiniGamesPlayer(player, false);
    }

    /* Others */
    public boolean hasMiniGameCoins(int miniGameCoins){
        return this.miniGameCoins >= miniGameCoins;
    }

    public boolean hasMiniGameTickets(int miniGameTickets){
        return this.miniGameTickets >= miniGameTickets;
    }

    public void requiredMiniGameCoins(int price){
        Player p = getPlayer();

        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
        int needed = price - getMiniGameCoins();

        p.sendMessage(Messages.REQUIRED_CURRENCY.get(this, "§f§l", needed + "", needed == 1 ? "Coin" : "Coins"));
    }

    public void addTicketAmount(TicketType type, int amount){
        Ticket ticket = getTicket(type);

        if(ticket != null)
            ticket.setAmount(ticket.getAmount() + amount);
        else
            this.tickets.add(new Ticket(type, amount));

        updateTickets();
    }

    public void removeTicket(TicketType type){
        Ticket ticket = getTicket(type);
        if(ticket.getAmount() == 1)
            this.tickets.remove(ticket);
        else
            ticket.setAmount(ticket.getAmount() -1);

        updateTickets();
    }

    public Ticket getTicket(TicketType type){
        for(Ticket ticket : getTickets()){
            if(ticket.getType() == type)
                return ticket;
        }
        return null;
    }

    public int getTicketAmount(TicketType type){
        Ticket ticket = getTicket(type);

        if(ticket != null)
            return ticket.getAmount();
        return 0;
    }

    public void requiredMiniGameTickets(int price){
        Player p = getPlayer();

        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
        int needed = price - getMiniGameTickets();

        p.sendMessage(Messages.REQUIRED_CURRENCY.get(this, "§f§l", needed + "", needed == 1 ? "Ticket" : "Tickets"));
    }
    
    public void updateTracker(List<MiniGamesPlayer> players){
        List<MiniGamesPlayer> newplayers = new ArrayList<>();
        newplayers.addAll(players);
        newplayers.remove(this);

        if(newplayers.size() != 0){
            double distance = 100000;
            OMPlayer nearest = null;

            for(OMPlayer omp : newplayers){
                Player p = omp.getPlayer();

                if(getPlayer().getWorld().getName().equals(p.getWorld().getName())){
                    double pdistnace = getPlayer().getLocation().distance(p.getLocation());
                    if(pdistnace <= distance){
                        distance = pdistnace;
                        nearest = omp;
                    }
                }
            }

            if(nearest != null){
                for(ItemStack item : getPlayer().getInventory().getContents()){
                    if(item != null && item.getType() == Material.COMPASS){
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName("§6§lTracking: §f§l" + nearest.getPlayer().getName() + " §6§l" + MiniGamesMessages.WORD_DISTANCE.get(this) + ": §f§l" + String.format("%.1f", distance));
                        item.setItemMeta(meta);
                    }
                }
                getPlayer().setCompassTarget(nearest.getPlayer().getLocation());
            }
        }
    }

    /* Database */
    public void addMiniGameCoins(int miniGameCoins){
        this.miniGameCoins += miniGameCoins;

        Database.get().update("MiniGameCoins", "coins", "" + getMiniGameCoins(), "uuid", getUUID().toString());
    }

    public void removeMiniGameCoins(int miniGameCoins){
        this.miniGameCoins -= miniGameCoins;

        Database.get().update("MiniGameCoins", "coins", "" + getMiniGameCoins(), "uuid", getUUID().toString());
    }

    public void addMiniGameTickets(int miniGameTickets){
        this.miniGameTickets += miniGameTickets;

        Database.get().update("MiniGames-MGTickets", "mgtickets", "" + getMiniGameTickets(), "uuid", getUUID().toString());
    }

    public void removeMiniGameTickets(int miniGameTickets){
        this.miniGameTickets -= miniGameTickets;

        Database.get().update("MiniGames-MGTickets", "mgtickets", "" + getMiniGameTickets(), "uuid", getUUID().toString());
    }

    private void loadMiniGameCoins(String uuid){
        if(Database.get().containsPath("MiniGameCoins", "uuid", "uuid", uuid)){
            this.miniGameCoins = Database.get().getInt("MiniGameCoins", "coins", "uuid", uuid);
        }
        else{
            Database.get().insert("MiniGameCoins", "uuid`, `coins", uuid + "', '" + 0);
            this.miniGameCoins = 0;
        }
    }

    private void loadMiniGameTickets(String uuid){
        if(Database.get().containsPath("MiniGames-MGTickets", "uuid", "uuid", uuid)){
            this.miniGameTickets = Database.get().getInt("MiniGames-MGTickets", "mgtickets", "uuid", uuid);
        }
        else{
            Database.get().insert("MiniGames-MGTickets", "uuid`, `mgtickets", uuid + "', '" + 15);
            this.miniGameTickets = 15;
        }
    }

    private void loadTickets(String uuid){
        if(Database.get().containsPath("MiniGames-Tickets", "uuid", "uuid", uuid)){
            String ticketString = Database.get().getString("MiniGames-Tickets", "tickets", "uuid", uuid);

            if(!ticketString.equals("")){
                String[] tickets = ticketString.split("\\|");
                for(String ticket : tickets){
                    String[] ticketParts = ticket.split("\\:");
                    addTicketAmount(TicketType.fromID(Integer.parseInt(ticketParts[0])), Integer.parseInt(ticketParts[1]));
                }
            }
        }
        else{
            Database.get().insert("MiniGames-Tickets", "uuid`, `tickets", uuid + "', '" + "");
        }

        if(getTicket(TicketType.CHICKEN_MAMA_KIT) == null){
            addTicketAmount(TicketType.CHICKEN_MAMA_KIT, 1);
        }
    }

    private void loadSurvivalGamesPlayer(String uuid){
        int kills = 0, wins = 0, loses = 0, bestStreak = 0;
        Color leatherColor = null;

        if(Database.get().containsPath("SurvivalGames-Kills", "uuid", "uuid", uuid))
            kills = Database.get().getInt("SurvivalGames-Kills", "kills", "uuid", uuid);
        else
            Database.get().insert("SurvivalGames-Kills", "uuid`, `kills", uuid + "', '" + 0);

        if(Database.get().containsPath("SurvivalGames-Wins", "uuid", "uuid", uuid))
            wins = Database.get().getInt("SurvivalGames-Wins", "wins", "uuid", uuid);
        else
            Database.get().insert("SurvivalGames-Wins", "uuid`, `wins", uuid + "', '" + 0);

        if(Database.get().containsPath("SurvivalGames-Loses", "uuid", "uuid", uuid))
            loses = Database.get().getInt("SurvivalGames-Loses", "loses", "uuid", uuid);
        else
            Database.get().insert("SurvivalGames-Loses", "uuid`, `loses", uuid + "', '" + 0);

        if(Database.get().containsPath("SurvivalGames-BestStreak", "uuid", "uuid", uuid))
            bestStreak = Database.get().getInt("SurvivalGames-BestStreak", "beststreak", "uuid", uuid);
        else
            Database.get().insert("SurvivalGames-BestStreak", "uuid`, `bestStreak", uuid + "', '" + 0);

        if(Database.get().containsPath("SurvivalGames-Color", "uuid", "uuid", uuid))
            leatherColor = ColorUtils.parse(Database.get().getString("SurvivalGames-Color", "color", "uuid", uuid));

        this.survivalGamesPlayer = new SurvivalGamesPlayer(this, kills, wins, loses, bestStreak, leatherColor);
    }

    private void loadUhcPlayer(String uuid){
        int kills = 0, wins = 0, loses = 0, bestStreak = 0;

        if(Database.get().containsPath("UHC-Kills", "uuid", "uuid", uuid))
            kills = Database.get().getInt("UHC-Kills", "kills", "uuid", uuid);
        else
            Database.get().insert("UHC-Kills", "uuid`, `kills", uuid + "', '" + 0);

        if(Database.get().containsPath("UHC-Wins", "uuid", "uuid", uuid))
            wins = Database.get().getInt("UHC-Wins", "wins", "uuid", uuid);
        else
            Database.get().insert("UHC-Wins", "uuid`, `wins", uuid + "', '" + 0);

        if(Database.get().containsPath("UHC-Loses", "uuid", "uuid", uuid))
            loses = Database.get().getInt("UHC-Loses", "loses", "uuid", uuid);
        else
            Database.get().insert("UHC-Loses", "uuid`, `loses", uuid + "', '" + 0);

        if(Database.get().containsPath("UHC-BestStreak", "uuid", "uuid", uuid))
            bestStreak = Database.get().getInt("UHC-BestStreak", "beststreak", "uuid", uuid);
        else
            Database.get().insert("UHC-BestStreak", "uuid`, `bestStreak", uuid + "', '" + 0);

        this.uhcPlayer = new UHCPlayer(this, kills, wins, loses, bestStreak);
    }

    private void loadSkywarsPlayer(String uuid){
        int kills = 0, wins = 0, loses = 0, bestStreak = 0;
        TicketType cage = null;

        if(Database.get().containsPath("Skywars-Kills", "uuid", "uuid", uuid))
            kills = Database.get().getInt("Skywars-Kills", "kills", "uuid", uuid);
        else
            Database.get().insert("Skywars-Kills", "uuid`, `kills", uuid + "', '" + 0);

        if(Database.get().containsPath("Skywars-Wins", "uuid", "uuid", uuid))
            wins = Database.get().getInt("Skywars-Wins", "wins", "uuid", uuid);
        else
            Database.get().insert("Skywars-Wins", "uuid`, `wins", uuid + "', '" + 0);

        if(Database.get().containsPath("Skywars-Loses", "uuid", "uuid", uuid))
            loses = Database.get().getInt("Skywars-Loses", "loses", "uuid", uuid);
        else
            Database.get().insert("Skywars-Loses", "uuid`, `loses", uuid + "', '" + 0);

        if(Database.get().containsPath("Skywars-BestStreak", "uuid", "uuid", uuid))
            bestStreak = Database.get().getInt("Skywars-BestStreak", "beststreak", "uuid", uuid);
        else
            Database.get().insert("Skywars-BestStreak", "uuid`, `bestStreak", uuid + "', '" + 0);

        if(Database.get().containsPath("Skywars-Cage", "uuid", "uuid", uuid))
            cage = TicketType.valueOf(Database.get().getString("Skywars-Cage", "cage", "uuid", uuid));

        this.skywarsPlayer = new SkywarsPlayer(this, kills, wins, loses, bestStreak, cage);
    }

    private void loadChickenFightPlayer(String uuid){
        int kills = 0, wins = 0, loses = 0, bestStreak = 0;

        if(Database.get().containsPath("ChickenFight-Kills", "uuid", "uuid", uuid))
            kills = Database.get().getInt("ChickenFight-Kills", "kills", "uuid", uuid);
        else
            Database.get().insert("ChickenFight-Kills", "uuid`, `kills", uuid + "', '" + 0);

        if(Database.get().containsPath("ChickenFight-Wins", "uuid", "uuid", uuid))
            wins = Database.get().getInt("ChickenFight-Wins", "wins", "uuid", uuid);
        else
            Database.get().insert("ChickenFight-Wins", "uuid`, `wins", uuid + "', '" + 0);

        if(Database.get().containsPath("ChickenFight-Loses", "uuid", "uuid", uuid))
            loses = Database.get().getInt("ChickenFight-Loses", "loses", "uuid", uuid);
        else
            Database.get().insert("ChickenFight-Loses", "uuid`, `loses", uuid + "', '" + 0);

        if(Database.get().containsPath("ChickenFight-BestStreak", "uuid", "uuid", uuid))
            bestStreak = Database.get().getInt("ChickenFight-BestStreak", "beststreak", "uuid", uuid);
        else
            Database.get().insert("ChickenFight-BestStreak", "uuid`, `bestStreak", uuid + "', '" + 0);

        this.chickenFightPlayer = new ChickenFightPlayer(this, kills, wins, loses, bestStreak);
    }

    private void loadGhostAttackPlayer(String uuid){
        int kills = 0, ghostkills = 0, wins = 0, ghostwins = 0, loses = 0, bestStreak = 0;

        if(Database.get().containsPath("GhostAttack-Kills", "uuid", "uuid", uuid))
            kills = Database.get().getInt("GhostAttack-Kills", "kills", "uuid", uuid);
        else
            Database.get().insert("GhostAttack-Kills", "uuid`, `kills", uuid + "', '" + 0);

        if(Database.get().containsPath("GhostAttack-GhostKills", "uuid", "uuid", uuid))
            ghostkills = Database.get().getInt("GhostAttack-GhostKills", "ghostkills", "uuid", uuid);
        else
            Database.get().insert("GhostAttack-GhostKills", "uuid`, `ghostkills", uuid + "', '" + 0);

        if(Database.get().containsPath("GhostAttack-Wins", "uuid", "uuid", uuid))
            wins = Database.get().getInt("GhostAttack-Wins", "wins", "uuid", uuid);
        else
            Database.get().insert("GhostAttack-Wins", "uuid`, `wins", uuid + "', '" + 0);

        if(Database.get().containsPath("GhostAttack-GhostWins", "uuid", "uuid", uuid))
            ghostwins = Database.get().getInt("GhostAttack-GhostWins", "ghostwins", "uuid", uuid);
        else
            Database.get().insert("GhostAttack-GhostWins", "uuid`, `ghostwins", uuid + "', '" + 0);

        if(Database.get().containsPath("GhostAttack-Loses", "uuid", "uuid", uuid))
            loses = Database.get().getInt("GhostAttack-Loses", "loses", "uuid", uuid);
        else
            Database.get().insert("GhostAttack-Loses", "uuid`, `loses", uuid + "', '" + 0);

        if(Database.get().containsPath("GhostAttack-BestStreak", "uuid", "uuid", uuid))
            bestStreak = Database.get().getInt("GhostAttack-BestStreak", "beststreak", "uuid", uuid);
        else
            Database.get().insert("GhostAttack-BestStreak", "uuid`, `bestStreak", uuid + "', '" + 0);

        this.ghostAttackPlayer = new GhostAttackPlayer(this, kills, ghostkills, wins, ghostwins, loses, bestStreak);
    }

    private void loadSpleefPlayer(String uuid){
        int kills = 0, wins = 0, loses = 0, bestStreak = 0;

        if(Database.get().containsPath("Spleef-Kills", "uuid", "uuid", uuid))
            kills = Database.get().getInt("Spleef-Kills", "kills", "uuid", uuid);
        else
            Database.get().insert("Spleef-Kills", "uuid`, `kills", uuid + "', '" + 0);

        if(Database.get().containsPath("Spleef-Wins", "uuid", "uuid", uuid))
            wins = Database.get().getInt("Spleef-Wins", "wins", "uuid", uuid);
        else
            Database.get().insert("Spleef-Wins", "uuid`, `wins", uuid + "', '" + 0);

        if(Database.get().containsPath("Spleef-Loses", "uuid", "uuid", uuid))
            loses = Database.get().getInt("Spleef-Loses", "loses", "uuid", uuid);
        else
            Database.get().insert("Spleef-Loses", "uuid`, `loses", uuid + "', '" + 0);

        if(Database.get().containsPath("Spleef-BestStreak", "uuid", "uuid", uuid))
            bestStreak = Database.get().getInt("Spleef-BestStreak", "beststreak", "uuid", uuid);
        else
            Database.get().insert("Spleef-BestStreak", "uuid`, `bestStreak", uuid + "', '" + 0);

        //if(Database.get().containsPath("Spleef-BlocksBroken", "uuid", "uuid", uuid))
        //    blocksbroken = Database.get().getInt("Spleef-BlocksBroken", "blocksbroken", "uuid", uuid);
        //else
        //    Database.get().insert("Spleef-BlocksBroken", "uuid`, `blocksbroken", uuid + "', '" + 0);

        this.spleefPlayer = new SpleefPlayer(this, kills, wins, loses, bestStreak);
    }

    private void loadSplatcraftPlayer(String uuid){
        int kills = 0, wins = 0, loses = 0, bestStreak = 0;

        if(Database.get().containsPath("Splatcraft-Kills", "uuid", "uuid", uuid))
            kills = Database.get().getInt("Splatcraft-Kills", "kills", "uuid", uuid);
        else
            Database.get().insert("Splatcraft-Kills", "uuid`, `kills", uuid + "', '" + 0);

        if(Database.get().containsPath("Splatcraft-Wins", "uuid", "uuid", uuid))
            wins = Database.get().getInt("Splatcraft-Wins", "wins", "uuid", uuid);
        else
            Database.get().insert("Splatcraft-Wins", "uuid`, `wins", uuid + "', '" + 0);

        if(Database.get().containsPath("Splatcraft-Loses", "uuid", "uuid", uuid))
            loses = Database.get().getInt("Splatcraft-Loses", "loses", "uuid", uuid);
        else
            Database.get().insert("Splatcraft-Loses", "uuid`, `loses", uuid + "', '" + 0);

        if(Database.get().containsPath("Splatcraft-BestStreak", "uuid", "uuid", uuid))
            bestStreak = Database.get().getInt("Splatcraft-BestStreak", "beststreak", "uuid", uuid);
        else
            Database.get().insert("Splatcraft-BestStreak", "uuid`, `bestStreak", uuid + "', '" + 0);

        //if(Database.get().containsPath("Splatcraft-BlocksColored", "uuid", "uuid", uuid))
        //    blockscolored = Database.get().getInt("Splatcraft-BlocksColored", "blockscolored", "uuid", uuid);
        //else
        //    Database.get().insert("Splatcraft-BlocksColored", "uuid`, `blockscolored", uuid + "', '" + 0);

        this.splatcraftPlayer = new SplatcraftPlayer(this, kills, wins, loses, bestStreak);
    }

    private void updateTickets(){
        String tickets = "";
        for(Ticket ticket : getTickets()){
            if(tickets.equals("")){
                tickets = ticket.toString();
            }
            else{
                tickets += "|" + ticket.toString();
            }
        }

        Database.get().update("MiniGames-Tickets", "tickets", "" + tickets, "uuid", getUUID().toString());
    }
}
