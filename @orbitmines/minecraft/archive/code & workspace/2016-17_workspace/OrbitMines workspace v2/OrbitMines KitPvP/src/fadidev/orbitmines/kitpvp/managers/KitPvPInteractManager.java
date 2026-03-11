package fadidev.orbitmines.kitpvp.managers;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Particle;
import fadidev.orbitmines.api.inventory.perks.CosmeticPerksInv;
import fadidev.orbitmines.api.managers.InteractManager;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPCooldowns;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMap;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.inventories.BoosterInv;
import fadidev.orbitmines.kitpvp.inventories.KitSelectorInv;
import fadidev.orbitmines.kitpvp.utils.enums.ItemType;
import fadidev.orbitmines.kitpvp.utils.enums.ProjectileType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Color.RED;

/**
 * Created by Fadi on 10-9-2016.
 */
public class KitPvPInteractManager extends InteractManager {

    /*
     * TODO
     * Not the best code: (Old Code)
     * Should edit this shortly.
     */

    private OrbitMinesKitPvP kitPvP;
    private KitPvPPlayer omp;

    public KitPvPInteractManager(PlayerInteractEvent e) {
        super(e);

        kitPvP = OrbitMinesKitPvP.getKitPvP();
        omp = KitPvPPlayer.getKitPvPPlayer(e.getPlayer());
    }

    public boolean handleWrittenBook(){
        return item.getType() != Material.WRITTEN_BOOK;
    }

    public boolean handleTeleporter(){
        if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§nTeleporter")){
            e.setCancelled(true);
            omp.updateInventory();

            kitPvP.getTeleporterInv().open(p);

            return true;
        }
        return false;
    }

    public boolean handleBackToLobby(){
        if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().endsWith("Lobby")){
            e.setCancelled(true);
            omp.updateInventory();

            if(omp.isSpectator()){
                omp.setSpectator(false);
                p.teleport(kitPvP.getSpawn());
                omp.clearInventory();
                kitPvP.getLobbyKit().get(omp.getLanguage()).setItems(p);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
                p.setFlying(false);
                p.setAllowFlight(false);

                omp.show();
            }

            return true;
        }
        return false;
    }

    public boolean handleCosmeticPerks(){
        if(item.getType() == Material.ENDER_CHEST && item.getItemMeta().getDisplayName().equals("§9§nCosmetic Perks")){
            e.setCancelled(true);
            omp.updateInventory();

            new CosmeticPerksInv().open(p);

            return true;
        }
        return false;
    }

    public boolean handleAchievements(){
        if(item.getType() == Material.EXP_BOTTLE && item.getItemMeta().getDisplayName().equals("§d§nAchievements")){
            e.setCancelled(true);
            omp.updateInventory();

            p.sendMessage("§a§o" + KitPvPMessages.WORD_COMING_SOON.get(omp) + "...");
            // TODO ADD ACHIEVEMENTS \\

            return true;
        }
        return false;
    }

    public boolean handleServerSelector(){
        if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nServer Selector")){
            e.setCancelled(true);
            omp.updateInventory();

            api.getServerSelector().open(p);

            return true;
        }
        return false;
    }

    public boolean handleKitSelector(){
        if(item.getType() == Material.DIAMOND_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§b§nKit Selector")){
            e.setCancelled(true);
            omp.updateInventory();

            new KitSelectorInv().open(p);

            return true;
        }
        return false;
    }

    public boolean handleBoosters(){
        if(item.getType() == Material.GOLD_NUGGET && item.getItemMeta().getDisplayName().equals("§a§nBoosters")){
            e.setCancelled(true);
            omp.updateInventory();

            new BoosterInv().open(p);

            return true;
        }
        return false;
    }

    public void handleSpectator(){
        if(omp.isSpectator() && !omp.isOpMode())
            e.setCancelled(true);
    }

    public void handlePhysicalAction(){
        if(!omp.isOpMode()){
            if(a == Action.PHYSICAL && (b == null || b.getType() != Material.STONE_PLATE && b.getType() != Material.WOOD_PLATE)){
                e.setCancelled(true);
            }
        }
    }

    public void handleSnowball(){
        if(item.getType() == Material.SNOW_BALL){
            if(a == Action.RIGHT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
                e.setCancelled(true);
                omp.updateInventory();
            }
        }
    }

    public void handleVoteSigns(){
        if(b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)){
            KitPvPMap map = KitPvPMap.getKitPvPMap(b.getLocation());

            if(map == null || omp.hasVoted())
                return;

            omp.getPlayer().playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
            omp.vote(map);
        }
    }

    public void handleKitPvPEnchantments(){
        if(item.getItemMeta().getLore() != null){
            List<String> lore = item.getItemMeta().getLore();

            if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){
                if(lore.contains(ItemType.FIRE_SPELL_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.FIRE_SPELL_I)){
                        Vector velocity = p.getLocation().getDirection().multiply(1.1);
                        double speed = velocity.length();
                        Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
                        double spray = 5D;

                        for(int i = 0; i < 3; i++){
                            FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.FIRE, (byte) 0);

                            block.setVelocity(new Vector(direction.getX() + (Math.random() - 1.5) / spray, direction.getY() + (Math.random() - 1.5) / spray, direction.getZ() + (Math.random() - 1.5) / spray).normalize().multiply(speed));
                            block.setDropItem(false);
                        }

                        omp.resetCooldown(KitPvPCooldowns.FIRE_SPELL_I);
                    }
                }
                else if(lore.contains(ItemType.FIRE_SPELL_II.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.FIRE_SPELL_II)){
                        Vector velocity = p.getLocation().getDirection().multiply(1.1);
                        double speed = velocity.length();
                        Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
                        double spray = 5D;

                        for(int i = 0; i < 8; i++){
                            FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.FIRE, (byte) 0);

                            block.setVelocity(new Vector(direction.getX() + (Math.random() - 1.5) / spray, direction.getY() + (Math.random() - 1.5) / spray, direction.getZ() + (Math.random() - 1.5) / spray).normalize().multiply(speed));
                            block.setDropItem(false);
                        }

                        omp.resetCooldown(KitPvPCooldowns.FIRE_SPELL_II);
                    }
                }
                else if(lore.contains(ItemType.POTION_LAUNCHER_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.POTION_LAUNCHER_I)){
                        ItemStack item = ItemUtils.addEffect(new ItemStack(Material.SPLASH_POTION, 1), PotionEffectType.HARM, 0, 0, false);

                        ThrownPotion sp = p.launchProjectile(ThrownPotion.class);
                        sp.setItem(item);
                        sp.setVelocity(p.getLocation().getDirection().multiply(2));

                        omp.resetCooldown(KitPvPCooldowns.POTION_LAUNCHER_I);
                    }
                }
                else if(lore.contains(ItemType.WITHER_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.WITHER_I)){
                        ItemStack item = ItemUtils.itemstack(Material.REDSTONE, 1, "§b§lNecromancer §a§lLvL 3§8 || §cSoul");

                        if(p.getInventory().containsAtLeast(item, 1)){
                            Vector velocity = p.getEyeLocation().getDirection().multiply(1);
                            double speed = velocity.length();
                            Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
                            double spray = 3.5D;

                            for(int i = 0; i < 3; i++){
                                WitherSkull ws = p.launchProjectile(WitherSkull.class);
                                ws.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5) / spray, direction.getY() + (Math.random() - 0.5) / spray, direction.getZ() + (Math.random() - 0.5) / spray).normalize().multiply(speed));
                            }

                            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 4, 2);
                            p.getInventory().removeItem(item);
                            omp.updateInventory();

                            omp.resetCooldown(KitPvPCooldowns.WITHER_I);
                        }
                    }
                }
                else if(lore.contains(ItemType.HEALING_I.getName())){
                    if(p.isSneaking() && !omp.onCooldown(KitPvPCooldowns.HEALING_I)){
                        omp.addPotionEffect(PotionEffectType.REGENERATION, 4, 2);

                        omp.resetCooldown(KitPvPCooldowns.HEALING_I);
                    }
                }
                else if(lore.contains(ItemType.HEALING_II.getName())){
                    if(p.isSneaking() && !omp.onCooldown(KitPvPCooldowns.HEALING_II)){
                        omp.addPotionEffect(PotionEffectType.REGENERATION, 5, 2);

                        omp.resetCooldown(KitPvPCooldowns.HEALING_II);
                    }
                }
                else if(lore.contains(ItemType.BARRIER_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.BARRIER_I)){
                        createBarrier(p);
                        omp.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 0);

                        omp.resetCooldown(KitPvPCooldowns.BARRIER_I);
                    }
                }
                else if(lore.contains(ItemType.BARRIER_II.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.BARRIER_II)){
                        createBarrier(p);
                        omp.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 1);

                        omp.resetCooldown(KitPvPCooldowns.BARRIER_II);
                    }
                }
                else if(lore.contains(ItemType.TNT_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.TNT_I)){
                        TNTPrimed tnt = p.getWorld().spawn(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), TNTPrimed.class);
                        tnt.setFuseTicks(15);
                        tnt.setVelocity(p.getEyeLocation().getDirection().multiply(2));

                        omp.resetCooldown(KitPvPCooldowns.TNT_I);
                    }
                }
                else if(lore.contains(ItemType.HEALING_KIT_I.getName())){
                    p.getInventory().removeItem(item);
                    p.setHealth(20);
                }
                else if(lore.contains(ItemType.MAGIC_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.MAGIC_I)){
                        p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 4, 2);
                        for(Entity en : p.getNearbyEntities(5, 5, 5)){
                            if(en instanceof Player){
                                Player p2 = (Player) en;
                                OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
                                omp2.addPotionEffect(PotionEffectType.WITHER, 8, 1);
                                p2.playSound(p2.getLocation(), Sound.ENTITY_WITHER_SHOOT, 4, 2);
                            }
                        }

                        omp.resetCooldown(KitPvPCooldowns.MAGIC_I);
                    }
                }
                else if(lore.contains(ItemType.FISH_ATTACK_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.FISH_ATTACK_I)){
                        p.playSound(p.getLocation(), Sound.WEATHER_RAIN, 4, 4);
                        for(Entity en : p.getNearbyEntities(4, 4, 4)){
                            if(en instanceof Player){
                                Player p2 = (Player) en;
                                OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
                                omp2.addPotionEffect(PotionEffectType.POISON, 4, 2);
                                p2.playSound(p2.getLocation(), Sound.WEATHER_RAIN, 4, 4);
                            }
                        }

                        Particle pa = new Particle(org.bukkit.Particle.WATER_SPLASH, p.getLocation());
                        pa.setSize(2, 2, 2);
                        pa.setAmount(50);
                        pa.send(Bukkit.getOnlinePlayers());

                        omp.resetCooldown(KitPvPCooldowns.FISH_ATTACK_I);
                    }
                }
                else if(lore.contains(ItemType.SHIELD_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.SHIELD_I)){
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 4, 1);
                        omp.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 0);

                        omp.resetCooldown(KitPvPCooldowns.SHIELD_I);
                    }
                }
                else if(lore.contains(ItemType.SHIELD_II.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.SHIELD_II)){
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 4, 1);
                        omp.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 12, 1);

                        omp.resetCooldown(KitPvPCooldowns.SHIELD_II);
                    }
                }
                else if(lore.contains(ItemType.BLOCK_EXPLOSION_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.BLOCK_EXPLOSION_I)){
                        FallingBlock fb = p.getWorld().spawnFallingBlock(p.getLocation().add(0, 1, 0), item.getType(), (byte) item.getDurability());
                        fb.setDropItem(false);
                        fb.setCustomName("BlockExplosion");
                        fb.setVelocity(p.getLocation().getDirection().multiply(0.8));

                        omp.resetCooldown(KitPvPCooldowns.BLOCK_EXPLOSION_I);
                    }
                }
                else if(lore.contains(ItemType.UNDEATH_SUMMON_I.getName())){
                    if(!omp.onCooldown(KitPvPCooldowns.UNDEATH_SUMMON_I)){
                        Location l = p.getLocation();

                        final Entity e1 = Mob.PIG_ZOMBIE.spawnNoAttack(new Location(l.getWorld(), l.getX(), l.getY() +2, l.getZ() +2, l.getYaw(), l.getPitch()), "§4Undeath Baby");
                        final Entity e2 = Mob.PIG_ZOMBIE.spawnNoAttack(new Location(l.getWorld(), l.getX(), l.getY() +2, l.getZ() -2, l.getYaw(), l.getPitch()), "§4Undeath Baby");

                        for(final Entity e : Arrays.asList(e1, e2)){
                            ((PigZombie) e).setBaby(true);
                            e.setCustomNameVisible(true);
                            EntityEquipment ee = ((LivingEntity) e).getEquipment();
                            ee.setItemInHand(ItemUtils.addEnchantment(new ItemStack(Material.GOLD_SWORD), Enchantment.DURABILITY, 20));
                            ee.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.FUCHSIA));
                            ee.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.FUCHSIA));
                            ee.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.FUCHSIA));
                            ee.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.FUCHSIA));
                            ((PigZombie) e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
                            omp.getSummonedUndeath().add(e);

                            new BukkitRunnable(){
                                public void run(){
                                    omp.getSummonedUndeath().remove(e);
                                    if(!e.isDead()){
                                        e.getWorld().playEffect(e.getLocation(), Effect.STEP_SOUND, 152);
                                        e.remove();
                                    }
                                }
                            }.runTaskLater(kitPvP, 300);
                        }

                        omp.resetCooldown(KitPvPCooldowns.UNDEATH_SUMMON_I);
                    }
                }
                else if(lore.contains(ItemType.PAINTBALLS_I.getName())){
                    e.setCancelled(true);
                    omp.updateInventory();

                    if(!omp.onCooldown(KitPvPCooldowns.PAINTBALLS_I)){
                        EnderPearl e = p.launchProjectile(EnderPearl.class);
                        e.setCustomName(item.getItemMeta().getDisplayName());

                        kitPvP.addProjectile(e, ProjectileType.PAINTBALLS_I);

                        omp.resetCooldown(KitPvPCooldowns.PAINTBALLS_I);
                    }
                }
                else{}
            }
            else if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
                if(lore.contains(ItemType.PAINTBALLS_I.getName())){
                    int itemindex = p.getInventory().first(item);

                    String name = item.getItemMeta().getDisplayName();
                    Color color = RED;
                    if(name.endsWith("Red §8|| §bWeapon§f")){
                        name = name.replace("§c§lRed", "§b§lBlue");
                        color = Color.AQUA;
                    }
                    else if(name.endsWith("Blue §8|| §bWeapon§f")){
                        name = name.replace("§b§lBlue", "§0§lBlack");
                        color = Color.BLACK;
                    }
                    else if(name.endsWith("Black §8|| §bWeapon§f")){
                        name = name.replace("§0§lBlack", "§7§lGray");
                        color = Color.SILVER;
                    }
                    else if(name.endsWith("Gray §8|| §bWeapon§f")){
                        name = name.replace("§7§lGray", "§a§lGreen");
                        color = Color.LIME;
                    }
                    else if(name.endsWith("Green §8|| §bWeapon§f")){
                        name = name.replace("§a§lGreen", "§c§lRed");
                        color = Color.RED;
                    }

                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(name);
                    item.setItemMeta(meta);
                    p.getInventory().setItem(itemindex, item);

                    int index = 0;
                    ItemStack[] items = new ItemStack[4];
                    for(ItemStack item : p.getInventory().getArmorContents()){
                        if(item != null){
                            items[index] = ItemUtils.addColor(item, color);
                        }

                        index++;
                    }
                    p.getInventory().setArmorContents(items);

                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                }
            }
        }
    }

    private void createBarrier(Player p){
        World w = p.getWorld();
        Location l = p.getLocation();
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();

        Block b1 = w.getBlockAt(x, y -2, z);
        Block b2 = w.getBlockAt(x + 1, y -2, z);
        Block b3 = w.getBlockAt(x - 1, y -2, z);
        Block b4 = w.getBlockAt(x, y -2, z - 1);
        Block b5 = w.getBlockAt(x + 1, y -2, z - 1);
        Block b6 = w.getBlockAt(x - 1, y -2, z - 1);
        Block b7 = w.getBlockAt(x, y -2, z + 1);
        Block b8 = w.getBlockAt(x + 1, y -2, z + 1);
        Block b9 = w.getBlockAt(x - 1, y -2, z + 1);
        Block b10 = w.getBlockAt(x, y +3, z);
        Block b11 = w.getBlockAt(x + 1, y +3, z);
        Block b12 = w.getBlockAt(x - 1, y +3, z);
        Block b13 = w.getBlockAt(x, y +3, z - 1);
        Block b14 = w.getBlockAt(x + 1, y +3, z - 1);
        Block b15 = w.getBlockAt(x - 1, y +3, z - 1);
        Block b16 = w.getBlockAt(x, y +3, z + 1);
        Block b17 = w.getBlockAt(x + 1, y +3, z + 1);
        Block b18 = w.getBlockAt(x - 1, y +3, z + 1);
        Block b19 = w.getBlockAt(x, y -1, z + 2);
        Block b20 = w.getBlockAt(x + 1, y -1, z + 2);
        Block b21 = w.getBlockAt(x - 1, y -1, z + 2);
        Block b22 = w.getBlockAt(x - 2, y -1, z);
        Block b23 = w.getBlockAt(x - 2, y -1, z + 1);
        Block b24 = w.getBlockAt(x - 2, y -1, z - 1);
        Block b25 = w.getBlockAt(x, y -1, z - 2);
        Block b26 = w.getBlockAt(x + 1, y -1, z - 2);
        Block b27 = w.getBlockAt(x - 1, y -1, z - 2);
        Block b28 = w.getBlockAt(x + 2, y -1, z);
        Block b29 = w.getBlockAt(x + 2, y -1, z + 1);
        Block b30 = w.getBlockAt(x + 2, y -1, z - 1);
        Block b31 = w.getBlockAt(x, y +2, z + 2);
        Block b32 = w.getBlockAt(x + 1, y +2, z + 2);
        Block b33 = w.getBlockAt(x - 1, y +2, z + 2);
        Block b34 = w.getBlockAt(x - 2, y +2, z);
        Block b35 = w.getBlockAt(x - 2, y +2, z + 1);
        Block b36 = w.getBlockAt(x - 2, y +2, z - 1);
        Block b37 = w.getBlockAt(x, y +2, z - 2);
        Block b38 = w.getBlockAt(x + 1, y +2, z - 2);
        Block b39 = w.getBlockAt(x - 1, y +2, z - 2);
        Block b40 = w.getBlockAt(x + 2, y +2, z);
        Block b41 = w.getBlockAt(x + 2, y +2, z + 1);
        Block b42 = w.getBlockAt(x + 2, y +2, z - 1);
        Block b43 = w.getBlockAt(x - 2, y, z + 2);
        Block b44 = w.getBlockAt(x - 2, y + 1, z + 2);
        Block b45 = w.getBlockAt(x - 2, y, z - 2);
        Block b46 = w.getBlockAt(x - 2, y + 1, z - 2);
        Block b47 = w.getBlockAt(x + 2, y, z + 2);
        Block b48 = w.getBlockAt(x + 2, y + 1, z + 2);
        Block b49 = w.getBlockAt(x + 2, y, z - 2);
        Block b50 = w.getBlockAt(x + 2, y + 1, z - 2);
        Block b51 = w.getBlockAt(x, y, z + 3);
        Block b52 = w.getBlockAt(x + 1, y, z + 3);
        Block b53 = w.getBlockAt(x - 1, y, z + 3);
        Block b54 = w.getBlockAt(x, y + 1, z + 3);
        Block b55 = w.getBlockAt(x + 1, y + 1, z + 3);
        Block b56 = w.getBlockAt(x - 1, y + 1, z + 3);
        Block b57 = w.getBlockAt(x, y, z - 3);
        Block b58 = w.getBlockAt(x + 1, y, z - 3);
        Block b59 = w.getBlockAt(x - 1, y, z - 3);
        Block b60 = w.getBlockAt(x, y + 1, z - 3);
        Block b61 = w.getBlockAt(x + 1, y + 1, z - 3);
        Block b62 = w.getBlockAt(x - 1, y + 1, z - 3);
        Block b63 = w.getBlockAt(x + 3, y, z);
        Block b64 = w.getBlockAt(x + 3, y, z - 1);
        Block b65 = w.getBlockAt(x + 3, y, z + 1);
        Block b66 = w.getBlockAt(x + 3, y + 1, z);
        Block b67 = w.getBlockAt(x + 3, y + 1, z - 1);
        Block b68 = w.getBlockAt(x + 3, y + 1, z + 1);
        Block b69 = w.getBlockAt(x - 3, y, z);
        Block b70 = w.getBlockAt(x - 3, y, z - 1);
        Block b71 = w.getBlockAt(x - 3, y, z + 1);
        Block b72 = w.getBlockAt(x - 3, y + 1, z);
        Block b73 = w.getBlockAt(x - 3, y + 1, z - 1);
        Block b74 = w.getBlockAt(x - 3, y + 1, z + 1);

        final HashMap<Block, Material> materials = new HashMap<Block, Material>();
        List<Block> blocks = Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30, b31, b32, b33, b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45, b46, b47, b48, b49, b50, b51, b52, b53, b54, b55, b56, b57, b58, b59, b60, b61, b62, b63, b64, b65, b66, b67, b68, b69, b70, b71, b72, b73, b74);
        final List<Block> emptyBlocks = new ArrayList<>();

        for(Block b : blocks){
            if(b.isEmpty()){
                materials.put(b, b.getType());
                emptyBlocks.add(b);

                if(Utils.RANDOM.nextBoolean())
                    b.setType(Material.LEAVES);
                else
                    b.setType(Material.LOG);
            }
        }

        new BukkitRunnable(){
            public void run(){
                for(Block b : emptyBlocks){
                    b.setType(materials.get(b));
                }
            }
        }.runTaskLater(kitPvP, 100);
    }
}
