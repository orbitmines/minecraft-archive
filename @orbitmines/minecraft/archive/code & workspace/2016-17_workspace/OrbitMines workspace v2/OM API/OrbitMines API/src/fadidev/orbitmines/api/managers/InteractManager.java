package fadidev.orbitmines.api.managers;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.firework.FireWork;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.perks.Pet;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Fadi on 6-9-2016.
 */
public class InteractManager {

    /*
     * TODO
     * Not the best code: (Old Code)
     * Should edit this shortly.
     */

    protected OrbitMinesAPI api;
    protected PlayerInteractEvent e;
    protected Player p;
    protected OMPlayer omp;
    protected ItemStack item;
    protected Block b;
    protected Action a;

    public InteractManager(PlayerInteractEvent e){
        this.api = OrbitMinesAPI.getApi();
        this.e = e;
        this.p = e.getPlayer();
        this.omp = OMPlayer.getOMPlayer(p);
        this.item = e.getItem();
        this.b = e.getClickedBlock();
        this.a = e.getAction();
    }

    protected Player getPlayer() {
        return p;
    }

    /* Protect spawn */
    public boolean handleClickable(){
        if(!omp.isOpMode() && b != null && (b.getType() == Material.CHEST || b.getType() == Material.ENDER_CHEST || b.getType() == Material.TRAPPED_CHEST || b.getType() == Material.FURNACE || b.getType() == Material.WORKBENCH || b.getType() == Material.ANVIL || b.getType() == Material.ENCHANTMENT_TABLE || b.getType() == Material.DISPENSER || b.getType() == Material.HOPPER || b.getType() == Material.DROPPER || b.getType() == Material.TRAP_DOOR)){
            e.setCancelled(true);

            return true;
        }
        return false;
    }

    public boolean handlePetAbilities(){
        if(!omp.hasPetEnabled())
            return false;
        
        if(handleChickenPetEggBomb()){}
        else if(handleChickenPetAge()){}
        else if(handleCreeperPetType()){}
        else if(handleCreeperPetExplode()){}
        else if(handleOcelotPetColor()){}
        else if(handleOcelotPetKittyCannon()){}
        else if(handleHorsePetColor()){}
        else if(handleHorsePetSpeed()){}
        else if(handleSilverfishPetLeap()){}
        else if(handleSilverfishPetBomb()){}
        else if(handleSheepPetDisco()){}
        else if(handleSheepPetColor()){}
        else if(handleCowPetMilkExplosion()){}
        else if(handleCowPetAge()){}
        else if(handleWolfPetAge()){}
        else if(handleWolfPetBark()){}
        else if(handleSlimePetJump()){}
        else if(handleSlimePetSize()){}
        else if(handlePetPigBabies()){}
        else if(handlePetPigAge()){}
        else if(handleMagmaCubePetSize()){}
        else if(handleMagmaCubePetFireball()){}
        else if(handleMushroomCowPetShroomTrail()){}
        else if(handleMushroomCowPetFirework()){}
        else if(handleSpiderPetWebs()){}
        else if(handleSpiderPetLauncher()){}
        else if(handleSquidPetInkBomb()){}
        else if(handleSquidPetWaterSpout()){}
        else if(handleFlameThrower()){}
        else return false;

        return true;
    }

    /* Pet Abilities */
    private boolean handleChickenPetEggBomb(){
        if(item.getType() == Material.EGG && item.getItemMeta().getDisplayName().equals("§7§nEgg " + Messages.PET_BOMB.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            api.getGadgets().getEggBombs().add(p.launchProjectile(Egg.class));

            return true;
        }
        return false;
    }

    private boolean handleChickenPetAge(){
        if(item.getType() == Material.RAW_CHICKEN && item.getItemMeta().getDisplayName().equals("§c§n" + Messages.PET_CHANGE_AGE.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Chicken c = (Chicken) omp.getPet();

            if(c.isAdult()){
                c.setBaby();
                p.sendMessage(Messages.PET_CHANGE_AGE_BABY.get(omp, omp.getPetName(Pet.CHICKEN), "§c"));
                item.setAmount(1);
            }
            else{
                c.setAdult();
                p.sendMessage(Messages.PET_CHANGE_AGE_ADULT.get(omp, omp.getPetName(Pet.CHICKEN), "§c"));
                item.setAmount(2);
            }
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleCreeperPetType(){
        if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().startsWith("§a§n" + Messages.PET_CHANGE_TYPE.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Creeper c = (Creeper) omp.getPet();

            if(c.isPowered()){
                c.setPowered(false);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§a§n" + Messages.PET_CHANGE_TYPE.get(omp) + "§7 (§6§lNORMAL§7)");
                item.setItemMeta(meta);
            }
            else{
                c.setPowered(true);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§a§n" + Messages.PET_CHANGE_TYPE.get(omp) + "§7 (§e§lLIGHTNING§7)");
                item.setItemMeta(meta);
            }

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleCreeperPetExplode(){
        if(item.getType() == Material.TNT && item.getItemMeta().getDisplayName().equals(Messages.PET_EXPLODE.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Creeper c = (Creeper) omp.getPet();

            c.getWorld().playEffect(c.getLocation(), Effect.EXPLOSION_HUGE, 4);
            c.getWorld().playSound(c.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
            omp.setPet(null);
            omp.setPetEnabled(null);

            p.leaveVehicle();

            for(Entity en : c.getNearbyEntities(3, 3, 3)){
                if(en instanceof Player){
                    en.setVelocity(en.getLocation().getDirection().multiply(-1).add(new Vector(0, 1.3, 0)));
                }
            }

            c.remove();

            return true;
        }
        return false;
    }

    private boolean handleOcelotPetColor(){
        if(item.getType() == Material.RAW_FISH && item.getItemMeta().getDisplayName().equals("§9§n" + Messages.PET_CHANGE_COLOR.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Ocelot o = (Ocelot) omp.getPet();

            switch(o.getCatType()){
                case BLACK_CAT:
                    o.setCatType(Ocelot.Type.RED_CAT);
                    break;
                case RED_CAT:
                    o.setCatType(Ocelot.Type.SIAMESE_CAT);
                    break;
                case SIAMESE_CAT:
                    o.setCatType(Ocelot.Type.WILD_OCELOT);
                    break;
                case WILD_OCELOT:
                    o.setCatType(Ocelot.Type.BLACK_CAT);
                    break;
                default:
                    break;
            }

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleOcelotPetKittyCannon(){
        if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().equals("§e§nKitty Cannon")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_KITTY_CANNON_USAGE)){
                final Ocelot o = (Ocelot) p.getWorld().spawnEntity(p.getLocation(), EntityType.OCELOT);
                o.setBaby();
                o.setVelocity(p.getLocation().getDirection().multiply(2));
                o.setRemoveWhenFarAway(false);
                o.setCatType(Arrays.asList(Ocelot.Type.values()).get(new Random().nextInt(Ocelot.Type.values().length)));

                new BukkitRunnable(){
                    public void run(){
                        o.getWorld().playEffect(o.getLocation(), Effect.EXPLOSION_LARGE, 1);
                        o.getWorld().playSound(o.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
                        o.remove();
                    }
                }.runTaskLater(api, 20);

                omp.resetCooldown(Cooldowns.PET_KITTY_CANNON_USAGE);
            }

            return true;
        }
        return false;
    }

    private boolean handleHorsePetColor(){
        if(item.getType() == Material.LEATHER && item.getItemMeta().getDisplayName().equals("§e§n" + Messages.PET_CHANGE_COLOR.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Horse h = (Horse) omp.getPet();

            switch(h.getColor()){
                case BLACK:
                    h.setColor(Horse.Color.BROWN);
                    break;
                case BROWN:
                    h.setColor(Horse.Color.CHESTNUT);
                    break;
                case CHESTNUT:
                    h.setColor(Horse.Color.CREAMY);
                    break;
                case CREAMY:
                    h.setColor(Horse.Color.DARK_BROWN);
                    break;
                case DARK_BROWN:
                    h.setColor(Horse.Color.GRAY);
                    break;
                case GRAY:
                    h.setColor(Horse.Color.WHITE);
                    break;
                case WHITE:
                    h.setColor(Horse.Color.BLACK);
                    break;
                default:
                    break;
            }

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleHorsePetSpeed(){
        if(item.getType() == Material.FEATHER && item.getItemMeta().getDisplayName().equals("§f§n" + Messages.PET_CHANGE_SPEED.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            int speed = item.getAmount();
            double newSpeed;

            if(speed == 3){
                newSpeed = api.getNms().entity().getSpeed(omp.getPet()) / 3;
                speed = 1;
            }
            else if(speed == 2){
                newSpeed = (api.getNms().entity().getSpeed(omp.getPet()) / 2) * 3;
                speed++;
            }
            else{
                newSpeed = api.getNms().entity().getSpeed(omp.getPet()) * 2;
                speed++;
            }

            api.getNms().entity().setSpeed(omp.getPet(), newSpeed);
            item.setAmount(speed);

            p.sendMessage(Messages.PET_SPEED_CHANGED.get(omp, omp.getPetName(Pet.HORSE), speed + ""));
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleSilverfishPetLeap(){
        if(item.getType() == Material.STONE_HOE && item.getItemMeta().getDisplayName().equals("§8§nLeap")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_LEAP_USAGE)){
                Silverfish s = (Silverfish) omp.getPet();
                s.setVelocity(p.getLocation().getDirection().multiply(1.3).add(new Vector(0, 0.3, 0)));

                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 6, 1);

                omp.resetCooldown(Cooldowns.PET_LEAP_USAGE);
            }

            return true;
        }
        return false;
    }

    private boolean handleSilverfishPetBomb(){
        if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().equals("§7§nSilverfish " + Messages.PET_BOMB.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_SILVERFISH_BOMB_USAGE)){
                ItemStack item = ItemUtils.itemstack(Material.MONSTER_EGG, 1, p.getName(), 60);
                Location pl = p.getLocation();
                Location l = new Location(p.getWorld(), pl.getX(), pl.getY() +1, pl.getZ());

                final Item iEn = p.getWorld().dropItem(l, item);
                iEn.setVelocity(p.getLocation().getDirection().multiply(1.1));

                api.getGadgets().getSilverFishBombs().add(iEn);

                omp.resetCooldown(Cooldowns.PET_SILVERFISH_BOMB_USAGE);
            }

            return true;
        }
        return false;
    }

    private boolean handleSheepPetDisco(){
        if(item.getType() == Material.WOOL && item.getItemMeta().getDisplayName().startsWith("§f§nSheep Disco")){
            e.setCancelled(true);
            omp.updateInventory();

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§nSheep Disco§7 (" + Utils.statusString(omp.getLanguage(), !omp.hasPetSheepDisco()) + "§7)");
            item.setItemMeta(meta);

            p.sendMessage(Messages.PET_TOGGLE_SHEEP_DISCO.get(omp));
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            omp.setPetSheepDisco(!omp.hasPetSheepDisco());

            return true;
        }
        return false;
    }

    private boolean handleSheepPetColor(){
        if(item.getType() == Material.INK_SACK && item.getItemMeta().getDisplayName().startsWith("§f§n" + Messages.PET_CHANGE_COLOR.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Sheep s = (Sheep) omp.getPet();
            DyeColor c = ColorUtils.getNext(s);
            item.setDurability(c.getDyeData());

            s.setColor(c);

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§n" + Messages.PET_CHANGE_COLOR.get(omp) + "§7 (" + ColorUtils.getName(c) + "§7)");
            item.setItemMeta(meta);

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleCowPetMilkExplosion(){
        if(item.getType() == Material.MILK_BUCKET && item.getItemMeta().getDisplayName().equals("§f§nMilk Explosion")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_MILK_EXPLOSION)){
                ItemStack item = ItemUtils.itemstack(Material.MILK_BUCKET, 1, p.getName());

                final Item iEn = p.getWorld().dropItem(p.getLocation(), item);
                iEn.setVelocity(p.getLocation().getDirection().multiply(0.8));

                new BukkitRunnable(){
                    public void run(){
                        Location l = iEn.getLocation();

                        FireWork fw = new FireWork(l.subtract(0, 1, 0));
                        fw.getBuilder().withColor(Color.WHITE);
                        fw.getBuilder().withColor(Color.WHITE);
                        fw.getBuilder().withFade(Color.WHITE);
                        fw.getBuilder().with(FireworkEffect.Type.BALL_LARGE);
                        fw.getBuilder().withFlicker();
                        fw.getBuilder().withTrail();
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

                omp.resetCooldown(Cooldowns.PET_MILK_EXPLOSION);
            }

            return true;
        }
        return false;
    }

    private boolean handleCowPetAge(){
        if(item.getType() == Material.RAW_BEEF && item.getItemMeta().getDisplayName().equals("§c§n" + Messages.PET_CHANGE_AGE.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Cow cow = (Cow) omp.getPet();

            if(cow.isAdult()){
                cow.setBaby();
                p.sendMessage(Messages.PET_CHANGE_AGE_BABY.get(omp, omp.getPetName(Pet.COW), "§8"));
                item.setAmount(1);
            }
            else{
                cow.setAdult();
                p.sendMessage(Messages.PET_CHANGE_AGE_ADULT.get(omp, omp.getPetName(Pet.COW), "§8"));
                item.setAmount(2);
            }

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleWolfPetAge(){
        if(item.getType() == Material.BONE && item.getItemMeta().getDisplayName().equals("§7§n" + Messages.PET_CHANGE_AGE.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Wolf wolf = (Wolf) omp.getPet();

            if(wolf.isAdult()){
                wolf.setBaby();
                p.sendMessage(Messages.PET_CHANGE_AGE_BABY.get(omp, omp.getPetName(Pet.WOLF), "§7"));
                item.setAmount(1);
            }
            else{
                wolf.setAdult();
                p.sendMessage(Messages.PET_CHANGE_AGE_ADULT.get(omp, omp.getPetName(Pet.WOLF), "§7"));
                item.setAmount(2);
            }

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleWolfPetBark(){
        if(item.getType() == Material.COOKED_BEEF && item.getItemMeta().getDisplayName().equals("§6§nBark")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_BARK)){
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WOLF_GROWL, 10, 1);

                for(Entity en : p.getNearbyEntities(3, 3, 3)){
                    if(en instanceof Player){
                        Player p2 = (Player) en;
                        OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
                        if(omp2.canReceiveVelocity())
                            p2.setVelocity(p.getLocation().getDirection().subtract(p2.getLocation().getDirection()).multiply(4));
                    }
                }

                for(int iB = 0; iB < 20; iB++){
                    ItemStack item = new ItemStack(Material.BONE, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("Bark " + iB);
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

                omp.resetCooldown(Cooldowns.PET_BARK);
            }

            return true;
        }
        return false;
    }

    private boolean handleSlimePetJump(){
        if(item.getType() == Material.LEATHER_BOOTS && item.getItemMeta().getDisplayName().equals("§6§nSuper Jump")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_JUMP)){
                omp.getPet().setVelocity(new Vector(0, 3, 0));

                omp.resetCooldown(Cooldowns.PET_JUMP);
            }

            return true;
        }
        return false;
    }

    private boolean handleSlimePetSize(){
        if(item.getType() == Material.SLIME_BALL && item.getItemMeta().getDisplayName().equals("§a§n" + Messages.PET_CHANGE_SIZE.get(omp))){
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

            p.sendMessage(Messages.PET_SIZE_CHANGED.get(omp, omp.getPetName(Pet.SLIME), size + "", "§a"));
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handlePetPigBabies(){
        if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().equals("§d§nBaby Pigs§7 (" + Utils.statusString(omp.getLanguage(), omp.hasPetBabyPigs()) + "§7)")){
            e.setCancelled(true);
            omp.updateInventory();

            ItemStack item = ItemUtils.itemstack(Material.MONSTER_EGG, 1, "§d§nBaby Pigs§7 (" + Utils.statusString(omp.getLanguage(), !omp.hasPetBabyPigs()) + "§7)", 90);
            p.getInventory().setItem(2, item);

            p.sendMessage(Messages.PET_TOGGLE_BABY_PIGS.get(omp));
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            omp.setPetBabyPigs(!omp.hasPetBabyPigs());

            if(omp.hasPetBabyPigs()){
                List<Entity> list = new ArrayList<>();
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

            return true;
        }
        return false;
    }

    private boolean handlePetPigAge(){
        if(item.getType() == Material.PORK && item.getItemMeta().getDisplayName().equals("§d§n" + Messages.PET_CHANGE_AGE.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            Pig pig = (Pig) omp.getPet();

            if(pig.isAdult()){
                pig.setBaby();
                p.sendMessage(Messages.PET_CHANGE_AGE_BABY.get(omp, omp.getPetName(Pet.PIG), "§d"));
                item.setAmount(1);
            }
            else{
                pig.setAdult();
                p.sendMessage(Messages.PET_CHANGE_AGE_ADULT.get(omp, omp.getPetName(Pet.PIG), "§d"));
                item.setAmount(2);
            }

            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleMagmaCubePetSize(){
        if(item.getType() == Material.MAGMA_CREAM && item.getItemMeta().getDisplayName().equals("§c§n" + Messages.PET_CHANGE_SIZE.get(omp))){
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

            p.sendMessage(Messages.PET_SIZE_CHANGED.get(omp, omp.getPetName(Pet.MAGMA_CUBE), size + "", "§c"));
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

            return true;
        }
        return false;
    }

    private boolean handleMagmaCubePetFireball(){
        if(item.getType() == Material.FIREBALL && item.getItemMeta().getDisplayName().equals("§6§nFireball")){
            api.getGadgets().getFireballs().add(p.launchProjectile(Fireball.class));

            return true;
        }
        return false;
    }

    private boolean handleMushroomCowPetShroomTrail(){
        if(item.getType() == Material.HUGE_MUSHROOM_1 || item.getType() == Material.HUGE_MUSHROOM_2){
            if(item.getItemMeta().getDisplayName().equals("§4§nShroom Trail§7 (" + Utils.statusString(omp.getLanguage(), omp.hasPetShroomTrail()) + "§7)")){
                e.setCancelled(true);
                omp.updateInventory();

                ItemStack item = ItemUtils.itemstack(Material.HUGE_MUSHROOM_2, 1, "§4§nShroom Trail§7 (" + Utils.statusString(omp.getLanguage(), !omp.hasPetShroomTrail()) + "§7)", 14);
                if(omp.hasPetShroomTrail())
                    item.setType(Material.HUGE_MUSHROOM_1);

                p.getInventory().setItem(2, item);

                p.sendMessage(Messages.PET_TOGGLE_SHROOM_TRAIL.get(omp));
                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

                omp.setPetShroomTrail(!omp.hasPetShroomTrail());
            }

            return true;
        }
        return false;
    }

    private boolean handleMushroomCowPetFirework(){
        if(item.getType() == Material.FIREWORK && item.getItemMeta().getDisplayName().equals("§c§nBaby Firework")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_BABY_FIREWORK)){

                final MushroomCow cow = (MushroomCow) p.getWorld().spawnEntity(p.getLocation(), EntityType.MUSHROOM_COW);
                cow.setAge(1);
                cow.setAgeLock(true);
                cow.setRemoveWhenFarAway(false);
                cow.setVelocity(p.getLocation().getDirection().multiply(1.2).setY(2));
                cow.setMaxHealth((double) Integer.MAX_VALUE);

                new BukkitRunnable(){
                    public void run(){
                        FireWork fw = new FireWork(cow.getLocation());

                        fw.getBuilder().withColor(Color.RED);
                        fw.getBuilder().withColor(Color.RED);
                        fw.getBuilder().withFade(Color.RED);
                        fw.getBuilder().with(FireworkEffect.Type.BALL);
                        fw.getBuilder().withFlicker();
                        fw.getBuilder().withTrail();
                        fw.build();
                        fw.explode();

                        cow.remove();
                    }
                }.runTaskLater(api, 30);

                omp.resetCooldown(Cooldowns.PET_BABY_FIREWORK);
            }

            return true;
        }
        return false;
    }

    private boolean handleSpiderPetWebs(){
        if(item.getType() == Material.WEB && item.getItemMeta().getDisplayName().equals("§f§nWebs")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_WEBS)){
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

                omp.resetCooldown(Cooldowns.PET_WEBS);
            }

            return true;
        }
        return false;
    }

    private boolean handleSpiderPetLauncher(){
        if(item.getType() == Material.SPIDER_EYE && item.getItemMeta().getDisplayName().equals("§5§nSpider Launcher")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_SPIDER_LAUNCHER)){
                final Spider s = (Spider) p.getWorld().spawnEntity(p.getLocation(), EntityType.SPIDER);
                s.setVelocity(p.getLocation().getDirection().multiply(1.5));
                s.setRemoveWhenFarAway(false);

                new BukkitRunnable(){
                    public void run(){
                        s.remove();
                    }
                }.runTaskLater(api, 80);

                omp.resetCooldown(Cooldowns.PET_SPIDER_LAUNCHER);
            }

            return true;
        }
        return false;
    }

    private boolean handleSquidPetInkBomb(){
        if(item.getType() == Material.INK_SACK && item.getItemMeta().getDisplayName().equals("§8§nInk " + Messages.PET_BOMB.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.PET_INK_BOMB)){
                e.setCancelled(true);
                omp.updateInventory();

                Item itemEn = p.getWorld().dropItem(p.getLocation(), ItemUtils.itemstack(Material.INK_SACK, 1, "§8§nInk Bomb " + p.getName()));
                itemEn.setVelocity(p.getLocation().getDirection().multiply(1.3));
                itemEn.setPickupDelay(Integer.MAX_VALUE);
                api.getGadgets().getInkBombs().add(itemEn);
                api.getGadgets().getInkBombTime().put(itemEn, 10 * 3);

                omp.resetCooldown(Cooldowns.PET_INK_BOMB);
            }

            return true;
        }
        return false;
    }

    private boolean handleSquidPetWaterSpout(){
        if(item.getType() == Material.WATER_BUCKET && item.getItemMeta().getDisplayName().equals("§9§nWater Spout")){
            e.setCancelled(true);
            omp.updateInventory();

            FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.STAINED_GLASS, (byte) 11);
            block.setVelocity(p.getLocation().getDirection().multiply(1.1));
            block.setDropItem(false);

            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_WATER_AMBIENT, 6, 1);

            return true;
        }
        return false;
    }

    public boolean handleGadgets(){
        if(!api.getServerPlugin().gadgetsEnabled())
            return false;

        if(handleMagmaCubeSoccer()){}
        else if(handleSwapTeleporter()){}
        else if(handleCreeperLauncher()){}
        else if(handlePaintballs()){}
        else if(handleBookExplosion()){}
        else if(handleSnowGolemAttack()){}
        else if(handlePetRide()){}
        else if(handleStacker()){}
        else if(handleFlameThrower()){}
        else if(handleFireworkGun()){}
        else if(handleGrapplingHook()){}
        else return false;

        return true;
    }

    /* Gadgets */
    private boolean handleMagmaCubeSoccer(){
        if(item.getType() == Material.MAGMA_CREAM && item.getItemMeta().getDisplayName().equals("§c§nMagmaCube Soccer")){
            if(omp.getSoccerMagmaCube() == null){
                MagmaCube mc = (MagmaCube) p.getWorld().spawnEntity(p.getLocation(), EntityType.MAGMA_CUBE);
                mc.setSize(1);
                mc.setRemoveWhenFarAway(false);
                mc.setCustomName("§cSoccer Ball");
                mc.setCustomNameVisible(true);

                api.getGadgets().getSoccerMagmaCubes().add(mc);
                omp.setSoccerMagmaCube(mc);

                p.sendMessage(Messages.PET_ENABLE_MAGMACUBE_BALL.get(omp));
                p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, 152);
            }
            else{
                omp.getSoccerMagmaCube().teleport(p.getLocation());
                p.sendMessage(Messages.PET_TELEPORT_MAGMACUBE_BALL.get(omp));
                p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, 152);
            }

            return true;
        }
        return false;
    }

    private boolean handleSwapTeleporter(){
        if(item.getType() == Material.EYE_OF_ENDER && item.getItemMeta().getDisplayName().equals("§2§nSwap Teleporter")){
            e.setCancelled(true);

            if(omp.canReceiveVelocity()){
                if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
                    if(!omp.onCooldown(Cooldowns.SWAP_TELEPORTER)){
                        ItemStack item = new ItemStack(Material.EYE_OF_ENDER, 1);
                        final Entity en = p.getWorld().dropItem(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), item);
                        en.setVelocity(p.getLocation().getDirection().multiply(1.5));

                        if(omp.getSwapTeleporter() != null){
                            api.getGadgets().getSwapTeleporter().remove(omp.getSwapTeleporter());
                            omp.setSwapTeleporter(null);
                        }

                        api.getGadgets().getSwapTeleporter().put(en, omp);

                        new BukkitRunnable(){
                            public void run(){
                                if(api.getGadgets().getSwapTeleporter().containsKey(en)){
                                    api.getGadgets().getSwapTeleporter().remove(en);
                                    en.remove();
                                    omp.setSwapTeleporter(null);
                                }
                            }
                        }.runTaskLater(api, 100);

                        omp.resetCooldown(Cooldowns.SWAP_TELEPORTER);
                    }
                }
            }

            return true;
        }
        return false;
    }

    private boolean handleCreeperLauncher(){
        if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§a§nCreeper Launcher")){
            e.setCancelled(true);
            omp.updateInventory();

            if(omp.canReceiveVelocity()){
                if(!omp.onCooldown(Cooldowns.CREEPER_LAUNCHER)){
                    Creeper creeper = (Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
                    creeper.setPowered(true);
                    creeper.setVelocity(p.getLocation().getDirection().normalize().multiply(1.5));

                    api.getGadgets().getCreeperLaunched().add(creeper);

                    omp.resetCooldown(Cooldowns.CREEPER_LAUNCHER);
                }
            }

            return true;
        }
        return false;
    }

    private boolean handlePaintballs(){
        if(item.getType() == Material.SNOW_BALL && item.getItemMeta().getDisplayName().equals("§f§nPaintballs")){
            e.setCancelled(true);
            omp.updateInventory();

            api.getGadgets().getPaintBalls().add(p.launchProjectile(Snowball.class));

            return true;
        }
        return false;
    }

    private boolean handleBookExplosion(){
        if(item.getType() == Material.BOOK && item.getItemMeta().getDisplayName().equals("§7§nBook Explosion")){
            e.setCancelled(true);
            omp.updateInventory();

            if(omp.canReceiveVelocity()){
                if(!omp.onCooldown(Cooldowns.BOOK_EXPLOSION)){
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);

                    for(int i = 1; i <= 12; i++){
                        ItemStack item = ItemUtils.itemstack(Material.PAPER, 1, "Paper " + i);
                        final Item paper = p.getWorld().dropItem(p.getLocation(), item);
                        paper.setVelocity(Utils.randomVelocity());

                        new BukkitRunnable(){
                            @Override
                            public void run(){
                                paper.remove();
                            }
                        }.runTaskLater(api, 200);

                        omp.resetCooldown(Cooldowns.BOOK_EXPLOSION);
                    }
                }
            }

            return true;
        }
        return false;
    }

    private boolean handleSnowGolemAttack(){
        if(item.getType() == Material.PUMPKIN && item.getItemMeta().getDisplayName().equals("§6§nSnowman Attack")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(Cooldowns.SGA_USAGE)){
                Item iEn = p.getWorld().dropItem(p.getLocation(), ItemUtils.itemstack(Material.PUMPKIN, 1, p.getName()));
                iEn.setVelocity(p.getLocation().getDirection().multiply(0.5));

                omp.setSgaSeconds(0);
                omp.setSgaItem(iEn);

                for(Player player : Bukkit.getOnlinePlayers()){
                    player.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 5, 1);
                    player.sendMessage(Messages.PET_SUMMON_SGA.get(OMPlayer.getOMPlayer(player), omp.getName()));
                }

                omp.resetCooldown(Cooldowns.SGA_USAGE);
            }

            return true;
        }
        return false;
    }

    private boolean handlePetRide(){
        if(item.getType() == Material.SADDLE && item.getItemMeta().getDisplayName().equals("§e§nPet Ride")){
            e.setCancelled(true);
            omp.updateInventory();

            return true;
        }
        return false;
    }

    private boolean handleStacker(){
        if(item.getType() == Material.LEASH){
            e.setCancelled(true);
            omp.updateInventory();

            return true;
        }
        return false;
    }

    private boolean handleFlameThrower(){
        if(item.getType() == Material.BLAZE_POWDER && item.getItemMeta().getDisplayName().equals("§e§nFlame Thrower")){
            e.setCancelled(true);

            FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.FIRE, (byte) 0);
            block.setVelocity(p.getLocation().getDirection().multiply(1.1));
            block.setDropItem(false);

            return true;
        }
        return false;
    }

    private boolean handleFireworkGun(){
        if(item.getType() == Material.FIREBALL && item.getItemMeta().getDisplayName().startsWith("§c§nFirework Gun")){
            e.setCancelled(true);
            omp.updateInventory();

            if(omp.getFireworkPasses() != 0){
                FireWork fw = new FireWork(p.getLocation());
                fw.applySettings(omp.getFireworkSettings());
                fw.setVelocity(p.getLocation().getDirection().multiply(0.2));

                omp.removeFireworkPass();

                ItemStack item = ItemUtils.itemstack(Material.FIREBALL, 1, "§c§nFirework Gun§r §c(§6" + omp.getFireworkPasses() + "§c)");
                p.getInventory().setItem(api.getServerPlugin().getGadgetSlot(), new ItemStack(item));
            }
            else{
                p.sendMessage(Messages.NO_FIREWORK_PASSES.get(omp));
            }

            return true;
        }
        return false;
    }

    public boolean handleGrapplingHook(){
        return item.getType() != Material.FISHING_ROD || !item.getItemMeta().getDisplayName().equals("§7§nGrappling Hook");
    }
}
