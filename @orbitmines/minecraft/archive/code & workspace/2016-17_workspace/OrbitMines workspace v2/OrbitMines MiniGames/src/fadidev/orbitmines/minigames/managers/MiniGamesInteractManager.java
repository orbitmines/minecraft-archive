package fadidev.orbitmines.minigames.managers;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.inventory.perks.CosmeticPerksInv;
import fadidev.orbitmines.api.managers.InteractManager;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.perks.Trail;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesCooldowns;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.data.SkywarsData;
import fadidev.orbitmines.minigames.handlers.data.SurvivalGamesData;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.inventories.GameEffectsInv;
import fadidev.orbitmines.minigames.inventories.StatsInv;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MiniGamesInteractManager extends InteractManager {

    /*
     * TODO
     * Not the best code: (Old Code)
     * Should edit this shortly.
     */

    private OrbitMinesMiniGames miniGames;
    private MiniGamesPlayer omp;

    public MiniGamesInteractManager(PlayerInteractEvent e) {
        super(e);

        miniGames = OrbitMinesMiniGames.getMiniGames();
        omp = MiniGamesPlayer.getMiniGamesPlayer(e.getPlayer());
    }

    public boolean handleCosmeticPerks(){
        if(item.getType() == Material.ENDER_CHEST && item.getItemMeta().getDisplayName().equals("§9§nCosmetic Perks")){
            e.setCancelled(true);
            omp.updateInventory();

            if(omp.getArena().getState() == State.WAITING)
                new CosmeticPerksInv().open(p);

            return true;
        }
        return false;
    }

    public void handleSpectator(){
        if(omp.getArena() != null && omp.getArena().isSpectator(omp) && !omp.isOpMode())
            e.setCancelled(true);
    }

    public boolean handleTeleporter(){
        if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§nTeleporter")){
            e.setCancelled(true);
            omp.updateInventory();

            omp.getArena().getTeleporterInv().open(p);

            return true;
        }
        return false;
    }

    public boolean handleBackToHub(){
        if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().endsWith("Hub")){
            e.setCancelled(true);
            omp.updateInventory();

            if(omp.getArena() != null)
                omp.getArena().leave(omp);

            return true;
        }
        return false;
    }



    public boolean handleGameEffects(){
        if(item.getType() == Material.BREWING_STAND_ITEM && item.getItemMeta().getDisplayName().equals("§e§nGame Effects")){
            e.setCancelled(true);
            omp.updateInventory();

            if(omp.getArena() != null)
                new GameEffectsInv(omp.getArena().getType()).open(p);

            return true;
        }
        return false;
    }

    public boolean handleStats(){
        if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§2§nStats")){
            e.setCancelled(true);

            new StatsInv().open(p);

            return true;
        }
        return false;
    }

    public void handleChests(){
        Arena arena = omp.getArena();

        if(arena != null && (arena.getType() == MiniGameType.SURVIVAL_GAMES || arena.getType() == MiniGameType.SKYWARS) && arena.isPlayer(omp) && b != null && (b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST)){
            Chest c = (Chest) b.getState();

            switch(arena.getType()){
                case SURVIVAL_GAMES:
                    SurvivalGamesData sgData = (SurvivalGamesData) arena.getData();
                    if(!sgData.getLootedChests().contains(c)){
                        sgData.randomChest(omp, c);
                        sgData.getLootedChests().add(c);

                        for(Block b2 : Arrays.asList(b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST))){
                            if(b2 != null && b2.getType() == b.getType()){
                                Chest c2 = (Chest) b2.getState();
                                sgData.getLootedChests().add(c2);
                            }
                        }
                    }
                    break;
                case SKYWARS:
                    SkywarsData swData = (SkywarsData) arena.getData();
                    if(!swData.getLootedChests().contains(c)){
                        if(!swData.getPlacedChests().contains(b)){
                            swData.randomChest(c);
                            swData.getLootedChests().add(c);

                            for(Block b2 : Arrays.asList(b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST))){
                                if(b2 != null && b2.getType() == b.getType()){
                                    Chest c2 = (Chest) b2.getState();
                                    swData.getLootedChests().add(c2);
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }

    public boolean handleFeatherAttack(){
        if(item.getType() == Material.FEATHER && item.getItemMeta().getDisplayName().equals("§f§lFeather Attack")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(MiniGamesCooldowns.FEATHER_ATTACK)){
                p.sendMessage(MiniGamesMessages.USE_ABILITY.get(omp, "§f§lFeather Attack"));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_CHICKEN_HURT, 5, 1);

                for(Entity en : p.getNearbyEntities(2, 2, 2)){
                    if(en instanceof Player){
                        Player player = (Player) en;
                        MiniGamesPlayer omplayer = MiniGamesPlayer.getMiniGamesPlayer(player);

                        if(!omplayer.getArena().isSpectator(omplayer)){
                            Vector v = omplayer.getChickenFightPlayer().getVelocity(player.getLocation().subtract(p.getLocation()).toVector().normalize().multiply(2)).add(new Vector(0, omplayer.getChickenFightPlayer().getKnockbackMotifier() / 2, 0));
                            player.setVelocity(v);
                            player.damage(Utils.random(2, 4), p);
                        }
                    }
                }

                for(int i = 0; i < 25; i++){
                    final Item item = p.getWorld().dropItem(p.getLocation(), ItemUtils.itemstack(Material.FEATHER, 1, "Feather " + p.getName() + i));
                    item.setPickupDelay(Integer.MAX_VALUE);
                    item.setVelocity(Utils.randomVelocity());

                    new BukkitRunnable(){
                        public void run(){
                            item.remove();
                        }
                    }.runTaskLater(miniGames, 40);
                }

                omp.resetCooldown(MiniGamesCooldowns.FEATHER_ATTACK);
            }

            return true;
        }
        return false;
    }

    public boolean handleEggBomb(){
        if(item.getType() == Material.EGG && item.getItemMeta().getDisplayName().equals("§f§lEgg Bomb")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(MiniGamesCooldowns.EGG_BOMB)){
                p.sendMessage(MiniGamesMessages.USE_ABILITY.get(omp, "§f§lEgg Bomb"));
                p.getWorld().playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

                p.launchProjectile(Egg.class);

                omp.resetCooldown(MiniGamesCooldowns.EGG_BOMB);
            }

            return true;
        }
        return false;
    }

    public boolean handleFireShield(){
        if(item.getType() == Material.FIREBALL && item.getItemMeta().getDisplayName().equals("§f§lFire Shield")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(MiniGamesCooldowns.FIRE_SHIELD)){
                p.sendMessage(MiniGamesMessages.USE_ABILITY.get(omp, "§f§lFire Shield"));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 5, 1);

                omp.parseCylinderTrail(Trail.MOB_SPAWNER);

                for(Entity en : p.getNearbyEntities(2.5, 2.5, 2.5)){
                    if(en instanceof Player){
                        Player player = (Player) en;

                        player.setFireTicks(80);
                        player.playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 5, 1);
                    }
                }

                omp.resetCooldown(MiniGamesCooldowns.FIRE_SHIELD);
            }

            return true;
        }
        return false;
    }

    public boolean handleIronFist(){
        if(item.getType() == Material.IRON_INGOT && item.getItemMeta().getDisplayName().equals("§f§lIron Fist")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.onCooldown(MiniGamesCooldowns.IRON_FIST)){
                if(p.getHealth() > 2){
                    p.sendMessage(MiniGamesMessages.USE_ABILITY.get(omp, "§f§lIron Fist"));
                    p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 0.1F);


                    for(Entity en : p.getNearbyEntities(2.5, 2.5, 2.5)){
                        if(en instanceof Player){
                            Player player = (Player) en;
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 2));
                        }
                    }
                    p.damage(2D);

                    omp.resetCooldown(MiniGamesCooldowns.IRON_FIST);
                }
                else{
                    if(!omp.onCooldown(Cooldowns.MESSAGE)){
                        p.sendMessage(MiniGamesMessages.DIE_TO_ABILITY.get(omp, "§f§lIron Fist"));

                        omp.resetCooldown(Cooldowns.MESSAGE);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
