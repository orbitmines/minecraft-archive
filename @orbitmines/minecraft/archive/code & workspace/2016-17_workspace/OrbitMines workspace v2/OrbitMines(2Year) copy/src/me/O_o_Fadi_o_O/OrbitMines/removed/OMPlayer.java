package me.O_o_Fadi_o_O.OrbitMines.removed;

import me.O_o_Fadi_o_O.OrbitMines.removed.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.MindCraftPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils.TicketType;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils.Rank;
import org.bukkit.*;

import static com.sun.xml.internal.ws.util.JAXWSUtils.getUUID;
import static sun.audio.AudioPlayer.player;

public class OMPlayer {
	
	@SuppressWarnings("deprecation")
	public void load(){

		try{
			String uuid = getUUID().toString();
			boolean tp = false;
			boolean newplayer = false;

			if(ServerData.isServer(Server.PRISON)){
				Rank rank = Rank.fromID(0);
				int gold = 0, gambletickets = 0;
				boolean clockenabled = false;


				if(Database.get().containsPath("Prison-GambleTickets", "uuid", "uuid", uuid)){
					gambletickets = Database.get().getInt("Prison-GambleTickets", "gambletickets", "uuid", uuid);
				}
				else{
					Database.get().insert("Prison-GambleTickets", "uuid`, `gambletickets", uuid + "', '" + 0);
				}

				this.prisonplayer = PrisonPlayer.addPrisonPlayer(player, rank, gold, gambletickets, clockenabled);
			}
			else{}

		}
	}
}
