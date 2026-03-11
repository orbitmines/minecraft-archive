package om.fog.handlers;

import om.fog.handlers.players.FoGPlayer;
import om.fog.invs.BankInv;

import org.bukkit.inventory.ItemStack;

public class Bank {

	private FoGPlayer omp;
	private int size;
	private ItemStack[] items;
	private BankInv bankInv;
	
	public Bank(FoGPlayer omp, int size, ItemStack[] items){
		this.omp = omp;
		this.size = size;
		this.items = items;
		this.bankInv = new BankInv();
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public ItemStack[] getItems() {
		return items;
	}
	public void setItems(ItemStack[] items) {
		this.items = items;
	}
	public void setCurrentItems(ItemStack[] items){
		int page = getBankInv().getPage();
		int index = 9 * (page -1);
		
		for(ItemStack item : items){
			this.items[index] = item;
			
			index++;
		}
		
		omp.updateBank(omp.getUUID().toString());
	}
	
	public BankInv getBankInv() {
		return bankInv;
	}
}
