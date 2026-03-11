package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends Command {

	String[] alias = { "/give" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}


	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();

		if(omp.hasPerms(StaffRank.OWNER)){
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

											p.sendMessage(Messages.CMD_GIVE.get(omp, item.getAmount() + "", item.getType().toString().toLowerCase()));

										}catch(IllegalArgumentException ex){
											p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Item ID.");
			    						}
			    					}
			    					else{
			    						try{
				    						ItemStack item = new ItemStack(Material.getMaterial(id), amount);
				    						item.setDurability((short) durability);
				    						p2.getInventory().addItem(item);
				    						p2.updateInventory();

											p.sendMessage(Messages.CMD_GIVE_PLAYER.get(omp, omp2.getName(), item.getAmount() + "", item.getType().toString().toLowerCase()));
											p2.sendMessage(Messages.CMD_GIVEN.get(omp2, omp.getName(), item.getAmount() + "", item.getType().toString().toLowerCase()));
			    						}catch(IllegalArgumentException ex){
											p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Item ID.");
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

											p.sendMessage(Messages.CMD_GIVE.get(omp, item.getAmount() + "", item.getType().toString().toLowerCase()));
				    					}
				    					else{
				    						ItemStack item = new ItemStack(m, amount);
				    						item.setDurability((short) durability);
				    						p2.getInventory().addItem(item);
				    						p2.updateInventory();

											p.sendMessage(Messages.CMD_GIVE_PLAYER.get(omp, omp2.getName(), item.getAmount() + "", item.getType().toString().toLowerCase()));
											p2.sendMessage(Messages.CMD_GIVEN.get(omp2, omp.getName(), item.getAmount() + "", item.getType().toString().toLowerCase()));
				    					}
			    					}
			    					else{
										p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Item ID.");
			    					}
			    				}
		    				}
		    				catch(NumberFormatException ex){
								p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Item Durability.");
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

										p.sendMessage(Messages.CMD_GIVE.get(omp, item.getAmount() + "", item.getType().toString().toLowerCase()));
		    						}catch(Exception ex){
										p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Item ID.");
		    						}
		    					}
		    					else{
		    						try{
			    						ItemStack item = new ItemStack(Material.getMaterial(id), amount);
			    						p2.getInventory().addItem(item);
			    						p2.updateInventory();

										p.sendMessage(Messages.CMD_GIVE_PLAYER.get(omp, omp2.getName(), item.getAmount() + "", item.getType().toString().toLowerCase()));
										p2.sendMessage(Messages.CMD_GIVEN.get(omp2, omp.getName(), item.getAmount() + "", item.getType().toString().toLowerCase()));
		    						}catch(Exception ex){
										p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Item ID.");
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

										p.sendMessage(Messages.CMD_GIVE.get(omp, item.getAmount() + "", item.getType().toString().toLowerCase()));
			    					}
			    					else{
			    						ItemStack item = new ItemStack(m, amount);
			    						p2.getInventory().addItem(item);
			    						p2.updateInventory();

										p.sendMessage(Messages.CMD_GIVE_PLAYER.get(omp, omp2.getName(), item.getAmount() + "", item.getType().toString().toLowerCase()));
										p2.sendMessage(Messages.CMD_GIVEN.get(omp2, omp.getName(), item.getAmount() + "", item.getType().toString().toLowerCase()));
			    					}
		    					}
		    					else{
		    						p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Item ID.");
		    					}
		    				}
		    			}
		    		}
		    		else{
		    			p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
		    		}
    			}
    			catch(NumberFormatException ex){
    				p.sendMessage(Messages.CMD_NOT_NUMBER.get(omp, a[3]));
    			}
	    	}
	    	else{
		    	p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§6" + a[0].toLowerCase() + " <player> <item | id> (amount)§7)");
	    	}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
