package om.fog.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import om.api.utils.ItemUtils;
import om.fog.handlers.players.FoGPlayer;
import om.fog.invs.SwordsInv;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Suit;
import om.fog.utils.enums.SwordLore.SwordLoreLevel;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Swords {

	private FoGPlayer omp;
	private SwordsInv swordsInv;
	private SwordLoreLevel[] swordItems;
	
	public Swords(FoGPlayer omp, Suit suit, Faction faction, SwordLoreLevel[] swordItems){
		this.omp = omp;
		this.swordItems = swordItems;
		this.swordsInv = new SwordsInv(suit, faction);
	}
	
	public SwordLoreLevel[] getSwordItems() {
		return swordItems;
	}
	public void setSwordItems(SwordLoreLevel[] swordItems) {
		this.swordItems = swordItems;
		
		omp.updateSwords(omp.getUUID().toString());
	}
	public void addSwordItem(FoGPlayer omp, SwordLoreLevel swordItem){
		int index = 0;
		for(SwordLoreLevel sl : swordItems){
			if(sl == null){
				swordItems[index] = swordItem;
				break;
			}
			else{
				if(sl.getLore() == swordItem.getLore()){
					swordItems[index] = null;
					omp.getPlayer().getInventory().addItem(sl.getLore().getEnchantment(sl.getLevel()));
				}
			}
			index++;
		}
		
		updateSwordItem();
	}
	public void removeSwordItem(FoGPlayer omp, int slot){
		SwordLoreLevel sl = swordItems[slot];
		swordItems[slot] = null;
		omp.getPlayer().getInventory().addItem(sl.getLore().getEnchantment(sl.getLevel()));
		
		updateSwordItem();
	}
	
	private void updateSwordItem(){
		SwordLoreLevel[] newSl = new SwordLoreLevel[9];
		int index = 0;
		for(SwordLoreLevel sl : Arrays.asList(swordItems)){
			if(sl != null){
				newSl[index] = sl;
				index++;
			}
		}
		
		setSwordItems(newSl);
	}
	
	public void checkItems(Player p, ItemStack item){
		item = ItemUtils.setUnbreakable(ItemUtils.hideFlags(item, 2, 4));
		int slot = 0;
		for(ItemStack i : p.getInventory().getContents()){
			if(i != null && i.getItemMeta() != null && i.getItemMeta().getDisplayName() != null && i.getItemMeta().getDisplayName().endsWith("Weapon")){
				p.getInventory().setItem(slot, item);
				return;
			}
			slot++;
		}
		
		p.getInventory().addItem(item);
	}
	
	public ItemStack getSword(FoGPlayer omp, Suit suit){
		ItemStack item = new ItemStack(suit.getSword());
		for(SwordLoreLevel sl : swordItems){
			if(sl != null){
				sl.getLore().addAfter(item, sl.getLevel());
			}
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(omp.getFaction().getColor() + suit.getName(omp.getFaction()) + " §8| " + omp.getFaction().getColor() + "Weapon");
		List<String> lore = meta.getLore();
		if(lore == null) lore = new ArrayList<String>();
		lore.add("");
		lore.add("§fSoulbound");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public SwordsInv getSwordsInv() {
		return swordsInv;
	}
	
	public void setSwordsInv(Suit suit, Faction faction) {
		this.swordsInv = new SwordsInv(suit, faction);
	}
}
