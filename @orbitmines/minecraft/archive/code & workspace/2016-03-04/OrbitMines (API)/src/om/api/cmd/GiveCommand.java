package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.PlayerUtils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends Command {

	String[] alias = { "/give" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			if(a.length == 3 || a.length == 4){
    			try{
    				int amount = 64;
    				if(a.length == 4){
    					amount = Integer.parseInt(a[3]);
    				}
    				
		    		Player p2 = PlayerUtils.getPlayer(a[1]);
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
}
