package com.orbitmines.archive.minecraft._2019.libs.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordEmote;
import lombok.Getter;

public enum CustomEmote implements DiscordEmote {
    // IMPORTANT: Emotes must be under 256kb in size, or they will not upload

    iron_ingot(Image.IRON_INGOT),
    gold_ingot(Image.GOLD_INGOT),
    diamond_item(Image.DIAMOND),

    orbitmines(Image.ORBITMINES_ICON_COMPRESSED),
    kitpvp(Image.KITPVP_ICON_COMPRESSED),
    prison(Image.PRISON_ICON_COMPRESSED),
    minigames(Image.MINIGAMES_ICON_COMPRESSED),
    skyblock(Image.SKYBLOCK_ICON_COMPRESSED),
    survival(Image.SURVIVAL_ICON_COMPRESSED),
    fog(1, Image.FOG_ICON_COMPRESSED),
    creative(Image.CREATIVE_ICON_COMPRESSED),

    iron(Image.VIP_IRON_COMPRESSED),
    gold(Image.VIP_GOLD_COMPRESSED),
    diamond(Image.VIP_DIAMOND_COMPRESSED),
    emerald(Image.VIP_EMERALD_COMPRESSED),

    barrier(Image.BARRIER),
    prismarine_shard(Image.PRISMARINE_SHARD),

    unknown_player(Image.STEVE);

    private final int index;
    @Getter private final Image image;

    CustomEmote(Image image) {
        this(0, image);
    }

    CustomEmote(int index, Image image) {
        this.index = index;
        this.image = image;
    }

    public String getName() {
        return toString();
    }

    public int index() {
        return this.index;
    }

    public static CustomEmote from(Server server) {
        try {
            return CustomEmote.valueOf(server.toString().toLowerCase());
        } catch(NullPointerException | IllegalArgumentException ex) {
            return orbitmines;
        }
    }

    public static CustomEmote from(VipRank vipRank) {
        try {
            return CustomEmote.valueOf(vipRank.toString().toLowerCase());
        } catch(NullPointerException | IllegalArgumentException ex) {
            return CustomEmote.orbitmines;
        }
    }

}
