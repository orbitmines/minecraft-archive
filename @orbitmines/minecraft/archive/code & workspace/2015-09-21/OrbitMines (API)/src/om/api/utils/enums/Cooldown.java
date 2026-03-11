package om.api.utils.enums;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import om.api.handlers.ActionBar;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.cp.Gadget;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.inventory.ItemStack;

public enum Cooldown {
	
	PORTAL_USAGE(3, null, null),
	NPC_INTERACT(1, null, null),
	SGA_USAGE(180, Gadget.SNOWMAN_ATTACK.getName(), Gadget.SNOWMAN_ATTACK.getName()),
	PET_KITTY_CANNON_USAGE(1, "§e§lKitty Cannon", "§e§lKitty Cannon"),
	PET_LEAP_USAGE(4, "§8§lLeap", "§8§lLeap"),
	PET_SILVERFISH_BOMB_USAGE(6, "§7§lSilverfish Bomb", "§7§lSilverfish Bomb"),
	PET_MILK_EXPLOSION(4, "§f§lMilk Explosion", "§f§lMilk Explosion"),
	PET_BARK(3, "§6§lBark", "§6§lBark"),
	PET_JUMP(5, "§6§lSuper Jump", "§6§lSuper Jump"),
	PET_BABY_FIREWORK(1, "§c§lBaby Firework", "§c§lBaby Firework"),
	PET_WEBS(4, "§f§lWebs", "§f§lWebs"),
	PET_SPIDER_LAUNCHER(2, "§5§lSpider Launcher", "§5§lSpider Launcher"),
	PET_INK_BOMB(3, "§8§lInk Bomb", "§8§lInk Bomb"),
	SWAP_TELEPORTER(4, Gadget.SWAP_TELEPORTER.getName(), Gadget.SWAP_TELEPORTER.getName()),
	CREEPER_LAUNCHER(6, Gadget.CREEPER_LAUNCHER.getName(), Gadget.CREEPER_LAUNCHER.getName()),
	BOOK_EXPLOSION(7, Gadget.BOOK_EXPLOSION.getName(), Gadget.BOOK_EXPLOSION.getName()),

	MESSAGE(2, null, null),
	TELEPORTING(3, null, null),
	
	// KitPvP Cooldowns \\
	FIRE_SPELL_I(1, "§c§lFire Spell", "§cFireWand"),
	FIRE_SPELL_II(1, "§c§lFire Spell", "§cFireWand"),
	POTION_LAUNCHER_I(5, "§e§lPotion Launcher", "§ePotion Launcher"),
	WITHER_I(5, "§8§lNecromancer's Staff", "§8Necromancer's Staff"),
	HEALING_I(20, "§a§lHealing", "§bWeapon§a"),
	HEALING_II(20, "§a§lHealing", "§bWeapon§a"),
	BARRIER_I(120, "§d§lBarrier", "§dBarrier"),
	BARRIER_II(100, "§d§lBarrier", "§dBarrier"),
	TNT_I(7, "§4§lTNT Launcher", "§4TNT Launcher"),
	MAGIC_I(30, "§5§lMagic", "§bWeapon§5"),
	FISH_ATTACK_I(5, "§9§lFish Attack", "§9Fish Attack"),
	SHIELD_I(40, "§7§lShield", "§7Shield"),
	SHIELD_II(40, "§7§lShield", "§7Shield"),
	BLOCK_EXPLOSION_I(25, "§e§lBlock Explosion", "§eBlock Explosion"),
	UNDEATH_SUMMON_I(7, "§d§lSummon the Undeath", "§dSummon the Undeath"),
	PAINTBALLS_I(1, "§f§lPaintballs", "§bWeapon§f"),
	PAINTBALLS_I_USAGE(2, null, null),
	
	// Creative Cooldowns \\
	BROADCAST(300, null, null),
	
	// Survival Cooldowns \\
	PVP_CONFIRM(15, null, null),
	
	// SkyBlock Cooldowns \\
	NETHER_TELEPORTING(8, null, null),
	
	// Prison Cooldowns \\
	RESET_MINE(-1, null, null),
	STARTER_KIT(18000, null, null),
	
	// MiniGames Cooldowns \\
	FEATHER_ATTACK(6, "§f§lFeather Attack", "§f§lFeather Attack"),
	EGG_BOMB(7, "§f§lEgg Bomb", "§f§lEgg Bomb"),
	FIRE_SHIELD(12, "§f§lFire Shield", "§f§lFire Shield"),
	IRON_FIST(20, "§f§lIron Fist", "§f§lIron Fist"),
	
	WOOL_TRAIL(1, null, null);
	
	public static List<Cooldown> cooldowns = new ArrayList<Cooldown>();
	private int cooldown;
	private String name;
	private String itemName;
	
	Cooldown(int cooldown, String name, String itemName){
		this.cooldown = cooldown;
		this.name = name;
		this.itemName = itemName;
	}
	
	public int getCooldown(OMPlayer omp){
		switch(this){
			case RESET_MINE:
				if(omp.hasPerms(VIPRank.Diamond_VIP)){
					return 300 * 1000;
				}
				else{
					return 600 * 1000;
				}
			default:
				return cooldown * 1000;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getItemName() {
		return itemName;
	}

	public static List<Cooldown> getKitPvPCooldowns(){
		return Arrays.asList(FIRE_SPELL_I, FIRE_SPELL_II, POTION_LAUNCHER_I, WITHER_I, HEALING_I, HEALING_II, BARRIER_I, BARRIER_II, TNT_I, MAGIC_I, FISH_ATTACK_I, SHIELD_I, SHIELD_II, BLOCK_EXPLOSION_I, PAINTBALLS_I);
	}
	
	public static List<Cooldown> getCreativeCooldowns(){
		return Arrays.asList(MESSAGE, BROADCAST);
	}
	
	public static List<Cooldown> getMiniGameCooldowns(){
		return Arrays.asList(FEATHER_ATTACK, EGG_BOMB, FIRE_SHIELD, IRON_FIST);
	}
	
	public String getAction(){
		switch(this){
			case SWAP_TELEPORTER:
				return "§e§lLeft Click";
			default:
				return "§e§lRight Click";
		}
	}
	
	public static HashMap<OMPlayer, Double> prevdouble = new HashMap<OMPlayer, Double>();
	public void updateActionBar(OMPlayer omp){
		ItemStack item = omp.getPlayer().getItemInHand();
		
		if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && !getName().equals("")){
			boolean equals = false;
			
			if(item.getItemMeta().getDisplayName().endsWith(getName().replace("§l", "§n"))){
				equals = true;
			}
			else if(item.getItemMeta().getDisplayName().endsWith(getItemName())){
				equals = true;
			}
			else{}
			
			if(equals && omp.onCooldown(this)){
				double cooldown = getCooldown(omp) / 1000;
				double left = cooldown - ((System.currentTimeMillis() - omp.getCooldown(this)) / 1000);
				
				String format = "ss,S";
				if(left < 10){
					format = "s,S";
				}
				if(left > 60){
					format = "m: ss,S";
				}
				if(left > 600){
					format = "mm: ss,S";
				}
				String leftstring = (new SimpleDateFormat(format).format(new Date(getCooldown(omp) - (System.currentTimeMillis() - omp.getCooldown(this))))).replace(":", "m");
				leftstring = leftstring.substring(0, leftstring.indexOf(",") +2) + "s";
				
				String bar = "";
				if(leftstring.contains("m")){
					left = (Integer.parseInt(leftstring.substring(0, leftstring.indexOf("m"))) * 60) + Integer.parseInt(leftstring.substring(leftstring.indexOf("m") +2, leftstring.indexOf(","))) + (Double.parseDouble(leftstring.substring(leftstring.indexOf(",") +1, leftstring.indexOf(",") +2)) / 10);
				}
				else{	
					left = Integer.parseInt(leftstring.substring(0, leftstring.indexOf(","))) + (Double.parseDouble(leftstring.substring(leftstring.indexOf(",") +1, leftstring.indexOf(",") +2)) / 10);
				}
				double red = 40 - (((left / cooldown) * 100) / 2.5) + 2;
				
				/*
				 * Fix incorrect numbers;
				 */
				if(prevdouble.containsKey(omp) && (prevdouble.get(omp) - red) >= 1.1){
					red = prevdouble.get(omp);
				}
				else{
					prevdouble.put(omp, red);
				}
				
				bar += "§a|||||||||||||||||||||||||||||||||||||||| §8| §f" + leftstring + " §8| " + getName();
				bar = bar.substring(0, (int) red) + "§c" + bar.substring((int) red);
				
				ActionBar actionbar = new ActionBar(bar);
				actionbar.send(omp.getPlayer());
			}
			else{
				if(equals){
					ActionBar actionbar = new ActionBar(getAction() + " §8| " + getName());
					actionbar.send(omp.getPlayer());
				}
			}
		}
	}
}

