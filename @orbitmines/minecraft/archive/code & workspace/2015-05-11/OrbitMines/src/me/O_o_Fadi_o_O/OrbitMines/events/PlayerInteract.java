package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.managers.InteractManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener{
	
	Start hub = Start.getInstance();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		ItemStack item = e.getItem();
		
		if(omp.isLoaded()){
			InteractManager manager = new InteractManager(e);
			
			if(omp.isAFK()){
				omp.noLongerAFK();
			}
			
			if(ServerData.isServer(Server.HUB)){
				if(p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
					manager.handleKickSign();
					manager.handleMindCraft();
				}
				
				if(item != null){
					if(!omp.isOpMode() && manager.handleRulesBook() && manager.handleGrapplingHook()){
						e.setCancelled(true);
					}
					
					// Lobby Inventory \\
					manager.handleCosmeticPerks();
					manager.handleSettings();
					manager.handleFly();
					manager.handleServerSelector();
					manager.handleAchievements();
					manager.handleRulesBook();
				}
			}
			
			if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
				if(item != null){
					// Pets \\
					if(omp.getPet() != null){
						manager.handleChickenPetEggBomb();
						manager.handleChickenPetAge();
						manager.handleCreeperPetType();
						manager.handleCreeperPetExplode();
						manager.handleOcelotPetColor();
						manager.handleOcelotPetKittyCannon();
						manager.handleHorsePetColor();
						manager.handleHorsePetSpeed();
						manager.handleSilverfishPetLeap();
						manager.handleSilverfishPetBomb();
						manager.handleSheepPetDisco();
						manager.handleSheepPetColor();
						manager.handleCowPetMilkExplosion();
						manager.handleCowPetAge();
						manager.handleWolfPetAge();
						manager.handleWolfPetBark();
						manager.handleSlimePetJump();
						manager.handleSlimePetSize();
						manager.handlePetPigBabies();
						manager.handlePetPigAge();
						manager.handleMagmaCubePetSize();
						manager.handleMagmaCubePetFireball();
						manager.handleMushroomCowPetShroomTrail();
						manager.handleMushroomCowPetFirework();
						manager.handleSpiderPetWebs();
						manager.handleSpiderPetLauncher();
						manager.handleSquidPetInkBomb();
						manager.handleSquidPetWaterSpout();
					}
					
					// Gadets \\
					manager.handleFlameThrower();
					manager.handleMagmaCubeSoccer();
					manager.handleSwapTeleporter();
					manager.handleCreeperLauncher();
					manager.handlePaintballs();
					manager.handleBookExplosion();
					manager.handleSnowGolemAttack();
					manager.handlePetRide();
					manager.handleStacker();
					manager.handleFireworkGun();
				}
			}			
		}
		else{
			omp.notLoaded();
		}
	}
}
