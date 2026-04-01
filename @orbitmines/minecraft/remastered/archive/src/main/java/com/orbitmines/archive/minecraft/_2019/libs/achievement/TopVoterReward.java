package com.orbitmines.archive.minecraft._2019.libs.achievement;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import lombok.Getter;

@Deprecated
public enum TopVoterReward {

    FIRST_PLACE(LootItem.Type.BUYCRAFT_VOUCHER, 5),
    SECOND_PLACE(LootItem.Type.BUYCRAFT_VOUCHER, 3),
    THIRD_PLACE(LootItem.Type.BUYCRAFT_VOUCHER, 2);

    public static final int COMMUNITY_GOAL = 2000;
    public static final int COMMUNITY_GOAL_SOLARS_PER_VOTE = 2;

    @Getter
    private final LootItem.Type type;
    @Getter private final int count;

    TopVoterReward(LootItem.Type type, int count) {
        this.type = type;
        this.count = count;
    }
}