package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.firework.FireworkSettings;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FireworkInv extends OMInventory {
	
	public FireworkInv(){
		setInventory(Bukkit.createInventory(null, 54, "§0§lFireworks"));
	}
	
	@Override
	public void open(Player player){
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());

		registerLast(player);
	}

	@Override
	public void onClick(OMPlayer omp, InventoryClickEvent e) {
		e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

		if(ItemUtils.isNull(item))
		    return;

        Player p = omp.getPlayer();
        FireworkSettings settings = omp.getFireworkSettings();
        
        if(item.getItemMeta().getDisplayName().startsWith("§7Color 1:")){
            settings.nextColor1();
            new FireworkInv().open(p);
        }
        else if(item.getItemMeta().getDisplayName().startsWith("§7Color 2:")){
            settings.nextColor2();
            new FireworkInv().open(p);
        }
        else if(item.getItemMeta().getDisplayName().startsWith("§7Fade 1:")){
            settings.nextFade1();
            new FireworkInv().open(p);
        }
        else if(item.getItemMeta().getDisplayName().startsWith("§7Fade 2:")){
            settings.nextFade2();
            new FireworkInv().open(p);
        }
        else if(item.getItemMeta().getDisplayName().startsWith("§7Trail:")){
            settings.nextTrail();
            new FireworkInv().open(p);
        }
        else if(item.getItemMeta().getDisplayName().startsWith("§7Flicker:")){
            settings.nextFlicker();
            new FireworkInv().open(p);
        }
        else if(item.getItemMeta().getDisplayName().startsWith("§7Type:")){
            settings.nextType();
            new FireworkInv().open(p);
        }
        else if(item.getType() == Material.ENDER_CHEST){
            new CosmeticPerksInv().open(p);
        }
        else if(item.getType() == Material.ANVIL){
            omp.giveFireworkGun();
        }
        else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§6§l+5 Firework Passes")){
            if(omp.hasVIPPoints(2))
                new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 2, new ConfirmInv.Action() {
                    @Override
                    public void confirmed(OMPlayer omp) {
                        omp.removeVipPoints(2);
                        omp.addFireworkPasses(5);
                        new FireworkInv().open(omp.getPlayer());
                    }

                    @Override
                    public void cancelled(OMPlayer omp) {
                        new FireworkInv().open(omp.getPlayer());
                    }
                }).open(p);
            else
                omp.requiredVIPPoints(2);
        }
        else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§6§l+25 Firework Passes")){
            if(omp.hasVIPPoints(10))
                new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 10, new ConfirmInv.Action() {
                    @Override
                    public void confirmed(OMPlayer omp) {
                        omp.removeVipPoints(10);
                        omp.addFireworkPasses(25);
                        new FireworkInv().open(omp.getPlayer());
                    }

                    @Override
                    public void cancelled(OMPlayer omp) {
                        new FireworkInv().open(omp.getPlayer());
                    }
                }).open(p);
            else
                omp.requiredVIPPoints(10);
        }
	}

	private ItemStack[] getContents(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		FireworkSettings fireworkSettings = omp.getFireworkSettings();
		{
			ItemStack item = new ItemStack(fireworkSettings.getColor1() == Color.MAROON ? Material.REDSTONE : Material.INK_SACK, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Color 1: §l" + ColorUtils.getName(fireworkSettings.getColor1()));
			item.setItemMeta(itemmeta);
			item.setDurability(ColorUtils.getDurability(fireworkSettings.getColor1()));

			contents[10] = item;
		}
		{
			Color color2 = fireworkSettings.getColor2();
			if(color2 != null){
				ItemStack item = new ItemStack(color2 == Color.MAROON ? Material.REDSTONE : Material.INK_SACK, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Color 2: §l" + ColorUtils.getName(color2));
				item.setItemMeta(itemmeta);
				item.setDurability(ColorUtils.getDurability(color2));

				contents[28] = item;
			}
			else{
				ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Color 2: " + Utils.statusString(omp.getLanguage(), false));
				item.setItemMeta(itemmeta);
				item.setDurability((short) 14);
				contents[28] = item;
			}
		}
		{
			Color fade1 = fireworkSettings.getFade1();
			if(fade1 != null){
				ItemStack item = new ItemStack(fade1 == Color.MAROON ? Material.REDSTONE : Material.INK_SACK, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Fade 1: §l" + ColorUtils.getName(fade1));
				item.setItemMeta(itemmeta);
				item.setDurability(ColorUtils.getDurability(fade1));
				contents[12] = item;
			}
			else{
				ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Fade 1: " + Utils.statusString(omp.getLanguage(), false));
				item.setItemMeta(itemmeta);
				item.setDurability((short) 14);
				contents[12] = item;
			}
		}
		{
			Color fade2 = fireworkSettings.getFade2();
			if(fade2 != null){
				ItemStack item = new ItemStack(fade2 == Color.MAROON ? Material.REDSTONE : Material.INK_SACK, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Fade 2: §l" + ColorUtils.getName(fade2));
				item.setItemMeta(itemmeta);
				item.setDurability(ColorUtils.getDurability(fade2));
				contents[30] = item;
			}
			else{
				ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Fade 2: " + Utils.statusString(omp.getLanguage(), false));
				item.setItemMeta(itemmeta);
				item.setDurability((short) 14);
				contents[30] = item;
			}
		}

		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Trail: " + Utils.statusString(omp.getLanguage(), fireworkSettings.hasTrail()));
			item.setItemMeta(itemmeta);
			item.setDurability(Utils.statusDurability(fireworkSettings.hasTrail()));
			contents[14] = item;
		}
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Flicker: " + Utils.statusString(omp.getLanguage(), fireworkSettings.hasFlicker()));
			item.setItemMeta(itemmeta);
			item.setDurability(Utils.statusDurability(fireworkSettings.hasFlicker()));
			contents[32] = item;
		}
		{
			ItemStack item = new ItemStack(ColorUtils.getMaterial(fireworkSettings.getType()), 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Type: " + ColorUtils.getName(fireworkSettings.getType()));
			item.setItemMeta(itemmeta);
			item.setDurability(ColorUtils.getDurability(fireworkSettings.getType()));
			contents[25] = item;
		}
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[48] = item;
		}
		{
			ItemStack item = new ItemStack(Material.ANVIL, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(Messages.INV_CREATE_FIREWORK.get(omp));
			item.setItemMeta(itemmeta);
			contents[49] = item;
		}
		{
			ItemStack item = new ItemStack(Material.EMPTY_MAP, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§c§nFirework Passes:§r §6§n" + omp.getFireworkPasses());
			item.setItemMeta(itemmeta);
			contents[50] = item;
		}
		{
			ItemStack item = new ItemStack(Material.EMPTY_MAP, 5);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§6§l+5 Firework Passes");
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b2 VIP Points");
			lore.add("");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			contents[52] = item;
		}
		{
			ItemStack item = new ItemStack(Material.EMPTY_MAP, 25);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§6§l+25 Firework Passes");
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b10 VIP Points");
			lore.add("");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			contents[53] = item;
		}
		
		return contents;
	}
}

