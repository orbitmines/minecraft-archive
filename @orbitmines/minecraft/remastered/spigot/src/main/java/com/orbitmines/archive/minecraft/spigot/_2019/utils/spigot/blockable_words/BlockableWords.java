package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.blockable_words;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.blockable_words.builder.Blockable2DLetterBuilder;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.HashMap;
import java.util.Map;

public abstract class BlockableWords {
    // TODO: Personalized Words, through #sendBlockChange

    private static Map<Character, BlockableLetter> defaultFont = new HashMap<>();

    /* Default letter font */
    static {
        registerLetters(new BlockableLetter[] {
                new Blockable2DLetterBuilder(new char[] { 'a', 'A' }, 3,
                    "XXX",
                    "X X",
                    "XXX",
                    "X X",
                    "X X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'b', 'B' }, 3,
                    "XX ",
                    "X X",
                    "XX",
                    "X X",
                    "XX "
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'c', 'C' }, 3,
                    "XXX",
                    "X  ",
                    "X  ",
                    "X  ",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'd', 'D' }, 3,
                    "XX ",
                    "X X",
                    "X X",
                    "X X",
                    "XX "
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'e', 'E' }, 3,
                    "XXX",
                    "X  ",
                    "XX",
                    "X  ",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'f', 'F' }, 3,
                    "XXX",
                    "X  ",
                    "XX",
                    "X  ",
                    "X  "
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'g', 'G' }, 3,
                    "XXX",
                    "X  ",
                    "X X",
                    "X X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'h', 'H' }, 3,
                    "X X",
                    "X X",
                    "XXX",
                    "X X",
                    "X X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'i', 'I' }, 3,
                    "XXX",
                    " X ",
                    " X ",
                    " X ",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'j', 'J' }, 3,
                    "  X",
                    "  X",
                    "  X",
                    "  X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'k', 'K' }, 3,
                    "X X",
                    "X X",
                    "XX ",
                    "X X",
                    "X X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'l', 'L' }, 3,
                    "X  ",
                    "X  ",
                    "X  ",
                    "X  ",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'm', 'M' }, 3,
                    "X X",
                    "XXX",
                    "X X",
                    "X X",
                    "X X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'n', 'N' }, 3,
                    "XX ",
                    "X X",
                    "X X",
                    "X X",
                    "X X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'o', 'O' }, 3,
                    "XXX",
                    "X X",
                    "X X",
                    "X X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'p', 'P' }, 3,
                    "XXX",
                    "X X",
                    "XXX",
                    "X  ",
                    "X  "
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'q', 'Q' }, 3,
                    "XXX",
                    "X X",
                    "XXX",
                    "  X",
                    "  X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'r', 'R' }, 3,
                    "XX ",
                    "X X",
                    "XX ",
                    "X X",
                    "X X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 's', 'S' }, 3,
                    "XXX",
                    "X  ",
                    "XXX",
                    "  X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 't', 'T' }, 3,
                    "XXX",
                    " X ",
                    " X ",
                    " X ",
                    " X "
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'u', 'U' }, 3,
                    "X X",
                    "X X",
                    "X X",
                    "X X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'v', 'V' }, 3,
                    "X X",
                    "X X",
                    "X X",
                    "X X",
                    " X "
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'w', 'W' }, 3,
                    "X X",
                    "X X",
                    "X X",
                    "XXX",
                    "X X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'x', 'X' }, 3,
                    "X X",
                    "X X",
                    " X ",
                    "X X",
                    "X X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'y', 'Y' }, 3,
                    "X X",
                    "X X",
                    " X ",
                    " X",
                    " X"
                ).build(),
                new Blockable2DLetterBuilder(new char[] { 'z', 'Z' }, 3,
                    "XXX",
                    "  X",
                    " X ",
                    "X  ",
                    "XXX"
                ).build(),

                new Blockable2DLetterBuilder('0', 3,
                    "XXX",
                    "X X",
                    "X X",
                    "X X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder('1', 3,
                    " X ",
                    "XX ",
                    " X ",
                    " X ",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder('2', 3,
                    "XXX",
                    "  X",
                    "XXX",
                    "X ",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder('3', 3,
                    "XXX",
                    "  X",
                    "XXX",
                    "  X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder('4', 3,
                    "X X",
                    "X X",
                    "XXX",
                    "  X",
                    "  X"
                ).build(),
                new Blockable2DLetterBuilder('5', 3,
                    "XXX",
                    "X ",
                    "XXX",
                    "  X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder('6', 3,
                    "XXX",
                    "X ",
                    "XXX",
                    "X X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder('7', 3,
                    "XXX",
                    "  X",
                    "  X",
                    "  X",
                    "  X"
                ).build(),
                new Blockable2DLetterBuilder('8', 3,
                    "XXX",
                    "X X",
                    "XXX",
                    "X X",
                    "XXX"
                ).build(),
                new Blockable2DLetterBuilder('9', 3,
                    "XXX",
                    "X X",
                    "XXX",
                    "  X",
                    "XXX"
                ).build(),

                new Blockable2DLetterBuilder('.', 1,
                    "X"
                ).build(),
                new Blockable2DLetterBuilder(':', 1,
                    " ",
                    "X",
                    " ",
                    "X",
                    " "
                ).build(),
                new Blockable2DLetterBuilder('\'', 1,
                    "X",
                    " ",
                    " ",
                    " ",
                    " "
                ).build(),
                new Blockable2DLetterBuilder(' ', 1,
                    " "
                ).build()
        });
    }

    /* Call this to override default font */
    public static void registerLetters(BlockableLetter[] blockableLetters) {
        for (BlockableLetter letter : blockableLetters) {
            for (char c : letter.getChars()) {
                defaultFont.put(c, letter);
            }
        }
    }

    @Getter protected final Map<Character, BlockableLetter> font;

    @Getter protected Location location;
    @Getter protected BlockFace facing;
    @Getter protected MutableString word;

    protected Map<Block, BlockableLetter> bufferedWord;
    protected Map<Block, BlockableLetter> currentWord;

    public BlockableWords(Location location, BlockFace facing, MutableString word) {
        this(location, facing, word, defaultFont);
    }

    public BlockableWords(Location location, BlockFace facing, MutableString word, Map<Character, BlockableLetter> font) {
        this.location = location;
        this.facing = facing;
        this.word = word;
        this.font = font;
        this.bufferedWord = new HashMap<>();
        this.currentWord = new HashMap<>();
    }

    protected abstract Material getMaterial(int index, char c, Block block, int xOff, int yOff, int zOff);

    protected void setBlock(int index, char c, Block block, int xOff, int yOff, int zOff) {
        Material material = getMaterial(index, c, block, xOff, yOff, zOff);
        block.setType(material);
    }

    protected void clearBlock(Block block, BlockableLetter letter) {
        block.setType(Material.AIR);
    }

    protected Block applyOffset(Location location, BlockFace facing, int offset, int xOff, int yOff, int zOff) {
        switch (facing) {

            case NORTH: //  1, 2, 3
                return location.getBlock().getRelative(offset + xOff, yOff, zOff);
            case EAST:  //  3, 2, 1
                return location.getBlock().getRelative(zOff, yOff, offset + xOff);
            case SOUTH: // -1, 2, -3
                return location.getBlock().getRelative(-offset - xOff, yOff, - zOff);
            case WEST:  // -3, 2, -1
                return location.getBlock().getRelative(- zOff, yOff, -offset - xOff);
        }
        throw new IllegalArgumentException();
    }

    public void update() {
        bufferedWord.clear();

        String word = this.word.getString();
        char[] chars = word.toCharArray();

        /* Generate new words */
        int offset = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            BlockableLetter letter = font.get(c);

            for (int[] offsets : letter.getOffsets()) {
                int xOff = offsets[0], yOff = offsets[1], zOff = offsets[2];

                Block block = applyOffset(location, facing, offset, xOff, yOff, zOff);
                currentWord.remove(block);
                bufferedWord.put(block, letter);

                setBlock(i, c, block, xOff, yOff, zOff);
            }

            offset += letter.getLength() + 1;
        }

        /* Clear leftovers from last words */
        for (Block block : currentWord.keySet()) {
            BlockableLetter letter = currentWord.get(block);

            clearBlock(block, letter);
        }

        Map<Block, BlockableLetter> temp = bufferedWord;
        bufferedWord = currentWord;
        currentWord = temp;
    }

    public void clear() {
        for (Block block : currentWord.keySet()) {
            BlockableLetter letter = currentWord.get(block);
            clearBlock(block, letter);
        }

        currentWord.clear();
    }
}
