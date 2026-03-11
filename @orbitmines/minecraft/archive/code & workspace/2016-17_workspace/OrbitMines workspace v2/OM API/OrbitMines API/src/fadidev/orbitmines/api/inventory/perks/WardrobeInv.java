package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.nms.customitem.CustomItemNms;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class WardrobeInv extends OMInventory {

	public WardrobeInv(){
		setInventory(Bukkit.createInventory(null, 54, "§0§lWardrobe"));
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

        if (ItemUtils.isNull(item))
            return;

        Player p = omp.getPlayer();

        if(item.getType() == Material.LEATHER_CHESTPLATE && item.getItemMeta().getDisplayName().contains("Disco Armor")){
            if(omp.getWardrobe().size() >= 2){
                if(omp.hasUnlockedWardrobeDisco()){
                    p.closeInventory();
                    omp.setWardrobeDisco(true);
                }
                else{
                    if(omp.hasVIPPoints(500))
                        new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 500, new ConfirmInv.Action() {
                            @Override
                            public void confirmed(OMPlayer omp) {
                                omp.removeVipPoints(500);
                                omp.setUnlockedWardrobeDisco(true);
                                new WardrobeInv().open(omp.getPlayer());
                            }

                            @Override
                            public void cancelled(OMPlayer omp) {
                                new WardrobeInv().open(omp.getPlayer());
                            }
                        }).open(p);
                    else
                        omp.requiredVIPPoints(500);
                }
            }
            else{
                p.sendMessage("§7" + Messages.WORD_REQUIRED.get(omp) + ": " + item.getItemMeta().getLore().get(3).substring(12));
                p.playSound(p.getLocation(), Sound.BLOCK_LAVA_POP, 5, 1);
            }
        }
        else if(item.getType() == Material.IRON_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§7Iron Armor")){
            if(omp.hasPerms(VIPRank.IRON_VIP)){
                p.closeInventory();
                omp.wardrobe(VIPRank.IRON_VIP);
            }
            else{
                omp.requiredVIPRank(VIPRank.IRON_VIP);
            }
        }
        else if(item.getType() == Material.GOLD_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§6Gold Armor")){
            if(omp.hasPerms(VIPRank.GOLD_VIP)){
                p.closeInventory();
                omp.wardrobe(VIPRank.GOLD_VIP);
            }
            else{
                omp.requiredVIPRank(VIPRank.GOLD_VIP);
            }
        }
        else if(item.getType() == Material.DIAMOND_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§bDiamond Armor")){
            if(omp.hasPerms(VIPRank.DIAMOND_VIP)){
                p.closeInventory();
                omp.wardrobe(VIPRank.DIAMOND_VIP);
            }
            else{
                omp.requiredVIPRank(VIPRank.DIAMOND_VIP);
            }
        }
        else if(item.getType() == Material.CHAINMAIL_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§7Chainmail Armor")){
            if(omp.hasPerms(VIPRank.EMERALD_VIP)){
                p.closeInventory();
                omp.wardrobe(VIPRank.EMERALD_VIP);
            }
            else{
                omp.requiredVIPRank(VIPRank.EMERALD_VIP);
            }
        }
        else if(item.getType() == Material.ENDER_CHEST){
            new CosmeticPerksInv().open(p);
        }
        else if(item.getType() == Material.LAVA_BUCKET){
            if(omp.hasWardrobeEnabled()){
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 5, 1);
                omp.disableWardrobe();
            }
            else{
                p.sendMessage(Messages.INV_WARDROBE_NOT_ENABLED.get(omp));
            }
        }
        else{
            for(final Color color : ColorUtils.WARDROBE){
                if(item.getType() == Material.LEATHER_CHESTPLATE && item.getItemMeta().getDisplayName().equals(ColorUtils.getName(color) + " Armor")){
                    if(omp.hasWardrobe(color)){
                        p.closeInventory();
                        omp.wardrobe(color);
                    }
                    else{
                        if(omp.hasVIPPoints(250))
                            new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 250, new ConfirmInv.Action() {
                                @Override
                                public void confirmed(OMPlayer omp) {
                                    omp.removeVipPoints(250);
                                    omp.addWardrobe(color);
                                    new WardrobeInv().open(omp.getPlayer());
                                }

                                @Override
                                public void cancelled(OMPlayer omp) {
                                    new WardrobeInv().open(omp.getPlayer());
                                }
                            }).open(p);
                        else
                            omp.requiredVIPPoints(250);
                    }
                    break;
                }
            }
        }
    }
	
	private ItemStack[] getContents(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
        CustomItemNms itemNms = OrbitMinesAPI.getApi().getNms().customItem();

		contents[10] = getItem(omp, Color.WHITE);
		contents[11] = getItem(omp, Color.BLUE);
		contents[12] = getItem(omp, Color.GREEN);
		contents[13] = getItem(omp, Color.BLACK);
		contents[14] = getItem(omp, Color.AQUA);
		contents[15] = getItem(omp, Color.FUCHSIA);
		contents[16] = getItem(omp, Color.LIME);
		contents[19] = getItem(omp, Color.NAVY);
		contents[20] = getItem(omp, Color.PURPLE);
		contents[21] = getItem(omp, Color.ORANGE);
		contents[22] = getItem(omp, Color.RED);
		contents[23] = getItem(omp, Color.TEAL);
		contents[24] = getItem(omp, Color.YELLOW);
		contents[25] = getItem(omp, Color.GRAY);
		{
			ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Iron Armor");
			List<String> itemLore = new ArrayList<>();
			itemLore.add("");
			if(!omp.hasPerms(VIPRank.IRON_VIP))
				itemLore.add("§c" + Messages.WORD_REQUIRED.get(omp) + ": §7§lIron VIP");
			else
				itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
			
			itemLore.add("");
			itemmeta.setLore(itemLore);
			item.setItemMeta(itemmeta);
			contents[29] = itemNms.hideFlags(item, 2);
		}
		{
			ItemStack item = new ItemStack(Material.GOLD_CHESTPLATE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§6Gold Armor");
			List<String> itemLore = new ArrayList<>();
			itemLore.add("");
			if(!omp.hasPerms(VIPRank.GOLD_VIP))
				itemLore.add("§c" + Messages.WORD_REQUIRED.get(omp) + ": §6§lGold VIP");
			else
				itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));

			itemLore.add("");
			itemmeta.setLore(itemLore);
			item.setItemMeta(itemmeta);
			contents[30] = itemNms.hideFlags(item, 2);
		}
		{
			ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§bDiamond Armor");
			List<String> itemLore = new ArrayList<>();
			itemLore.add("");
			if(!omp.hasPerms(VIPRank.DIAMOND_VIP))
				itemLore.add("§c" + Messages.WORD_REQUIRED.get(omp) + ": §b§lDiamond VIP");
			else
				itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));

			itemLore.add("");
			itemmeta.setLore(itemLore);
			item.setItemMeta(itemmeta);
			contents[32] = itemNms.hideFlags(item, 2);
		}
		{
			ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Chainmail Armor");
			List<String> itemLore = new ArrayList<>();
			itemLore.add("");
			if(!omp.hasPerms(VIPRank.EMERALD_VIP))
				itemLore.add("§c" + Messages.WORD_REQUIRED.get(omp) + ": §a§lEmerald VIP");
			else
				itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));

			itemLore.add("");
			itemmeta.setLore(itemLore);
			item.setItemMeta(itemmeta);
			contents[33] = itemNms.hideFlags(item, 2);
		}
		
		contents[31] = setDiscoItem(getInventory(), omp);
		
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[48] = item;
		}
		{
			ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(Messages.INV_DISABLE_WARDROBE.get(omp));
			item.setItemMeta(itemmeta);
			contents[50] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Color color){
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
		itemmeta.setDisplayName(ColorUtils.getName(color) + " Armor");
		List<String> itemLore = new ArrayList<>();
		itemLore.add("");
		if(!omp.hasWardrobe(color))
			itemLore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b250 VIP Points");
		else
			itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));

		itemLore.add("");
		itemmeta.setColor(color);
		itemmeta.setLore(itemLore);
		item.setItemMeta(itemmeta);
		
		return OrbitMinesAPI.getApi().getNms().customItem().hideFlags(item, 2);
	}
	
	public static ItemStack setDiscoItem(Inventory inv, OMPlayer omp){
		Color color = ColorUtils.random(ColorUtils.WARDROBE);
		
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
		itemmeta.setDisplayName(ColorUtils.getColor(color) + "Disco Armor");
		List<String> itemLore = new ArrayList<>();
		itemLore.add(Messages.INV_USES_UNLOCKED_ARMOR.get(omp));
		itemLore.add("");
		
		if(omp.getWardrobe().size() >= 2){
			if(!omp.hasUnlockedWardrobeDisco())
				itemLore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b500 VIP Points");
			else
				itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
		}
		else{
			itemLore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b500 VIP Points");
			itemLore.add("§c" + Messages.WORD_REQUIRED.get(omp) + ": " + ColorUtils.getColor(color) + "2 Armor Sets");
		}
		itemLore.add("");
		itemmeta.setColor(color);
		itemmeta.setLore(itemLore);
		item.setItemMeta(itemmeta);
		inv.setItem(31, item);
		return OrbitMinesAPI.getApi().getNms().customItem().hideFlags(item, 2);
	}
}
