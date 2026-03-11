package fadidev.orbitmines.hub.events.bungee;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.MiniGameArena;
import fadidev.orbitmines.hub.utils.enums.MiniGameType;
import fadidev.orbitmines.hub.utils.enums.State;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * Created by Fadi on 3-9-2016.
 */
public class BungeeMessageEvent implements PluginMessageListener {

    private OrbitMinesHub hub;

    public BungeeMessageEvent(){
        hub = OrbitMinesHub.getHub();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message){
        if(!channel.equals("BungeeCord") && !channel.equals("OrbitMinesHub"))
            return;

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        try{
            String subChannel = in.readUTF();

            MiniGameType type = MiniGameType.fromShortName(subChannel);

            if(type != null){
                String[] data = in.readUTF().split("\\|");
                int arenaId = Integer.parseInt(data[0]);
                State state = State.valueOf(data[1]);
                int players = Integer.parseInt(data[2]);
                int minutes = Integer.parseInt(data[3]);
                int seconds = Integer.parseInt(data[4]);

                MiniGameArena arena = MiniGameArena.getMiniGameArena(type, arenaId);
                arena.setState(state);
                arena.setPlayers(players);
                arena.setMinutes(minutes);
                arena.setSeconds(seconds);
                arena.setLastResponse(0);
            }
            else if(subChannel.equals("MGArea")){
                final String name = in.readUTF();

                new BukkitRunnable(){
                    public void run(){
                        Player p = PlayerUtils.getPlayer(name);

                        if(p != null){
                            OMPlayer omp = OMPlayer.getOMPlayer(p);
                            omp.toMiniGameArea();
                        }
                    }
                }.runTaskLater(hub, 40);
            }

        }catch(EOFException e){
        }catch(IOException e){e.printStackTrace();}
    }
}
