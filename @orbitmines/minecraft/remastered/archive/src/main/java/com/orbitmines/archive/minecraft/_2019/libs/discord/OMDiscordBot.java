package com.orbitmines.archive.minecraft._2019.libs.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.SkinLibrary;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordBot;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.entities.Icon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class OMDiscordBot extends DiscordBot {

    public static OMDiscordBot INSTANCE;

    private static final int MAX_DISCORD_EMOTES = 48; /* Actual limit is 50, but give some room for error as async processes may try to create multiple at the same time */
    private static final int MAX_DISCORD_ANIMATED_EMOTES = 48; /* Actual limit is 50, but give some room for error as async processes may try to create multiple at the same time */
    private final SkinLibrary skinLibrary;

    public OMDiscordBot(String token, String serverId, SkinLibrary skinLibrary) {
        super(token, serverId);
        INSTANCE = this;

        this.skinLibrary = skinLibrary;
    }

    protected abstract void withPlayerEmoteAsync(Runnable runnable);

    public RichCustomEmoji getDefaultPlayerEmote() {
        return getEmote(CustomEmote.unknown_player);
    }

    public String getPlayerEmoteName(String playerName) {
        return "player_" + playerName;
    }

    public String getPlayerDisplay(PlayerInstance player, RichCustomEmoji playerEmote, String playerName) {
        return playerEmote.getAsMention() + getToDiscordPrefix(player) + " **" + playerName + "**";
    }

    private String getToDiscordPrefix(PlayerInstance player) {
        StringBuilder stringBuilder = new StringBuilder();

        StaffRank staffRank = player.getStaffRank();

        if (staffRank.isNone()) {
            VipRank vipRank = player.getVipRank();

            if (!vipRank.isNone())
                stringBuilder.
                        append(" ").
                        append(getEmote(CustomEmote.from(vipRank)).getAsMention()).
                        append("**").append(vipRank.getName()).append("**");
        } else {
            stringBuilder.
                    append(" ").
                    append("**").append(staffRank.getName()).append("**");
        }

        return stringBuilder.toString();
    }

    /* Returns the default player emote, or if possible the player's own emote in the given action */
    public void withPlayerEmote(UUID uuid, String playerName, boolean override, Consumer<RichCustomEmoji> action) {
        withPlayerEmoteAsync(
            () -> {
                String emoteName = getPlayerEmoteName(playerName);

                /* RichCustomEmoji already present on the Discord server */
                RichCustomEmoji emote = getEmote(emoteName);
                if (emote != null && !override) {
                    if (action != null)
                        action.accept(emote);

                    return;
                }

                /* Create RichCustomEmoji Icon */
                Icon icon;
                try {
                    boolean normal = getEmoteCount(true) >= MAX_DISCORD_ANIMATED_EMOTES;

                    File skin = normal ? skinLibrary.getSkin(SkinLibrary.Type.HEAD_FLAT, uuid) : skinLibrary.getSkinAsGif(SkinLibrary.Type.HEAD_FLAT, uuid);

                    icon = Icon.from(skin);
                } catch (IOException | NullPointerException | IllegalArgumentException e) {
                    e.printStackTrace();

                    /* Return default player emote if icon creation failed */
                    if (action != null)
                        action.accept(getDefaultPlayerEmote());

                    return;
                }

                /* Override current emote of player */
                if (emote != null) {
                    deleteAndCreateEmote(emote, emoteName, icon, action);
                    return;
                }

                /* Too many emotes on the Discord server, destroy oldest and create new */
                if (getGuild().getEmojis().size() >= (MAX_DISCORD_EMOTES + MAX_DISCORD_ANIMATED_EMOTES)) {
                    RichCustomEmoji oldest = getOldestPlayerEmote();

                    /* Shouldn't happen, but just in case */
                    if (oldest == null) {
                        action.accept(getDefaultPlayerEmote());
                        return;
                    }

                    deleteAndCreateEmote(oldest, emoteName, icon, action);
                    return;
                }

                /* We can simply create the emote if all checks have passed */
                createEmote(emoteName, icon, action);
            }
        );
    }

    private void deleteAndCreateEmote(RichCustomEmoji emoteToDelete, String emoteName, Icon icon, Consumer<RichCustomEmoji> action) {
        emoteToDelete.delete().queue(
                (e) -> createEmote(emoteName, icon, action),

                /* If deletion failed return default player emote */
                (t) -> action.accept(getDefaultPlayerEmote())
        );
    }

    private void createEmote(String emoteName, Icon icon, Consumer<RichCustomEmoji> onCreate) {
        getController().createEmoji(emoteName, icon).queue(onCreate, (t) -> onCreate.accept(getDefaultPlayerEmote()));
    }

    private int getEmoteCount(boolean animated) {
        int count = 0;

        for (RichCustomEmoji emote : getGuild().getEmojis()) {
            if (emote.isAnimated() == animated)
                count++;
        }

        return count;
    }

    public RichCustomEmoji getOldestPlayerEmote() {
        RichCustomEmoji oldest = null;

        for (RichCustomEmoji emote : getGuild().getEmojis()) {
            if (!emote.getName().startsWith("player_"))
                continue;

            if (oldest == null || oldest.getTimeCreated().isAfter(emote.getTimeCreated()))
                oldest = emote;
        }

        return oldest;
    }

    public void deleteAllExistingPlayerEmotes() {
        List<RichCustomEmoji> emotes = getGuild().getEmojis();
        for (RichCustomEmoji emote : new ArrayList<>(emotes)) {
            if (emote.getName().startsWith("player_"))
                emote.delete().queue();
        }
    }
}
