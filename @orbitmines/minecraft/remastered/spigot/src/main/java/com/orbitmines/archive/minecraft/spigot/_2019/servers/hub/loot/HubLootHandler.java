package com.orbitmines.archive.minecraft.spigot._2019.servers.hub.loot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootItemType;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.Hub;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.player.HubPlayer;

public class HubLootHandler extends LootHandler<Hub, HubPlayer> {

    public HubLootHandler(HubPlayer player) {
        super(player);
    }

    @Override
    protected void give(LootItemType type, LootItem... item) {
        throw new IllegalStateException("There are no Hub specific Period Items");
    }

    @Override
    protected void give(PeriodLootItem item) {
        throw new IllegalStateException("There are no Hub specific Period Items");
    }

    @Override
    protected boolean canReceive(PeriodLootItem.Type type) {
        throw new IllegalStateException("There are no Hub specific Period Items");
    }
}
