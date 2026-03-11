package me.O_o_Fadi_o_O.OrbitMinesBungee.managers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import me.O_o_Fadi_o_O.OrbitMinesBungee.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMinesBungee.utils.ServerInfo;
import me.O_o_Fadi_o_O.OrbitMinesBungee.utils.StatusListener;

public class DefaultPingManager implements StatusListener {
	
	public static int i = 0;
	public static String om;
	
	@Override
	public ServerInfo update() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("        §6§lOrbitMines§4§lNetwork ");
		list.add("");
		list.add(" §7§o'One does not simply walk into Orbit' ");
		list.add("");
		list.add("  §7§lWebsite ");
		list.add("     §6www.orbitmines.com ");
		list.add("  §7§lDonate ");
		list.add("     §3shop.orbitmines.com ");
		list.add("  §7§lVote ");
		list.add("     §borbitmines.com/vote ");
		list.add("");
		
		BufferedImage img = null;
		 
        try{
            img = ImageIO.read(new File("ServerLogo.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
		
		return new ServerInfo(getRandomStringMessage(), img, 20, "", list);
	}
	
	public static String getRandomStringMessage(){
		List<String> list = new ArrayList<String>();
		if(!ServerData.getBungee().inMaintenanceMode()){
			if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				list.add("                §6§lOrbitMines§4§lNetwork §3§l1.8                       §4§l§k!§c§l§k!§6§l§k!§d§l Free Kit Saturday §e@ §c§lKitPvP §6§l§k!§c§l§k!§4§l§k!");
			}
			
			list.add("                §6§lOrbitMines§4§lNetwork §3§l1.8                       §4§l§k!§c§l§k!§6§l§k!§e§l Don't forget to vote §b§l/vote §6§l§k!§c§l§k!§4§l§k!");
		}
		else{
			list.add("                §6§lOrbitMines§4§lNetwork §3§l1.8                      §5§l§k!§d§l§k!§5§l§k! §dYou'll be able to join in a moment §5§l§k!§d§l§k!§5§l§k!");
		}
		
		return list.get(new Random().nextInt(list.size()));
	}
}
