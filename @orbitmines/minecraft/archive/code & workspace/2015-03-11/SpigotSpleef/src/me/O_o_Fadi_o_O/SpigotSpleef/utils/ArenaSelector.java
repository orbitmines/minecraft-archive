package me.O_o_Fadi_o_O.SpigotSpleef.utils;

import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.Start;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class ArenaSelector {

	private Inventory inventory;
	private int size;
	private String name;
	private List<ArenaSelectorItemStack> itemstacks;
	private int animateddots;
	
	public ArenaSelector(Inventory inventory, int size, String name, List<ArenaSelectorItemStack> itemstacks){
		this.inventory = inventory;
		this.size = size;
		this.name = name;
		this.itemstacks = itemstacks;
		this.animateddots = 0;
		
		new BukkitRunnable(){
			public void run(){
				nextAnimatedDots();
				int index = 0;
				for(ArenaSelectorItemStack item : getItemStacks()){
					if(item != null){
						item.update();
						getInventory().setItem(index, item.getItemStack());
					}
					else{
						getInventory().setItem(index, null);
					}
					index++;
				}
			}
		}.runTaskTimer(Start.getInstance(), 0, 20);
	}
	
	public int getAnimatedDots(){
		return this.animateddots;
	}
	public String getAnimatedDotsString(){
		return Message.getMessage(MessageName.ANIMATED_DOTS).getMessages().get((getAnimatedDots() -1));
	}
	public String nextAnimatedDots(){
		List<String> messages = Message.getMessage(MessageName.ANIMATED_DOTS).getMessages();
		
		if(this.animateddots >= messages.size()){this.animateddots = 0;}
		this.animateddots = getAnimatedDots() +1;
		return messages.get((getAnimatedDots() -1));
	}

	public Inventory getInventory(){
		return this.inventory;
	}
	public void setInventory(Inventory inventory){
		this.inventory = inventory;
	}

	public int getSize(){
		return this.size;
	}
	public void setSize(int size){
		this.size = size;
		this.inventory.setMaxStackSize(getSize());
	}

	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}

	public List<ArenaSelectorItemStack> getItemStacks(){
		return this.itemstacks;
	}
	public void setItemStacks(List<ArenaSelectorItemStack> itemstacks){
		this.itemstacks = itemstacks;
	}
	
	public void openInventory(Player p){
		p.openInventory(getInventory());
	}
}
