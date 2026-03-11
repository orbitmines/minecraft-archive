package me.O_o_Fadi_o_O.OrbitMines.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Title;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ChatColorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.CosmeticPerksInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.DisguiseInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.FireworkInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.GadgetInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.HatInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.PetInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.TrailInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.WardrobeInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.ComponentMessage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.StaffRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


public class CommandManager {

	public static enum Command {
		
		UUID,
		PLUGINS,
		PL,
		BUILDER,
		SERVERS,
		PERKS,
		OPMODE,
		TOPVOTERS,
		AFK,
		NICK,
		DISGUISE,
		DIS,
		D,
		UNDISGUISE,
		UNDIS,
		UND,
		GIVE,
		TP,
		TELEPORT,
		SKULL,
		EAT,
		FEED,
		FLY,
		GMS,
		GMC,
		GMA,
		GMSPEC,
		GAMEMODE,
		GM,
		VOTE,
		PETS,
		CHATCOLORS,
		DISGUISES,
		GADGETS,
		WARDROBE,
		TRAILS,
		HATS,
		FIREWORKS,
		SILENT;
		
		public String getName(){
			return "/" + this.toString().toLowerCase();
		}
		
		public void dispatch(OMPlayer omp, String[] a){
			switch(this){
				case AFK:
					dispatchAFK(omp, a);
					break;
				case BUILDER:
					dispatchBuilder(omp, a);
					break;
				case D:
					dispatchDisguise(omp, a);
					break;
				case DIS:
					dispatchDisguise(omp, a);
					break;
				case DISGUISE:
					dispatchDisguise(omp, a);
					break;
				case EAT:
					dispatchFeed(omp, a);
					break;
				case FEED:
					dispatchFeed(omp, a);
					break;
				case FLY:
					dispatchFly(omp, a);
					break;
				case GAMEMODE:
					dispatchGM(omp, a);
					break;
				case GIVE:
					dispatchGive(omp, a);
					break;
				case GM:
					dispatchGM(omp, a);
					break;
				case GMA:
					dispatchGMA(omp, a);
					break;
				case GMC:
					dispatchGMC(omp, a);
					break;
				case GMS:
					dispatchGMS(omp, a);
					break;
				case GMSPEC:
					dispatchGMSpec(omp, a);
					break;
				case NICK:
					dispatchNick(omp, a);
					break;
				case OPMODE:
					dispatchOPMode(omp, a);
					break;
				case PERKS:
					dispatchPerks(omp, a);
					break;
				case PL:
					dispatchPlugins(omp, a);
					break;
				case PLUGINS:
					dispatchPlugins(omp, a);
					break;
				case SERVERS:
					dispatchServers(omp, a);
					break;
				case SKULL:
					dispatchSkull(omp, a);
					break;
				case TELEPORT:
					dispatchTeleport(omp, a);
					break;
				case TOPVOTERS:
					dispatchTopVoters(omp, a);
					break;
				case TP:
					dispatchTeleport(omp, a);
					break;
				case UND:
					dispatchUndisguise(omp, a);
					break;
				case UNDIS:
					dispatchUndisguise(omp, a);
					break;
				case UNDISGUISE:
					dispatchUndisguise(omp, a);
					break;
				case UUID:
					dispatchUUID(omp, a);
					break;
				case VOTE:
					dispatchVote(omp, a);
					break;
				case CHATCOLORS:
					dispatchChatColors(omp, a);
					break;
				case DISGUISES:
					dispatchDisguises(omp, a);
					break;
				case FIREWORKS:
					dispatchFireworks(omp, a);
					break;
				case GADGETS:
					dispatchGadgets(omp, a);
					break;
				case HATS:
					dispatchHats(omp, a);
					break;
				case PETS:
					dispatchPets(omp, a);
					break;
				case TRAILS:
					dispatchTrails(omp, a);
					break;
				case WARDROBE:
					dispatchWardrobe(omp, a);
				case SILENT:
					dispatchSilent(omp, a);
					break;
				default:
					break;
			}
		}

		private void dispatchSilent(OMPlayer omp, String[] a){
			omp.setSilentMode(!omp.isSilentMode());
		}
		
		private void dispatchChatColors(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new ChatColorInv().open(p);
		}
		
		private void dispatchDisguises(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new DisguiseInv().open(p);
		}
		
		private void dispatchFireworks(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new FireworkInv().open(p);
		}
		
		private void dispatchGadgets(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new GadgetInv().open(p);
		}
		
		private void dispatchHats(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new HatInv().open(p);
		}
		
		private void dispatchPets(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new PetInv().open(p);
		}
		
		private void dispatchTrails(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new TrailInv().open(p);
		}
		
		private void dispatchWardrobe(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new WardrobeInv().open(p);
		}
		
		private void dispatchAFK(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			if(omp.hasPerms(VIPRank.Iron_VIP)){
				if(omp.isAFK()){
					omp.noLongerAFK();
	    		}
	    		else{
		    		if(a.length == 1){
		    			omp.nowAFK(null);
		    		}
		    		else if(a.length == 2){
			    		if(omp.hasPerms(VIPRank.Diamond_VIP)){
				    		if(a[1].length() <= 20){
				    			String afkmessage = a[1];
				    			if(omp.hasPerms(VIPRank.Emerald_VIP)){
					    			afkmessage = a[1].replaceAll("&a", "§a").replaceAll("&b", "§b").replaceAll("&c", "§c").replaceAll("&d", "§d").replaceAll("&e", "§e").replaceAll("&f", "§f").replaceAll("&0", "§0").replaceAll("&1", "§1").replaceAll("&2", "§2").replaceAll("&3", "§3").replaceAll("&4", "§4").replaceAll("&5", "§5").replaceAll("&6", "§6").replaceAll("&7", "§7").replaceAll("&8", "§8").replaceAll("&9", "§9");
				    			}
				    			omp.nowAFK(afkmessage);
				    		}
				    		else{
				    			p.sendMessage("§7Your §6afk text§7 can't be longer than §620 characters§7!");
				    		}
			    		}
			    		else{
			    			p.sendMessage("§7You have to be a §9§lDiamond VIP§7 to use §6" + a[0].toLowerCase() + " <reason>§7.");
			    		}
			    	}
			    	else{
			    		p.sendMessage("§7Invalid Usage. (§6/afk §7or §6/afk <reason>§7)");
			    	}
	    		}
			}
			else{
    			p.sendMessage("§7You have to be an §7§lIron VIP§7 to use §6" + a[0].toLowerCase() + "§7.");
			}
		}
		
		private void dispatchBuilder(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(ServerData.isServer(Server.HUB) && omp.hasPerms(StaffRank.Builder)){
	    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + p.getName() + " BuilderWorld");
	    		p.sendMessage("§7Teleported to the §d§lBuilder World§7.");
	    		Title t = new Title("", "§d§lBuilder World");
	    		t.send(p);
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		@SuppressWarnings("deprecation")
		private void dispatchDisguise(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();

			if(omp.hasPerms(StaffRank.Owner)){
				if(a.length == 2){
		    		if(a[1].equalsIgnoreCase("block")){
		    			p.sendMessage("§7Invalid Usage. (§6/disguise block <id>§7)");
		    		}
		    		else{
			    		try{
			    			EntityType type = EntityType.valueOf(a[1].toUpperCase());
			    			omp.disguiseAsMob(type, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
			    			p.sendMessage("§7Disguised as: §6" + a[1].toUpperCase() + "§7.");
			    		}
			    		catch(IllegalArgumentException ex){
			    			p.sendMessage("§7Invalid Disguise.");
			    		}
		    		}
		    	}
		    	else if(a.length == 3){
		    		if(a[1].equalsIgnoreCase("block")){
		    			try{
		    				int id = Integer.parseInt(a[2]);
		    				omp.disguiseAsBlock(id, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
		    				p.sendMessage("§7Disguised as: §6" + Material.getMaterial(id).toString() + "§7.");
		    			}
		    			catch(IllegalArgumentException ex){
		    				p.sendMessage("§6" + a[2] + "§7 isn't a number.");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Invalid Disguise.");
		    		}
		    	}
		    	else if(a.length == 4){
		    		if(a[1].equalsIgnoreCase("player")){
		    			Player p2 = Utils.getPlayer(a[2]);
		    			OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    			
		    			if(p2 != null){
		    				try{
				    			EntityType type = EntityType.valueOf(a[3].toUpperCase());
				    			omp2.disguiseAsMob(type, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
				    			p.sendMessage("§7Disguised " + omp2.getName() + " §7as: §6" + a[3].toUpperCase() + "§7.");
				    			p2.sendMessage("§7Disguised as: §6" + a[3].toUpperCase() + "§7.");
				    		}
				    		catch(IllegalArgumentException ex){
				    			p.sendMessage("§7Invalid Disguise.");
				    		}
		    			}
		    			else{
		    				p.sendMessage("§7Player §6" + a[2] + " §7isn't §aOnline§7!");
		    			}
		    		}
		    		else if(a[1].equalsIgnoreCase("near")){
		    			try{
		    				int radius = Integer.parseInt(a[2]);
		    				
		    				try{
				    			EntityType type = EntityType.valueOf(a[3].toUpperCase());
				    			int amount = 1;
				    			
				    			for(Entity en : p.getNearbyEntities(radius, radius, radius)){
				    				if(en instanceof Player){
				    					amount++;
				    					Player player = (Player) en;
				    					OMPlayer omplayer = OMPlayer.getOMPlayer(player);
						    			omplayer.disguiseAsMob(type, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
				    					player.sendMessage("§7Disguised as: §6" + a[3].toUpperCase() + "§7.");
				    				}
				    			}

				    			omp.disguiseAsMob(type, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
				    			p.sendMessage("§7Disguised near players (§6" + amount + "§7) §7as: §6" + a[3].toUpperCase() + "§7.");
				    			p.sendMessage("§7Disguised as: §6" + a[3].toUpperCase() + "§7.");
				    			
				    		}
				    		catch(IllegalArgumentException ex){
				    			p.sendMessage("§7Invalid Disguise.");
				    		}
		    			}
		    			catch(NumberFormatException ex){
			    			p.sendMessage("§7Invalid Radius.");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Invalid Disguise.");
		    		}
		    	}
		    	else if(a.length == 5){
		    		if(a[1].equalsIgnoreCase("player")){
		    			Player p2 = Utils.getPlayer(a[2]);
		    			OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    			
		    			if(p2 != null){
		    				if(a[3].equalsIgnoreCase("block")){
				    			try{
				    				int id = Integer.parseInt(a[4]);

				    				omp2.disguiseAsBlock(id, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
				    				p.sendMessage("§7Disguised " + omp2.getName() + " §7as: §6Block§7. (§6" + Material.getMaterial(id).toString() + "§7)");
				    				p2.sendMessage("§7Disguised as: §aBlock§7. (§6" + Material.getMaterial(id).toString() + "§7)");
				    			}
				    			catch(NumberFormatException ex){
					    			p.sendMessage("§7Invalid ID.");
				    			}
				    		}
				    		else{
				    			p.sendMessage("§7Invalid Disguise.");
				    		}
		    			}
		    			else{
		    				p.sendMessage("§7Player §6" + a[2] + " §7isn't §aOnline§7!");
		    			}
		    		}
		    		else if(a[1].equalsIgnoreCase("near")){
		    			try{
		    				int radius = Integer.parseInt(a[2]);
		    				
		    				if(a[3].equalsIgnoreCase("block")){
			    				try{
			    					int id = Integer.parseInt(a[4]);
					    			
					    			int amount = 1;
					    			
					    			for(Entity en : p.getNearbyEntities(radius, radius, radius)){
					    				if(en instanceof Player){
					    					amount++;
					    					Player player = (Player) en;
					    					OMPlayer omplayer = OMPlayer.getOMPlayer(player);
					    					omplayer.disguiseAsBlock(id, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
					    					player.sendMessage("§7Disguised as: §aBlock§7. (§a" + Material.getMaterial(id).toString() + "§7)");
					    				}
					    			}

			    					omp.disguiseAsBlock(id, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
					    			p.sendMessage("§7Disguised near players (§a" + amount + "§7) §7as: §aBlock§7. (§a" + Material.getMaterial(id).toString() + "§7)");
					    			p.sendMessage("§7Disguised as: §aBlock§7. (§a" + Material.getMaterial(id).toString() + "§7)");
					    
					    		}
					    		catch(NumberFormatException ex){
					    			p.sendMessage("§7Invalid ID.");
					    		}
		    				}
		    				else{
				    			p.sendMessage("§7Invalid Disguise.");
		    				}
		    			}
		    			catch(NumberFormatException ex){
			    			p.sendMessage("§7Invalid Radius.");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Invalid Disguise.");
		    		}
		    	}
		    	else{
		    		p.sendMessage("§7§lMobs");
		    		p.sendMessage(" §6/d (player | <player>) <mob>§7): ");
		    		p.sendMessage(" §7§lAvailable§7: §6Armor_Stand§7, §6Bat§7, §6Blaze§7, §6Cave_Spider§7, §6Chicken§7, §6Cow§7, §6Creeper§7, §6Enderman§7, §6Enderman§7, §6Endermite§7, §6Ender_Dragon§7, §6Ghast§7, §6Giant§7, §6Guardian§7, §6Horse§7, §6Iron_Golem§7, §6Magma_Cube§7, §6Mushroom_Cow§7, §6Ocelot§7, §6Pig§7, §6Pig_Zombie§7, §6Rabbit§7, §6Sheep§7, §6Silverfish§7, §6Skeleton§7, §6Slime§7, §6Snowman§7, §6Spider§7, §6Squid§7, §6Villager§7, §6Witch§7, §6Wither§7, §6Wolf§7, §6Zombie");
		    		p.sendMessage("§7§lBlocks");
		    		p.sendMessage(" §6/d (player | <player>) block <id>");
		    		p.sendMessage("§7§lDisguise near to Mob");
		    		p.sendMessage(" §6/d near <radius> <mob>");
		    		p.sendMessage("§7§lDisguise near to Block");
		    		p.sendMessage(" §6/d near <radius> block <id>");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchFeed(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();

			if(omp.hasPerms(StaffRank.Owner)){
				if(a.length == 2){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    		
		    		if(p2 != null){
		    			if(p2 == p){
		    				p.setFoodLevel(20);
		    				p.sendMessage("§7Restored your §6Hungerbar§7!");
		    			}
		    			else{
		    				p.sendMessage("§7Restored " + omp2.getName() + "'s §6Hungerbar§7!");
		    				p2.sendMessage("§7" + omp.getName() + "§7 restored your §6Hungerbar§7!");
		    				p2.setFoodLevel(20);
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
				else{
		    		p.setFoodLevel(20);
		    		p.sendMessage("§7Restored your §6Hungerbar§7!");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}

		private void dispatchFly(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Moderator)){
				if(a.length == 2){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    		
		    		if(p2 != null){
		    			if(p2 == p){
				    		p.setAllowFlight(!p.getAllowFlight());
				    		p.setFlying(p.getAllowFlight());
				    		p.sendMessage(Utils.statusString(p.getAllowFlight()) + " §7your §fFly§7 mode!");
		    			}
		    			else{
				    		p2.setAllowFlight(!p2.getAllowFlight());
				    		p2.setFlying(p2.getAllowFlight());
				    		p.sendMessage(Utils.statusString(p2.getAllowFlight()) + " " + omp2.getName() + "'s §fFly§7 mode!");
				    		p2.sendMessage("§7" + omp.getName() + " " + Utils.statusString(p2.getAllowFlight()) + " §7your §fFly§7 mode!");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
		    	else{
		    		p.setAllowFlight(!p.getAllowFlight());
		    		p.setFlying(p.getAllowFlight());
		    		p.sendMessage(Utils.statusString(p.getAllowFlight()) + " §7your §fFly§7 mode!");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		@SuppressWarnings("deprecation")
		private void dispatchGive(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			if(omp.hasPerms(StaffRank.Owner)){
				if(a.length == 3 || a.length == 4){
	    			try{
	    				int amount = 64;
	    				if(a.length == 4){
	    					amount = Integer.parseInt(a[3]);
	    				}
	    				
			    		Player p2 = Utils.getPlayer(a[1]);
			    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
			    		
			    		if(p2 != null){
			    			if(a[2].contains(":")){
			    				String[] itemstrings = a[2].split("\\:");
			    				
			    				try{
			    					int durability = Integer.parseInt(itemstrings[1]);
			    					
			    					try{
				    					int id = Integer.parseInt(itemstrings[0]);
				    					
				    					if(p2 == p){
				    						try{
					    						ItemStack item = new ItemStack(Material.getMaterial(id), amount);
					    						item.setDurability((short) durability);
					    						p.getInventory().addItem(item);
					    						p.updateInventory();
					    						
							    				p.sendMessage("§7You gave yourself §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
				    						}catch(IllegalArgumentException ex){
				    							p.sendMessage("§7There's no §6Item§7 with the ID §6" + id + "§7!");
				    						}
				    					}
				    					else{
				    						try{
					    						ItemStack item = new ItemStack(Material.getMaterial(id), amount);
					    						item.setDurability((short) durability);
					    						p2.getInventory().addItem(item);
					    						p2.updateInventory();
					    						
							    				p.sendMessage("§7You gave " + omp2.getName() + " §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
							    				p2.sendMessage("§7" + omp.getName() +"§7 gave you §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
				    						}catch(IllegalArgumentException ex){
				    							p.sendMessage("§7There's no §6Item§7 with the ID §6" + id + "§7!");
				    						}
				    					}
				    					
				    				}catch(NumberFormatException ex){
				    					Material m = null;
				    					
				    					for(Material ma : Material.values()){
				    						if(ma.toString().equalsIgnoreCase(itemstrings[0])){
				    							m = ma;
				    						}
				    						else if(ma.toString().replace("_", "").equalsIgnoreCase(itemstrings[0])){
				    							m = ma;
				    						}
				    						else{}
				    					}
				    					
				    					if(m != null){
					    					if(p2 == p){
					    						ItemStack item = new ItemStack(m, amount);
					    						item.setDurability((short) durability);
					    						p.getInventory().addItem(item);
					    						p.updateInventory();
					    						
							    				p.sendMessage("§7You gave yourself §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
					    					}
					    					else{
					    						ItemStack item = new ItemStack(m, amount);
					    						item.setDurability((short) durability);
					    						p2.getInventory().addItem(item);
					    						p2.updateInventory();
					    						
							    				p.sendMessage("§7You gave " + omp2.getName() + " §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
							    				p2.sendMessage("§7" + omp.getName() +"§7 gave you §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
					    					}
				    					}
				    					else{
				    						p.sendMessage("§7There's no §6Item§7 with the name §6" + itemstrings[0] + "§7!");
				    					}
				    				}
			    				}
			    				catch(NumberFormatException ex){
			    					p.sendMessage("§7The Durability §6" + itemstrings[1] + "§7 isn't a number!");
			    				}
			    			}
			    			else{
			    				try{
			    					int id = Integer.parseInt(a[2]);
			    					
			    					if(p2 == p){
			    						try{
				    						ItemStack item = new ItemStack(Material.getMaterial(id), amount);
				    						p.getInventory().addItem(item);
				    						p.updateInventory();
				    						
						    				p.sendMessage("§7You gave yourself §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
			    						}catch(Exception ex){
			    							p.sendMessage("§7There's no §6Item§7 with the ID §6" + id + "§7!");
			    						}
			    					}
			    					else{
			    						try{
				    						ItemStack item = new ItemStack(Material.getMaterial(id), amount);
				    						p2.getInventory().addItem(item);
				    						p2.updateInventory();
				    						
						    				p.sendMessage("§7You gave " + omp2.getName() + " §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
						    				p2.sendMessage("§7" + omp.getName() +"§7 gave you §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
			    						}catch(Exception ex){
			    							p.sendMessage("§7There's no §6Item§7 with the ID §6" + id + "§7!");
			    						}
			    					}
			    					
			    				}catch(NumberFormatException ex){
			    					Material m = null;
			    					
			    					for(Material ma : Material.values()){
			    						if(ma.toString().equalsIgnoreCase(a[2])){
			    							m = ma;
			    						}
			    						else if(ma.toString().replace("_", "").equalsIgnoreCase(a[2])){
			    							m = ma;
			    						}
			    						else{}
			    					}
			    					
			    					if(m != null){
				    					if(p2 == p){
				    						ItemStack item = new ItemStack(m, amount);
				    						p.getInventory().addItem(item);
				    						p.updateInventory();
				    						
						    				p.sendMessage("§7You gave yourself §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
				    					}
				    					else{
				    						ItemStack item = new ItemStack(m, amount);
				    						p2.getInventory().addItem(item);
				    						p2.updateInventory();
				    						
						    				p.sendMessage("§7You gave " + omp2.getName() + " §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
						    				p2.sendMessage("§7" + omp.getName() +"§7 gave you §6§l" + item.getAmount() + " §6" + item.getType().toString().toLowerCase() + "§7!");
				    					}
			    					}
			    					else{
			    						p.sendMessage("§7There's no §6Item§7 with the name §6" + a[2] + "§7!");
			    					}
			    				}
			    			}
			    		}
			    		else{
			    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
			    		}
	    			}
	    			catch(NumberFormatException ex){
	    				p.sendMessage("§7The amount §6" + a[3] + "§7 isn't a number!");
	    			}
		    	}
		    	else{
			    	p.sendMessage("§7Invalid Usage. (§6" + a[0].toLowerCase() + " <player> <item | id> (amount)§7)");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchGM(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Owner)){
				if(a.length == 2){
	    			if(Utils.isGameMode(GameMode.SURVIVAL, a)){
	    				p.setGameMode(GameMode.SURVIVAL);
	    				p.sendMessage("§7Set your §6GameMode§7 to §a§lSurvival§7!");
	    			}
	    			else if(Utils.isGameMode(GameMode.CREATIVE, a)){
	    				p.setGameMode(GameMode.CREATIVE);
	    				p.sendMessage("§7Set your §6GameMode§7 to §d§lCreative§7!");
	    			}
	    			else if(Utils.isGameMode(GameMode.ADVENTURE, a)){
	    				p.setGameMode(GameMode.ADVENTURE);
	    				p.sendMessage("§7Set your §6GameMode§7 to §2§lAdventure§7!");
	    			}
	    			else if(Utils.isGameMode(GameMode.SPECTATOR, a)){
	    				p.setGameMode(GameMode.SPECTATOR);
	    				p.sendMessage("§7Set your §6GameMode§7 to §e§lSpectate§7!");
	    			}
	    			else{
	    				p.sendMessage("§7Invalid Usage. (§6" + a[0] + " s|c|a|spec§7)");
	    			}
		    	}
		    	else if(a.length == 3){
		    		Player p2 = Utils.getPlayer(a[2]);
		    		
		    		if(p2 != null){
		    			if(p2 == p){
			    			if(Utils.isGameMode(GameMode.SURVIVAL, a)){
			    				p.setGameMode(GameMode.SURVIVAL);
			    				p.sendMessage("§7Set your §6GameMode§7 to §a§lSurvival§7!");
			    			}
			    			else if(Utils.isGameMode(GameMode.CREATIVE, a)){
			    				p.setGameMode(GameMode.CREATIVE);
			    				p.sendMessage("§7Set your §6GameMode§7 to §d§lCreative§7!");
			    			}
			    			else if(Utils.isGameMode(GameMode.ADVENTURE, a)){
			    				p.setGameMode(GameMode.ADVENTURE);
			    				p.sendMessage("§7Set your §6GameMode§7 to §2§lAdventure§7!");
			    			}
			    			else if(Utils.isGameMode(GameMode.SPECTATOR, a)){
			    				p.setGameMode(GameMode.SPECTATOR);
			    				p.sendMessage("§7Set your §6GameMode§7 to §e§lSpectate§7!");
			    			}
			    			else{
			    				p.sendMessage("§7Invalid Usage. (§6" + a[0] + " s|c|a|spec <player>§7)");
			    			}
		    			}
		    			else{
		    				OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
			    			if(Utils.isGameMode(GameMode.SURVIVAL, a)){
			    				p2.setGameMode(GameMode.SURVIVAL);
			    				p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §a§lSurvival§7!");
			    				p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §a§lSurvival§7!");
			    			}
			    			else if(Utils.isGameMode(GameMode.CREATIVE, a)){
			    				p2.setGameMode(GameMode.CREATIVE);
			    				p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §d§lCreative§7!");
			    				p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §d§lCreative§7!");
			    			}
			    			else if(Utils.isGameMode(GameMode.ADVENTURE, a)){
			    				p2.setGameMode(GameMode.ADVENTURE);
			    				p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §2§lAdventure§7!");
			    				p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §2§lAdventure§7!");
			    			}
			    			else if(Utils.isGameMode(GameMode.SPECTATOR, a)){
			    				p2.setGameMode(GameMode.SPECTATOR);
			    				p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §e§lSpectate§7!");
			    				p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §e§lSpectate§7!");
			    			}
			    			else{
			    				p.sendMessage("§7Invalid Usage. (§6" + a[0] + " s|c|a|spec <player>§7)");
			    			}
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[2] + " §7isn't §aOnline§7!");
		    		}
		    	}
		    	else{
	    			p.sendMessage("§7Invalid Usage. (§6" + a[0] + " s|c|a|spec§7)");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchGMA(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Owner)){
				if(a.length == 2){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
			    		
		    		if(p2 != null){
		    			if(p2 == p){
			    			p.setGameMode(GameMode.ADVENTURE);
			    			p.sendMessage("§7Set your §6GameMode§7 to §2§lAdventure§7!");
		    			}
		    			else{
			    			p2.setGameMode(GameMode.ADVENTURE);
			    			p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §2§lAdventure§7!");
			    			p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §2§lAdventure§7!");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
		    	else{
		    		p.setGameMode(GameMode.ADVENTURE);
		    		p.sendMessage("§7Set your §6GameMode§7 to §2§lAdventure§7!");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchGMC(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Owner)){
				if(a.length == 2){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
			    		
		    		if(p2 != null){
		    			if(p2 == p){
			    			p.setGameMode(GameMode.CREATIVE);
			    			p.sendMessage("§7Set your §6GameMode§7 to §d§lCreative§7!");
		    			}
		    			else{
			    			p2.setGameMode(GameMode.CREATIVE);
			    			p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §d§lCreative§7!");
			    			p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §d§lCreative§7!");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
		    	else{
		    		p.setGameMode(GameMode.CREATIVE);
		    		p.sendMessage("§7Set your §6GameMode§7 to §d§lCreative§7!");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchGMS(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Owner)){
				if(a.length == 2){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
			    		
		    		if(p2 != null){
		    			if(p2 == p){
			    			p.setGameMode(GameMode.SURVIVAL);
			    			p.sendMessage("§7Set your §6GameMode§7 to §a§lSurvival§7!");
		    			}
		    			else{
			    			p2.setGameMode(GameMode.SURVIVAL);
			    			p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §a§lSurvival§7!");
			    			p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §a§lSurvival§7!");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
		    	else{
		    		p.setGameMode(GameMode.SURVIVAL);
		    		p.sendMessage("§7Set your §6GameMode§7 to §a§lSurvival§7!");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchGMSpec(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Owner)){
				if(a.length == 2){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    		
		    		if(p2 != null){
		    			if(p2 == p){
			    			p.setGameMode(GameMode.SPECTATOR);
			    			p.sendMessage("§7Set your §6GameMode§7 to §e§lSpectate§7!");
		    			}
		    			else{
			    			p2.setGameMode(GameMode.SPECTATOR);
			    			p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §e§lSpectate§7!");
			    			p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §e§lSpectate§7!");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
		    	else{
		    		p.setGameMode(GameMode.SPECTATOR);
	    			p.sendMessage("§7Set your §6GameMode§7 to §e§lSpectate§7!");
		    	}
			}
			else if(omp.hasPerms(StaffRank.Moderator)){
				if(p.getGameMode() == GameMode.SPECTATOR){
					if(ServerData.isServer(Server.CREATIVE)){
			    		p.setGameMode(GameMode.CREATIVE);
		    			p.sendMessage("§7Set your §6GameMode§7 to §d§lCreative§7!");
					}
					else{
			    		p.setGameMode(GameMode.SURVIVAL);
		    			p.sendMessage("§7Set your §6GameMode§7 to §a§lSurvival§7!");
					}
				}
				else{
		    		p.setGameMode(GameMode.SPECTATOR);
	    			p.sendMessage("§7Set your §6GameMode§7 to §e§lSpectate§7!");
				}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchNick(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(VIPRank.Gold_VIP)){
				if(a.length == 2){
		    		if(a[1].length() <= 30){
		    			
		    			if(a[1].equalsIgnoreCase("off")){
		    				p.sendMessage("§7Removed your §6nickname§7!");
		    				omp.setNickname(null);
		    			}
		    			else{
			    			if(omp.hasPerms(VIPRank.Emerald_VIP)){
			    				String newnickname = a[1].replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&0", "§0").replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8").replace("&9", "§9").replace("&r", "§r").replace("&k", "§k").replace("&m", "§m").replace("&n", "§n").replace("&l", "§l");
			    				p.sendMessage("§7Changed your §6nickname§7 to '§a" + newnickname + "§7'.");
			    				omp.setNickname(newnickname);
			    			}
			    			else if(omp.hasPerms(VIPRank.Diamond_VIP)){
			    				String newnickname = a[1].replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&0", "§0").replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8").replace("&9", "§9");
			    				p.sendMessage("§7Changed your §6nickname§7 to '§9" + newnickname + "§7'.");
			    				omp.setNickname(newnickname);
			    			}
			    			else{
			    				p.sendMessage("§7Changed your §6nickname§7 to '§6" + a[1] + "§7'.");
			    				omp.setNickname(a[1]);
			    			}
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Your §6nickname§7 cannot be longer than §630 characters§7!");
		    		}
		    	}
		    	else{
		    		p.sendMessage("§7Invalid Usage. (§6/nick <nickname | off>§7)");
		    	}
			}
			else{
	    		p.sendMessage("§7Permission Denied. (You have to be a §6§lGold VIP§7)");
			}
		}
		
		private void dispatchOPMode(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Owner)){
				omp.setOpMode(!omp.isOpMode());
				p.sendMessage("§7Your §4§lOP Mode§7 is now " + Utils.statusString(omp.isOpMode()) + "§7!");
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchPerks(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			new CosmeticPerksInv().open(p);
		}
		
		private void dispatchPlugins(OMPlayer omp, String[] a){
			omp.unknownCommand(a[0]);
		}
		
		private void dispatchServers(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
		
			ServerSelectorInv.get().open(p);
		}
		
		private void dispatchSkull(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Owner) || ServerData.isServer(Server.HUB) && omp.hasPerms(StaffRank.Builder) && p.getWorld().getName().equals(ServerData.getHub().getBuilderWorld().getName())){
				if(a.length == 2){
		    		p.sendMessage("§7You've been given §6" + a[1] + "'s§7 skull.");

    				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
    				SkullMeta meta = (SkullMeta) item.getItemMeta();
    				meta.setDisplayName("§6" + a[1] + "'s §7Skull");
    				meta.setOwner(a[1]);
    				item.setItemMeta(meta);
    				item.setDurability((short) 3);
    				
    				p.getInventory().addItem(item);
		    	}
		    	else{
			    	p.sendMessage("§7Invalid Usage. (§6" + a[0].toLowerCase() + " <player>§7)");
		    	}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchTopVoters(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			int votes1 = 0;
			int votes2 = 0;
			int votes3 = 0;
			int votes4 = 0;
			int votes5 = 0;
			
			String player1 = null;
			String player2 = null;
			String player3 = null;
			String player4 = null;
			String player5 = null;
			
			for(String player : ServerStorage.voters.keySet()){
				int votes = ServerStorage.voters.get(player);
				if(votes >= votes1){
					votes5 = votes4;
					votes4 = votes3;
					votes3 = votes2;
					votes2 = votes1;
					votes1 = votes;

					player5 = player4;
					player4 = player3;
					player3 = player2;
					player2 = player1;
					player1 = player;
					
				}
				else if(votes >= votes2){
					votes5 = votes4;
					votes4 = votes3;
					votes3 = votes2;
					votes2 = votes;

					player5 = player4;
					player4 = player3;
					player3 = player2;
					player2 = player;
				}
				else if(votes >= votes3){
					votes5 = votes4;
					votes4 = votes3;
					votes3 = votes;

					player5 = player4;
					player4 = player3;
					player3 = player;
				}
				else if(votes >= votes4){
					votes5 = votes4;
					votes4 = votes;

					player5 = player4;
					player4 = player;
				}
				else if(votes >= votes5){
					votes5 = votes;

					player5 = player;
				}else{}
			}
			
			p.sendMessage("");
			p.sendMessage("§b§lVote §8| §b§lTop 5 Voters§7:");
			sendTopVoterMessage(p, "§6§l1.§6", player1, votes1);
			sendTopVoterMessage(p, "§7§l2.§7", player2, votes2);
			sendTopVoterMessage(p, "§c§l3.§c", player3, votes3);
			sendTopVoterMessage(p, "§8§l4.§8", player4, votes4);
			sendTopVoterMessage(p, "§8§l5.§8", player5, votes5);
		}
		
		private void sendTopVoterMessage(Player p, String placement, String player, int votes){
			if(votes == 1){
				p.sendMessage("  " + placement + " " + player + " §7| §b" + votes + " Vote");
			}
			else{
				if(player == null){
					p.sendMessage("  " + placement + " ");
				}
				else{
					p.sendMessage("  " + placement + " " + player + " §7| §b" + votes + " Votes");
				}
			}
		}
		
		private void dispatchTeleport(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Moderator)){
				if(a.length == 2){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    		
		    		if(p2 != null){
		    			if(p2 != p){
				    		p.teleport(p2);
				    		p.sendMessage("§7Teleported to " + omp2.getName() + "§7!");
		    			}
		    			else{
		    				p.sendMessage("§7You can't §6teleport§7 to yourself!");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
				else if(a.length == 3){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		Player p3 = Utils.getPlayer(a[2]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    		OMPlayer omp3 = OMPlayer.getOMPlayer(p3);
		    		
		    		if(p2 != null){
		    			if(p3 != null){
					    	p2.teleport(p3);
					    	p.sendMessage("§7Teleported " + omp2.getName() + "§7 to " + omp3.getName() + "§7!");
		    			}
		    			else{
		    				p.sendMessage("§7Player §6" + a[2] + " §7isn't §aOnline§7!");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
		    	else if(a.length == 5){
		    		Player p2 = Utils.getPlayer(a[1]);
		    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    		
		    		if(p2 != null){
		    			try{
			    			int x = Integer.parseInt(a[2]);
			    			int y = Integer.parseInt(a[3]);
			    			int z = Integer.parseInt(a[4]);
			    			
			    			Location l = new Location(p2.getWorld(), x, y, z, p2.getLocation().getYaw(), p2.getLocation().getPitch());
			    			
					    	p2.teleport(l);
					    	
					    	if(p2 != p){
					    		p.sendMessage("§7Teleported " + omp2.getName() + "§7 to §6" + x + "§7, §6" + y + "§7, §6" + z + "§7!");
					    	}
					    	else{
					    		p.sendMessage("§7Teleported to §6" + x + "§7, §6" + y + "§7, §6" + z + "§7!");
					    	}
					    }catch(NumberFormatException ex){
		    				p.sendMessage("§7The given coordinates aren't numbers!");
		    			}
		    		}
		    		else{
		    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
		    		}
		    	}
	    		else{
			    	p.sendMessage("§7Invalid Usage. §7(§6" + a[0].toLowerCase() + " <player | player1> (player2 | x) (y) (z)");
	    		}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchUndisguise(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Owner)){
				if(omp.isDisguised()){
					omp.undisguise();
					p.sendMessage("§7You are no longer §6disguised§7.");
				}
				else{
					p.sendMessage("§7You aren't §6disguised§7!");
				}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchUUID(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();
			
			if(omp.hasPerms(StaffRank.Moderator)){
				if(a.length == 2){
					UUID uuid = Utils.getUUID(a[1]);

					if(uuid != null){
						p.sendMessage("");
						p.sendMessage("§7Loading §6" + a[1] + "'s §7UUID info...");
						
						ComponentMessage cm = new ComponentMessage();
						cm.addPart(" §7UUID: ", null, null, null, null);
						cm.addPart("§6" + uuid.toString(), ClickEvent.Action.SUGGEST_COMMAND, uuid.toString(), Action.SHOW_TEXT, "§7Copy §6UUID§7.");
						cm.addPart("§7.", null, null, null, null);
						cm.send(p);
						
						p.sendMessage(" §7Name History:");
						HashMap<String, String> names = Utils.getNames(uuid);
						for(String name : names.keySet()){
							if(names.get(name) != null){
								p.sendMessage("  §6" + name + " " + names.get(name));
							}
							else{
								p.sendMessage("  §6" + name);
							}
						}
					}
					else{
						if(a[1].length() > 16){
							uuid = java.util.UUID.fromString(a[1]);
							
							if(uuid != null){
								p.sendMessage("");
								p.sendMessage("§7Loading §6" + a[1] + "§7 info...");
								
								ComponentMessage cm = new ComponentMessage();
								cm.addPart(" §7UUID: ", null, null, null, null);
								cm.addPart("§6" + uuid.toString(), ClickEvent.Action.SUGGEST_COMMAND, uuid.toString(), Action.SHOW_TEXT, "§7Copy §6UUID§7.");
								cm.addPart("§7.", null, null, null, null);
								cm.send(p);
								
								p.sendMessage(" §7Name History:");
								HashMap<String, String> names = Utils.getNames(uuid);
								for(String name : names.keySet()){
									if(names.get(name) != null){
										p.sendMessage("  §6" + name + " " + names.get(name));
									}
									else{
										p.sendMessage("  §6" + name);
									}
								}
							}
							else{
								p.sendMessage("§7Invalid UUID. (§6" + a[0].toLowerCase() + " <player>§7)");
							}
						}
						else{
							p.sendMessage("§7Invalid Player. (§6" + a[0].toLowerCase() + " <player>§7)");
						}
					}
				}
				else{
					p.sendMessage("§7Invalid Usage. (§6" + a[0].toLowerCase() + " <player | uuid>§7)");
				}
			}
			else{
				omp.unknownCommand(a[0]);
			}
		}
		
		private void dispatchVote(OMPlayer omp, String[] a){
			Player p = omp.getPlayer();

			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
			p.sendMessage("");
			p.sendMessage("§b§lVote §8| §7Vote for §b§lRewards§7!");
			p.sendMessage("§b§lVote §8| §7Reward in the " + ServerData.getServer().getName() + "§7 Server:");
			p.sendMessage("§b§lVote §8| §7");
			for(String s : ServerData.getServer().getVoteRewardsMessages()){
				p.sendMessage("§b§lVote §8| §7  - " + s);
			}
			p.sendMessage("§b§lVote §8| §7");
			p.sendMessage("§b§lVote §8| §7Vote at §b§lwww.orbitmines.com");
			p.sendMessage("§b§lVote §8| §7Your total Votes this Month: §b§l" + omp.getVotes());
		}
		
		public static List<String> getCommands(){
			List<String> commands = new ArrayList<String>();
			for(Command command : values()){
				commands.add(command.getName());
			}
			return commands;
		}
		public static List<String> getCommandNames(){
			List<String> commands = new ArrayList<String>();
			for(Command command : values()){
				commands.add(command.getName().substring(1));
			}
			return commands;
		}
		
		public static boolean isCommand(String cmd){
			for(Command command : values()){
				if(command.getName().equalsIgnoreCase(cmd)){
					return true;
				}
			}
			return false;
		}
	}
}
