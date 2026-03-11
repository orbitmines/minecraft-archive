package fadidev.orbitmines.minigames.events.bungee;

import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
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

    private OrbitMinesMiniGames miniGames;

    public BungeeMessageEvent(){
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message){
        if(!channel.equals("BungeeCord") && !channel.equals("OrbitMinesMiniGames"))
            return;

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        try{
            String subChannel = in.readUTF();

            MiniGameType type = MiniGameType.fromShortName(subChannel);

            if(type != null){
                String[] data = in.readUTF().split("\\|");
                int arenaId = Integer.parseInt(data[0]);
                final String playerName = data[1];

                Arena arena = Arena.getArena(type, arenaId);
                miniGames.getPlayersToJoin().put(playerName, arena);

                new BukkitRunnable(){
                    public void run(){
                        miniGames.getPlayersToJoin().remove(playerName);
                    }
                }.runTaskLater(miniGames, 1200);
            }

        }catch(EOFException e){
        }catch(IOException e){e.printStackTrace();}
    }
}
