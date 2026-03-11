package me.O_o_Fadi_o_O.OrbitMines.removed;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.TeleporterInv;
import me.O_o_Fadi_o_O.OrbitMines.removed.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.removed.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.MindCraftPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils.TicketType;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Lumberjack;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils.MineType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import sun.org.mozilla.javascript.internal.Kit;

import java.util.*;

import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

public class Runnables {

	public class MindCraftNPCRunnable extends BukkitRunnable {
		
		@SuppressWarnings("deprecation")
		@Override
		public void run(){//0, 10
			if(ServerData.isServer(Server.PRISON)){
				for(Lumberjack lj : ServerData.getPrison().getLumberjacks()){
					if(!lj.isWorking()){
						List<Block> takenblocks = lj.getTakenBlocks();
						
						if(takenblocks.size() > 0){
							lj.setWorking(true);
							lj.setWorkingOn(takenblocks.get(0));
							
							lj.getNPC().setItemInHand(new ItemStack(Material.LOG));
							lj.getToRemove().setType(Material.AIR);
						}
					}
					else{
						if(lj.getWorkingOn() != null){
							if(lj.getNPC().getEntity().getLocation().distance(lj.getWorkingOn().getLocation()) <= 1.75){
								lj.getNPC().setItemInHand(new ItemStack(Material.AIR));
								lj.getToRemove().setType(Material.LOG);
								
								lj.getWorkingOn().setType(Material.LOG);
								lj.getWorkingOn().setData((byte) 4);
								lj.getWorkingOn().getWorld().playEffect(lj.getWorkingOn().getLocation(), Effect.STEP_SOUND, 17);
								
								lj.setWorkingOn(null);
							}
						}
						else{
							lj.getNPC().setItemInHand(new ItemStack(Material.IRON_AXE));
							
							if(lj.getNPC().getEntity().getLocation().distance(lj.getLocation()) <= 1.25){
								lj.setWorking(false);
							}
						}
					}
				}
			}
		}
	}
}
