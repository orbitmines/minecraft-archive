package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.api.utils.Utils;
import om.api.utils.enums.cp.Hat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HatInv extends InventoryInstance {
	
	public HatInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lHats");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player){
		inventory.setContents(getContects(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContects(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
	
		setPage(omp, omp.getHatsInvPage());
		
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			inventory.setItem(48, item);
		}
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Hat Block Trail: " + Utils.statusString(omp.hasHatsBlockTrail()));
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(omp.hasHat()){
				if(!omp.hasUnlockedHatsBlockTrail()){
					itemlore.add("§cPrice: §b750 VIP Points");
					item.setDurability((short) 14);
				}
				else{
					itemlore.add("§a§lUnlocked");
					item.setDurability(Utils.statusDurability(omp.hasHatsBlockTrail()));
				}
			}
			else{
				itemlore.add("§cPrice: §b750 VIP Points");
				itemlore.add("§cRequired: §6A Hat");
				item.setDurability((short) 14);
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			inventory.setItem(49, item);
		}
		{
			ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§4§nDisable Hat");
			item.setItemMeta(itemmeta);
			inventory.setItem(50, item);
		}
		
		return inventory.getContents();
	}
	
	public void setPage(OMPlayer omp, int page){
		switch(page){
			case 1:
				getInventory().setItem(36, null);
				getInventory().setItem(44, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§nMore Hats >>"));
				
				setItem(omp, Hat.GOLD_ORE, 0);
				setItem(omp, Hat.GOLD_ORE, 0);
				setItem(omp, Hat.STONE_BRICKS, 1);
				setItem(omp, Hat.GREEN_GLASS, 2);
				setItem(omp, Hat.CACTUS, 3);
				setItem(omp, Hat.EMERALD_ORE, 4);
				setItem(omp, Hat.IRON_BLOCK, 5);
				setItem(omp, Hat.SNOW, 6);
				setItem(omp, Hat.TNT, 7);
				setItem(omp, Hat.COAL_ORE, 8);
				setItem(omp, Hat.BLACK_GLASS, 9);
				setItem(omp, Hat.GOLD_BLOCK, 10);
				setItem(omp, Hat.FURNACE, 11);
				setItem(omp, Hat.QUARTZ_BLOCK, 12);
				setItem(omp, Hat.HAY_BALE, 13);
				setItem(omp, Hat.REDSTONE_ORE, 14);
				setItem(omp, Hat.ICE, 15);
				setItem(omp, Hat.WORKBENCH, 16);
				setItem(omp, Hat.DIAMOND_BLOCK, 17);
				setItem(omp, Hat.IRON_ORE, 18);
				setItem(omp, Hat.GRASS, 19);
				setItem(omp, Hat.RED_GLASS, 20);
				setItem(omp, Hat.BEDROCK, 21);
				setItem(omp, Hat.LAPIS_ORE, 22);
				setItem(omp, Hat.REDSTONE_BLOCK, 23);
				setItem(omp, Hat.QUARTZ_BLOCK, 24);
				setItem(omp, Hat.LAPIS_BLOCK, 25);
				setItem(omp, Hat.MAGENTA_GLASS, 26);
				setItem(omp, Hat.COAL_BLOCK, 27);
				setItem(omp, Hat.EMERALD_BLOCK, 28);
				setItem(omp, Hat.MELON, 29);
				setItem(omp, Hat.GLASS, 30);
				setItem(omp, Hat.YELLOW_GLASS, 31);
				setItem(omp, Hat.MYCELIUM, 32);
				setItem(omp, Hat.LEAVES, 33);
				setItem(omp, Hat.DIAMOND_ORE, 34);
				setItem(omp, Hat.ORANGE_GLASS, 35);
				break;
			case 2:
				getInventory().setItem(36, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§n<< More Hats"));
				getInventory().setItem(44, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§nMore Hats >>"));
				
				setItem(omp, Hat.STONE_BRICKS, 0);
				setItem(omp, Hat.GREEN_GLASS, 1);
				setItem(omp, Hat.CACTUS, 2);
				setItem(omp, Hat.EMERALD_ORE, 3);
				setItem(omp, Hat.IRON_BLOCK, 4);
				setItem(omp, Hat.SNOW, 5);
				setItem(omp, Hat.TNT, 6);
				setItem(omp, Hat.COAL_ORE, 7);
				setItem(omp, Hat.DIORITE, 8);
				setItem(omp, Hat.GOLD_BLOCK, 9);
				setItem(omp, Hat.FURNACE, 10);
				setItem(omp, Hat.QUARTZ_BLOCK, 11);
				setItem(omp, Hat.HAY_BALE, 12);
				setItem(omp, Hat.REDSTONE_ORE, 13);
				setItem(omp, Hat.ICE, 14);
				setItem(omp, Hat.WORKBENCH, 15);
				setItem(omp, Hat.DIAMOND_BLOCK, 16);
				setItem(omp, Hat.DARK_PRISMARINE, 17);
				setItem(omp, Hat.GRASS, 18);
				setItem(omp, Hat.RED_GLASS, 19);
				setItem(omp, Hat.BEDROCK, 20);
				setItem(omp, Hat.LAPIS_ORE, 21);
				setItem(omp, Hat.REDSTONE_BLOCK, 22);
				setItem(omp, Hat.QUARTZ_BLOCK, 23);
				setItem(omp, Hat.LAPIS_BLOCK, 24);
				setItem(omp, Hat.MAGENTA_GLASS, 25);
				setItem(omp, Hat.SPONGE, 26);
				setItem(omp, Hat.EMERALD_BLOCK, 27);
				setItem(omp, Hat.MELON, 28);
				setItem(omp, Hat.GLASS, 29);
				setItem(omp, Hat.YELLOW_GLASS, 30);
				setItem(omp, Hat.MYCELIUM, 31);
				setItem(omp, Hat.LEAVES, 32);
				setItem(omp, Hat.DIAMOND_ORE, 33);
				setItem(omp, Hat.ORANGE_GLASS, 34);
				setItem(omp, Hat.SLIME_BLOCK, 35);
				break;
			case 3:
				getInventory().setItem(36, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§n<< More Hats"));
				getInventory().setItem(44, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§nMore Hats >>"));
				
				setItem(omp, Hat.GREEN_GLASS, 0);
				setItem(omp, Hat.CACTUS, 1);
				setItem(omp, Hat.EMERALD_ORE, 2);
				setItem(omp, Hat.IRON_BLOCK, 3);
				setItem(omp, Hat.SNOW, 4);
				setItem(omp, Hat.TNT, 5);
				setItem(omp, Hat.COAL_ORE, 6);
				setItem(omp, Hat.DIORITE, 7);
				setItem(omp, Hat.SEA_LANTERN, 8);
				setItem(omp, Hat.FURNACE, 9);
				setItem(omp, Hat.QUARTZ_BLOCK, 10);
				setItem(omp, Hat.HAY_BALE, 11);
				setItem(omp, Hat.REDSTONE_ORE, 12);
				setItem(omp, Hat.ICE, 13);
				setItem(omp, Hat.WORKBENCH, 14);
				setItem(omp, Hat.DIAMOND_BLOCK, 15);
				setItem(omp, Hat.DARK_PRISMARINE, 16);
				setItem(omp, Hat.PRISMARINE_BRICKS, 17);
				setItem(omp, Hat.RED_GLASS, 18);
				setItem(omp, Hat.BEDROCK, 19);
				setItem(omp, Hat.LAPIS_ORE, 20);
				setItem(omp, Hat.REDSTONE_BLOCK, 21);
				setItem(omp, Hat.QUARTZ_BLOCK, 22);
				setItem(omp, Hat.LAPIS_BLOCK, 23);
				setItem(omp, Hat.MAGENTA_GLASS, 24);
				setItem(omp, Hat.SPONGE, 25);
				setItem(omp, Hat.GRANITE, 26);
				setItem(omp, Hat.MELON, 27);
				setItem(omp, Hat.GLASS, 28);
				setItem(omp, Hat.YELLOW_GLASS, 29);
				setItem(omp, Hat.MYCELIUM, 30);
				setItem(omp, Hat.LEAVES, 31);
				setItem(omp, Hat.DIAMOND_ORE, 32);
				setItem(omp, Hat.ORANGE_GLASS, 33);
				setItem(omp, Hat.SLIME_BLOCK, 34);
				setItem(omp, Hat.CHEST, 35);
				break;
			case 4:
				getInventory().setItem(36, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§n<< More Hats"));
				getInventory().setItem(44, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§nMore Hats >>"));
				
				setItem(omp, Hat.CACTUS, 0);
				setItem(omp, Hat.EMERALD_ORE, 1);
				setItem(omp, Hat.IRON_BLOCK, 2);
				setItem(omp, Hat.SNOW, 3);
				setItem(omp, Hat.TNT, 4);
				setItem(omp, Hat.COAL_ORE, 5);
				setItem(omp, Hat.DIORITE, 6);
				setItem(omp, Hat.SEA_LANTERN, 7);
				setItem(omp, Hat.GLOWSTONE, 8);
				setItem(omp, Hat.QUARTZ_BLOCK, 9);
				setItem(omp, Hat.HAY_BALE, 10);
				setItem(omp, Hat.REDSTONE_ORE, 11);
				setItem(omp, Hat.ICE, 12);
				setItem(omp, Hat.WORKBENCH, 13);
				setItem(omp, Hat.DIAMOND_BLOCK, 14);
				setItem(omp, Hat.DARK_PRISMARINE, 15);
				setItem(omp, Hat.PRISMARINE_BRICKS, 16);
				setItem(omp, Hat.WET_SPONGE, 17);
				setItem(omp, Hat.BEDROCK, 18);
				setItem(omp, Hat.LAPIS_ORE, 19);
				setItem(omp, Hat.REDSTONE_BLOCK, 20);
				setItem(omp, Hat.QUARTZ_BLOCK, 21);
				setItem(omp, Hat.LAPIS_BLOCK, 22);
				setItem(omp, Hat.MAGENTA_GLASS, 23);
				setItem(omp, Hat.SPONGE, 24);
				setItem(omp, Hat.GRANITE, 25);
				setItem(omp, Hat.ANDESITE, 26);
				setItem(omp, Hat.GLASS, 27);
				setItem(omp, Hat.YELLOW_GLASS, 28);
				setItem(omp, Hat.MYCELIUM, 29);
				setItem(omp, Hat.LEAVES, 30);
				setItem(omp, Hat.DIAMOND_ORE, 31);
				setItem(omp, Hat.ORANGE_GLASS, 32);
				setItem(omp, Hat.SLIME_BLOCK, 33);
				setItem(omp, Hat.CHEST, 34);
				setItem(omp, Hat.BLUE_GLASS, 35);
				break;
			case 5:
				getInventory().setItem(36, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§n<< More Hats"));
				getInventory().setItem(44, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§nMore Hats >>"));
				
				setItem(omp, Hat.EMERALD_ORE, 0);
				setItem(omp, Hat.IRON_BLOCK, 1);
				setItem(omp, Hat.SNOW, 2);
				setItem(omp, Hat.TNT, 3);
				setItem(omp, Hat.COAL_ORE, 4);
				setItem(omp, Hat.DIORITE, 5);
				setItem(omp, Hat.SEA_LANTERN, 6);
				setItem(omp, Hat.GLOWSTONE, 7);
				setItem(omp, Hat.ACACIA_WOOD, 8);
				setItem(omp, Hat.HAY_BALE, 9);
				setItem(omp, Hat.REDSTONE_ORE, 10);
				setItem(omp, Hat.ICE, 11);
				setItem(omp, Hat.WORKBENCH, 12);
				setItem(omp, Hat.DIAMOND_BLOCK, 13);
				setItem(omp, Hat.DARK_PRISMARINE, 14);
				setItem(omp, Hat.PRISMARINE_BRICKS, 15);
				setItem(omp, Hat.WET_SPONGE, 16);
				setItem(omp, Hat.RED_WOOL, 17);
				setItem(omp, Hat.LAPIS_ORE, 18);
				setItem(omp, Hat.REDSTONE_BLOCK, 19);
				setItem(omp, Hat.QUARTZ_BLOCK, 20);
				setItem(omp, Hat.LAPIS_BLOCK, 21);
				setItem(omp, Hat.MAGENTA_GLASS, 22);
				setItem(omp, Hat.SPONGE, 23);
				setItem(omp, Hat.GRANITE, 24);
				setItem(omp, Hat.ANDESITE, 25);
				setItem(omp, Hat.BROWN_GLASS, 26);
				setItem(omp, Hat.YELLOW_GLASS, 27);
				setItem(omp, Hat.MYCELIUM, 28);
				setItem(omp, Hat.LEAVES, 29);
				setItem(omp, Hat.DIAMOND_ORE, 30);
				setItem(omp, Hat.ORANGE_GLASS, 31);
				setItem(omp, Hat.SLIME_BLOCK, 32);
				setItem(omp, Hat.CHEST, 33);
				setItem(omp, Hat.BLUE_GLASS, 34);
				setItem(omp, Hat.SOUL_SAND, 35);
				break;
			case 6:
				getInventory().setItem(36, ItemUtils.setDisplayname(new ItemStack(Material.EMPTY_MAP), "§e§n<< More Hats"));
				getInventory().setItem(44, null);
				
				setItem(omp, Hat.IRON_BLOCK, 0);
				setItem(omp, Hat.SNOW, 1);
				setItem(omp, Hat.TNT, 2);
				setItem(omp, Hat.COAL_ORE, 3);
				setItem(omp, Hat.DIORITE, 4);
				setItem(omp, Hat.SEA_LANTERN, 5);
				setItem(omp, Hat.GLOWSTONE, 6);
				setItem(omp, Hat.ACACIA_WOOD, 7);
				setItem(omp, Hat.CHISELLED_STONE_BRICKS, 8);
				setItem(omp, Hat.REDSTONE_ORE, 9);
				setItem(omp, Hat.ICE, 10);
				setItem(omp, Hat.WORKBENCH, 11);
				setItem(omp, Hat.DIAMOND_BLOCK, 12);
				setItem(omp, Hat.DARK_PRISMARINE, 13);
				setItem(omp, Hat.PRISMARINE_BRICKS, 14);
				setItem(omp, Hat.WET_SPONGE, 15);
				setItem(omp, Hat.RED_WOOL, 16);
				setItem(omp, Hat.BOOKSHELF, 17);
				setItem(omp, Hat.REDSTONE_BLOCK, 18);
				setItem(omp, Hat.QUARTZ_BLOCK, 19);
				setItem(omp, Hat.LAPIS_BLOCK, 20);
				setItem(omp, Hat.MAGENTA_GLASS, 21);
				setItem(omp, Hat.SPONGE, 22);
				setItem(omp, Hat.GRANITE, 23);
				setItem(omp, Hat.ANDESITE, 24);
				setItem(omp, Hat.BROWN_GLASS, 25);
				setItem(omp, Hat.NETHERRACK, 26);
				setItem(omp, Hat.MYCELIUM, 27);
				setItem(omp, Hat.LEAVES, 28);
				setItem(omp, Hat.DIAMOND_ORE, 29);
				setItem(omp, Hat.ORANGE_GLASS, 30);
				setItem(omp, Hat.SLIME_BLOCK, 31);
				setItem(omp, Hat.CHEST, 32);
				setItem(omp, Hat.BLUE_GLASS, 33);
				setItem(omp, Hat.SOUL_SAND, 34);
				setItem(omp, Hat.CYAN_GLASS, 35);
				break;
			default:
				break;
		}
	}
	
	private void setItem(OMPlayer omp, Hat hat, int slot){
		Inventory inv = getInventory();

		ItemStack item = new ItemStack(hat.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(hat.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		if(!hat.hasHat(omp)){
			itemlore.add(hat.getPriceName());
		}
		else{
			itemlore.add("§a§lUnlocked");
		}
		itemlore.add("");
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		item.setDurability(hat.getDurability());
		inv.setItem(slot, item); 
	}
}
