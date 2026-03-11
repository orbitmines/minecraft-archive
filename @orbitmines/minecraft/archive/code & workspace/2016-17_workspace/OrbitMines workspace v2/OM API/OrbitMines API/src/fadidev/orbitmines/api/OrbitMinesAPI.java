package fadidev.orbitmines.api;

import fadidev.orbitmines.api.cmd.*;
import fadidev.orbitmines.api.events.*;
import fadidev.orbitmines.api.events.bungee.BungeeMessageEvent;
import fadidev.orbitmines.api.handlers.*;
import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.chat.ActionBar;
import fadidev.orbitmines.api.handlers.npc.Hologram;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.inventory.ServerSelectorInv;
import fadidev.orbitmines.api.managers.ClickManager;
import fadidev.orbitmines.api.managers.ConfigManager;
import fadidev.orbitmines.api.managers.GadgetManager;
import fadidev.orbitmines.api.managers.VoteManager;
import fadidev.orbitmines.api.nms.Nms;
import fadidev.orbitmines.api.runnables.DatabaseRunnable;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.*;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Server;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Fadi on 3-9-2016.
 */
public class OrbitMinesAPI extends JavaPlugin {

    private static OrbitMinesAPI api;
    private OrbitMinesServer serverPlugin;

    private Nms nms;
    private Database database;

    private ServerSelectorInv serverSelector;

    private ConfigManager configManager;
    private VoteManager voteManager;
    private ClickManager clickManager;
    private GadgetManager gadgets;

    private List<StringInt> voters;
    private List<StringInt> pendingVoters;
    private Map<Player, OMPlayer> players;
    private List<OMPlayer> omPlayers;
    private Map<Server, Integer> onlinePlayers;
    private List<NPC> npcs;
    private List<NPCArmorStand> npcArmorStands;
    private List<Kit> kits;
    private List<Currency> currencies;
    private Map<Cooldown.Action, List<Cooldown>> cooldowns;
    private List<Entity> pets;
    private List<Chunk> chunks;
    private List<Hologram> holograms;
    private List<Command> commands;
    private List<Podium> podia;
    private Map<Player, ActionBar> currentActionBars;
    private Map<Long, List<OMRunnable>> runnables;

    public void onEnable() {
        api = this;

        this.configManager = new ConfigManager();
        getConfigManager().setup("settings", "voters");

        this.voteManager = new VoteManager();
        this.clickManager = new ClickManager();
        this.gadgets = new GadgetManager();

        new Nms();

        this.serverSelector = new ServerSelectorInv();

        // test maps & lists
        this.voters = new ArrayList<>();
        this.pendingVoters = new ArrayList<>();
        this.players = new HashMap<>();
        this.omPlayers = new ArrayList<>();
        this.onlinePlayers = new HashMap<>();
        this.npcs = new ArrayList<>();
        this.npcArmorStands = new ArrayList<>();
        this.kits = new ArrayList<>();
        this.currencies = new ArrayList<>();
        this.cooldowns = new HashMap<>();
        this.pets = new ArrayList<>();
        this.chunks = new ArrayList<>();
        this.holograms = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.podia = new ArrayList<>();
        this.currentActionBars = new HashMap<>();
        this.runnables = new HashMap<>();

        registerEvents();
        registerCurrencies();
        registerBungee();
        registerCommands();
        registerRunnables();

        this.database = new Database(getConfigManager().get("settings").getString("host"), 3306, "OrbitMines", getConfigManager().get("settings").getString("user"), getConfigManager().get("settings").getString("password"));

        loadPendingVotes();
    }

    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()){
            p.kickPlayer("§6§lOrbitMines§4§lNetwork\n    §7Restarting " + getServerType().getName() + "§7 Server...");
        }
    }

    /* Getters & Setters */

    public static OrbitMinesAPI getApi() {
        return api;
    }

    public OrbitMinesServer getServerPlugin() {
        return serverPlugin;
    }

    public void setServerPlugin(OrbitMinesServer serverPlugin) {
        this.serverPlugin = serverPlugin;
    }

    public Server getServerType() {
        if(getServerPlugin() == null)
            return Server.HUB;

        return getServerPlugin().getServer();
    }

    public Nms getNms() {
        return nms;
    }

    public void setNms(Nms nms) {
        this.nms = nms;
    }

    public Database getDB(){
        return database;
    }

    public ServerSelectorInv getServerSelector() {
        return serverSelector;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }

    public ClickManager getClickManager() {
        return clickManager;
    }

    public GadgetManager getGadgets() {
        return gadgets;
    }

    public List<StringInt> getVoters() {
        return voters;
    }

    public void setVoters(List<StringInt> voters) {
        this.voters = voters;
    }

    public List<StringInt> getPendingVoters() {
        return pendingVoters;
    }

    public void setPendingVoters(List<StringInt> pendingVoters) {
        this.pendingVoters = pendingVoters;
    }

    public Map<Player, OMPlayer> getPlayers() {
        return players;
    }

    public List<OMPlayer> getOMPlayers() {
        return omPlayers;
    }

    public Map<Server, Integer> getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getOnlinePlayers(Server server){
        return onlinePlayers.get(server);
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public void registerNpc(NPC npc){
        if(!getNpcs().contains(npc))
            getNpcs().add(npc);
    }

    public List<NPCArmorStand> getNpcArmorStands() {
        return npcArmorStands;
    }

    public void registerNpcArmorStand(NPCArmorStand npcArmorStand){
        if(!getNpcArmorStands().contains(npcArmorStand))
            getNpcArmorStands().add(npcArmorStand);
    }

    public List<Kit> getKits() {
        return kits;
    }

    public void registerKit(Kit kit){
        if(!getKits().contains(kit))
            getKits().add(kit);
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void registerCurrency(Currency currency){
        if(!getCurrencies().contains(currency))
            getCurrencies().add(currency);
    }

    public Map<Cooldown.Action, List<Cooldown>> getCooldowns() {
        return cooldowns;
    }

    public void registerCooldown(Cooldown cooldown){
        if(!getCooldowns().containsKey(cooldown.getAction()))
            getCooldowns().put(cooldown.getAction(), new ArrayList<Cooldown>());

        getCooldowns().get(cooldown.getAction()).add(cooldown);
    }

    public List<Entity> getPets() {
        return pets;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public List<Hologram> getHolograms() {
        return holograms;
    }

    public void registerHologram(Hologram hologram){
        if(!getHolograms().contains(hologram))
            getHolograms().add(hologram);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void registerCommand(Command command){
        if(!getCommands().contains(command))
            getCommands().add(command);
    }

    public void unregisterCommand(String command){
        for(Command cmd : new ArrayList<>(getCommands())){
            for(String alias : cmd.getAlias()){
                if(alias.equalsIgnoreCase(command))
                    getCommands().remove(cmd);
            }
        }
    }

    public List<Podium> getPodia() {
        return podia;
    }

    public void registerPodium(Podium podium){
        if(!getPodia().contains(podium))
            getPodia().add(podium);
    }

    public Map<Player, ActionBar> getCurrentActionBars() {
        return currentActionBars;
    }

    public Map<Long, List<OMRunnable>> getRunnables() {
        return runnables;
    }

    public DoubleBoolean dispatchCommand(OMPlayer omp, String[] a){
        for(Command cmd : getCommands()){
            for(String alias : cmd.getAlias()){
                if(!a[0].equalsIgnoreCase(alias))
                    continue;

                return new DoubleBoolean(true, cmd.dispatchCancelled(omp, a));
            }
        }
        return null;
    }

    /* load & register */
    private void loadPendingVotes(){
        FileConfiguration config = getConfigManager().get("voters");
        if(config.getStringList("VoteRewardsNotGiven") != null){
            List<StringInt> list = new ArrayList<>();
            for(String s : config.getStringList("VoteRewardsNotGiven")){
                String[] sParts = s.split("\\|");
                list.add(new StringInt(sParts[0], Integer.parseInt(sParts[1])));
            }
            pendingVoters.clear();
            pendingVoters.addAll(list);
        }
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        getServer().getPluginManager().registerEvents(new InteractEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new UnloadEvent(), this);
        getServer().getPluginManager().registerEvents(new VoteEvent(), this);
        getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new SignEvent(), this);
    }

    private void registerCurrencies(){
        registerCurrency(new Currency("VIP Point", "VIP Points", "§b§l", Material.DIAMOND));
        registerCurrency(new Currency("OMT", "OMT", "§e§l", Material.GOLD_NUGGET));
    }

    private void registerBungee(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageEvent());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "OrbitMinesAPI");
        getServer().getMessenger().registerIncomingPluginChannel(this, "OrbitMinesAPI", new BungeeMessageEvent());
    }

    private void registerCommands(){
        registerCommand(new AddEnchantmentCommand());
        registerCommand(new AFKCommand());
        registerCommand(new ChatColorsCommand());
        registerCommand(new ClearEntitiesCommand());
        registerCommand(new DisguiseCommand());
        registerCommand(new DisguisesCommand());
        registerCommand(new FeedCommand());
        registerCommand(new FireworkCommand());
        registerCommand(new FlyCommand());
        registerCommand(new GadgetsCommand());
        registerCommand(new GameModeACommand());
        registerCommand(new GameModeCCommand());
        registerCommand(new GameModeCommand());
        registerCommand(new GameModeSCommand());
        registerCommand(new GameModeSpecCommand());
        registerCommand(new GiveCommand());
        registerCommand(new HatsCommand());
        registerCommand(new HealCommand());
        registerCommand(new InvSeeCommand());
        registerCommand(new NickCommand());
        registerCommand(new OPModeCommand());
        registerCommand(new PerksCommand());
        registerCommand(new PetsCommand());
        registerCommand(new PluginsCommand());
        registerCommand(new ServersCommand());
        registerCommand(new SilentCommand());
        registerCommand(new SkullCommand());
        registerCommand(new TeleportCommand());
        registerCommand(new TopVotersCommand());
        registerCommand(new TrailsCommand());
        registerCommand(new UndisguiseCommand());
        registerCommand(new UUIDCommand());
        registerCommand(new VoteCommand());
        registerCommand(new WardrobeCommand());

        ConsoleCommandExecuter ccE = new ConsoleCommandExecuter();
        List<String> list = Arrays.asList("setvip", "setstaff", "votes", "vippoints", "omt");

        for(String command : list){
            getCommand(command).setExecutor(ccE);
        }
    }

    private void registerRunnables(){
        new NPCMovingRunnable();
        new DatabaseRunnable().runTaskAsynchronously(this);
        new PlayerDataRunnable();
        new PodiumRunnable();
        new ServerRunnable();
        new VoteRunnable();

        loadOrbitMinesServer();
    }

    private int index;

    private void loadOrbitMinesServer(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(getServerPlugin() == null){
                    if(index == 4) {
                        Utils.consoleWarning("Cannot find a plugin that contains the 'OrbitMinesServer' class, disabling API...");
                        getServer().getPluginManager().disablePlugin(getApi());
                    }
                    else{
                        index++;
                        loadOrbitMinesServer();
                    }
                }
                else{
                    getServerPlugin().startNpcRunnable();
                    getServerPlugin().startPlayerRunnable();
                    getServerPlugin().startPlayerSecondRunnable();

                    if(getServerPlugin().gadgetsEnabled()){
                        getServer().getPluginManager().registerEvents(new AnimationEvent(), getApi());
                        getServer().getPluginManager().registerEvents(new BlockChangeEvent(), getApi());
                        getServer().getPluginManager().registerEvents(new BlockFormEvent(), getApi());
                        getServer().getPluginManager().registerEvents(new DamageEvent(), getApi());
                        getServer().getPluginManager().registerEvents(new DeathEvent(), getApi());
                        getServer().getPluginManager().registerEvents(new DismountEvent(), getApi());
                        getServer().getPluginManager().registerEvents(new ExplodeEvent(), getApi());
                        getServer().getPluginManager().registerEvents(new PickupEvent(), getApi());
                        getServer().getPluginManager().registerEvents(new ProjHitEvent(), getApi());

                        new GadgetRunnable();
                    }

                    if(getServerPlugin().hatsEnabled()){
                        getServer().getPluginManager().registerEvents(new MoveEvent(), getApi());
                    }
                }
            }
        }.runTaskLater(this, 20);
    }
}
