package om.fog.utils;

import java.util.Random;

import om.fog.utils.enums.ToolLore;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class FoGUtils {

	public static String parseString(int level){
		switch(level){
			case 1:
				return "I";
			case 2:
				return "II";
			case 3:
				return "III";
			case 4:
				return "IV";
			case 5:
				return "V";
			case 6:
				return "VI";
			case 7:
				return "VII";
			case 8:
				return "VIII";
			case 9:
				return "IX";
			case 10:
				return "X";
		}
		return null;
	}
	
	public static int parseInteger(String s){
		switch(s){
			case "I":
				return 1;
			case "II":
				return 2;
			case "III":
				return 3;
			case "IV":
				return 4;
			case "V":
				return 5;
			case "VI":
				return 6;
			case "VII":
				return 7;
			case "VIII":
				return 8;
			case "IX":
				return 9;
			case "X":
				return 10;
		}
		return -1;
	}
	

	public static Location getRandomLocation(World world, int x1, int z1, int x2, int z2){
		Random r = new Random();
		
		int lowX = x1;
		int highX = x2;
		if(x2 < x1){
			lowX = x2; 
			highX = x1;
		}
		
		int lowZ = z1;
		int highZ = z2;
		if(z2 < z1){
			lowZ = z2; 
			highZ = z1;
		}
		
		int rX = r.nextInt(highX - lowX) + lowX;
		int rZ = r.nextInt(highZ - lowZ) + lowZ;
		
		return new Location(world, rX, getHighestYPos(world, rX, rZ), rZ);
	}
	
	public static int getHighestYPos(World world, int x, int z){
		for(int i = 250; i > 0; i--){
			Block b = world.getBlockAt(x, i, z);
			
			if(b != null && !b.isEmpty() && b.getType() != Material.BARRIER){
				return i +1;
			}
		}
		return 100;
	}
	
	public static ItemStack[] applyFortune(ItemStack inhand, ItemStack toadd){
		int level = ToolLore.LUCK.getLevel(inhand);
		if(level != -1){
			int r = new Random().nextInt(level +2);
			
			int multiplier = 2;
			for(int i = 0; i < level; i++){
				if(r == i){
					int amount = toadd.getAmount() * multiplier;
					int itemsleft = amount % toadd.getType().getMaxStackSize();
					
					if(itemsleft != amount){
						int stacks = (amount - itemsleft) / toadd.getType().getMaxStackSize();
						
						ItemStack[] items = new ItemStack[stacks +1];
						
						for(int i2 = 0; i2 < stacks; i2++){
							ItemStack item = new ItemStack(toadd);
							item.setAmount(item.getType().getMaxStackSize());
							items[i2] = item;
						}
						
						toadd.setAmount(itemsleft);
						items[stacks] = toadd;
						
						return items;
					}
					else{
						ItemStack[] items = new ItemStack[1];
						toadd.setAmount(amount);
						items[0] = toadd;
						
						return items;
					}
				}
				
				multiplier++;
			}
		}
		
		ItemStack[] items = new ItemStack[1];
		items[0] = toadd;
		
		return items;
	}
}
