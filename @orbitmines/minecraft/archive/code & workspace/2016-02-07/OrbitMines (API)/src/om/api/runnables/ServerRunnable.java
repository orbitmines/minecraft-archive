package om.api.runnables;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import om.api.API;
import om.api.utils.enums.Server;

import org.bukkit.Bukkit;

public class ServerRunnable extends OMRunnable {

	private API api;
	
	public ServerRunnable(Duration duration) {
		super(duration);
		
		this.api = API.getInstance();
	}

	@Override
	protected void run() {
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
