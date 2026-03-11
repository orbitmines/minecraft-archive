package fadidev.orbitmines.api.events.bungee;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.utils.enums.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * Created by Fadi on 3-9-2016.
 */
public class BungeeMessageEvent implements PluginMessageListener {

    private OrbitMinesAPI api;

    public BungeeMessageEvent(){
        api = OrbitMinesAPI.getApi();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message){
        if(!channel.equals("BungeeCord") && !channel.equals("OrbitMinesAPI"))
            return;

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        try{
            String subChannel = in.readUTF();

            if(subChannel.equals("PlayerCount")){
                String server = in.readUTF();

                if(!server.equalsIgnoreCase("all")){
                    if(in.available() > 0){
                        int onlinePlayers = in.readInt();
                        api.getOnlinePlayers().put(Server.valueOf(server.toUpperCase()), onlinePlayers);
                    }
                    else{
                        api.getOnlinePlayers().put(Server.valueOf(server.toUpperCase()), -1);
                    }
                }
            }

        }catch(EOFException e){
        }catch(IOException e){e.printStackTrace();}
    }
}
