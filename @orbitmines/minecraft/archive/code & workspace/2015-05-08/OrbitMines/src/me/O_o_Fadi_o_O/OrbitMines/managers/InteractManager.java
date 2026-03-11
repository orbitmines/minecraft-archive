package me.O_o_Fadi_o_O.OrbitMines.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Database;
import me.O_o_Fadi_o_O.OrbitMines.utils.FireWork;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Title;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.CosmeticPerksInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.SettingsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.HubServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MindCraftColor;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Pet;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.MindCraftPlayer;
import net.minecraft.server.v1_8_R2.AttributeInstance;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.GenericAttributes;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class InteractManager {

	private PlayerInteractEvent e;
	private Player p;
	private OMPlayer omp;
	private ItemStack item;
	private Block b;
	private Action a;
	
	public InteractManager(PlayerInteractEvent e){
		this.e = e;
		this.p = e.getPlayer();
		this.omp = OMPlayer.getOMPlayer(p);
		this.item = e.getItem();
		this.b = e.getClickedBlock();
		this.a = e.getAction();
	}
	
	public void handleChickenPetEggBomb(){
		if(item.getType() == Material.EGG && item.getItemMeta().getDisplayName().equals("§7§nEgg Bomb")){
			e.setCancelled(true);
			omp.updateInventory();
			
			ServerStorage.eggbombs.add((Egg) p.launchProjectile(Egg.class));
		}
	}
	
	public void handleChickenPetAge(){
		if(item.getType() == Material.RAW_CHICKEN && item.getItemMeta().getDisplayName().equals("§c§nChange Age")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Chicken c = (Chicken) omp.getPet();
			
			if(c.isAdult()){
				c.setBaby();
				p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.CHICKEN) +"'s§7 Age§7 to a §7§lBaby§7!");
				item.setAmount(1);
			}
			else{
				c.setAdult();
				p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.CHICKEN)  +"'s§7 Age§7 to an §7§lAdult§7!");
				item.setAmount(2);
			}
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);

		}
	}
	
	public void handleCreeperPetType(){
		if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().startsWith("§a§nChange Type")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Creeper c = (Creeper) omp.getPet();
			
			if(c.isPowered()){
				c.setPowered(false);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§a§nChange Type§7 (§6§lNORMAL§7)");
				item.setItemMeta(meta);
			}
			else{
				c.setPowered(true);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§a§nChange Type§7 (§e§lLIGHTNING§7)");
				item.setItemMeta(meta);
			}
			
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleCreeperPetExplode(){
		if(item.getType() == Material.TNT && item.getItemMeta().getDisplayName().equals("§c§nExplode")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Creeper c = (Creeper) omp.getPet();
			
			c.getWorld().playEffect(c.getLocation(), Effect.EXPLOSION_HUGE, 4);
			c.getWorld().playSound(c.getLocation(), Sound.EXPLODE, 5, 1);
			omp.setPet(null);
			omp.setPetEnabled(null);
			
			p.leaveVehicle();
			
        	for(Entity en : c.getNearbyEntities(3, 3, 3)){
        		if(en instanceof Player){
        			en.setVelocity(en.getLocation().getDirection().multiply(-1).add(new Vector(0, 1.3, 0)));
        		}
        	}
        	
			c.remove();
		}
	}
	
	public void handleOcelotPetColor(){
		if(item.getType() == Material.RAW_FISH && item.getItemMeta().getDisplayName().equals("§9§nChange Color")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Ocelot o = (Ocelot) omp.getPet();
			
			switch(o.getCatType()){
				case BLACK_CAT:
					o.setCatType(Type.RED_CAT);
					break;
				case RED_CAT:
					o.setCatType(Type.SIAMESE_CAT);
					break;
				case SIAMESE_CAT:
					o.setCatType(Type.WILD_OCELOT);
					break;
				case WILD_OCELOT:
					o.setCatType(Type.BLACK_CAT);
					break;
				default:
					break;
			}
			
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleOcelotPetKittyCannon(){
		if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().equals("§e§nKitty Cannon")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_KITTY_CANNON_USAGE)){
				final Ocelot o = (Ocelot) p.getWorld().spawnEntity(p.getLocation(), EntityType.OCELOT);
				o.setBaby();
				o.setVelocity(p.getLocation().getDirection().multiply(2));
				o.setRemoveWhenFarAway(false);
				o.setCatType(Arrays.asList(Type.values()).get(new Random().nextInt(Type.values().length)));
				
				new BukkitRunnable(){
					public void run(){
						if(o instanceof LivingEntity){
							o.getWorld().playEffect(o.getLocation(), Effect.EXPLOSION_LARGE, 1);
							o.getWorld().playSound(o.getLocation(), Sound.EXPLODE, 5, 1);
							o.remove();
						}
					}
				}.runTaskLater(Start.getInstance(), 20);
				
				omp.resetCooldown(Cooldown.PET_KITTY_CANNON_USAGE);
			}
		}
	}
	
	public void handleHorsePetColor(){
		if(item.getType() == Material.LEATHER && item.getItemMeta().getDisplayName().equals("§e§nChange Color")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Horse h = (Horse) omp.getPet();
			
			switch(h.getColor()){
				case BLACK:
					h.setColor(Color.BROWN);
					break;
				case BROWN:
					h.setColor(Color.CHESTNUT);
					break;
				case CHESTNUT:
					h.setColor(Color.CREAMY);
					break;
				case CREAMY:
					h.setColor(Color.DARK_BROWN);
					break;
				case DARK_BROWN:
					h.setColor(Color.GRAY);
					break;
				case GRAY:
					h.setColor(Color.WHITE);
					break;
				case WHITE:
					h.setColor(Color.BLACK);
					break;
				default:
					break;
			}
			
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleHorsePetSpeed(){
		if(item.getType() == Material.FEATHER && item.getItemMeta().getDisplayName().equals("§f§nChange Speed")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Horse h = (Horse) omp.getPet();
			
			int speed = item.getAmount();
			AttributeInstance currentSpeed = ((EntityInsentient) ((CraftLivingEntity) h).getHandle()).getAttributeInstance(GenericAttributes.d);
			double newSpeed = currentSpeed.b();
			
			if(speed == 3){
		        newSpeed = currentSpeed.b() / 3;
				speed = 1;
			}
			else if(speed == 2){
				newSpeed = (currentSpeed.b() / 2) * 3;
		        speed++;
			}
			else{
				newSpeed = currentSpeed.b() * 2;
				speed++;
			}
			
			currentSpeed.setValue(newSpeed);
			item.setAmount(speed);
			
			p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.HORSE) +"'s§6 Speed§7 to §6§l" + speed + "§7!");
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleSilverfishPetLeap(){
		if(item.getType() == Material.STONE_HOE && item.getItemMeta().getDisplayName().equals("§8§nLeap")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_LEAP_USAGE)){
				Silverfish s = (Silverfish) omp.getPet();
				s.setVelocity(p.getLocation().getDirection().multiply(1.3).add(new Vector(0, 0.3, 0)));
				
				p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_BLAST, 6, 1);
				
				omp.resetCooldown(Cooldown.PET_LEAP_USAGE);
			}
		}
	}
	
	public void handleSilverfishPetBomb(){
		if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().equals("§7§nSilverfish Bomb")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_SILVERFISH_BOMB_USAGE)){
				ItemStack item = Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.MONSTER_EGG, 1), p.getName()), 60);
				Location pl = p.getLocation();
				Location l = new Location(p.getWorld(), pl.getX(), pl.getY() +1, pl.getZ());
				
				final Item iEn = p.getWorld().dropItem(l, item);
				iEn.setVelocity(p.getLocation().getDirection().multiply(1.1));
				
				ServerStorage.silverfishbombs.add(iEn);
				
				omp.resetCooldown(Cooldown.PET_SILVERFISH_BOMB_USAGE);
			}
		}
	}
	
	public void handleSheepPetDisco(){
		if(item.getType() == Material.WOOL && item.getItemMeta().getDisplayName().startsWith("§f§nSheep Disco")){
			e.setCancelled(true);
			omp.updateInventory();
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f§nSheep Disco§7 (" + Utils.statusString(!omp.hasPetSheepDisco()) + "§7)");
			item.setItemMeta(meta);
			
			p.sendMessage("§9Cosmetic Perks §8| " + Utils.statusString(!omp.hasPetSheepDisco()) + "§7 the §fSheep Disco§7!");
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
			
			omp.setPetSheepDisco(!omp.hasPetSheepDisco());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleSheepPetColor(){
		if(item.getType() == Material.INK_SACK && item.getItemMeta().getDisplayName().startsWith("§f§nChange Color")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Sheep s = (Sheep) omp.getPet();
			DyeColor c = Utils.getNextDyeColor(s);
			item.setDurability(c.getDyeData());
			
			s.setColor(c);
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f§nChange Color§7 (" + Utils.getDyeColorName(c) + "§7)");
			item.setItemMeta(meta);
			
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleCowPetMilkExplosion(){
		if(item.getType() == Material.MILK_BUCKET && item.getItemMeta().getDisplayName().equals("§f§nMilk Explosion")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_MILK_EXPLOSION)){
				ItemStack item = Utils.setDisplayname(new ItemStack(Material.MILK_BUCKET, 1), p.getName());
			
				final Item iEn = p.getWorld().dropItem(p.getLocation(), item);
				iEn.setVelocity(p.getLocation().getDirection().multiply(0.8));
				
				new BukkitRunnable(){
					@SuppressWarnings("deprecation")
					public void run(){
						Location l = iEn.getLocation();
						
						FireWork fw = new FireWork(l.subtract(0, 1, 0));
						fw.withColor(org.bukkit.Color.WHITE);
						fw.withColor(org.bukkit.Color.WHITE);
						fw.withFade(org.bukkit.Color.WHITE);
						fw.with(org.bukkit.FireworkEffect.Type.BALL_LARGE);
						fw.withFlicker();
						fw.withTrail();
						fw.build();
						fw.explode();
				        
				        iEn.remove();
						
				        Location l1 = new Location(iEn.getWorld(), l.getBlockX() +1, l.getBlockY() +1, l.getBlockZ() +1);
				        Location l2 = new Location(iEn.getWorld(), l.getBlockX() -1, l.getBlockY() -1, l.getBlockZ() -1);
				        
				        for(final Block b : Utils.getBlocksBetween(l1, l2)){
				        	if(!b.isEmpty() && b.getType() != Material.AIR && b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN){
				        		for(Player player : Bukkit.getOnlinePlayers()){
				        			player.sendBlockChange(b.getLocation(), Material.SNOW_BLOCK, (byte) 0);
				        		}
				        		new BukkitRunnable(){
				        			@Override
				        			public void run() {
				        				for(Player p : Bukkit.getOnlinePlayers()){
				        					p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
				        				}
				        			}
				        		}.runTaskLater(Start.getInstance(), 80);
				        	}
				        }
					}
				}.runTaskLater(Start.getInstance(), 60);
				
				omp.resetCooldown(Cooldown.PET_MILK_EXPLOSION);
			}
		}
	}
	
	public void handleCowPetAge(){
		if(item.getType() == Material.RAW_BEEF && item.getItemMeta().getDisplayName().equals("§c§nChange Age")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Cow cow = (Cow) omp.getPet();
			
			if(cow.isAdult()){
				cow.setBaby();
				p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.COW) +"'s§8 Age§7 to a §8§lBaby§7!");
				item.setAmount(1);
			}
			else{
				cow.setAdult();
				p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.COW) +"'s§8 Age§7 to an §8§lAdult§7!");
				item.setAmount(2);
			}
			
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleWolfPetAge(){
		if(item.getType() == Material.BONE && item.getItemMeta().getDisplayName().equals("§7§nChange Age")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Wolf wolf = (Wolf) omp.getPet();
			
			if(wolf.isAdult()){
				wolf.setBaby();
				p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.WOLF) +"'s§7 Age§7 to a §7§lBaby§7!");
				item.setAmount(1);
			}
			else{
				wolf.setAdult();
				p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.WOLF) +"'s§7 Age§7 to an §7§lAdult§7!");
				item.setAmount(2);
			}
			
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleWolfPetBark(){
		if(item.getType() == Material.COOKED_BEEF && item.getItemMeta().getDisplayName().equals("§6§nBark")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_BARK)){
				p.getWorld().playSound(p.getLocation(), Sound.WOLF_BARK, 10, 1);
				
				for(Entity en : p.getNearbyEntities(3, 3, 3)){
					if(en instanceof Player){
						Player p2 = (Player) en;
						OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
						if(!omp2.isInLapisParkour()){
							p2.setVelocity(p.getLocation().getDirection().subtract(p2.getLocation().getDirection()).multiply(4));
						}
					}
				}
				
				for(int iB = 0; iB < 20; iB++){
					ItemStack item = new ItemStack(Material.BONE, 1);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName("" + iB);
					item.setItemMeta(meta);
					final Item iEn = p.getWorld().dropItem(p.getLocation(), item);
					
					iEn.setVelocity(Utils.getRandomVelocity());

					new BukkitRunnable(){
						
						@Override
						public void run() {
							iEn.remove();
						}
					}.runTaskLater(Start.getInstance(), 60);	
				}
				
				omp.resetCooldown(Cooldown.PET_BARK);
			}
		}
	}
	
	public void handleSlimePetJump(){
		if(item.getType() == Material.LEATHER_BOOTS && item.getItemMeta().getDisplayName().equals("§6§nSuper Jump")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_JUMP)){
				omp.getPet().setVelocity(new Vector(0, 3, 0));
				
				omp.resetCooldown(Cooldown.PET_JUMP);
			}
		}
	}
	
	public void handleSlimePetSize(){
		if(item.getType() == Material.SLIME_BALL && item.getItemMeta().getDisplayName().equals("§a§nChange Size")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Slime s = (Slime) omp.getPet();
			
			int size = item.getAmount();
			
			if(size == 3){
				size = 1;
			}
			else{
				size++;
			}
			
			item.setAmount(size);
			s.setSize(size);
			
			p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.SLIME) +"'s§a Size§7 to §a§l" + size + "§7!");
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handlePetPigBabies(){
		if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().equals("§d§nBaby Pigs§7 (" + Utils.statusString(omp.hasPetBabyPigs()) + "§7)")){
			e.setCancelled(true);
			omp.updateInventory();
			
			ItemStack item = Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.MONSTER_EGG, 1), "§d§nBaby Pigs§7 (" + Utils.statusString(!omp.hasPetBabyPigs()) + "§7)"), 90);
			p.getInventory().setItem(2, item);
			
			p.sendMessage("§9Cosmetic Perks §8| " + Utils.statusString(!omp.hasPetBabyPigs()) + "§7 the §dBaby Pigs§7!");
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
			
			omp.setPetBabyPigs(!omp.hasPetBabyPigs());
			
			if(omp.hasPetBabyPigs()){
				List<Entity> list = new ArrayList<Entity>();
				for(int i = 1; i <= 2; i++){
					Pig pig = (Pig) p.getWorld().spawnEntity(p.getLocation(), EntityType.PIG);
					pig.setBaby();
					pig.setAgeLock(true);
					pig.setRemoveWhenFarAway(false);
					list.add(pig);
				}
				omp.setPetBabyPigEntities(list);
			}
			else{
				for(Entity en : omp.getPetBabyPigEntities()){
					en.remove();
				}
				omp.setPetBabyPigEntities(null);
			}
		}
	}
	
	public void handlePetPigAge(){
		if(item.getType() == Material.PORK && item.getItemMeta().getDisplayName().equals("§d§nChange Age")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Pig pig = (Pig) omp.getPet();
			
			if(pig.isAdult()){
				pig.setBaby();
				p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.PIG) + "'s§d Age§7 to a §d§lBaby§7!");
				item.setAmount(1);
			}
			else{
				pig.setAdult();
				p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.PIG)  + "'s§d Age§7 to an §d§lAdult§7!");
				item.setAmount(2);
			}
			
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleMagmaCubePetSize(){
		if(item.getType() == Material.MAGMA_CREAM && item.getItemMeta().getDisplayName().equals("§c§nChange Size")){
			e.setCancelled(true);
			omp.updateInventory();
			
			MagmaCube mc = (MagmaCube) omp.getPet();
			
			int size = item.getAmount();
			
			if(size == 3){
				size = 1;
			}
			else{
				size++;
			}
			
			item.setAmount(size);
			mc.setSize(size);
			
			p.sendMessage("§9Cosmetic Perks §8|§7 Changed §f" + omp.getPetName(Pet.MAGMA_CUBE) +"'s§c Size§7 to §c§l" + size + "§7!");
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleMagmaCubePetFireball(){
		if(item.getType() == Material.FIREBALL && item.getItemMeta().getDisplayName().equals("§6§nFireball")){
			ServerStorage.fireballs.add((Fireball) p.launchProjectile(Fireball.class));
		}
	}
	
	public void handleMushroomCowPetShroomTrail(){
		if(item.getType() == Material.HUGE_MUSHROOM_1 || item.getType() == Material.HUGE_MUSHROOM_2){
			if(item.getItemMeta().getDisplayName().equals("§4§nShroom Trail§7 (" + Utils.statusString(omp.hasPetShroomTrail()) + "§7)")){
				e.setCancelled(true);
				omp.updateInventory();
				
				ItemStack item = Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.HUGE_MUSHROOM_2, 1), "§4§nShroom Trail§7 (" + Utils.statusString(!omp.hasPetShroomTrail()) + "§7)"), 14);
				if(omp.hasPetShroomTrail()){
					item.setType(Material.HUGE_MUSHROOM_1);
				}
				p.getInventory().setItem(2, item);
				
				p.sendMessage("§9Cosmetic Perks §8| " + Utils.statusString(!omp.hasPetShroomTrail()) + "§7 the §4Shroom Trail§7!");
				p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
				
				omp.setPetShroomTrail(!omp.hasPetShroomTrail());
			}
		}
	}
	
	public void handleMushroomCowPetFirework(){
		if(item.getType() == Material.FIREWORK && item.getItemMeta().getDisplayName().equals("§c§nBaby Firework")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_BABY_FIREWORK)){
			
				final MushroomCow cow = (MushroomCow) p.getWorld().spawnEntity(p.getLocation(), EntityType.MUSHROOM_COW);
				cow.setAge(1);
				cow.setAgeLock(true);
				cow.setRemoveWhenFarAway(false);
				cow.setVelocity(p.getLocation().getDirection().multiply(1.2).setY(2));
				cow.setMaxHealth((double) Integer.MAX_VALUE);
				
				new BukkitRunnable(){
					public void run(){
						if(cow instanceof LivingEntity){
							FireWork fw = new FireWork(cow.getLocation());
					       
							fw.withColor(org.bukkit.Color.RED);
							fw.withColor(org.bukkit.Color.RED);
					        fw.withFade(org.bukkit.Color.RED);
					        fw.with(org.bukkit.FireworkEffect.Type.BALL);
					        fw.withFlicker();
					        fw.withTrail();
					        fw.build();
					        fw.explode();
					        
							cow.remove();
						}
					}
				}.runTaskLater(Start.getInstance(), 30);

				omp.resetCooldown(Cooldown.PET_BABY_FIREWORK);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleSpiderPetWebs(){
		if(item.getType() == Material.WEB && item.getItemMeta().getDisplayName().equals("§f§nWebs")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_WEBS)){
				FallingBlock block1 = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.WEB, (byte) 0);
				block1.setVelocity(p.getLocation().getDirection().multiply(1.1));
				block1.setDropItem(false);
				
				Vector velocity = block1.getVelocity();
				double speed = velocity.length();
				Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
				double spray = 5D;
				
				for (int i2 = 0; i2 < 2; i2++) {
					FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.WEB, (byte) 0);
					
				  	block.setVelocity(new Vector(direction.getX() + (Math.random() - 1.5) / spray, direction.getY() + (Math.random() - 1.5) / spray, direction.getZ() + (Math.random() - 1.5) / spray).normalize().multiply(speed));
					block.setDropItem(false);
				}

				omp.resetCooldown(Cooldown.PET_WEBS);
			}
		}
	}
	
	public void handleSpiderPetLauncher(){
		if(item.getType() == Material.SPIDER_EYE && item.getItemMeta().getDisplayName().equals("§5§nSpider Launcher")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_SPIDER_LAUNCHER)){
				final Spider s = (Spider) p.getWorld().spawnEntity(p.getLocation(), EntityType.SPIDER);
				s.setVelocity(p.getLocation().getDirection().multiply(1.5));
				s.setRemoveWhenFarAway(false);
				
				new BukkitRunnable(){
					public void run(){
						if(s instanceof LivingEntity){
							s.remove();
						}
					}
				}.runTaskLater(Start.getInstance(), 80);
				
				omp.resetCooldown(Cooldown.PET_SPIDER_LAUNCHER);
			}
		}
	}
	
	public void handleSquidPetInkBomb(){
		if(item.getType() == Material.INK_SACK && item.getItemMeta().getDisplayName().equals("§8§nInk Bomb")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_INK_BOMB)){
				e.setCancelled(true);
				omp.updateInventory();
				
				Item itemEn = p.getWorld().dropItem(p.getLocation(), Utils.setDisplayname(new ItemStack(Material.INK_SACK, 1), "§8§nInk Bomb " + p.getName()));
				itemEn.setVelocity(p.getLocation().getDirection().multiply(1.3));
				itemEn.setPickupDelay(Integer.MAX_VALUE);
				ServerStorage.inkbombs.add(itemEn);
				ServerStorage.inkbombtime.put(itemEn, 10 * 3);
				
				omp.resetCooldown(Cooldown.PET_INK_BOMB);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleSquidPetWaterSpout(){
		if(item.getType() == Material.WATER_BUCKET && item.getItemMeta().getDisplayName().equals("§9§nWater Spout")){
			e.setCancelled(true);
			omp.updateInventory();
			
	        FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.STAINED_GLASS, (byte) 11);
	        block.setVelocity(p.getLocation().getDirection().multiply(1.1));
	        block.setDropItem(false);
            
            p.getWorld().playSound(p.getLocation(), Sound.WATER, 6, 1);
		}
	}
	
	public boolean handleGrapplingHook(){
		if(item.getType() == Material.FISHING_ROD && item.getItemMeta().getDisplayName().equals("§7§nGrappling Hook")){
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void handleFlameThrower(){
		if(item.getType() == Material.BLAZE_POWDER && item.getItemMeta().getDisplayName().equals("§e§nFlame Thrower")){
			e.setCancelled(true);
			
	        FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.FIRE, (byte) 0);
	        block.setVelocity(p.getLocation().getDirection().multiply(1.1));
	        block.setDropItem(false);
		}
	}
	
	public void handleMagmaCubeSoccer(){
		if(item.getType() == Material.MAGMA_CREAM && item.getItemMeta().getDisplayName().equals("§c§nMagmaCube Soccer")){
			if(omp.getSoccerMagmaCube() == null){
				MagmaCube mc = (MagmaCube) p.getWorld().spawnEntity(p.getLocation(), EntityType.MAGMA_CUBE);
				mc.setSize(1);
				mc.setRemoveWhenFarAway(false);
				mc.setCustomName("§cSoccer Ball");
				mc.setCustomNameVisible(true);
				
				ServerStorage.soccermagmacubes.add(mc);
				omp.setSoccerMagmaCube(mc);
				
				p.sendMessage("§9Cosmetic Perks §8| §a§lENABLED§7 your §cMagmaCube Ball§7. §eRight Click§7 to remove it, §eLeft Click§7 to shoot it.");
				p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, 152);
			}
			else{
				omp.getSoccerMagmaCube().teleport(p.getLocation());
				p.sendMessage("§9Cosmetic Perks §8| §7Teleported your §cMagmaCube Ball§7 to you!");
				p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, 152);
			}
		}
	}
	
	public void handleSwapTeleporter(){
		if(item.getType() == Material.EYE_OF_ENDER && item.getItemMeta().getDisplayName().equals("§2§nSwap Teleporter")){
			e.setCancelled(true);
			
			if(!omp.isInLapisParkour()){
				if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
					if(!omp.onCooldown(Cooldown.SWAP_TELEPORTER)){
						ItemStack item = new ItemStack(Material.EYE_OF_ENDER, 1);
						final Entity en = p.getWorld().dropItem(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), item);
						en.setVelocity(p.getLocation().getDirection().multiply(1.5));
						
						if(omp.getSwapTeleporter() != null){
							ServerStorage.swapteleporter.remove(omp.getSwapTeleporter());
							omp.setSwapTeleporter(null);
						}
						
						ServerStorage.swapteleporter.put(en, omp);
						
						new BukkitRunnable(){
							public void run(){
								if(ServerStorage.swapteleporter.containsKey(en)){
									ServerStorage.swapteleporter.remove(en);
									en.remove();
									omp.setSwapTeleporter(null);
								}
							}
						}.runTaskLater(Start.getInstance(), 100);
						
						omp.resetCooldown(Cooldown.SWAP_TELEPORTER);
					}
				}
			}
		}
	}
	
	public void handleCreeperLauncher(){
		if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§a§nCreeper Launcher")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.isInLapisParkour()){
				if(!omp.onCooldown(Cooldown.CREEPER_LAUNCHER)){
					Creeper creeper = (Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
					creeper.setPowered(true);
					creeper.setVelocity(p.getLocation().getDirection().normalize().multiply(1.5));
					
					ServerStorage.creeperlaunched.add(creeper);
					
					omp.resetCooldown(Cooldown.CREEPER_LAUNCHER);
				}
			}
		}
	}
	
	public void handlePaintballs(){				
		if(item.getType() == Material.SNOW_BALL && item.getItemMeta().getDisplayName().equals("§f§nPaintballs")){
			e.setCancelled(true);
			omp.updateInventory();
			
			ServerStorage.paintballs.add(p.launchProjectile(Snowball.class));
		}
	}
	
	public void handleBookExplosion(){
		if(item.getType() == Material.BOOK && item.getItemMeta().getDisplayName().equals("§7§nBook Explosion")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.isInLapisParkour()){
				if(!omp.onCooldown(Cooldown.BOOK_EXPLOSION)){
					p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 5, 1);
					
					for(int i = 1; i <= 12; i++){
						ItemStack item = Utils.setDisplayname(new ItemStack(Material.PAPER), "Paper " + i);
						final Item paper = p.getWorld().dropItem(p.getLocation(), item);
						paper.setVelocity(Utils.getRandomVelocity());
						
						new BukkitRunnable(){
							@Override
							public void run(){
								paper.remove();
							}
						}.runTaskLater(Start.getInstance(), 200);
						
						omp.resetCooldown(Cooldown.BOOK_EXPLOSION);
					}
				}
			}
		}
	}
	
	public void handleSnowGolemAttack(){
		if(item.getType() == Material.PUMPKIN && item.getItemMeta().getDisplayName().equals("§6§nSnowman Attack")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.SGA_USAGE)){
				Item iEn = p.getWorld().dropItem(p.getLocation(), Utils.setDisplayname(new ItemStack(Material.PUMPKIN, 1), p.getName()));
				iEn.setVelocity(p.getLocation().getDirection().multiply(0.5));
				
				omp.setSGASeconds(0);
				omp.setSGAItem(iEn);
				
				Bukkit.broadcastMessage(omp.getName() + "§7 summoned a §6§lSnowman Attack§7!");
				for(Player player : Bukkit.getOnlinePlayers()){
					player.playSound(p.getLocation(), Sound.WITHER_SPAWN, 5, 1);
				}
				
				omp.resetCooldown(Cooldown.SGA_USAGE);
			}
		}
	}
	
	public void handlePetRide(){
		if(item.getType() == Material.SADDLE && item.getItemMeta().getDisplayName().equals("§e§nPet Ride")){
			e.setCancelled(true);
			omp.updateInventory();
		}
	}
	
	public void handleStacker(){
		if(item.getType() == Material.LEASH){
			e.setCancelled(true);
			omp.updateInventory();
		}
	}
	
	public void handleFireworkGun(){
		if(item.getType() == Material.FIREBALL && item.getItemMeta().getDisplayName().startsWith("§c§nFirework Gun")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(omp.getFireworkPasses() != 0){
				FireWork fw = new FireWork(p.getLocation());
				fw.applySettings(omp.getFWSettings());
		        fw.setVelocity(p.getLocation().getDirection().multiply(0.2));
		        
		        omp.removeFireworkPass();
		        
				ItemStack item = Utils.setDisplayname(new ItemStack(Material.FIREBALL, 1), "§c§nFirework Gun§r §c(§6" + omp.getFireworkPasses() + "§c)");
				if(ServerData.isServer(Server.HUB)){
					p.getInventory().setItem(5, new ItemStack(item));
				}
				else{
					p.getInventory().setItem(6, new ItemStack(item));
				}
			}
			else{
				p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7You don't have any §6§lFirework Passes§7.");
			}
		}
	}
	
	public void handleCosmeticPerks(){
		if(item.getType() == Material.ENDER_CHEST && item.getItemMeta().getDisplayName().equals("§9§nCosmetic Perks")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.isInLapisParkour()){
				new CosmeticPerksInv().open(p);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleSettings(){
		if(item.getType() == Material.REDSTONE_TORCH_ON && item.getItemMeta().getDisplayName().equals("§c§nSettings")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.isInLapisParkour()){
				new SettingsInv(p).open(p);
				p.playEffect(p.getLocation(), Effect.STEP_SOUND, 152);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleFly(){
		if(item.getType() == Material.FEATHER && item.getItemMeta().getDisplayName().equals("§f§nFly")){
			if(omp.hasPerms(VIPRank.Iron_VIP)){
				if(!omp.isInLapisParkour()){
					p.playEffect(p.getLocation(), Effect.STEP_SOUND, 80);
		    		p.setAllowFlight(!p.getAllowFlight());
		    		p.setFlying(p.getAllowFlight());
					p.sendMessage("§f§l§oFly " + Utils.statusString(p.getAllowFlight()));
				}
			}
			else{
				p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
				p.sendMessage("§f§l§oFly §8| §4§lDENIED §7Required: §7§lIron VIP§7!");
			}
		}
	}
	
	public boolean handleRulesBook(){
		if(item.getType() != Material.WRITTEN_BOOK){
			return true;
		}
		return false;
	}
	
	public void handleAchievements(){
		if(item.getType() == Material.EXP_BOTTLE && item.getItemMeta().getDisplayName().equals("§d§nAchievements")){
			e.setCancelled(true);
			omp.updateInventory();
			
			p.sendMessage("§a§oComing Soon...");
			// TODO ADD ACHIEVEMENTS \\
		}
	}
	
	public void handleServerSelector(){
		if(item.getType() == Material.ENDER_PEARL){
			e.setCancelled(true);
			omp.updateInventory();
			
			ServerSelectorInv.get().open(p);
		}
	}
	
	public void handleKickSign(){
		if(b != null && b.getType() == Material.WALL_SIGN){
			if(b.getLocation().getBlockX() == -18 && b.getLocation().getBlockZ() == 2){
				p.kickPlayer("§7Well that's unfortunate. :)");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleMindCraft(){
		if(omp.isInMindCraft()){
			final MindCraftPlayer mcp = omp.getMCPlayer();
			HubServer hub = ServerData.getHub();
			
			if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
				if(item.getType() == Material.TNT && item.getItemMeta().getDisplayName().equals("§4§lReset Colors")){
					e.setCancelled(true);
					omp.updateInventory();
					
					p.playSound(p.getLocation(), Sound.EXPLODE, 5, 1);
					
					List<String> blocksfromturn = mcp.getBlocksFromTurns();
					blocksfromturn.set(0, "0|0|0|0");
					
					List<Block> blocks = hub.getMCBlocksForTurn().get(0);
					
					for(Block bl : blocks){
						p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) 0);
					}
				}
				if(item.getType() == Material.REDSTONE_TORCH_ON && item.getItemMeta().getDisplayName().equals("§c§lEnd Turn")){
					e.setCancelled(true);
					omp.updateInventory();
					
					List<String> blocksfromturn = Utils.asNewList(mcp.getBlocksFromTurns());
					
					String newturnnext = blocksfromturn.get(0);
					if(!newturnnext.contains("0")){
						List<String> blockstatusfromturn = Utils.asNewList(mcp.getStatusFromTurns());
						
						int currentturn = mcp.getCurrentTurn();
						String newturn = "0|0|0|0";
						
						blocksfromturn.set(0, newturn);
						blocksfromturn.set(currentturn, newturnnext);
						
						mcp.setBlocksFromTurns(blocksfromturn);
						
						String[] correctturn = mcp.getCorrectTurn().split("\\|");
						String[] thisturn = newturnnext.split("\\|");
						
						List<String> correctturnints = Arrays.asList(correctturn);
						List<String> thisturnints = Arrays.asList(thisturn);
						
						String status = "";
						int correct = 0;
						int otherplace = 0;
						int incorrect = 0;
						
						for(int iR = 3; iR > -1; iR--){
							if(correctturnints.get(iR).equals(thisturnints.get(iR))){
								correct++;
								correctturnints.set(iR, "0");
							}
						}
						for(int iR = 3; iR > -1; iR--){
							if(correctturnints.contains(thisturnints.get(iR))){
								otherplace++;
								correctturnints.set(iR, "0");
							}
							else{
								if(!correctturnints.get(iR).equals("0")){
									incorrect++;
								}
							}
						}
						
						if(correct + otherplace + incorrect == 5){
							otherplace--;
						}
						
						if(correct != 0){
							for(int iR = 0; iR < correct; iR++){
								status += "|" + "5";
							}
						}
						if(otherplace != 0){
							for(int iR = 0; iR < otherplace; iR++){
								status += "|" + "4";
							}
						}
						if(incorrect != 0){
							for(int iR = 0; iR < incorrect; iR++){
								status += "|" + "0";
							}
						}
						
						status = status.substring(1);
						
						blockstatusfromturn.set(currentturn, status);
						mcp.setStatusFromTurns(blockstatusfromturn);
						
						mcp.setCurrentTurn(currentturn +1);
						
						List<Block> blocks = hub.getMCBlocksForTurn().get(0);
						List<Block> blocks2 = hub.getMCBlocksForTurn().get(currentturn);
						
						for(Block bl : blocks){
							p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) 0);
						}
						for(Block bl : blocks2){
							p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) Integer.parseInt(thisturn[blocks2.indexOf(bl)]));
						}
						
						if(mcp.getCorrectTurn().equals(newturnnext)){
							p.getInventory().clear();
							p.sendMessage("§c§lMindCraft §8| §7You guessed the color combination!");
							p.sendMessage("§c§lMindCraft §8| §c§l+1 Win");
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
							
							blocksfromturn.set(11, mcp.getCorrectTurn());
							mcp.setBlocksFromTurns(blocksfromturn);
							
							mcp.addWin();
							
							if(mcp.getBestGame() != -1){
								if(currentturn < mcp.getBestGame()){
									mcp.setBestGame(currentturn);
								}
							}
							else{
								Database.get().insert("MasterMind-BestGame", "uuid`, `turns", omp.getUUID().toString() + "', '" + currentturn);
							}
							
							FireWork fw = new FireWork(p.getLocation());
							fw.randomize();
							fw.build();
							
							Title t = new Title("§c§lMindCraft", "§7You won! §c+1 Win");
							t.send(p);
							
							p.getInventory().clear();
							omp.updateInventory();
							
							new BukkitRunnable(){
								public void run(){
									mcp.reset();
								}
							}.runTaskLater(Start.getInstance(), 100);
						}
						else if(currentturn == 10){
							blocksfromturn.set(11, mcp.getCorrectTurn());
							mcp.setBlocksFromTurns(blocksfromturn);
							
							p.sendMessage("§c§lMindCraft §8| §7You §cLost§7! Try again!");
							
							Title t = new Title("§c§lMindCraft", "§7You Lost! Try again!");
							t.send(p);
							
							p.getInventory().clear();
							omp.updateInventory();
							
							new BukkitRunnable(){
								public void run(){
									mcp.reset();
								}
							}.runTaskLater(Start.getInstance(), 100);
						}
						else{
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
							p.sendMessage("§c§lMindCraft §8| §7Correct: §a" + correct + " §7Other Place: §e" + otherplace + " §7Incorrect: §c" + incorrect);
						}
					}
					else{
						p.sendMessage("§c§lMindCraft §8| §7You didn't use all color slots!");
					}
				}
			}
			if(a == Action.RIGHT_CLICK_BLOCK){
				List<Block> blocks = hub.getMCBlocksForTurn().get(0);
				if(blocks.contains(b)){
					if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
						for(final MindCraftColor color : MindCraftColor.values()){
							if(item.getType() == Material.WOOL && item.getItemMeta().getDisplayName().equals(color.getName())){
								e.setCancelled(true);
								omp.updateInventory();
								
								List<String> blocksfromturn = mcp.getBlocksFromTurns();
								String turn = blocksfromturn.get(0);
								String[] turnblocks = turn.split("\\|");
								turnblocks[blocks.indexOf(b)] = "" + color.getData();
								String newturn = "";
								for(String s : turnblocks){
									newturn += "|" + s;
								}
								newturn = newturn.substring(1);
								blocksfromturn.set(0, newturn);
								
								mcp.setBlocksFromTurns(blocksfromturn);
								
								new BukkitRunnable(){
									public void run(){
										p.sendBlockChange(b.getLocation(), Material.WOOL, color.getData());
									}
								}.runTaskLater(Start.getInstance(), 1);
							}
						}
					}
				}
			}
		}
	}
}
