package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.loot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.Donation;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootItemType;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Sound;

public class SurvivalLootHandler extends LootHandler<Survival, SurvivalPlayer> {

    public SurvivalLootHandler(SurvivalPlayer player) {
        super(player);
    }

    @Override
    protected void give(LootItemType type, LootItem... items) {
        int count = 0;
        for (LootItem item : items) {
            count += item.getCount();
        }

        switch (type) {
            case DONATION: {
                Donation.Type donation = EnumUtils.byOrdinal(Donation.Type.class, count);

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);

                switch (donation) {

                    case SURVIVAL_CLAIMBLOCKS_10000:
                    case SURVIVAL_CLAIMBLOCKS_25000:
                    case SURVIVAL_CLAIMBLOCKS_75000:
                    case SURVIVAL_CLAIMBLOCKS_50000: {
                        int blocks = 0;
                        switch (donation) {
                            case SURVIVAL_CLAIMBLOCKS_10000:
                                blocks = 10000;
                                break;
                            case SURVIVAL_CLAIMBLOCKS_25000:
                                blocks = 25000;
                                break;
                            case SURVIVAL_CLAIMBLOCKS_75000:
                                blocks = 75000;
                                break;
                            case SURVIVAL_CLAIMBLOCKS_50000:
                                blocks = 50000;
                                break;
                        }

                        player.addClaimBlocks(blocks);
                        player.update(SurvivalPlayerModel.column.CLAIM_BLOCKS);

                        player.sendMessage("Claimblocks", Color.INFO, "spigot", "player.loot.received", "§9§l" + NumberUtils.locale(blocks) + " Claimblocks§7");
                        break;
                    }
                    case SURVIVAL_HOMES_25:
                    case SURVIVAL_HOMES_100: {
                        int homes = 0;
                        switch (donation) {
                            case SURVIVAL_HOMES_25:
                                homes = 25;
                                break;
                            case SURVIVAL_HOMES_100:
                                homes = 100;
                                break;
                        }

                        player.addExtraHomes(homes);
                        player.update(SurvivalPlayerModel.column.EXTRA_HOMES);

                        player.sendMessage("Homes", Color.INFO, "spigot", "player.loot.received", "§6§l" + NumberUtils.locale(homes) + " Homes§7");
                        break;
                    }
                    case SURVIVAL_WARP_1: {
                        player.setWarpSlotShop(true);
                        player.update(SurvivalPlayerModel.column.WARP_SLOT_SHOP);

                        player.sendMessage("Warp", Color.INFO, "spigot", "player.loot.received", "§3§l1 Warp Slot§7");
                        break;
                    }
                }
                break;
            }
            case SURVIVAL_SPAWNER_MINER: {
                SpigotServer.getInstance().runSync(() -> {
                    player.getInventory().addItem(Survival.SPAWNER_MINER.clone().build());
                });
                break;
            }
        }
    }

    @Override
    protected void give(PeriodLootItem item) {
        switch (item.getType()) {

            case SURVIVAL_BACK_CHARGES: {
                int count;

                switch (player.getVipRank()) {

                    case IRON:
                        count = 50;
                        break;
                    case GOLD:
                        count = 125;
                        break;
                    case DIAMOND:
                        count = 200;
                        break;
                    case EMERALD:
                        count = 300;
                        break;
                    default:
                        return;
                }

                player.addBackCharges(count);
                player.update(SurvivalPlayerModel.column.BACK_CHARGES);

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);
                player.sendMessage("Loot", Color.INFO, "spigot", "player.loot.received", "§6§l" + NumberUtils.locale(count) + " Back Charges§7");

                break;
            }
            case SURVIVAL_SPAWNER_ITEM: {
                switch (player.getVipRank()) {

                    case EMERALD:
                        player.getInventory().addItem(Survival.SPAWNER_MINER.build());
                        break;
                    default:
                        return;
                }

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);
                player.sendMessage("Loot", Color.INFO, "spigot", "player.loot.received", Survival.SPAWNER_MINER.getDisplayName() + "§7");

                break;
            }
        }
    }

    @Override
    protected boolean canReceive(PeriodLootItem.Type type) {
        switch (type) {

            case SURVIVAL_SPAWNER_ITEM:
                return !player.isInventoryFull();
        }
        return true;
    }
}
