package fadidev.bungeemsg.runnables;

import java.util.List;

import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.AutoAnnouncer;
import me.O_o_Fadi_o_O.BungeeMSG.Start;
import me.O_o_Fadi_o_O.BungeeMSG.managers.StorageManager;
import me.O_o_Fadi_o_O.BungeeMSG.managers.TitleManager;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AutoAnnouncerRunnable implements Runnable {

	private BungeeMSG msg;
	
	public AutoAnnouncerRunnable() {
		this.msg = BungeeMSG.getInstance();
	}
	
	@Override
	public void run(){
		if(msg.getAutoAnnouncers().size() > 0){
			for(AutoAnnouncer aa : msg.getAutoAnnouncers()){
				aa.setTimer(aa.getTimer() +1);
				
				if(aa.getTimer() > aa.getDelay()){
					int index = aa.getIndex();
					
					List<List<String>> messageslist = aa.getMessages();
					if(index >= messageslist.size()) index = 0;
					
					List<String> messages = messageslist.get(index);
					
					for(ServerInfo info : aa.getServers()){
						for(ProxiedPlayer player : info.getPlayers()){
							for(String message : messages){
								//Start.sendMessageNullCheck(player, null, TitleManager.importTitle(player, message.replace("&", "§").replace("%receiver%", player.getName()).replace("%server-receiver%", StorageManager.servernames.get(player.getServer().getInfo()).replace("&", "§"))));
							}
						}
					}
					
					aa.setIndex(index);
					aa.setTimer(0);
				}
			}
		}
	}
}
