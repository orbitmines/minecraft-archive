package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.datapoints;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.ChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockDataUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.Direction;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.FloatingItem;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointSign;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class SurvivalDataPointSpawnShop extends DataPointSign {

    public SurvivalDataPointSpawnShop() {
        super("SHOP", Type.GOLD_PLATE, Material.YELLOW_WOOL);
    }

    @Override
    public boolean buildAt(DataPointLoader loader, Location location, String[] data) {
        BlockFace face = BlockFace.valueOf(data[0]);
        int ordinal = Integer.parseInt(data[1]) - 1;

        new BukkitRunnable() {
            @Override
            public void run() {
                create(location, face, ShopType.values()[ordinal]);
            }
        }.runTaskLater(Survival.getInstance(), 1);

        return true;
    }

    @Override
    public boolean setup() {
        return true;
    }

    public void create(Location location, BlockFace face, ShopType shopType) {
        BlockDataUtils.setChest(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), Chest.Type.SINGLE, face, false);

        Location signLoc = Direction.valueOf(face.toString()).getAsNewLocation(location);
        BlockDataUtils.setWallSign(signLoc.getWorld(), signLoc.getBlockX(), signLoc.getBlockY(), signLoc.getBlockZ(), Material.OAK_WALL_SIGN, face, false);

        Sign sign = (Sign) signLoc.getBlock().getState();
        sign.setLine(0, "[shop]");
        sign.update();

        FloatingItem item = new FloatingItem(null, location.add(0.5, shopType.rotation == null ? -0.75 : 0, 0.5));

        ItemBuilder itemBuilder;
        int amount;
        int price;

        if (shopType.rotation == null) {
            /* Normal */
            itemBuilder = new ItemBuilder(shopType.getMaterial());
            amount = shopType.amount;
            price = shopType.price;
        } else {
            /* Rotating */

            RotationItem rotation = RandomUtils.randomFrom(shopType.rotation);
            itemBuilder = rotation.itemBuilder.clone();
            amount = rotation.amount;
            price = rotation.price;

            switch (shopType) {
                case ROTATING: {
                    item.addLine(() -> "§a§lDAILY ITEM", false);
                    break;
                }
                case ROTATING_ENCHANTMENTS: {
                    item.addLine(() -> "§d§lDAILY ENCHANTED BOOK", false);
                    break;
                }
            }

            item.addLine(() -> "§7§o" + rotation.name, false);
        }

        SpawnChestShop chestShop = new SpawnChestShop(signLoc, itemBuilder, amount, price);
        ((Survival) Survival.getInstance()).getChestShops().add(chestShop);

        item.setItemBuilder(itemBuilder::build);

        item.create();
    }

    public class SpawnChestShop extends ChestShop {

        private ItemBuilder itemBuilder;

        public SpawnChestShop(Location location, ItemBuilder itemBuilder, int amount, int price) {
            super(null, null, location, itemBuilder.getMaterial(), SurvivalChestShop.PurchaseType.BUY, amount, price);

            this.itemBuilder = itemBuilder;

            refill();
        }

        @Override
        public void buy(SurvivalPlayer player) {
            super.buy(player);

            refill();
        }

        private void refill() {
            org.bukkit.block.Chest chest = BlockUtils.getChestAtSign(getLocation());

            for (int i = 0; i < chest.getInventory().getSize(); i++) {
                chest.getInventory().setItem(i, itemBuilder.clone().setAmount(itemBuilder.getMaterial().getMaxStackSize()).build());
            }
        }

        @Override
        public void update(SurvivalChestShop.column... columns) {

        }

        @Override
        public void insert() {

        }

        @Override
        public void delete() {

        }
    }

    public enum ShopType {

        WHEAT_SEEDS(Material.WHEAT_SEEDS, 32, 150),
        POTATO(Material.POTATO, 16, 150),
        MELON_SEEDS(Material.MELON_SEEDS, 4, 150),
        BEETROOT_SEEDS(Material.BEETROOT_SEEDS, 16, 150),
        SUGAR_CANE(Material.SUGAR_CANE, 8, 200),
        CACTUS(Material.CACTUS, 8, 200),

        DIRT(Material.DIRT, 64, 100),
        COBBLESTONE(Material.COBBLESTONE, 64, 100),

        OAK_LOG(Material.OAK_LOG, 16, 100),
        BIRCH_LOG(Material.BIRCH_LOG, 16, 100),
        DARK_OAK_LOG(Material.DARK_OAK_LOG, 16, 100),
        JUNGLE_LOG(Material.JUNGLE_LOG, 16, 100),

        LEATHER(Material.LEATHER, 8, 200),
        COAL(Material.COAL, 16, 250),
        IRON_INGOT(Material.IRON_INGOT, 8, 200),
        GOLD_INGOT(Material.GOLD_INGOT, 8, 500),
        DIAMOND(Material.DIAMOND, 1, 500),
        GLOWSTONE(Material.GLOWSTONE, 16, 400),

        CARROT(Material.CARROT, 16, 150),
        PUMPKIN_SEEDS(Material.PUMPKIN_SEEDS, 4, 150),

        ROTATING(
                new RotationItem("Villager Spawn Egg", new ItemBuilder(Material.VILLAGER_SPAWN_EGG), 1, 5000),
                new RotationItem("Cow Spawn Egg", new ItemBuilder(Material.COW_SPAWN_EGG), 1, 1000),
                new RotationItem("Pig Spawn Egg", new ItemBuilder(Material.PIG_SPAWN_EGG), 1, 1000),
                new RotationItem("Chicken Spawn Egg", new ItemBuilder(Material.CHICKEN_SPAWN_EGG), 1, 750),
                new RotationItem("Sheep Spawn Egg", new ItemBuilder(Material.SHEEP_SPAWN_EGG), 1, 750),
                new RotationItem("8 Slimeballs", new ItemBuilder(Material.SLIME_BALL), 8, 500),
                new RotationItem("Saddle", new ItemBuilder(Material.SADDLE), 1, 1500),
                new RotationItem("Diamond Horse Armor", new ItemBuilder(Material.DIAMOND_HORSE_ARMOR), 1, 2000),
                new RotationItem("Name Tag", new ItemBuilder(Material.NAME_TAG), 1, 750),
                new RotationItem("Nautilus Shell", new ItemBuilder(Material.NAUTILUS_SHELL), 1, 750)
        ),
        ROTATING_ENCHANTMENTS(
                new RotationItem("Mending", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.MENDING, 1), 1, 10000),
                new RotationItem("Efficiency IV", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.EFFICIENCY, 4), 1, 5000),
                new RotationItem("Efficiency V", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.EFFICIENCY, 5), 1, 10000),
                new RotationItem("Sharpness IV", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.SHARPNESS, 4), 1, 4000),
                new RotationItem("Protection IV", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.PROTECTION, 4), 1, 7500),
                new RotationItem("Fortune II", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.FORTUNE, 2), 1, 4000),
                new RotationItem("Fortune III", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.FORTUNE, 3), 1, 7500),
                new RotationItem("Unbreaking III", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.UNBREAKING, 3), 1, 5000),
                new RotationItem("Aqua Infinity", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.AQUA_AFFINITY, 1), 1, 2500),
                new RotationItem("Silk Touch", new ItemBuilder(Material.ENCHANTED_BOOK).addEnchantment(Enchantment.SILK_TOUCH, 1), 1, 7500)
        ),

        SPRUCE_LOG(Material.SPRUCE_LOG, 16, 100),
        ACACIA_LOG(Material.ACACIA_LOG, 16, 100),
        /* 24 */

        ;

        private final Material material;
        private final int amount;
        private final int price;

        private final RotationItem[] rotation;

        ShopType(RotationItem... rotation) {
            this.material = null;
            this.amount = 0;
            this.price = 0;
            this.rotation = rotation;
        }

        ShopType(Material material, int amount, int price) {
            this.material = material;
            this.amount = amount;
            this.price = price;
            this.rotation = null;
        }

        public Material getMaterial() {
            return material;
        }

        public int getAmount() {
            return amount;
        }

        public int getPrice() {
            return price;
        }
    }

    public static class RotationItem {

        private final String name;
        private final ItemBuilder itemBuilder;
        private final int amount;
        private final int price;

        public RotationItem(String name, ItemBuilder itemBuilder, int amount, int price) {
            this.name = name;
            this.itemBuilder = itemBuilder;
            this.amount = amount;
            this.price = price;
        }
    }
}
