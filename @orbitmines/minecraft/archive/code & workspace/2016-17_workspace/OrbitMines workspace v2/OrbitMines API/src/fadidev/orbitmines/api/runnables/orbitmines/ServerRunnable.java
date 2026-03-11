package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import org.bukkit.Bukkit;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerRunnable extends OMRunnable {

	private OrbitMinesAPI api;

	public ServerRunnable() {
		super(TimeUnit.SECOND, 1);

		api = OrbitMinesAPI.getApi();
	}

	@Override
	public void run() {
		for(Server server : Server.values()){
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
	    
			try{
				out.writeUTF("PlayerCount");
			  	out.writeUTF(server.toString().toLowerCase());
			}catch (IOException e){
				e.printStackTrace();
			}

			Bukkit.getServer().sendPluginMessage(api, "BungeeCord", b.toByteArray());
		}

		api.getServerSelector().update();
	}
}
