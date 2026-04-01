package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ConsoleUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.actionbar.ActionBarNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.actionbar.ActionBarNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.armorstand.ArmorStandNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.armorstand.ArmorStandNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.bednpc.BedNpcNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.bednpc.BedNpcNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.brigadier.BrigadierNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.brigadier.BrigadierNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.firework.FireworkNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.firework.FireworkNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.NpcNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.NpcNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.bat.BatNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.bat.BatNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.blaze.BlazeNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.blaze.BlazeNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cave_spider.CaveSpiderNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cave_spider.CaveSpiderNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.chicken.ChickenNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.chicken.ChickenNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cod.CodNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cod.CodNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cow.CowNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cow.CowNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.creeper.CreeperNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.creeper.CreeperNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.dolphin.DolphinNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.dolphin.DolphinNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.donkey.DonkeyNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.donkey.DonkeyNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.drowned.DrownedNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.drowned.DrownedNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.elder_guardian.ElderGuardianNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.elder_guardian.ElderGuardianNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ender_dragon.EnderDragonNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ender_dragon.EnderDragonNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.enderman.EndermanNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.enderman.EndermanNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.endermite.EndermiteNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.endermite.EndermiteNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.evoker.EvokerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.evoker.EvokerNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ghast.GhastNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ghast.GhastNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.giant.GiantNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.giant.GiantNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.guardian.GuardianNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.guardian.GuardianNpc_1_13_R2;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.horse.HorseNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.horse.HorseNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.husk.HuskNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.husk.HuskNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.illusioner.IllusionerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.illusioner.IllusionerNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.iron_golem.IronGolemNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.iron_golem.IronGolemNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.llama.LlamaNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.llama.LlamaNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.magma_cube.MagmaCubeNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.magma_cube.MagmaCubeNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.mule.MuleNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.mule.MuleNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.mushroom_cow.MushroomCowNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.mushroom_cow.MushroomCowNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ocelot.OcelotNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ocelot.OcelotNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.parrot.ParrotNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.parrot.ParrotNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.phantom.PhantomNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.phantom.PhantomNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pig.PigNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pig.PigNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pig_zombie.PigZombieNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pig_zombie.PigZombieNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.polar_bear.PolarBearNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.polar_bear.PolarBearNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pufferfish.PufferFishNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pufferfish.PufferFishNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.rabbit.RabbitNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.rabbit.RabbitNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.salmon.SalmonNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.salmon.SalmonNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.sheep.SheepNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.sheep.SheepNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.shulker.ShulkerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.shulker.ShulkerNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.silverfish.SilverfishNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.silverfish.SilverfishNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.skeleton.SkeletonNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.skeleton.SkeletonNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.skeleton_horse.SkeletonHorseNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.skeleton_horse.SkeletonHorseNpc_1_13_R2;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.slime.SlimeNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.slime.SlimeNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.snowman.SnowmanNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.snowman.SnowmanNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.spider.SpiderNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.spider.SpiderNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.squid.SquidNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.squid.SquidNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.stray.StrayNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.stray.StrayNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.tropical_fish.TropicalFishNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.tropical_fish.TropicalFishNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.turtle.TurtleNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.turtle.TurtleNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.vex.VexNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.vex.VexNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.villager.VillagerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.villager.VillagerNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.vindicator.VindicatorNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.vindicator.VindicatorNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.witch.WitchNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.witch.WitchNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wither.WitherNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wither.WitherNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wither_skeleton.WitherSkeletonNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wither_skeleton.WitherSkeletonNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wolf.WolfNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wolf.WolfNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie.ZombieNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie.ZombieNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_horse.ZombieHorseNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_horse.ZombieHorseNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_villager.ZombieVillagerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_villager.ZombieVillagerNpc_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.tablist.TabListNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.tablist.TabListNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.world.WorldNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.world.WorldNms_1_14_R1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import org.bukkit.entity.Player;

/*
* OrbitMines - @author Fadi Shawki - 26-6-2017
*/
public class Nms {

    private String version;

    /* NPCs */
    private NpcNms npcNms;

    @Getter private BatNpc batNpc;
    @Getter private BlazeNpc blazeNpc;
    @Getter private CaveSpiderNpc caveSpiderNpc;
    @Getter private ChickenNpc chickenNpc;
    @Getter private CodNpc codNpc;
    @Getter private CowNpc cowNpc;
    @Getter private CreeperNpc creeperNpc;
    @Getter private DolphinNpc dolphinNpc;
    @Getter private DonkeyNpc donkeyNpc;
    @Getter private DrownedNpc drownedNpc;
    @Getter private ElderGuardianNpc elderGuardianNpc;
    @Getter private EnderDragonNpc enderDragonNpc;
    @Getter private EndermanNpc endermanNpc;
    @Getter private EndermiteNpc endermiteNpc;
    @Getter private EvokerNpc evokerNpc;
    @Getter private GhastNpc ghastNpc;
    @Getter private GiantNpc giantNpc;
    @Getter private GuardianNpc guardianNpc;
    @Getter private HorseNpc horseNpc;
    @Getter private HuskNpc huskNpc;
    @Getter private IllusionerNpc illusionerNpc;
    @Getter private IronGolemNpc ironGolemNpc;
    @Getter private LlamaNpc llamaNpc;
    @Getter private MagmaCubeNpc magmaCubeNpc;
    @Getter private MuleNpc muleNpc;
    @Getter private MushroomCowNpc mushroomCowNpc;
    @Getter private OcelotNpc ocelotNpc;
    @Getter private ParrotNpc parrotNpc;
    @Getter private PhantomNpc phantomNpc;
    @Getter private PigNpc pigNpc;
    @Getter private PigZombieNpc pigZombieNpc;
    @Getter private PolarBearNpc polarBearNpc;
    @Getter private PufferFishNpc pufferFishNpc;
    @Getter private RabbitNpc rabbitNpc;
    @Getter private SalmonNpc salmonNpc;
    @Getter private SheepNpc sheepNpc;
    @Getter private ShulkerNpc shulkerNpc;
    @Getter private SilverfishNpc silverfishNpc;
    @Getter private SkeletonNpc skeletonNpc;
    @Getter private SkeletonHorseNpc skeletonHorseNpc;
    @Getter private SlimeNpc slimeNpc;
    @Getter private SnowmanNpc snowmanNpc;
    @Getter private SpiderNpc spiderNpc;
    @Getter private SquidNpc squidNpc;
    @Getter private StrayNpc strayNpc;
    @Getter private TropicalFishNpc tropicalFishNpc;
    @Getter private TurtleNpc turtleNpc;
    @Getter private VexNpc vexNpc;
    @Getter private VillagerNpc villagerNpc;
    @Getter private VindicatorNpc vindicatorNpc;
    @Getter private WitchNpc witchNpc;
    @Getter private WitherNpc witherNpc;
    @Getter private WitherSkeletonNpc witherSkeletonNpc;
    @Getter private WolfNpc wolfNpc;
    @Getter private ZombieNpc zombieNpc;
    @Getter private ZombieHorseNpc zombieHorseNpc;
    @Getter private ZombieVillagerNpc zombieVillagerNpc;

    /* Entity */
    private EntityNms entityNms;
    /* ActionBar */
    private ActionBarNms actionBarNms;
    /* ItemStack */
    private ItemStackNms itemStackNms;
    /* ArmorStand */
    private ArmorStandNms armorStandNms;
    /* BedNpc */
    private BedNpcNms bedNpcNms;
    /* Firework */
    private FireworkNms fireworkNms;
    /* TabList */
    private TabListNms tabListNms;
    /* World */
    private WorldNms worldNms;
    /* Brigadier */
    private BrigadierNms brigadierNms;

    public Nms(SpigotServer server) {
        this.version = checkVersion(server);

        if (this.version == null) {
            ConsoleUtils.empty();
            ConsoleUtils.warn("Error while registering NMS!");
            ConsoleUtils.warn("Disabling plugin...");
            ConsoleUtils.empty();
            server.getServer().getPluginManager().disablePlugin(server);
        }
    }

    //TODO, Only initialize classes when called through method, so we are not creating useless objects in memory
    private String checkVersion(SpigotServer server) {
        String version;

        try {
            version = server.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }

        switch (version) {

            case "v1_14_R1":
                /* NPCs */
                npcNms = new NpcNms_1_14_R1();

                batNpc = new BatNpc_1_14_R1(npcNms);
                blazeNpc = new BlazeNpc_1_14_R1(npcNms);
                caveSpiderNpc = new CaveSpiderNpc_1_14_R1(npcNms);
                chickenNpc = new ChickenNpc_1_14_R1(npcNms);
                codNpc = new CodNpc_1_14_R1(npcNms);
                cowNpc = new CowNpc_1_14_R1(npcNms);
                creeperNpc = new CreeperNpc_1_14_R1(npcNms);
                dolphinNpc = new DolphinNpc_1_14_R1(npcNms);
                donkeyNpc = new DonkeyNpc_1_14_R1(npcNms);
                drownedNpc = new DrownedNpc_1_14_R1(npcNms);
                elderGuardianNpc = new ElderGuardianNpc_1_14_R1(npcNms);
                enderDragonNpc = new EnderDragonNpc_1_14_R1(npcNms);
                endermanNpc = new EndermanNpc_1_14_R1(npcNms);
                endermiteNpc = new EndermiteNpc_1_14_R1(npcNms);
                evokerNpc = new EvokerNpc_1_14_R1(npcNms);
                ghastNpc = new GhastNpc_1_14_R1(npcNms);
                giantNpc = new GiantNpc_1_14_R1(npcNms);
                guardianNpc = new GuardianNpc_1_13_R2(npcNms);
                horseNpc = new HorseNpc_1_14_R1(npcNms);
                huskNpc = new HuskNpc_1_14_R1(npcNms);
                illusionerNpc = new IllusionerNpc_1_14_R1(npcNms);
                ironGolemNpc = new IronGolemNpc_1_14_R1(npcNms);
                llamaNpc = new LlamaNpc_1_14_R1(npcNms);
                magmaCubeNpc = new MagmaCubeNpc_1_14_R1(npcNms);
                muleNpc = new MuleNpc_1_14_R1(npcNms);
                mushroomCowNpc = new MushroomCowNpc_1_14_R1(npcNms);
                ocelotNpc = new OcelotNpc_1_14_R1(npcNms);
                parrotNpc = new ParrotNpc_1_14_R1(npcNms);
                phantomNpc = new PhantomNpc_1_14_R1(npcNms);
                pigNpc = new PigNpc_1_14_R1(npcNms);
                pigZombieNpc = new PigZombieNpc_1_14_R1(npcNms);
                polarBearNpc = new PolarBearNpc_1_14_R1(npcNms);
                pufferFishNpc = new PufferFishNpc_1_14_R1(npcNms);
                rabbitNpc = new RabbitNpc_1_14_R1(npcNms);
                salmonNpc = new SalmonNpc_1_14_R1(npcNms);
                sheepNpc = new SheepNpc_1_14_R1(npcNms);
                shulkerNpc = new ShulkerNpc_1_14_R1(npcNms);
                silverfishNpc = new SilverfishNpc_1_14_R1(npcNms);
                skeletonNpc = new SkeletonNpc_1_14_R1(npcNms);
                skeletonHorseNpc = new SkeletonHorseNpc_1_13_R2(npcNms);
                slimeNpc = new SlimeNpc_1_14_R1(npcNms);
                snowmanNpc = new SnowmanNpc_1_14_R1(npcNms);
                spiderNpc = new SpiderNpc_1_14_R1(npcNms);
                squidNpc = new SquidNpc_1_14_R1(npcNms);
                strayNpc = new StrayNpc_1_14_R1(npcNms);
                tropicalFishNpc = new TropicalFishNpc_1_14_R1(npcNms);
                turtleNpc = new TurtleNpc_1_14_R1(npcNms);
                vexNpc = new VexNpc_1_14_R1(npcNms);
                villagerNpc = new VillagerNpc_1_14_R1(npcNms);
                vindicatorNpc = new VindicatorNpc_1_14_R1(npcNms);
                witchNpc = new WitchNpc_1_14_R1(npcNms);
                witherNpc = new WitherNpc_1_14_R1(npcNms);
                witherSkeletonNpc = new WitherSkeletonNpc_1_14_R1(npcNms);
                wolfNpc = new WolfNpc_1_14_R1(npcNms);
                zombieNpc = new ZombieNpc_1_14_R1(npcNms);
                zombieHorseNpc = new ZombieHorseNpc_1_14_R1(npcNms);
                zombieVillagerNpc = new ZombieVillagerNpc_1_14_R1(npcNms);

                /* Entity */
                entityNms = new EntityNms_1_14_R1();
                /* ActionBar */
                actionBarNms = new ActionBarNms_1_14_R1();
                /* ItemStack */
                itemStackNms = new ItemStackNms_1_14_R1();
                /* ArmorStand */
                armorStandNms = new ArmorStandNms_1_14_R1();
                /* BedNpc */
                bedNpcNms = new BedNpcNms_1_14_R1(server);
                /* Firework */
                fireworkNms = new FireworkNms_1_14_R1(server);
                /* TabList */
                tabListNms = new TabListNms_1_14_R1();
                /* World */
                worldNms = new WorldNms_1_14_R1();
                /* Brigadier */
                brigadierNms = new BrigadierNms_1_14_R1();

                break;
            default:
                return null;
        }

        return version;
    }

    public String getVersion() {
        return version;
    }

    public NpcNms npc() {
        return npcNms;
    }

    public EntityNms entity() {
        return entityNms;
    }

    public ActionBarNms actionBar() {
        return actionBarNms;
    }

    public AnvilNms anvilGui(Player player, AnvilNms.AnvilClickEventHandler handler) {
        return anvilGui(player, handler, null);
    }

    public AnvilNms anvilGui(Player player, AnvilNms.AnvilClickEventHandler handler, AnvilNms.AnvilCloseEvent closeEvent) {
        switch (version) {
            case "v1_14_R1": // 1.14
                return new AnvilNms_1_14_R1(player, handler, closeEvent);
        }
        return null;
    }

    public ItemStackNms customItem() {
        return itemStackNms;
    }

    public ArmorStandNms armorStand() {
        return armorStandNms;
    }

    public BedNpcNms bedNpc() {
        return bedNpcNms;
    }

    public FireworkNms firework() {
        return fireworkNms;
    }

    public TabListNms tabList() {
        return tabListNms;
    }

    public WorldNms world() {
        return worldNms;
    }

    public BrigadierNms brigadier() {
        return brigadierNms;
    }
}
