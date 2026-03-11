package fadidev.orbitmines.kitpvp;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.Podium;
import fadidev.orbitmines.api.handlers.StringInt;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.nms.customitem.CustomItemNms;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Direction;
import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.kitpvp.cmd.FreeKitCommand;
import fadidev.orbitmines.kitpvp.cmd.KitCommand;
import fadidev.orbitmines.kitpvp.events.*;
import fadidev.orbitmines.kitpvp.handlers.ActiveBooster;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMap;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.inventories.KitPvPOMTShopInv;
import fadidev.orbitmines.kitpvp.inventories.KitPvPTeleporterInv;
import fadidev.orbitmines.kitpvp.inventories.KitSelectorInv;
import fadidev.orbitmines.kitpvp.inventories.MasteryInv;
import fadidev.orbitmines.kitpvp.runnables.*;
import fadidev.orbitmines.kitpvp.utils.enums.ArmorType;
import fadidev.orbitmines.kitpvp.utils.enums.ItemType;
import fadidev.orbitmines.kitpvp.utils.enums.KitPvPKit;
import fadidev.orbitmines.kitpvp.utils.enums.ProjectileType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.*;

/**
 * Created by Fadi on 10-9-2016.
 */
public class OrbitMinesKitPvP extends JavaPlugin {

    private static OrbitMinesKitPvP kitPvP;
    private OrbitMinesAPI api;
    private KitPvP omServer;

    private Map<Player, KitPvPPlayer> players;
    private List<KitPvPPlayer> kitPvPPlayers;

    private boolean freeKitEnabled;
    private World lobby;
    private World arena;
    private Location spawn;
    private List<KitPvPMap> maps;
    private KitPvPMap currentMap;
    private Map<Projectile, ProjectileType> projectiles;
    private ActiveBooster booster;
    private Map<Block, Integer> paintballBlocks;
    private Map<Block, Player> paintballBlockPlayers;
    private KitPvPTeleporterInv teleporterInv;
    private Map<Language, Kit> lobbyKit;
    private Map<Language, Kit> spectatorKit;
    private List<StringInt> topKills;

    private NPCArmorStand masteryNpc;

    @Override
    public void onEnable() {
        kitPvP = this;
        omServer = new KitPvP();
        api = omServer.getApi();

        this.lobby = Bukkit.getWorld("KitPvPLobby");
        this.arena = Bukkit.getWorld("KitPvPArenas");
        this.spawn = new Location(getLobby(), -0.5, 74, -0.5, 90, 0);
        this.maps = new ArrayList<>();
        this.players = new HashMap<>();
        this.projectiles = new HashMap<>();
        this.kitPvPPlayers = new ArrayList<>();
        this.paintballBlocks = new HashMap<>();
        this.paintballBlockPlayers = new HashMap<>();
        this.teleporterInv = new KitPvPTeleporterInv();
        this.lobbyKit = new HashMap<>();
        this.spectatorKit = new HashMap<>();
        this.topKills = new ArrayList<>();

        registerRunnables();
        registerEvents();
        registerCommands();
        registerCurrencies();
        registerLobbyKit();
        registerSpectatorKit();
        registerMaps();
        registerKits();
        registerPodium();
        setRandomMap();

        new BukkitRunnable(){
            @Override
            public void run() {
                WorldUtils.removeAllEntitiesFrame();
                spawnNpcs();
            }
        }.runTaskLater(this, 100);
    }

    @Override
    public void onDisable() {
        WorldUtils.removeAllEntitiesFrame();
    }

    public static OrbitMinesKitPvP getKitPvP() {
        return kitPvP;
    }

    public OrbitMinesAPI getApi() {
        return api;
    }

    public KitPvP getOmServer() {
        return omServer;
    }

    public List<KitPvPPlayer> getKitPvPPlayers() {
        return kitPvPPlayers;
    }

    public Map<Player, KitPvPPlayer> getPlayers() {
        return players;
    }

    public boolean isFreeKitEnabled() {
        return freeKitEnabled;
    }

    public void setFreeKitEnabled(boolean freeKitEnabled) {
        this.freeKitEnabled = freeKitEnabled;
    }

    public World getLobby() {
        return lobby;
    }

    public World getArena() {
        return arena;
    }

    public Location getSpawn() {
        return spawn;
    }

    public List<KitPvPMap> getMaps() {
        return maps;
    }

    public KitPvPMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(KitPvPMap currentMap) {
        this.currentMap = currentMap;
    }

    public Map<Projectile, ProjectileType> getProjectiles() {
        return projectiles;
    }

    public ActiveBooster getBooster() {
        return booster;
    }

    public void setBooster(ActiveBooster booster) {
        this.booster = booster;
    }

    public Map<Block, Integer> getPaintballBlocks() {
        return paintballBlocks;
    }

    public Map<Block, Player> getPaintballBlockPlayers() {
        return paintballBlockPlayers;
    }

    public KitPvPTeleporterInv getTeleporterInv() {
        return teleporterInv;
    }

    public Map<Language, Kit> getLobbyKit() {
        return lobbyKit;
    }

    public Map<Language, Kit> getSpectatorKit() {
        return spectatorKit;
    }

    public List<StringInt> getTopKills() {
        return topKills;
    }

    public void setTopKills(List<StringInt> topKills) {
        this.topKills = topKills;
    }

    public NPCArmorStand getMasteryNpc() {
        return masteryNpc;
    }

    /* Other */
    public void setRandomMap(){
        currentMap = maps.get(Utils.RANDOM.nextInt(maps.size()));
        currentMap.resetTimer();
    }

    public void setNextMap(){
        List<KitPvPMap> maps = new ArrayList<>();
        int votes = 0;

        for(KitPvPMap map : getMaps()){
            if(map.getVotes().size() > 0){
                if(map.getVotes().size() > votes){
                    votes = map.getVotes().size();
                    maps.clear();
                    maps.add(map);
                }
                else if(map.getVotes().size() == votes){
                    maps.add(map);
                }Enchantment.DA
            }

            map.getVotes().clear();
        }

        if(votes == 0){
            maps = new ArrayList<>(this.maps);
            if (currentMap != null)
                maps.remove(currentMap);
        }

        currentMap = maps.get(Utils.RANDOM.nextInt(maps.size()));
        currentMap.resetTimer();
    }

    public boolean hasBooster(){
        return booster != null;
    }

    public boolean isProjectile(Projectile projectile){
        return projectiles.containsKey(projectile);
    }

    public ProjectileType getProjectileType(Projectile projectile){
        return this.projectiles.get(projectile);
    }

    public void addProjectile(Projectile projectile, ProjectileType type){
        this.projectiles.put(projectile, type);
    }

    public void removeProjectile(Projectile projectile){
        this.projectiles.remove(projectile);
    }

    /* register */

    private void registerCommands(){
        getApi().registerCommand(new FreeKitCommand());
        getApi().registerCommand(new KitCommand());
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new BlockChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new DropEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new ExpChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new FadeEvent(), this);
        getServer().getPluginManager().registerEvents(new FoodEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PickupEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new ProjHitEvent(), this);
        getServer().getPluginManager().registerEvents(new ShootBowEvent(), this);
        getServer().getPluginManager().registerEvents(new SwapHandItemsEvent(), this);
        getServer().getPluginManager().registerEvents(new TeleportEvent(), this);
    }

    private void registerCurrencies(){
        getApi().registerCurrency(new Currency("Coin", "Coins", "§6§l", Material.GOLD_INGOT));
    }

    private void registerRunnables(){
        new BoosterRunnable();
        new FreeKitRunnable();
        new MapRunnable();
        new MasteryNpcRunnable();
        new TeleporterRunnable();
        new TopKillsRunnable();
    }

    private void registerPodium(){
        getApi().registerPodium(new Podium(new Location(getLobby(), 6, 74, -1), Direction.WEST) {
            @Override
            public String[] getLines(int index, StringInt stringInt) {
                String[] lines = new String[4];
                lines[0] = "§lTop Kills (" + (index +1) + ")";
                lines[1] = "";

                if(stringInt == null || stringInt.getString() == null){
                    lines[2] = "";
                    lines[3] = "";
                }
                else{
                    lines[2] = stringInt.getString();

                    if(stringInt.getInteger() == 1)
                        lines[3] = "§o" + stringInt.getInteger() + " Kill";
                    else
                        lines[3] = "§o" + stringInt.getInteger() + " Kills";
                }

                return lines;
            }

            @Override
            public List<StringInt> getStringInts() {
                return getTopKills();
            }
        });
    }

    private void registerLobbyKit(){
        {
            Kit kit = new Kit("Lobby_" + Language.DUTCH.toString());
            
            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) item.getItemMeta();
            meta.setDisplayName("§c§nBook of Enchantments");
            meta.addPage("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
            meta.setPage(1,
                    "\n\n          §0§lBook\n"
                            + "         §0§lof \n"
                            + "   §0§lEnchantments");
            meta.setPage(2,
                    "§0§lLightning §0§o(Sword)\n"
                            + " §0§l- Lightning I\n"
                            + "  §0§oOn Hit, 25% kans dat er bliksem neerdaalt op je tegenstander.\n"
                            + " §0§l- Lightning II\n"
                            + "  §0§oOn Hit, 33% kans dat er bliksem neerdaalt op je tegenstander.\n");
            meta.setPage(3,
                    "§0§lBlindness\n"
                            + " §0§l- Blindness I\n"
                            + "  §0§oOn Hit, geeft 2 seconden Blindness aan je tegenstander.\n"
                            + " §0§l- Blindness II\n"
                            + "  §0§oOn Hit, geeft 3 seconden Blindness aan je tegenstander.\n");
            meta.setPage(4,
                    "§0§lHealing\n"
                            + " §0§l- Healing I\n"
                            + "  §0§oSHIFT + Rechtermuisklik, geeft je 4 seconden Regeneration III.\n"
                            + " §0§l- Healing II\n"
                            + "  §0§oSHIFT + Rechtermuisklik, geeft je 5 seconden Regeneration III.\n");
            meta.setPage(5,
                    "§0§lVampire\n"
                            + " §0§l- Vampire I\n"
                            + "  §0§oOn Hit, geeft 1.5 hearts aan jezelf terug.\n");
            meta.setPage(6,
                    "§0§lMagic\n"
                            + " §0§l- Magic I\n"
                            + "  §0§oOn Hit, geeft 4 seconden Wither I aan je tegenstander.\n");
            meta.setPage(7,
                    "§0§lKnockup\n"
                            + " §0§l- Knockup I\n"
                            + "  §0§oOn Hit, 50% kans om je tegenstander in de lucht te gooien.\n");
            meta.setPage(8,
                    "§0§lLightning §0§o(Bow)\n"
                            + " §0§l- Lightning I\n"
                            + "  §0§oOn Hit, daalt er bliksem neer op je tegenstander.\n");
            meta.setPage(9,
                    "§0§lUndeath\n"
                            + " §0§l- Undeath I\n"
                            + "  §0§oOn Hit, spawnt 3 Undeath Knights die je tegenstander aanvallen.\n"
                            + " §0§l- Undeath II\n"
                            + "  §0§oOn Hit, spawnt 3 Undeath Knights en 1 Undeath Archer die je tegenstander aanvallen.\n");
            meta.setPage(10,
                    "§0§lExplosive\n"
                            + " §0§l- Explosive I\n"
                            + "  §0§oOn Hit, spawnt een TNT op de locatie van je tegenstander.\n");
            meta.setPage(11,
                    "§0§lArrow Split\n"
                            + " §0§l- Arrow Split I\n"
                            + "  §0§oWanneer je een arrow lostlaat, zullen vier andere eromheen gespawnt worden.\n");
            meta.setPage(12,
                    "§0§lWither Armor\n"
                            + " §0§l- Wither Armor I\n"
                            + "  §0§oWanneer je geraakt wordt, geeft 5 seconden Wither II aan je tegenstander.\n");
            meta.setPage(13,
                    "§0§lMolten Armor\n"
                            + " §0§l- Molten Armor I\n"
                            + "  §0§oWanneer je geraakt wordt, geeft 5 seconden Blindness en 5 seconden Slowness VI aan je tegenstander.\n");
            meta.setPage(14,
                    "§0§lFire Trail\n"
                            + " §0§l- Fire Trail I\n"
                            + "  §0§oWanneer je loopt, zal het pad waar je op loopt in vuur veranderen.\n");
            meta.setPage(15,
                    "§0§lLight\n"
                            + " §0§l- Light I\n"
                            + "  §0§oWanneer je over water loopt, zal het water cobblestone worden.\n");
            meta.setPage(16,
                    "§0§lFly\n"
                            + " §0§l- Fly I\n"
                            + "  §0§oSHIFT, zorgt ervoor dat je kan vliegen!\n");
            meta.setPage(17,
                    "§0§lArthropods\n"
                            + " §0§l- Arthropods I\n"
                            + "  §0§oOn Hit, 16.67% kans dat er een Spider spawnt op je tegenstander.\n"
                            + " §0§l- Arthropods II\n"
                            + "  §0§oOn Hit, 20% kans dat er een Spider Jockey spawnt op je tegenstander\n");
            meta.setPage(18,
                    " §0§l- Arthropods III\n"
                            + "  §0§oOn Hit, 25% kans dat een Spider en een Spider Jockey spawnen op je tegenstander.\n");
            meta.setPage(19,
                    "§0§lWither\n"
                            + " §0§l- Wither I\n"
                            + "  §0§oRechtermuisklik, schiet 4 Wither Skulls. Prijs: 1 Soul. Wanneer je een kill krijgt, krijg je 1 Soul.\n");
            meta.setPage(20,
                    "§0§lBarrier\n"
                            + " §0§l- Barrier I\n"
                            + "  §0§oRechtermuisklik, spawnt een schild om je heen, geeft 5 seconden Resistance.\n"
                            + " §0§l- Barrier II\n"
                            + "  §0§oRechtermuisklik, spawnt een schild om je heen, geeft 5 seconden Resistance II.\n");
            meta.setPage(21,
                    "§0§lTNT\n"
                            + " §0§l- TNT I\n"
                            + "  §0§oRechtermuisklik, schiet TNT naar de plek waar je naar kijkt.\n");
            meta.setPage(22,
                    "§0§lFish Attack\n"
                            + " §0§l- Fish Attack I\n"
                            + "  §0§oRechtermuisklik, geeft 4 seconden Poison III aan dichtbijzijnde tegenstanders.\n");
            meta.setPage(23,
                    "§0§lShield\n"
                            + " §0§l- Shield I\n"
                            + "  §0§oRechtermuisklik, geeft je 10 seconden Resistance.\n"
                            + " §0§l- Shield II\n"
                            + "  §0§oRechtermuisklik, geeft je 12 seconden Resistance II.\n");
            meta.setPage(24,
                    "§0§lTrade\n"
                            + " §0§l- Trade I\n"
                            + "  §0§oOn Hit, verwijderd het item, en steelt het item dat je tegenstander in zijn/haar hand heeft.\n");
            meta.setPage(25,
                    "§0§lHealing Kit\n"
                            + " §0§l- Healing Kit I\n"
                            + "  §0§oRechtermuisklik, verwijderd het item, en geeft je al je health terug.\n");
            meta.setPage(26,
                    "§0§lBlock Explosion\n"
                            + " §0§l- Block Explosion I\n"
                            + "  §0§oRechtermuisklik, maakt een explosie die dichtbijzijnde tegenstanders blind en slowt.\n");
            meta.setPage(27,
                    "§0§lUndeath Summon\n"
                            + " §0§l- Undeath Summon I\n"
                            + "  §0§oRechtermuisklik, spawnt twee baby pigmans die voor jouw vechten!\n");
            meta.setPage(28,
                    "§0§lPaintballs\n"
                            + " §0§l- Paintballs I\n"
                            + "  §0§oRechtermuisklik, maakt een gebied dat een effect geeft als je er op loopt, anders voor elke kleur.\n  §0§oLinkermuisklik, verander kleur.\n");
            meta.setAuthor("§6§lOrbitMines§c§lKitPvP");
            item.setItemMeta(meta);

            kit.setItem(0, item);
            kit.setItem(1, ItemUtils.itemstack(Material.EXP_BOTTLE, 1, "§d§nAchievements"));
            kit.setItem(4, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nServer Selector"));
            kit.setItem(5, ItemUtils.itemstack(Material.GOLD_NUGGET, 1, "§a§nBoosters"));
            kit.setItem(7, ItemUtils.itemstack(Material.ENDER_CHEST, 1, "§9§nCosmetic Perks"));
            kit.setItem(8, ItemUtils.itemstack(Material.DIAMOND_CHESTPLATE, 1, "§b§nKit Selector"));

            kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 0));

            getApi().registerKit(kit);
            getLobbyKit().put(Language.DUTCH, kit);
        }
        {
            Kit kit = new Kit("Lobby_" + Language.ENGLISH.toString());

            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) item.getItemMeta();
            meta.setDisplayName("§c§nBook of Enchantments");
            meta.addPage("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
            meta.setPage(1,
                    "\n\n          §0§lBook\n"
                            + "         §0§lof \n"
                            + "   §0§lEnchantments");
            meta.setPage(2,
                    "§0§lLightning §0§o(Sword)\n"
                            + " §0§l- Lightning I\n"
                            + "  §0§oOn Hit, 25% chance for a lightning to strike on your opponent.\n"
                            + " §0§l- Lightning II\n"
                            + "  §0§oOn Hit, 33% chance for a lightning to strike on your opponent.\n");
            meta.setPage(3,
                    "§0§lBlindness\n"
                            + " §0§l- Blindness I\n"
                            + "  §0§oOn Hit, giving 2 seconds of Blindness to your opponent.\n"
                            + " §0§l- Blindness II\n"
                            + "  §0§oOn Hit, giving 3 seconds of Blindness to your opponent.\n");
            meta.setPage(4,
                    "§0§lHealing\n"
                            + " §0§l- Healing I\n"
                            + "  §0§oSHIFT + Right Click, giving 4 seconds of Regeneration III to yourself.\n"
                            + " §0§l- Healing II\n"
                            + "  §0§oSHIFT + Right Click, giving 5 seconds of Regeneration III to yourself.\n");
            meta.setPage(5,
                    "§0§lVampire\n"
                            + " §0§l- Vampire I\n"
                            + "  §0§oOn Hit, restoring 1.5 hearts to your health.\n");
            meta.setPage(6,
                    "§0§lMagic\n"
                            + " §0§l- Magic I\n"
                            + "  §0§oOn Hit, giving 4 second of Wither I to your opponent.\n");
            meta.setPage(7,
                    "§0§lKnockup\n"
                            + " §0§l- Knockup I\n"
                            + "  §0§oOn Hit, 50% chance to knockup your opponent in the air.\n");
            meta.setPage(8,
                    "§0§lLightning §0§o(Bow)\n"
                            + " §0§l- Lightning I\n"
                            + "  §0§oOn Hit, striking a lightning on your opponent.\n");
            meta.setPage(9,
                    "§0§lUndeath\n"
                            + " §0§l- Undeath I\n"
                            + "  §0§oOn Hit, spawning 3 Undeath Knights that will attack your opponent.\n"
                            + " §0§l- Undeath II\n"
                            + "  §0§oOn Hit, spawning 3 Undeath Knights and 1 Undeath Archer that will attack your opponent.\n");
            meta.setPage(10,
                    "§0§lExplosive\n"
                            + " §0§l- Explosive I\n"
                            + "  §0§oOn Hit, spawning a TNT at your opponents position.\n");
            meta.setPage(11,
                    "§0§lArrow Split\n"
                            + " §0§l- Arrow Split I\n"
                            + "  §0§oWhen releasing an arrow, 4 more will spawn at its side.\n");
            meta.setPage(12,
                    "§0§lWither Armor\n"
                            + " §0§l- Wither Armor I\n"
                            + "  §0§oWhen getting hit, 5 seconds of Wither II to your opponent.\n");
            meta.setPage(13,
                    "§0§lMolten Armor\n"
                            + " §0§l- Molten Armor I\n"
                            + "  §0§oWhen getting hit, giving 5 seconds of Blindness and 5 seconds of Slowness VI to your opponent.\n");
            meta.setPage(14,
                    "§0§lFire Trail\n"
                            + " §0§l- Fire Trail I\n"
                            + "  §0§oWhen walking, a path of fire will appear behind you.\n");
            meta.setPage(15,
                    "§0§lLight\n"
                            + " §0§l- Light I\n"
                            + "  §0§oWhen walking over water, replacing nearby blocks with Cobblestone.\n");
            meta.setPage(16,
                    "§0§lFly\n"
                            + " §0§l- Fly I\n"
                            + "  §0§oSHIFT, ability to fly!\n");
            meta.setPage(17,
                    "§0§lArthropods\n"
                            + " §0§l- Arthropods I\n"
                            + "  §0§oOn Hit, 16.67% chance for a Spider to spawn on your opponent.\n"
                            + " §0§l- Arthropods II\n"
                            + "  §0§oOn Hit, 20% chance for a Spider Jockey to spawn on your opponent.\n");
            meta.setPage(18,
                    " §0§l- Arthropods III\n"
                            + "  §0§oOn Hit, 25% chance for a Spider and a Spider Jockey to spawn on your opponent.\n");
            meta.setPage(19,
                    "§0§lWither\n"
                            + " §0§l- Wither I\n"
                            + "  §0§oRight Click, shooting 4 Wither Skulls from your position. Price: 1 Soul. Killing an opponent will give you a Soul.\n");
            meta.setPage(20,
                    "§0§lBarrier\n"
                            + " §0§l- Barrier I\n"
                            + "  §0§oRight Click, spawning a force field around you, giving 5 seconds of Resistance.\n"
                            + " §0§l- Barrier II\n"
                            + "  §0§oRight Click, spawning a force field around you, giving 5 seconds of Resistance II.\n");
            meta.setPage(21,
                    "§0§lTNT\n"
                            + " §0§l- TNT I\n"
                            + "  §0§oRight Click, shooting a TNT where you're looking at.\n");
            meta.setPage(22,
                    "§0§lFish Attack\n"
                            + " §0§l- Fish Attack I\n"
                            + "  §0§oRight Click, giving 4 seconds of Poison III to all nearby opponents.\n");
            meta.setPage(23,
                    "§0§lShield\n"
                            + " §0§l- Shield I\n"
                            + "  §0§oRight Click, giving 10 seconds of Resistance to yourself.\n"
                            + " §0§l- Shield II\n"
                            + "  §0§oRight Click, giving 12 seconds of Resistance II to yourself.\n");
            meta.setPage(24,
                    "§0§lTrade\n"
                            + " §0§l- Trade I\n"
                            + "  §0§oOn Hit, removing this item and stealing your opponents weapon.\n");
            meta.setPage(25,
                    "§0§lHealing Kit\n"
                            + " §0§l- Healing Kit I\n"
                            + "  §0§oRight Click, removing this item and restoring you to full health.\n");
            meta.setPage(26,
                    "§0§lBlock Explosion\n"
                            + " §0§l- Block Explosion I\n"
                            + "  §0§oRight Click, creating an explosion of blocks which will blind and slow nearby opponents.\n");
            meta.setPage(27,
                    "§0§lUndeath Summon\n"
                            + " §0§l- Undeath Summon I\n"
                            + "  §0§oRight Click, spawning two baby pigmans that will kill for you!\n");
            meta.setPage(28,
                    "§0§lPaintballs\n"
                            + " §0§l- Paintballs I\n"
                            + "  §0§oRight Click, creating an area which will give an effect specified to its color.\n  §0§oLeft Click, change color.\n");
            meta.setAuthor("§6§lOrbitMines§c§lKitPvP");
            item.setItemMeta(meta);

            kit.setItem(0, item);
            kit.setItem(1, ItemUtils.itemstack(Material.EXP_BOTTLE, 1, "§d§nAchievements"));
            kit.setItem(4, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nServer Selector"));
            kit.setItem(5, ItemUtils.itemstack(Material.GOLD_NUGGET, 1, "§a§nBoosters"));
            kit.setItem(7, ItemUtils.itemstack(Material.ENDER_CHEST, 1, "§9§nCosmetic Perks"));
            kit.setItem(8, ItemUtils.itemstack(Material.DIAMOND_CHESTPLATE, 1, "§b§nKit Selector"));

            kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 0));

            getApi().registerKit(kit);
            getLobbyKit().put(Language.ENGLISH, kit);
        }
    }

    private void registerSpectatorKit(){
        {
            Kit kit = new Kit("Spectator_" + Language.DUTCH.toString());
            kit.setItem(3, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nTerug naar de Lobby"));
            kit.setItem(5, ItemUtils.itemstack(Material.NAME_TAG, 1, "§e§nTeleporter"));

            getApi().registerKit(kit);
            getSpectatorKit().put(Language.DUTCH, kit);
        }
        {
            Kit kit = new Kit("Spectator_" + Language.ENGLISH.toString());
            kit.setItem(3, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nBack to the Lobby"));
            kit.setItem(5, ItemUtils.itemstack(Material.NAME_TAG, 1, "§e§nTeleporter"));

            getApi().registerKit(kit);
            getSpectatorKit().put(Language.ENGLISH, kit);
        }
    }

    private void registerMaps(){
        {
            KitPvPMap map = new KitPvPMap("Snow Town");
            map.setBuilders("§4§lOwner §4O_o_Fadi_o_O\n§b§lMod §bAlderius");
            map.setSpawns(Arrays.asList(new Location(getArena(), -63.5, 9, -1182.5, -45, 0), new Location(getArena(), -92.5, 14, -1079.5, -166, 0), new Location(getArena(), -134.5, 9, -1131.5, -113, 0), new Location(getArena(), -115, 12.5, -1187.5, -70, 0), new Location(getArena(), -90.5, 9, -1171.5, 143, 0), new Location(getArena(), -62.5, 9, -1138.5, -71, 0), new Location(getArena(), -108.5, 11, -1150.5, -139, 0), new Location(getArena(), -91.5, 10, -1205.5, -30, 0), new Location(getArena(), -144.5, 10, -1165.5, -75, 0), new Location(getArena(), -66.5, 10, -1098.5, -126, 0)));
            map.setSpectatorSpawn(new Location(getArena(), -93.5, 22, -1154.5, 145, 0));
            map.setMaxY(30);
            map.setVoteSign(new Location(getLobby(), -21, 76, 0));
            maps.add(map);
        }
        {
            KitPvPMap map = new KitPvPMap("Mountain Village");
            map.setBuilders("§b§lMod §bAlderius\n§b§lMod §bsharewoods\n§4§lOwner §4O_o_Fadi_o_O");
            map.setSpawns(Arrays.asList(new Location(getArena(), -352.5, 5, -1366.5, -54, 0), new Location(getArena(), -317.5, 4, -1329.5, 46, 0), new Location(getArena(), -283.5, 4, -1296.5, 165, 0), new Location(getArena(), -303.5, 5, -1315.5, 39, 0), new Location(getArena(), -284.5, 4, -1348.5, 168, 0), new Location(getArena(), -303.5, 4, -1345.5, 135, 0), new Location(getArena(), -316.5, 9, -1364.5, 30, 0), new Location(getArena(), -337.5, 5, -1344.5, -126, 0), new Location(getArena(), -349.5, 4, -1327.5, -113, 0), new Location(getArena(), -323.5, 5, -1296.5, -161, 0)));
            map.setSpectatorSpawn(new Location(getArena(), -308.5, 16, -1326.5, 137, 0));
            map.setMaxY(26);
            map.setVoteSign(new Location(getLobby(), -21, 76, 1));
            maps.add(map);
        }
        {
            KitPvPMap map = new KitPvPMap("Desert");
            map.setBuilders("§b§lMod §bAlderius");
            map.setSpawns(Arrays.asList(new Location(getArena(), -463.5, 29.5, -1139, 20, 0), new Location(getArena(), -422.5, 36, -1140.5, 105, 0), new Location(getArena(), -484, 33, -1060, 155, 0), new Location(getArena(), -523, 40, -1084, -90, 0), new Location(getArena(), -516.5, 37, -1123.5, 140, 0), new Location(getArena(), -450, 29, -1042.5, 156, 0), new Location(getArena(), -430.5, 34, -1080.5, 93, 0), new Location(getArena(), -451.5, 29, -1098.5, 20, 0), new Location(getArena(), -430.5, 38, -1035.5, 135, 0), new Location(getArena(), -510.5, 36, -1099.5, -123, 0)));
            map.setSpectatorSpawn(new Location(getArena(), -465, 43.5, -1085, 89, 17));
            map.setMaxY(47);
            map.setVoteSign(new Location(getLobby(), -21, 76, -1));
            maps.add(map);
        }
        {
            KitPvPMap map = new KitPvPMap("Arena");
            map.setBuilders("§b§lMod §bAlderius");
            map.setSpawns(Arrays.asList(
                    new Location(getArena(), 8.5, 34, -1577.5, -90, 0),
                    new Location(getArena(), 39.5, 34, -1608.5, -30, 0),
                    new Location(getArena(), 88.5, 34, -1578.5, 120, 0),
                    new Location(getArena(), 65.5, 36, -1572.5, 62, 0),
                    new Location(getArena(), 28.5, 39, -1539.5, -130, 0),
                    new Location(getArena(), 84.5, 34, -1556.5, 60, 0),
                    new Location(getArena(), 37.5, 34, -1556.5, -140, 0),
                    new Location(getArena(), 47.5, 34, -1534.5, -166, 0),
                    new Location(getArena(), 23.5, 34, -1551.5, 0, 0),
                    new Location(getArena(), 55.5, 34, -1618.5, 40, 0)));
            map.setSpectatorSpawn(new Location(getArena(), 48.5, 49, -1576.5, 90, 90));
            map.setMaxY(53);
            map.setVoteSign(new Location(getLobby(), -21, 75, 0));
            maps.add(map);
        }
       /* {
            KitPvPMap map = new KitPvPMap("Sky Fortress");
            map.setBuilders("§d§lBuilder §dcasidas\n§6§lGold §6blackdeadgames\n§9§lDiamond §9gapisabelle\n§6§lGold §6Jurre101\n§b§lMod §bAlderius");
            map.setSpawns(Arrays.asList(
                    new Location(getArena(), 8.5, 34, -1577.5, -90, 0),
                    new Location(getArena(), 39.5, 34, -1608.5, -30, 0),
                    new Location(getArena(), 88.5, 34, -1578.5, 120, 0),
                    new Location(getArena(), 65.5, 36, -1572.5, 62, 0),
                    new Location(getArena(), 28.5, 39, -1539.5, -130, 0),
                    new Location(getArena(), 84.5, 34, -1556.5, 60, 0),
                    new Location(getArena(), 37.5, 34, -1556.5, -140, 0),
                    new Location(getArena(), 47.5, 34, -1534.5, -166, 0),
                    new Location(getArena(), 23.5, 34, -1551.5, 0, 0),
                    new Location(getArena(), 55.5, 34, -1618.5, 40, 0)));
            map.setSpectatorSpawn(new Location(getArena(), 48.5, 49, -1576.5, 90, 90));
            map.setMaxY(53);
            map.setVoteSign(new Location(getLobby(), -21, 75, 1));
            maps.add(map);
        }*/
    }

    private void spawnNpcs(){
        {
            NPC npc = new NPC(Mob.SKELETON, new Location(getLobby(), -11.5, 74, 10.5, -135, 0), "§b§lKit Selector", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new KitSelectorInv().open(player);
                }
            });
            npc.setSkeletonType(Skeleton.SkeletonType.WITHER);
            npc.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));

            getApi().registerNpc(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -5.5, 74, -7.5, -45, 0), new NPCArmorStand.InteractAction() {
                @Override
                public void click(Player player, NPCArmorStand npcArmorStand) {
                    getApi().getServerSelector().open(player);
                }
            });
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setUseItem(true);
            npc.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
            npc.setItemName("§3§lServer Selector");
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPC npc = new NPC(Mob.SKELETON, new Location(getLobby(), 10.5, 74, -11.5, 45, 0), "§e§lSpectate", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);

                    if(omp.getPet() != null)
                        omp.disablePet();
                    if(omp.hasTrailEnabled())
                        omp.disableTrail();

                    omp.setSpectator(true);
                    omp.teleportToMap();
                    omp.clearInventory();
                    omp.updateInventory();
                    getSpectatorKit().get(omp.getLanguage()).setItems(player);
                }
            });
            npc.setSkeletonType(Skeleton.SkeletonType.WITHER);
            npc.setItemInHand(new ItemStack(Material.ENDER_PEARL));

            getApi().registerNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.BLAZE, new Location(getLobby(), -4.5, 75, -11.5, 0, 0), "§e§lOMT Shop", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new KitPvPOMTShopInv().open(player);
                }
            });

            getApi().registerNpc(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -2.5, 75, 1, -135, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -2, 75, -2.5, -45, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 1.5, 75, -2, 45, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 1, 75, 1.5, 135, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -9.25, 73.5, 11.92, 90, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-2).setY(0).setZ(0.4));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.DIAMOND_SWORD, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -10.25, 73.1, 9.875, 90, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-3.1).setY(0).setZ(0));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.GOLDEN_APPLE, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -10.1, 73.15, 9.8, 60, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-3.1).setY(0).setZ(-0.25));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.GOLDEN_APPLE, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 9.5, 75, -12.5, 0, 0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setUseItem(true);
            npc.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 10.5, 74.5, -9.5, 0, 0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setUseItem(true);
            npc.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 2.5, 76, 8.5, -180, 0), new NPCArmorStand.InteractAction() {
                @Override
                public void click(Player player, NPCArmorStand npcArmorStand) {
                    new MasteryInv().open(player);
                }
            });
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setSmall(true);
            npc.setUseItem(true);
            npc.setItemStack(new ItemStack(Material.DIAMOND_SWORD, 1));
            npc.setItemName("§c§lMasteries");
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
            masteryNpc = npc;
        }
    }

    private void registerKits(){
        CustomItemNms itemNms = getApi().getNms().customItem();
        {// Knight Level 1 \\
            Kit kit = new Kit(KitPvPKit.KNIGHT.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lKnight §a§lLvL 1§8 || §bWeapon")), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lKnight §a§lLvL 1§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lKnight §a§lLvL 1§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lKnight §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lKnight §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lKnight §a§lLvL 1§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// Knight Level 2 \\
            Kit kit = new Kit(KitPvPKit.KNIGHT.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lKnight §a§lLvL 2§8 || §bWeapon")), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lKnight §a§lLvL 2§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lKnight §a§lLvL 2§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lKnight §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lKnight §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lKnight §a§lLvL 2§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// Knight Level 3 \\
            Kit kit = new Kit(KitPvPKit.KNIGHT.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lKnight §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lKnight §a§lLvL 3§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));
            kit.setItem(2, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lKnight §a§lLvL 3§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lKnight §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lKnight §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lKnight §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lKnight §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Archer Level 1 \\
            Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lArcher §a§lLvL 1§8 || §bWeapon")), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.BOW, 1, "§b§lArcher §a§lLvL 1§8 || §bBow")), 4));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 32, "§b§lArcher §a§lLvL 1§8 || §bArrow"));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lArcher §a§lLvL 1§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lArcher §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(204, 255, 51))), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lArcher §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(204, 255, 51))), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lArcher §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(204, 255, 51))), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lArcher §a§lLvL 1§8 || §bBoots"), Color.fromBGR(204, 255, 51))), 4));

            getApi().registerKit(kit);
        }
        {// Archer Level 2 \\
            Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lArcher §a§lLvL 2§8 || §bWeapon")), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lArcher §a§lLvL 2§8 || §bBow", 0, ProjectileType.LIGHTNING_I.getName()), Enchantment.DURABILITY, 1)), 5));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 32, "§b§lArcher §a§lLvL 2§8 || §bArrow"));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lArcher §a§lLvL 2§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lArcher §a§lLvL 2§8 || §bHelmet"), Color.fromBGR(204, 255, 51))), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lArcher §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(204, 255, 51))), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lArcher §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(204, 255, 51))), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lArcher §a§lLvL 2§8 || §bBoots"), Color.fromBGR(204, 255, 51))), 4));

            getApi().registerKit(kit);
        }
        {// Archer Level 3 \\
            Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lArcher §a§lLvL 3§8 || §bWeapon")), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lArcher §a§lLvL 3§8 || §bBow", 0, ProjectileType.LIGHTNING_I.getName()), Enchantment.ARROW_DAMAGE, 1)), 4));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 32, "§b§lArcher §a§lLvL 3§8 || §bArrow"));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lArcher §a§lLvL 3§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lArcher §a§lLvL 3§8 || §bHelmet"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lArcher §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lArcher §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lArcher §a§lLvL 3§8 || §bBoots"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Soldier Level 1 \\
            Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lSoldier §a§lLvL 1§8 || §bWeapon")), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.BOW, 1, "§b§lSoldier §a§lLvL 1§8 || §bBow")), 4));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 16, "§b§lSoldier §a§lLvL 1§8 || §bArrow"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lSoldier §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_CHESTPLATE, 1, "§b§lSoldier §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSoldier §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSoldier §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Soldier Level 2 \\
            Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lSoldier §a§lLvL 2§8 || §bWeapon", 0, ItemType.LIGHTNING_I.getName()), Enchantment.DURABILITY, 1)), 5));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.BOW, 1, "§b§lSoldier §a§lLvL 2§8 || §bBow")), 4));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 16, "§b§lSoldier §a§lLvL 2§8 || §bArrow"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lSoldier §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_CHESTPLATE, 1, "§b§lSoldier §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSoldier §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSoldier §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Soldier Level 3 \\
            Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lSoldier §a§lLvL 3§8 || §bWeapon", 0, ItemType.LIGHTNING_II.getName()), Enchantment.DAMAGE_ALL, 1)), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lSoldier §a§lLvL 3§8 || §bBow"), Enchantment.ARROW_DAMAGE, 1)), 4));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 20, "§b§lSoldier §a§lLvL 3§8 || §bArrow"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lSoldier §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_CHESTPLATE, 1, "§b§lSoldier §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSoldier §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSoldier §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Wizard Level 1 \\
            Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lWizard §a§lLvL 1§8 || §bWeapon")), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lWizard §a§lLvL 1§8 || §dRegeneration Potion"), PotionEffectType.REGENERATION, 45 * 20, 0, true));
            kit.setItem(2, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lWizard §a§lLvL 1§8 || §bSpeed Potion"), PotionEffectType.SPEED, 180 * 20, 0, true));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §7Weakness Potion"), PotionEffectType.WEAKNESS, 67 * 20, 0, true));
            kit.setItem(4, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));
            kit.setItem(5, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.GOLD_HELMET, 1, "§b§lWizard §a§lLvL 1§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lWizard §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lWizard §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lWizard §a§lLvL 1§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// Wizard Level 2 \\
            Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lWizard §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lWizard §a§lLvL 1§8 || §dRegeneration Potion"), PotionEffectType.REGENERATION, 45 * 20, 0, true));
            kit.setItem(2, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lWizard §a§lLvL 1§8 || §bSpeed Potion"), PotionEffectType.SPEED, 180 * 20, 0, true));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §7Weakness Potion"), PotionEffectType.WEAKNESS, 67 * 20, 0, true));
            kit.setItem(4, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));
            kit.setItem(5, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));
            kit.setItem(6, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BLAZE_ROD, 1, "§b§lWizard §a§lLvL 2§8 || §cFire Wand", 0, ItemType.FIRE_SPELL_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HELMET, 1, "§b§lWizard §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lWizard §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lWizard §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lWizard §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Wizard Level 3 \\
            Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lWizard §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lWizard §a§lLvL 1§8 || §dRegeneration Potion"), PotionEffectType.REGENERATION, 45 * 20, 0, true));
            kit.setItem(2, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lWizard §a§lLvL 1§8 || §bSpeed Potion"), PotionEffectType.SPEED, 180 * 20, 0, true));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §7Weakness Potion"), PotionEffectType.WEAKNESS, 67 * 20, 0, true));
            kit.setItem(4, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));
            kit.setItem(5, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), PotionEffectType.HEAL, 0, 0, true));
            kit.setItem(6, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BLAZE_ROD, 1, "§b§lWizard §a§lLvL 3§8 || §cFire Wand", 0, ItemType.FIRE_SPELL_II.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HELMET, 1, "§b§lWizard §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lWizard §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lWizard §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lWizard §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// Tank Level 1 \\
            Kit kit = new Kit(KitPvPKit.TANK.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.WOOD_SWORD, 1, "§b§lTank §a§lLvL 1§8 || §bWeapon"), Enchantment.KNOCKBACK, 1)), 4));
            kit.setItem(1, ItemUtils.itemstack(Material.GOLDEN_APPLE, 2, "§b§lTank §a§lLvL 1§8 || §eGolden Apple"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_HELMET, 1, "§b§lTank §a§lLvL 1§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.DIAMOND_CHESTPLATE, 1, "§b§lTank §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_LEGGINGS, 1, "§b§lTank §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_BOOTS, 1, "§b§lTank §a§lLvL 1§8 || §bBoots")), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));

            getApi().registerKit(kit);
        }
        {// Tank Level 2 \\
            Kit kit = new Kit(KitPvPKit.TANK.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lTank §a§lLvL 2§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
            kit.setItem(1, ItemUtils.itemstack(Material.GOLDEN_APPLE, 3, "§b§lTank §a§lLvL 2§8 || §eGolden Apple"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_HELMET, 1, "§b§lTank §a§lLvL 2§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.DIAMOND_CHESTPLATE, 1, "§b§lTank §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_LEGGINGS, 1, "§b§lTank §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_BOOTS, 1, "§b§lTank §a§lLvL 2§8 || §bBoots")), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));

            getApi().registerKit(kit);
        }
        {// Tank Level 3 \\
            Kit kit = new Kit(KitPvPKit.TANK.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lTank §a§lLvL 3§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
            kit.setItem(1, ItemUtils.itemstack(Material.GOLDEN_APPLE, 4, "§b§lTank §a§lLvL 3§8 || §eGolden Apple"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_HELMET, 1, "§b§lTank §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.DIAMOND_CHESTPLATE, 1, "§b§lTank §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_LEGGINGS, 1, "§b§lTank §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_BOOTS, 1, "§b§lTank §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));

            getApi().registerKit(kit);
        }
        {// Drunk Level 1 \\
            Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lDrunk §a§lLvL 1§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lDrunk §a§lLvL 1§8 || §5Strength Potion"), PotionEffectType.INCREASE_DAMAGE, 180 * 20, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lDrunk §a§lLvL 1§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lDrunk §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lDrunk §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lDrunk §a§lLvL 1§8 || §bBoots")), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 1));

            getApi().registerKit(kit);
        }
        {// Drunk Level 2 \\
            Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lDrunk §a§lLvL 2§8 || §bWeapon", 0, ItemType.BLINDNESS_I.getName()), Enchantment.KNOCKBACK, 2)), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lDrunk §a§lLvL 2§8 || §5Strength Potion"), PotionEffectType.INCREASE_DAMAGE, 180 * 20, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lDrunk §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lDrunk §a§lLvL 2§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lDrunk §a§lLvL 2§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lDrunk §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 0));

            getApi().registerKit(kit);
        }
        {// Drunk Level 3 \\
            Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lDrunk §a§lLvL 3§8 || §bWeapon", 0, ItemType.BLINDNESS_II.getName()), Enchantment.KNOCKBACK, 2)), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.POTION, 1, "§b§lDrunk §a§lLvL 3§8 || §5Strength Potion"), PotionEffectType.INCREASE_DAMAGE, 180 * 20, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lDrunk §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lDrunk §a§lLvL 3§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lDrunk §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lDrunk §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 0));

            getApi().registerKit(kit);
        }
        {// Pyro Level 1 \\
            Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lPyro §a§lLvL 1§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HELMET, 1, "§b§lPyro §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lPyro §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lPyro §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lPyro §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_FIRE, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));

            getApi().registerKit(kit);
        }
        {// Pyro Level 2 \\
            Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lPyro §a§lLvL 2§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2), Enchantment.DAMAGE_ALL, 1)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HELMET, 1, "§b§lPyro §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lPyro §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lPyro §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lPyro §a§lLvL 2§8 || §bBoots", 0, ArmorType.FIRE_TRAIL_I.getName()), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));

            getApi().registerKit(kit);
        }
        {// Pyro Level 3 \\
            Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lPyro §a§lLvL 3§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2), Enchantment.DAMAGE_ALL, 1)), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lPyro §a§lLvL 3§8 || §bBow"), Enchantment.ARROW_FIRE, 1)), 4));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 12, "§b§lPyro §a§lLvL 3§8 || §bArrow"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HELMET, 1, "§b§lPyro §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lPyro §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lPyro §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lPyro §a§lLvL 3§8 || §bBoots", 0, ArmorType.FIRE_TRAIL_I.getName()), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));

            getApi().registerKit(kit);
        }
        {// Bunny Level 1 \\
            Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lBunny §a§lLvL 1§8 || §bWeapon")), 4));
            kit.setItem(1, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §2Poison Potion"), PotionEffectType.POISON, 16 * 20, 1, true));
            kit.setItem(2, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), PotionEffectType.HARM, 0, 0, true));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), PotionEffectType.HARM, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lBunny §a§lLvL 1§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lBunny §a§lLvL 1§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lBunny §a§lLvL 1§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lBunny §a§lLvL 1§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));

            getApi().registerKit(kit);
        }
        {// Bunny Level 2 \\
            Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lBunny §a§lLvL 2§8 || §bWeapon")), 4));
            kit.setItem(1, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CARROT_ITEM, 1, "§b§lBunny §a§lLvL 2§8 || §6Knockback Carrot"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 10));
            kit.setItem(2, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §2Poison Potion"), PotionEffectType.POISON, 16 * 20, 1, true));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §2Poison Potion"), PotionEffectType.POISON, 16 * 20, 1, true));
            kit.setItem(4, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), PotionEffectType.HARM, 0, 0, true));
            kit.setItem(5, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), PotionEffectType.HARM, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lBunny §a§lLvL 2§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lBunny §a§lLvL 2§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lBunny §a§lLvL 2§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lBunny §a§lLvL 2§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));

            getApi().registerKit(kit);
        }
        {// Bunny Level 3 \\
            Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lBunny §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
            kit.setItem(1, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CARROT_ITEM, 1, "§b§lBunny §a§lLvL 3§8 || §6Knockback Carrot"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 10));
            kit.setItem(2, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §2Poison Potion"), PotionEffectType.POISON, 16 * 20, 1, true));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §2Poison Potion"), PotionEffectType.POISON, 16 * 20, 1, true));
            kit.setItem(4, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), PotionEffectType.HARM, 0, 0, true));
            kit.setItem(5, ItemUtils.addEffect(ItemUtils.itemstack(Material.SPLASH_POTION, 1, "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), PotionEffectType.HARM, 0, 0, true));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lBunny §a§lLvL 3§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lBunny §a§lLvL 3§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lBunny §a§lLvL 3§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lBunny §a§lLvL 3§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));

            getApi().registerKit(kit);
        }
        {// Necromancer Level 1 \\
            Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HOE, 1, "§b§lNecromancer §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8)), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lNecromancer §a§lLvL 1§8 || §bBow", 0, ProjectileType.UNDEATH_I.getName()), Enchantment.ARROW_DAMAGE, 1)), 4));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 5, "§b§lNecromancer §a§lLvL 1§8 || §bArrow"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lNecromancer §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lNecromancer §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lNecromancer §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lNecromancer §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Necromancer Level 2 \\
            Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HOE, 1, "§b§lNecromancer §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8)), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lNecromancer §a§lLvL 2§8 || §bBow", 0, ProjectileType.UNDEATH_II.getName()), Enchantment.ARROW_DAMAGE, 1)), 4));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 10, "§b§lNecromancer §a§lLvL 2§8 || §bArrow"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lNecromancer §a§lLvL 2§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lNecromancer §a§lLvL 2§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lNecromancer §a§lLvL 2§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lNecromancer §a§lLvL 2§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// Necromancer Level 3 \\
            Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HOE, 1, "§b§lNecromancer §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8)), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STICK, 1, "§b§lNecromancer §a§lLvL 3§8 || §8Necromancer's Staff", 0, ItemType.WITHER_I.getName()), Enchantment.DURABILITY, 1), 5));
            kit.setItem(2, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lNecromancer §a§lLvL 3§8 || §bBow", 0, ProjectileType.UNDEATH_II.getName()), Enchantment.ARROW_DAMAGE, 1)), 4));
            kit.setItem(3, ItemUtils.itemstack(Material.ARROW, 10, "§b§lNecromancer §a§lLvL 3§8 || §bArrow"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lNecromancer §a§lLvL 3§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lNecromancer §a§lLvL 3§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lNecromancer §a§lLvL 3§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lNecromancer §a§lLvL 3§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// King Level 1 \\
            Kit kit = new Kit(KitPvPKit.KING.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lKing §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.DIAMOND_HELMET, 1, "§b§lKing §a§lLvL 1§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lKing §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lKing §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lKing §a§lLvL 1§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// King Level 2 \\
            Kit kit = new Kit(KitPvPKit.KING.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lKing §a§lLvL 2§8 || §bWeapon§a", 0, ItemType.HEALING_I.getName()), Enchantment.DAMAGE_ALL, 1)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.DIAMOND_HELMET, 1, "§b§lKing §a§lLvL 2§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lKing §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lKing §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lKing §a§lLvL 2§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// King Level 3 \\
            Kit kit = new Kit(KitPvPKit.KING.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lKing §a§lLvL 3§8 || §bWeapon§a", 0, ItemType.HEALING_II.getName()), Enchantment.DAMAGE_ALL, 1)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.DIAMOND_HELMET, 1, "§b§lKing §a§lLvL 3§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lKing §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lKing §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lKing §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// Tree Level 1 \\
            Kit kit = new Kit(KitPvPKit.TREE.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEAVES, 1, "§b§lTree §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 4)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEAVES, 1, "§b§lTree §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lTree §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lTree §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lTree §a§lLvL 1§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// Tree Level 2 \\
            Kit kit = new Kit(KitPvPKit.TREE.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEAVES, 1, "§b§lTree §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LOG, 1, "§b§lTree §a§lLvL 2§8 || §dBarrier", 1, ItemType.BARRIER_I.getName()), Enchantment.DURABILITY, 1)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEAVES, 1, "§b§lTree §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lTree §a§lLvL 2§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lTree §a§lLvL 2§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lTree §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Tree Level 3 \\
            Kit kit = new Kit(KitPvPKit.TREE.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEAVES, 1, "§b§lTree §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LOG, 1, "§b§lTree §a§lLvL 3§8 || §dBarrier", 1, ItemType.BARRIER_II.getName()), Enchantment.DURABILITY, 1)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEAVES, 1, "§b§lTree §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lTree §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lTree §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lTree §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Blaze Level 1 \\
            Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BLAZE_POWDER, 1, "§b§lBlaze §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 4), Enchantment.FIRE_ASPECT, 2)), 4));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STAINED_CLAY, 1, "§b§lBlaze §a§lLvL 1§8 || §bHelmet", 4), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lBlaze §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lBlaze §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lBlaze §a§lLvL 1§8 || §bBoots"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));

            getApi().registerKit(kit);
        }
        {// Blaze Level 2 \\
            Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BLAZE_POWDER, 1, "§b§lBlaze §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.FIRE_ASPECT, 3)), 4));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STAINED_CLAY, 1, "§b§lBlaze §a§lLvL 2§8 || §bHelmet", 4), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lBlaze §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lBlaze §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lBlaze §a§lLvL 2§8 || §bBoots", 0, ArmorType.LIGHT_I.getName()), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));

            getApi().registerKit(kit);
        }
        {// Blaze Level 3 \\
            Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BLAZE_POWDER, 1, "§b§lBlaze §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.FIRE_ASPECT, 4)), 4));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STAINED_CLAY, 1, "§b§lBlaze §a§lLvL 3§8 || §bHelmet", 4), Enchantment.PROTECTION_ENVIRONMENTAL, 3), Enchantment.PROTECTION_FIRE, 3), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lBlaze §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lBlaze §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lBlaze §a§lLvL 3§8 || §bBoots", 0, ArmorType.LIGHT_I.getName()), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));

            getApi().registerKit(kit);
        }
        {// TNT Level 1 \\
            Kit kit = new Kit(KitPvPKit.TNT.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.TNT, 1, "§b§lTNT §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 3), 4));
            kit.setItem(1, ItemUtils.addEnchantment(ItemUtils.itemstack(Material.REDSTONE_TORCH_ON, 1, "§b§lTNT §a§lLvL 1§8 || §4TNT Launcher", 8193, ItemType.TNT_I.getName()), Enchantment.DURABILITY, 1));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.TNT, 1, "§b§lTNT §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lTNT §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lTNT §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lTNT §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));

            getApi().registerKit(kit);
        }
        {// TNT Level 2 \\
            Kit kit = new Kit(KitPvPKit.TNT.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.TNT, 1, "§b§lTNT §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 4), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lTNT §a§lLvL 2§8 || §bBow", 0, ProjectileType.EXPLOSIVE_I.getName()), Enchantment.DURABILITY, 1)), 5));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 2, "§b§lTNT §a§lLvL 2§8 || §bArrow"));
            kit.setItem(3, ItemUtils.addEnchantment(ItemUtils.itemstack(Material.REDSTONE_TORCH_ON, 1, "§b§lTNT §a§lLvL 2§8 || §4TNT Launcher", 0, ItemType.TNT_I.getName()), Enchantment.DURABILITY, 1));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.TNT, 1, "§b§lTNT §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lTNT §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 2), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lTNT §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lTNT §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 2)), 4));

            getApi().registerKit(kit);
        }
        {// TNT Level 3 \\
            Kit kit = new Kit(KitPvPKit.TNT.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.TNT, 1, "§b§lTNT §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 4), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lTNT §a§lLvL 3§8 || §bBow", 0, ProjectileType.EXPLOSIVE_I.getName()), Enchantment.DURABILITY, 1)), 5));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 4, "§b§lTNT §a§lLvL 3§8 || §bArrow"));
            kit.setItem(3, ItemUtils.addEnchantment(ItemUtils.itemstack(Material.REDSTONE_TORCH_ON, 1, "§b§lTNT §a§lLvL 3§8 || §4TNT Launcher", 0, ItemType.TNT_I.getName()), Enchantment.DURABILITY, 1));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.TNT, 1, "§b§lTNT §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§b§lTNT §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§b§lTNT §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§b§lTNT §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Fisherman Level 1 \\
            Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.FISHING_ROD, 1, "§b§lFisherman §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.RAW_FISH, 1, "§b§lFisherman §a§lLvL 1§8 || §bFish", 1), Enchantment.KNOCKBACK, 4), Enchantment.FIRE_ASPECT, 2), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lFisherman §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lFisherman §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lFisherman §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lFisherman §a§lLvL 1§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// Fisherman Level 2 \\
            Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.FISHING_ROD, 1, "§b§lFisherman §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.RAW_FISH, 1, "§b§lFisherman §a§lLvL 2§8 || §bFish", 1), Enchantment.KNOCKBACK, 4), Enchantment.FIRE_ASPECT, 2), 4));
            kit.setItem(2, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.RAW_FISH, 1, "§b§lFisherman §a§lLvL 2§8 || §9Fish Attack", 3, ItemType.FISH_ATTACK_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lFisherman §a§lLvL 2§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lFisherman §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lFisherman §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lFisherman §a§lLvL 2§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// Fisherman Level 3 \\
            Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.FISHING_ROD, 1, "§b§lFisherman §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.RAW_FISH, 1, "§b§lFisherman §a§lLvL 3§8 || §bFish", 1), Enchantment.KNOCKBACK, 5), Enchantment.FIRE_ASPECT, 2), 4));
            kit.setItem(2, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.RAW_FISH, 1, "§b§lFisherman §a§lLvL 3§8 || §9Fish Attack", 3, ItemType.FISH_ATTACK_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lFisherman §a§lLvL 3§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lFisherman §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lFisherman §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lFisherman §a§lLvL 3§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));

            getApi().registerKit(kit);
        }
        {// SnowGolem Level 1 \\
            Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.SNOW_BALL, 1, "§b§lSnowGolem §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.KNOCKBACK, 2), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.PUMPKIN, 1, "§b§lSnowGolem §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lSnowGolem §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSnowGolem §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSnowGolem §a§lLvL 1§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));

            getApi().registerKit(kit);
        }
        {// SnowGolem Level 2 \\
            Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.SNOW_BALL, 1, "§b§lSnowGolem §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.KNOCKBACK, 2), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_FENCE, 1, "§b§lSnowGolem §a§lLvL 2§8 || §7Shield", 3, ItemType.SHIELD_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.PUMPKIN, 1, "§b§lSnowGolem §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lSnowGolem §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSnowGolem §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSnowGolem §a§lLvL 2§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));

            getApi().registerKit(kit);
        }
        {// SnowGolem Level 3 \\
            Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.SNOW_BALL, 1, "§b§lSnowGolem §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 7), Enchantment.KNOCKBACK, 3), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_FENCE, 1, "§b§lSnowGolem §a§lLvL 3§8 || §7Shield", 3, ItemType.SHIELD_II.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.PUMPKIN, 1, "§b§lSnowGolem §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lSnowGolem §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSnowGolem §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSnowGolem §a§lLvL 3§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));

            getApi().registerKit(kit);
        }
        {// Librarian Level 1 \\
            Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.PAPER, 1, "§b§lLibrarian §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOOKSHELF, 1, "§b§lLibrarian §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lLibrarian §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lLibrarian §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lLibrarian §a§lLvL 1§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// Librarian Level 2 \\
            Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.PAPER, 1, "§b§lLibrarian §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOOK, 1, "§b§lLibrarian §a§lLvL 2§8 || §6Knockback Book"), Enchantment.KNOCKBACK, 5), 4));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOOKSHELF, 1, "§b§lLibrarian §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lLibrarian §a§lLvL 2§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lLibrarian §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lLibrarian §a§lLvL 2§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// Librarian Level 3 \\
            Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.PAPER, 1, "§b§lLibrarian §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOOK, 1, "§b§lLibrarian §a§lLvL 3§8 || §6Knockback Book"), Enchantment.KNOCKBACK, 6), 4));

            kit.setHelmet(itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOOKSHELF, 1, "§b§lLibrarian §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lLibrarian §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lLibrarian §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lLibrarian §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Spider Level 1 \\
            Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.SPIDER_EYE, 1, "§b§lSpider §a§lLvL 1§8 || §bWeapon", 0, ItemType.ARTHROPODS_I.getName()), Enchantment.DAMAGE_ALL, 6)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lSpider §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lSpider §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSpider §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSpider §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Spider Level 2 \\
            Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.SPIDER_EYE, 1, "§b§lSpider §a§lLvL 2§8 || §bWeapon", 0, ItemType.ARTHROPODS_II.getName()), Enchantment.DAMAGE_ALL, 6)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lSpider §a§lLvL 2§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lSpider §a§lLvL 2§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSpider §a§lLvL 2§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSpider §a§lLvL 2§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// Spider Level 3 \\
            Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.SPIDER_EYE, 1, "§b§lSpider §a§lLvL 3§8 || §bWeapon", 0, ItemType.ARTHROPODS_III.getName()), Enchantment.DAMAGE_ALL, 6)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lSpider §a§lLvL 3§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lSpider §a§lLvL 3§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lSpider §a§lLvL 3§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lSpider §a§lLvL 3§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// Villager Level 1 \\
            Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.EMERALD, 1, "§b§lVillager §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lVillager §a§lLvL 1§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lVillager §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lVillager §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lVillager §a§lLvL 1§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// Villager Level 2 \\
            Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 2");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.EMERALD, 1, "§b§lVillager §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), Enchantment.FIRE_ASPECT, 1), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lVillager §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lVillager §a§lLvL 2§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lVillager §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lVillager §a§lLvL 2§8 || §bBoots")), 4));

            getApi().registerKit(kit);
        }
        {// Villager Level 3 \\
            Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 3");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.EMERALD, 1, "§b§lVillager §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), Enchantment.FIRE_ASPECT, 1), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BREAD, 1, "§b§lVillager §a§lLvL 3§8 || §aTrade System", 0, ItemType.TRADE_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lVillager §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lVillager §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lVillager §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lVillager §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Assassin Level 1 \\
            Kit kit = new Kit(KitPvPKit.ASSASSIN.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.DIAMOND_SWORD, 1, "§b§lAssassin §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lAssassin §a§lLvL 1§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lAssassin §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lAssassin §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.DIAMOND_BOOTS, 1, "§b§lAssassin §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FALL, 3)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 1));

            getApi().registerKit(kit);
        }
        {// Lord Level 1 \\
            Kit kit = new Kit(KitPvPKit.LORD.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.GOLD_SWORD, 1, "§b§lLord §a§lLvL 1§8 || §bWeapon")), 4));
            kit.setItem(1, ItemUtils.itemstack(Material.GOLDEN_APPLE, 5, "§b§lLord §a§lLvL 1§8 || §eGolden Apple"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_HELMET, 1, "§b§lLord §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
            //kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lLord §a§lLvL 1§8 || §bChestplate")), 4));
            //kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lLord §a§lLvL 1§8 || §bLeggings")), 4));
            //kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lLord §a§lLvL 1§8 || §bBoots")), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2000000, 1));

            getApi().registerKit(kit);
        }
        {// Vampire Level 1 \\
            Kit kit = new Kit(KitPvPKit.VAMPIRE.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lVampire §a§lLvL 1§8 || §bWeapon", 0, ItemType.VAMPIRE_I.getName()), Enchantment.DURABILITY, 1)), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lVampire §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lVampire §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lVampire §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lVampire §a§lLvL 1§8 || §bBoots"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// DarkMage Level 1 \\
            Kit kit = new Kit(KitPvPKit.DARKMAGE.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lDarkMage §a§lLvL 1§8 || §bWeapon§5", 0, ItemType.MAGIC_I.getName()), Enchantment.DAMAGE_ALL, 1)), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BREWING_STAND_ITEM, 1, "§b§lDarkMage §a§lLvL 1§8 || §ePotion Launcher", 0, ItemType.POTION_LAUNCHER_I.getName()), Enchantment.DURABILITY, 1)), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lDarkMage §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lDarkMage §a§lLvL 1§8 || §bChestplate", 0, ArmorType.WITHER_ARMOR_I.getName()), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lDarkMage §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lDarkMage §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Beast Level 1 \\
            Kit kit = new Kit(KitPvPKit.BEAST.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_AXE, 1, "§b§lBeast §a§lLvL 1§8 || §bWeapon", 0, ItemType.KNOCKUP_I.getName()), Enchantment.DAMAGE_ALL, 1)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lBeast §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lBeast §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lBeast §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lBeast §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
        {// Fish Level 1 \\
            Kit kit = new Kit(KitPvPKit.FISH.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.RAW_FISH, 1, "§b§lFish §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.RAW_FISH, 1, "§b§lFish §a§lLvL 1§8 || §6Knockback Fish", 2), Enchantment.KNOCKBACK, 5), 4));
            kit.setItem(2, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.COOKED_FISH, 1, "§b§lFish §a§lLvL 1§8 || §4Hot Fish"), Enchantment.DAMAGE_ALL, 2), Enchantment.FIRE_ASPECT, 2), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§b§lFish §a§lLvL 1§8 || §bHelmet")), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§b§lFish §a§lLvL 1§8 || §bChestplate")), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§b§lFish §a§lLvL 1§8 || §bLeggings")), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§b§lFish §a§lLvL 1§8 || §bBoots")), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2000000, 2));

            getApi().registerKit(kit);
        }
        {// Heavy Level 1 \\
            Kit kit = new Kit(KitPvPKit.HEAVY.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§b§lHeavy §a§lLvL 1§8 || §bWeapon")), 4));
            kit.setItem(1, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.BOW, 1, "§b§lHeavy §a§lLvL 1§8 || §bBow", 0, ProjectileType.ARROW_SPLIT_I.getName()), Enchantment.DURABILITY, 1)), 5));
            kit.setItem(2, ItemUtils.itemstack(Material.ARROW, 8, "§b§lHeavy §a§lLvL 1§8 || §bArrow"));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lHeavy §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lHeavy §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lHeavy §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lHeavy §a§lLvL 1§8 || §bBoots"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));

            getApi().registerKit(kit);
        }
        {// GrimReaper Level 1 \\
            Kit kit = new Kit(KitPvPKit.GRIMREAPER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_AXE, 1, "§b§lGrimReaper §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 3)), 4));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.SKULL_ITEM, 1, "§b§lGrimReaper §a§lLvL 1§8 || §bHelmet", 1), Enchantment.PROTECTION_ENVIRONMENTAL, 7)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lGrimReaper §a§lLvL 1§8 || §bChestplate", 0, ArmorType.MOLTEN_ARMOR_I.getName()), Color.fromBGR(100, 100, 100)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lGrimReaper §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(100, 100, 100))), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lGrimReaper §a§lLvL 1§8 || §bBoots"), Color.fromBGR(100, 100, 100))), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 0));

            getApi().registerKit(kit);
        }
        {// Miner Level 1 \\
            Kit kit = new Kit(KitPvPKit.MINER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_PICKAXE, 1, "§b§lMiner §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.COAL, 1, "§b§lMiner §a§lLvL 1§8 || §1Miner Power", 0, ItemType.HEALING_KIT_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lMiner §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lMiner §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lMiner §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lMiner §a§lLvL 1§8 || §bBoots"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 0));

            getApi().registerKit(kit);
        }
        {// Farmer Level 1 \\
            Kit kit = new Kit(KitPvPKit.FARMER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STONE_SWORD, 1, "§b§lFarmer §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 2)), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.HAY_BLOCK, 1, "§b§lFarmer §a§lLvL 1§8 || §eBlock Explosion", 0, ItemType.BLOCK_EXPLOSION_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lFarmer §a§lLvL 1§8 || §bHelmet"), Color.YELLOW), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lFarmer §a§lLvL 1§8 || §bChestplate"), Color.YELLOW)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lFarmer §a§lLvL 1§8 || §bLeggings"), Color.YELLOW)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lFarmer §a§lLvL 1§8 || §bBoots"), Color.YELLOW), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));

            getApi().registerKit(kit);
        }
        {// Undeath King Level 1 \\
            Kit kit = new Kit(KitPvPKit.UNDEATH_KING.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GOLD_SWORD, 1, "§b§lUndeath King §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
            kit.setItem(1, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.ROTTEN_FLESH, 1, "§b§lUndeath King §a§lLvL 1§8 || §dSummon the Undeath", 0, ItemType.UNDEATH_SUMMON_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lUndeath King §a§lLvL 1§8 || §bHelmet"), Color.FUCHSIA)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lUndeath King §a§lLvL 1§8 || §bChestplate"), Color.FUCHSIA)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lUndeath King §a§lLvL 1§8 || §bLeggings"), Color.FUCHSIA)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lUndeath King §a§lLvL 1§8 || §bBoots"), Color.FUCHSIA)), 4));

            getApi().registerKit(kit);
        }
        {// Engineer Level 1 \\
            Kit kit = new Kit(KitPvPKit.ENGINEER.getName() + " 1");
            kit.setItem(0, itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§b§lEngineer §a§lLvL 1§8 || §c§lRed §8|| §bWeapon§f", 0, ItemType.PAINTBALLS_I.getName()), Enchantment.DURABILITY, 1), 5));

            kit.setHelmet(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§b§lEngineer §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setChestplate(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§b§lEngineer §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setLeggings(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§b§lEngineer §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
            kit.setBoots(itemNms.hideFlags(itemNms.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§b§lEngineer §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));

            getApi().registerKit(kit);
        }
    }
}
