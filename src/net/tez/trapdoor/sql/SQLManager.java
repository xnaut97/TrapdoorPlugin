package net.tez.trapdoor.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import net.tez.trapdoor.TrapDoor;

/**
 * A class that will manage all functions relating to SQL Database.
 */
public final class SQLManager {

	/**
	 * Check whether a value that existed in SQL Database or not.
	 * @param value Value need checking.
	 * @return {@code true} if that value has been existed, {@code false} otherwise.
	 */
	public static final boolean exists(String table, String value) {
		try {
			PreparedStatement statement = TrapDoor.dbConnection.prepareStatement("SELECT * FROM " + table + "WHERE LOCATION=?");
			statement.setString(1, value);

			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return true;
			}

		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	/**
	 * Add a new value into location column of {@code table}.
	 * @param table Table that the value will be put in.
	 * @param value The value need putting
	 * @return {@code true} if success, {@code false} otherwise.
	 */
	public static final boolean add(String table, String value) {
		try {
			if(exists(table, value) == true) return false;
			PreparedStatement insert = TrapDoor.dbConnection.prepareStatement("INSERT INTO " + table + "(LOCATION) VALUE (?)");
			insert.setString(1, value);
			insert.executeUpdate();

			Bukkit.getConsoleSender().sendMessage("§aAdded data into the SQL Database for value : §6" + value);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Delete a value from location column of {@code table}.
	 * @param table Table that the value is locating and going to be removed.
	 * @param value The value need removing
	 * @return {@code true} if success, {@code false} otherwise.
	 */
	public static boolean remove(String table, String value) {
		try {
			if(exists(table, value)) {
				PreparedStatement insert = TrapDoor.dbConnection.prepareStatement("DELETE FROM " + table + " WHERE LOCATION=?");
				insert.setString(1, value);
				insert.executeUpdate();

				Bukkit.getConsoleSender().sendMessage("§eRemoved data into the SQL Database for value : §6" + value);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
