package fadidev.orbitmines.hub.handlers.players;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.*;
import fadidev.orbitmines.hub.handlers.players.minigames.*;
import fadidev.orbitmines.hub.utils.enums.TicketType;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 8-9-2016.
 */
public class HubPlayer extends OMPlayer {

    private static OrbitMinesHub hub;

    private CageBuilder cageBuilder;
    private boolean canChat;

    private boolean scoreboardEnabled;
    private boolean completedLapisParkour;
    private boolean inLapisParkour;
    private boolean inMindcraft;

    private int miniGameCoins;
    private int miniGameTickets;

    private List<Ticket> tickets;

    private MindCraftPlayer mindCraftPlayer;

    private ChickenFightPlayer chickenFightPlayer;
    private GhostAttackPlayer ghostAttackPlayer;
    private SkywarsPlayer skywarsPlayer;
    private SplatcraftPlayer splatcraftPlayer;
    private SpleefPlayer spleefPlayer;
    private SurvivalGamesPlayer survivalGamesPlayer;
    private UHCPlayer uhcPlayer;

    public HubPlayer(Player player, boolean loaded) {
        super(player, loaded);
    }

    /* Abstract methods */

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public void vote() {
        updateVotes();
        addOrbitMinesTokens(1);

        Title t = new Title("§b§lVote", "§e+1 OrbitMines Token", 20, 40, 20);
        t.send(getPlayer());
    }

    @Override
    public void unloadPlayerData() {

        String uuid = getUUID().toString();

        updateScoreboardEnabled(uuid);

        sendQuitMessage();
    }

    @Override
    public void loadPlayerData() {
        this.canChat = false;
        this.inLapisParkour = false;
        this.inMindcraft = false;
        this.tickets = new ArrayList<>();

        hub = OrbitMinesHub.getHub();
        hub.getLobbyKit().get(getLanguage()).setItems(getPlayer());
        hub.getPlayers().put(getPlayer(), this);
        hub.getHubPlayers().add(this);

        String uuid = getUUID().toString();
        try{

            loadMiniGameCoins(uuid);
            loadMiniGameTickets(uuid);
            loadTickets(uuid);

            loadScoreboardEnabled(uuid);
            loadLapisParkourCompleted(uuid);

            loadMindcraftPlayer(uuid);
            loadChickenFightPlayer(uuid);
            loadGhostAttackPlayer(uuid);
            loadSkywarsPlayer(uuid);
            loadSplatcraftPlayer(uuid);
            loadSpleefPlayer(uuid);
            loadSurvivalGamesPlayer(uuid);
            loadUhcPlayer(uuid);

        }catch(NullPointerException ex){
            ex.printStackTrace();
        }

        sendJoinMessage();

        if(hasPerms(StaffRank.BUILDER) || hub.getBuilderPerms().contains(getUUID())){
            Permission permission = hub.getPermission();
            permission.playerAddTransient(hub.getBuilderWorld1().getName(), getPlayer().getName(), "worldedit.*");
            permission.playerAddTransient(hub.getBuilderWorld2().getName(), getPlayer().getName(), "worldedit.*");
            permission.playerAddTransient(hub.getBuilderWorld2().getName(), getPlayer().getName(), "voxelsniper.sniper");
        }

        if(hasScoreboardEnabled())
            setScoreboard(new HubScoreboard(this));

        if(!isNewPlayer())
            return;

        hub.setPlayerCounter(hub.getPlayerCounter() + 1);

        new BukkitRunnable(){
            public void run(){
                for(HubPlayer omp : hub.getHubPlayers()){
                    HubMessages.JOINED_ORBITMINES.get(omp, getPlayer().getName(), hub.getPlayerCounter() + "");
                }
            }
        }.runTaskLater(hub, 1);
    }

    @Override
    public boolean canReceiveVelocity() {
        return !isInLapisParkour() && !isInMindcraft();
    }

    /* Getters & Setters */

    public CageBuilder getCageBuilder() {
        return cageBuilder;
    }

    public void setCageBuilder(CageBuilder cageBuilder) {
        this.cageBuilder = cageBuilder;
    }

    public boolean canChat() {
        return canChat;
    }

    public void setCanChat(boolean canChat) {
        this.canChat = canChat;

        if(canChat)
            getPlayer().sendMessage(HubMessages.CAN_CHAT_ENABLED.get(this));
    }

    public boolean hasScoreboardEnabled() {
        return scoreboardEnabled;
    }

    public void setScoreboardEnabled(boolean scoreboardEnabled) {
        this.scoreboardEnabled = scoreboardEnabled;

        if(!scoreboardEnabled)
            resetScoreboard();
        else
            setScoreboard(new HubScoreboard(this));
    }

    public boolean hasCompletedLapisParkour() {
        return completedLapisParkour;
    }

    public void setCompletedLapisParkour(boolean completedLapisParkour) {
        this.completedLapisParkour = completedLapisParkour;
    }

    public boolean isInLapisParkour() {
        return inLapisParkour;
    }

    public void setInLapisParkour(boolean inLapisParkour) {
        this.inLapisParkour = inLapisParkour;
    }

    public boolean isInMindcraft() {
        return inMindcraft;
    }

    public void setInMindcraft(boolean inMindcraft) {
        this.inMindcraft = inMindcraft;
    }

    public int getMiniGameCoins() {
        return miniGameCoins;
    }

    public int getMiniGameTickets() {
        return miniGameTickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    /* Other */

    public boolean hasMiniGameCoins(int miniGameCoins){
        return this.miniGameCoins >= miniGameCoins;
    }

    public boolean hasMiniGameTickets(int miniGameTickets){
        return this.miniGameTickets >= miniGameTickets;
    }

    public void toMiniGame(MiniGameArena arena){
        {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            try{
                out.writeUTF("Forward");
                out.writeUTF("ALL");
                out.writeUTF(arena.getType().getShortName());

                out.writeUTF(arena.getArenaID() + "|" + getPlayer().getName());
            }catch(IOException ex){
                ex.printStackTrace();
            }

            getPlayer().sendPluginMessage(hub, "BungeeCord", b.toByteArray());
        }

        {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            try{
                out.writeUTF("Connect");
                out.writeUTF(Server.MINIGAMES.toString().toLowerCase());
            }catch(IOException eee){}

            getPlayer().sendPluginMessage(hub, "BungeeCord", b.toByteArray());
        }
    }

    public void requiredMiniGameCoins(int price){
        Player p = getPlayer();

        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
        int needed = price - getMiniGameCoins();

        p.sendMessage(Messages.REQUIRED_CURRENCY.get(this, "§f§l", needed + "", needed == 1 ? "Coin" : "Coins"));
    }

    public void requiredMiniGameTickets(int price){
        Player p = getPlayer();

        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
        int needed = price - getMiniGameTickets();

        p.sendMessage(Messages.REQUIRED_CURRENCY.get(this, "§f§l", needed + "", needed == 1 ? "Ticket" : "Tickets"));
    }

    public void joinLapisParkour(){
        setInLapisParkour(true);
        Player p = getPlayer();

        if(getPetEnabled() != null){
            disablePet();
        }
        for(PotionEffect eff : p.getActivePotionEffects()){
            p.removePotionEffect(eff.getType());
        }
        if(p.getVehicle() != null){
            p.leaveVehicle();
        }

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
        p.sendMessage("");
        p.sendMessage("§1§lLapis Parkour");
        p.sendMessage(" §7- " + HubMessages.CANT_STOP_SPRINTING.get(this) + ".");
        p.sendMessage(" §7- " + Messages.WORD_REWARD.get(this) + ": §b§l250 VIP Points§7.");
        p.sendMessage("");
        p.sendMessage("§f§l" + HubMessages.GOOD_LUCK.get(this) + "!");

        Title t = new Title("§1§lLapis Parkour", "§7" + HubMessages.CANT_STOP_SPRINTING.get(this) + "!", 20, 40, 20);
        t.send(p);
    }

    public void leaveLapisParkour(){
        setInLapisParkour(false);
        getPlayer().sendMessage(HubMessages.LEAVE_LAPIS_PARKOUR.get(this));
    }

    public boolean inBuilderWorld(){
        return getPlayer().getWorld().getName().equals(hub.getBuilderWorld1().getName()) || getPlayer().getWorld().getName().equals(hub.getBuilderWorld2().getName());
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

    public MindCraftPlayer getMindCraftPlayer() {
        return mindCraftPlayer;
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

    /* OMPlayer getter */

    public static HubPlayer getHubPlayer(Player player){
        if(hub == null)
            hub = OrbitMinesHub.getHub();

        HubPlayer omp = hub.getPlayers().get(player);
        if(omp != null)
            return omp;

        return new HubPlayer(player, false);
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

    private void loadScoreboardEnabled(String uuid){
        if(Database.get().containsPath("Hub-Scoreboard", "uuid", "uuid", uuid)){
            this.scoreboardEnabled = Boolean.parseBoolean(Database.get().getString("Hub-Scoreboard", "scoreboard", "uuid", uuid));
        }
        else{
            Database.get().insert("Hub-Scoreboard", "uuid`, `scoreboard", uuid + "', '" + true);
            this.scoreboardEnabled = true;
        }
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

    private void loadLapisParkourCompleted(String uuid){
        this.completedLapisParkour = Database.get().containsPath("ParkourCompleted", "uuid", "uuid", uuid);
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

    private void loadMindcraftPlayer(String uuid){
        int wins = 0;
        int bestGame = -1;

        if(Database.get().containsPath("MasterMind-Wins", "uuid", "uuid", uuid))
            wins = Database.get().getInt("MasterMind-Wins", "wins", "uuid", uuid);
        else
            Database.get().insert("MasterMind-Wins", "uuid`, `wins", uuid + "', '" + 0);

        if(Database.get().containsPath("MasterMind-BestGame", "uuid", "uuid", uuid))
            bestGame = Database.get().getInt("MasterMind-BestGame", "turns", "uuid", uuid);

        this.mindCraftPlayer = new MindCraftPlayer(this, wins, bestGame);
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

    private void updateScoreboardEnabled(String uuid){
        Database.get().update("Hub-Scoreboard", "scoreboard", "" + hasScoreboardEnabled(), "uuid", uuid);
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
