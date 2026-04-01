package com.orbitmines.archive.minecraft._2019.libs.achievement;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import lombok.Getter;

import java.time.Month;

@Deprecated
public class PersonalAchievement {

    public static final PersonalAchievement[] MONTHLY_ACHIEVEMENT_VOTES = {
            new PersonalAchievement(1, 50, 2500, 0),
            new PersonalAchievement(2, 100, 0, 250),
            new PersonalAchievement(3, DateUtils.getMonth() == Month.FEBRUARY ? 140 : 150, 2500, 250)
    };

    @Getter private final int tier;
    @Getter private final int votes;
    @Getter private final int prisms;
    @Getter private final int solars;

    public PersonalAchievement(int tier, int votes, int prisms, int solars) {
        this.tier = tier;
        this.votes = votes;
        this.prisms = prisms;
        this.solars = solars;
    }
}
