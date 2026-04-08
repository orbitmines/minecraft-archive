package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.loot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.Donation;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootItemType;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import org.bukkit.Sound;

public class CreativeLootHandler extends LootHandler<Creative, CreativePlayer> {

    public CreativeLootHandler(CreativePlayer player) {
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

                }
                break;
            }
        }
    }

    @Override
    protected void give(PeriodLootItem item) {
        switch (item.getType()) {

        }
    }

    @Override
    protected boolean canReceive(PeriodLootItem.Type type) {
        switch (type) {

        }
        return true;
    }
}
