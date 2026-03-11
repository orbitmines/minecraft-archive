package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DisguiseCommand extends Command {

	String[] alias = { "/disguise", "/dis", "/d" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();

		if(omp.hasPerms(StaffRank.OWNER)){
			if(a.length == 2){
	    		if(a[1].equalsIgnoreCase("block")){
	    			p.sendMessage(Messages.CMD_INVALID_DISGUISE_BLOCK_USAGE.get(omp));
	    		}
	    		else{
		    		try{
		    			EntityType type = EntityType.valueOf(a[1].toUpperCase());
		    			omp.disguiseAsMob(type, false, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
		    			p.sendMessage(Messages.CMD_DISGUISED_AS.get(omp, a[1].toUpperCase()));
		    		}
		    		catch(IllegalArgumentException ex){
		    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Disguise.");
		    		}
	    		}
	    	}
	    	else if(a.length == 3){
	    		if(a[1].equalsIgnoreCase("block")){
	    			try{
	    				int id = Integer.parseInt(a[2]);
	    				omp.disguiseAsBlock(id, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
						p.sendMessage(Messages.CMD_DISGUISED_AS.get(omp, Material.getMaterial(id).toString()));
	    			}
	    			catch(IllegalArgumentException ex){
                        p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Block ID.");
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Disguise.");
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
                            p.sendMessage(Messages.CMD_DISGUISED_PLAYER_AS.get(omp, omp2.getName(), a[3].toUpperCase()));
                            p2.sendMessage(Messages.CMD_DISGUISED_AS.get(omp2, a[3].toUpperCase()));
			    		}
			    		catch(IllegalArgumentException ex){
			    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Disguise.");
			    		}
	    			}
	    			else{
	    				p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[2]));
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
                                    player.sendMessage(Messages.CMD_DISGUISED_AS.get(omplayer, a[3].toUpperCase()));
			    				}
			    			}

			    			omp.disguiseAsMob(type, false, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
                            p.sendMessage(Messages.CMD_DISGUISED_NEAR_AS.get(omp, amount + "", a[3].toUpperCase()));
                            p.sendMessage(Messages.CMD_DISGUISED_AS.get(omp, a[3].toUpperCase()));
			    			
			    		}
			    		catch(IllegalArgumentException ex){
			    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Disguise.");
			    		}
	    			}
	    			catch(NumberFormatException ex){
		    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Radius.");
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Disguise.");
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
                                p.sendMessage(Messages.CMD_DISGUISED_PLAYER_AS_BLOCK.get(omp, omp2.getName(), Material.getMaterial(id).toString()));
			    				p2.sendMessage(Messages.CMD_DISGUISED_AS_BLOCK.get(omp2, Material.getMaterial(id).toString()));
			    			}
			    			catch(NumberFormatException ex){
				    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Block ID.");
			    			}
			    		}
			    		else{
			    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Disguise.");
			    		}
	    			}
	    			else{
                        p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[2]));
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
                                        player.sendMessage(Messages.CMD_DISGUISED_AS_BLOCK.get(omplayer, Material.getMaterial(id).toString()));
				    				}
				    			}

		    					omp.disguiseAsBlock(id, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
                                p.sendMessage(Messages.CMD_DISGUISED_NEAR_AS_BLOCK.get(omp, amount + "", Material.getMaterial(id).toString()));
                                p.sendMessage(Messages.CMD_DISGUISED_AS_BLOCK.get(omp, Material.getMaterial(id).toString()));

				    		}
				    		catch(NumberFormatException ex){
				    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Block ID.");
				    		}
	    				}
	    				else{
			    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Disguise.");
	    				}
	    			}
	    			catch(NumberFormatException ex){
		    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Radius.");
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Disguise.");
	    		}
	    	}
	    	else{
	    		p.sendMessage("§7§lMobs");
	    		p.sendMessage(" §6/d (player | <player>) <mob>§7): ");
	    		p.sendMessage(" §7§l" + Messages.WORD_AVAILABLE.get(omp) + "§7: §6Armor_Stand§7, §6Bat§7, §6Blaze§7, §6Cave_Spider§7, §6Chicken§7, §6Cow§7, §6Creeper§7, §6Enderman§7, §6Enderman§7, §6Endermite§7, §6Ender_Dragon§7, §6Ghast§7, §6Giant§7, §6Guardian§7, §6Horse§7, §6Iron_Golem§7, §6Magma_Cube§7, §6Mushroom_Cow§7, §6Ocelot§7, §6Pig§7, §6Pig_Zombie, §6Polar_Bear§7, §6Rabbit§7, §6Sheep§7, §6Silverfish§7, §6Skeleton§7, §6Slime§7, §6Snowman§7, §6Spider§7, §6Squid§7, §6Villager§7, §6Witch§7, §6Wither§7, §6Wolf§7, §6Zombie");
	    		p.sendMessage("§7§lBlocks");
	    		p.sendMessage(" §6/d (player | <player>) block <id>");
	    		p.sendMessage(Messages.CMD_HELP_NEAR_TO_MOB.get(omp));
	    		p.sendMessage(" §6/d near <radius> <mob>");
	    		p.sendMessage(Messages.CMD_HELP_NEAR_TO_BLOCK.get(omp));
	    		p.sendMessage(" §6/d near <radius> block <id>");
	    	}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
