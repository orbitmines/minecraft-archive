package fadidev.orbitmines.kitpvp.utils.enums;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum ArmorType {
	
	MOLTEN_ARMOR_I("§7Molten Armor I"),
	WITHER_ARMOR_I("§7Wither Armor I"),
	FIRE_TRAIL_I("§7Fire Trail I"),
	LIGHT_I("§7Light I");
	
	private OrbitMinesKitPvP kitPvP;
	private String name;
	
	ArmorType(String name){
		this.kitPvP = OrbitMinesKitPvP.getKitPvP();
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public List<String> addEnchantment(List<String> itemLore){
        itemLore.add(getName());
		return itemLore;
	}

    public void playEnchantment(OMPlayer ompD){
        playEnchantment(ompD, null);
    }
	
	public void playEnchantment(OMPlayer ompD, OMPlayer ompE){
		Player p = ompD.getPlayer();
		
		switch(this){
			case MOLTEN_ARMOR_I:
				ompD.addPotionEffect(PotionEffectType.BLINDNESS, 5, 0);
				ompD.addPotionEffect(PotionEffectType.SLOW, 5, 5);
				break;
			case WITHER_ARMOR_I:
				ompD.addPotionEffect(PotionEffectType.WITHER, 5, 1);
				break;
			case FIRE_TRAIL_I:
				final Block bl = p.getWorld().getBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
			
				if(bl.isEmpty()){
					bl.setType(Material.FIRE);
					
					new BukkitRunnable(){
						public void run(){
							bl.setType(Material.AIR);
						}
					}.runTaskLater(kitPvP, 40);
				}
				break;
			case LIGHT_I:
				Location l1 = new Location(p.getWorld(), p.getLocation().getBlockX() +1, p.getLocation().getBlockY() -1, p.getLocation().getBlockZ());
				Location l2 = new Location(p.getWorld(), p.getLocation().getBlockX() -1, p.getLocation().getBlockY() -1, p.getLocation().getBlockZ() -1);
				
				for(final Block b : WorldUtils.getBlocksBetween(l1, l2)){
					if(b.getType() != Material.COBBLESTONE){
						final Material Cb = b.getType();
					
						if(b.getType().equals(Material.WATER) || b.getType().equals(Material.STATIONARY_WATER)){
							b.setType(Material.COBBLESTONE);
						}
						removeBlock(p, b, Cb);
					}
				}
				break;
			default:
				break;
		}
	}
	
	public void removeBlock(final Player p, final Block b, final Material m){
		new BukkitRunnable(){
			public void run(){
				if(p.getWorld().getName().equals(b.getWorld().getName()) && p.getLocation().distance(b.getLocation()) >= 3){
					b.setType(m);
				}
				else{
					removeBlock(p, b, m);
				}
			}
		}.runTaskLater(kitPvP, 30);
	}
	
	public static List<ArmorType> getArmorTypes(KitPvPKit kit){
		List<ArmorType> types = new ArrayList<>();
		
		switch(kit){
			case BLAZE:
				types = Collections.singletonList(ArmorType.LIGHT_I);
				break;
			case DARKMAGE:
				types = Collections.singletonList(ArmorType.WITHER_ARMOR_I);
				break;
			case GRIMREAPER:
				types = Collections.singletonList(ArmorType.MOLTEN_ARMOR_I);
				break;
			case PYRO:
				types = Collections.singletonList(ArmorType.FIRE_TRAIL_I);
				break;
			default:
				break;
		}
		
		return types;
	}
}
