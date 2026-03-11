package me.O_o_Fadi_o_O.UHC.scoreboard;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.managers.Manager;
import me.O_o_Fadi_o_O.UHC.utils.GameState;
import me.confuser.barapi.BarAPI;

import org.bukkit.entity.Player;

public class BossBar {

	public static int iB = 0;
	public static void setBossbar(Player p){
		
		if(Start.state == GameState.ENDING){
			BarAPI.setMessage(p, "§7§lWinner: §a§l" + Manager.Winner.getName());
		}
		if(Start.state == GameState.LOBBY){
			if(iB == 1){
				BarAPI.setMessage(p, "§6§lWaiting.");
			}
			else if(iB == 2){
				BarAPI.setMessage(p, "§6§lWaiting..");
			}
			else if(iB == 3){
				BarAPI.setMessage(p, "§6§lWaiting...");
			}
		}
		if(Start.state == GameState.WARMUP){
			BarAPI.setMessage(p, "§7§lStarting in §3§l" + Manager.Seconds + "§7§l...");
		}
		if(Start.state == GameState.NOPVP){
			double current = ((Manager.Minutes * 60) + Manager.Seconds) / 27;
			BarAPI.setMessage(p, "§c§lPvP §a§lEnabled§7§l in §3§l" + Manager.Minutes + "m " +Manager.Seconds + "s", (float) current);
		}
		if(Start.state == GameState.PVP){
			BarAPI.setMessage(p, "§e§lBorder: §6§l" + Manager.BorderSize + " Blocks§e§l!");
		}
	}
}
