package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.utils.Utils;

import java.sql.*;
import java.util.HashMap;

public class Database {

	private static OrbitMinesAPI api;
	private Connection connection;
	private String hostname;
	private int port;
	private String databasename;
	private String username;
	private String password;
	
	public Database(String hostname, int port, String databasename, String username, String password){
		api = OrbitMinesAPI.getApi();
		this.hostname = hostname;
		this.port = port;
		this.databasename = databasename;
		this.username = username;
		this.password = password;

		openConnection();
	}

	public Connection getConnection(){
		return connection;
	}
	public void setConnection(Connection connection){
		this.connection = connection;
	}
	
	public boolean containsPath(String table, String column, String columnwhere, String value){
		checkConnection();
		
		String query = "SELECT `" + column + "` FROM `" + table + "` WHERE `" + columnwhere + "`='" + value + "'";
		
		try{
			ResultSet rs = getConnection().prepareStatement(query).executeQuery();
			
			boolean bl = rs.next();
			rs.close();
			
			return bl;
		}catch(SQLException ex){
			ex.printStackTrace();
			return false;
		}
	}

	public void insert(String table, String colums, String values){
		checkConnection();
		
		String query = "INSERT INTO `" + table + "` (`" + colums + "`) VALUES ('" + values + "')";		
		
		try{
			PreparedStatement ps = getConnection().prepareStatement(query);
			ps.execute();
			ps.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public void delete(String table, String column, String value){
		checkConnection();
		
		try{
			Statement s = getConnection().createStatement();
			s.executeUpdate("DELETE FROM `" + table + "` WHERE `" + column + "` = '" + value + "'");
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public int getInt(String table, String column, String columnwhere, String value){
		checkConnection();
		
		int integer = 0;
		
		String query = "SELECT `" + column + "` FROM `" + table + "` WHERE `" + columnwhere + "` = '" + value + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while(rs.next()){
				integer = rs.getInt(column);
			}
			
			rs.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return integer;
	}
	
	public String getString(String table, String column, String columnwhere, String value){
		checkConnection();
		
		String string = "";
		
		String query = "SELECT `" + column + "` FROM `" + table + "` WHERE `" + columnwhere + "` = '" + value + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while(rs.next()){
				string = rs.getString(column);
			}
			
			rs.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}

		string = string.replace("`", "'");
		return string;
	}
	
	public HashMap<String, Integer> getIntEntries(String table, String columnkeys, String columnvalues){
		checkConnection();
		
		HashMap<String, Integer> intentries = new HashMap<String, Integer>();
		
		String query = "SELECT * FROM `" + table + "`";
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while(rs.next()){
				intentries.put(rs.getString(columnkeys), rs.getInt(columnvalues));
			}
			
			rs.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return intentries;
	}
	
	public HashMap<String, String> getStringEntries(String table, String columnkeys, String columnvalues){
		checkConnection();
		
		HashMap<String, String> stringentries = new HashMap<String, String>();
		
		String query = "SELECT * FROM `" + table + "`";
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while(rs.next()){
				stringentries.put(rs.getString(columnkeys), rs.getString(columnvalues));
			}
			
			rs.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return stringentries;
	}
	
	public void update(String table, String column, String value, String containswhere, String valuewhere){
		checkConnection();
		
		value = value.replace("'", "`");
		valuewhere = valuewhere.replace("'", "`");
		
		try{
			Statement s = connection.createStatement();
			s.executeUpdate("UPDATE `" + table + "` SET `" + column + "` = '" + value + "' WHERE `" + containswhere + "` = '" + valuewhere + "'");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public synchronized void openConnection(){
		try{
			setConnection(DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + databasename, username, password));
		}catch(SQLException ex){
            Utils.consoleWarning("Cannot connect to the database. Disabling OrbitMines API...");
            Utils.consoleWarning("Error:");
            ex.printStackTrace();
            api.getServer().getPluginManager().disablePlugin(api);
		}
	}
	
	private void checkConnection(){
		try{
			if(getConnection().isClosed()){
				openConnection();
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public static Database get(){
		return api.getDB();
	}
}
