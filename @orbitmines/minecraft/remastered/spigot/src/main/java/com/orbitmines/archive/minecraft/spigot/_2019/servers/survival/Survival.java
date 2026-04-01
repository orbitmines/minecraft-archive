package com.orbitmines.archive.minecraft.spigot._2019.servers.survival;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.TabListHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalRegion;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.EnterVoidEvent;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.SignLogEvent;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.SpawnLocationEvent;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.chat.SurvivalChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.claim.ClaimHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.*;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.claim.CommandClaim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.claim.CommandClaimList;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.home.CommandDelHome;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.home.CommandHome;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.home.CommandHomes;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.home.CommandSetHome;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.region.CommandNearby;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.region.CommandRegion;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.warp.CommandMyWarps;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.warp.CommandWarp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.ChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Warp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.datapoints.SurvivalDataPointEndReset;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.datapoints.SurvivalDataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.datapoints.SurvivalDataPointNetherReset;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.datapoints.SurvivalDataPointSpawnpoint;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events.*;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.SurvivalPrismSolarShopGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items.ChestShopBlockTarget;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items.ClaimTool;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items.PetTicket;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.leaderboards.LeaderBoardCredits;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.loot.SurvivalLootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region.Region;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region.RegionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.runnables.ChestShopRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.runnables.ChunkLimiterRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.runnables.ClaimAchievementRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.runnables.WorldSaveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.teleportable.SurvivalSpawn;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ConsoleUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.Hologram;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.MobNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.Prevention;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Survival extends OMServer<Survival, SurvivalPlayer> {

    public static ItemBuilder SPAWNER_MINER = new ItemBuilder(Material.DIAMOND_PICKAXE, 1, "§5§lSpawner Miner", "§7§oOne time use§4").addEnchantment(Enchantment.SILK_TOUCH, 1).unbreakable(true).addFlag(ItemFlag.HIDE_ATTRIBUTES);

    public static ItemBuilder PET_TICKET = new ItemBuilder(Material.NAME_TAG, 1, "§6§lPet Ticket").addEnchantment(Enchantment.UNBREAKING, 1).addFlag(ItemFlag.HIDE_ENCHANTS);

    public static long PHANTOM_DELAY_AFTER_BED_ENTER = TimeUnit.HOURS.toMillis(2);

    public static final int MAX_ENTITY_CRAMMING = 16;
    public static final int MAX_ENTITY_PER_CHUNK = 48;
    public static final int TICKS_FOR_ITEM_AS_REMOVABLE_CANDIDATE = 20 * 120;

    @Getter private ArrayList<ChestShop> chestShops = new ArrayList<>();
    @Getter private ArrayList<Warp> warps = new ArrayList<>();
    @Getter private ClaimHandler claimHandler;

    @Getter private World world;
    @Setter @Getter private World worldNether;
    @Setter @Getter private World worldTheEnd;

    private List<Location> spawnLocations;
    @Getter private SurvivalSpawn spawnTeleportable;

    @Override
    public SurvivalPlayer newPlayerInstance(Player player) {
        return new SurvivalPlayer(player, this);
    }

    @Override
    public void afterStartupSync() {
        claimHandler = new ClaimHandler(this);

        spawnTeleportable = new SurvivalSpawn(this);

        Prevention.prevent(this, getLobby().getWorld(),
            Prevention.BLOCK_BREAK,
            Prevention.BLOCK_INTERACTING,
            Prevention.BLOCK_PLACE,
//            Prevention.CHUNK_UNLOAD,
            Prevention.ENTITY_INTERACTING,
            Prevention.LEAF_DECAY,
            Prevention.PLAYER_DAMAGE,
            Prevention.WEATHER_CHANGE,
            Prevention.MONSTER_EGG_USAGE,
            Prevention.BUCKET_USAGE,
            Prevention.FOOD_CHANGE
        );
        getLobby().getWorld().setPVP(false);
        getLobby().getWorld().setTime(6000);

        world = Bukkit.getWorld("world");
        world.getWorldBorder().setCenter(0.5, 0.5);
        world.getWorldBorder().setSize(Region.WORLD_BORDER);
        world.setPVP(false);
        world.setAutoSave(false);
        /* GameRule.DO_FIRE_TICK removed in 26.1 */
        world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, MAX_ENTITY_CRAMMING);
        Region.WORLD = world;

        worldNether = Bukkit.getWorld("world_nether");
        worldNether.setPVP(false);
        worldNether.setAutoSave(false);
        /* GameRule.DO_FIRE_TICK removed in 26.1 */
        world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, MAX_ENTITY_CRAMMING);

        worldTheEnd = Bukkit.getWorld("world_the_end");
        worldTheEnd.setPVP(false);
        worldTheEnd.setAutoSave(false);
        /* GameRule.DO_FIRE_TICK removed in 26.1 */
        world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, MAX_ENTITY_CRAMMING);

        registerEvents(
            new SpawnLocationEvent() {
                @Override
                public Location getSpawnLocation(Player player) {
                    if (player.hasPlayedBefore())
                        return null;

                    Location location = RandomUtils.randomFrom(spawnLocations);
                    location.setWorld(getLobby().getWorld());
                    return location;
                }
            },
            new EnterVoidEvent<Survival, SurvivalPlayer>(this, getLobby().getWorld()) {
                @Override
                public Location getRespawnLocation(SurvivalPlayer player) {
                    return RandomUtils.randomFrom(spawnLocations);
                }
            },
            new SignLogEvent<>(this),
            new AchievementEvents(this),
            new DeathEvent(this),
            new FlyEvent(this),
            new PhantomEvent(this),
            new SignColorEvent(this),
            new SpawnerMinerEvents(this),
            new TeleportingEvents(this),
            new ClaimEvents(this),
            new ChestShopEvents(this)
        );

        setupRegions();
        setupClaims();
        setupItems();

        registerCommands(
            new CommandClaim(this),
            new CommandClaimList(this),
            new CommandDelHome(this),
            new CommandHome(this),
            new CommandHomes(this),
            new CommandSetHome(this),
            new CommandNearby(this),
            new CommandRegion(this),
            new CommandMyWarps(this),
            new CommandWarp(this),
            new CommandBack(this),
            new CommandCredits(this),
            new CommandEnderchest(this),
            new CommandPay(this),
            new CommandPet(this),
            new CommandPrismShop(this),
            new CommandSpawn(this),
            new CommandTeleport(this),
            new CommandTeleportAccept(this),
            new CommandTeleportHere(this),
            new CommandFly(this),
            new CommandWorkbench(this)
        );

        this.chestShops.addAll(ChestShop.getAll());
        this.warps.addAll(Warp.getAll());

        /* DataPoints */
        spawnLocations = ((SurvivalDataPointSpawnpoint) (getLobby().getDataPointHandler().getDataPoint(SurvivalDataPointHandler.Type.SPAWNPOINT))).getSpawns();

        for (Location location : ((SurvivalDataPointNetherReset) (getLobby().getDataPointHandler().getDataPoint(SurvivalDataPointHandler.Type.NETHER_RESET))).getLocations()) {
            ResetTimer timer = ResetTimer.NETHER_RESET;

            Hologram hologram = new Hologram(location, 1, Hologram.Face.UP);
            hologram.addLine(() -> timer.getDisplayName() + " Reset", false);
            hologram.addLine(() -> "§7" + timer.getResetInString(Language.ENGLISH), false);
            hologram.create();

            timer.getHolograms().add(hologram);
        }
        for (Location location : ((SurvivalDataPointEndReset) (getLobby().getDataPointHandler().getDataPoint(SurvivalDataPointHandler.Type.END_RESET))).getLocations()) {
            ResetTimer timer = ResetTimer.END_RESET;

            Hologram hologram = new Hologram(location, 0, Hologram.Face.UP);
            hologram.addLine(() -> timer.getDisplayName() + " Reset", false);
            hologram.addLine(() -> "§7" + timer.getResetInString(Language.ENGLISH), false);
            hologram.create();

            timer.getHolograms().add(hologram);
        }

        /* Setup Nether/End Reset Timers */
        for (ResetTimer timer : ResetTimer.values()) {
            timer.setup(this);
        }
    }

    private void registerCommands(Command<Survival, SurvivalPlayer>... commands) {
        for (Command<Survival, SurvivalPlayer> command : commands) {
            command.register();
        }
    }

    @Override
    protected void registerRunnables() {
        super.registerRunnables();

        new ClaimAchievementRunnable(this).async().start();
        new ChestShopRunnable(this).async().start();
        new WorldSaveRunnable(this).start();
        new ChunkLimiterRunnable(this).async().start();
    }

    public Location getSpawn() {
        return RandomUtils.randomFrom(spawnLocations);
    }

    public ChestShop getChestShop(Block block) {
        for (ChestShop shop : chestShops) {
            if (BlockUtils.equals(shop.getLocation().getBlock(), block))
                return shop;
        }

        return null;
    }

    public boolean canClaimIn(World world) {
        return this.world.equals(world) || this.worldNether.equals(world) || this.worldTheEnd.equals(world);
    }

    public boolean canEditOtherClaims(SurvivalPlayer omp) {
        return omp.isEligible(StaffRank.ADMIN) && omp.isOpMode();
    }

    @Override
    public Server getType() {
        return Server.SURVIVAL;
    }

    @Override
    public ChatHandler newChatHandler(PlayerInstance sender, ChatHandler.Type type, String message) {
        return new SurvivalChatHandler(this, type, sender, message);
    }

    @Override
    protected Survival instance() {
        return this;
    }

    @Override
    public TabListHandler newTabListHandler(SurvivalPlayer player) {
        return new TabListHandler<>(this, player);
    }

    @Override
    public LootHandler newLootHandler(SurvivalPlayer player) {
        return new SurvivalLootHandler(player);
    }

    @Override
    public boolean clearPlayerData() {
        return false;
    }

    @Override
    public boolean saveChunksOnRestart() {
        return true;
    }

    @Override
    public boolean broadcastWhenSaving() {
        return true;
    }

    @Override
    public boolean shouldSetupLobby() {
        return true;
    }

    @Override
    public DataPointHandler createDataPointHandler(OMMap.Type type) {
        return new SurvivalDataPointHandler();
    }

    @Override
    public void setupNpc(String string, Location location) {
        switch (string.toUpperCase()) {
            case "SURVIVAL_SHOP": {
                MobNpc npc = new MobNpc<>(Mob.DOLPHIN, location, () -> "§8§lOrbit§7§lMines " + Server.SURVIVAL.getDisplayName(), () -> "§9§lPrism §7§l& §e§lSolar §3§lShop");
                npc.setInteractAction((event, player) -> new SurvivalPrismSolarShopGUI((SurvivalPlayer) player).open());

                npc.create();
                break;
            }
        }
    }

    @Override
    protected void instantiateLeaderBoards() {
        new LeaderBoardCredits();
    }

    public Warp getWarp(String name) {
        for (Warp warp : this.warps) {
            if (warp.getName().equalsIgnoreCase(name))
                return warp;
        }
        return null;
    }

    private void setupItems() {
        new ClaimTool(this);
        new ClaimTool.Hover(this);
        new PetTicket(this);

        new ChestShopBlockTarget(this);
    }

    private void setupClaims() {
        for (SurvivalClaim claim : SurvivalClaim.getAll(SurvivalClaim.class)) {
            claimHandler.addClaim(new Claim(claim), false);
        }

        /* Setup Spawn Protection for The End, so that no-one can claim it */
        Claim endClaim = new Claim(null, new Location(worldTheEnd, -200, 0, -200), new Location(worldTheEnd, 200, 0, 200));
        endClaim.setName("End Claim");
        claimHandler.addClaim(endClaim, false);

        ConsoleUtils.success(claimHandler.getClaims().size() + " Claims loaded.");
    }

    private void setupRegions() {
        ArrayList<SurvivalRegion> loaded = SurvivalRegion.getAll(SurvivalRegion.class);
        for (SurvivalRegion region : loaded) {
            new Region(region);
        }

        int count = Environment.get() == Environment.development ? 25 : Region.REGION_COUNT;

        loop:
        for (int i = 1; i <= count; i++) {
            for (SurvivalRegion region : loaded) {
                if (region.getId() == i)
                    continue loop;
            }


            RegionBuilder builder = new RegionBuilder(world, i);
            builder.build();

            Location location = builder.getFixedSpawnLocation();
            SurvivalRegion newRegion = new SurvivalRegion(
                (long) i,
                location,
                builder.getBeaconLocation(),
                builder.getInventoryX(),
                builder.getInventoryY(),
                builder.isUnderWaterRegion(),
                location.getWorld().getBiome(location.getBlockX(), location.getBlockZ()),
                DateUtils.now()
            );
            newRegion.insert();

            new Region(newRegion);

            world.save();
        }
    }
}
