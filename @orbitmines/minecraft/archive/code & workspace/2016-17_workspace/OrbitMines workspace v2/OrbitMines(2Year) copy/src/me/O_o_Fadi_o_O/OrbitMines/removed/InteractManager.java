package me.O_o_Fadi_o_O.OrbitMines.removed;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;
import me.O_o_Fadi_o_O.OrbitMines.NMS.NoAttackPigZombie;
import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.CosmeticPerksInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.TeleporterInv;
import me.O_o_Fadi_o_O.OrbitMines.removed.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.removed.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.removed.ServerData.SkyBlockServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.BoosterInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.KitSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ItemType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ProjectileType;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.GameEffectsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.StatsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.SurvivalUtils.ShopType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class InteractManager {

	private PlayerInteractEvent e;
	private Player p;
	private OMPlayer omp;
	private KitPvPPlayer kp;
	private PrisonPlayer pp;
	private ItemStack item;
	private Block b;
	private Action a;
	
	public InteractManager(PlayerInteractEvent e){
		this.e = e;
		this.p = e.getPlayer();
		this.omp = OMPlayer.getOMPlayer(p);
		this.kp = omp.getKitPvPPlayer();
		this.pp = omp.getPrisonPlayer();
		this.item = e.getItem();
		this.b = e.getClickedBlock();
		this.a = e.getAction();
	}

	
	public void handleBackToHub(){
		if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nBack to the Hub")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(omp.getArena() != null){
				omp.getArena().leave(omp);
			}
		}
	}

	public void handleRegions(){
		if(e.getClickedBlock() != null && a != Action.RIGHT_CLICK_AIR && a != Action.LEFT_CLICK_AIR && p.getWorld().getName().equals(ServerData.getSurvival().getSurvivalWorld().getName()) && Region.isInRegion(omp, e.getClickedBlock().getLocation())){
			e.setCancelled(true);
			omp.updateInventory();
		}
	}
	
	public void handleMonsterEggs(){
		if(item != null && item.getType() == Material.MONSTER_EGG){
			e.setCancelled(true);
			omp.updateInventory();
		}
	}

	
	public void handleSnowball(){
		if(item.getType() == Material.SNOW_BALL){
			if(a == Action.RIGHT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
				e.setCancelled(true);
				omp.updateInventory();
			}
		}
	}
	
	public void handleBackToLobby(){
		if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nBack to the Lobby")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(kp.isSpectator()){
				KitPvPServer kitpvp = ServerData.getKitPvP();
				
				kp.setPlayer();
				p.teleport(kitpvp.getSpawn());
				omp.clearInventory();
				kitpvp.giveLobbyItems(omp);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
				p.setFlying(false);
				p.setAllowFlight(false);
				
				omp.show();
			}
		}
	}
	
	public void handleKitSelector(){
		if(item.getType() == Material.DIAMOND_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§b§nKit Selector")){
			e.setCancelled(true);
			omp.updateInventory();
			
			new KitSelectorInv().open(p);
		}
	}
	
	public void handlePhysicalAction(){
		if(!omp.isOpMode()){
			if(a == Action.PHYSICAL && (b == null || b.getType() != Material.STONE_PLATE && b.getType() != Material.WOOD_PLATE)){
				e.setCancelled(true);
			}
		}
	}



	public void handleCosmeticPerks(){
		if(item.getType() == Material.ENDER_CHEST && item.getItemMeta().getDisplayName().equals("§9§nCosmetic Perks")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!ServerData.isServer(Server.HUB) || !omp.isInLapisParkour()){
				new CosmeticPerksInv().open(p);
			}
		}
	}

	public boolean handleWrittenBook(){
		if(item.getType() != Material.WRITTEN_BOOK){
			return true;
		}
		return false;
	}

	public void handleAchievements(){
		if(item.getType() == Material.EXP_BOTTLE && item.getItemMeta().getDisplayName().equals("§d§nAchievements")){
			e.setCancelled(true);
			omp.updateInventory();

			p.sendMessage("§a§oComing Soon...");
			// TODO ADD ACHIEVEMENTS \\
		}
	}
}
