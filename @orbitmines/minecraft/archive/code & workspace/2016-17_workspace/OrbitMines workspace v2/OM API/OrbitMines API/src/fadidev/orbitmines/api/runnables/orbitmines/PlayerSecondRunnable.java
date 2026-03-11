package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Particle;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.perks.WardrobeInv;
import fadidev.orbitmines.api.runnables.OMRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class PlayerSecondRunnable extends OMRunnable {

	private OrbitMinesAPI api;

	public PlayerSecondRunnable() {
		super(TimeUnit.SECOND, 1);

        api = OrbitMinesAPI.getApi();
	}

	protected abstract void run(OMPlayer omp);
	protected abstract void giveLobbyItems(OMPlayer omp);

	@Override
	public void run() {
		for(OMPlayer omp : api.getOMPlayers()){
		    Player p = omp.getPlayer();
            
            if(api.getServerPlugin().wardrobeEnabled()){
                if(omp.isWardrobeDisco())
                    omp.discoWardrobe();
                
                if(p.getOpenInventory().getTopInventory().getName() != null){
                    if(p.getOpenInventory().getTopInventory().getName().equals("§0§lWardrobe")){
                        WardrobeInv.setDiscoItem(p.getOpenInventory().getTopInventory(), omp);
                    }
                    else if(p.getOpenInventory().getTopInventory().getName().equals("§0§lConfirm your Purchase") && p.getOpenInventory().getTopInventory().getItem(13).getItemMeta().getDisplayName().endsWith("Disco Armor")){
                        ConfirmInv.setDiscoItem(p.getOpenInventory().getTopInventory(), omp);
                    }
                }
            }

            if(api.getServerPlugin().gadgetsEnabled()){
                if(omp.canReceiveVelocity()){
                    if(p.getOpenInventory().getTopInventory() instanceof AnvilInventory){
                        {
                            ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName("§9§nCosmetic Perks");
                            item.setItemMeta(meta);
                            p.getInventory().setItem(12, item);
                        }
                        {
                            //todo nl
                            ItemStack item = new ItemStack(Material.NAME_TAG, 1);
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName("§f§oClick the §6§oRight§f§o Egg to rename your Pet!");
                            item.setItemMeta(meta);
                            p.getInventory().setItem(14, item);
                        }
                    }
                    else{
                        p.getInventory().setItem(12, null);
                        p.getInventory().setItem(14, null);
                    }
                }

                if(omp.getPet() != null){
                    if(p.getVehicle() != null && p.getVehicle() == omp.getPet()){
                        omp.givePetInventory();
                    }
                    else{
                        if(!omp.isOpMode())
                            giveLobbyItems(omp);
                    }
                }
                else{
                    if(!omp.isOpMode())
                        giveLobbyItems(omp);
                }
            }

            if(api.getServerPlugin().gadgetsEnabled()){
                if(omp.getSgaSeconds() != -1){
                    int seconds = omp.getSgaSeconds();
                    omp.setSgaSeconds(seconds +1);

                    if(omp.getSgaItem() != null && omp.getSgaSnowGolems().size() == 0){
                        Location l = omp.getSgaItem().getLocation();

                        if(seconds <= 10){
                            l.getWorld().playSound(l, Sound.ENTITY_WITHER_SPAWN, 5, 1);
                            Particle pa = new Particle(org.bukkit.Particle.FLAME, l);
                            pa.setSize(1, 1, 1);
                            pa.setAmount(30);
                            pa.send(Bukkit.getOnlinePlayers());
                        }
                        else if(seconds == 11){
                            l.getWorld().playSound(l, Sound.ENTITY_WITHER_DEATH, 5, 1);
                            Snowman s1 = (Snowman) l.getWorld().spawnEntity(l, EntityType.SNOWMAN);
                            Snowman s2 = (Snowman) l.getWorld().spawnEntity(l, EntityType.SNOWMAN);
                            Snowman s3 = (Snowman) l.getWorld().spawnEntity(l, EntityType.SNOWMAN);

                            s1.setPassenger(s2);
                            s2.setPassenger(s3);
                            omp.getSgaItem().remove();
                            omp.setSgaItem(null);

                            List<Entity> snowGolems = omp.getSgaSnowGolems();
                            snowGolems.clear();
                            snowGolems.add(s1);
                            snowGolems.add(s2);
                            snowGolems.add(s3);
                        }
                    }
                }
            }

			run(omp);
		}
	}
}
