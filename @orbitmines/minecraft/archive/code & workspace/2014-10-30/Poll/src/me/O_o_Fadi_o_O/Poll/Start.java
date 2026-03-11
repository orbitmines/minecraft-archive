package me.O_o_Fadi_o_O.Poll;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.Hub.Hub;
import net.minecraft.server.v1_7_R3.ChatSerializer;
import net.minecraft.server.v1_7_R3.IChatBaseComponent;
import net.minecraft.server.v1_7_R3.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends JavaPlugin implements Listener{
	
	public static List<String> PlayersVoted = new ArrayList<String>();
	static Connection connection;
	
	public static Hub Hub;
	
	public void onEnable(){
		
		Hub = (Hub) getServer().getPluginManager().getPlugin("Hub");
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		if(!(new File(getDataFolder(), "config.yml")).exists()){
			saveDefaultConfig();
		}
		
		for(String s : getConfig().getStringList("PlayersVoted")){
			PlayersVoted.add(s);
		}
	
		new BukkitRunnable(){

			@Override
			public void run() {
				
				openConnection();
				
			}
		}.runTaskAsynchronously(this);
	}
	
	public synchronized void openConnection(){
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://sql-3.verygames.net:3306/minecraft4268", "minecraft4268", "hnfxy5h48");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void addOMT(Player p, int OMT) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `OrbitMinesTokens` SET `omt` = '" + (getOMT(p) + OMT) + "' WHERE `name` = '" + p.getName() + "'");
		me.O_o_Fadi_o_O.Hub.Hub.omt.put(p.getName(), getOMT(p));
	}
	
	public int getOMT(Player p){
		int omt = 0;
		
		String query = "SELECT `omt` FROM `OrbitMinesTokens` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				omt = rs.getInt("omt");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return omt;
	}
	
	public void sendPollMessage(Player p){
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		p.sendMessage("");
		p.sendMessage("§6§lPoll §8| §eSkyBlock is going to get an update. How much space should there be between the borders of 2 islands?");
		p.sendMessage("");
		
		{
			IChatBaseComponent comp = ChatSerializer
			.a("{\"text\":\" \",\"extra\":[{\"text\":\"§d§l0 Blocks\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§dClick Here to Vote for '0 Blocks'\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/pollvote 0blocks\"}}]}");
			PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		{
			IChatBaseComponent comp = ChatSerializer
			.a("{\"text\":\" \",\"extra\":[{\"text\":\"§d§l10 Blocks\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§dClick Here to Vote for '10 Blocks'\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/pollvote 10blocks\"}}]}");
			PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		{
			IChatBaseComponent comp = ChatSerializer
			.a("{\"text\":\" \",\"extra\":[{\"text\":\"§d§l25 Blocks\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§dClick Here to Vote for '25 Blocks'\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/pollvote 25blocks\"}}]}");
			PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		p.sendMessage("");
		p.sendMessage("§f§oVote and get §e§l5 OMT§f§o!");
		p.sendMessage("§f§o(Click on a line to Vote)");
		p.sendMessage("");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		final Player p = e.getPlayer();
		
		new BukkitRunnable(){
			
			@Override
			public void run(){
				
				if(!PlayersVoted.contains(p.getName())){
					sendPollMessage(p);
				}
				
			}
		}.runTaskLater(this, 40 * 2);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,String l, String[] a) {
		
		if(cmd.getName().equalsIgnoreCase("pollvote")){
			
			if(sender instanceof Player){
				
				Player p = (Player) sender;
				
				if(a.length == 0){
					
				}
				else if(a[0].equalsIgnoreCase("0blocks")){
					
					if(!PlayersVoted.contains(p.getName())){
						
						PlayersVoted.add(p.getName());
						getConfig().set("PlayersVoted", PlayersVoted);
						
						List<String> list = getConfig().getStringList("VotedFor0Blocks");
						list.add(p.getName());
						getConfig().set("VotedFor0Blocks", list);
						
						getConfig().set("0Blocks", getConfig().getInt("0Blocks") +1);
						saveConfig();
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
						p.sendMessage("");
						p.sendMessage("§6§lPoll §8| §7Thanks for your Vote!");
						p.sendMessage("§6§lPoll §8| §7You voted for §d§l0 Blocks");
						p.sendMessage("§6§lPoll §8| §e§l+5 OMT");
						p.sendMessage("");
						
						try {
							addOMT(p, 5);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				else if(a[0].equalsIgnoreCase("10blocks")){
					
					if(!PlayersVoted.contains(p.getName())){
						
						PlayersVoted.add(p.getName());
						getConfig().set("PlayersVoted", PlayersVoted);
						
						List<String> list = getConfig().getStringList("VotedFor10Blocks");
						list.add(p.getName());
						getConfig().set("VotedFor10Blocks", list);
						
						getConfig().set("10Blocks", getConfig().getInt("10Blocks") +1);
						saveConfig();
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
						p.sendMessage("");
						p.sendMessage("§6§lPoll §8| §7Thanks for your Vote!");
						p.sendMessage("§6§lPoll §8| §7You voted for §d§l10 Blocks");
						p.sendMessage("§6§lPoll §8| §e§l+5 OMT");
						p.sendMessage("");
						
						try {
							addOMT(p, 5);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				else if(a[0].equalsIgnoreCase("25blocks")){
					
					if(!PlayersVoted.contains(p.getName())){
						
						PlayersVoted.add(p.getName());
						getConfig().set("PlayersVoted", PlayersVoted);
						
						List<String> list = getConfig().getStringList("VotedFor25Blocks");
						list.add(p.getName());
						getConfig().set("VotedFor25Blocks", list);
						
						getConfig().set("25Blocks", getConfig().getInt("25Blocks") +1);
						saveConfig();
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
						p.sendMessage("");
						p.sendMessage("§6§lPoll §8| §7Thanks for your Vote!");
						p.sendMessage("§6§lPoll §8| §7You voted for §d§l25 Blocks");
						p.sendMessage("§6§lPoll §8| §e§l+5 OMT");
						p.sendMessage("");
						
						try {
							addOMT(p, 5);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
		
				
			}
			
		}
		
		return false;
	}
}
