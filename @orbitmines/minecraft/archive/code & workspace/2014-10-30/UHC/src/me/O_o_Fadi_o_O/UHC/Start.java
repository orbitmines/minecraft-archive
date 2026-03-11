package me.O_o_Fadi_o_O.UHC;

import me.O_o_Fadi_o_O.UHC.events.BlockBreak;
import me.O_o_Fadi_o_O.UHC.events.BlockPlace;
import me.O_o_Fadi_o_O.UHC.events.ClickEvent;
import me.O_o_Fadi_o_O.UHC.events.DamageByEntityEvent;
import me.O_o_Fadi_o_O.UHC.events.DamageEvent;
import me.O_o_Fadi_o_O.UHC.events.DeathEvent;
import me.O_o_Fadi_o_O.UHC.events.DropEvent;
import me.O_o_Fadi_o_O.UHC.events.FoodEvent;
import me.O_o_Fadi_o_O.UHC.events.InteractEvent;
import me.O_o_Fadi_o_O.UHC.events.JoinEvent;
import me.O_o_Fadi_o_O.UHC.events.PickupEvent;
import me.O_o_Fadi_o_O.UHC.events.PlayerChat;
import me.O_o_Fadi_o_O.UHC.events.QuitEvent;
import me.O_o_Fadi_o_O.UHC.managers.Manager;
import me.O_o_Fadi_o_O.UHC.scoreboard.Board;
import me.O_o_Fadi_o_O.UHC.scoreboard.BossBar;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

public class Start extends JavaPlugin {
	
	public static GameState state;
	
	public void onEnable(){
		
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new BlockPlace(), this);
		getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new DeathEvent(), this);
		getServer().getPluginManager().registerEvents(new PickupEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractEvent(), this);
		getServer().getPluginManager().registerEvents(new DropEvent(), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new FoodEvent(), this);
		
		state = GameState.LOBBY;
		Manager.Seconds = 20;
		Manager.Minutes = 0;
		
		for(Player p : Bukkit.getOnlinePlayers()){
			Manager.Players.add(p);
			
			for(Player player : Bukkit.getOnlinePlayers()){
				p.showPlayer(player);
				player.showPlayer(p);
			}
		}
		
		new BukkitRunnable(){
			
			@Override
			public void run(){
				
				Board.i = Board.i + 1;
				BossBar.iB = BossBar.iB +1;
				
				if(Board.i == 4){
					Board.i = 1;
				}
				if(BossBar.iB == 4){
					BossBar.iB = 1;
				}
				
				if(Manager.Seconds != -1){Manager.Seconds = Manager.Seconds -1;}
				if(Manager.Seconds == -1){Manager.Minutes = Manager.Minutes -1; Manager.Seconds = 59;}
				if(state == GameState.WARMUP){
					if(Manager.Seconds <= 10 && Manager.Seconds != 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						}
					}
				}
				if(state == GameState.NOPVP){
					if(Manager.Minutes == 40 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 35 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 30 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 25 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 20 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 15 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 10 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 5 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 2 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
					if(Manager.Minutes == 1 && Manager.Seconds == 0){
						Bukkit.broadcastMessage(Manager.getBroadcastMessage());
						for(Player p : Bukkit.getOnlinePlayers()){
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						}
					}
				}
				if(Manager.Minutes == 0 && Manager.Seconds == 0){
					if(state == GameState.WARMUP){
						Manager.startGame();
					}
					else if(state == GameState.NOPVP){
						Manager.setPvP();
					}
					else{}
				}
				if(state == GameState.PVP){
					if(Manager.BorderSize > 50){
						Manager.BorderSize = Manager.BorderSize -1;
					}
				}
				if(state == GameState.ENDING){
					for(Player p : Bukkit.getOnlinePlayers()){
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						if(p.getAllowFlight() == false){
							p.setAllowFlight(true);
							p.setFlying(true);
						}
					}
				}
				
				for(Player p : Bukkit.getOnlinePlayers()){
					Board.setScoreboard(p);
					BossBar.setBossbar(p);
					
					if(Manager.Spectators.contains(p)){
						 ((CraftPlayer) p).getHandle().setInvisible(true);
						 p.setGameMode(GameMode.CREATIVE);
						 Manager.clearInventory(p);
						 ItemStack item = new ItemStack(Material.COMPASS, 1);
						 ItemMeta meta = item.getItemMeta();
						 meta.setDisplayName("§c§lRandom Teleport §7§o(Right Click)");
						 item.setItemMeta(meta);
						 p.getInventory().setItem(0, item);
						 if(p.getAllowFlight() == false){
							 p.setAllowFlight(true);
							 p.setFlying(true);
						 }
					}
				}
			}
		}.runTaskTimer(this, 0, 20);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] a) {
		
		if(cmd.getName().equalsIgnoreCase("startgame")){
			
			if(sender instanceof Player){
				Player p = (Player) sender;
				
				if(p.isOp()){
					state = GameState.WARMUP;
					Manager.Minutes = 0;
					Manager.Seconds = 20;
				}
			}
		}
		
		return false;
	}
}
