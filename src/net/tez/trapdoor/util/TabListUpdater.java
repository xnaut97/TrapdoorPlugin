package net.tez.trapdoor.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.tez.trapdoor.TrapDoor;

public class TabListUpdater extends BukkitRunnable{
	
	private boolean isRunning;
	public TabListUpdater() {
		isRunning = true;
		runTaskTimerAsynchronously(TrapDoor.getInstance(), 1, 1);
	}
	
	@Override
	public void run() {
		if(!isRunning)
		{
			this.cancel();
			return;
		}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
			EntityPlayer entityPlayer = ((CraftPlayer) p).getHandle();
			String displayName = p.getDisplayName();
			if(displayName == null)
			{
				PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
				connection.sendPacket(addPacket);
				PacketPlayOutPlayerInfo updatePacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, entityPlayer);
				GameProfile profile = new GameProfile(p.getUniqueId(), ChatColor.stripColor(p.getDisplayName()));
				entityPlayer.setProfile(profile);
				connection.sendPacket(updatePacket);
				return;
			}
			String format = displayName.replace("~", "");
			PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
			connection.sendPacket(addPacket);
			PacketPlayOutPlayerInfo updatePacket = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, entityPlayer);
			GameProfile profile = new GameProfile(p.getUniqueId(), format);
			entityPlayer.setProfile(profile);
			connection.sendPacket(updatePacket);
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
