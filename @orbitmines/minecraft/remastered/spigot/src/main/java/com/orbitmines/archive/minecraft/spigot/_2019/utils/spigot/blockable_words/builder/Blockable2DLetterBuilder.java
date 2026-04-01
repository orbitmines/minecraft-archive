package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.blockable_words.builder;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.blockable_words.BlockableLetter;

import java.util.ArrayList;
import java.util.List;

public class Blockable2DLetterBuilder {

    private final char[] chars;
    private final int length;
    private final String[] lines;

    public Blockable2DLetterBuilder(char c, int length, String... lines) {
        this(new char[] { c }, length, lines);
    }

    public Blockable2DLetterBuilder(char[] chars, int length, String... lines) {
        this.chars = chars;
        this.length = length;
        this.lines = lines;
    }

    public BlockableLetter build() {
        List<int[]> offsets = new ArrayList<>();

        /* Render bottom to top */
        for (int i = lines.length - 1, y = 0; i >= 0; i--, y++) {
            String line = lines[i];
            char[] chars = line.toCharArray();

            for (int x = 0; x < chars.length; x++) {
                char c = chars[x];

                if (c != 'X')
                    continue;

                offsets.add(new int[] { x, y, 0 });
            }
        }

        return new BlockableLetter(chars, length, offsets.toArray(new int[0][]));
    }
}
