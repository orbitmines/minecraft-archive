package com.orbitmines.minecraft.spigot.servers.fog;

import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootItemType;

public class FoGLootHandler extends LootHandler<FoG, FoGPlayer> {

    public FoGLootHandler(FoGPlayer player) {
        super(player);
    }

    @Override
    protected void give(LootItemType type, LootItem... items) {
        /* POC: nothing — vote/donation rewards are not wired yet. */
    }

    @Override
    protected void give(PeriodLootItem item) {
        /* POC: nothing. */
    }

    @Override
    protected boolean canReceive(PeriodLootItem.Type type) {
        return true;
    }
}
