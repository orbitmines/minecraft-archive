package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.chat;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class KitPvPChatHandler extends ChatHandler<KitPvP, KitPvPPlayer> {

    public KitPvPChatHandler(KitPvP server, Type type, PlayerInstance sender) {
        super(server, type, sender);
    }

    public KitPvPChatHandler(KitPvP server, Type type, PlayerInstance sender, String message) {
        super(server, type, sender, message);
    }

    @Override
    public void appendSender(TextBuilder<KitPvPPlayer> builder) {
        KitPvPPlayerModel.LevelData levelData;

        if (sender instanceof KitPvPPlayer) {
            levelData = ((KitPvPPlayer) sender).getLevelData();

            if (((KitPvPPlayer) sender).isSpectator())
                builder.add(Color.YELLOW, p -> "Spec ");

        } else {
            KitPvPPlayerModel player = sender instanceof OfflinePlayer ? KitPvPPlayerModel.findOrInitializeBy(sender.getUUID()) : new KitPvPPlayerModel(null);
            levelData = player.getLevelData();
        }

        if (levelData != null) {
            Color color = levelData.getColor();
            int level = levelData.getLevel();

            builder.add(color, p -> level + " ")
                .hover(HoverEvent.Action.SHOW_TEXT, p -> {
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append(sender.getName(Name.RAW_COLORED)).append("\n").
                        append("§7Level: ").append(color.getCc()).append("§l").append(level).append("\n");

                    if (level == KitPvPPlayerModel.LevelData.maxLevel)
                        stringBuilder.append(color).append("Max Level");
                    else
                        stringBuilder.append("§7Next level: §e§l").
                            append(NumberUtils.locale(levelData.getCurrentLevelXp())).
                            append(" XP §7§l/ ").
                            append(NumberUtils.locale(levelData.getNextLevelXp()));

                    return stringBuilder.toString();
                });
        }

        super.appendSender(builder);
    }
}
