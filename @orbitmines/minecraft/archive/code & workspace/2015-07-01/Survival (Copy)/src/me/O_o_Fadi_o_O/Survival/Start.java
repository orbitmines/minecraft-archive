package me.O_o_Fadi_o_O.Survival;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.O_o_Fadi_o_O.Survival.NMS.CustomBlaze;
import me.O_o_Fadi_o_O.Survival.NMS.CustomCreeper;
import me.O_o_Fadi_o_O.Survival.NMS.CustomSkeleton;
import me.O_o_Fadi_o_O.Survival.NMS.CustomVillager;
import me.O_o_Fadi_o_O.Survival.NMS.PetChicken;
import me.O_o_Fadi_o_O.Survival.NMS.PetCow;
import me.O_o_Fadi_o_O.Survival.NMS.PetCreeper;
import me.O_o_Fadi_o_O.Survival.NMS.PetMagmaCube;
import me.O_o_Fadi_o_O.Survival.NMS.PetMushroomCow;
import me.O_o_Fadi_o_O.Survival.NMS.PetOcelot;
import me.O_o_Fadi_o_O.Survival.NMS.PetPig;
import me.O_o_Fadi_o_O.Survival.NMS.PetSheep;
import me.O_o_Fadi_o_O.Survival.NMS.PetSilverfish;
import me.O_o_Fadi_o_O.Survival.NMS.PetSlime;
import me.O_o_Fadi_o_O.Survival.NMS.PetSpider;
import me.O_o_Fadi_o_O.Survival.NMS.PetSquid;
import me.O_o_Fadi_o_O.Survival.NMS.PetWolf;
import me.O_o_Fadi_o_O.Survival.events.BreakEvent;
import me.O_o_Fadi_o_O.Survival.events.ClickEvent;
import me.O_o_Fadi_o_O.Survival.events.ClickEvent2;
import me.O_o_Fadi_o_O.Survival.events.CommandPreprocessEvent;
import me.O_o_Fadi_o_O.Survival.events.DamageEvent;
import me.O_o_Fadi_o_O.Survival.events.DespawnEvent;
import me.O_o_Fadi_o_O.Survival.events.EntityDamage;
import me.O_o_Fadi_o_O.Survival.events.EntityDeath;
import me.O_o_Fadi_o_O.Survival.events.EntityInteractEvent;
import me.O_o_Fadi_o_O.Survival.events.InteractAtEntityEvent;
import me.O_o_Fadi_o_O.Survival.events.JoinEvent;
import me.O_o_Fadi_o_O.Survival.events.PlaceEvent;
import me.O_o_Fadi_o_O.Survival.events.PlayerChat;
import me.O_o_Fadi_o_O.Survival.events.PlayerInteract;
import me.O_o_Fadi_o_O.Survival.events.PlayerMove;
import me.O_o_Fadi_o_O.Survival.events.QuitEvent;
import me.O_o_Fadi_o_O.Survival.events.SignEvent;
import me.O_o_Fadi_o_O.Survival.events.VoteEvent;
import me.O_o_Fadi_o_O.Survival.jobs.managers.JobManager;
import me.O_o_Fadi_o_O.Survival.jobs.managers.MerchantManager;
import me.O_o_Fadi_o_O.Survival.jobs.utils.JobMaterial;
import me.O_o_Fadi_o_O.Survival.managers.BungeeManager;
import me.O_o_Fadi_o_O.Survival.managers.ConfigManager;
import me.O_o_Fadi_o_O.Survival.managers.DatabaseManager;
import me.O_o_Fadi_o_O.Survival.managers.NPCManager;
import me.O_o_Fadi_o_O.Survival.managers.NetherPortalManager;
import me.O_o_Fadi_o_O.Survival.managers.PlayerManager;
import me.O_o_Fadi_o_O.Survival.managers.RegionManager;
import me.O_o_Fadi_o_O.Survival.managers.ScoreboardTitleManager;
import me.O_o_Fadi_o_O.Survival.managers.StorageManager;
import me.O_o_Fadi_o_O.Survival.managers.VoteManager;
import me.O_o_Fadi_o_O.Survival.runnables.DatabaseRunnable;
import me.O_o_Fadi_o_O.Survival.runnables.PetRunnable;
import me.O_o_Fadi_o_O.Survival.runnables.PlayerRunnable;
import me.O_o_Fadi_o_O.Survival.runnables.ScoreboardRunnable;
import me.O_o_Fadi_o_O.Survival.runnables.ServerSelectorRunnable;
import me.O_o_Fadi_o_O.Survival.utils.ArmorStandType;
import me.O_o_Fadi_o_O.Survival.utils.ChatColor;
import me.O_o_Fadi_o_O.Survival.utils.Disguise;
import me.O_o_Fadi_o_O.Survival.utils.Server;
import me.O_o_Fadi_o_O.Survival.utils.StaffRank;
import me.O_o_Fadi_o_O.Survival.utils.Trail;
import me.O_o_Fadi_o_O.Survival.utils.TrailType;
import me.O_o_Fadi_o_O.Survival.utils.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class Start extends JavaPlugin {
	
	@SuppressWarnings("deprecation")
	public void onEnable(){
		
			ItemStack zombieskull = new ItemStack(Material.SKULL_ITEM, 1);
			zombieskull.setDurability((short) 2);
			npcmanager.spawnArmorStand(
					StorageManager.lobbyworld, 
					new Location(StorageManager.lobbyworld, -3.5, 74.5, 22.5, -117, 0), 
					null,
					true, 
					true, 
					true, 
					EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0), 
					false, 
					EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0.5).setY(0).setZ(0), 
					false,
					zombieskull,
					new ItemStack(Material.GOLD_CHESTPLATE, 1),
					new ItemStack(Material.GOLD_LEGGINGS, 1),
					new ItemStack(Material.GOLD_BOOTS, 1),
					new ItemStack(Material.GOLD_SWORD, 1),
					ArmorStandType.NORMAL);
			npcmanager.spawnArmorStand(
					StorageManager.lobbyworld, 
					new Location(StorageManager.lobbyworld, 0.5, 74.5, 22.5, 117, 0), 
					null,
					true, 
					true, 
					true, 
					EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0), 
					false, 
					EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0), 
					false,
					zombieskull,
					new ItemStack(Material.GOLD_CHESTPLATE, 1),
					new ItemStack(Material.GOLD_LEGGINGS, 1),
					new ItemStack(Material.GOLD_BOOTS, 1),
					new ItemStack(Material.GOLD_SWORD, 1),
					ArmorStandType.NORMAL);
			npcmanager.spawnArmorStand(
					StorageManager.lobbyworld, 
					new Location(StorageManager.lobbyworld, -1.5, 74, 21.5, -180, 0), 
					"§2§lPvP Area",
					false, 
					true, 
					true, 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					false, 
					EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					false,
					zombieskull,
					new ItemStack(Material.DIAMOND_CHESTPLATE, 1),
					new ItemStack(Material.DIAMOND_LEGGINGS, 1),
					new ItemStack(Material.DIAMOND_BOOTS, 1),
					new ItemStack(Material.DIAMOND_SWORD, 1),
					ArmorStandType.PVP);
		}
	}
}
