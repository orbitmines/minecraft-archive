package fadidev.orbitmines.api.inventory;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.perks.Color;
import fadidev.orbitmines.api.utils.enums.perks.TrailType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ConfirmInv extends OMInventory {
	
	private ItemStack itemstack;
	private Currency currency;
	private int price;
    private Action action;
	
	public ConfirmInv(ItemStack itemstack, Currency currency, int price, Action action){
		setInventory(Bukkit.createInventory(null, 45, "§0§lConfirm your Purchase"));

		if(itemstack == null)
		    return;

        this.itemstack = itemstack;
        this.currency = currency;
        this.price = price;
        this.action = action;
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

    private Action getAction() {
        return action;
    }

    public void open(Player player){
	    open(player, true);
	}

    public void open(Player player, boolean clearLore){
        OMPlayer omp = OMPlayer.getOMPlayer(player);
        getInventory().setContents(getContents(omp));
        setPriceItemStack(omp);
        setBuyingItemStack(omp, clearLore);
        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if (ItemUtils.isNull(item))
            return;

        Player p = omp.getPlayer();

        if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§a§l" + Messages.WORD_CONFIRM.get(omp))){
            p.sendMessage(Messages.INV_ITEM_BOUGHT.get(omp, e.getInventory().getItem(13).getItemMeta().getDisplayName()));
            p.sendMessage("§7" + Messages.WORD_PRICE.get(omp) + ": " + e.getInventory().getItem(31).getItemMeta().getDisplayName().substring(9) + "§7.");
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);

            getAction().confirmed(omp);
        }
        else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§l" + Messages.WORD_CANCEL.get(omp))){
            p.sendMessage(Messages.INV_PURCHASE_CANCELLED.get(omp, e.getInventory().getItem(13).getItemMeta().getDisplayName()));

            getAction().cancelled(omp);
        }
    }
	
	private void setBuyingItemStack(OMPlayer omp, boolean clearLore){
		ItemStack item = getItemStack();
		
		// Check
		if(item.getItemMeta().getDisplayName() != null){
			ItemMeta meta = item.getItemMeta();
			if(meta.getDisplayName().equals("§6§l+5 Firework Passes"))
				meta.setDisplayName("§6§l5 Firework Passes");
			else if(meta.getDisplayName().equals("§6§l+25 Firework Passes"))
				meta.setDisplayName("§6§l25 Firework Passes");
			else if(meta.getDisplayName().startsWith("§7Hat Block Trail"))
				meta.setDisplayName("§7Hat Block Trail");
			else if(meta.getDisplayName().endsWith(": " + Utils.statusString(omp.getLanguage(), false)))
				meta.setDisplayName(meta.getDisplayName().replace(": " + Utils.statusString(omp.getLanguage(), false), ""));
			
			for(TrailType trailtype : TrailType.values()){
				if(meta.getDisplayName().startsWith(trailtype.getName()))
					meta.setDisplayName(trailtype.getName());
			}

			if(clearLore)
			    meta.setLore(null);

			item.setItemMeta(meta);
		}
		
		getInventory().setItem(13, item);
	}
	
	private void setPriceItemStack(OMPlayer omp){
        Currency currency = getCurrency();

		ItemStack item = new ItemStack(currency.getMaterial(), 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§c" + Messages.WORD_PRICE.get(omp) + ": " + getCurrency().getColor() + getPrice() + " " + currency.getName(getPrice()));
		item.setItemMeta(meta);

		getInventory().setItem(31, item);
	}
	
	private ItemStack[] getContents(OMPlayer omp){
		ItemStack confirm = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta confirmmeta = confirm.getItemMeta();
		confirmmeta.setDisplayName("§a§l" + Messages.WORD_CONFIRM.get(omp));
		confirm.setItemMeta(confirmmeta);
		confirm.setDurability((short) 5);
		
		ItemStack cancel = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta cancelmeta = cancel.getItemMeta();
		cancelmeta.setDisplayName("§c§l" + Messages.WORD_CANCEL.get(omp));
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
		Color color = ColorUtils.random(ColorUtils.WARDROBE);
		org.bukkit.Color bukkitColor = color.getBukkitColor();

		ItemStack item = new ItemStack(color.getMaterial(), 1);
		if(bukkitColor != null) {
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(bukkitColor);
            meta.setDisplayName(color.getColor() + "Disco Armor");
            item.setItemMeta(meta);
        }
		
		inv.setItem(13, item);
	}

	public static abstract class Action {

        public abstract void confirmed(OMPlayer omp);
        public abstract void cancelled(OMPlayer omp);

    }
}
