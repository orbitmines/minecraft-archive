package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.blockable_words;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class BlockableLetter {

    @Getter char[] chars;
    @Getter private final int length;
    @Getter private final List<int[]> offsets;

    public BlockableLetter(char c, int length, int[]... offsets) {
        this(new char[] { c }, length, offsets);
    }

    public BlockableLetter(char[] chars, int length, int[]... offsets) {
        this.chars = chars;
        this.length = length;
        this.offsets = Arrays.asList(offsets);
    }
}
