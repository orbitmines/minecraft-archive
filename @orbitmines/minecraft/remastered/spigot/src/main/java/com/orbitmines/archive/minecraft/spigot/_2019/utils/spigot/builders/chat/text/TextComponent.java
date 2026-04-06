package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ColorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.inventory.ItemStack;

public class TextComponent<P extends SpigotPlayer> {

    private final TextBuilder<P> builder;

    @Getter private final ChatColor chatColor;
    @Getter private final MutablePlayerString<P> mutableString;

    @Getter private ClickEvent.Action clickAction;
    @Getter private MutablePlayerString<P> clickEvent;

    @Getter private HoverEvent.Action hoverAction;
    @Getter private MutablePlayerString<P> hoverEvent;
    @Getter private ItemStack hoverItemStack;

    @Getter private boolean bold;
    @Getter private boolean italic;
    @Getter private boolean obfuscated;
    @Getter private boolean strikethrough;
    @Getter private boolean underlined;

    public TextComponent(Color color, MutablePlayerString<P> mutableString) {
        this(null, color, mutableString);
    }

    public TextComponent(ChatColor chatColor, MutablePlayerString<P> mutableString) {
        this(null, chatColor, mutableString);
    }

    public TextComponent(TextBuilder<P> builder, Color color, MutablePlayerString<P> mutableString) {
        this(builder, ColorUtils.toChatColor(color).asBungee(), mutableString);
    }

    public TextComponent(TextBuilder<P> builder, ChatColor chatColor, MutablePlayerString<P> mutableString) {
        this.builder = builder;
        this.chatColor = chatColor;
        this.mutableString = mutableString;
        this.bold = false;
        this.italic = false;
        this.obfuscated = false;
        this.strikethrough = false;
        this.underlined = false;
    }

    public TextComponent(TextBuilder<P> builder, TextComponent<P> textComponent) {
        this.builder = builder;
        this.chatColor = textComponent.chatColor;
        this.mutableString = textComponent.mutableString;
        this.clickAction = textComponent.clickAction;
        this.clickEvent = textComponent.clickEvent;
        this.hoverAction = textComponent.hoverAction;
        this.hoverEvent = textComponent.hoverEvent;
        this.hoverItemStack = textComponent.hoverItemStack;
        this.bold = textComponent.bold;
        this.italic = textComponent.italic;
        this.obfuscated = textComponent.obfuscated;
        this.strikethrough = textComponent.strikethrough;
        this.underlined = textComponent.underlined;
    }

    public TextComponent<P> space() {
        if (builder == null)
            throw new UnsupportedOperationException();

        return builder.space();
    }
    public TextComponent<P> add(Color color, MutablePlayerString<P> mutableString) {
        if (builder == null)
            throw new UnsupportedOperationException();

        return builder.add(color, mutableString);
    }
    public TextComponent<P> add(ChatColor chatColor, MutablePlayerString<P> mutableString) {
        if (builder == null)
            throw new UnsupportedOperationException();

        return builder.add(chatColor, mutableString);
    }

    public TextBuilder<P> builder() {
        return builder;
    }

    public TextComponent<P> click(ClickEvent.Action clickAction, MutablePlayerString<P> clickEvent) {
        this.clickAction = clickAction;
        this.clickEvent = clickEvent;

        return this;
    }

    public TextComponent<P> hover(HoverEvent.Action hoverAction, MutablePlayerString<P> hoverEvent) {
        this.hoverAction = hoverAction;
        this.hoverEvent = hoverEvent;

        return this;
    }

    public TextComponent<P> hoverItem(ItemStack itemStack) {
        this.hoverAction = HoverEvent.Action.SHOW_ITEM;
        this.hoverItemStack = itemStack;

        return this;
    }

    public TextComponent<P> bold() {
        this.bold = true;
        return this;
    }

    public TextComponent<P> italic() {
        this.italic = true;
        return this;
    }

    public TextComponent<P> obfuscated() {
        this.obfuscated = true;
        return this;
    }

    public TextComponent<P> strikethrough() {
        this.strikethrough = true;
        return this;
    }

    public TextComponent<P> underlined() {
        this.underlined = true;
        return this;
    }

    public TextComponent<P> bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public TextComponent<P> italic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public TextComponent<P> obfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    public TextComponent<P> strikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public TextComponent<P> underlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public net.md_5.bungee.api.chat.TextComponent toBungee(P player) {
        net.md_5.bungee.api.chat.TextComponent component = new net.md_5.bungee.api.chat.TextComponent(this.mutableString.toString(player));
        component.setColor(this.chatColor);

        if (clickAction != null)
            component.setClickEvent(new ClickEvent(this.clickAction, this.clickEvent.toString(player)));
        if (hoverAction != null) {
            if (hoverItemStack == null) {
                component.setHoverEvent(new HoverEvent(this.hoverAction, new Text(this.hoverEvent.toString(player))));
            }
            /* Item hover is applied at the NMS level in applyNmsHover() */
        }

        component.setBold(this.bold);
        component.setItalic(this.italic);
        component.setObfuscated(this.obfuscated);
        component.setStrikethrough(this.strikethrough);
        component.setUnderlined(this.underlined);

        return component;
    }

    /**
     * Applies item hover events at the NMS level, bypassing BungeeCord's old-format ItemTag.
     * Must be called on the NMS component after toBungee() conversion.
     */
    public void applyNmsHover(net.minecraft.network.chat.MutableComponent nmsComponent) {
        if (hoverItemStack == null)
            return;

        net.minecraft.world.item.ItemStack nmsStack = org.bukkit.craftbukkit.inventory.CraftItemStack.asNMSCopy(hoverItemStack);
        net.minecraft.network.chat.HoverEvent nmsHover = new net.minecraft.network.chat.HoverEvent.ShowItem(
                net.minecraft.world.item.ItemStackTemplate.fromNonEmptyStack(nmsStack)
        );
        nmsComponent.setStyle(nmsComponent.getStyle().withHoverEvent(nmsHover));
    }

    public boolean hasItemHover() {
        return hoverItemStack != null;
    }

    public TextComponent<P> copyTo(TextBuilder<P> builder) {
        return new TextComponent<>(builder, this);
    }
}
