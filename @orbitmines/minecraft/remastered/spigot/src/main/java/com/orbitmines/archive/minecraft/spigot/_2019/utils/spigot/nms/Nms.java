package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms;

import org.bukkit.Bukkit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ConsoleUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.actionbar.ActionBarNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.actionbar.ActionBarNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.armorstand.ArmorStandNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.armorstand.ArmorStandNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.bednpc.BedNpcNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.bednpc.BedNpcNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.brigadier.BrigadierNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.brigadier.BrigadierNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.firework.FireworkNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.firework.FireworkNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.NpcNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.NpcNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.allay.AllayNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.allay.AllayNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.armadillo.ArmadilloNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.armadillo.ArmadilloNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.axolotl.AxolotlNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.axolotl.AxolotlNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.bat.BatNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.bat.BatNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.bee.BeeNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.bee.BeeNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.blaze.BlazeNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.blaze.BlazeNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.bogged.BoggedNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.bogged.BoggedNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.breeze.BreezeNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.breeze.BreezeNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.camel.CamelNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.camel.CamelNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.camel_husk.CamelHuskNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.camel_husk.CamelHuskNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cat.CatNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cat.CatNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cave_spider.CaveSpiderNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cave_spider.CaveSpiderNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.chicken.ChickenNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.chicken.ChickenNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cod.CodNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cod.CodNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.copper_golem.CopperGolemNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.copper_golem.CopperGolemNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cow.CowNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.cow.CowNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.creaking.CreakingNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.creaking.CreakingNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.creeper.CreeperNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.creeper.CreeperNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.dolphin.DolphinNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.dolphin.DolphinNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.donkey.DonkeyNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.donkey.DonkeyNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.drowned.DrownedNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.drowned.DrownedNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.elder_guardian.ElderGuardianNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.elder_guardian.ElderGuardianNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ender_dragon.EnderDragonNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ender_dragon.EnderDragonNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.enderman.EndermanNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.enderman.EndermanNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.endermite.EndermiteNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.endermite.EndermiteNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.evoker.EvokerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.evoker.EvokerNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.fox.FoxNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.fox.FoxNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.frog.FrogNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.frog.FrogNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ghast.GhastNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ghast.GhastNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.giant.GiantNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.giant.GiantNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.glow_squid.GlowSquidNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.glow_squid.GlowSquidNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.goat.GoatNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.goat.GoatNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.guardian.GuardianNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.guardian.GuardianNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.happy_ghast.HappyGhastNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.happy_ghast.HappyGhastNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.hoglin.HoglinNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.hoglin.HoglinNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.horse.HorseNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.horse.HorseNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.husk.HuskNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.husk.HuskNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.illusioner.IllusionerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.illusioner.IllusionerNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.iron_golem.IronGolemNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.iron_golem.IronGolemNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.llama.LlamaNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.llama.LlamaNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.magma_cube.MagmaCubeNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.magma_cube.MagmaCubeNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.mule.MuleNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.mule.MuleNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.mushroom_cow.MushroomCowNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.mushroom_cow.MushroomCowNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.nautilus.NautilusNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.nautilus.NautilusNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ocelot.OcelotNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ocelot.OcelotNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.panda.PandaNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.panda.PandaNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.parched.ParchedNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.parched.ParchedNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.parrot.ParrotNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.parrot.ParrotNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.phantom.PhantomNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.phantom.PhantomNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pig.PigNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pig.PigNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pig_zombie.PigZombieNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pig_zombie.PigZombieNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.piglin.PiglinNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.piglin.PiglinNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.piglin_brute.PiglinBruteNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.piglin_brute.PiglinBruteNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pillager.PillagerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pillager.PillagerNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.polar_bear.PolarBearNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.polar_bear.PolarBearNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pufferfish.PufferFishNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.pufferfish.PufferFishNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.rabbit.RabbitNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.rabbit.RabbitNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ravager.RavagerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.ravager.RavagerNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.salmon.SalmonNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.salmon.SalmonNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.sheep.SheepNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.sheep.SheepNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.shulker.ShulkerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.shulker.ShulkerNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.silverfish.SilverfishNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.silverfish.SilverfishNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.skeleton.SkeletonNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.skeleton.SkeletonNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.skeleton_horse.SkeletonHorseNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.skeleton_horse.SkeletonHorseNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.slime.SlimeNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.slime.SlimeNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.sniffer.SnifferNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.sniffer.SnifferNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.snowman.SnowmanNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.snowman.SnowmanNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.spider.SpiderNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.spider.SpiderNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.squid.SquidNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.squid.SquidNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.stray.StrayNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.stray.StrayNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.strider.StriderNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.strider.StriderNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.tadpole.TadpoleNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.tadpole.TadpoleNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.trader_llama.TraderLlamaNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.trader_llama.TraderLlamaNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.tropical_fish.TropicalFishNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.tropical_fish.TropicalFishNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.turtle.TurtleNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.turtle.TurtleNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.vex.VexNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.vex.VexNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.villager.VillagerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.villager.VillagerNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.vindicator.VindicatorNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.vindicator.VindicatorNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wandering_trader.WanderingTraderNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wandering_trader.WanderingTraderNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.warden.WardenNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.warden.WardenNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.witch.WitchNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.witch.WitchNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wither.WitherNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wither.WitherNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wither_skeleton.WitherSkeletonNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wither_skeleton.WitherSkeletonNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wolf.WolfNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.wolf.WolfNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zoglin.ZoglinNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zoglin.ZoglinNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie.ZombieNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie.ZombieNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_horse.ZombieHorseNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_horse.ZombieHorseNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_nautilus.ZombieNautilusNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_nautilus.ZombieNautilusNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_villager.ZombieVillagerNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_villager.ZombieVillagerNpc_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.tablist.TabListNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.tablist.TabListNms_26_1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.world.WorldNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.world.WorldNms_26_1;
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

    @Getter private AllayNpc allayNpc;
    @Getter private ArmadilloNpc armadilloNpc;
    @Getter private AxolotlNpc axolotlNpc;
    @Getter private BatNpc batNpc;
    @Getter private BeeNpc beeNpc;
    @Getter private BlazeNpc blazeNpc;
    @Getter private BoggedNpc boggedNpc;
    @Getter private BreezeNpc breezeNpc;
    @Getter private CamelNpc camelNpc;
    @Getter private CamelHuskNpc camelHuskNpc;
    @Getter private CatNpc catNpc;
    @Getter private CaveSpiderNpc caveSpiderNpc;
    @Getter private ChickenNpc chickenNpc;
    @Getter private CodNpc codNpc;
    @Getter private CopperGolemNpc copperGolemNpc;
    @Getter private CowNpc cowNpc;
    @Getter private CreakingNpc creakingNpc;
    @Getter private CreeperNpc creeperNpc;
    @Getter private DolphinNpc dolphinNpc;
    @Getter private DonkeyNpc donkeyNpc;
    @Getter private DrownedNpc drownedNpc;
    @Getter private ElderGuardianNpc elderGuardianNpc;
    @Getter private EnderDragonNpc enderDragonNpc;
    @Getter private EndermanNpc endermanNpc;
    @Getter private EndermiteNpc endermiteNpc;
    @Getter private EvokerNpc evokerNpc;
    @Getter private FoxNpc foxNpc;
    @Getter private FrogNpc frogNpc;
    @Getter private GhastNpc ghastNpc;
    @Getter private GiantNpc giantNpc;
    @Getter private GlowSquidNpc glowSquidNpc;
    @Getter private GoatNpc goatNpc;
    @Getter private GuardianNpc guardianNpc;
    @Getter private HappyGhastNpc happyGhastNpc;
    @Getter private HoglinNpc hoglinNpc;
    @Getter private HorseNpc horseNpc;
    @Getter private HuskNpc huskNpc;
    @Getter private IllusionerNpc illusionerNpc;
    @Getter private IronGolemNpc ironGolemNpc;
    @Getter private LlamaNpc llamaNpc;
    @Getter private MagmaCubeNpc magmaCubeNpc;
    @Getter private MuleNpc muleNpc;
    @Getter private MushroomCowNpc mushroomCowNpc;
    @Getter private NautilusNpc nautilusNpc;
    @Getter private OcelotNpc ocelotNpc;
    @Getter private PandaNpc pandaNpc;
    @Getter private ParchedNpc parchedNpc;
    @Getter private ParrotNpc parrotNpc;
    @Getter private PhantomNpc phantomNpc;
    @Getter private PigNpc pigNpc;
    @Getter private PigZombieNpc pigZombieNpc;
    @Getter private PiglinNpc piglinNpc;
    @Getter private PiglinBruteNpc piglinBruteNpc;
    @Getter private PillagerNpc pillagerNpc;
    @Getter private PolarBearNpc polarBearNpc;
    @Getter private PufferFishNpc pufferFishNpc;
    @Getter private RabbitNpc rabbitNpc;
    @Getter private RavagerNpc ravagerNpc;
    @Getter private SalmonNpc salmonNpc;
    @Getter private SheepNpc sheepNpc;
    @Getter private ShulkerNpc shulkerNpc;
    @Getter private SilverfishNpc silverfishNpc;
    @Getter private SkeletonNpc skeletonNpc;
    @Getter private SkeletonHorseNpc skeletonHorseNpc;
    @Getter private SlimeNpc slimeNpc;
    @Getter private SnifferNpc snifferNpc;
    @Getter private SnowmanNpc snowmanNpc;
    @Getter private SpiderNpc spiderNpc;
    @Getter private SquidNpc squidNpc;
    @Getter private StrayNpc strayNpc;
    @Getter private StriderNpc striderNpc;
    @Getter private TadpoleNpc tadpoleNpc;
    @Getter private TraderLlamaNpc traderLlamaNpc;
    @Getter private TropicalFishNpc tropicalFishNpc;
    @Getter private TurtleNpc turtleNpc;
    @Getter private VexNpc vexNpc;
    @Getter private VillagerNpc villagerNpc;
    @Getter private VindicatorNpc vindicatorNpc;
    @Getter private WanderingTraderNpc wanderingTraderNpc;
    @Getter private WardenNpc wardenNpc;
    @Getter private WitchNpc witchNpc;
    @Getter private WitherNpc witherNpc;
    @Getter private WitherSkeletonNpc witherSkeletonNpc;
    @Getter private WolfNpc wolfNpc;
    @Getter private ZoglinNpc zoglinNpc;
    @Getter private ZombieNpc zombieNpc;
    @Getter private ZombieHorseNpc zombieHorseNpc;
    @Getter private ZombieNautilusNpc zombieNautilusNpc;
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
            Bukkit.getPluginManager().disablePlugin(server.getPlugin());
        }
    }

    //TODO, Only initialize classes when called through method, so we are not creating useless objects in memory
    private String checkVersion(SpigotServer server) {
        /* NPCs */
        npcNms = new NpcNms_26_1();

        allayNpc = new AllayNpc_26_1(npcNms);
        armadilloNpc = new ArmadilloNpc_26_1(npcNms);
        axolotlNpc = new AxolotlNpc_26_1(npcNms);
        batNpc = new BatNpc_26_1(npcNms);
        beeNpc = new BeeNpc_26_1(npcNms);
        blazeNpc = new BlazeNpc_26_1(npcNms);
        boggedNpc = new BoggedNpc_26_1(npcNms);
        breezeNpc = new BreezeNpc_26_1(npcNms);
        camelNpc = new CamelNpc_26_1(npcNms);
        camelHuskNpc = new CamelHuskNpc_26_1(npcNms);
        catNpc = new CatNpc_26_1(npcNms);
        caveSpiderNpc = new CaveSpiderNpc_26_1(npcNms);
        chickenNpc = new ChickenNpc_26_1(npcNms);
        codNpc = new CodNpc_26_1(npcNms);
        copperGolemNpc = new CopperGolemNpc_26_1(npcNms);
        cowNpc = new CowNpc_26_1(npcNms);
        creakingNpc = new CreakingNpc_26_1(npcNms);
        creeperNpc = new CreeperNpc_26_1(npcNms);
        dolphinNpc = new DolphinNpc_26_1(npcNms);
        donkeyNpc = new DonkeyNpc_26_1(npcNms);
        drownedNpc = new DrownedNpc_26_1(npcNms);
        elderGuardianNpc = new ElderGuardianNpc_26_1(npcNms);
        enderDragonNpc = new EnderDragonNpc_26_1(npcNms);
        endermanNpc = new EndermanNpc_26_1(npcNms);
        endermiteNpc = new EndermiteNpc_26_1(npcNms);
        evokerNpc = new EvokerNpc_26_1(npcNms);
        foxNpc = new FoxNpc_26_1(npcNms);
        frogNpc = new FrogNpc_26_1(npcNms);
        ghastNpc = new GhastNpc_26_1(npcNms);
        giantNpc = new GiantNpc_26_1(npcNms);
        glowSquidNpc = new GlowSquidNpc_26_1(npcNms);
        goatNpc = new GoatNpc_26_1(npcNms);
        guardianNpc = new GuardianNpc_26_1(npcNms);
        happyGhastNpc = new HappyGhastNpc_26_1(npcNms);
        hoglinNpc = new HoglinNpc_26_1(npcNms);
        horseNpc = new HorseNpc_26_1(npcNms);
        huskNpc = new HuskNpc_26_1(npcNms);
        illusionerNpc = new IllusionerNpc_26_1(npcNms);
        ironGolemNpc = new IronGolemNpc_26_1(npcNms);
        llamaNpc = new LlamaNpc_26_1(npcNms);
        magmaCubeNpc = new MagmaCubeNpc_26_1(npcNms);
        muleNpc = new MuleNpc_26_1(npcNms);
        mushroomCowNpc = new MushroomCowNpc_26_1(npcNms);
        nautilusNpc = new NautilusNpc_26_1(npcNms);
        ocelotNpc = new OcelotNpc_26_1(npcNms);
        pandaNpc = new PandaNpc_26_1(npcNms);
        parchedNpc = new ParchedNpc_26_1(npcNms);
        parrotNpc = new ParrotNpc_26_1(npcNms);
        phantomNpc = new PhantomNpc_26_1(npcNms);
        pigNpc = new PigNpc_26_1(npcNms);
        pigZombieNpc = new PigZombieNpc_26_1(npcNms);
        piglinNpc = new PiglinNpc_26_1(npcNms);
        piglinBruteNpc = new PiglinBruteNpc_26_1(npcNms);
        pillagerNpc = new PillagerNpc_26_1(npcNms);
        polarBearNpc = new PolarBearNpc_26_1(npcNms);
        pufferFishNpc = new PufferFishNpc_26_1(npcNms);
        rabbitNpc = new RabbitNpc_26_1(npcNms);
        ravagerNpc = new RavagerNpc_26_1(npcNms);
        salmonNpc = new SalmonNpc_26_1(npcNms);
        sheepNpc = new SheepNpc_26_1(npcNms);
        shulkerNpc = new ShulkerNpc_26_1(npcNms);
        silverfishNpc = new SilverfishNpc_26_1(npcNms);
        skeletonNpc = new SkeletonNpc_26_1(npcNms);
        skeletonHorseNpc = new SkeletonHorseNpc_26_1(npcNms);
        slimeNpc = new SlimeNpc_26_1(npcNms);
        snifferNpc = new SnifferNpc_26_1(npcNms);
        snowmanNpc = new SnowmanNpc_26_1(npcNms);
        spiderNpc = new SpiderNpc_26_1(npcNms);
        squidNpc = new SquidNpc_26_1(npcNms);
        strayNpc = new StrayNpc_26_1(npcNms);
        striderNpc = new StriderNpc_26_1(npcNms);
        tadpoleNpc = new TadpoleNpc_26_1(npcNms);
        traderLlamaNpc = new TraderLlamaNpc_26_1(npcNms);
        tropicalFishNpc = new TropicalFishNpc_26_1(npcNms);
        turtleNpc = new TurtleNpc_26_1(npcNms);
        vexNpc = new VexNpc_26_1(npcNms);
        villagerNpc = new VillagerNpc_26_1(npcNms);
        vindicatorNpc = new VindicatorNpc_26_1(npcNms);
        wanderingTraderNpc = new WanderingTraderNpc_26_1(npcNms);
        wardenNpc = new WardenNpc_26_1(npcNms);
        witchNpc = new WitchNpc_26_1(npcNms);
        witherNpc = new WitherNpc_26_1(npcNms);
        witherSkeletonNpc = new WitherSkeletonNpc_26_1(npcNms);
        wolfNpc = new WolfNpc_26_1(npcNms);
        zoglinNpc = new ZoglinNpc_26_1(npcNms);
        zombieNpc = new ZombieNpc_26_1(npcNms);
        zombieHorseNpc = new ZombieHorseNpc_26_1(npcNms);
        zombieNautilusNpc = new ZombieNautilusNpc_26_1(npcNms);
        zombieVillagerNpc = new ZombieVillagerNpc_26_1(npcNms);

        /* Entity */
        entityNms = new EntityNms_26_1();
        /* ActionBar */
        actionBarNms = new ActionBarNms_26_1();
        /* ItemStack */
        itemStackNms = new ItemStackNms_26_1();
        /* ArmorStand */
        armorStandNms = new ArmorStandNms_26_1();
        /* BedNpc */
        bedNpcNms = new BedNpcNms_26_1(server);
        /* Firework */
        fireworkNms = new FireworkNms_26_1(server);
        /* TabList */
        tabListNms = new TabListNms_26_1();
        /* World */
        worldNms = new WorldNms_26_1();
        /* Brigadier */
        brigadierNms = new BrigadierNms_26_1();

        return "26_1";
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
        return new AnvilNms_26_1(player, handler, closeEvent);
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
