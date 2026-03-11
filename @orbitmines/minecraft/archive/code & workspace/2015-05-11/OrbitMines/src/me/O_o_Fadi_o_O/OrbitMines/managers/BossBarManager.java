package me.O_o_Fadi_o_O.OrbitMines.managers;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Map;
import me.mgone.bossbarapi.BossbarAPI;

import org.bukkit.entity.Player;

public class BossBarManager {
	
	public static int i;

	public static void setBossBar(OMPlayer omp){
		Player p = omp.getPlayer();
		
		if(!ServerData.isServer(Server.KITPVP)){
			if(i <= 10){
				BossbarAPI.removeBar(p);
				BossbarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6Welcome §6§l" + p.getName() + "§6!");
			}
			else if(i <= 20){
				BossbarAPI.removeBar(p);
				BossbarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6You have §6§l" + omp.getOrbitMinesTokens() + "§6 OMT");
			}
			else if(i <= 30){
				BossbarAPI.removeBar(p);
				BossbarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6§lwww.orbitmines.com");
			}
			else if(i <= 40){
				BossbarAPI.removeBar(p);
				BossbarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6IP: §6§lHub.OrbitMinesMC.com");
			}
			else if(i <= 50){
				BossbarAPI.removeBar(p);
				BossbarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6You have §6§l" + omp.getVIPPoints() + "§6 VIP Points");
			}
			else if(i <= 60){
				BossbarAPI.removeBar(p);
				BossbarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6You have §6§l" + omp.getVotes() + "§6 Votes");
				i = 0;
			}
		}
		else{
			Map map = ServerData.getKitPvP().getCurrentMap();
			
			BossbarAPI.removeBar(p);
			BossbarAPI.setMessage(p, "§7Map Switch in §6§l" + map.getMinutes() + "m " + map.getSeconds() + "s§7...");
			i = 0;
		}
	}
}
