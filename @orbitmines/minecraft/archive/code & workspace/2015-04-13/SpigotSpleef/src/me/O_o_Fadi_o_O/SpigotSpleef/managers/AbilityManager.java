package me.O_o_Fadi_o_O.SpigotSpleef.managers;

import me.O_o_Fadi_o_O.SpigotSpleef.utils.Ability;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.AbilityMotivier;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.KitItemStack;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Message;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.MessageName;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AbilityManager {

	public static void loadAbilityMotiviers(){
		for(String ability : ConfigManager.config.getConfigurationSection("AbilityMotiviers").getKeys(false)){
			try{
				AbilityMotivier motivier = new AbilityMotivier(Ability.valueOf(ability));
				motivier.setCooldown(loadCooldown(ability));
				motivier.setPower(loadPower(ability));
				motivier.setSound(loadSound(ability));
				motivier.setMaxAmount(loadMaxAmount(ability));
				StorageManager.abilities.add(motivier);
			}
			catch(IllegalArgumentException ex){
				Bukkit.getLogger().warning("");
				Bukkit.getLogger().warning("[SpigotSpleef] AbilityMotivier cannot find the Ability '" + ability + "'!");
				Bukkit.getLogger().warning("");
			}
		}
	}
	
	private static int loadCooldown(String path){
		if(ConfigManager.config.contains("AbilityMotiviers." + path + ".Cooldown")){
			return ConfigManager.config.getInt("AbilityMotiviers." + path + ".Cooldown");
		}
		else{
			return 5;
		}
	}
	
	private static int loadPower(String path){
		if(ConfigManager.config.contains("AbilityMotiviers." + path + ".Power")){
			return ConfigManager.config.getInt("AbilityMotiviers." + path + ".Power");
		}
		else{
			return 2;
		}
	}
	
	private static Sound loadSound(String path){
		String sound = ConfigManager.config.getString("AbilityMotiviers." + path + ".Sound");
		if(sound != null){
			try{
				return Sound.valueOf(sound);
			}catch(IllegalArgumentException ex){
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	private static int loadMaxAmount(String path){
		if(ConfigManager.config.contains("AbilityMotiviers." + path + ".MaxAmount")){
			return ConfigManager.config.getInt("AbilityMotiviers." + path + ".MaxAmount");
		}
		else{
			return 3;
		}
	}
	
	public static void checkJump(SpleefPlayer sp, KitItemStack item){
		long last = 0;
		if(sp.getCooldown(item) != null){
			last = sp.getCooldown(item);
		}
		
		int cooldown = 1000;
		double power = 1.00;
		AbilityMotivier motivier = AbilityMotivier.getMotivier(Ability.JUMP);
		
		if(motivier != null){
			cooldown = cooldown * motivier.getCooldown();
			
			if(motivier.getPower() != 0){
				power = power + (motivier.getPower() / 10); 
			}
		}
		else{
			cooldown = cooldown * 5;
		}
				
		if(System.currentTimeMillis() - last >= cooldown){
			if(motivier != null && motivier.getSound() != null){
				sp.getPlayer().playSound(sp.getPlayer().getLocation(), motivier.getSound(), 5, 1);
			}
			sp.getPlayer().setVelocity(sp.getPlayer().getLocation().getDirection().multiply(power));
			sp.setCooldown(item, System.currentTimeMillis());
			
			Message m = Message.getMessage(MessageName.JUMP_USAGE);
			m.updateForPlayer(sp.getPlayer(), null);
			m.send(sp.getPlayer());
		}
		else{
			int cooldowntime = (int) (cooldown / 1000 - ((System.currentTimeMillis() - last) / 1000));
			Message m = Message.getMessage(MessageName.JUMP_ON_COOLDOWN);
			m.updateForPlayer(sp.getPlayer(), null);
			m.replace("%cooldown%", "" + cooldowntime);
			m.send(sp.getPlayer());
		}
	}

	public static void checkThrowEgg(SpleefPlayer sp, KitItemStack item){
		long last = 0;
		if(sp.getCooldown(item) != null){
			last = sp.getCooldown(item);
		}
		
		int cooldown = 1000;
		AbilityMotivier motivier = AbilityMotivier.getMotivier(Ability.THROW_EGG);
		
		if(motivier != null){
			cooldown = cooldown * motivier.getCooldown();
		}
		else{
			cooldown = cooldown * 5;
		}
				
		if(System.currentTimeMillis() - last >= cooldown){
			if(motivier != null && motivier.getSound() != null){
				sp.getPlayer().playSound(sp.getPlayer().getLocation(), motivier.getSound(), 5, 1);
			}
			Egg egg = (Egg) sp.getPlayer().launchProjectile(Egg.class);
			sp.addThrownEgg(egg);
			StorageManager.projectiles.put(egg, sp.getArena());
			
			sp.setCooldown(item, System.currentTimeMillis());
		}
		else{
			int cooldowntime = (int) (cooldown / 1000 - ((System.currentTimeMillis() - last) / 1000));
			Message m = Message.getMessage(MessageName.THROW_EGG_ON_COOLDOWN);
			m.replace("&", "§");
			m.replace("%cooldown%", "" + cooldowntime);
			m.send(sp.getPlayer());
		}
	}
	
	public static void checkInstantTNT(SpleefPlayer sp, Block b, KitItemStack item){
		TNTPrimed tnt = (TNTPrimed) b.getWorld().spawnEntity(b.getLocation().add(0.5, 1, 0.5), EntityType.PRIMED_TNT);
		tnt.setFuseTicks((int) (20 * 1.5));
		sp.addInstantTNT(tnt);
		StorageManager.instanttnt.add(tnt);
	}
	
	public static void checkRangeSmall(SpleefPlayer sp, Projectile proj, KitItemStack item){
		sp.getArena().addProjectileSmall(proj);
		StorageManager.projectiles.put(proj, sp.getArena());
	}
	
	public static void checkRangeMedium(SpleefPlayer sp, Projectile proj, KitItemStack item){
		sp.getArena().addProjectileMedium(proj);
		StorageManager.projectiles.put(proj, sp.getArena());
	}
	
	public static void checkRangeBig(SpleefPlayer sp, Projectile proj, KitItemStack item){
		sp.getArena().addProjectileBig(proj);
		StorageManager.projectiles.put(proj, sp.getArena());
	}
	
	public static void checkTeleportArrows(SpleefPlayer sp, Projectile proj, KitItemStack item){
		sp.getArena().addTeleportArrow(proj);
		StorageManager.projectiles.put(proj, sp.getArena());
	}
	
	public static ItemStack handlePlusOne(Ability ability, SpleefPlayer sp, KitItemStack item, int index, ItemStack newi, ItemMeta meta){

		long last = 0;
		if(sp.getCooldown(item) != null){
			last = sp.getCooldown(item);
		}
		
		int cooldown = 1000;
		int maxamount = 64;
		AbilityMotivier motivier = AbilityMotivier.getMotivier(ability);
		
		if(motivier != null){
			if(motivier.getCooldown() != 0){
				cooldown = cooldown * motivier.getCooldown();
			}
			if(motivier.getMaxAmount() != 0){
				maxamount = motivier.getMaxAmount();
			}
		}
		else{
			cooldown = cooldown * 5;
		}
		
		ItemStack itemstack = sp.getPlayer().getInventory().getItem(index);
		
		if(System.currentTimeMillis() - last >= cooldown){					
			if(itemstack != null){
				if(itemstack.getAmount() != 64 && itemstack.getAmount() != maxamount){
					if(motivier != null && motivier.getSound() != null){
						sp.getPlayer().playSound(sp.getPlayer().getLocation(), motivier.getSound(), 5, 1);
					}
					
					newi.setAmount(itemstack.getAmount() +1);
				}
				else{
					newi.setAmount(itemstack.getAmount());
				}
			}
			else{
				if(motivier != null && motivier.getSound() != null){
					sp.getPlayer().playSound(sp.getPlayer().getLocation(), motivier.getSound(), 5, 1);
				}
				
				newi.setAmount(1);
			}
			sp.setCooldown(item, System.currentTimeMillis());
		}
		else{
			newi = itemstack;
			if(itemstack != null){
				meta = itemstack.getItemMeta();
			}
		}
		
		if(newi != null && meta != null){
			newi.setItemMeta(meta);
		}
		
		return newi;
	}
}
