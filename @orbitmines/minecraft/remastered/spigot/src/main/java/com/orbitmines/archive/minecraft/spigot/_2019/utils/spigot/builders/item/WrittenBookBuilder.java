package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WrittenBookBuilder<P extends SpigotPlayer> extends ItemBuilderInstance<WrittenBookBuilder, BookMeta> {

    @Getter private MutablePlayerString<P> title;
    @Getter private MutablePlayerString<P> author;
    @Getter private List<TextBuilder<P>> pages;
    @Getter private BookMeta.Generation generation;

    public WrittenBookBuilder() {
        super(Material.WRITTEN_BOOK);

        this.pages = new ArrayList<>();
    }

    public WrittenBookBuilder(int amount) {
        super(Material.WRITTEN_BOOK, amount);

        this.pages = new ArrayList<>();
    }

    public WrittenBookBuilder(int amount, String displayName) {
        super(Material.WRITTEN_BOOK, amount, displayName, null, null);

        this.pages = new ArrayList<>();
    }

    public WrittenBookBuilder(int amount, String displayName, MutablePlayerString<P> author, TextBuilder<P>... textBuilders) {
        super(Material.WRITTEN_BOOK, amount, displayName);

        this.author = author;
        this.pages = new ArrayList<>(Arrays.asList(textBuilders));
    }

    public WrittenBookBuilder(int amount, String displayName, String... lore) {
        super(Material.WRITTEN_BOOK, amount, displayName, lore);

        this.pages = new ArrayList<>();
    }

    public WrittenBookBuilder(int amount, String displayName, List<String> lore) {
        this(amount, displayName, lore, null, (TextBuilder<P>[]) null);
    }

    public WrittenBookBuilder(int amount, String displayName, List<String> lore, MutablePlayerString<P> author, TextBuilder<P>... textBuilders) {
        super(Material.WRITTEN_BOOK, amount, displayName, lore);

        this.author = author;
        this.pages = textBuilders != null ? new ArrayList<>(Arrays.asList(textBuilders)) : new ArrayList<>();
        this.generation = BookMeta.Generation.ORIGINAL;
    }

    public WrittenBookBuilder(WrittenBookBuilder<P> itemBuilder) {
        super(itemBuilder);

        this.author = itemBuilder.author;
        this.pages = new ArrayList<>(itemBuilder.pages);
    }

    public WrittenBookBuilder<P> setAuthor(MutablePlayerString<P> author) { this.author = author; return this; }
    public WrittenBookBuilder<P> setTitle(MutablePlayerString<P> title) { this.title = title; return this; }
    public WrittenBookBuilder<P> setPages(List<TextBuilder<P>> pages) { this.pages = pages; return this; }
    public WrittenBookBuilder<P> addPage(TextBuilder<P> page) { this.pages.add(page); return this; }
    public WrittenBookBuilder<P> setGeneration(BookMeta.Generation generation) { this.generation = generation; return this; }

    @Override
    public WrittenBookBuilder<P> clone() {
        return new WrittenBookBuilder<P>(this);
    }

    @Override
    protected WrittenBookBuilder<P> getInstance() {
        return this;
    }

    public ItemStack build(P player) {
        ItemStack itemStack = build();
        BookMeta meta = (BookMeta) itemStack.getItemMeta();

        if (this.title != null)
            meta.setTitle(this.title.toString(player));

        if (this.author != null)
            meta.setAuthor(this.author.toString(player));

        for (int i = 0; i < this.pages.size(); i++) {
            meta.spigot().addPage(this.pages.get(i).buildArray(player));
        }

        meta.setGeneration(this.generation);

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
