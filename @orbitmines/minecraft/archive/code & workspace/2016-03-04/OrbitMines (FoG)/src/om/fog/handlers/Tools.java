package om.fog.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import om.api.utils.ItemUtils;
import om.fog.handlers.players.FoGPlayer;
import om.fog.invs.ToolsInv;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Suit;
import om.fog.utils.enums.ToolLore.ToolLoreLevel;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Tools {

	private FoGPlayer omp;
	private ToolsInv toolsInv;
	private ToolLoreLevel[] toolItems;
	
	public Tools(FoGPlayer omp, Suit suit, Faction faction, ToolLoreLevel[] toolItems){
		this.omp = omp;
		this.toolItems = toolItems;
		this.toolsInv = new ToolsInv(suit, faction);
	}
	
	public ToolLoreLevel[] getToolItems() {
		return toolItems;
	}
	public void setToolItems(ToolLoreLevel[] toolItems) {
		this.toolItems = toolItems;
		
		omp.updateTools(omp.getUUID().toString());
	}
	public void addToolItem(FoGPlayer omp, ToolLoreLevel toolItem){
		int index = 0;
		for(ToolLoreLevel sl : toolItems){
			if(sl == null){
				toolItems[index] = toolItem;
				break;
			}
			else{
				if(sl.getLore() == toolItem.getLore()){
					toolItems[index] = null;
					omp.getPlayer().getInventory().addItem(sl.getLore().getEnchantment(sl.getLevel()));
				}
			}
			index++;
		}
		
		updateToolItem();
	}
	public void removeToolItem(FoGPlayer omp, int slot){
		ToolLoreLevel sl = toolItems[slot];
		toolItems[slot] = null;
		omp.getPlayer().getInventory().addItem(sl.getLore().getEnchantment(sl.getLevel()));
		
		updateToolItem();
	}
	
	private void updateToolItem(){
		ToolLoreLevel[] newSl = new ToolLoreLevel[9];
		int index = 0;
		for(ToolLoreLevel sl : Arrays.asList(toolItems)){
			if(sl != null){
				newSl[index] = sl;
				index++;
			}
		}
		
		setToolItems(newSl);
	}
	
	public void checkItems(Player p, ItemStack item){
		item = ItemUtils.setUnbreakable(ItemUtils.hideFlags(item, 2, 4));
		int slot = 0;
		for(ItemStack i : p.getInventory().getContents()){
			if(i != null && i.getItemMeta() != null && i.getItemMeta().getDisplayName() != null && i.getItemMeta().getDisplayName().endsWith("Tool")){
				p.getInventory().setItem(slot, item);
				return;
			}
			slot++;
		}
		
		p.getInventory().addItem(item);
	}
	
	public ItemStack getTool(FoGPlayer omp, Suit suit){
		ItemStack item = new ItemStack(suit.getTool());
		for(ToolLoreLevel sl : toolItems){
			if(sl != null){
				sl.getLore().addAfter(item, sl.getLevel());
			}
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(omp.getFaction().getColor() + suit.getName(omp.getFaction()) + " §8| " + omp.getFaction().getColor() + "Tool");
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		lore.add("");
		lore.add("§fSoulbound");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ToolsInv getToolsInv() {
		return toolsInv;
	}
	
	public void setToolsInv(Suit suit, Faction faction) {
		this.toolsInv = new ToolsInv(suit, faction);
	}
}
