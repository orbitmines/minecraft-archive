package om.api.managers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import om.api.API;
import om.api.utils.enums.Server;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeManager implements PluginMessageListener {
	
	private API api;
	
	public BungeeManager(){
		api = API.getInstance();
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message){
		if(!channel.equals("BungeeCord") && !channel.equals("OrbitMinesAPI")){
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
						api.getOnlinePlayers().put(Server.valueOf(server.toUpperCase()), onlineplayers);
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
