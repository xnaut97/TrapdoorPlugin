package net.tez.trapdoor;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Openable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.tez.trapdoor.sql.SQLManager;
import net.tez.trapdoor.util.LocationUtils;

public class EventListener implements Listener{

	@EventHandler
	public void interact(PlayerInteractEvent e)
	{
		Block b = e.getClickedBlock();
		//Check if clicked block is not trapdoor type
		if(e.getHand() != EquipmentSlot.HAND) return;
		if(!b.getType().toString().contains("_TRAPDOOR")) return;
		
		//Cast blockstate and openable
		BlockState state = b.getState();
		Openable open = (Openable) state.getBlockData();
		if(open.isOpen()) 
		{
			open.setOpen(false);
			state.setBlockData(open);
			state.update();
			return;
		}
		//Get location of block
		Location bLoc = b.getLocation();
		
		//Apply new block data
		open.setOpen(true);
		state.setBlockData(open);
		state.update();
		
		//Check if player didn't install SQL
		if(TrapDoor.hasSQL == false) return;
		
		//If player don't set value for toggle boolean then return
		if(String.valueOf(TrapDoor.toggleSQL) == null) return;
		
		//If player don't enable sql
		if(TrapDoor.toggleSQL == false) return;
		
		//Format the location then send message and send to database
		String locFormat = LocationUtils.toString(bLoc);
		e.getPlayer().sendMessage("§aUpdated to database with location: §6" + locFormat);
		SQLManager.add(TrapDoor.TABLE_NAME, locFormat);
	}
}
