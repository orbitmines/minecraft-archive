package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ClickEvent implements Listener {

    private OrbitMinesKitPvP kitPvP;

    public ClickEvent(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            Player p = (Player) e.getWhoClicked();
            KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
            if(!omp.isOpMode() && (e.getSlotType() == InventoryType.SlotType.ARMOR || omp.getKitSelected() == null || omp.isSpectator())){
                e.setResult(Event.Result.DENY);
                omp.updateInventory();
            }

            ItemStack item = e.getCurrentItem();
            if(omp.isSpectator() && !ItemUtils.isNull(item)){
                if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§nTeleporter")){
                    e.setCancelled(true);
                    kitPvP.getTeleporterInv().open(p);

                    return;
                }
                else if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().endsWith("Lobby")){
                    e.setCancelled(true);

                    omp.setSpectator(false);
                    p.teleport(kitPvP.getSpawn());
                    omp.clearInventory();
                    kitPvP.getLobbyKit().get(omp.getLanguage()).setItems(p);
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
                    p.setFlying(false);
                    p.setAllowFlight(false);
                    omp.show();

                    return;
                }
            }

            kitPvP.getOmServer().getClickManager().handleOMInventories(e);
        }
    }
}
