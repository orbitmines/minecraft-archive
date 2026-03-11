package om.api.invs.others;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.ColorUtils;
import om.api.utils.enums.Currency;
import om.api.utils.enums.cp.TrailType;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ConfirmInv extends InventoryInstance {
	
	private ItemStack itemstack;
	private Currency currency;
	private int price;
	
	public ConfirmInv(ItemStack itemstack, Currency currency, int price){
		Inventory inventory = Bukkit.createInventory(null, 45, "§0§lConfirm your Purchase");
		if(itemstack != null){
			inventory.setContents(getContects());
			this.itemstack = itemstack;
			this.currency = currency;
			this.price = price;
		}
		this.inventory = inventory;
	}
	
	public ItemStack getItemStack(){
		return itemstack;
	}
	public void setItemStack(ItemStack itemstack){
		this.itemstack = itemstack;
	}
	
	public Currency getCurrency(){
		return currency;
	}
	public void setCurrency(Currency currency){
		this.currency = currency;
	}
	
	public int getPrice(){
		return price;
	}
	public void setPrice(int price){
		this.price = price;
	}
	
	public void open(Player player){
		setPriceItemStack();
		setBuyingItemStack();
		player.openInventory(getInventory());
	}
	
	private void setBuyingItemStack(){
		ItemStack item = getItemStack();
		
		// Check
		if(item.getItemMeta().getDisplayName() != null){
			ItemMeta meta = item.getItemMeta();
			if(meta.getDisplayName().equals("§6§l+5 Firework Passes")){
				meta.setDisplayName("§6§l5 Firework Passes");
			}
			if(meta.getDisplayName().equals("§6§l+25 Firework Passes")){
				meta.setDisplayName("§6§l25 Firework Passes");
			}
			if(meta.getDisplayName().startsWith("§7Hat Block Trail")){
				meta.setDisplayName("§7Hat Block Trail");
			}
			if(meta.getDisplayName().endsWith(": §c§lDISABLED")){
				meta.setDisplayName(meta.getDisplayName().replace(": §c§lDISABLED", ""));
			}
			
			for(TrailType trailtype : TrailType.values()){
				if(meta.getDisplayName().startsWith(trailtype.getName())){
					meta.setDisplayName(trailtype.getName());
				}
			}
			meta.setLore(null);
			item.setItemMeta(meta);
		}
		
		this.inventory.setItem(13, item);
	}
	
	private void setPriceItemStack(){
		ItemStack item = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta = item.getItemMeta();
		
		Currency currency = getCurrency();
		
		if(currency == Currency.VIP_POINTS){
			item.setType(Material.DIAMOND);
			meta.setDisplayName("§cPrice: §b§l" + getPrice() + " VIP Points");
		}
		else if(currency == Currency.ORBITMINES_TOKENS){
			item.setType(Material.GOLD_INGOT);
			if(getPrice() == 1){
				meta.setDisplayName("§cPrice: §e§l" + getPrice() + " OrbitMines Token");
			}
			else{
				meta.setDisplayName("§cPrice: §e§l" + getPrice() + " OrbitMines Tokens");
			}
		}
		else if(currency == Currency.MINIGAME_POINTS){
			item.setType(Material.SNOW_BALL);
			meta.setDisplayName("§cPrice: §f§l" + getPrice() + " MiniGame Coins");
		}
		else if(currency == Currency.PRISON_GOLD){
			item.setType(Material.GOLD_INGOT);
			meta.setDisplayName("§cPrice: §6§l" + getPrice() + " Gold");
		}
		else{}
		
		item.setItemMeta(meta);
		this.inventory.setItem(31, item);
	}
	
	private ItemStack[] getContects(){
		ItemStack confirm = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta confirmmeta = confirm.getItemMeta();
		confirmmeta.setDisplayName("§a§lConfirm");
		confirm.setItemMeta(confirmmeta);
		confirm.setDurability((short) 5);
		
		ItemStack cancel = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta cancelmeta = cancel.getItemMeta();
		cancelmeta.setDisplayName("§c§lCancel");
		cancel.setItemMeta(cancelmeta);
		cancel.setDurability((short) 14);
		
		ItemStack[] contents = new ItemStack[45];
		contents[0] = confirm;
		contents[1] = confirm;
		contents[2] = confirm;
		contents[9] = confirm;
		contents[10] = confirm;
		contents[11] = confirm;
		contents[18] = confirm;
		contents[19] = confirm;
		contents[20] = confirm;
		contents[27] = confirm;
		contents[28] = confirm;
		contents[29] = confirm;
		contents[36] = confirm;
		contents[37] = confirm;
		contents[38] = confirm;
		
		contents[6] = cancel;
		contents[7] = cancel;
		contents[8] = cancel;
		contents[15] = cancel;
		contents[16] = cancel;
		contents[17] = cancel;
		contents[24] = cancel;
		contents[25] = cancel;
		contents[26] = cancel;
		contents[33] = cancel;
		contents[34] = cancel;
		contents[35] = cancel;
		contents[42] = cancel;
		contents[43] = cancel;
		contents[44] = cancel;
		
		return contents;
	}
	
	public static void setDiscoItem(Inventory inv, OMPlayer omp){
		Color color = ColorUtils.random(ColorUtils.getWardrobe());
		
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();	
		meta.setColor(color);
		meta.setDisplayName(ColorUtils.getColor(color) + "Disco Armor");
		item.setItemMeta(meta);
		
		inv.setItem(13, item);
	}
}
