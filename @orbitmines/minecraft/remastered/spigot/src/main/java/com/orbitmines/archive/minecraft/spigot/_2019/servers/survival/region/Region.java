package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalRegion;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.teleportable.Teleportable;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.utils.BiomeUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.VectorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
* Survival - @author Fadi Shawki - 2017
*/
public class Region implements Teleportable {

    /*
        Regions are setup in a square, example:
        1 1 1
        1 1 1
        1 1 1
        3x3

        ...
        30x30 (900 regions, this will make the regions range from 15 * 1500 = 22,500 -> -11,250 <-> 11,250)
     */
    public static final int REGION_SIZE = 15;
    public static final int START_X = 0;
    public static final int START_Z = 0;
    public static final int OFFSET = 1500;
    /* Blocks away from Region (diameter) */
    public static final int PROTECTION = 8;

    public static int REGION_COUNT = REGION_SIZE * REGION_SIZE;
    public static final int LAST_REGION_DISTANCE = (REGION_SIZE / 2) * OFFSET;
    public static int TELEPORTABLE = 10 * 10;
//    public static final int WORLD_BORDER = (int) Math.sqrt(TELEPORTABLE) * OFFSET + 10000; /* World size: 50kx50k, Additional 10k blocks surrounding the regions, *outer space* */
    public static final int WORLD_BORDER = 30000;

    public static World WORLD;

//    public static PotionBuilder UNDERWATER_POTION = new PotionBuilder(PotionEffectType.CONDUIT_POWER, 260, 0, true, true);

    private static List<Region> regions = new ArrayList<>();
    private static List<Region> aboveWaterRegions = new ArrayList<>();

    private static Cooldown REGION_INTERACT = new Cooldown(2000);

    private final SurvivalRegion model;

    private final ItemBuilder icon;

    public Region(SurvivalRegion model) {
        regions.add(this);

        this.model = model;

        this.icon = toItemBuilder();

        if (getId() > Region.TELEPORTABLE)
            return;

        if (!isUnderWater())
            aboveWaterRegions.add(this);
    }

    public ItemBuilder getIcon() {
        return icon.clone();
    }

    @Override
    public int getDuration(SurvivalPlayer player) {
        return 3;
    }

    @Override
    public Color getColor() {
        return Color.LIME;
    }

    @Override
    public String getName() {
        return "Region " + getId();
    }

    @Override
    public void onTeleport(SurvivalPlayer player, Location from, Location to) {
        player.setBackLocation(from);

        SpigotServer.getInstance().runSync(() -> {
            Block block = getBeaconLocation().getBlock();

            if ((block.getState() instanceof Beacon))
                return;

            Beacon beacon = (Beacon) block.getState();
            beacon.setPrimaryEffect(isUnderWater() ? PotionEffectType.CONDUIT_POWER : PotionEffectType.SPEED);
            beacon.update(true);
        });
    }

    /*

        SurvivalRegion Delegates

     */

    public Long getId() {
        return model.getId();
    }

    @Override
    public Location getLocation() {
        return model.getLocation();
    }


    public Location getBeaconLocation() {
        return model.getBeaconLocation();
    }

    public int getInventoryX() {
        return model.getInventoryX();
    }

    public int getInventoryY() {
        return model.getInventoryY();
    }

    public boolean isUnderWater() {
        return model.isUnderWater();
    }

    public Biome getBiome() {
        return model.getBiome();
    }

    public Date getCreatedOn() {
        return model.getCreatedOn();
    }

    public void insert() {
        model.insert();
    }

    public void update(SurvivalRegion.column... columns) {
        model.update(columns);
    }

    public void delete() {
        model.delete();
    }

    public boolean isInserted() {
        return model.isInserted();
    }

    public boolean isDestroyed() {
        return model.isDestroyed();
    }

    public void reload() {
        model.reload();
    }

    public void insertOrUpdate(SurvivalRegion.column... columns) {
        model.insertOrUpdate(columns);
    }

    public static Region getRegion(int id) {
        for (Region region : regions) {
            if (region.getId() == id)
                return region;
        }
        return null;
    }

    public static boolean isInRegion(Location location) {
        return isInRegion(null, location);
    }

    public static boolean isInRegion(SurvivalPlayer player, Location location) {
        if (!Region.WORLD.getName().equals(location.getWorld().getName()) || player != null && player.isOpMode())
            return false;

        int x = Math.abs(location.getBlockX());
        int z = Math.abs(location.getBlockZ());

        if (x > LAST_REGION_DISTANCE + PROTECTION || z > LAST_REGION_DISTANCE + PROTECTION)
            return false;

        int xDistance = x % OFFSET;
        if (xDistance > OFFSET / 2)
            xDistance = Math.abs(xDistance - OFFSET);

        int zDistance = z % OFFSET;
        if (zDistance > OFFSET / 2)
            zDistance = Math.abs(zDistance - OFFSET);

        Location l1 = new Location(location.getWorld(), 0, 0, 0);
        Location l2 = new Location(location.getWorld(), xDistance, 0, zDistance);

        double distance = l1.distance(l2);

        boolean inRegion = distance <= PROTECTION;

        if (player != null && inRegion && !player.onCooldown(REGION_INTERACT)) {
            new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.region.interacting"), 60).send();

            player.resetCooldown(REGION_INTERACT);
        }
        return inRegion;
    }

    public static Region random() {
        return RandomUtils.randomFrom(regions);
    }

    public static Region randomTeleportable(boolean aboveWater) {
        if (aboveWater)
            return RandomUtils.randomFrom(aboveWaterRegions);

        return RandomUtils.randomFrom(regions.subList(0, TELEPORTABLE - 1));
    }

    public static List<Region> getRegions() {
        return regions;
    }

    public static Region getRegion(int inventoryX, int inventoryY) {
        for (Region region : regions) {
            if (region.getInventoryX() == inventoryX && region.getInventoryY() == inventoryY)
                return region;
        }
        return null;
    }

    public static Region getNearest(Location location) {
        return getNearest(location, null);
    }

    public static Region getNearest(Location location, Region cached) {
        Vector vector = location.toVector();

        if (cached != null && VectorUtils.distance2D(vector, cached.getLocation().toVector()) < (Region.OFFSET / 2))
            return cached;

        Region region = null;
        double distance = 0;

        for (Region rg : Region.getRegions()) {
            double d = VectorUtils.distance2D(vector, rg.getLocation().toVector());

            if (region == null || d < distance) {
                region = rg;
                distance = d;
            }
        }

        return region;
    }

    private ItemBuilder toItemBuilder() {
        ItemBuilder item = new ItemBuilder(BiomeUtils.material(getBiome()));
        item.setAmount((int) (getId() > 64 ? 64 : getId()));
        item.setDisplayName("§7§lRegion §a§l" + getId());
        item.addLore(" §7Biome: " + BiomeUtils.name(getBiome()));
        item.addLore(" §7XZ: §a§l" + NumberUtils.locale(getLocation().getBlockX()) + " §7/ §a§l" + NumberUtils.locale(getLocation().getBlockZ()));

        if (getId() == 1)
            item.glow();

        return item;
    }
}
