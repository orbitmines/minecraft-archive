package me.O_o_Fadi_o_O.OrbitMinesBungeeCord.managers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMinesBungeeCord.Start;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DatabaseManager {

	private static String getquery = "";
	private static String containsquery = "";
	static Connection connection;
	
	public static synchronized void openConnection(){
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://sql-3.verygames.net:3306/minecraft4268", "minecraft4268", "hnfxy5h48");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static boolean containsPath(String column, String Table, String contains){

		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		containsquery = "SELECT `" + column + "` FROM `" + Table + "` WHERE `" + column + "`='" + contains + "'";
		
		try {
			ResultSet rs = connection.prepareStatement(containsquery).executeQuery();
			
			boolean temp = rs.next();
			rs.close();
			return temp;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	public static synchronized void insertInt(String player, String Table, String column, int i){

		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(player).toString();
		
		getquery = "INSERT INTO `" + Table + "` (`uuid`, `" + column + "`) VALUES ('" + uuid + "', '" + i + "')";		
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	public static synchronized void insertString(String player, String Table, String column, String i){

		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(player).toString();
		
		getquery = "INSERT INTO `" + Table + "` (`uuid`, `" + column + "`) VALUES ('" + uuid + "', '" + i.replaceAll("'", "`") + "')";
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	public synchronized static String getString(String player, String Table, String column){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(player).toString();
		
		String string = "";
		
		String query = "SELECT `" + column +"` FROM `" + Table +"` WHERE `uuid` = '" + uuid + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				string = rs.getString(column);
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return string;
	}
	
	public static void insertReport(String PlayerReported, String PlayerReporterBy, String Reason, String ReportedOn, String server){

		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(PlayerReported).toString();
		
		getquery = "INSERT INTO `PlayerReports` (`uuid`, `reportedby`, `reason`, `reportedon`, `server`) VALUES ('" + uuid +"', '" + PlayerReporterBy +"', '" + Reason +"', '" + ReportedOn +"', '" + server + "')";
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertIPBan(String BannedIP, String BannedByPlayer, String Reason, String BannedOn, String BannedUntil){

		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		getquery = "INSERT INTO `PlayerIPBans` (`ip`, `bannedby`, `reason`, `bannedon`, `banneduntil`) VALUES ('" + BannedIP +"', '" + BannedByPlayer +"', '" + Reason +"', '" + BannedOn +"', '" + BannedUntil +"')";
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertBan(String BannedPlayer, String BannedByPlayer, String Reason, String BannedOn, String BannedUntil){

		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(BannedPlayer).toString();
		
		getquery = "INSERT INTO `PlayerBans` (`uuid`, `bannedby`, `reason`, `bannedon`, `banneduntil`) VALUES ('" + uuid +"', '" + BannedByPlayer +"', '" + Reason +"', '" + BannedOn +"', '" + BannedUntil +"')";
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static String getBannedIPInfo(String BannedIP, String column){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String string = "";
		
		String query = "SELECT `" + column +"` FROM `PlayerIPBans` WHERE `ip` = '" + BannedIP + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				string = rs.getString(column);
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return string;
	}
	
	public static String getBannedInfo(String BannedPlayer, String column){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(BannedPlayer).toString();
		
		String string = "";
		
		String query = "SELECT `" + column +"` FROM `PlayerBans` WHERE `uuid` = '" + uuid + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				string = rs.getString(column);
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return string;
	}
	
	public static void unBan(String BannedPlayer){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(BannedPlayer).toString();
		
		String query = "DELETE FROM `PlayerBans` WHERE `uuid` = '" + uuid + "'"; 
		
		try{
			connection.prepareStatement(query).executeUpdate();
			
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void unBanIP(String BannedIP){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String query = "DELETE FROM `PlayerIPBans` WHERE `ip` = '" + BannedIP + "'"; 
		
		try{
			connection.prepareStatement(query).executeUpdate();
			
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static void setLastOnline(ProxiedPlayer p){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		Calendar now = Calendar.getInstance();
        
        Date lastonlinedate = new Date(now.getTimeInMillis()); 
        SimpleDateFormat nowsd = new SimpleDateFormat();
        nowsd.applyPattern( "dd-MM-yyyy HH:mm:ss" );
        String lastonline = nowsd.format(lastonlinedate);
		
        UUID puuid = Start.getUUIDfromPlayer(p);
		String uuid = puuid.toString();
		
		Start.lastonline.put(puuid, lastonline);
        
		if(DatabaseManager.containsPath("uuid", "PlayerLastOnline", uuid)){
	        try{
	        	Statement s = connection.createStatement();
				s.executeUpdate("UPDATE `PlayerLastOnline` SET `date` = '" + lastonline + "' WHERE `uuid` = '" + uuid + "'");
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        }
		}
        else{
        	DatabaseManager.insertString(p.getName(), "PlayerLastOnline", "date", lastonline);
        }
	}
	
	public static void setIP(String player, String ip){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(player).toString();
		
		try{
			Statement s = connection.createStatement();
			s.executeUpdate("UPDATE `PlayerIPs` SET `ip` = '" + ip + "' WHERE `uuid` = '" + uuid + "'");
		}catch(Exception ex){
			ex.printStackTrace();
	    }
	}
	
	public static void setSilentJoin(String player, Boolean b){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(player).toString();
		
		try{
			Statement s = connection.createStatement();
			s.executeUpdate("UPDATE `PlayerSilentJoin` SET `silentjoin` = '" + b + "' WHERE `uuid` = '" + uuid + "'");
		}catch(Exception ex){
			ex.printStackTrace();
	    }
	}
	
	public static void setFriends(String player, String friends){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(player).toString();
		
		try{
			Statement s = connection.createStatement();
			s.executeUpdate("UPDATE `Friends` SET `friends` = '" + friends + "' WHERE `uuid` = '" + uuid + "'");
		}catch(Exception ex){
			ex.printStackTrace();
	    }
	}
	public static void removeFriends(String player){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String uuid = Start.getUUIDfromString(player).toString();
		
		try{
			Statement s = connection.createStatement();
			s.executeUpdate("DELETE FROM `Friends` WHERE `uuid` = '" + uuid + "'");
		}catch(Exception ex){
			ex.printStackTrace();
	    }
	}
	
	public static void setUUID(ProxiedPlayer p){
		if(containsPath("name", "PlayerUUIDs", p.getName())){
			try {
				if(connection.isClosed()){
					openConnection();
				}
			} catch (SQLException e) {e.printStackTrace();}
			
			try{
				Statement s = connection.createStatement();
				s.executeUpdate("UPDATE `PlayerUUIDs` SET `uuid` = '" + Start.getUUIDfromPlayer(p).toString() + "' WHERE `name` = '" + p.getName() + "'");
			}catch(Exception ex){
				ex.printStackTrace();
		    }
		}
		else{
			try {
				if(connection.isClosed()){
					openConnection();
				}
			} catch (SQLException e) {e.printStackTrace();}
			
			String uuid = Start.getUUIDfromPlayer(p).toString();
			
			getquery = "INSERT INTO `PlayerUUIDs` (`name`, `uuid`) VALUES ('" + p.getName() + "', '" + uuid + "')";
			
			try{
				PreparedStatement ps = connection.prepareStatement(getquery);
				ps.execute();
				ps.close();
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public static void removeFromName(String player, String Table){
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		try{
			Statement s = connection.createStatement();
			s.executeUpdate("DELETE FROM `" + Table + "` WHERE `uuid` = '" + player + "'");
		}catch(Exception ex){
			ex.printStackTrace();
	    }
	}
	
	public static void checkForOldUser(ProxiedPlayer p){
		
		if(!containsPath("uuid", "CheckedForOldUser", Start.getUUIDfromPlayer(p).toString())){
			setUUID(p);
			
			if(containsPath("uuid", "ChatColors", p.getName())){
				String s = getStringFromName(p.getName(), "ChatColors", "color");
				
				removeFromName(p.getName(), "ChatColors");
				insertString(p.getName(), "ChatColors", "color", s);
			}
			if(containsPath("uuid", "ChatColors-Black", p.getName())){
				removeFromName(p.getName(), "ChatColors-Black");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-Black");
			}
			if(containsPath("uuid", "ChatColors-Blue", p.getName())){
				removeFromName(p.getName(), "ChatColors-Blue");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-Blue");
			}
			if(containsPath("uuid", "ChatColors-Bold", p.getName())){
				String s = getStringFromName(p.getName(), "ChatColors-Bold", "bold");
				
				removeFromName(p.getName(), "ChatColors-Bold");
				insertString(p.getName(), "ChatColors-Bold", "bold", s);
			}
			if(containsPath("uuid", "ChatColors-Cursive", p.getName())){
				String s = getStringFromName(p.getName(), "ChatColors-Cursive", "cursive");
				
				removeFromName(p.getName(), "ChatColors-Cursive");
				insertString(p.getName(), "ChatColors-Cursive", "cursive", s);
			}
			if(containsPath("uuid", "ChatColors-DarkBlue", p.getName())){
				removeFromName(p.getName(), "ChatColors-DarkBlue");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-DarkBlue");
			}
			if(containsPath("uuid", "ChatColors-DarkGray", p.getName())){
				removeFromName(p.getName(), "ChatColors-DarkGray");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-DarkGray");
			}
			if(containsPath("uuid", "ChatColors-DarkRed", p.getName())){
				removeFromName(p.getName(), "ChatColors-DarkRed");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-DarkRed");
			}
			if(containsPath("uuid", "ChatColors-Green", p.getName())){
				removeFromName(p.getName(), "ChatColors-Green");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-Green");
			}
			if(containsPath("uuid", "ChatColors-LightBlue", p.getName())){
				removeFromName(p.getName(), "ChatColors-LightBlue");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-LightBlue");
			}
			if(containsPath("uuid", "ChatColors-LightGreen", p.getName())){
				removeFromName(p.getName(), "ChatColors-LightGreen");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-LightGreen");
			}
			if(containsPath("uuid", "ChatColors-Pink", p.getName())){
				removeFromName(p.getName(), "ChatColors-Pink");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-Pink");
			}
			if(containsPath("uuid", "ChatColors-Red", p.getName())){
				removeFromName(p.getName(), "ChatColors-Red");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-Red");
			}
			if(containsPath("uuid", "ChatColors-White", p.getName())){
				removeFromName(p.getName(), "ChatColors-White");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ChatColors-White");
			}
			if(containsPath("uuid", "ChickenFight-BabyChicken", p.getName())){
				int i = getIntegerFromName(p.getName(), "ChickenFight-BabyChicken", "babychicken");
				
				removeFromName(p.getName(), "ChickenFight-BabyChicken");
				insertInt(p.getName(), "ChickenFight-BabyChicken", "babychicken", i);
			}
			if(containsPath("uuid", "ChickenFight-BestStreak", p.getName())){
				int i = getIntegerFromName(p.getName(), "ChickenFight-BestStreak", "beststreak");
				
				removeFromName(p.getName(), "ChickenFight-BestStreak");
				insertInt(p.getName(), "ChickenFight-BestStreak", "beststreak", i);
			}
			if(containsPath("uuid", "ChickenFight-ChickenWarrior", p.getName())){
				int i = getIntegerFromName(p.getName(), "ChickenFight-ChickenWarrior", "chickenwarrior");
				
				removeFromName(p.getName(), "ChickenFight-ChickenWarrior");
				insertInt(p.getName(), "ChickenFight-ChickenWarrior", "chickenwarrior", i);
			}
			if(containsPath("uuid", "ChickenFight-HotWing", p.getName())){
				int i = getIntegerFromName(p.getName(), "ChickenFight-HotWing", "hotwing");
				
				removeFromName(p.getName(), "ChickenFight-HotWing");
				insertInt(p.getName(), "ChickenFight-HotWing", "hotwing", i);
			}
			if(containsPath("uuid", "Dis-Bat", p.getName())){
				removeFromName(p.getName(), "Dis-Bat");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Bat");
			}
			if(containsPath("uuid", "Dis-CaveSpider", p.getName())){
				removeFromName(p.getName(), "Dis-CaveSpider");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-CaveSpider");
			}
			if(containsPath("uuid", "Dis-Chicken", p.getName())){
				removeFromName(p.getName(), "Dis-Chicken");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Chicken");
			}
			if(containsPath("uuid", "Dis-Cow", p.getName())){
				removeFromName(p.getName(), "Dis-Cow");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Cow");
			}
			if(containsPath("uuid", "Dis-Creeper", p.getName())){
				removeFromName(p.getName(), "Dis-Creeper");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Creeper");
			}
			if(containsPath("uuid", "Dis-Enderman", p.getName())){
				removeFromName(p.getName(), "Dis-Enderman");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Enderman");
			}
			if(containsPath("uuid", "Dis-Ghast", p.getName())){
				removeFromName(p.getName(), "Dis-Ghast");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Ghast");
			}
			if(containsPath("uuid", "Dis-Horse", p.getName())){
				removeFromName(p.getName(), "Dis-Horse");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Horse");
			}
			if(containsPath("uuid", "Dis-IronGolem", p.getName())){
				removeFromName(p.getName(), "Dis-IronGolem");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-IronGolem");
			}
			if(containsPath("uuid", "Dis-MagmaCube", p.getName())){
				removeFromName(p.getName(), "Dis-MagmaCube");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-MagmaCube");
			}
			if(containsPath("uuid", "Dis-MushroomCow", p.getName())){
				removeFromName(p.getName(), "Dis-MushroomCow");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-MushroomCow");
			}
			if(containsPath("uuid", "Dis-Ocelot", p.getName())){
				removeFromName(p.getName(), "Dis-Ocelot");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Ocelot");
			}
			if(containsPath("uuid", "Dis-Rabbit", p.getName())){
				removeFromName(p.getName(), "Dis-Rabbit");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Rabbit");
			}
			if(containsPath("uuid", "Dis-Sheep", p.getName())){
				removeFromName(p.getName(), "Dis-Sheep");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Sheep");
			}
			if(containsPath("uuid", "Dis-Silverfish", p.getName())){
				removeFromName(p.getName(), "Dis-Silverfish");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Silverfish");
			}
			if(containsPath("uuid", "Dis-Skeleton", p.getName())){
				removeFromName(p.getName(), "Dis-Skeleton");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Skeleton");
			}
			if(containsPath("uuid", "Dis-SkeletonHorse", p.getName())){
				removeFromName(p.getName(), "Dis-SkeletonHorse");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-SkeletonHorse");
			}
			if(containsPath("uuid", "Dis-Slime", p.getName())){
				removeFromName(p.getName(), "Dis-Slime");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Slime");
			}
			if(containsPath("uuid", "Dis-Snowman", p.getName())){
				removeFromName(p.getName(), "Dis-Snowman");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Snowman");
			}
			if(containsPath("uuid", "Dis-Spider", p.getName())){
				removeFromName(p.getName(), "Dis-Spider");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Spider");
			}
			if(containsPath("uuid", "Dis-Squid", p.getName())){
				removeFromName(p.getName(), "Dis-Squid");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Squid");
			}
			if(containsPath("uuid", "Dis-Witch", p.getName())){
				removeFromName(p.getName(), "Dis-Witch");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Witch");
			}
			if(containsPath("uuid", "Dis-Wolf", p.getName())){
				removeFromName(p.getName(), "Dis-Wolf");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-Wolf");
			}
			if(containsPath("uuid", "Dis-ZombiePigman", p.getName())){
				removeFromName(p.getName(), "Dis-ZombiePigman");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Dis-ZombiePigman");
			}
			if(containsPath("uuid", "Fireworks-Passes", p.getName())){
				int i = getIntegerFromName(p.getName(), "Fireworks-Passes", "passes");
				
				removeFromName(p.getName(), "Fireworks-Passes");
				insertInt(p.getName(), "Fireworks-Passes", "passes", i);
			}
			if(containsPath("uuid", "Fireworks-Settings", p.getName())){
				String s = getStringFromName(p.getName(), "Fireworks-Settings", "settings");
				
				removeFromName(p.getName(), "Fireworks-Settings");
				insertString(p.getName(), "Fireworks-Settings", "settings", s);
			}
			/*if(containsPath("uuid", "Friends", p.getName())){
				String[] friends = DatabaseManager.getStringFromName(p.getName(), "Friends", "friends").split("\\|");
				String[] newfriends = new String[friends.length];
				int index = 0;
				for(String friend : friends){
					newfriends[index] = Start.getUUIDfromString(friend));
					index++;
				}
				
				String friendstring = "";
				
				for(String friend : newfriends){
					friendstring = friendstring + "|" + friend;
				}
				
				if(friendstring.startsWith("|")){
					friendstring = friendstring.substring(1);
				}
				
				removeFromName(p.getName(), "Friends");
				insertString(p.getName(), "Friends", "friends", friendstring);
			}TODO*/
			if(containsPath("uuid", "Gadgets-BookExplosion", p.getName())){
				removeFromName(p.getName(), "Gadgets-BookExplosion");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Gadgets-BookExplosion");
			}
			if(containsPath("uuid", "Gadgets-CreeperLauncher", p.getName())){
				removeFromName(p.getName(), "Gadgets-CreeperLauncher");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Gadgets-CreeperLauncher");
			}
			if(containsPath("uuid", "Gadgets-MagmaCubeSoccer", p.getName())){
				removeFromName(p.getName(), "Gadgets-MagmaCubeSoccer");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Gadgets-MagmaCubeSoccer");
			}
			if(containsPath("uuid", "Gadgets-Paintballs", p.getName())){
				removeFromName(p.getName(), "Gadgets-Paintballs");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Gadgets-Paintballs");
			}
			if(containsPath("uuid", "Gadgets-PetRide", p.getName())){
				removeFromName(p.getName(), "Gadgets-PetRide");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Gadgets-PetRide");
			}
			if(containsPath("uuid", "Gadgets-SnowmanAttack", p.getName())){
				removeFromName(p.getName(), "Gadgets-SnowmanAttack");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Gadgets-SnowmanAttack");
			}
			if(containsPath("uuid", "Gadgets-SwapTeleporter", p.getName())){
				removeFromName(p.getName(), "Gadgets-SwapTeleporter");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Gadgets-SwapTeleporter");
			}
			if(containsPath("uuid", "Hats-Andesite", p.getName())){
				removeFromName(p.getName(), "Hats-Andesite");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Andesite");
			}
			if(containsPath("uuid", "Hats-Bedrock", p.getName())){
				removeFromName(p.getName(), "Hats-Bedrock");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Bedrock");
			}
			if(containsPath("uuid", "Hats-BlackGlass", p.getName())){
				removeFromName(p.getName(), "Hats-BlackGlass");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-BlackGlass");
			}
			if(containsPath("uuid", "Hats-BlockTrail", p.getName())){
				String s = getStringFromName(p.getName(), "Hats-BlockTrail", "blocktrail");
				
				removeFromName(p.getName(), "Hats-BlockTrail");
				insertString(p.getName(), "Hats-BlockTrail", "blocktrail", s);
			}
			if(containsPath("uuid", "Hats-BlueGlass", p.getName())){
				removeFromName(p.getName(), "Hats-BlueGlass");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-BlueGlass");
			}
			if(containsPath("uuid", "Hats-Cactus", p.getName())){
				removeFromName(p.getName(), "Hats-Cactus");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Cactus");
			}
			if(containsPath("uuid", "Hats-Chest", p.getName())){
				removeFromName(p.getName(), "Hats-Chest");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Chest");
			}
			if(containsPath("uuid", "Hats-CoalBlock", p.getName())){
				removeFromName(p.getName(), "Hats-CoalBlock");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-CoalBlock");
			}
			if(containsPath("uuid", "Hats-CoalOre", p.getName())){
				removeFromName(p.getName(), "Hats-CoalOre");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-CoalOre");
			}
			if(containsPath("uuid", "Hats-DarkPrismarine", p.getName())){
				removeFromName(p.getName(), "Hats-DarkPrismarine");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-DarkPrismarine");
			}
			if(containsPath("uuid", "Hats-Diorite", p.getName())){
				removeFromName(p.getName(), "Hats-Diorite");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Diorite");
			}
			if(containsPath("uuid", "Hats-Furnace", p.getName())){
				removeFromName(p.getName(), "Hats-Furnace");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Furnace");
			}
			if(containsPath("uuid", "Hats-Glass", p.getName())){
				removeFromName(p.getName(), "Hats-Glass");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Glass");
			}
			if(containsPath("uuid", "Hats-GreenGlass", p.getName())){
				removeFromName(p.getName(), "Hats-GreenGlass");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-GreenGlass");
			}
			if(containsPath("uuid", "Hats-HayBale", p.getName())){
				removeFromName(p.getName(), "Hats-HayBale");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-HayBale");
			}
			if(containsPath("uuid", "Hats-Ice", p.getName())){
				removeFromName(p.getName(), "Hats-Ice");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Ice");
			}
			if(containsPath("uuid", "Hats-LapisBlock", p.getName())){
				removeFromName(p.getName(), "Hats-LapisBlock");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-LapisBlock");
			}
			if(containsPath("uuid", "Hats-LapisOre", p.getName())){
				removeFromName(p.getName(), "Hats-LapisOre");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-LapisOre");
			}
			if(containsPath("uuid", "Hats-Leaves", p.getName())){
				removeFromName(p.getName(), "Hats-Leaves");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Leaves");
			}
			if(containsPath("uuid", "Hats-MagentaGlass", p.getName())){
				removeFromName(p.getName(), "Hats-MagentaGlass");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-MagentaGlass");
			}
			if(containsPath("uuid", "Hats-Melon", p.getName())){
				removeFromName(p.getName(), "Hats-Melon");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Melon");
			}
			if(containsPath("uuid", "Hats-Mycelium", p.getName())){
				removeFromName(p.getName(), "Hats-Mycelium");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Mycelium");
			}
			if(containsPath("uuid", "Hats-OrangeGlass", p.getName())){
				removeFromName(p.getName(), "Hats-OrangeGlass");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-OrangeGlass");
			}
			if(containsPath("uuid", "Hats-PrismarineBricks", p.getName())){
				removeFromName(p.getName(), "Hats-PrismarineBricks");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-PrismarineBricks");
			}
			if(containsPath("uuid", "Hats-QuartzBlock", p.getName())){
				removeFromName(p.getName(), "Hats-QuartzBlock");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-QuartzBlock");
			}
			if(containsPath("uuid", "Hats-QuartzOre", p.getName())){
				removeFromName(p.getName(), "Hats-QuartzOre");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-QuartzOre");
			}
			if(containsPath("uuid", "Hats-RedGlass", p.getName())){
				removeFromName(p.getName(), "Hats-RedGlass");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-RedGlass");
			}
			if(containsPath("uuid", "Hats-RedstoneBlock", p.getName())){
				removeFromName(p.getName(), "Hats-RedstoneBlock");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-RedstoneBlock");
			}
			if(containsPath("uuid", "Hats-RedstoneOre", p.getName())){
				removeFromName(p.getName(), "Hats-RedstoneOre");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-RedstoneOre");
			}
			if(containsPath("uuid", "Hats-SeaLantern", p.getName())){
				removeFromName(p.getName(), "Hats-SeaLantern");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-SeaLantern");
			}
			if(containsPath("uuid", "Hats-SlimeBlock", p.getName())){
				removeFromName(p.getName(), "Hats-SlimeBlock");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-SlimeBlock");
			}
			if(containsPath("uuid", "Hats-Snow", p.getName())){
				removeFromName(p.getName(), "Hats-Snow");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Snow");
			}
			if(containsPath("uuid", "Hats-Sponge", p.getName())){
				removeFromName(p.getName(), "Hats-Sponge");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Sponge");
			}
			if(containsPath("uuid", "Hats-StoneBricks", p.getName())){
				removeFromName(p.getName(), "Hats-StoneBricks");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-StoneBricks");
			}
			if(containsPath("uuid", "Hats-TNT", p.getName())){
				removeFromName(p.getName(), "Hats-TNT");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-TNT");
			}
			if(containsPath("uuid", "Hats-WetSponge", p.getName())){
				removeFromName(p.getName(), "Hats-WetSponge");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-WetSponge");
			}
			if(containsPath("uuid", "Hats-Workbench", p.getName())){
				removeFromName(p.getName(), "Hats-Workbench");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-Workbench");
			}
			if(containsPath("uuid", "Hats-YellowGlass", p.getName())){
				removeFromName(p.getName(), "Hats-YellowGlass");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Hats-YellowGlass");
			}
			if(containsPath("uuid", "Hub-Players", p.getName())){
				String s = getStringFromName(p.getName(), "Hub-Players", "players");
				
				removeFromName(p.getName(), "Hub-Players");
				insertString(p.getName(), "Hub-Players", "players", s);
			}
			if(containsPath("uuid", "Hub-Scoreboard", p.getName())){
				String s = getStringFromName(p.getName(), "Hub-Scoreboard", "scoreboard");
				
				removeFromName(p.getName(), "Hub-Scoreboard");
				insertString(p.getName(), "Hub-Scoreboard", "scoreboard", s);
			}
			if(containsPath("uuid", "Hub-Stacker", p.getName())){
				String s = getStringFromName(p.getName(), "Hub-Stacker", "stacker");
				
				removeFromName(p.getName(), "Hub-Stacker");
				insertString(p.getName(), "Hub-Stacker", "stacker", s);
			}
			if(containsPath("uuid", "KitPvP-BestStreak", p.getName())){
				int i = getIntegerFromName(p.getName(), "KitPvP-BestStreak", "beststreak");
				
				removeFromName(p.getName(), "KitPvP-BestStreak");
				insertInt(p.getName(), "KitPvP-BestStreak", "beststreak", i);
			}
			if(containsPath("uuid", "KitPvP-Deaths", p.getName())){
				int i = getIntegerFromName(p.getName(), "KitPvP-Deaths", "deaths");
				
				removeFromName(p.getName(), "KitPvP-Deaths");
				insertInt(p.getName(), "KitPvP-Deaths", "deaths", i);
			}
			if(containsPath("uuid", "KitPvP-Kills", p.getName())){
				int i = getIntegerFromName(p.getName(), "KitPvP-Kills", "kills");
				
				removeFromName(p.getName(), "KitPvP-Kills");
				insertInt(p.getName(), "KitPvP-Kills", "kills", i);
			}
			if(containsPath("uuid", "KitPvP-Levels", p.getName())){
				int i = getIntegerFromName(p.getName(), "KitPvP-Levels", "levels");
				
				removeFromName(p.getName(), "KitPvP-Levels");
				insertInt(p.getName(), "KitPvP-Levels", "levels", i);
			}
			if(containsPath("uuid", "KitPvP-Money", p.getName())){
				int i = getIntegerFromName(p.getName(), "KitPvP-Money", "money");
				
				removeFromName(p.getName(), "KitPvP-Money");
				insertInt(p.getName(), "KitPvP-Money", "money", i);
			}
			if(containsPath("uuid", "Kits-Archer", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Archer", "archer");
				
				removeFromName(p.getName(), "Kits-Archer");
				insertInt(p.getName(), "Kits-Archer", "archer", i);
			}
			if(containsPath("uuid", "Kits-Assassin", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Assassin", "assassin");
				
				removeFromName(p.getName(), "Kits-Assassin");
				insertInt(p.getName(), "Kits-Assassin", "assassin", i);
			}
			if(containsPath("uuid", "Kits-Beast", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Beast", "beast");
				
				removeFromName(p.getName(), "Kits-Beast");
				insertInt(p.getName(), "Kits-Beast", "beast", i);
			}
			if(containsPath("uuid", "Kits-Blaze", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Blaze", "blaze");
				
				removeFromName(p.getName(), "Kits-Blaze");
				insertInt(p.getName(), "Kits-Blaze", "blaze", i);
			}
			if(containsPath("uuid", "Kits-Bunny", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Bunny", "bunny");
				
				removeFromName(p.getName(), "Kits-Bunny");
				insertInt(p.getName(), "Kits-Bunny", "bunny", i);
			}
			if(containsPath("uuid", "Kits-DarkMage", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-DarkMage", "darkmage");
				
				removeFromName(p.getName(), "Kits-DarkMage");
				insertInt(p.getName(), "Kits-DarkMage", "darkmage", i);
			}
			if(containsPath("uuid", "Kits-Drunk", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Drunk", "drunk");
				
				removeFromName(p.getName(), "Kits-Drunk");
				insertInt(p.getName(), "Kits-Drunk", "drunk", i);
			}
			if(containsPath("uuid", "Kits-Fish", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Fish", "fish");
				
				removeFromName(p.getName(), "Kits-Fish");
				insertInt(p.getName(), "Kits-Fish", "fish", i);
			}
			if(containsPath("uuid", "Kits-Fisherman", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Fisherman", "fisherman");
				
				removeFromName(p.getName(), "Kits-Fisherman");
				insertInt(p.getName(), "Kits-Fisherman", "fisherman", i);
			}
			if(containsPath("uuid", "Kits-GrimReaper", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-GrimReaper", "grimreaper");
				
				removeFromName(p.getName(), "Kits-GrimReaper");
				insertInt(p.getName(), "Kits-GrimReaper", "grimreaper", i);
			}
			if(containsPath("uuid", "Kits-Heavy", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Heavy", "heavy");
				
				removeFromName(p.getName(), "Kits-Heavy");
				insertInt(p.getName(), "Kits-Heavy", "heavy", i);
			}
			if(containsPath("uuid", "Kits-King", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-King", "king");
				
				removeFromName(p.getName(), "Kits-King");
				insertInt(p.getName(), "Kits-King", "king", i);
			}
			if(containsPath("uuid", "Kits-Knight", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Knight", "knight");
				
				removeFromName(p.getName(), "Kits-Knight");
				insertInt(p.getName(), "Kits-Knight", "knight", i);
			}
			if(containsPath("uuid", "Kits-Librarian", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Librarian", "librarian");
				
				removeFromName(p.getName(), "Kits-Librarian");
				insertInt(p.getName(), "Kits-Librarian", "librarian", i);
			}
			if(containsPath("uuid", "Kits-Lord", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Lord", "lord");
				
				removeFromName(p.getName(), "Kits-Lord");
				insertInt(p.getName(), "Kits-Lord", "lord", i);
			}
			if(containsPath("uuid", "Kits-Miner", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Miner", "miner");
				
				removeFromName(p.getName(), "Kits-Miner");
				insertInt(p.getName(), "Kits-Miner", "miner", i);
			}
			if(containsPath("uuid", "Kits-Necromancer", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Necromancer", "necromancer");
				
				removeFromName(p.getName(), "Kits-Necromancer");
				insertInt(p.getName(), "Kits-Necromancer", "necromancer", i);
			}
			if(containsPath("uuid", "Kits-Pyro", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Pyro", "pyro");
				
				removeFromName(p.getName(), "Kits-Pyro");
				insertInt(p.getName(), "Kits-Pyro", "pyro", i);
			}
			if(containsPath("uuid", "Kits-SnowGolem", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-SnowGolem", "snowgolem");
				
				removeFromName(p.getName(), "Kits-SnowGolem");
				insertInt(p.getName(), "Kits-SnowGolem", "snowgolem", i);
			}
			if(containsPath("uuid", "Kits-Soldier", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Soldier", "soldier");
				
				removeFromName(p.getName(), "Kits-Soldier");
				insertInt(p.getName(), "Kits-Soldier", "soldier", i);
			}
			if(containsPath("uuid", "Kits-Spider", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Spider", "spider");
				
				removeFromName(p.getName(), "Kits-Spider");
				insertInt(p.getName(), "Kits-Spider", "spider", i);
			}
			if(containsPath("uuid", "Kits-Tank", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Tank", "tank");
				
				removeFromName(p.getName(), "Kits-Tank");
				insertInt(p.getName(), "Kits-Tank", "tank", i);
			}
			if(containsPath("uuid", "Kits-TNT", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-TNT", "tnt");
				
				removeFromName(p.getName(), "Kits-TNT");
				insertInt(p.getName(), "Kits-TNT", "tnt", i);
			}
			if(containsPath("uuid", "Kits-Tree", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Tree", "tree");
				
				removeFromName(p.getName(), "Kits-Tree");
				insertInt(p.getName(), "Kits-Tree", "tree", i);
			}
			if(containsPath("uuid", "Kits-Vampire", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Vampire", "vampire");
				
				removeFromName(p.getName(), "Kits-Vampire");
				insertInt(p.getName(), "Kits-Vampire", "vampire", i);
			}
			if(containsPath("uuid", "Kits-Villager", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Villager", "villager");
				
				removeFromName(p.getName(), "Kits-Villager");
				insertInt(p.getName(), "Kits-Villager", "villager", i);
			}
			if(containsPath("uuid", "Kits-Wizard", p.getName())){
				int i = getIntegerFromName(p.getName(), "Kits-Wizard", "wizard");
				
				removeFromName(p.getName(), "Kits-Wizard");
				insertInt(p.getName(), "Kits-Wizard", "wizard", i);
			}
			if(containsPath("uuid", "MasterMind-BestGame", p.getName())){
				int i = getIntegerFromName(p.getName(), "MasterMind-BestGame", "turns");
				
				removeFromName(p.getName(), "MasterMind-BestGame");
				insertInt(p.getName(), "MasterMind-BestGame", "turns", i);
			}
			if(containsPath("uuid", "MasterMind-Wins", p.getName())){
				int i = getIntegerFromName(p.getName(), "MasterMind-Wins", "wins");
				
				removeFromName(p.getName(), "MasterMind-Wins");
				insertInt(p.getName(), "MasterMind-Wins", "wins", i);
			}
			if(containsPath("uuid", "MiniGameCoins", p.getName())){
				int i = getIntegerFromName(p.getName(), "MiniGameCoins", "coins");
				
				removeFromName(p.getName(), "MiniGameCoins");
				insertInt(p.getName(), "MiniGameCoins", "coins", i);
			}
			if(containsPath("uuid", "MiniGameKills", p.getName())){
				int i = getIntegerFromName(p.getName(), "MiniGameKills", "kills");
				
				removeFromName(p.getName(), "MiniGameKills");
				insertInt(p.getName(), "MiniGameKills", "kills", i);
			}
			if(containsPath("uuid", "MiniGameLoses", p.getName())){
				int i = getIntegerFromName(p.getName(), "MiniGameLoses", "loses");
				
				removeFromName(p.getName(), "MiniGameLoses");
				insertInt(p.getName(), "MiniGameLoses", "loses", i);
			}
			if(containsPath("uuid", "MiniGameWins", p.getName())){
				int i = getIntegerFromName(p.getName(), "MiniGameWins", "wins");
				
				removeFromName(p.getName(), "MiniGameWins");
				insertInt(p.getName(), "MiniGameWins", "wins", i);
			}
			if(containsPath("uuid", "OrbitMinesTokens", p.getName())){
				int i = getIntegerFromName(p.getName(), "OrbitMinesTokens", "omt");
				
				removeFromName(p.getName(), "OrbitMinesTokens");
				insertInt(p.getName(), "OrbitMinesTokens", "omt", i);
			}
			if(containsPath("uuid", "ParkourCompleted", p.getName())){
				removeFromName(p.getName(), "ParkourCompleted");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "ParkourCompleted");
			}
			if(containsPath("uuid", "Pets-Chicken", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Chicken", "petname");
				
				removeFromName(p.getName(), "Pets-Chicken");
				insertString(p.getName(), "Pets-Chicken", "petname", s);
			}
			if(containsPath("uuid", "Pets-Cow", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Cow", "petname");
				
				removeFromName(p.getName(), "Pets-Cow");
				insertString(p.getName(), "Pets-Cow", "petname", s);
			}
			if(containsPath("uuid", "Pets-Creeper", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Creeper", "petname");
				
				removeFromName(p.getName(), "Pets-Creeper");
				insertString(p.getName(), "Pets-Creeper", "petname", s);
			}
			if(containsPath("uuid", "Pets-Horse", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Horse", "petname");
				
				removeFromName(p.getName(), "Pets-Horse");
				insertString(p.getName(), "Pets-Horse", "petname", s);
			}
			if(containsPath("uuid", "Pets-MagmaCube", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-MagmaCube", "petname");
				
				removeFromName(p.getName(), "Pets-MagmaCube");
				insertString(p.getName(), "Pets-MagmaCube", "petname", s);
			}
			if(containsPath("uuid", "Pets-MushroomCow", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-MushroomCow", "petname");
				
				removeFromName(p.getName(), "Pets-MushroomCow");
				insertString(p.getName(), "Pets-MushroomCow", "petname", s);
			}
			if(containsPath("uuid", "Pets-Ocelot", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Ocelot", "petname");
				
				removeFromName(p.getName(), "Pets-Ocelot");
				insertString(p.getName(), "Pets-Ocelot", "petname", s);
			}
			if(containsPath("uuid", "Pets-Pig", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Pig", "petname");
				
				removeFromName(p.getName(), "Pets-Pig");
				insertString(p.getName(), "Pets-Pig", "petname", s);
			}
			if(containsPath("uuid", "Pets-Sheep", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Sheep", "petname");
				
				removeFromName(p.getName(), "Pets-Sheep");
				insertString(p.getName(), "Pets-Sheep", "petname", s);
			}
			if(containsPath("uuid", "Pets-Silverfish", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Silverfish", "petname");
				
				removeFromName(p.getName(), "Pets-Silverfish");
				insertString(p.getName(), "Pets-Silverfish", "petname", s);
			}
			if(containsPath("uuid", "Pets-Slime", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Slime", "petname");
				
				removeFromName(p.getName(), "Pets-Slime");
				insertString(p.getName(), "Pets-Slime", "petname", s);
			}
			if(containsPath("uuid", "Pets-Spider", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Spider", "petname");
				
				removeFromName(p.getName(), "Pets-Spider");
				insertString(p.getName(), "Pets-Spider", "petname", s);
			}
			if(containsPath("uuid", "Pets-Squid", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Squid", "petname");
				
				removeFromName(p.getName(), "Pets-Squid");
				insertString(p.getName(), "Pets-Squid", "petname", s);
			}
			if(containsPath("uuid", "Pets-Wolf", p.getName())){
				String s = getStringFromName(p.getName(), "Pets-Wolf", "petname");
				
				removeFromName(p.getName(), "Pets-Wolf");
				insertString(p.getName(), "Pets-Wolf", "petname", s);
			}
			// TODO ->> BANS INSERT BY HAND
			if(containsPath("uuid", "PlayerIPs", p.getName())){
				String s = getStringFromName(p.getName(), "PlayerIPs", "ip");
				
				removeFromName(p.getName(), "PlayerIPs");
				insertString(p.getName(), "PlayerIPs", "ip", s);
			}
			if(containsPath("uuid", "PlayerLastOnline", p.getName())){
				String s = getStringFromName(p.getName(), "PlayerLastOnline", "date");
				
				removeFromName(p.getName(), "PlayerLastOnline");
				insertString(p.getName(), "PlayerLastOnline", "date", s);
			}
			if(containsPath("uuid", "PlayerMonthlyVIPPoints", p.getName())){
				removeFromName(p.getName(), "PlayerMonthlyVIPPoints");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "PlayerMonthlyVIPPoints");
			}
			if(containsPath("uuid", "PlayerNicknames", p.getName())){
				String s = getStringFromName(p.getName(), "PlayerNicknames", "nick");
				
				removeFromName(p.getName(), "PlayerNicknames");
				insertString(p.getName(), "PlayerNicknames", "nick", s);
			}
			if(containsPath("uuid", "PlayerSilentJoin", p.getName())){
				String s = getStringFromName(p.getName(), "PlayerSilentJoin", "silentjoin");
				
				removeFromName(p.getName(), "PlayerSilentJoin");
				insertString(p.getName(), "PlayerSilentJoin", "silentjoin", s);
			}
			// PlayerUUIDs -> IGNORE
			if(containsPath("uuid", "Rank-Staff", p.getName())){
				String s = getStringFromName(p.getName(), "Rank-Staff", "staff");
				
				removeFromName(p.getName(), "Rank-Staff");
				insertString(p.getName(), "Rank-Staff", "staff", s);
			}
			if(containsPath("uuid", "Rank-VIP", p.getName())){
				String s = getStringFromName(p.getName(), "Rank-VIP", "vip");
				
				removeFromName(p.getName(), "Rank-VIP");
				insertString(p.getName(), "Rank-VIP", "vip", s);
			}
			if(containsPath("uuid", "SurvivalGames-BestStreak", p.getName())){
				int i = getIntegerFromName(p.getName(), "SurvivalGames-BestStreak", "beststreak");
				
				removeFromName(p.getName(), "SurvivalGames-BestStreak");
				insertInt(p.getName(), "SurvivalGames-BestStreak", "beststreak", i);
			}
			if(containsPath("uuid", "SurvivalGames-Kills", p.getName())){
				int i = getIntegerFromName(p.getName(), "SurvivalGames-Kills", "kills");
				
				removeFromName(p.getName(), "SurvivalGames-Kills");
				insertInt(p.getName(), "SurvivalGames-Kills", "kills", i);
			}
			if(containsPath("uuid", "SurvivalGames-Loses", p.getName())){
				int i = getIntegerFromName(p.getName(), "SurvivalGames-Loses", "loses");
				
				removeFromName(p.getName(), "SurvivalGames-Loses");
				insertInt(p.getName(), "SurvivalGames-Loses", "loses", i);
			}
			if(containsPath("uuid", "SurvivalGames-Wins", p.getName())){
				int i = getIntegerFromName(p.getName(), "SurvivalGames-Wins", "wins");
				
				removeFromName(p.getName(), "SurvivalGames-Wins");
				insertInt(p.getName(), "SurvivalGames-Wins", "wins", i);
			}
			if(containsPath("uuid", "Trails-AngryVillager", p.getName())){
				removeFromName(p.getName(), "Trails-AngryVillager");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-AngryVillager");
			}
			if(containsPath("uuid", "Trails-Bubble", p.getName())){
				removeFromName(p.getName(), "Trails-Bubble");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Bubble");
			}
			if(containsPath("uuid", "Trails-Crit", p.getName())){
				removeFromName(p.getName(), "Trails-Crit");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Crit");
			}
			if(containsPath("uuid", "Trails-ETable", p.getName())){
				removeFromName(p.getName(), "Trails-ETable");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-ETable");
			}
			if(containsPath("uuid", "Trails-Explode", p.getName())){
				removeFromName(p.getName(), "Trails-Explode");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Explode");
			}
			if(containsPath("uuid", "Trails-Firework", p.getName())){
				removeFromName(p.getName(), "Trails-Firework");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Firework");
			}
			if(containsPath("uuid", "Trails-HappyVillager", p.getName())){
				removeFromName(p.getName(), "Trails-HappyVillager");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-HappyVillager");
			}
			if(containsPath("uuid", "Trails-Hearts", p.getName())){
				removeFromName(p.getName(), "Trails-Hearts");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Hearts");
			}
			if(containsPath("uuid", "Trails-MobSpawner", p.getName())){
				removeFromName(p.getName(), "Trails-MobSpawner");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-MobSpawner");
			}
			if(containsPath("uuid", "Trails-Music", p.getName())){
				removeFromName(p.getName(), "Trails-Music");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Music");
			}
			if(containsPath("uuid", "Trails-Slime", p.getName())){
				removeFromName(p.getName(), "Trails-Slime");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Slime");
			}
			if(containsPath("uuid", "Trails-Smoke", p.getName())){
				removeFromName(p.getName(), "Trails-Smoke");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Smoke");
			}
			if(containsPath("uuid", "Trails-Snow", p.getName())){
				removeFromName(p.getName(), "Trails-Snow");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Snow");
			}
			if(containsPath("uuid", "Trails-Type", p.getName())){
				String s = getStringFromName(p.getName(), "Trails-Type", "type");
				
				removeFromName(p.getName(), "Trails-Type");
				insertString(p.getName(), "Trails-Type", "type", s);
			}
			if(containsPath("uuid", "Trails-TypeBig", p.getName())){
				removeFromName(p.getName(), "Trails-TypeBig");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-TypeBig");
			}
			if(containsPath("uuid", "Trails-TypeBody", p.getName())){
				removeFromName(p.getName(), "Trails-TypeBody");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-TypeBody");
			}
			if(containsPath("uuid", "Trails-TypeGround", p.getName())){
				removeFromName(p.getName(), "Trails-TypeGround");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-TypeGround");
			}
			if(containsPath("uuid", "Trails-TypeHead", p.getName())){
				removeFromName(p.getName(), "Trails-TypeHead");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-TypeHead");
			}
			if(containsPath("uuid", "Trails-TypeParticlesAmount", p.getName())){
				int i = getIntegerFromName(p.getName(), "Trails-TypeParticlesAmount", "amount");
				
				removeFromName(p.getName(), "Trails-TypeParticlesAmount");
				insertInt(p.getName(), "Trails-TypeParticlesAmount", "amount", i);
			}
			if(containsPath("uuid", "Trails-TypeSpecial", p.getName())){
				String s = getStringFromName(p.getName(), "Trails-TypeSpecial", "special");
				
				removeFromName(p.getName(), "Trails-TypeSpecial");
				insertString(p.getName(), "Trails-TypeSpecial", "special", s);
			}
			if(containsPath("uuid", "Trails-TypeVertical", p.getName())){
				removeFromName(p.getName(), "Trails-TypeVertical");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-TypeVertical");
			}
			if(containsPath("uuid", "Trails-Water", p.getName())){
				removeFromName(p.getName(), "Trails-Water");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Water");
			}
			if(containsPath("uuid", "Trails-Witch", p.getName())){
				removeFromName(p.getName(), "Trails-Witch");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Trails-Witch");
			}
			if(containsPath("uuid", "VIPPoints", p.getName())){
				int i = getIntegerFromName(p.getName(), "VIPPoints", "points");
				
				removeFromName(p.getName(), "VIPPoints");
				insertInt(p.getName(), "VIPPoints", "points", i);
			}
			if(containsPath("uuid", "Votes", p.getName())){
				int i = getIntegerFromName(p.getName(), "Votes", "votes");
				
				removeFromName(p.getName(), "Votes");
				insertInt(p.getName(), "Votes", "votes", i);
			}
			if(containsPath("uuid", "Wardrobe-Black", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Black");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Black");
			}
			if(containsPath("uuid", "Wardrobe-Blue", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Blue");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Blue");
			}
			if(containsPath("uuid", "Wardrobe-Cyan", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Cyan");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Cyan");
			}
			if(containsPath("uuid", "Wardrobe-DarkBlue", p.getName())){
				removeFromName(p.getName(), "Wardrobe-DarkBlue");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-DarkBlue");
			}
			if(containsPath("uuid", "Wardrobe-Disco", p.getName())){
				String s = getStringFromName(p.getName(), "Wardrobe-Disco", "disco");
				
				removeFromName(p.getName(), "Wardrobe-Disco");
				insertString(p.getName(), "Wardrobe-Disco", "disco", s);
			}
			if(containsPath("uuid", "Wardrobe-Gray", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Gray");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Gray");
			}
			if(containsPath("uuid", "Wardrobe-Green", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Green");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Green");
			}
			if(containsPath("uuid", "Wardrobe-LightBlue", p.getName())){
				removeFromName(p.getName(), "Wardrobe-LightBlue");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-LightBlue");
			}
			if(containsPath("uuid", "Wardrobe-LightGreen", p.getName())){
				removeFromName(p.getName(), "Wardrobe-LightGreen");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-LightGreen");
			}
			if(containsPath("uuid", "Wardrobe-Orange", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Orange");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Orange");
			}
			if(containsPath("uuid", "Wardrobe-Pink", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Pink");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Pink");
			}
			if(containsPath("uuid", "Wardrobe-Purple", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Purple");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Purple");
			}
			if(containsPath("uuid", "Wardrobe-Red", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Red");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Red");
			}
			if(containsPath("uuid", "Wardrobe-White", p.getName())){
				removeFromName(p.getName(), "Wardrobe-White");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-White");
			}
			if(containsPath("uuid", "Wardrobe-Yellow", p.getName())){
				removeFromName(p.getName(), "Wardrobe-Yellow");
				insertUUID(Start.getUUIDfromPlayer(p).toString(), "Wardrobe-Yellow");
			}
			
			insertUUID(Start.getUUIDfromPlayer(p).toString(), "CheckedForOldUser");
		}
	}
	
	public static void insertUUID(String uuid, String Table){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		getquery = "INSERT INTO `" + Table + "` (`uuid`) VALUES ('" + uuid + "')";	
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			
			ps.execute();
			ps.close();
		}catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	public static String getStringFromName(String player, String Table, String column){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String string = "";
		
		String query = "SELECT `" + column +"` FROM `" + Table +"` WHERE `uuid` = '" + player + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				string = rs.getString(column);
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return string;
	}
	public static int getIntegerFromName(String player, String Table, String column){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		int i = 0;
		
		String query = "SELECT `" + column +"` FROM `" + Table +"` WHERE `uuid` = '" + player + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				i = rs.getInt(column);
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return i;
	}
	
	public static String getUUID(String player){
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String string = "";
		
		String query = "SELECT `uuid` FROM `PlayerUUIDs` WHERE `name` = '" + player + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				string = rs.getString("uuid");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return string;
	}
	public static String getNameFromUUID(UUID uuid){
		
		String uuidstring = uuid.toString();
		
		try {
			if(connection.isClosed()){
				openConnection();
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		String string = "";
		
		String query = "SELECT `name` FROM `PlayerUUIDs` WHERE `uuid` = '" + uuidstring + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				string = rs.getString("name");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return string;
	}
}
