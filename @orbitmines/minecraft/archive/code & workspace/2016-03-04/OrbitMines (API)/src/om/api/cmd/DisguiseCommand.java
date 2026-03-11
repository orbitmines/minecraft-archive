package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.PlayerUtils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseCommand extends Command {

	String[] alias = { "/disguise", "/dis", "/d" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();

		if(omp.hasPerms(StaffRank.Owner)){
			if(a.length == 2){
	    		if(a[1].equalsIgnoreCase("block")){
	    			p.sendMessage("§7Invalid Usage. (§6/disguise block <id>§7)");
	    		}
	    		else{
		    		try{
		    			EntityType type = EntityType.valueOf(a[1].toUpperCase());
		    			omp.disguiseAsMob(type, false, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
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
	    			Player p2 = PlayerUtils.getPlayer(a[2]);
	    			OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
	    			
	    			if(p2 != null){
	    				try{
			    			EntityType type = EntityType.valueOf(a[3].toUpperCase());
			    			omp2.disguiseAsMob(type, false, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
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
					    			omplayer.disguiseAsMob(type, false, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
			    					player.sendMessage("§7Disguised as: §6" + a[3].toUpperCase() + "§7.");
			    				}
			    			}

			    			omp.disguiseAsMob(type, false, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
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
	    			Player p2 = PlayerUtils.getPlayer(a[2]);
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
}
