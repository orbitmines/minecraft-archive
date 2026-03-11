package me.O_o_Fadi_o_O.OrbitMines.managers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeManager implements PluginMessageListener {
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message){
		if(!channel.equals("BungeeCord") && !channel.equals(ServerData.getServer().toString())){
            return;
        }
		
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		 
		try{
			String subChannel = in.readUTF();			 
			 
			if(subChannel.equals("PlayerCount")){
				String server = in.readUTF();
				
				if(!server.equalsIgnoreCase("all")){
					if(in.available() > 0){
						int onlineplayers = in.readInt();
						ServerStorage.onlineplayers.put(Server.valueOf(server.toUpperCase()), onlineplayers);
					} 
					else{
						ServerStorage.onlineplayers.put(Server.valueOf(server.toUpperCase()), -1);
					}
				}
			}
		}catch(EOFException e){
		}catch(IOException e){e.printStackTrace();}
	}
}
