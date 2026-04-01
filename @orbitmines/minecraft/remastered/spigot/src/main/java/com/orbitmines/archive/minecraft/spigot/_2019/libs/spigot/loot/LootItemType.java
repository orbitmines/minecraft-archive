package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot;

/*
 * OrbitMines - @author Fadi Shawki - 15-6-2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.Donation;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public enum LootItemType {

    DONATION() {
        @Override
        public Server getServer(int count) {
            return byOrdinal(count).getServer();
        }

        @Override
        public String getDisplayName(int count) {
            return byOrdinal(count).getTitle();
        }

        @Override
        public ItemBuilder getIcon(int count) {
            switch(byOrdinal(count)) {

                case VIP_IRON:
                    return new ItemBuilder(Material.IRON_INGOT);
                case VIP_GOLD:
                case VIP_UPGRADE_IRON_GOLD:
                    return new ItemBuilder(Material.GOLD_INGOT);
                case VIP_DIAMOND:
                case VIP_UPGRADE_IRON_DIAMOND:
                case VIP_UPGRADE_GOLD_DIAMOND:
                    return new ItemBuilder(Material.DIAMOND);
                case VIP_EMERALD:
                case VIP_UPGRADE_IRON_EMERALD:
                case VIP_UPGRADE_GOLD_EMERALD:
                case VIP_UPGRADE_DIAMOND_EMERALD:
                    return new ItemBuilder(Material.EMERALD);
                case SOLARS_1500:
                case SOLARS_4000:
                case SOLARS_12500:
                    return new ItemBuilder(Material.SUNFLOWER);
                case SURVIVAL_CLAIMBLOCKS_10000:
                case SURVIVAL_CLAIMBLOCKS_25000:
                case SURVIVAL_CLAIMBLOCKS_75000:
                case SURVIVAL_CLAIMBLOCKS_50000:
                    return new ItemBuilder(Material.STONE_HOE).addFlag(ItemFlag.HIDE_ATTRIBUTES);
                case SURVIVAL_HOMES_25:
                case SURVIVAL_HOMES_100:
                    return new ItemBuilder(Material.SPRUCE_DOOR);
                case SURVIVAL_WARP_1:
                    return new ItemBuilder(Material.ENDER_PEARL);
            }
            return new ItemBuilder(Material.STONE);
        }

        private Donation.Type byOrdinal(int ordinal) {
            return EnumUtils.byOrdinal(Donation.Type.class, ordinal, Donation.Type.DONATION);
        }
    },
    BUYCRAFT_VOUCHER() {
        @Override
        public String getDisplayName(int count) {
            return "§3§l" + count + "€ OrbitMines Shop Voucher";
        }

        @Override
        public ItemBuilder getIcon(int count) {
            return new ItemBuilder(Material.PAPER);
        }
    },
    PRISMS() {
        @Override
        public String getDisplayName(int count) {
            return "§9§l" + NumberUtils.locale(count) + " " + (count == 1 ? "Prism" : "Prisms");
        }

        @Override
        public ItemBuilder getIcon(int count) {
            return new ItemBuilder(Material.PRISMARINE_SHARD);
        }
    },
    SOLARS() {
        @Override
        public String getDisplayName(int count) {
            return "§e§l" + NumberUtils.locale(count) + " " + (count == 1 ? "Solar" : "Solars");
        }

        @Override
        public ItemBuilder getIcon(int count) {
            return new ItemBuilder(Material.SUNFLOWER);
        }
    },
    SURVIVAL_CREDITS(Server.SURVIVAL) {
        @Override
        public String getDisplayName(int count) {
            return "§2§l" + NumberUtils.locale(count) + " " + (count == 1 ? "Credit" : "Credits");
        }

        @Override
        public ItemBuilder getIcon(int count) {
            return new ItemBuilder(Material.TURTLE_SCUTE);
        }
    },
    SURVIVAL_SPAWNER_MINER(Server.SURVIVAL) {
        @Override
        public String getDisplayName(int count) {
            return "§5§l" + count + " Spawner Miner";
        }

        @Override
        public ItemBuilder getIcon(int count) {
            return new ItemBuilder(Material.DIAMOND_PICKAXE).glow();
        }
    };

    private Server server;

    LootItemType() {
        this(null);
    }

    LootItemType(Server server) {
        this.server = server;
    }

    public Server getServer(int count) {
        return server;
    }

    public String getDisplayName(int count) {
        throw new IllegalStateException();
    }

    public ItemBuilder getIcon(int count) {
        throw new IllegalStateException();
    }

    public static LootItemType from(LootItem.Type type) {
        return LootItemType.valueOf(type.toString());
    }
}
