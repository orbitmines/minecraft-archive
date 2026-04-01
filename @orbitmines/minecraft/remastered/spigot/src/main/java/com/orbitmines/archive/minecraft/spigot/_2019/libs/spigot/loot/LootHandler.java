package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.Donation;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerVipGainedPublisher;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.jedis.JedisManager;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.Title;
import org.bukkit.Sound;
import redis.clients.jedis.Jedis;

public abstract class LootHandler<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

    protected final P player;

    public LootHandler(P player) {
        this.player = player;
    }

    protected abstract void give(LootItemType type, LootItem... item);

    protected abstract void give(PeriodLootItem item);

    protected abstract boolean canReceive(PeriodLootItem.Type type);

    public void collect(LootItemType type, LootItem... items) {
        if (type == LootItemType.BUYCRAFT_VOUCHER)
            return;

        int count = 0;
        for (LootItem item : items) {
            count += item.getCount();
        }

        if (type.getServer(count) != null && type.getServer(count) != player.server().getType())
            return;

        switch (type) {

            case DONATION: {
                if (type.getServer(count) != null) {
                    give(type, items);
                    break;
                }

                Donation.Type donation = EnumUtils.byOrdinal(Donation.Type.class, count);
                if (donation.getPreviousRank() != null && player.getVipRank() != donation.getPreviousRank()) {
                    player.sendMessage("Loot", Color.RED, "spigot", "player.insufficient_rank", donation.getPreviousRank().getDisplayName() + "§7");
                    return;
                } else if (donation.getUnlockedRank() != null && player.getVipRank().ordinal() >= donation.getUnlockedRank().ordinal()) {
                    player.sendMessage("Loot", Color.RED, "spigot", "player.rank_already_unlocked", player.getVipRank().getDisplayName() + "§7");
                    return;
                }

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);

                switch (donation) {

                    case VIP_IRON:
                    case VIP_GOLD:
                    case VIP_DIAMOND:
                    case VIP_EMERALD:
                    case VIP_UPGRADE_IRON_GOLD:
                    case VIP_UPGRADE_IRON_DIAMOND:
                    case VIP_UPGRADE_IRON_EMERALD:
                    case VIP_UPGRADE_GOLD_DIAMOND:
                    case VIP_UPGRADE_GOLD_EMERALD:
                    case VIP_UPGRADE_DIAMOND_EMERALD: {
                        VipRank rank = donation.getUnlockedRank();

                        Title<P> t = new Title<>(player -> "", player -> "§7" + player.translate("spigot", "player.loot.vip_gained", rank.getDisplayName() + "§7"), 20, 80, 20);
                        t.send(player);

                        player.setVipRank(rank);
                        player.update(PlayerModel.column.VIP_RANK);

                        LootItem item = new LootItem(player.getUUID(), LootItem.Type.SOLARS, Rarity.LEGENDARY, rank.getInitialSolars(donation.getPreviousRank()), donation.getTitle().replace("§l", "§l§o") + " §e§l§oSolars");
                        item.insert();
                        player.getLootItems(false).add(item);

                        /* Notify other services of Vip Gain */
                        new PlayerVipGainedPublisher().publish(player, rank);

                        /* Update Online Player */
                        try (Jedis jedis = JedisManager.get()) {
                            jedis.hset("player:" + player.getUUID().toString(), "vip_rank", player.getVipRank().toString());
                        }

                        break;
                    }
                    case SOLARS_1500:
                    case SOLARS_4000:
                    case SOLARS_12500: {
                        int solars = 0;
                        switch (donation) {
                            case SOLARS_1500:
                                solars = 1500;
                                break;
                            case SOLARS_4000:
                                solars = 4000;
                                break;
                            case SOLARS_12500:
                                solars = 12500;
                                break;
                        }

                        player.addSolars(solars);
                        player.update(PlayerModel.column.SOLARS);

                        player.sendMessage("Solars", Color.INFO, "spigot", "player.loot.received", "§e§l" + NumberUtils.locale(solars) + " Solars§7");
                        break;
                    }
                }

                break;
            }
            case PRISMS: {
                player.addPrisms(count);
                player.update(PlayerModel.column.PRISMS);

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);
                player.sendMessage("Prisms", Color.INFO, "spigot", "player.loot.received", "§9§l" + NumberUtils.locale(count) + " " + (count == 1 ? "Prism" : "Prisms") + "§7");
                break;
            }
            case SOLARS: {
                player.addSolars(count);
                player.update(PlayerModel.column.SOLARS);

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);
                player.sendMessage("Solars", Color.INFO, "spigot", "player.loot.received", "§e§l" + NumberUtils.locale(count) + " " + (count == 1 ? "Solar" : "Solars") + "§7");
                break;
            }
            default:
                give(type, items);
                break;
        }

        for (LootItem item : items) {
            item.delete();
            player.getLootItems(false).remove(item);
        }
    }

    public void collect(PeriodLootItem item) {
        item.collect();

        if (!item.isInserted()) {
            item.insert();
            player.getPeriodLootItems(false).add(item);
        } else {
            item.update(PeriodLootItem.column.LAST_USAGE_AT);
        }

        switch (item.getType()) {

            case DAILY: {
                int prisms = 250;

                player.addPrisms(prisms);
                player.update(PlayerModel.column.PRISMS);

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);
                player.sendMessage("Loot", Color.INFO, "spigot", "player.loot.received", "§9§l" + NumberUtils.locale(prisms) + " Prisms§7");
                break;
            }
            case MONTHLY: {
                int prisms = 2500;
                int solars = 50;

                player.addPrisms(prisms);
                player.addSolars(solars);
                player.update(PlayerModel.column.PRISMS, PlayerModel.column.SOLARS);

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);
                player.sendMessage("Loot", Color.INFO, "spigot", "player.loot.received", "§e§l" + NumberUtils.locale(solars) + " Solars§7 & §9§l" + NumberUtils.locale(prisms) + " Prisms§7");
                break;
            }
            case MONTHLY_VIP: {
                int count;

                switch (player.getVipRank()) {

                    case IRON:
                        count = 150;
                        break;
                    case GOLD:
                        count = 300;
                        break;
                    case DIAMOND:
                        count = 600;
                        break;
                    case EMERALD:
                        count = 1000;
                        break;
                    default:
                        return;
                }

                player.addSolars(count);
                player.update(PlayerModel.column.SOLARS);

                player.playSound(Sound.ENTITY_PLAYER_LEVELUP);
                player.sendMessage("Loot", Color.INFO, "spigot", "player.loot.received", "§e§l" + NumberUtils.locale(count) + " Solars§7");
                break;
            }
            default:
                give(item);
                break;
        }
    }

    public boolean canCollect(PeriodLootItem.Type type) {
        switch (type) {

            case DAILY:
            case MONTHLY:
            case MONTHLY_VIP:
                return true;
            default:
                return canReceive(type);
        }
    }
}
