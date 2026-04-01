package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.Nms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.MobNpcNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Arrays;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public enum Mob {

    ALLAY(EntityType.ALLAY, Material.ALLAY_SPAWN_EGG, "Allay"),
    ARMADILLO(EntityType.ARMADILLO, Material.ARMADILLO_SPAWN_EGG, "Armadillo"),
    AXOLOTL(EntityType.AXOLOTL, Material.AXOLOTL_SPAWN_EGG, "Axolotl"),
    BAT(EntityType.BAT, Material.BAT_SPAWN_EGG, "Bat"),
    BEE(EntityType.BEE, Material.BEE_SPAWN_EGG, "Bee"),
    BLAZE(EntityType.BLAZE, Material.BLAZE_SPAWN_EGG, "Blaze"),
    BOGGED(EntityType.BOGGED, Material.BOGGED_SPAWN_EGG, "Bogged"),
    BREEZE(EntityType.BREEZE, Material.BREEZE_SPAWN_EGG, "Breeze"),
    CAMEL(EntityType.CAMEL, Material.CAMEL_SPAWN_EGG, "Camel"),
    CAMEL_HUSK(EntityType.CAMEL_HUSK, Material.CAMEL_HUSK_SPAWN_EGG, "Camel Husk"),
    CAT(EntityType.CAT, Material.CAT_SPAWN_EGG, "Cat"),
    CAVE_SPIDER(EntityType.CAVE_SPIDER, Material.CAVE_SPIDER_SPAWN_EGG, "Cave Spider"),
    CHICKEN(EntityType.CHICKEN, Material.CHICKEN_SPAWN_EGG, "Chicken"),
    COD(EntityType.COD, Material.COD_SPAWN_EGG, "Cod"),
    COPPER_GOLEM(EntityType.COPPER_GOLEM, Material.COPPER_GOLEM_SPAWN_EGG, "Copper Golem"),
    COW(EntityType.COW, Material.COW_SPAWN_EGG, "Cow"),
    CREAKING(EntityType.CREAKING, Material.CREAKING_SPAWN_EGG, "Creaking"),
    CREEPER(EntityType.CREEPER, Material.CREEPER_SPAWN_EGG, "Creeper"),
    DOLPHIN(EntityType.DOLPHIN, Material.DOLPHIN_SPAWN_EGG, "Dolphin"),
    DONKEY(EntityType.DONKEY, Material.DONKEY_SPAWN_EGG, "Donkey"),
    DROWNED(EntityType.DROWNED, Material.DROWNED_SPAWN_EGG, "Drowned"),
    ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN, Material.ELDER_GUARDIAN_SPAWN_EGG, "Elder Guardian"),
    ENDER_DRAGON(EntityType.ENDER_DRAGON, Material.DRAGON_EGG, "Ender Dragon"),
    ENDERMAN(EntityType.ENDERMAN, Material.ENDERMAN_SPAWN_EGG, "Enderman"),
    ENDERMITE(EntityType.ENDERMITE, Material.ENDERMITE_SPAWN_EGG, "Endermite"),
    EVOKER(EntityType.EVOKER, Material.EVOKER_SPAWN_EGG, "Evoker"),
    FOX(EntityType.FOX, Material.FOX_SPAWN_EGG, "Fox"),
    FROG(EntityType.FROG, Material.FROG_SPAWN_EGG, "Frog"),
    GHAST(EntityType.GHAST, Material.GHAST_SPAWN_EGG, "Ghast"),
    GIANT(EntityType.GIANT, Material.ZOMBIE_SPAWN_EGG, "Giant"),
    GLOW_SQUID(EntityType.GLOW_SQUID, Material.GLOW_SQUID_SPAWN_EGG, "Glow Squid"),
    GOAT(EntityType.GOAT, Material.GOAT_SPAWN_EGG, "Goat"),
    GUARDIAN(EntityType.GUARDIAN, Material.GUARDIAN_SPAWN_EGG, "Guardian"),
    HAPPY_GHAST(EntityType.HAPPY_GHAST, Material.HAPPY_GHAST_SPAWN_EGG, "Happy Ghast"),
    HOGLIN(EntityType.HOGLIN, Material.HOGLIN_SPAWN_EGG, "Hoglin"),
    HORSE(EntityType.HORSE, Material.HORSE_SPAWN_EGG, "Horse"),
    HUSK(EntityType.HUSK, Material.HUSK_SPAWN_EGG, "Husk"),
    ILLUSIONER(EntityType.ILLUSIONER, Material.PILLAGER_SPAWN_EGG, "Illusioner"),
    IRON_GOLEM(EntityType.IRON_GOLEM, Material.IRON_GOLEM_SPAWN_EGG, "Iron Golem"),
    LLAMA(EntityType.LLAMA, Material.LLAMA_SPAWN_EGG, "Llama"),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, Material.MAGMA_CUBE_SPAWN_EGG, "Magma Cube"),
    MULE(EntityType.MULE, Material.MULE_SPAWN_EGG, "Mule"),
    MUSHROOM_COW(EntityType.MOOSHROOM, Material.MOOSHROOM_SPAWN_EGG, "Mushroom Cow"),
    NAUTILUS(EntityType.NAUTILUS, Material.NAUTILUS_SPAWN_EGG, "Nautilus"),
    OCELOT(EntityType.OCELOT, Material.OCELOT_SPAWN_EGG, "Ocelot"),
    PANDA(EntityType.PANDA, Material.PANDA_SPAWN_EGG, "Panda"),
    PARCHED(EntityType.PARCHED, Material.PARCHED_SPAWN_EGG, "Parched"),
    PHANTOM(EntityType.PHANTOM, Material.PHANTOM_SPAWN_EGG, "Phantom"),
    PARROT(EntityType.PARROT, Material.PARROT_SPAWN_EGG, "Parrot"),
    PIG(EntityType.PIG, Material.PIG_SPAWN_EGG, "Pig"),
    PIGLIN(EntityType.PIGLIN, Material.PIGLIN_SPAWN_EGG, "Piglin"),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, Material.PIGLIN_BRUTE_SPAWN_EGG, "Piglin Brute"),
    PILLAGER(EntityType.PILLAGER, Material.PILLAGER_SPAWN_EGG, "Pillager"),
    POLAR_BEAR(EntityType.POLAR_BEAR, Material.POLAR_BEAR_SPAWN_EGG, "Polar Bear"),
    PUFFERFISH(EntityType.PUFFERFISH, Material.PUFFERFISH_SPAWN_EGG, "Pufferfish"),
    RABBIT(EntityType.RABBIT, Material.RABBIT_SPAWN_EGG, "Rabbit"),
    RAVAGER(EntityType.RAVAGER, Material.RAVAGER_SPAWN_EGG, "Ravager"),
    SALMON(EntityType.SALMON, Material.SALMON_SPAWN_EGG, "Salmon"),
    SHEEP(EntityType.SHEEP, Material.SHEEP_SPAWN_EGG, "Sheep"),
    SHULKER(EntityType.SHULKER, Material.SHULKER_SPAWN_EGG, "Shulker"),
    SILVERFISH(EntityType.SILVERFISH, Material.SILVERFISH_SPAWN_EGG, "Silverfish"),
    SKELETON(EntityType.SKELETON, Material.SKELETON_SPAWN_EGG, "Skeleton"),
    SKELETON_HORSE(EntityType.SKELETON_HORSE, Material.SKELETON_HORSE_SPAWN_EGG, "Skeleton Horse"),
    SLIME(EntityType.SLIME, Material.SLIME_SPAWN_EGG, "Slime"),
    SNIFFER(EntityType.SNIFFER, Material.SNIFFER_SPAWN_EGG, "Sniffer"),
    SNOW_GOLEM(EntityType.SNOW_GOLEM, Material.SNOW_GOLEM_SPAWN_EGG, "Snow Golem"),
    SPIDER(EntityType.SPIDER, Material.SPIDER_SPAWN_EGG, "Spider"),
    SQUID(EntityType.SQUID, Material.SQUID_SPAWN_EGG, "Squid"),
    STRAY(EntityType.STRAY, Material.STRAY_SPAWN_EGG, "Stray"),
    STRIDER(EntityType.STRIDER, Material.STRIDER_SPAWN_EGG, "Strider"),
    TADPOLE(EntityType.TADPOLE, Material.TADPOLE_SPAWN_EGG, "Tadpole"),
    TRADER_LLAMA(EntityType.TRADER_LLAMA, Material.TRADER_LLAMA_SPAWN_EGG, "Trader Llama"),
    TROPICAL_FISH(EntityType.TROPICAL_FISH, Material.TROPICAL_FISH_SPAWN_EGG, "Tropical Fish"),
    TURTLE(EntityType.TURTLE, Material.TURTLE_SPAWN_EGG, "Turtle"),
    VEX(EntityType.VEX, Material.VEX_SPAWN_EGG, "Vex"),
    VILLAGER(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG, "Villager"),
    VINDICATOR(EntityType.VINDICATOR, Material.VINDICATOR_SPAWN_EGG, "Vindicator"),
    WANDERING_TRADER(EntityType.WANDERING_TRADER, Material.WANDERING_TRADER_SPAWN_EGG, "Wandering Trader"),
    WARDEN(EntityType.WARDEN, Material.WARDEN_SPAWN_EGG, "Warden"),
    WITCH(EntityType.WITCH, Material.WITCH_SPAWN_EGG, "Witch"),
    WITHER(EntityType.WITHER, Material.WITHER_SKELETON_SPAWN_EGG, "Wither"),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, Material.WITHER_SKELETON_SPAWN_EGG, "Wither Skeleton"),
    WOLF(EntityType.WOLF, Material.WOLF_SPAWN_EGG, "Wolf"),
    ZOGLIN(EntityType.ZOGLIN, Material.ZOGLIN_SPAWN_EGG, "Zoglin"),
    ZOMBIE(EntityType.ZOMBIE, Material.ZOMBIE_SPAWN_EGG, "Zombie"),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, Material.ZOMBIE_HORSE_SPAWN_EGG, "Zombie Horse"),
    ZOMBIE_NAUTILUS(EntityType.ZOMBIE_NAUTILUS, Material.ZOMBIE_NAUTILUS_SPAWN_EGG, "Zombie Nautilus"),
    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, Material.ZOMBIE_VILLAGER_SPAWN_EGG, "Zombie Villager"),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN, Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, "Zombified Piglin");

    private final EntityType type;
    private final Material spawnEggMaterial;
    private final String name;

    Mob(EntityType type, Material spawnEggMaterial, String name) {
        this.type = type;
        this.spawnEggMaterial = spawnEggMaterial;
        this.name = name;
    }

    public EntityType getType() {
        return type;
    }

    public Material getSpawnEggMaterial() {
        return spawnEggMaterial;
    }

    public String getName() {
        return name;
    }

    public Entity spawn(Location location, MobNpcNms.Option... optionsArray) {
        return nms().spawn(location, Arrays.asList(optionsArray));
    }

    public MobNpcNms nms() {
        Nms nms = SpigotServer.getInstance().getNms();

        switch (this) {

            case ALLAY:
                return nms.getAllayNpc();
            case ARMADILLO:
                return nms.getArmadilloNpc();
            case AXOLOTL:
                return nms.getAxolotlNpc();
            case BAT:
                return nms.getBatNpc();
            case BEE:
                return nms.getBeeNpc();
            case BLAZE:
                return nms.getBlazeNpc();
            case BOGGED:
                return nms.getBoggedNpc();
            case BREEZE:
                return nms.getBreezeNpc();
            case CAMEL:
                return nms.getCamelNpc();
            case CAMEL_HUSK:
                return nms.getCamelHuskNpc();
            case CAT:
                return nms.getCatNpc();
            case CAVE_SPIDER:
                return nms.getCaveSpiderNpc();
            case CHICKEN:
                return nms.getChickenNpc();
            case COD:
                return nms.getCodNpc();
            case COPPER_GOLEM:
                return nms.getCopperGolemNpc();
            case COW:
                return nms.getCowNpc();
            case CREAKING:
                return nms.getCreakingNpc();
            case CREEPER:
                return nms.getCreeperNpc();
            case DOLPHIN:
                return nms.getDolphinNpc();
            case DONKEY:
                return nms.getDonkeyNpc();
            case DROWNED:
                return nms.getDrownedNpc();
            case ELDER_GUARDIAN:
                return nms.getElderGuardianNpc();
            case ENDER_DRAGON:
                return nms.getEnderDragonNpc();
            case ENDERMAN:
                return nms.getEndermanNpc();
            case ENDERMITE:
                return nms.getEndermiteNpc();
            case EVOKER:
                return nms.getEvokerNpc();
            case FOX:
                return nms.getFoxNpc();
            case FROG:
                return nms.getFrogNpc();
            case GHAST:
                return nms.getGhastNpc();
            case GIANT:
                return nms.getGiantNpc();
            case GLOW_SQUID:
                return nms.getGlowSquidNpc();
            case GOAT:
                return nms.getGoatNpc();
            case GUARDIAN:
                return nms.getGuardianNpc();
            case HAPPY_GHAST:
                return nms.getHappyGhastNpc();
            case HOGLIN:
                return nms.getHoglinNpc();
            case HORSE:
                return nms.getHorseNpc();
            case HUSK:
                return nms.getHuskNpc();
            case ILLUSIONER:
                return nms.getIllusionerNpc();
            case IRON_GOLEM:
                return nms.getIronGolemNpc();
            case LLAMA:
                return nms.getLlamaNpc();
            case MAGMA_CUBE:
                return nms.getMagmaCubeNpc();
            case MULE:
                return nms.getMuleNpc();
            case MUSHROOM_COW:
                return nms.getMushroomCowNpc();
            case NAUTILUS:
                return nms.getNautilusNpc();
            case OCELOT:
                return nms.getOcelotNpc();
            case PANDA:
                return nms.getPandaNpc();
            case PARCHED:
                return nms.getParchedNpc();
            case PHANTOM:
                return nms.getPhantomNpc();
            case PARROT:
                return nms.getParrotNpc();
            case PIG:
                return nms.getPigNpc();
            case PIGLIN:
                return nms.getPiglinNpc();
            case PIGLIN_BRUTE:
                return nms.getPiglinBruteNpc();
            case PILLAGER:
                return nms.getPillagerNpc();
            case POLAR_BEAR:
                return nms.getPolarBearNpc();
            case PUFFERFISH:
                return nms.getPufferFishNpc();
            case RABBIT:
                return nms.getRabbitNpc();
            case RAVAGER:
                return nms.getRavagerNpc();
            case SALMON:
                return nms.getSalmonNpc();
            case SHEEP:
                return nms.getSheepNpc();
            case SHULKER:
                return nms.getShulkerNpc();
            case SILVERFISH:
                return nms.getSilverfishNpc();
            case SKELETON:
                return nms.getSkeletonNpc();
            case SKELETON_HORSE:
                return nms.getSkeletonHorseNpc();
            case SLIME:
                return nms.getSlimeNpc();
            case SNIFFER:
                return nms.getSnifferNpc();
            case SNOW_GOLEM:
                return nms.getSnowmanNpc();
            case SPIDER:
                return nms.getSpiderNpc();
            case SQUID:
                return nms.getSquidNpc();
            case STRAY:
                return nms.getStrayNpc();
            case STRIDER:
                return nms.getStriderNpc();
            case TADPOLE:
                return nms.getTadpoleNpc();
            case TRADER_LLAMA:
                return nms.getTraderLlamaNpc();
            case TROPICAL_FISH:
                return nms.getTropicalFishNpc();
            case TURTLE:
                return nms.getTurtleNpc();
            case VEX:
                return nms.getVexNpc();
            case VILLAGER:
                return nms.getVillagerNpc();
            case VINDICATOR:
                return nms.getVindicatorNpc();
            case WANDERING_TRADER:
                return nms.getWanderingTraderNpc();
            case WARDEN:
                return nms.getWardenNpc();
            case WITCH:
                return nms.getWitchNpc();
            case WITHER:
                return nms.getWitherNpc();
            case WITHER_SKELETON:
                return nms.getWitherSkeletonNpc();
            case WOLF:
                return nms.getWolfNpc();
            case ZOGLIN:
                return nms.getZoglinNpc();
            case ZOMBIE:
                return nms.getZombieNpc();
            case ZOMBIE_HORSE:
                return nms.getZombieHorseNpc();
            case ZOMBIE_NAUTILUS:
                return nms.getZombieNautilusNpc();
            case ZOMBIE_VILLAGER:
                return nms.getZombieVillagerNpc();
            case ZOMBIFIED_PIGLIN:
                return nms.getPigZombieNpc();
        }
        throw new IllegalStateException();
    }

    public static Mob from(String name) {
        for (Mob mob : Mob.values()) {
            if (mob.getName().equals(name))
                return mob;
        }
        return null;
    }

    public static Mob from(EntityType type) {
        for (Mob mob : Mob.values()) {
            if (mob.getType() == type)
                return mob;
        }
        return null;
    }
}
