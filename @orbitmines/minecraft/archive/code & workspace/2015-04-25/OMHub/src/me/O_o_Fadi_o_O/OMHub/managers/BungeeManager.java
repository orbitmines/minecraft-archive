package me.O_o_Fadi_o_O.OMHub.managers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import me.O_o_Fadi_o_O.OMHub.Start;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Server;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeManager implements PluginMessageListener {

	Start hub = Start.getInstance();
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message){
		if(!channel.equals("BungeeCord")){
            return;
        }
		
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		 
		try{
			String subChannel = in.readUTF();			 
			 
			if(subChannel.equals("PlayerCount")){
				 
				String serv = in.readUTF();
				
				if(!serv.equalsIgnoreCase("all")){
					Server server = Server.valueOf(serv.toUpperCase());
					if(in.available() > 0){
						try{
							Socket sock = new Socket(server.getIP(), 25565);
							 
							DataOutputStream out = new DataOutputStream(sock.getOutputStream());
							DataInputStream in2 = new DataInputStream(sock.getInputStream());
							 
							out.write(0xFE);
							/*
							int b;
							StringBuffer str = new StringBuffer();
							while ((b = in.read()) != -1) {
							if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
							// Not sure what use the two characters are so I omit them
							str.append((char) b);
							System.out.println(b + ":" + ((char) b));
							}
							}
							 
							String[] data = str.toString().split("§");
							String serverMotd = data[0];
							int onlinePlayers = Integer.parseInt(data[1]);
							int maxPlayers = Integer.parseInt(data[2]);
							 
							System.out.println(String.format(
							"MOTD: \"%s\"\nOnline Players: %d/%d", serverMotd,
							onlinePlayers, maxPlayers));
							*/
							
							int onlineplayers = in.readInt();
							ServerStorage.onlineplayers.put(server, onlineplayers);
							 
						} catch (UnknownHostException e) {
							ServerStorage.onlineplayers.put(server, -1);
							e.printStackTrace();
						} catch (IOException e) {
							ServerStorage.onlineplayers.put(server, -1);
							e.printStackTrace();
						}
					} 
					else{
						ServerStorage.onlineplayers.put(server, -1);
					}
				}
			}
		}catch(EOFException e){
		}catch(IOException e){e.printStackTrace();}
	}
}
