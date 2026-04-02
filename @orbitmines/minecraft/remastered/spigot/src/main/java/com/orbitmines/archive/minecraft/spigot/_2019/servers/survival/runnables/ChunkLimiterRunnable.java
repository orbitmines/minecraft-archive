package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.WorldUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.*;
import java.util.stream.Collectors;

public class ChunkLimiterRunnable extends PassiveRunnable<Survival> {

    private Survival survival;
    private List<World> worlds;

    public ChunkLimiterRunnable(Survival plugin) {
        super(plugin, Interval.of(TimeUnit.SECOND, 15));

        this.survival = plugin;
        this.worlds = Arrays.asList(plugin.getWorld(), plugin.getWorldNether(), plugin.getWorldTheEnd());
    }

    @Override
    public void onRun() {
        Map<Chunk, List<Entity>> searches = new HashMap<>();

        for (World world : worlds) {
            for (Chunk chunk : world.getLoadedChunks()) {
                Entity[] inChunk = chunk.getEntities();
                int count = inChunk.length;

                if (count <= Survival.MAX_ENTITY_PER_CHUNK)
                    continue;

                List<Entity> entities = new EntitySearch(inChunk).search(count - Survival.MAX_ENTITY_PER_CHUNK);

                if (entities.size() == 0)
                    continue;

                searches.put(chunk, entities);
            }
        }

        if (searches.size() == 0)
            return;

        survival.runSync(() -> {
            for (Chunk chunk : searches.keySet()) {
                if (!chunk.isLoaded())
                    return;

                List<Entity> entities = searches.get(chunk);
                Map<EntityType, Integer> entityTypes = new HashMap<>();

                for (Entity entity : entities) {
                    entityTypes.put(entity.getType(), entityTypes.computeIfAbsent(entity.getType(), type -> 0) + 1);

                    entity.remove();
                }

                /* TODO: Remove, this is a 1.14 fix */
                for (Entity nearby : entities.get(0).getNearbyEntities(30, 30, 30)) {
                    if (!(nearby instanceof Player))
                        continue;

                    for (Entity entity : entities) {
                        survival.getNms().entity().destroyEntity((Player) nearby, entity);
                    }
                }

                notify(chunk, entities.size(), entityTypes);
            }
        });
    }

    private void notify(Chunk chunk, int count, Map<EntityType, Integer> entityTypes) {
        EmbedBuilder message = new EmbedBuilder();
        message.setAuthor("Chunk " + chunk.getX() + ", " + chunk.getZ() + " (x: " + WorldUtils.chunkToBlock(chunk.getX()) + ", z: " + WorldUtils.chunkToBlock(chunk.getZ()) + ")");
        message.setDescription("Removed " + count + " entities");

        for (EntityType type : entityTypes.keySet()) {
            message.addField(type.toString(), entityTypes.get(type) + "", false);
        }

        message.setColor(Color.ORANGE.getAwtColor());

        survival.discord(bot -> bot.getTextChannel(CustomChannel.ENTITY_CLEAR_LOG).sendMessageEmbeds(message.build()).queue());
    }

    public class EntitySearch {

        /* Priorities by entity type, removal with the first */
        private static final int ITEM_PRIORITY = 0;
        private static final int MONSTER_PRIORITY = 1;
        private static final int FLYING_PRIORITY = 2;
        private static final int WATER_MOB_PRIORITY = 3;
        private static final int ANIMAL_PRIORITY = 4;
        private static final int VILLAGER_PRIORITY = 5;
        private static final int TAMABLE_PRIORITY = 6;

        private final Entity[] inChunk;

        public EntitySearch(Entity[] inChunk) {
            this.inChunk = inChunk;
        }

        public List<Entity> search(int count) {
            return Arrays.stream(inChunk).
                filter(entity ->
                    /* Not the following entities */
                    !(entity instanceof Player)
                        && !(entity instanceof ItemFrame)
                        && !(entity instanceof Minecart)
                        && !(entity instanceof Boss)
                        && !(entity instanceof ElderGuardian)
                        && !(entity instanceof EnderCrystal)
                    /* Skip entities with NameTag */
                    && entity.getCustomName() == null
                    /* All non-items and items that our candidate for removal */
                    && (!(entity instanceof Item) && !(entity instanceof ExperienceOrb) || entity.getTicksLived() > Survival.TICKS_FOR_ITEM_AS_REMOVABLE_CANDIDATE)
                ).
                /* Sort by priority of removal, the first element being the first to remove */
                sorted((entity1, entity2) -> {
                    int priority1 = getPriority(entity1);
                    int priority2 = getPriority(entity2);

                    return priority1 - priority2;
                }).
                /* Limit by given count */
                limit(count).

                collect(Collectors.toList());
        }

        private int getPriority(Entity entity) {
            if (entity instanceof Item || entity instanceof ExperienceOrb)
                return ITEM_PRIORITY;
            else if (entity instanceof AbstractVillager)
                return VILLAGER_PRIORITY;
            else if (entity instanceof Tameable)
                return TAMABLE_PRIORITY;
            else if (entity instanceof WaterMob)
                return WATER_MOB_PRIORITY;
            else if (entity instanceof Flying)
                return FLYING_PRIORITY;
            else if (entity instanceof Animals)
                return ANIMAL_PRIORITY;
            else if (entity instanceof Monster)
                return MONSTER_PRIORITY;
            else
                return Integer.MAX_VALUE;
        }
    }
}
