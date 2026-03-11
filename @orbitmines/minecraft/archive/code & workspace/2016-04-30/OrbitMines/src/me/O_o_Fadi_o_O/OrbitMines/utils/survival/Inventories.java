package me.O_o_Fadi_o_O.OrbitMines.utils.survival;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.AnvilGUI;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventories {

	public static class RegionInv {
		
		private Inventory inventory;
		
		public RegionInv(){
			this.inventory = Bukkit.createInventory(null, 45, "§0§lRegion Teleporter");
			this.inventory.setContents(getContects());
		}
		
		public Inventory getInventory(){
			return inventory;
		}
		public void setInventory(Inventory inventory){
			this.inventory = inventory;
		}
		
		public void open(Player player){
			player.openInventory(getInventory());
		}
		
		public ItemStack[] getContects(){
			ItemStack[] contents = inventory.getContents();
			
			for(Region r : Region.getRegions()){
				contents[r.getSlot()] = r.getItemStack();
			}
			
			return contents;
		}
	}

	public static class WarpInv {
		
		private Inventory inventory;
		private int page;
		private boolean favoritewarps;
		
		public WarpInv(int page, boolean favoritewarps){
			this.inventory = Bukkit.createInventory(null, 36, "§0§lWarps");
			this.page = page;
			this.favoritewarps = favoritewarps;
		}
		
		public Inventory getInventory(){
			return inventory;
		}
		public void setInventory(Inventory inventory){
			this.inventory = inventory;
		}
		
		public void open(Player player){
			player.openInventory(getInventory());
			this.inventory.setContents(getContects(OMPlayer.getOMPlayer(player), this.page));
		}
		
		public ItemStack[] getContects(OMPlayer omp, int page){
			ItemStack[] contents = inventory.getContents();
			SurvivalPlayer sp = omp.getSurvivalPlayer();
			
			if(favoritewarps){
				contents[0] = Utils.setDisplayname(new ItemStack(Material.COMPASS), "§7§lZoek Warps");
				contents[9] = Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.DIAMOND), "§a§lFavoriete Warps"), Enchantment.DURABILITY, 1), 1);
			}
			else{
				contents[0] = Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.COMPASS), "§a§lZoek Warps"), Enchantment.DURABILITY, 1), 1);
				contents[9] = Utils.setDisplayname(new ItemStack(Material.DIAMOND), "§7§lFavoriete Warps");
			}
			
			int index = 0;
			List<Warp> warps = new ArrayList<Warp>();
			if(favoritewarps){
				warps = sp.getFavoriteWarps();
			}
			else{
				warps = Warp.getWarps();
			}
			for(int i = 2; i < 34; i++){
				if(i == 7){ i = 11; }
				if(i == 16){ i = 20; }
				if(i == 25){ i = 29; }
				
				contents[i] = getWarpItem(sp, warps, index + 5 * page);
				index++;
			}
			
			if(page == 0){
				contents[17] = Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.STAINED_GLASS_PANE, page), "§8Onbeschikbaar"), 7);
			}
			else{
				contents[17] = Utils.setDisplayname(new ItemStack(Material.ENDER_PEARL, page), "§3Scroll Omhoog");
			}
			
			if(contents[33].getType() == Material.STAINED_GLASS_PANE){
				contents[26] = Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.STAINED_GLASS_PANE, page +2), "§8Onbeschikbaar"), 7);
			}
			else{
				contents[26] = Utils.setDisplayname(new ItemStack(Material.ENDER_PEARL, page +2), "§3Scroll Omlaag");
			}
			
			return contents;
		}
		
		private ItemStack getWarpItem(SurvivalPlayer sp, List<Warp> warps, int index){
			if(warps.size() >= index +1){
				if(warps.get(index) != null){
					Warp warp = warps.get(index);
					
					ItemStack item = new ItemStack(warp.getItemStack());
					ItemMeta meta = item.getItemMeta();
					List<String> lore = meta.getLore();
					lore.add("");
					lore.add(" §3Rechtermuisknop §7- Teleporteren");
					if(sp.getFavoriteWarps().contains(warp)){
						lore.add(" §3Linkermuisknop §7- §cVerwijder van favorieten");
					}
					else{
						lore.add(" §3Linkermuisknop §7- §aVoeg toe aan favorieten");
					}
					meta.setLore(lore);
					item.setItemMeta(meta);
					
					if(sp.getFavoriteWarps().contains(warp)){
						return Utils.hideFlags(Utils.addEnchantment(item, Enchantment.DURABILITY, 1), 1);
					}
					return item;
				}
			}
			return Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.STAINED_GLASS_PANE), "§8Leeg Warp Slot"), 7);
		}
	}

	public static class WarpEditorInv {
		
		private Inventory inventory;
		private Warp warp;
		
		public WarpEditorInv(Warp warp){
			this.warp = warp;
			if(warp != null){
				this.inventory = Bukkit.createInventory(null, 9, "§0§lEdit Warp: " + warp.getName());
				this.inventory.setContents(getContects());
			}
			else{
				this.inventory = Bukkit.createInventory(null, 9, "§0§lEdit Warp: ");
			}
		}
		
		public Inventory getInventory(){
			return inventory;
		}
		public void setInventory(Inventory inventory){
			this.inventory = inventory;
		}
		
		public void open(Player player){
			player.openInventory(getInventory());
		}
		
		public ItemStack[] getContects(){
			ItemStack[] contents = inventory.getContents();
			
			contents[1] = Utils.setDisplayname(new ItemStack(Material.NAME_TAG), "§e§lVerander de Warp Naam");
			contents[3] = Utils.setDurability(Utils.setDisplayname(new ItemStack(warp.getItemStack().getType()), "§7§lVerander het Warp Item"), warp.getItemStack().getDurability());
			contents[5] = Utils.setDisplayname(new ItemStack(Material.MAP), "§6§lZet de Warp Locatie");
			
			if(warp.isEnabled()){
				contents[7] = Utils.setDisplayname(new ItemStack(Material.EYE_OF_ENDER), "§7§lWarp staat §a§lAAN");
			}
			else{
				contents[7] = Utils.setDisplayname(new ItemStack(Material.ENDER_PEARL), "§7§lWarp staat §c§lUIT");
			}
			
			return contents;
		}
	}

	public static class WarpItemEditorInv {
		
		private Inventory inventory;
		
		public WarpItemEditorInv(Warp warp){
			if(warp != null){
				this.inventory = Bukkit.createInventory(null, 27, "§0§lWarp Item: " + warp.getName());
				this.inventory.setContents(getContects());
			}
			else{
				this.inventory = Bukkit.createInventory(null, 27, "§0§lWarp Item: ");
			}
		}
		
		public Inventory getInventory(){
			return inventory;
		}
		public void setInventory(Inventory inventory){
			this.inventory = inventory;
		}
		
		public void open(Player player){
			player.openInventory(getInventory());
		}
		
		public ItemStack[] getContects(){
			ItemStack[] contents = inventory.getContents();
			
			contents[0] = getItemStack("§fBirch Sapling", Material.SAPLING, 2);
			contents[1] = getItemStack("§eSand", Material.SAND, 0);
			contents[2] = getItemStack("§cRed Rose", Material.RED_ROSE, 0);
			contents[3] = getItemStack("§aOak Sapling", Material.SAPLING, 0);
			contents[4] = getItemStack("§bPacked Ice", Material.ICE, 1);
			contents[5] = getItemStack("§bIce", Material.ICE, 0);
			contents[6] = getItemStack("§2Jungle Sapling", Material.SAPLING, 3);
			contents[7] = getItemStack("§2Spruce Sapling", Material.SAPLING, 1);
			contents[8] = getItemStack("§6Yellow Stained Clay", Material.STAINED_CLAY, 4);
			contents[9] = getItemStack("§cMushroom Block", Material.HUGE_MUSHROOM_2, 2);
			contents[10] = getItemStack("§9Water Bucket", Material.WATER_BUCKET, 0);
			contents[11] = getItemStack("§aGrass Block", Material.GRASS, 0);
			contents[12] = getItemStack("§2Dark Oak Sapling", Material.SAPLING, 5);
			contents[13] = getItemStack("§eAcacia Sapling", Material.SAPLING, 4);
			contents[14] = getItemStack("§fSnow Block", Material.SNOW_BLOCK, 0);
			contents[15] = getItemStack("§7Stone", Material.STONE, 0);
			contents[16] = getItemStack("§eSun Flower", Material.DOUBLE_PLANT, 0);
			contents[17] = getItemStack("§2Vine", Material.VINE, 0);

			contents[22] = Utils.setDisplayname(new ItemStack(Material.BARRIER), "§c§lAnnuleren");
			
			return contents;
		}
		
		private ItemStack getItemStack(String name, Material material, int durability){
			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Verander in " + name);
			item.setItemMeta(meta);
			item.setDurability((short) durability);
			
			return item;
		}
	}

	public static class WarpRenameInv {
		
		private AnvilGUI anvilgui;
		
		public WarpRenameInv(final OMPlayer omp, final Warp warp){
			this.anvilgui = new AnvilGUI(omp.getPlayer(), new AnvilGUI.AnvilClickEventHandler(){
				
				@Override
				public void onAnvilClick(AnvilGUI.AnvilClickEvent e){
					if(e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
						String warpname = e.getName();
						Player p = omp.getPlayer();
						SurvivalPlayer sp = omp.getSurvivalPlayer();
						List<Warp> warps = ServerData.getSurvival().getWarps();
						
						if(Warp.getWarp(warpname) == null){
							if(warpname.length() <= 20){
								boolean cancreate = true;
								for(int i = 0; i < warpname.length(); i++){
									int c = warpname.charAt(i);
									if(!Character.isAlphabetic(c) && !Character.isDigit(c)){
										cancreate = false;
									}
								}
	
								e.setWillClose(cancreate);
								e.setWillDestroy(cancreate);
								if(cancreate){
									if(warp == null){
										Biome b = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
										Warp w = new Warp(warps.size() +1, p.getUniqueId(), warpname, p.getLocation(), true, new SurvivalUtils().getMaterial(b), new SurvivalUtils().getDurability(b));
										warps.add(w);
										sp.getWarps().add(w);
										
										p.sendMessage("§7Warp Gemaakt! (§6" + warpname + "§7)");
									}
									else{
										p.sendMessage("§7Naam van Waro '§6" + warp.getName() + "§7' veranderd in '§6" + warpname + "§7'.");
										warp.setName(warpname);
										warp.updateItemStack(warp.getItemStack().getType(), warp.getItemStack().getDurability());
									}
									
									p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
									Warp.saveToConfig();
								}
								else{
									p.sendMessage("§7Alleen §6letters§7 en §6cijfers§7 zijn toegestaan!");
								}
							}
							else{
								e.setWillClose(false);
								e.setWillDestroy(false);
								
								p.sendMessage("§7Je niet meer dan §620 karakters§7 in een warp naame gebruiken!");
							}
						}
						else{
							e.setWillClose(false);
							e.setWillDestroy(false);
							
							p.sendMessage("§7Er is al een warp genaamd '§6" + warpname + "§7'.");
						}
					}
					else{
						e.setWillClose(false);
						e.setWillDestroy(false);
					}
				}
				
			});
			
			{
				ItemStack item = new ItemStack(Material.NAME_TAG, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("Warp Naam");
				item.setItemMeta(meta);
				this.anvilgui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, item);
			}
		}
		
		public AnvilGUI getAnvilGUI(){
			return anvilgui;
		}
		public void setAnvilGUI(AnvilGUI anvilgui){
			this.anvilgui = anvilgui;
		}
		
		public void open(){
			anvilgui.open();
		}
	}
}
