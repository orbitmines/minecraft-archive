package om.fog.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import om.api.utils.ItemUtils;
import om.fog.handlers.players.FoGPlayer;
import om.fog.invs.ShieldsInv;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.ShieldLore.ArmorType;
import om.fog.utils.enums.ShieldLore.ShieldLoreLevel;
import om.fog.utils.enums.Suit;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Shields {

	private FoGPlayer omp;
	private ShieldsInv shieldsInv;
	private ShieldLoreLevel[][] shieldItems;
	
	public Shields(FoGPlayer omp, Suit suit, Faction faction, ShieldLoreLevel[][] shieldItems){
		this.omp = omp;
		this.shieldItems = shieldItems;
		this.shieldsInv = new ShieldsInv(suit, faction);
	}
	
	public ShieldLoreLevel[][] getShieldItems() {
		return shieldItems;
	}
	public void setShieldItems(ShieldLoreLevel[][] shieldItems) {
		this.shieldItems = shieldItems;
		
		omp.updateShields(omp.getUUID().toString());
	}
	public void addShieldItem(FoGPlayer omp, ArmorType type, ShieldLoreLevel shieldItem){
		int index = 0;
		for(ShieldLoreLevel sl : shieldItems[type.getIndex()]){
			if(sl == null){
				shieldItems[type.getIndex()][index] = shieldItem;
				break;
			}
			else{
				if(sl.getLore() == shieldItem.getLore()){
					shieldItems[type.getIndex()][index] = null;
					omp.getPlayer().getInventory().addItem(sl.getLore().getEnchantment(sl.getLevel()));
				}
			}
			index++;
		}
		
		updateShieldItem();
	}
	public void removeShieldItem(FoGPlayer omp, ArmorType type, int slot){
		ShieldLoreLevel sl = shieldItems[type.getIndex()][slot];
		shieldItems[type.getIndex()][slot] = null;
		omp.getPlayer().getInventory().addItem(sl.getLore().getEnchantment(sl.getLevel()));
		
		updateShieldItem();
	}
	
	private void updateShieldItem(){
		ShieldLoreLevel[][] newSl = new ShieldLoreLevel[4][8];
		
		for(int i = 0; i < 4; i++){
			int index = 0;
			for(ShieldLoreLevel sl : Arrays.asList(shieldItems[i])){
				if(sl != null){
					newSl[i][index] = sl;
					index++;
				}
			}
		}
		
		setShieldItems(newSl);
	}
	
	public void checkItems(FoGPlayer omp, ArmorType type, ItemStack item){
		item = ItemUtils.setUnbreakable(ItemUtils.hideFlags(item, 2, 4));
		
		ItemStack[] armorContents = omp.getPlayer().getInventory().getArmorContents();
		armorContents[type.getIndex()] = item;
		omp.getPlayer().getInventory().setArmorContents(armorContents);
		omp.updateShield();
	}
	
	public ItemStack getShield(FoGPlayer omp, ArmorType type, Suit suit){
		ItemStack item = new ItemStack(suit.getArmorContents(omp.getFaction())[type.getIndex()]);
		for(ShieldLoreLevel sl : shieldItems[type.getIndex()]){
			if(sl != null){
				sl.getLore().addAfter(item, sl.getLevel());
			}
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(omp.getFaction().getColor() + suit.getName(omp.getFaction()) + " §8| " + omp.getFaction().getColor() + type.getName());
		List<String> lore = meta.getLore();
		if(lore == null){
			lore = new ArrayList<String>();
		}
		
		lore.add("");
		lore.add("§fSoulbound");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ShieldsInv getShieldsInv() {
		return shieldsInv;
	}
	
	public void setShieldsInv(Suit suit, Faction faction) {
		this.shieldsInv = new ShieldsInv(suit, faction);
	}
}
