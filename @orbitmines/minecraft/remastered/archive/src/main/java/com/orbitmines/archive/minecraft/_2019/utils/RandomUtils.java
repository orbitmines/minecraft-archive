package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    /*
        If Minecraft/Spigot ever switches to a more multi-threaded instance:

        Instances of java.util.Random are threadsafe. However, the concurrent use of the same java.util.
        Random instance across threads may encounter contention and consequent poor performance. Consider
        instead using ThreadLocalRandom in multithreaded designs.
     */
    public static Random RANDOM = new Random();

    /* Returns a random int between 0 and (max-1), e.g. setting max to 100 will return 0-99 */
    public static int i(int max) {
        return RANDOM.nextInt(max);
    }

    /* Returns a random int between min and max */
    public static int i(int min, int max) {
        return min + i(max - min);
    }

    public static boolean chance(double percentage) {
        return percentage >= 1 || RANDOM.nextDouble() <= percentage;
    }

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    /* Returns a random object from a collection */
    public static <T> T randomFrom(Collection<T> collection) {
        if (collection.isEmpty())
            return null;

        int random = i(collection.size());
        int step = 0;

        for (T obj : collection) {
            if (random == step)
                return obj;

            step++;
        }
        return null;
    }

    /* Returns a random object from an array */
    public static <T> T randomFrom(T... objects) {
        return objects[i(objects.length)];
    }

    public static <T> Collection<T> randomFrom(Collection<T> list, int count) {
        Collection<T> selected = new ArrayList<>();
        Collection<T> newList = new ArrayList<>(list);

        for (int i = 0; i < count; i++) {
            T obj = randomFrom(newList);
            newList.remove(obj);
            selected.add(obj);
        }

        return selected;
    }

    public static <T> void addToRandomEmptySlot(T object, T[] array) throws ArrayIndexOutOfBoundsException {
        List<Integer> emptyIndexes = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null)
                emptyIndexes.add(i);
        }

        if (emptyIndexes.isEmpty())
            throw new ArrayIndexOutOfBoundsException("there are no free slots in the array");

        array[randomFrom(emptyIndexes)] = object;
    }
}
