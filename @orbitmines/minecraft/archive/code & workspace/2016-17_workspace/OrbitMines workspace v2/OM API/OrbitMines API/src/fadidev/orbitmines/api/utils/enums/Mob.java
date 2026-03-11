package fadidev.orbitmines.api.utils.enums;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.nms.Nms;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 15-5-2016.
 */
public enum Mob {

    BAT("§8§lBat", 65, "Bat", 8),
    BLAZE("§6§lBlaze", 61, "Blaze", 8),
    CAVE_SPIDER("§3§lCave Spider", 59, "CaveSpider", 8),
    CHICKEN("§7§lChicken", 93, "Chicken", 8),
    COW("§8§lCow", 92, "Cow", 8),
    CREEPER("§a§lCreeper", 50, "Creeper", 8),
    ENDERMAN("§3§lEnderman", 58, "Enderman", 8),
    ENDERMITE("§8§lEndermite", 67, "Endermite", 8),
    GHAST("§f§lGhast", 56, "Ghast", 8),
    GUARDIAN("§3§lGuardian", 68, "Guardian", 8),
    HORSE("§e§lHorse", 100, "EntityHorse", 8),
    IRON_GOLEM("§f§lIron Golem", 99, "VillagerGolem", 8),
    MAGMA_CUBE("§c§lMagma Cube", 62, "LavaSlime", 8),
    MUSHROOM_COW("§c§lMushroom Cow", 96, "MushroomCow", 8),
    OCELOT("§e§lOcelot", 98, "Ozelot", 8),
    PIG("§d§lPig", 90, "Pig", 8),
    PIG_ZOMBIE("§d§lZombie Pigman", 57, "PigZombie", 8),
    POLAR_BEAR("§f§lPolar Bear", 102, "PolarBear", 10),
    RABBIT("§6§lRabbit", 101, "Rabbit", 8),
    SHEEP("§f§lSheep", 91, "Sheep", 8),
    SILVERFISH("§7§lSilverfish", 60, "Silverfish", 8),
    SKELETON("§7§lSkeleton", 51, "Skeleton", 8),
    SLIME("§a§lSlime", 55, "Slime", 8),
    SNOWMAN("§f§lSnowman", 97, "SnowMan", 8),
    SPIDER("§8§lSpider", 52, "Spider", 8),
    SQUID("§3§lSquid", 94, "Squid", 8),
    VILLAGER("§6§lVillager", 120, "Villager", 8),
    WITCH("§3§lWitch", 66, "Witch", 8),
    WITHER("§8§lWither", 64, "WitherBoss", 8),
    WOLF("§7§lWolf", 95, "Wolf", 8),
    ZOMBIE("§2§lZombie", 54, "Zombie", 8);

    private String name;
    private int eggId;
    private String rawName;
    private int requiredVersion;

    Mob(String name, int eggId, String rawName, int requiredVersion){
        this.name = name;
        this.eggId = eggId;
        this.rawName = rawName;
        this.requiredVersion = requiredVersion;
    }

    public String getName() {
        return name;
    }

    public int getEggId() {
        return eggId;
    }

    public String getRawName() {
        return rawName;
    }

    public int getRequiredVersion() {
        return requiredVersion;
    }

    public Entity spawn(Location location){
        return spawn(location, null, false, false);
    }

    public Entity spawn(Location location, String displayName){
        return spawn(location, displayName, false, false);
    }

    public Entity spawnMoving(Location location){
        return spawn(location, null, true, false);
    }

    public Entity spawnMoving(Location location, String displayName){
        return spawn(location, displayName, true, false);
    }

    public Entity spawnNoAttack(Location location){
        return spawn(location, null, true, true);
    }

    public Entity spawnNoAttack(Location location, String displayName){
        return spawn(location, displayName, true, true);
    }

    public Entity spawn(Location location, String displayName, boolean moving, boolean noAttack){
        Nms nms = OrbitMinesAPI.getApi().getNms();
        switch(this){
            case BAT:
                return nms.getBatNpc().spawn(location, displayName, moving, noAttack);
            case BLAZE:
                return nms.getBlazeNpc().spawn(location, displayName, moving, noAttack);
            case CAVE_SPIDER:
                return nms.getCaveSpiderNpc().spawn(location, displayName, moving, noAttack);
            case CHICKEN:
                return nms.getChickenNpc().spawn(location, displayName, moving, noAttack);
            case COW:
                return nms.getCowNpc().spawn(location, displayName, moving, noAttack);
            case CREEPER:
                return nms.getCreeperNpc().spawn(location, displayName, moving, noAttack);
            case ENDERMAN:
                return nms.getEndermanNpc().spawn(location, displayName, moving, noAttack);
            case ENDERMITE:
                return nms.getEndermiteNpc().spawn(location, displayName, moving, noAttack);
            case GHAST:
                return nms.getGhastNpc().spawn(location, displayName, moving, noAttack);
            case GUARDIAN:
                return nms.getGuardianNpc().spawn(location, displayName, moving, noAttack);
            case HORSE:
                return nms.getHorseNpc().spawn(location, displayName, moving, noAttack);
            case IRON_GOLEM:
                return nms.getIronGolemNpc().spawn(location, displayName, moving, noAttack);
            case MAGMA_CUBE:
                return nms.getMagmaCubeNpc().spawn(location, displayName, moving, noAttack);
            case MUSHROOM_COW:
                return nms.getMushroomCowNpc().spawn(location, displayName, moving, noAttack);
            case OCELOT:
                return nms.getOcelotNpc().spawn(location, displayName, moving, noAttack);
            case PIG:
                return nms.getPigNpc().spawn(location, displayName, moving, noAttack);
            case PIG_ZOMBIE:
                return nms.getPigZombieNpc().spawn(location, displayName, moving, noAttack);
            case POLAR_BEAR:
                return nms.getPolarBearNpc().spawn(location, displayName, moving, noAttack);
            case RABBIT:
                return nms.getRabbitNpc().spawn(location, displayName, moving, noAttack);
            case SHEEP:
                return nms.getSheepNpc().spawn(location, displayName, moving, noAttack);
            case SILVERFISH:
                return nms.getSilverfishNpc().spawn(location, displayName, moving, noAttack);
            case SKELETON:
                return nms.getSkeletonNpc().spawn(location, displayName, moving, noAttack);
            case SLIME:
                return nms.getSlimeNpc().spawn(location, displayName, moving, noAttack);
            case SNOWMAN:
                return nms.getSnowmanNpc().spawn(location, displayName, moving, noAttack);
            case SPIDER:
                return nms.getSpiderNpc().spawn(location, displayName, moving, noAttack);
            case SQUID:
                return nms.getSquidNpc().spawn(location, displayName, moving, noAttack);
            case VILLAGER:
                return nms.getVillagerNpc().spawn(location, displayName, moving, noAttack);
            case WITCH:
                return nms.getWitchNpc().spawn(location, displayName, moving, noAttack);
            case WITHER:
                return nms.getWitherNpc().spawn(location, displayName, moving, noAttack);
            case WOLF:
                return nms.getWolfNpc().spawn(location, displayName, moving, noAttack);
            case ZOMBIE:
                return nms.getZombieNpc().spawn(location, displayName, moving, noAttack);
            default:
                return null;
        }
    }

    public Entity spawnPet(Location location){
        return spawnPet(location, null);
    }

    public Entity spawnPet(Location location, String displayName){
        Nms nms = OrbitMinesAPI.getApi().getNms();
        switch(this){
            //case BAT:
            //    return nms.getBatPet().spawn(location, displayName);
            //case BLAZE:
            //    return nms.getBlazePet().spawn(location, displayName);
            //case CAVE_SPIDER:
            //    return nms.getCaveSpiderPet().spawn(location, displayName);
            case CHICKEN:
                return nms.getChickenPet().spawn(location, displayName);
            case COW:
                return nms.getCowPet().spawn(location, displayName);
            case CREEPER:
                return nms.getCreeperPet().spawn(location, displayName);
            //case ENDERMAN:
            //    return nms.getEndermanPet().spawn(location, displayName);
            //case ENDERMITE:
            //    return nms.getEndermitePet().spawn(location, displayName);
            //case GHAST:
            //    return nms.getGhastPet().spawn(location, displayName);
            //case GUARDIAN:
            //    return nms.getGuardianPet().spawn(location, displayName);
            case HORSE:
                Horse horse = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
                horse.setAdult();
                horse.setTamed(true);
                horse.setRemoveWhenFarAway(false);
                horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                horse.setColor(Horse.Color.BROWN);
                horse.setStyle(Horse.Style.WHITE);

                OrbitMinesAPI.getApi().getNms().entity().setSpeed(horse, 0.25);

                horse.setCustomName(displayName);
                horse.setCustomNameVisible(true);
                return horse;
            //case IRON_GOLEM:
            //    return nms.getIronGolemPet().spawn(location, displayName);
            case MAGMA_CUBE:
                return nms.getMagmaCubePet().spawn(location, displayName);
            case MUSHROOM_COW:
                return nms.getMushroomCowPet().spawn(location, displayName);
            case OCELOT:
                return nms.getOcelotPet().spawn(location, displayName);
            case PIG:
                return nms.getPigPet().spawn(location, displayName);
            //case PIG_ZOMBIE:
            //    return nms.getPigZombiePet().spawn(location, displayName);
            //case POLAR_BEAR:
            //    return nms.getPolarBearPet().spawn(location, displayName);
            //case RABBIT:
            //    return nms.getRabbitPet().spawn(location, displayName);
            case SHEEP:
                return nms.getSheepPet().spawn(location, displayName);
            case SILVERFISH:
                return nms.getSilverfishPet().spawn(location, displayName);
            //case SKELETON:
            //    return nms.getSkeletonPet().spawn(location, displayName);
            case SLIME:
                return nms.getSlimePet().spawn(location, displayName);
            //case SNOWMAN:
            //    return nms.getSnowmanPet().spawn(location, displayName);
            case SPIDER:
                return nms.getSpiderPet().spawn(location, displayName);
            case SQUID:
                return nms.getSquidPet().spawn(location, displayName);
            //case VILLAGER:
            //    return nms.getVillagerPet().spawn(location, displayName);
            //case WITCH:
            //    return nms.getWitchPet().spawn(location, displayName);
            //case WITHER:
            //    return nms.getWitherPet().spawn(location, displayName);
            case WOLF:
                return nms.getWolfPet().spawn(location, displayName);
            //case ZOMBIE:
            //    return nms.getZombiePet().spawn(location, displayName);
            default:
                return null;
        }
    }

    public static Mob fromId(int Id){
        for(Mob mob : Mob.values()){
            if(mob.getEggId() == Id)
                return mob;
        }
        return Mob.BLAZE;
    }

    public static Mob fromName(String name){
        for(Mob mob : Mob.values()){
            if(mob.getName().equals(name))
                return mob;
        }
        return Mob.BLAZE;
    }
}
