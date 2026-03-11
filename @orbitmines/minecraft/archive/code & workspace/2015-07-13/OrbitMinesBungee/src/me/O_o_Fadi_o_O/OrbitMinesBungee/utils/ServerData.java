package me.O_o_Fadi_o_O.OrbitMinesBungee.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMinesBungee.passes.Password;

public class ServerData {

	public static BungeeServer getBungee(){
		return ServerStorage.bungee;
	}
	
	public static class ServerStorage {

		public static Database database;
		public static BungeeServer bungee;
		public static List<Password> passwords = new ArrayList<Password>();
		
	}
	
	public static class BungeeServer {
		
		private boolean maintenancemode;
		private List<BungeePlayer> players;
		private List<String> announcements;
		private int announcementindex;
		private List<BannedPlayer> bannedplayers;
		private List<BannedIP> bannedips;
		private HashMap<UUID, String> ips;
		
		public BungeeServer(){
			ServerStorage.bungee = this;
			this.announcementindex = 0;
			this.players = new ArrayList<BungeePlayer>();
			this.bannedplayers = new ArrayList<BannedPlayer>();
			this.bannedips = new ArrayList<BannedIP>();
			this.ips = new HashMap<UUID, String>();
			
			registerAnnouncements();
			regiterBans();
			regiterIPBans();
		}

		public boolean inMaintenanceMode() {
			return maintenancemode;
		}
		public void setMaintenanceMode(boolean maintenancemode) {
			this.maintenancemode = maintenancemode;
		}

		public List<BungeePlayer> getPlayers() {
			return players;
		}
		public void setPlayers(List<BungeePlayer> players) {
			this.players = players;
		}

		public List<String> getAnnouncements() {
			return announcements;
		}
		public void setAnnouncements(List<String> announcements) {
			this.announcements = announcements;
		}

		public int getAnnouncementIndex() {
			return announcementindex;
		}
		public void setAnnouncementIndex(int announcementindex) {
			this.announcementindex = announcementindex;
		}
		public int getNextAnnouncementIndex(){
			announcementindex += 1;
			if(announcementindex == announcements.size()){
				announcementindex = 0;
			}
			
			return announcementindex;
		}

		public List<BannedPlayer> getBannedPlayers() {
			return bannedplayers;
		}
		public void setBannedPlayers(List<BannedPlayer> bannedplayers) {
			this.bannedplayers = bannedplayers;
		}

		public List<BannedIP> getBannedIPs() {
			return bannedips;
		}
		public void setBannedIPs(List<BannedIP> bannedips) {
			this.bannedips = bannedips;
		}

		public HashMap<UUID, String> getIPs() {
			return ips;
		}
		public void setIPs(HashMap<UUID, String> ips) {
			this.ips = ips;
		}
		
		private void registerAnnouncements(){
			this.announcements = new ArrayList<String>();
			this.announcements.add("§7\n » §7Follow News & Updates at §6www.orbitmines.com §7« \n§7");
			this.announcements.add("§7\n » §7Don't forget to Vote with §b/vote §7« \n§7");
			this.announcements.add("§7\n » §7Report bugs at §6www.orbitmines.com §7« \n§7");
			this.announcements.add("§7\n » §7Check out §b@OrbitMines§7 on Twitter §7« \n§7");
			this.announcements.add("§7\n » §7Support OrbitMines at §3shop.orbitmines.com §7« \n§7");
			this.announcements.add("§7\n » §7Support OrbitMines at §3shop.orbitmines.com §7« \n§7");
			this.announcements.add("§7\n » §7Report bugs at §6www.orbitmines.com §7« \n§7");
			this.announcements.add("§7\n » Report a player with §c/report <player> <reason> §7« \n§7");
		}
		
		private void regiterBans(){
			HashMap<String, String> bannedby = Database.get().getStringEntries("PlayerBans", "uuid", "bannedby");
			HashMap<String, String> bannedreason = Database.get().getStringEntries("PlayerBans", "uuid", "reason");
			HashMap<String, String> bannedon = Database.get().getStringEntries("PlayerBans", "uuid", "bannedon");
			HashMap<String, String> banneduntil = Database.get().getStringEntries("PlayerBans", "uuid", "banneduntil");
			
			for(String uuidstring : bannedby.keySet()){
				new BannedPlayer(UUID.fromString(uuidstring), bannedby.get(uuidstring), bannedreason.get(uuidstring), bannedon.get(uuidstring), banneduntil.get(uuidstring));
			}
		}
		
		private void regiterIPBans(){
			HashMap<String, String> bannedby = Database.get().getStringEntries("PlayerIPBans", "ip", "bannedby");
			HashMap<String, String> bannedreason = Database.get().getStringEntries("PlayerIPBans", "ip", "reason");
			HashMap<String, String> bannedon = Database.get().getStringEntries("PlayerIPBans", "ip", "bannedon");
			HashMap<String, String> banneduntil = Database.get().getStringEntries("PlayerIPBans", "ip", "banneduntil");
			
			for(String ip : bannedby.keySet()){
				new BannedIP(ip, bannedby.get(ip), bannedreason.get(ip), bannedon.get(ip), banneduntil.get(ip));
			}
		}
	}
}
