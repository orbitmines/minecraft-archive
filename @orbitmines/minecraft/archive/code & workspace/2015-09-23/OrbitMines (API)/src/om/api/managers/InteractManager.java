package om.api.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import om.api.API;
import om.api.handlers.FireWork;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.CosmeticPerksInv;
import om.api.utils.ColorUtils;
import om.api.utils.ItemUtils;
import om.api.utils.Utils;
import om.api.utils.WorldUtils;
import om.api.utils.enums.Cooldown;
import om.api.utils.enums.cp.Pet;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
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
	
	protected API api;
	protected PlayerInteractEvent e;
	protected Player p;
	protected OMPlayer omp;
	protected ItemStack item;
	protected Block b;
	protected Action a;
	
	public InteractManager(PlayerInteractEvent e){
		this.api = API.getInstance();
		this.e = e;
		this.p = e.getPlayer();
		this.omp = OMPlayer.getOMPlayer(p);
		this.item = e.getItem();
		this.b = e.getClickedBlock();
		this.a = e.getAction();
	}
	
	public void handleMonsterEggs(){
		if(item != null && item.getType() == Material.MONSTER_EGG){
			e.setCancelled(true);
			omp.updateInventory();
		}
	}
	
	public void handleClickable(){
		if(!omp.isOpMode() && b != null && (b.getType() == Material.CHEST || b.getType() == Material.ENDER_CHEST || b.getType() == Material.TRAPPED_CHEST || b.getType() == Material.FURNACE || b.getType() == Material.WORKBENCH || b.getType() == Material.ANVIL || b.getType() == Material.ENCHANTMENT_TABLE || b.getType() == Material.DISPENSER || b.getType() == Material.HOPPER || b.getType() == Material.DROPPER || b.getType() == Material.TRAP_DOOR)){
			e.setCancelled(true);
		}
	}
	public void handleChickenPetEggBomb(){
		if(item.getType() == Material.EGG && item.getItemMeta().getDisplayName().equals("§7§nEgg Bomb")){
			e.setCancelled(true);
			omp.updateInventory();
			
			api.getEggBombs().add((Egg) p.launchProjectile(Egg.class));
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
				}.runTaskLater(api, 20);
				
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
			AttributeInstance currentSpeed = ((EntityInsentient) ((CraftLivingEntity) h).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
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
				ItemStack item = ItemUtils.setItem(new ItemStack(Material.MONSTER_EGG, 1), p.getName(), 60);
				Location pl = p.getLocation();
				Location l = new Location(p.getWorld(), pl.getX(), pl.getY() +1, pl.getZ());
				
				final Item iEn = p.getWorld().dropItem(l, item);
				iEn.setVelocity(p.getLocation().getDirection().multiply(1.1));
				
				api.getSilverFishBombs().add(iEn);
				
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
			DyeColor c = ColorUtils.getNext(s);
			item.setDurability(c.getDyeData());
			
			s.setColor(c);
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f§nChange Color§7 (" + ColorUtils.getName(c) + "§7)");
			item.setItemMeta(meta);
			
			p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		}
	}
	
	public void handleCowPetMilkExplosion(){
		if(item.getType() == Material.MILK_BUCKET && item.getItemMeta().getDisplayName().equals("§f§nMilk Explosion")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.PET_MILK_EXPLOSION)){
				ItemStack item = ItemUtils.setDisplayname(new ItemStack(Material.MILK_BUCKET), p.getName());
			
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
				        
				        for(final Block b : WorldUtils.getBlocksBetween(l1, l2)){
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
				        		}.runTaskLater(api, 80);
				        	}
				        }
					}
				}.runTaskLater(api, 60);
				
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
					
					iEn.setVelocity(Utils.randomVelocity());

					new BukkitRunnable(){
						
						@Override
						public void run() {
							iEn.remove();
						}
					}.runTaskLater(api, 60);	
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
			
			ItemStack item = ItemUtils.setItem(new ItemStack(Material.MONSTER_EGG), "§d§nBaby Pigs§7 (" + Utils.statusString(!omp.hasPetBabyPigs()) + "§7)", 90);
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
			api.getFireballs().add((Fireball) p.launchProjectile(Fireball.class));
		}
	}
	
	public void handleMushroomCowPetShroomTrail(){
		if(item.getType() == Material.HUGE_MUSHROOM_1 || item.getType() == Material.HUGE_MUSHROOM_2){
			if(item.getItemMeta().getDisplayName().equals("§4§nShroom Trail§7 (" + Utils.statusString(omp.hasPetShroomTrail()) + "§7)")){
				e.setCancelled(true);
				omp.updateInventory();
				
				ItemStack item = ItemUtils.setItem(new ItemStack(Material.HUGE_MUSHROOM_2, 1), "§4§nShroom Trail§7 (" + Utils.statusString(!omp.hasPetShroomTrail()) + "§7)", 14);
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
				}.runTaskLater(api, 30);

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
				}.runTaskLater(api, 80);
				
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
				
				Item itemEn = p.getWorld().dropItem(p.getLocation(), ItemUtils.setDisplayname(new ItemStack(Material.INK_SACK, 1), "§8§nInk Bomb " + p.getName()));
				itemEn.setVelocity(p.getLocation().getDirection().multiply(1.3));
				itemEn.setPickupDelay(Integer.MAX_VALUE);
				api.getInkBombs().add(itemEn);
				api.getInkBombTime().put(itemEn, 10 * 3);
				
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
				
				api.getSoccerMagmaCubes().add(mc);
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
							api.getSwapTeleporter().remove(omp.getSwapTeleporter());
							omp.setSwapTeleporter(null);
						}
						
						api.getSwapTeleporter().put(en, omp);
						
						new BukkitRunnable(){
							public void run(){
								if(api.getSwapTeleporter().containsKey(en)){
									api.getSwapTeleporter().remove(en);
									en.remove();
									omp.setSwapTeleporter(null);
								}
							}
						}.runTaskLater(api, 100);
						
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
					
					api.getCreeperLaunched().add(creeper);
					
					omp.resetCooldown(Cooldown.CREEPER_LAUNCHER);
				}
			}
		}
	}
	
	public void handlePaintballs(){				
		if(item.getType() == Material.SNOW_BALL && item.getItemMeta().getDisplayName().equals("§f§nPaintballs")){
			e.setCancelled(true);
			omp.updateInventory();
			
			api.getPaintBalls().add(p.launchProjectile(Snowball.class));
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
						ItemStack item = ItemUtils.setDisplayname(new ItemStack(Material.PAPER), "Paper " + i);
						final Item paper = p.getWorld().dropItem(p.getLocation(), item);
						paper.setVelocity(Utils.randomVelocity());
						
						new BukkitRunnable(){
							@Override
							public void run(){
								paper.remove();
							}
						}.runTaskLater(api, 200);
						
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
				Item iEn = p.getWorld().dropItem(p.getLocation(), ItemUtils.setDisplayname(new ItemStack(Material.PUMPKIN, 1), p.getName()));
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
		        
				ItemStack item = ItemUtils.setDisplayname(new ItemStack(Material.FIREBALL, 1), "§c§nFirework Gun§r §c(§6" + omp.getFireworkPasses() + "§c)");
				p.getInventory().setItem(api.getGadgetSlot(), new ItemStack(item));
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
	
	public boolean handleWrittenBook(){
		if(item.getType() != Material.WRITTEN_BOOK){
			return true;
		}
		return false;
	}
	
	public void handleServerSelector(){
		if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nServer Selector")){
			e.setCancelled(true);
			omp.updateInventory();
			
			api.getServerSelector().open(p);
		}
	}
}
