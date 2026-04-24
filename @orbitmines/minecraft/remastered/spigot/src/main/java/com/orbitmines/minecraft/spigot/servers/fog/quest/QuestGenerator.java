package com.orbitmines.minecraft.spigot.servers.fog.quest;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Random;

/** Generates SkyBlock-style quests. Keeps the pool intentionally small for POC. */
public class QuestGenerator {

    private static final Random RANDOM = new Random();

    private static final Material[] COLLECT_POOL = {
            Material.COAL, Material.IRON_INGOT, Material.GOLD_INGOT, Material.DIAMOND,
            Material.OAK_LOG, Material.COBBLESTONE, Material.WHEAT, Material.CARROT
    };

    private static final EntityType[] DEFEAT_POOL = {
            EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER, EntityType.SPIDER,
            EntityType.WITCH, EntityType.BLAZE
    };

    public static Quest random(int level) {
        boolean collect = RANDOM.nextBoolean();
        if (collect) {
            Material m = COLLECT_POOL[RANDOM.nextInt(COLLECT_POOL.length)];
            int req = 16 + RANDOM.nextInt(48) + level * 4;
            return new Quest("q_" + System.currentTimeMillis(), QuestType.COLLECT, m.name(), req, 50 + level * 20, 40 + level * 10);
        } else {
            EntityType e = DEFEAT_POOL[RANDOM.nextInt(DEFEAT_POOL.length)];
            int req = 3 + RANDOM.nextInt(12) + level;
            return new Quest("q_" + System.currentTimeMillis(), QuestType.DEFEAT, e.name(), req, 80 + level * 30, 60 + level * 15);
        }
    }
}
