package net.tez.trapdoor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TrapDoor extends JavaPlugin {

	public static TrapDoor instance;
	public static String pluginPrefix = "[TrapDoor]";
	public static Connection dbConnection;
	
	private String URL, dbHost, dbPort, dbName, dbUser, dbPassword;

	public static String TABLE_NAME;
	
	//Make sure that player enable SQL to update when open trapdoor
	public static boolean toggleSQL;
	//Check if player has connected with database
	public static boolean hasSQL = false;
	@Override
	public void onEnable() {
		instance = this;
		
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);
		
		//Check if player has toggle sql then connect, otherwise return
		//Set default value to prevent null exception when player didn't set the value
		toggleSQL = getConfig().getBoolean("sql.toggle",true);
		if(toggleSQL == false) {
			Bukkit.getLogger().severe(pluginPrefix + " SQL database will not active due to it's disabled.");
			return;
		}
		
		TABLE_NAME = getConfig().getString("sql.table_name");
		dbHost = getConfig().getString("sql.host");
		dbPort = getConfig().getString("sql.port");
		dbName = getConfig().getString("sql.name");
		dbUser = getConfig().getString("sql.user");
		dbPassword = getConfig().getString("sql.password");
		URL = "jdbc:mysql://" + dbHost + (dbPort.equalsIgnoreCase("default") ? "" : ":" + dbPort) + "/" + dbName;
		
		try {
			dbConnection = DriverManager.getConnection(URL, dbUser, dbPassword);
			hasSQL = true;
		} catch (SQLException e) {
			Bukkit.getLogger().severe(pluginPrefix + " Unable to connect to SQL database, please check again.");
		}
		
	}
	
	public static TrapDoor getInstance() {
		return instance;
	}

}
