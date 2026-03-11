package me.O_o_Fadi_o_O.Hub.events;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import me.O_o_Fadi_o_O.Hub.Hub;
import me.O_o_Fadi_o_O.Hub.ReflectionUtil;
import me.O_o_Fadi_o_O.Hub.Inv.ChatColors;
import me.O_o_Fadi_o_O.Hub.Inv.CosmeticPerks;
import me.O_o_Fadi_o_O.Hub.Inv.Disguises;
import me.O_o_Fadi_o_O.Hub.Inv.Fireworks;
import me.O_o_Fadi_o_O.Hub.Inv.Gadgets;
import me.O_o_Fadi_o_O.Hub.Inv.Hats;
import me.O_o_Fadi_o_O.Hub.Inv.Pets;
import me.O_o_Fadi_o_O.Hub.Inv.Trails;
import me.O_o_Fadi_o_O.Hub.Inv.Wardrobe;
import me.O_o_Fadi_o_O.Hub.NMS.CustomCow;
import me.O_o_Fadi_o_O.Hub.NMS.CustomMagmaCube;
import me.O_o_Fadi_o_O.Hub.NMS.CustomMushroomCow;
import me.O_o_Fadi_o_O.Hub.NMS.CustomOcelot;
import me.O_o_Fadi_o_O.Hub.NMS.CustomPig;
import me.O_o_Fadi_o_O.Hub.NMS.CustomSheep;
import me.O_o_Fadi_o_O.Hub.NMS.CustomSilverfish;
import me.O_o_Fadi_o_O.Hub.NMS.CustomSlime;
import me.O_o_Fadi_o_O.Hub.NMS.CustomWolf;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scoreboard.DisplaySlot;

public class ClickEventENG implements Listener{
	
	Hub plugin;
	 
	public ClickEventENG(Hub instance) {
	plugin = instance;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		HumanEntity he = e.getWhoClicked();
		if((he instanceof Player)){
			final Player p = (Player) he;
			ItemStack c = e.getCurrentItem();
			
			try{
			if(c != null){
				if(e.getSlotType() == SlotType.ARMOR){
					e.setCancelled(true);
				}
				if(e.getInventory().getName().equals("§0§lServer Selector")){
					e.setCancelled(true);
					
					if(c.getType() == Material.IRON_SWORD){
						teleportServer(p, "kitpvp");
					}
					if(c.getType() == Material.DIAMOND_PICKAXE){
						teleportServer(p, "prison");
					}
					if(c.getType() == Material.WOOD_AXE){
						teleportServer(p, "creative");
					}
					if(c.getType() == Material.STONE_HOE){
						teleportServer(p, "survival");
					}
					if(c.getType() == Material.GRASS){
						teleportServer(p, "skyblock");
					}
					if(c.getType() == Material.BOW){
						teleportServer(p, "minigames");
					}
				}
				if(e.getInventory().getName().equals("§0§lCosmetic Perks")){
					e.setCancelled(true);
				
					if(c.getType() == Material.MONSTER_EGG){
						p.openInventory(Pets.getPetsInv(p));
					}
					if(c.getType() == Material.INK_SACK){
						p.openInventory(ChatColors.getChatColors(p));
					}
					if(c.getType() == Material.SKULL_ITEM){
						p.openInventory(Disguises.getDisguisesInv(p));
					}
					if(c.getType() == Material.COMPASS){
						p.openInventory(Gadgets.getGadgetsInv(p));
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE){
						p.openInventory(Wardrobe.getWardrobeInv(p));
					}
					if(c.getType() == Material.STRING){
						p.openInventory(Trails.getTrailsInv(p));
					}
					if(c.getType() == Material.PUMPKIN){
						p.openInventory(Hats.getHatsInv(p));
					}
					if(c.getType() == Material.FIREWORK){
						Inventory inv = Bukkit.createInventory(null, 54, "§0§lFireworks");
						Fireworks.getFireworksInv(p, inv);
					}
				}
				if(e.getInventory().getName().equals("§0§lFireworks")){
					e.setCancelled(true);
					try{
						if(c.getItemMeta().getDisplayName().startsWith("§7Color 1:")){
							
							Inventory inv = p.getOpenInventory().getTopInventory();
							
							if(Hub.FireworkColor1.get(p.getName()) != null){
								
								if(Hub.FireworkColor1.get(p.getName()) == Color.AQUA){
									Hub.FireworkColor1.put(p.getName(), Color.BLACK);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.BLACK){
									Hub.FireworkColor1.put(p.getName(), Color.BLUE);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.BLUE){
									Hub.FireworkColor1.put(p.getName(), Color.FUCHSIA);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.FUCHSIA){
									Hub.FireworkColor1.put(p.getName(), Color.GRAY);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.GRAY){
									Hub.FireworkColor1.put(p.getName(), Color.GREEN);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.GREEN){
									Hub.FireworkColor1.put(p.getName(), Color.LIME);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.LIME){
									Hub.FireworkColor1.put(p.getName(), Color.MAROON);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.MAROON){
									Hub.FireworkColor1.put(p.getName(), Color.NAVY);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.NAVY){
									Hub.FireworkColor1.put(p.getName(), Color.ORANGE);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.ORANGE){
									Hub.FireworkColor1.put(p.getName(), Color.PURPLE);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.PURPLE){
									Hub.FireworkColor1.put(p.getName(), Color.RED);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.RED){
									Hub.FireworkColor1.put(p.getName(), Color.SILVER);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.SILVER){
									Hub.FireworkColor1.put(p.getName(), Color.TEAL);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.TEAL){
									Hub.FireworkColor1.put(p.getName(), Color.WHITE);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.WHITE){
									Hub.FireworkColor1.put(p.getName(), Color.YELLOW);
									Fireworks.getColor1(inv, p, 10);
								}
								else if(Hub.FireworkColor1.get(p.getName()) == Color.YELLOW){
									Hub.FireworkColor1.put(p.getName(), Color.AQUA);
									Fireworks.getColor1(inv, p, 10);
								}
								
							}
							else{
								Hub.FireworkColor1.put(p.getName(), Color.BLACK);
								Fireworks.getColor1(inv, p, 10);
							}
						}
						if(c.getItemMeta().getDisplayName().startsWith("§7Color 2:")){
							
							Inventory inv = p.getOpenInventory().getTopInventory();
							
							if(Hub.FireworkColor2.get(p.getName()) != null){
								
								if(Hub.FireworkColor2.get(p.getName()) == Color.AQUA){
									Hub.FireworkColor2.put(p.getName(), Color.BLACK);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.BLACK){
									Hub.FireworkColor2.put(p.getName(), Color.BLUE);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.BLUE){
									Hub.FireworkColor2.put(p.getName(), Color.FUCHSIA);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.FUCHSIA){
									Hub.FireworkColor2.put(p.getName(), Color.GRAY);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.GRAY){
									Hub.FireworkColor2.put(p.getName(), Color.GREEN);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.GREEN){
									Hub.FireworkColor2.put(p.getName(), Color.LIME);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.LIME){
									Hub.FireworkColor2.put(p.getName(), Color.MAROON);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.MAROON){
									Hub.FireworkColor2.put(p.getName(), Color.NAVY);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.NAVY){
									Hub.FireworkColor2.put(p.getName(), Color.ORANGE);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.ORANGE){
									Hub.FireworkColor2.put(p.getName(), Color.PURPLE);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.PURPLE){
									Hub.FireworkColor2.put(p.getName(), Color.RED);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.RED){
									Hub.FireworkColor2.put(p.getName(), Color.SILVER);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.SILVER){
									Hub.FireworkColor2.put(p.getName(), Color.TEAL);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.TEAL){
									Hub.FireworkColor2.put(p.getName(), Color.WHITE);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.WHITE){
									Hub.FireworkColor2.put(p.getName(), Color.YELLOW);
									Fireworks.getColor2(inv, p, 28);
								}
								else if(Hub.FireworkColor2.get(p.getName()) == Color.YELLOW){
									Hub.FireworkColor2.remove(p.getName());
									Fireworks.getColor2(inv, p, 28);
								}
								
							}
							else{
								Hub.FireworkColor2.put(p.getName(), Color.AQUA);
								Fireworks.getColor2(inv, p, 28);
							}
						}
						if(c.getItemMeta().getDisplayName().startsWith("§7Fade 1:")){
							
							Inventory inv = p.getOpenInventory().getTopInventory();
							
							if(Hub.FireworkFade1.get(p.getName()) != null){
								
								if(Hub.FireworkFade1.get(p.getName()) == Color.AQUA){
									Hub.FireworkFade1.put(p.getName(), Color.BLACK);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.BLACK){
									Hub.FireworkFade1.put(p.getName(), Color.BLUE);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.BLUE){
									Hub.FireworkFade1.put(p.getName(), Color.FUCHSIA);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.FUCHSIA){
									Hub.FireworkFade1.put(p.getName(), Color.GRAY);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.GRAY){
									Hub.FireworkFade1.put(p.getName(), Color.GREEN);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.GREEN){
									Hub.FireworkFade1.put(p.getName(), Color.LIME);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.LIME){
									Hub.FireworkFade1.put(p.getName(), Color.MAROON);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.MAROON){
									Hub.FireworkFade1.put(p.getName(), Color.NAVY);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.NAVY){
									Hub.FireworkFade1.put(p.getName(), Color.ORANGE);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.ORANGE){
									Hub.FireworkFade1.put(p.getName(), Color.PURPLE);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.PURPLE){
									Hub.FireworkFade1.put(p.getName(), Color.RED);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.RED){
									Hub.FireworkFade1.put(p.getName(), Color.SILVER);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.SILVER){
									Hub.FireworkFade1.put(p.getName(), Color.TEAL);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.TEAL){
									Hub.FireworkFade1.put(p.getName(), Color.WHITE);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.WHITE){
									Hub.FireworkFade1.put(p.getName(), Color.YELLOW);
									Fireworks.getFade1(inv, p, 12);
								}
								else if(Hub.FireworkFade1.get(p.getName()) == Color.YELLOW){
									Hub.FireworkFade1.remove(p.getName());
									Fireworks.getFade1(inv, p, 12);
								}
								
							}
							else{
								Hub.FireworkFade1.put(p.getName(), Color.AQUA);
								Fireworks.getFade1(inv, p, 12);
							}
						}
						if(c.getItemMeta().getDisplayName().startsWith("§7Fade 2:")){
							
							Inventory inv = p.getOpenInventory().getTopInventory();
							
							if(Hub.FireworkFade2.get(p.getName()) != null){
								
								if(Hub.FireworkFade2.get(p.getName()) == Color.AQUA){
									Hub.FireworkFade2.put(p.getName(), Color.BLACK);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.BLACK){
									Hub.FireworkFade2.put(p.getName(), Color.BLUE);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.BLUE){
									Hub.FireworkFade2.put(p.getName(), Color.FUCHSIA);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.FUCHSIA){
									Hub.FireworkFade2.put(p.getName(), Color.GRAY);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.GRAY){
									Hub.FireworkFade2.put(p.getName(), Color.GREEN);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.GREEN){
									Hub.FireworkFade2.put(p.getName(), Color.LIME);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.LIME){
									Hub.FireworkFade2.put(p.getName(), Color.MAROON);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.MAROON){
									Hub.FireworkFade2.put(p.getName(), Color.NAVY);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.NAVY){
									Hub.FireworkFade2.put(p.getName(), Color.ORANGE);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.ORANGE){
									Hub.FireworkFade2.put(p.getName(), Color.PURPLE);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.PURPLE){
									Hub.FireworkFade2.put(p.getName(), Color.RED);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.RED){
									Hub.FireworkFade2.put(p.getName(), Color.SILVER);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.SILVER){
									Hub.FireworkFade2.put(p.getName(), Color.TEAL);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.TEAL){
									Hub.FireworkFade2.put(p.getName(), Color.WHITE);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.WHITE){
									Hub.FireworkFade2.put(p.getName(), Color.YELLOW);
									Fireworks.getFade2(inv, p, 30);
								}
								else if(Hub.FireworkFade2.get(p.getName()) == Color.YELLOW){
									Hub.FireworkFade2.remove(p.getName());
									Fireworks.getFade2(inv, p, 30);
								}
								
							}
							else{
								Hub.FireworkFade2.put(p.getName(), Color.AQUA);
								Fireworks.getFade2(inv, p, 30);
							}
						}
						if(c.getItemMeta().getDisplayName().startsWith("§7Trail:")){
							
							Inventory inv = p.getOpenInventory().getTopInventory();
							
							if(Hub.FireworkTrail.get(p.getName()) != null){
								
								if(Hub.FireworkTrail.get(p.getName()) == false){
									Hub.FireworkTrail.put(p.getName(), true);
									Fireworks.getTrail(inv, p, 14);
								}
								else if(Hub.FireworkTrail.get(p.getName()) == true){
									Hub.FireworkTrail.put(p.getName(), false);
									Fireworks.getTrail(inv, p, 14);
								}
								
							}
							else{
								Hub.FireworkTrail.put(p.getName(), true);
								Fireworks.getTrail(inv, p, 14);
							}
						}
						if(c.getItemMeta().getDisplayName().startsWith("§7Flicker:")){
							
							Inventory inv = p.getOpenInventory().getTopInventory();
							
							if(Hub.FireworkFlicker.get(p.getName()) != null){
								
								if(Hub.FireworkFlicker.get(p.getName()) == false){
									Hub.FireworkFlicker.put(p.getName(), true);
									Fireworks.getFlicker(inv, p, 32);
								}
								else if(Hub.FireworkFlicker.get(p.getName()) == true){
									Hub.FireworkFlicker.put(p.getName(), false);
									Fireworks.getFlicker(inv, p, 32);
								}
								
							}
							else{
								Hub.FireworkFlicker.put(p.getName(), true);
								Fireworks.getFlicker(inv, p, 32);
							}
						}
						if(c.getItemMeta().getDisplayName().startsWith("§7Type:")){
							
							Inventory inv = p.getOpenInventory().getTopInventory();
							
							if(Hub.FireworkType.get(p.getName()) != null){
								
								if(Hub.FireworkType.get(p.getName()) == Type.BALL){
									Hub.FireworkType.put(p.getName(), Type.BALL_LARGE);
									Fireworks.getType(inv, p, 25);
								}
								else if(Hub.FireworkType.get(p.getName()) == Type.BALL_LARGE){
									Hub.FireworkType.put(p.getName(), Type.BURST);
									Fireworks.getType(inv, p, 25);
								}
								else if(Hub.FireworkType.get(p.getName()) == Type.BURST){
									Hub.FireworkType.put(p.getName(), Type.CREEPER);
									Fireworks.getType(inv, p, 25);
								}
								else if(Hub.FireworkType.get(p.getName()) == Type.CREEPER){
									Hub.FireworkType.put(p.getName(), Type.STAR);
									Fireworks.getType(inv, p, 25);
								}
								else if(Hub.FireworkType.get(p.getName()) == Type.STAR){
									Hub.FireworkType.put(p.getName(), Type.BALL);
									Fireworks.getType(inv, p, 25);
								}
								
							}
							else{
								Hub.FireworkType.put(p.getName(), Type.BALL_LARGE);
								Fireworks.getType(inv, p, 25);
							}
						}
						if(c.getType() == Material.ENDER_CHEST){
							p.openInventory(CosmeticPerks.getCosmeticPerks(p));
							p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
						}
						if(c.getType() == Material.ANVIL){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.ANVIL_USE, 5, 1);
							
							ItemStack item = new ItemStack(Material.FIREBALL, 1);
							ItemMeta itemmeta = item.getItemMeta();
							itemmeta.setDisplayName("§c§nFirework Gun§r §c(§6" + Hub.FireworkPasses.get(p.getName()) + "§c)");
							item.setItemMeta(itemmeta);
							p.getInventory().setItem(5, new ItemStack(item));
						}
					}catch(Exception ex){
						
					}
					try{
						if(c.getType() == Material.EMPTY_MAP && c.getItemMeta().getDisplayName().equals("§6§l+5 Firework Passes")){
							if(Hub.points.get(p.getName()) >= 2){
								
								try {
									Hub.setFireworksPasses(p, Hub.FireworkPasses.get(p.getName()));
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								
								try {
									plugin.removeVIPPoints(p, 2);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								try {
									plugin.addFireworksPasses(p, 5);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §6§l5 Firework Passes§9 for§b 2 VIP Points§9!");
								
								Inventory inv = p.getOpenInventory().getTopInventory();
								Fireworks.getVIPPasses(inv, p, 50);
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
						if(c.getType() == Material.EMPTY_MAP && c.getItemMeta().getDisplayName().equals("§6§l+25 Firework Passes")){
							if(Hub.points.get(p.getName()) >= 10){
								
								try {
									Hub.setFireworksPasses(p, Hub.FireworkPasses.get(p.getName()));
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								
								try {
									plugin.removeVIPPoints(p, 10);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								try {
									plugin.addFireworksPasses(p, 25);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §6§l25 Firework Passes§9 for§b 10 VIP Points§9!");
								
								Inventory inv = p.getOpenInventory().getTopInventory();
								Fireworks.getVIPPasses(inv, p, 50);
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}catch(Exception ex){
						
					}
				}
				if(e.getInventory().getName().equals("§0§lGadgets")){
					e.setCancelled(true);
					
					if(c.getType() == Material.LEASH && c.getItemMeta().getDisplayName().equals("§6§lStacker")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							ItemStack item = new ItemStack(Material.LEASH, 1);
							ItemMeta itemmeta = item.getItemMeta();
							itemmeta.setDisplayName("§6§nStacker");
							item.setItemMeta(itemmeta);
							p.getInventory().setItem(5, new ItemStack(item));
							
							p.sendMessage("§fActivated your §6§lStacker§b Gadget§f!");
							
						}
						else{
							
						}
					}
					if(c.getType() == Material.SNOW_BALL && c.getItemMeta().getDisplayName().equals("§f§lPaintballs")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							ItemStack item = new ItemStack(Material.SNOW_BALL, 1);
							ItemMeta itemmeta = item.getItemMeta();
							itemmeta.setDisplayName("§f§nPaintballs");
							item.setItemMeta(itemmeta);
							p.getInventory().setItem(5, new ItemStack(item));
							
							p.sendMessage("§fActivated your §f§lPaintballs§b Gadget§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 700){
								
								try {
									plugin.removeVIPPoints(p, 700);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.gadgetsPaintballs.put(p.getName(), 1);
								plugin.insertPath(p.getName(), "Gadgets-Paintballs", "paintballs", 0);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §f§lPaintballs§b Gadget§9 for§b 700 VIP Points§9!");
								p.openInventory(Gadgets.getGadgetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.SKULL_ITEM && c.getItemMeta().getDisplayName().equals("§a§lCreeper Launcher")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
							ItemMeta itemmeta = item.getItemMeta();
							itemmeta.setDisplayName("§a§nCreeper Launcher");
							item.setItemMeta(itemmeta);
							item.setDurability((short) 4);
							p.getInventory().setItem(5, new ItemStack(item));
							
							p.sendMessage("§fActivated your §a§lCreeper Launcher§b Gadget§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 525){
								
								try {
									plugin.removeVIPPoints(p, 525);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.gadgetsCreeperLauncher.put(p.getName(), 1);
								plugin.insertPath(p.getName(), "Gadgets-CreeperLauncher", "launcher", 0);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §a§lCreeper Launcher§b Gadget§9 for§b 525 VIP Points§9!");
								p.openInventory(Gadgets.getGadgetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.SADDLE && c.getItemMeta().getDisplayName().equals("§e§lPet Ride")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							ItemStack item = new ItemStack(Material.SADDLE, 1);
							ItemMeta itemmeta = item.getItemMeta();
							itemmeta.setDisplayName("§e§nPet Ride");
							item.setItemMeta(itemmeta);
							p.getInventory().setItem(5, new ItemStack(item));
							
							p.sendMessage("§fActivated your §e§lPet Ride§b Gadget§f!");
							
						}
						else{
							if(c.getItemMeta().getLore().contains("§c§oPet Required")){
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§f§oYou §c§lDO NOT§f§o have a Pet!");
							}
							else{
								if(Hub.points.get(p.getName()) >= 100){
									
									try {
										plugin.removeVIPPoints(p, 100);
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
									Hub.gadgetsPetRide.put(p.getName(), 1);
									plugin.insertPath(p.getName(), "Gadgets-PetRide", "petride", 0);
									p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
									p.sendMessage("§9You've bought the §e§lPet Ride§b Gadget§9 for§b 100 VIP Points§9!");
									p.openInventory(Gadgets.getGadgetsInv(p));
								}
								else{
									p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
									p.sendMessage("§9You don't have enough§b VIP Points§9!");
								}
							}
						}
					}
					if(c.getType() == Material.BOOK && c.getItemMeta().getDisplayName().equals("§7§lBook Explosion")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							ItemStack item = new ItemStack(Material.BOOK, 1);
							ItemMeta itemmeta = item.getItemMeta();
							itemmeta.setDisplayName("§7§nBook Explosion");
							item.setItemMeta(itemmeta);
							p.getInventory().setItem(5, new ItemStack(item));
							
							p.sendMessage("§fActivated your §7§lBook Explosion§b Gadget§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.gadgetsBookExplosion.put(p.getName(), 1);
								plugin.insertPath(p.getName(), "Gadgets-BookExplosion", "bookexplosion", 0);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7§lBook Explosion§b Gadget§9 for§b 475 VIP Points§9!");
								p.openInventory(Gadgets.getGadgetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.ENDER_CHEST){
						p.openInventory(CosmeticPerks.getCosmeticPerks(p));
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					}
				}
				if(e.getInventory().getName().equals("§0§lSettings (" + p.getName() + ")")){
					e.setCancelled(true);
					if(c.getType() == Material.EYE_OF_ENDER && c.getItemMeta().getDisplayName().equals("§3§lPlayers§a§l ENABLED")){
						p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						p.sendMessage("§3§l§oPlayers §c§l§oDISABLED");
						
						for(Player player : Bukkit.getOnlinePlayers()){
							p.hidePlayer(player);
						}
						Hub.EnablePlayers.put(p.getName(), false);
						p.closeInventory();
					}
					if(c.getType() == Material.EYE_OF_ENDER && c.getItemMeta().getDisplayName().equals("§3§lPlayers§c§l DISABLED")){
						p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						p.sendMessage("§3§l§oPlayers §a§l§oENABLED");
						
						for(Player player : Bukkit.getOnlinePlayers()){
							p.showPlayer(player);
						}
						Hub.EnablePlayers.put(p.getName(), true);
						p.closeInventory();
					}
					if(c.getType() == Material.LEASH && c.getItemMeta().getDisplayName().equals("§6§lStacker§a§l ENABLED")){
						p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						p.sendMessage("§6§l§oStacker §c§l§oDISABLED");
						
						Hub.EnableStacker.put(p.getName(), false);
						p.closeInventory();
					}
					if(c.getType() == Material.LEASH && c.getItemMeta().getDisplayName().equals("§6§lStacker§c§l DISABLED")){
						p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						p.sendMessage("§6§l§oStacker §a§l§oENABLED");
						
						Hub.EnableStacker.put(p.getName(), true);
						p.closeInventory();
					}
					if(c.getType() == Material.PAPER && c.getItemMeta().getDisplayName().equals("§f§lScoreboard§a§l ENABLED")){
						p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						p.sendMessage("§f§l§oScoreboard §c§l§oDISABLED");
						
						Hub.EnableScoreboard.put(p.getName(), false);
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						p.closeInventory();
					}
					if(c.getType() == Material.PAPER && c.getItemMeta().getDisplayName().equals("§f§lScoreboard§c§l DISABLED")){
						p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						p.sendMessage("§f§l§oScoreboard §a§l§oENABLED");
						
						Hub.EnableScoreboard.put(p.getName(), true);
						p.closeInventory();
					}
				}
				if(e.getInventory().getName().equals("§0§lDisguises")){
					e.setCancelled(true);
					
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§2Witch Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.WITCH);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §2Witch Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 450){
								
								try {
									plugin.removeVIPPoints(p, 450);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disWitch.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Witch", "witch", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §2Witch Disguise§9 for§b 450 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§8Bat Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.BAT);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §8Bat Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 400){
								
								try {
									plugin.removeVIPPoints(p, 400);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disBat.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Bat", "bat", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Bat Disguise§9 for§b 400 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§fChicken Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.CHICKEN);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §fChicken Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 325){
								
								try {
									plugin.removeVIPPoints(p, 325);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disChicken.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Chicken", "chicken", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fChicken Disguise§9 for§b 325 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§eOcelot Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.OCELOT);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §eOcelot Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 375){
								
								try {
									plugin.removeVIPPoints(p, 375);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disOcelot.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Ocelot", "ocelot", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §eOcelot Disguise§9 for§b 375 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§4Mushroom Cow Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.MUSHROOM_COW);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §4Mushroom Cow Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 350){
								
								try {
									plugin.removeVIPPoints(p, 350);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disMushroomCow.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-MushroomCow", "mushroomcow", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §4Mushroom Cow Disguise§9 for§b 350 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§9Squid Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.SQUID);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §9Squid Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 500){
								
								try {
									plugin.removeVIPPoints(p, 500);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disSquid.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Squid", "squid", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §9Squid Disguise§9 for§b 500 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§aSlime Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.SLIME);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §aSlime Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 425){
								
								try {
									plugin.removeVIPPoints(p, 425);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disSlime.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Slime", "slime", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §aSlime Disguise§9 for§b 425 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§dZombie Pigman Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.PIG_ZOMBIE);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §dZombie Pigman Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 400){
								
								try {
									plugin.removeVIPPoints(p, 400);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disZombiePigman.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-ZombiePigman", "zombiepigman", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §dZombie Pigman Disguise§9 for§b 400 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§cMagma Cube Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.MAGMA_CUBE);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §cMagma Cube Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disMagmaCube.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-MagmaCube", "magmacube", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §cMagma Cube Disguise§9 for§b 475 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§7Skeleton Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.SKELETON);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §7Skeleton Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 500){
								
								try {
									plugin.removeVIPPoints(p, 500);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disSkeleton.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Skeleton", "skeleton", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Skeleton Disguise§9 for§b 500 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§7Wolf Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.WOLF);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §7Wolf Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 400){
								
								try {
									plugin.removeVIPPoints(p, 400);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disWolf.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Wolf", "wolf", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Wolf Disguise§9 for§b 400 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§8Spider Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.SPIDER);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §8Spider Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 375){
								
								try {
									plugin.removeVIPPoints(p, 375);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disSpider.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Spider", "spider", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Spider Disguise§9 for§b 375 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§7Silverfish Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.SILVERFISH);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §7Silverfish Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disSilverfish.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Silverfish", "silverfish", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Silverfish Disguise§9 for§b 475 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§fSheep Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.SHEEP);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §fSheep Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 375){
								
								try {
									plugin.removeVIPPoints(p, 375);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disSheep.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Sheep", "sheep", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fSheep Disguise§9 for§b 375 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§3Cave Spider Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.CAVE_SPIDER);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §3Cave Spider Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 400){
								
								try {
									plugin.removeVIPPoints(p, 400);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disCaveSpider.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-CaveSpider", "cavespider", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §3Cave Spider Disguise§9 for§b 400 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§aCreeper Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.CREEPER);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §aCreeper Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disCreeper.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Creeper", "creeper", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §aCreeper Disguise§9 for§b 475 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§8Cow Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.COW);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §8Cow Disguise§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 350){
								
								try {
									plugin.removeVIPPoints(p, 350);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.disCow.put(p.getName(), 1);
								plugin.insertDisguises(p.getName(), "Dis-Cow", "cow", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Cow Disguise§9 for§b 350 VIP Points§9!");
								p.openInventory(Disguises.getDisguisesInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§6Blaze Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.BLAZE);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §6Blaze Disguise§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §a§lEmerald VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§dPig Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.PIG);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §dPig Disguise§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §7§lIron VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§2Zombie Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.ZOMBIE);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §2Zombie Disguise§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §6§lGold VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§6Villager Disguise")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

							MobDisguise mob = new MobDisguise(DisguiseType.VILLAGER);
							DisguiseAPI.disguiseToPlayers(p, mob, Bukkit.getOnlinePlayers());
							
							p.sendMessage("§fActivated your §6Villager Disguise§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §b§lDiamond VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.ENDER_CHEST){
						p.openInventory(CosmeticPerks.getCosmeticPerks(p));
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					}
					if(c.getType() == Material.FIRE){
						p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
						p.closeInventory();
						p.sendMessage("§fYour Current Disguise has been §c§lREMOVED§f!");
						DisguiseAPI.undisguiseToAll(p);
					}
				}
				if(e.getInventory().getName().equals("§0§lHats")){
					e.setCancelled(true);
					
					if(c.getType() == Material.GOLD_ORE && c.getItemMeta().getDisplayName().equals("§6Gold Ore Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.GOLD_ORE, 1));
							
							p.sendMessage("§fActivated your §6Gold Ore Hat§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §6§lGold VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.SMOOTH_BRICK && c.getItemMeta().getDisplayName().equals("§7Stone Bricks Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.SMOOTH_BRICK, 1));
							
							p.sendMessage("§fActivated your §7Stone Bricks Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 75){
								
								try {
									plugin.removeVIPPoints(p, 75);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsStoneBricks.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-StoneBricks", "stonebricks", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Stone Bricks Hat§9 for§b 75 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.STAINED_GLASS && c.getItemMeta().getDisplayName().equals("§aLime Stained Glass Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.STAINED_GLASS, 1);
							item.setDurability((short) 5);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §aLime Stained Glass Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsGreenGlass.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-GreenGlass", "greenglass", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §aLime Stained Glass Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.CACTUS && c.getItemMeta().getDisplayName().equals("§2Cactus Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.CACTUS, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §2Cactus Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsCactus.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Cactus", "cactus", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §2Cactus Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.EMERALD_ORE && c.getItemMeta().getDisplayName().equals("§aEmerald Ore Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.EMERALD_ORE, 1));
							
							p.sendMessage("§fActivated your §aEmerald Ore Hat§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §a§lEmerald VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.IRON_BLOCK && c.getItemMeta().getDisplayName().equals("§7Iron Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.IRON_BLOCK, 1));
							
							p.sendMessage("§fActivated your §7Iron Block Hat§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §7§lIron VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.SNOW_BLOCK && c.getItemMeta().getDisplayName().equals("§fSnow Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.SNOW_BLOCK, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §fSnow Block Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 75){
								
								try {
									plugin.removeVIPPoints(p, 75);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsSnow.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Snow", "snow", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fSnow Block Hat§9 for§b 75 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.TNT && c.getItemMeta().getDisplayName().equals("§cTNT Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.TNT, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §cTNT Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsTNT.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-TNT", "tnt", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §cTNT Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.COAL_ORE && c.getItemMeta().getDisplayName().equals("§8Coal Ore Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.COAL_ORE, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §8Coal Ore Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsCoalOre.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-CoalOre", "coalore", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Coal Ore Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.STAINED_GLASS && c.getItemMeta().getDisplayName().equals("§8Black Stained Glass Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.STAINED_GLASS, 1);
							item.setDurability((short) 15);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §8Black Stained Glass Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsBlackGlass.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-BlackGlass", "blackglass", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Black Stained Glass Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.GOLD_BLOCK && c.getItemMeta().getDisplayName().equals("§6Gold Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.GOLD_BLOCK, 1));
							
							p.sendMessage("§fActivated your §6Gold Block Hat§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §6§lGold VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.FURNACE && c.getItemMeta().getDisplayName().equals("§7Furnace Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.FURNACE, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §7Furnace Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsFurnace.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Furnace", "furnace", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Furnace Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.QUARTZ_ORE && c.getItemMeta().getDisplayName().equals("§cQuartz Ore Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.QUARTZ_ORE, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §cQuartz Ore Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsQuartzOre.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-QuartzOre", "quartzore", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §cQuartz Ore Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.HAY_BLOCK && c.getItemMeta().getDisplayName().equals("§eHay Bale Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.HAY_BLOCK, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §eHay Bale Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsHayBale.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-HayBale", "haybale", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §eHay Bale Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.REDSTONE_ORE && c.getItemMeta().getDisplayName().equals("§4Redstone Ore Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.REDSTONE_ORE, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §4Redstone Ore Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsRedstoneOre.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-RedstoneOre", "redstoneore", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §4Redstone Ore Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.ICE && c.getItemMeta().getDisplayName().equals("§bIce Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.ICE, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §bIce Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 150){
								
								try {
									plugin.removeVIPPoints(p, 150);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsIce.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Ice", "ice", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §bIce Hat§9 for§b 150 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.WORKBENCH && c.getItemMeta().getDisplayName().equals("§6Workbench Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.WORKBENCH, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §6Workbench Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsWorkbench.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Workbench", "workbench", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §6Workbench Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.DIAMOND_BLOCK && c.getItemMeta().getDisplayName().equals("§bDiamond Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_BLOCK, 1));
							
							p.sendMessage("§fActivated your §bDiamond Block Hat§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §b§lDiamond VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.IRON_ORE && c.getItemMeta().getDisplayName().equals("§7Iron Ore Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.IRON_ORE, 1));
							
							p.sendMessage("§fActivated your §7Iron Ore Hat§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §7§lIron VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.GRASS && c.getItemMeta().getDisplayName().equals("§aGrass Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.GRASS, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §aGrass Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsGrass.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Grass", "grass", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §aGrass Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.STAINED_GLASS && c.getItemMeta().getDisplayName().equals("§4Red Stained Glass Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.STAINED_GLASS, 1);
							item.setDurability((short) 14);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §4Red Stained Glass Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsRedGlass.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-RedGlass", "redglass", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §4Red Stained Glass Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.BEDROCK && c.getItemMeta().getDisplayName().equals("§8Bedrock Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.BEDROCK, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §8Bedrock Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsBedrock.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Bedrock", "bedrock", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Bedrock Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LAPIS_ORE && c.getItemMeta().getDisplayName().equals("§1Lapis Ore Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.LAPIS_ORE, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §1Lapis Ore Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsLapisOre.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-LapisOre", "lapisore", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §1Lapis Ore Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.REDSTONE_BLOCK && c.getItemMeta().getDisplayName().equals("§4Redstone Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.REDSTONE_BLOCK, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §4Redstone Block Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsRedstoneBlock.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-RedstoneBlock", "redstoneblock", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §4Redstone Block Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.QUARTZ_BLOCK && c.getItemMeta().getDisplayName().equals("§fQuartz Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.QUARTZ_BLOCK, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §fQuartz Block Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 75){
								
								try {
									plugin.removeVIPPoints(p, 75);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsQuartzBlock.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-QuartzBlock", "quartzblock", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fQuartz Block Hat§9 for§b 75 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LAPIS_BLOCK && c.getItemMeta().getDisplayName().equals("§1Lapis Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.LAPIS_BLOCK, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §1Lapis Block Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsLapisBlock.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-LapisBlock", "lapisblock", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §1Lapis Block Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.STAINED_GLASS && c.getItemMeta().getDisplayName().equals("§dMagenta Stained Glass Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.STAINED_GLASS, 1);
							item.setDurability((short) 2);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §dMagenta Stained Glass Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsMagentaGlass.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-MagentaGlass", "magentaglass", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §dMagenta Stained Glass Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.COAL_BLOCK && c.getItemMeta().getDisplayName().equals("§0Coal Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.COAL_BLOCK, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §0Coal Block Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsCoalBlock.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-CoalBlock", "coalblock", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §0Coal Block Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.EMERALD_BLOCK && c.getItemMeta().getDisplayName().equals("§aEmerald Block Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.EMERALD_BLOCK, 1));
							
							p.sendMessage("§fActivated your §aEmerald Block Hat§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §a§lEmerald VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.MELON_BLOCK && c.getItemMeta().getDisplayName().equals("§2Melon Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.MELON_BLOCK, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §2Melon Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 100){
								
								try {
									plugin.removeVIPPoints(p, 100);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsMelon.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Melon", "melon", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §2Melon Hat§9 for§b 100 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.GLASS && c.getItemMeta().getDisplayName().equals("§fGlass Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.GLASS, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §fGlass Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 150){
								
								try {
									plugin.removeVIPPoints(p, 150);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsGlass.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Glass", "glass", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fGlass Hat§9 for§b 150 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.STAINED_GLASS && c.getItemMeta().getDisplayName().equals("§eYellow Stained Glass Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.STAINED_GLASS, 1);
							item.setDurability((short) 4);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §eYellow Stained Glass Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsYellowGlass.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-YellowGlass", "yellowglass", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §eYellow Stained Glass Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MYCEL && c.getItemMeta().getDisplayName().equals("§7Mycelium Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.MYCEL, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §7Mycelium Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 75){
								
								try {
									plugin.removeVIPPoints(p, 75);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsMycelium.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Mycelium", "mycelium", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Mycelium Hat§9 for§b 75 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEAVES && c.getItemMeta().getDisplayName().equals("§2Leaves Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.LEAVES, 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §2Leaves Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsLeaves.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-Leaves", "leaves", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §2Leaves Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.DIAMOND_ORE && c.getItemMeta().getDisplayName().equals("§bDiamond Ore Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_ORE, 1));
							
							p.sendMessage("§fActivated your §bDiamond Ore Hat§f!");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §b§lDiamond VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.STAINED_GLASS && c.getItemMeta().getDisplayName().equals("§6Orange Stained Glass Hat")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							ItemStack item = new ItemStack(Material.STAINED_GLASS, 1);
							item.setDurability((short) 1);
							
							p.getInventory().setHelmet(item);
							
							p.sendMessage("§fActivated your §6Orange Stained Glass Hat§f!");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 125){
								
								try {
									plugin.removeVIPPoints(p, 125);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.hatsOrangeGlass.put(p.getName(), 1);
								plugin.insertHats(p.getName(), "Hats-OrangeGlass", "orangeglass", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §6Orange Stained Glass Hat§9 for§b 125 VIP Points§9!");
								p.openInventory(Hats.getHatsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.ENDER_CHEST){
						p.openInventory(CosmeticPerks.getCosmeticPerks(p));
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					}
					if(c.getType() == Material.FIRE){
						p.getInventory().setHelmet(null);
						p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
						p.closeInventory();
						p.sendMessage("§fYour Current Hat has been §c§lREMOVED§f!");
					}
				}
				if(e.getInventory().getName().equals("§0§lTrails")){
					e.setCancelled(true);
					
					if(c.getType() == Material.FIREWORK && c.getItemMeta().getDisplayName().equals("§cFireWork Spark Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §cFireWork Spark Trail§f!");
							Hub.trail.put(p, "FireWork");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 400){
								
								try {
									plugin.removeVIPPoints(p, 400);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailsfirework.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "FIREWORK", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §cFireWork Spark Trail§9 for§b 400 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.NETHER_STALK && c.getItemMeta().getDisplayName().equals("§cHeart Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §cHeart Trail§f!");
							Hub.trail.put(p, "Hearts");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 300){
								
								try {
									plugin.removeVIPPoints(p, 300);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailshearts.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "HEARTS", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §cHearts Trail§9 for§b 300 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.TNT && c.getItemMeta().getDisplayName().equals("§4TNT Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §4TNT Trail§f!");
							Hub.trail.put(p, "TNT");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailsexplode.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "EXPLODE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §4TNT Trail§9 for§b 300 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.COAL && c.getItemMeta().getDisplayName().equals("§8Angry Villager Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §8Villager Trail§f!");
							Hub.trail.put(p, "AngryVillager");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 400){
								
								try {
									plugin.removeVIPPoints(p, 400);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailsangryvillager.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "ANGRYVILLAGER", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Angry Villager Trail§9 for§b 400 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.SLIME_BALL && c.getItemMeta().getDisplayName().equals("§aSlime Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §aSlime Trail§f!");
							Hub.trail.put(p, "Slime");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 275){
								
								try {
									plugin.removeVIPPoints(p, 275);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailsslime.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "SLIME", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §aSlime Trail§9 for§b 275 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§0Smoke Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §0Smoke Trail§f!");
							Hub.trail.put(p, "Smoke");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 325){
								
								try {
									plugin.removeVIPPoints(p, 325);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailssmoke.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "SMOKE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §0Smoke Trail§9 for§b 325 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§5Witch Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §5Witch Trail§f!");
							Hub.trail.put(p, "Witch");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 350){
								
								try {
									plugin.removeVIPPoints(p, 350);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailswitch.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "WITCH", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §5Witch Trail§9 for§b 350 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.DIAMOND_SWORD && c.getItemMeta().getDisplayName().equals("§bCrit Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §bCrit Trail§f!");
							Hub.trail.put(p, "Crit");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 375){
								
								try {
									plugin.removeVIPPoints(p, 375);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailscrit.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "CRIT", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §bCrit Trail§9 for§b 375 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.WATER_BUCKET && c.getItemMeta().getDisplayName().equals("§9Water Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §9Water Trail§f!");
							Hub.trail.put(p, "Water");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 425){
								
								try {
									plugin.removeVIPPoints(p, 425);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailswater.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "WATER", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §9Water Trail§9 for§b 425 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.NOTE_BLOCK && c.getItemMeta().getDisplayName().equals("§dMusic Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §dMusic Trail§f!");
							Hub.trail.put(p, "Music");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 625){
								
								try {
									plugin.removeVIPPoints(p, 625);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailsnote.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "MUSIC", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §dMusic Trail§9 for§b 625 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.SNOW_BALL && c.getItemMeta().getDisplayName().equals("§fSnow Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §fSnow Trail§f!");
							Hub.trail.put(p, "Snow");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailssnow.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "SNOW", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fSnow Trail§9 for§b 475 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.ENCHANTMENT_TABLE && c.getItemMeta().getDisplayName().equals("§1Enchantment Table Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §1Enchantment Table Trail§f!");
							Hub.trail.put(p, "EnchantmentTable");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 400){
								
								try {
									plugin.removeVIPPoints(p, 400);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailsenchantmenttable.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "ETABLE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §1Enchantment Table Trail§9 for§b 400 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.WEB && c.getItemMeta().getDisplayName().equals("§fBubble Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §fBubble Trail§f!");
							Hub.trail.put(p, "Bubble");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 375){
								
								try {
									plugin.removeVIPPoints(p, 375);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailsbubbles.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "BUBBLE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fBubble Trail§9 for§b 375 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MOB_SPAWNER && c.getItemMeta().getDisplayName().equals("§7Mob Spawner Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §7Mob Spawner Trail§f!");
							Hub.trail.put(p, "MobSpawner");
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 525){
								
								try {
									plugin.removeVIPPoints(p, 525);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.trailsmobspawner.put(p.getName(), 1);
								plugin.insertTrail(p.getName(), "MOBSPAWNER", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Mob Spawner Trail§9 for§b 525 VIP Points§9!");
								p.openInventory(Trails.getTrailsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.EMERALD && c.getItemMeta().getDisplayName().equals("§aHappy Villager Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §aHappy Villager Trail§f!");
							Hub.trail.put(p, "HappyVillager");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §a§lEmerald VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§3Magic Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §3Magic Trail§f!");
							Hub.trail.put(p, "Magic");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §7§lIron VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.LAVA_BUCKET && c.getItemMeta().getDisplayName().equals("§6Lava Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §6Lava Trail§f!");
							Hub.trail.put(p, "Lava");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §b§lDiamond VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.REDSTONE && c.getItemMeta().getDisplayName().equals("§4Rainbow Trail")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							
							p.sendMessage("§fActivated your §4Rainbow Trail§f!");
							Hub.trail.put(p, "Rainbow");
							
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §6§lGold VIP§c§o to use this!");
						}
					}
					if(c.getType() == Material.ENDER_CHEST){
						p.openInventory(CosmeticPerks.getCosmeticPerks(p));
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					}
					if(c.getType() == Material.FIRE){
						if(Hub.trail.containsKey(p)){
							Hub.trail.remove(p);
						}
						p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
						p.closeInventory();
						p.sendMessage("§fYour Current Trail has been §c§lREMOVED§f!");
					}
				}
				if(e.getInventory().getName().equals("§0§lChatColors")){
					e.setCancelled(true);
					
					if(c.getType() == Material.REDSTONE && c.getItemMeta().getDisplayName().equals("§4Dark Red ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §4Dark Red ChatColor§f!");
							Hub.chatcolor.put(p, "4");
							try {
								plugin.setChatColor(p, "4");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolordarkred.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "DARKRED", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §4Dark Red ChatColor§9 for§b 475 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§aLight Green ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §aLight Green ChatColor§f!");
							Hub.chatcolor.put(p, "a");
							try {
								plugin.setChatColor(p, "a");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 575){
								
								try {
									plugin.removeVIPPoints(p, 575);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolorlightgreen.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "LIGHTGREEN", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §aLight Green ChatColor§9 for§b 575 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§8Dark Gray ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §8Dark Gray ChatColor§f!");
							Hub.chatcolor.put(p, "8");
							try {
								plugin.setChatColor(p, "8");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolordarkgray.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "DARKGRAY", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Dark Gray ChatColor§9 for§b 250 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§cRed ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §cRed ChatColor§f!");
							Hub.chatcolor.put(p, "c");
							try {
								plugin.setChatColor(p, "c");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 650){
								
								try {
									plugin.removeVIPPoints(p, 650);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolorred.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "RED", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §cRed ChatColor§9 for§b 650 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§fWhite ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §fWhite ChatColor§f!");
							Hub.chatcolor.put(p, "f");
							try {
								plugin.setChatColor(p, "f");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 500){
								
								try {
									plugin.removeVIPPoints(p, 500);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolorwhite.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "WHITE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fWhite ChatColor§9 for§b 500 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§bLight Blue ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §bLight Blue ChatColor§f!");
							Hub.chatcolor.put(p, "b");
							try {
								plugin.setChatColor(p, "b");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 700){
								
								try {
									plugin.removeVIPPoints(p, 700);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolorlightblue.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "LIGHTBLUE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §bLight Blue ChatColor§9 for§b 700 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§dPink ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §dPink ChatColor§f!");
							Hub.chatcolor.put(p, "d");
							try {
								plugin.setChatColor(p, "d");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 525){
								
								try {
									plugin.removeVIPPoints(p, 525);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolorpink.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "PINK", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §dPink ChatColor§9 for§b 525 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§9Blue ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §9Blue ChatColor§f!");
							Hub.chatcolor.put(p, "9");
							try {
								plugin.setChatColor(p, "9");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolorblue.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "BLUE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §9Blue ChatColor§9 for§b 475 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§1Dark Blue ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §1Dark Blue ChatColor§f!");
							Hub.chatcolor.put(p, "1");
							try {
								plugin.setChatColor(p, "1");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 350){
								
								try {
									plugin.removeVIPPoints(p, 350);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolordarkblue.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "DARKBLUE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §1Dark Blue ChatColor§9 for§b 350 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§7Gray ChatColor")){
						
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
						
						p.sendMessage("§fActivated your §7Gray ChatColor§f!");
						Hub.chatcolor.put(p, "7");
						try {
							plugin.setChatColor(p, "7");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§2Green ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §2Green ChatColor§f!");
							Hub.chatcolor.put(p, "2");
							try {
								plugin.setChatColor(p, "2");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolorgreen.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "GREEN", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §2Green ChatColor§9 for§b 475 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§0Black ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §0Black ChatColor§f!");
							Hub.chatcolor.put(p, "0");
							try {
								plugin.setChatColor(p, "0");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
							if(Hub.points.get(p.getName()) >= 200){
								
								try {
									plugin.removeVIPPoints(p, 200);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.chatcolorblack.put(p.getName(), 1);
								plugin.insertChatColors(p.getName(), "BLACK", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §0Black ChatColor§9 for§b 200 VIP Points§9!");
								p.openInventory(ChatColors.getChatColors(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§eYellow ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §eYellow ChatColor§f!");
							Hub.chatcolor.put(p, "e");
							try {
								plugin.setChatColor(p, "e");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
								
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §6§lGold VIP§c§o to use this!");
							
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§6Orange ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §6Orange ChatColor§f!");
							Hub.chatcolor.put(p, "6");
							try {
								plugin.setChatColor(p, "6");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
								
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §a§lEmerald VIP§c§o to use this!");
							
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§5Purple ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §5Purple ChatColor§f!");
							Hub.chatcolor.put(p, "5");
							try {
								plugin.setChatColor(p, "5");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
								
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §7§lIron VIP§c§o to use this!");
							
						}
					}
					if(c.getType() == Material.INK_SACK && c.getItemMeta().getDisplayName().equals("§3Cyan ChatColor")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 5, 1);
							
							p.sendMessage("§fActivated your §3Cyan ChatColor§f!");
							Hub.chatcolor.put(p, "3");
							try {
								plugin.setChatColor(p, "3");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						}
						else{
								
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§c§oYou need to be §b§lDiamond VIP§c§o to use this!");
							
						}
					}
					if(c.getType() == Material.ENDER_CHEST){
						p.openInventory(CosmeticPerks.getCosmeticPerks(p));
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					}
				}
				if(e.getInventory().getName().equals("§0§lWardrobe")){
					e.setCancelled(true);
					
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§fWhite Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §fWhite Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§fWhite Armor");
								itemmeta.setColor(Color.WHITE);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§fWhite Armor");
								itemmeta.setColor(Color.WHITE);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§fWhite Armor");
								itemmeta.setColor(Color.WHITE);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.white.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "WHITE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fWhite Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§9Blue Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §9Blue Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§9Blue Armor");
								itemmeta.setColor(Color.BLUE);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§9Blue Armor");
								itemmeta.setColor(Color.BLUE);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§9Blue Armor");
								itemmeta.setColor(Color.BLUE);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.blue.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "BLUE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §9Blue Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§2Green Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §2Green Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§2Green Armor");
								itemmeta.setColor(Color.GREEN);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§2Green Armor");
								itemmeta.setColor(Color.GREEN);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§2Green Armor");
								itemmeta.setColor(Color.GREEN);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.green.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "GREEN", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §2Green Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§0Black Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §0Black Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§0Black Armor");
								itemmeta.setColor(Color.BLACK);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§0Black Armor");
								itemmeta.setColor(Color.BLACK);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§0Black Armor");
								itemmeta.setColor(Color.BLACK);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.black.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "BLACK", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §0Black Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§bLight Blue Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §bLight Blue Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§bLight Blue Armor");
								itemmeta.setColor(Color.AQUA);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§bLight Blue Armor");
								itemmeta.setColor(Color.AQUA);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§bLight Blue Armor");
								itemmeta.setColor(Color.AQUA);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.lightblue.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "LIGHTBLUE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §bLight Blue Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§dPink Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §dPink Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§dPink Armor");
								itemmeta.setColor(Color.FUCHSIA);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§dPink Armor");
								itemmeta.setColor(Color.FUCHSIA);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§dPink Armor");
								itemmeta.setColor(Color.FUCHSIA);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.pink.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "PINK", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §dPink Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§aLight Green Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §aLight Green Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§aLight Green Armor");
								itemmeta.setColor(Color.LIME);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§aLight Green Armor");
								itemmeta.setColor(Color.LIME);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§aLight Green Armor");
								itemmeta.setColor(Color.LIME);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.lightgreen.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "LIGHTGREEN", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §aLight Green Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§1Dark Blue Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §1Dark Blue Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§1Dark Blue Armor");
								itemmeta.setColor(Color.NAVY);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§1Dark Blue Armor");
								itemmeta.setColor(Color.NAVY);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§1Dark Blue Armor");
								itemmeta.setColor(Color.NAVY);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.darkblue.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "DARKBLUE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §1Dark Blue Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§5Purple Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §5Purple Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§5Purple Armor");
								itemmeta.setColor(Color.PURPLE);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§5Purple Armor");
								itemmeta.setColor(Color.PURPLE);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§5Purple Armor");
								itemmeta.setColor(Color.PURPLE);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.purple.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "PURPLE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §5Purple Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§6Orange Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §6Orange Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§6Orange Armor");
								itemmeta.setColor(Color.ORANGE);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§6Orange Armor");
								itemmeta.setColor(Color.ORANGE);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§6Orange Armor");
								itemmeta.setColor(Color.ORANGE);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.orange.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "ORANGE", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §6Orange Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§cRed Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §cRed Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§cRed Armor");
								itemmeta.setColor(Color.RED);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§cRed Armor");
								itemmeta.setColor(Color.RED);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§cRed Armor");
								itemmeta.setColor(Color.RED);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.red.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "RED", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §cRed Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§3Cyan Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §3Cyan Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§3Cyan Armor");
								itemmeta.setColor(Color.TEAL);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§3Cyan Armor");
								itemmeta.setColor(Color.TEAL);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§3Cyan Armor");
								itemmeta.setColor(Color.TEAL);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.cyan.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "CYAN", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §3Cyan Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§eYellow Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §eYellow Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§eYellow Armor");
								itemmeta.setColor(Color.YELLOW);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§eYellow Armor");
								itemmeta.setColor(Color.YELLOW);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§eYellow Armor");
								itemmeta.setColor(Color.YELLOW);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.yellow.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "YELLOW", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §eYellow Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.LEATHER_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§7Gray Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §7Gray Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§7Gray Armor");
								itemmeta.setColor(Color.GRAY);
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§7Gray Armor");
								itemmeta.setColor(Color.GRAY);
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
								LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
								itemmeta.setDisplayName("§7Gray Armor");
								itemmeta.setColor(Color.GRAY);
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							if(Hub.points.get(p.getName()) >= 250){
								
								try {
									plugin.removeVIPPoints(p, 250);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.gray.put(p.getName(), 1);
								plugin.insertWardrobe(p.getName(), "GRAY", 1);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Gray Armor§9 for§b 250 VIP Points§9!");
								p.openInventory(Wardrobe.getWardrobeInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.IRON_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§7Iron Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §7Iron Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§7Iron Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.IRON_LEGGINGS, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§7Iron Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.IRON_BOOTS, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§7Iron Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							p.sendMessage("§c§oYou need to be§7§l Iron VIP§c§o to use this!");
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
						}
					}
					if(c.getType() == Material.GOLD_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§6Gold Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §6Gold Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.GOLD_CHESTPLATE, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§6Gold Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.GOLD_LEGGINGS, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§6Gold Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.GOLD_BOOTS, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§6Gold Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							p.sendMessage("§c§oYou need to be§6§l Gold VIP§c§o to use this!");
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
						}
					}
					if(c.getType() == Material.DIAMOND_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§bDiamond Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §bDiamond Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§bDiamond Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§bDiamond Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§bDiamond Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							p.sendMessage("§c§oYou need to be§b§l Diamond VIP§c§o to use this!");
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
						}
					}
					if(c.getType() == Material.CHAINMAIL_CHESTPLATE && c.getItemMeta().getDisplayName().equals("§7Chainmail Armor")){
						
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							p.closeInventory();
							p.getInventory().setChestplate(null);
							p.getInventory().setLeggings(null);
							p.getInventory().setBoots(null);
							p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
							
							p.sendMessage("§fActivated your §7Chainmail Armor§f!");
							
							{
								ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§7Chainmail Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setChestplate(item);
							}
							{
								ItemStack item = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§7Chainmail Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setLeggings(item);
							}
							{
								ItemStack item = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
								ItemMeta itemmeta = item.getItemMeta();
								itemmeta.setDisplayName("§7Chainmail Armor");
								item.setItemMeta(itemmeta);
								p.getInventory().setBoots(item);
							}
						}
						else{
							p.sendMessage("§c§oYou need to be§a§l Emerald VIP§c§o to use this!");
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
						}
					}
					
					if(c.getType() == Material.FIRE){

						p.sendMessage("§fCurrent Armor has been §c§lREMOVED§f!");
						p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
						p.closeInventory();
						p.getInventory().setChestplate(null);
						p.getInventory().setLeggings(null);
						p.getInventory().setBoots(null);
					}
					if(c.getType() == Material.ENDER_CHEST){
						p.openInventory(CosmeticPerks.getCosmeticPerks(p));
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					}
				}
				if(e.getInventory().getName().equals("§0§lPets")){
					e.setCancelled(true);
					
					if(c.getType() == Material.FIRE){
						if(Hub.ownerpets.containsKey(p)){
							p.sendMessage("§fPet §c§lDISABLED§f!");
							p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
							p.closeInventory();
							
							for(Entity en : Bukkit.getWorld("Hub").getEntities()){
								if(en.getUniqueId() == Hub.ownerpets.get(p)){
									if(en instanceof LivingEntity){
										en.remove();
										Hub.owners.remove(Hub.ownerpets.get(p));
										Hub.ownerpets.remove(p);
									}
								}
							}
						}
						else{
							p.sendMessage("§fYou don't have a Pet activated!");
						}
					}
					if(c.getType() == Material.ENDER_CHEST){
						p.openInventory(CosmeticPerks.getCosmeticPerks(p));
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					}
					
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§4Mushroom Cow Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomMushroomCow cow = new CustomMushroomCow(nmsWorld);
						    cow.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(cow);
							cow.setAge(1);
							cow.setCustomName(Hub.mushroomcow.get(p.getName()));
							cow.setCustomNameVisible(true);
							
							final UUID petID = cow.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.COW_HURT, 5, 1);
							p.sendMessage("§fActivated your §4Mushroom Cow Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.mushroomcow.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "MUSHROOMCOW", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §4Mushroom Cow Pet§9 for§b 475 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§dPig Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomPig pig = new CustomPig(nmsWorld);
						    pig.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(pig);
							pig.setAge(1);
							pig.setCustomName(Hub.pig.get(p.getName()));
							pig.setCustomNameVisible(true);
							
							final UUID petID = pig.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.PIG_DEATH, 5, 1);
							p.sendMessage("§fActivated your §dPig Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 425){
								
								try {
									plugin.removeVIPPoints(p, 425);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.pig.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "PIG", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §dPig Pet§9 for§b 425 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§7Wolf Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomWolf wolf = new CustomWolf(nmsWorld);
						    wolf.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(wolf);
							wolf.setAge(1);
							wolf.setCustomName(Hub.wolf.get(p.getName()));
							wolf.setCustomNameVisible(true);
							
							final UUID petID = wolf.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.WOLF_BARK, 5, 1);
							p.sendMessage("§fActivated your §7Wolf Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.wolf.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "WOLF", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Wolf Pet§9 for§b 475 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§fSheep Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomSheep sheep = new CustomSheep(nmsWorld);
						    sheep.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(sheep);
							sheep.setAge(1);
							sheep.setCustomName(Hub.sheep.get(p.getName()));
							sheep.setCustomNameVisible(true);
							
							final UUID petID = sheep.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SHEEP_SHEAR, 5, 1);
							p.sendMessage("§fActivated your §fSheep Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 425){
								
								try {
									plugin.removeVIPPoints(p, 425);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.sheep.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "SHEEP", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §fSheep Pet§9 for§b 425 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§6Horse Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							

							Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
							horse.setAdult();
							horse.setCustomName(Hub.horse.get(p.getName()));
							horse.setCustomNameVisible(true);
							horse.setTamed(true);
							horse.setRemoveWhenFarAway(false);
							horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
							horse.setColor(Horse.Color.BROWN);
							horse.setStyle(Style.WHITE);
							
							final UUID petID = horse.getUniqueId();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.HORSE_HIT, 5, 1);
							p.sendMessage("§fActivated your §6Horse Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 525){
								
								try {
									plugin.removeVIPPoints(p, 525);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.horse.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "HORSE", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §6Horse Pet§9 for§b 525 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§cMagma Cube Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomMagmaCube magmacube = new CustomMagmaCube(nmsWorld);
						    magmacube.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(magmacube);
							magmacube.setSize(3);
							magmacube.setCustomName(Hub.magmacube.get(p.getName()));
							magmacube.setCustomNameVisible(true);
							
							final UUID petID = magmacube.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SLIME_WALK, 5, 1);
							p.sendMessage("§fActivated your §cMagma Cube Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 500){
								
								try {
									plugin.removeVIPPoints(p, 500);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.magmacube.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "MAGMACUBE", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §cMagma Cube Pet§9 for§b 500 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§aSlime Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomSlime slime = new CustomSlime(nmsWorld);
						    slime.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(slime);
							slime.setSize(3);
							slime.setCustomName(Hub.slime.get(p.getName()));
							slime.setCustomNameVisible(true);
							
							final UUID petID = slime.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SLIME_WALK, 5, 1);
							p.sendMessage("§fActivated your §aSlime Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 475){
								
								try {
									plugin.removeVIPPoints(p, 475);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.slime.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "SLIME", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §aSlime Pet§9 for§b 475 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§8Cow Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomCow cow = new CustomCow(nmsWorld);
						    cow.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(cow);
							cow.setAge(1);
							cow.setCustomName(Hub.cow.get(p.getName()));
							cow.setCustomNameVisible(true);
							
							final UUID petID = cow.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.COW_HURT, 5, 1);
							p.sendMessage("§fActivated your §8Cow Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 425){
								
								try {
									plugin.removeVIPPoints(p, 425);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.cow.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "COW", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §8Cow Pet§9 for§b 425 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§7Silverfish Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomSilverfish silverfish = new CustomSilverfish(nmsWorld);
						    silverfish.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(silverfish);
							silverfish.setCustomName(Hub.silverfish.get(p.getName()));
							silverfish.setCustomNameVisible(true);
							
							final UUID petID = silverfish.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SILVERFISH_HIT, 5, 1);
							p.sendMessage("§fActivated your §7Silverfish Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 450){
								
								try {
									plugin.removeVIPPoints(p, 450);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.silverfish.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "SILVERFISH", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought the §7Silverfish Pet§9 for§b 450 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
					if(c.getType() == Material.MONSTER_EGG && c.getItemMeta().getDisplayName().equals("§eOcelot Pet")){
						if(c.getItemMeta().getLore().contains("§a§lUnlocked")){
							if(Hub.ownerpets.containsKey(p)){
								
								
								for(Entity en : Bukkit.getWorld("Hub").getEntities()){
									if(en.getUniqueId() == Hub.ownerpets.get(p)){
										if(en instanceof LivingEntity){
											en.remove();
											Hub.owners.remove(Hub.ownerpets.get(p));
											Hub.ownerpets.remove(p);
										}
									}
								}
							}
							
							Location loc = p.getLocation();
							
						    net.minecraft.server.v1_7_R3.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
						    CustomOcelot ocelot = new CustomOcelot(nmsWorld);
						    ocelot.setPosition(loc.getX(), loc.getY(), loc.getZ());
						    nmsWorld.addEntity(ocelot);
							ocelot.setAge(1);
							ocelot.setCustomName(Hub.ocelot.get(p.getName()));
							ocelot.setCustomNameVisible(true);
							
							final UUID petID = ocelot.getUniqueID();
							Hub.owners.put(petID, p);
							Hub.ownerpets.put(p, petID);
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.CAT_MEOW, 5, 1);
							p.sendMessage("§fActivated your §eOcelot Pet§f!");
						}
						else{
							if(Hub.points.get(p.getName()) >= 450){
								
								try {
									plugin.removeVIPPoints(p, 450);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								Hub.ocelot.put(p.getName(), p.getName() + "s Pet");
								plugin.insertPets(p.getName(), "OCELOT", p.getName() + "s Pet");
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
								p.sendMessage("§9You've bought te §eOcelot Pet§9 for§b 450 VIP Points§9!");
								p.openInventory(Pets.getPetsInv(p));
							}
							else{
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
					}
				}
				
				if(!p.isOp() && !p.getWorld().getName().equals("SkyBlockWorld")){
					e.setCancelled(true);
				}
			}
			}catch(Exception ex){
				
			}
		}
	}
	
	public static double getDistance(Location l1, Location l2){
		double distance = l1.distance(l2);
		return distance;
	}
	
	public static void navigate(LivingEntity le, Location l, double v){
		try{
			
			Object entityLiving = ReflectionUtil.getMethod("getHandle", le.getClass(), 0).invoke(le);
			Object nav = ReflectionUtil.getMethod("getNavigation", entityLiving.getClass(), 0).invoke(entityLiving);
			ReflectionUtil.getMethod("a", nav.getClass(), 4).invoke(nav, l.getX(), l.getY(), l.getZ(), v);
			
		}catch(Exception ex){
			
		}
	}
	
    public void teleportServer(Player p, String server){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
 
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException eee) {
         
        }
 
        p.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray());
    }
}
