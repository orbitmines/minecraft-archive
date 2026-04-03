package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.chat;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SurvivalChatHandler extends ChatHandler<Survival, SurvivalPlayer> {

    public SurvivalChatHandler(Survival server, Type type, PlayerInstance sender) {
        super(server, type, sender);
    }

    public SurvivalChatHandler(Survival server, Type type, PlayerInstance sender, String message) {
        super(server, type, sender, message);
    }

    @Override
    protected void appendWord(TextBuilder<SurvivalPlayer> builder, SurvivalPlayer receiver, boolean mentioned, Color messageColor, String word) {
        if (sender instanceof SurvivalPlayer && word.equalsIgnoreCase("[item]")) {
            appendItem(builder, receiver, mentioned, messageColor, word);
            return;
        }

        super.appendWord(builder, receiver, mentioned, messageColor, word);
    }

    private void appendItem(TextBuilder<SurvivalPlayer> builder, SurvivalPlayer receiver, boolean mentioned, Color messageColor, String word) {
        try {
            SurvivalPlayer sender = (SurvivalPlayer) this.sender;
            ItemStack item = sender.getItemInMainHand();

            if (item == null || item.getType() == Material.AIR) {
                builder.add(Color.LIME, p -> "[item]")
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> {
                        StringBuilder stringBuilder = new StringBuilder("§a[item]");

                        for (String s : p.getLanguage().getStringArray("survival", "player.chat.item_explanation")) {
                            stringBuilder.append("\n").append("§7").append(s);
                        }

                        return stringBuilder.toString();
                    });
                return;
            }

            builder.add(Color.LIME, p -> "[")
                .hoverItem(item);

            if (item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && !item.getItemMeta().getDisplayName().isEmpty()) {
                String displayName = item.getItemMeta().getDisplayName();
                String[] display = displayName.split(" ");

                int charIndex = 0;
                for (int i = 0; i < display.length; i++) {
                    String space = i != 0 ? " " : "";
                    String part = display[i];

                    charIndex += space.length() + part.length();

                    String lastColors = ChatColor.getLastColors(displayName.substring(0, charIndex));

                    Color color = Color.WHITE;
                    for (Color c : Color.values()) {
                        if (!lastColors.contains(c.getCc()))
                            continue;

                        color = c;
                    }

                    if (color == Color.WHITE && item.getEnchantments().size() > 0) {
                        color = Color.AQUA;
                    }

                    int fI = i;
                    TextComponent<SurvivalPlayer> component = new TextComponent<SurvivalPlayer>(color, p -> space + (fI == 0 ? item.getAmount() + "x " : "") +  part)
                        .hoverItem(item);

                    component.bold(lastColors.contains("§l"));
                    component.italic(lastColors.contains("§o") || lastColors.isEmpty() /* Item Renamed */);
                    component.obfuscated(lastColors.contains("§k"));
                    component.strikethrough(lastColors.contains("§m"));
                    component.underlined(lastColors.contains("§n"));

                    builder.add(component);
                }
            } else {
                builder.add(Color.WHITE, p -> item.getAmount() + "x " + ItemUtils.getName(item.getType()))
                    .hoverItem(item);
            }

            builder.add(Color.LIME, p -> "]")
                .hoverItem(item);
        } catch(Exception ex) {
            ex.printStackTrace();
            super.appendWord(builder, receiver, mentioned, messageColor, word);
        }
    }
}
