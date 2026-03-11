package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMap;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockChangeEvent implements Listener {

	private OrbitMinesKitPvP kitPvP;

	public BlockChangeEvent(){
		kitPvP = OrbitMinesKitPvP.getKitPvP();
	}

	@EventHandler
	public void onChange(final EntityChangeBlockEvent e){
		Entity en = e.getEntity();
		
		if(!(en instanceof FallingBlock))
		    return;
        FallingBlock b = (FallingBlock) en;

        if(b.getMaterial() == Material.FIRE){
            new BukkitRunnable(){
                public void run(){
                    e.getBlock().setType(Material.AIR);
                }
            }.runTaskLater(kitPvP, 100);
        }
        else if(b.getMaterial() == Material.ICE){
            e.setCancelled(true);
        }
        else if(b.getCustomName() != null && b.getCustomName().equals("BlockExplosion")){
            e.setCancelled(true);

            for(Entity ent : en.getNearbyEntities(3, 3, 3)){
                if(!(ent instanceof Player))
                    continue;

                KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer((Player) ent);
                omp.addPotionEffect(PotionEffectType.BLINDNESS, 10, 0);
                omp.addPotionEffect(PotionEffectType.SLOW, 10, 5);
            }

            KitPvPMap map = kitPvP.getCurrentMap();
            List<Material> materials = new ArrayList<>();
            if(map.getMapName().equals("Snow Town")){
                materials.add(Material.SNOW_BLOCK);
                materials.add(Material.PACKED_ICE);
            }
            else if(map.getMapName().equals("Mountain Village")){
                materials.add(Material.GRASS);
                materials.add(Material.DIRT);
                materials.add(Material.GRAVEL);
            }
            else if(map.getMapName().equals("Desert")){
                materials.add(Material.SAND);
                materials.add(Material.CACTUS);
            }

            for(int i = 0; i <= 25; i++){
                FallingBlock fb = en.getWorld().spawnFallingBlock(en.getLocation(), materials.get(new Random().nextInt(materials.size())), (byte) 0);
                fb.setDropItem(false);
                fb.setVelocity(Utils.randomVelocity().multiply(2));
            }
        }
        else{
            en.remove();
            e.setCancelled(true);
        }
	}
}
