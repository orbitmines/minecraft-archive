package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import org.bukkit.Sound;

import java.lang.reflect.Array;
import java.util.List;

public abstract class PaginatableGUI<P extends SpigotPlayer, K, V> extends GUI<P> {

    protected final int pageSize;
    protected final int columns;
    protected final int rows;

    protected K key;
    protected int page;

    public PaginatableGUI(int size, String title, P viewer, K key, int columns, int rows) {
        super(size, title, viewer);

        this.key = key;

        this.pageSize = columns * rows;
        this.columns = columns;
        this.rows = rows;
        this.page = 0;
    }

    public abstract Item<P, ?> getItem(PageItem<V> pageItem);

    public abstract List<V> getCollection();

    public abstract Class<V> getTypeClass();

    public void open(int page) {
        this.page = page;

        super.open();
    }

    protected void paginate(int rowStart, int columnStart) {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                int r = row;
                int c = column;

                set(row + rowStart, column + columnStart, getItem(() -> getPage()[r][c]));
            }
        }
    }

    public void setPreviousPage(int row, int slot, MutableItemBuilder builder) {
        set(row, slot, new Item<P, MutableItemBuilder>(() -> {
            if (!hasPrevious())
                return null;

            return builder.toBuilder();
        }, event -> {
            if (!hasPrevious())
                return;

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            previous();
        }));
    }

    public void setNextPage(int row, int slot, MutableItemBuilder builder) {
        set(row, slot, new Item<P, MutableItemBuilder>(() -> {
            if (!hasNext())
                return null;

            return builder.toBuilder();
        }, event -> {
            if (!hasNext())
                return;

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            next();
        }));
    }

    protected boolean hasPrevious() {
        return page != 0;
    }

    protected boolean hasNext() {
        int amount = getCollection().size();

        if (amount <= pageSize)
            return false;

        int lastPage = amount % rows;
        lastPage = lastPage != 0 ? (amount - pageSize + (rows - lastPage)) / rows : (amount - pageSize) / rows;

        return lastPage > page;
    }

    protected void previous() {
        if (!hasPrevious())
            return;

        this.page--;
        update();
    }

    protected void next() {
        if (!hasNext())
            return;

        this.page++;
        update();
    }

    protected V[][] getPage() {
        List<V> collection = getCollection();

        V[][] page = (V[][]) Array.newInstance(getTypeClass(), this.rows, this.columns);

        /* Assign Initial Page, TODO: better calculation of pages which does not require initial page to be setup */
        loop:
        for (int row = 0, collectionIndex = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++, collectionIndex++) {
                if (collection.size() < collectionIndex + 1)
                    break loop;
                else
                    page[row][column] = collection.get(collectionIndex);
            }
        }

        if (this.page == 0)
            return page;

        /* Move through pages */
        for (int i = 0; i < this.page; i++) {
            for (int row = 0, collectionIndex = 0; row < this.rows; row++) {
                for (int column = 0; column < this.columns; column++, collectionIndex++) {
                    int check = -1;
                    if ((collectionIndex + 1) % this.columns == 0)
                        check = (collectionIndex + 1) / this.columns;

                    if (check != -1) {
                        int next = collectionIndex + check + (this.rows * i);
                        page[row][column] = collection.size() > next ? collection.get(next) : null;
                    } else {
                        page[row][column] = page[row][column + 1];
                    }
                }
            }
        }

        return page;
    }

    @FunctionalInterface
    public interface PageItem<I> {

        I getObject();

    }
}
