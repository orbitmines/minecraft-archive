package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Fadi on 1-10-2016.
 */
public class DamageEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player))
            return;

        Player p = (Player) e.getEntity();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(arena == null)
            return;

        if(arena.getState() != State.IN_GAME || arena.isSpectator(omp) || arena.getType() == MiniGameType.SKYWARS && (arena.getMinutes() == 15 && arena.getSeconds() == 0 || arena.getMinutes() == 14 && arena.getSeconds() >= 57) || arena.getType() == MiniGameType.CHICKEN_FIGHT && e.getCause() == EntityDamageEvent.DamageCause.FALL){
            e.setCancelled(true);
        }

        if(arena.getType() == MiniGameType.CHICKEN_FIGHT && arena.getState() == State.IN_GAME && !arena.isSpectator(omp)){
            Kit kit = omp.getChickenFightPlayer().getKitSelected();

            if(kit != null){
                if(kit.getName().equals(TicketType.CHICKEN_MAMA_KIT.toString())){
                    //e.setDamage(e.getDamage() * 1);
                }
                else if(kit.getName().equals(TicketType.BABY_CHICKEN_KIT.toString())){
                    e.setDamage(e.getDamage() * 1.1);
                }
                else if(kit.getName().equals(TicketType.HOT_WING_KIT.toString())){
                    e.setDamage(e.getDamage() * 1.5);
                }
                else if(kit.getName().equals(TicketType.CHICKEN_WARLORD_KIT.toString())){
                    e.setDamage(e.getDamage() * 0.8);
                }
                else if(kit.getName().equals(TicketType.CHICKEN_KIT.toString())){
                    e.setDamage(e.getDamage() * 0.9);
                }
            }
        }
    }
}
