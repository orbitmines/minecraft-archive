package me.O_o_Fadi_o_O.Test;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Start extends JavaPlugin implements Listener {
    private static final int CUSTOM_NAME_INDEX = 5;
	
	// Entities that have a per-player name
	private Table<UUID, String, String> entityName = HashBasedTable.create();
	
	@Override
	public void onEnable() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
				new PacketAdapter(this, ConnectionSide.SERVER_SIDE, 
								  Packets.Server.MOB_SPAWN, Packets.Server.ENTITY_METADATA) {
			@Override
			public void onPacketSending(PacketEvent event) {
				PacketContainer packet = event.getPacket();
				
				// You may also want to check event.getPacketID()
				final Entity entity = packet.getEntityModifier(event.getPlayer().getWorld()).read(0);
				final String name = entityName.get(entity.getUniqueId(), event.getPlayer().getName());
								
				if (name != null) {
					// Clone the packet!
					event.setPacket(packet = packet.deepClone());
				
					// This comes down to a difference in what the packets store in memory
					if (event.getPacketID() == Packets.Server.ENTITY_METADATA) {
						WrappedDataWatcher watcher = new WrappedDataWatcher(packet.getWatchableCollectionModifier().read(0));
						
						System.out.println("Set entity name " + name + " for " + event.getPlayer());
						processDataWatcher(watcher, name);
						packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
					} else {
						System.out.println("Spawn entity with name " + name + " for " + event.getPlayer());
						processDataWatcher(packet.getDataWatcherModifier().read(0), name);
					}
				}
			}
		});
		
		// Listeners
		getServer().getPluginManager().registerEvents(this, this);
	}
 
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		final LivingEntity entity = e.getEntity();
		final String name = Integer.toString(entity.getEntityId());
		
		// Different colors - how you compute and store this is up to you
		entityName.put(entity.getUniqueId(), "aadnk", ChatColor.RED + name);
		entityName.put(entity.getUniqueId(), "Acirium", ChatColor.GREEN + name);
		
		entity.setCustomName(name);
		entity.setCustomNameVisible(true);
	}
	
	private void processDataWatcher(WrappedDataWatcher watcher, String name) {
		// If it's being updated, change it!
		if (watcher.getObject(CUSTOM_NAME_INDEX) != null) {
			watcher.setObject(CUSTOM_NAME_INDEX, name);
		}
	}
}
