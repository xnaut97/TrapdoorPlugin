package net.tez.trapdoor.util;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
	/**
	 * Chuyển dạng String của một chuỗi vị trí đã được mã hóa về lại dạng
	 * {@link Location}.
	 */
	public static Location toLocation(String location) {
		if (location == null)
			return null;
		try {
			final String[] splitted = location.split(Pattern.quote(";"));

			World world = Bukkit.getWorld(splitted[0]);
			int x = Integer.parseInt(splitted[1]);
			int y = Integer.parseInt(splitted[2]);
			int z = Integer.parseInt(splitted[3]);

			Location loc = new Location(world, x, y, z);
			return loc;
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getLogger().severe("Không thể chuyển đổi '" + location + "' sang dạng org.bukkit.Location.");
			return null;
		}
	}

	/**
	 * Chuyển {@link Location} sang dạng String.
	 */
	public static String toString(Location location) {
		if (location == null)
			return null;
		try {
			String world = location.getWorld().getName();
			return world + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getLogger().severe("Không thể chuyển đổi Location sang dạng String !");
			return null;
		}
	}
}
