package fadidev.orbitmines.hub.runnables;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.HubMessages;
import fadidev.orbitmines.hub.handlers.ServerPortal;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 7-9-2016.
 */
public class HubPlayerSecondRunnable extends PlayerSecondRunnable {

    private OrbitMinesHub hub;

    public HubPlayerSecondRunnable(){
        hub = OrbitMinesHub.getHub();
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        HubPlayer omp = (HubPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.inBuilderWorld())
            return;

        if(!omp.isInMindcraft()){
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
        else{
            omp.getMindCraftPlayer().giveItems();
        }

        if(!omp.hasPlayersEnabled()){
            for(Player player : Bukkit.getOnlinePlayers()){
                p.hidePlayer(player);
            }
        }

        if(!omp.isOpMode()){
            if(!p.getWorld().getName().equals(hub.getLobby().getName())){
                p.teleport(hub.getSpawn());
            }
            else{
                if(p.getLocation().getY() <= 50 || p.getLocation().distance(hub.getSpawn()) >= 100){
                    p.teleport(hub.getSpawn());
                }
            }

            for(ServerPortal portal : hub.getServerPortals()){
                for(Block b : portal.getBlocks()){
                    if(!b.isEmpty())
                        continue;

                    p.sendBlockChange(b.getLocation(), Material.PORTAL, (byte) (portal.getServer() != Server.SURVIVAL && portal.getServer() != Server.SKYBLOCK && portal.getServer() != Server.FOG ? 2 : 1));
                }
            }
        }

        if(omp.isInLapisParkour()){
            if(hub.getLobby().getName().equals(p.getWorld().getName())){
                if(p.getLocation().distance(hub.getLapisParkour()) > 2){
                    Location l = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() -1, p.getLocation().getZ());

                    Material m = p.getWorld().getBlockAt(l).getType();

                    if(m != Material.GLOWSTONE){
                        if(!p.isSprinting()){
                            p.teleport(hub.getLapisParkour());
                            p.sendMessage("§7" + HubMessages.CANT_STOP_SPRINTING.get(omp) + "!");
                        }
                        else if(p.isOnGround()){
                            if(m == Material.GLOWSTONE){
                                completeLapisParkour(omp);
                            }
                            else{
                                Material block = hub.getType().getLapisParkourBlock();
                                if(m != block && m != Material.AIR){
                                    p.teleport(hub.getLapisParkour());
                                }
                            }
                        }
                        else if(p.getLocation().getY() <= 74){
                            p.teleport(hub.getLapisParkour());
                        }
                    }
                    else{
                        completeLapisParkour(omp);
                    }
                }
            }
            else{
                p.teleport(hub.getLapisParkour());
            }

            if(p.getAllowFlight()){
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage(Messages.CMD_TOGGLE_FLY.get(omp));
            }
        }

        if(!hub.getLobby().getName().equals(p.getWorld().getName()))
            return;

        hub.getMindCraft().updateSigns(omp);
        hub.getMindCraft().updateGame(omp);
    }

    @Override
    protected void giveLobbyItems(OMPlayer omp) {
        hub.getLobbyKit().get(omp.getLanguage()).replaceItems(omp.getPlayer());
    }

    private void completeLapisParkour(HubPlayer omp){
        Player p = omp.getPlayer();

        if(!omp.hasCompletedLapisParkour()){
            omp.setCompletedLapisParkour(true);

            p.teleport(hub.getLapisParkour());
            omp.setInLapisParkour(false);

            omp.addVIPPoints(250);
            Database.get().insert("ParkourCompleted", "uuid", p.getUniqueId().toString());
        }

        for(HubPlayer player : hub.getHubPlayers()){
            player.getPlayer().sendMessage(HubMessages.COMPLETED_LAPIS_PARKOUR.get(player, omp.getName()));
            player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
        }
    }
}
