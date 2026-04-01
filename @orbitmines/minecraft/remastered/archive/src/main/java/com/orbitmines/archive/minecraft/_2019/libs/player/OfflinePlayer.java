package com.orbitmines.archive.minecraft._2019.libs.player;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

public class OfflinePlayer implements PlayerInstance {

    private final UUID uuid;
    @Getter private final String rawName;
    @Getter private final String nickName;
    @Getter private final StaffRank staffRank;
    @Getter private final VipRank vipRank;

    private OfflinePlayer(UUID uuid) {
        this.uuid = uuid;

        Map<Selectable, Object> entry = DatabaseManager.getInstance().getDefault().getEntry(
            new MySQLQueryBuilder(PlayerModel.TABLE).
                where(PlayerModel.column.UUID.getColumn(), uuid.toString()),

            PlayerModel.column.NAME.getColumn(),
            PlayerModel.column.NICK_NAME.getColumn(),
            PlayerModel.column.STAFF_RANK.getColumn(),
            PlayerModel.column.VIP_RANK.getColumn()
        );

        if (entry != null) {
            this.rawName = (String) entry.get(PlayerModel.column.NAME.getColumn());
            this.nickName = (String) entry.get(PlayerModel.column.NICK_NAME.getColumn());
            this.staffRank = StaffRank.valueOf((String) entry.get(PlayerModel.column.STAFF_RANK.getColumn()));
            this.vipRank = VipRank.valueOf((String) entry.get(PlayerModel.column.VIP_RANK.getColumn()));
            return;
        }

        this.rawName = UUIDUtils.getName(uuid, true, false);
        this.nickName = null;
        this.staffRank = StaffRank.NONE;
        this.vipRank = VipRank.NONE;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public DiscordUser getDiscordUser() {
        return DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(getUUID()));
    }

    public static OfflinePlayer get(UUID uuid) {
        return new OfflinePlayer(uuid);
    }
}
