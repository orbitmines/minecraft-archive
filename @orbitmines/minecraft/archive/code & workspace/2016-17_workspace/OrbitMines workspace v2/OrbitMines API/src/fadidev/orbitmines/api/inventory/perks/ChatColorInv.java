package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.perks.ChatColor;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChatColorInv extends OMInventory {
	
	public ChatColorInv(){
		setInventory(Bukkit.createInventory(null, 45, "§0§lChatColors"));
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

        if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().contains("Bold ChatColor")){
            if(omp.hasPerms(VIPRank.EMERALD_VIP) && omp.hasUnlockedChatColorBold()){
                p.closeInventory();

                omp.setBold(!omp.isChatColorBold());
            }
            else{
                if(omp.hasPerms(VIPRank.EMERALD_VIP)){
                    if(omp.hasVIPPoints(3000))
                        new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 3000, new ConfirmInv.Action() {
                            @Override
                            public void confirmed(OMPlayer omp) {
                                omp.removeVipPoints(3000);
                                omp.setUnlockedBold(true);
                                new ChatColorInv().open(omp.getPlayer());
                            }

                            @Override
                            public void cancelled(OMPlayer omp) {
                                new ChatColorInv().open(omp.getPlayer());
                            }
                        }).open(p);
                    else
                        omp.requiredVIPPoints(3000);
                }
                else{
                    omp.requiredVIPRank(VIPRank.EMERALD_VIP);
                }
            }
        }
        else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().contains("Cursive ChatColor")){
            if(omp.hasPerms(VIPRank.DIAMOND_VIP) && omp.hasUnlockedChatColorCursive()){
                p.closeInventory();

                omp.setCursive(!omp.isChatColorCursive());
            }
            else{
                if(omp.hasPerms(VIPRank.DIAMOND_VIP)){
                    if(omp.hasVIPPoints(2000))
                        new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 2000, new ConfirmInv.Action() {
                            @Override
                            public void confirmed(OMPlayer omp) {
                                omp.removeVipPoints(2000);
                                omp.setUnlockedCursive(true);
                                new ChatColorInv().open(omp.getPlayer());
                            }

                            @Override
                            public void cancelled(OMPlayer omp) {
                                new ChatColorInv().open(omp.getPlayer());
                            }
                        }).open(p);
                    else
                        omp.requiredVIPPoints(2000);
                }
                else{
                    omp.requiredVIPRank(VIPRank.DIAMOND_VIP);
                }
            }
        }
        else if(item.getType() == Material.ENDER_CHEST){
            new CosmeticPerksInv().open(p);
        }
        else{
            for(final ChatColor chatcolor : ChatColor.values()){
                if(item.getType() == chatcolor.getMaterial() && item.getItemMeta().getDisplayName().equals(chatcolor.getName())){
                    if(chatcolor.hasChatColor(omp)){
                        p.closeInventory();
                        omp.setChatColor(chatcolor);
                    }
                    else{
                        if(chatcolor.getVIPRank() != null){
                            omp.requiredVIPRank(chatcolor.getVIPRank());
                        }
                        else{
                            if(omp.hasVIPPoints(chatcolor.getPrice()))
                                new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), chatcolor.getPrice(), new ConfirmInv.Action() {
                                    @Override
                                    public void confirmed(OMPlayer omp) {
                                        omp.removeVipPoints(chatcolor.getPrice());
                                        omp.addChatColor(chatcolor);
                                        new ChatColorInv().open(omp.getPlayer());
                                    }

                                    @Override
                                    public void cancelled(OMPlayer omp) {
                                        new ChatColorInv().open(omp.getPlayer());
                                    }
                                }).open(p);
                            else
                                omp.requiredVIPPoints(chatcolor.getPrice());
                        }
                    }
                    break;
                }
            }
        }
    }
	
	private ItemStack[] getContents(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		contents[9] = getItem(omp, ChatColor.DARK_RED);
		contents[10] = getItem(omp, ChatColor.LIGHT_GREEN);
		contents[11] = getItem(omp, ChatColor.DARK_GRAY);
		contents[12] = getItem(omp, ChatColor.RED);
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(omp.getChatColor().getColor() + "§lBold ChatColor: " + Utils.statusString(omp.getLanguage(), omp.isChatColorBold()));
			List<String> itemLore = new ArrayList<>();
			itemLore.add("");
			if(omp.hasPerms(VIPRank.EMERALD_VIP)){
				if(!omp.hasUnlockedChatColorBold())
					itemLore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b3000 VIP Points");
				else
					itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
			}
			else{
				itemLore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b3000 VIP Points");
				itemLore.add("§c" + Messages.WORD_REQUIRED.get(omp) + ": §a§lEmerald VIP");
			}
			itemLore.add("");
			itemmeta.setLore(itemLore);
			item.setItemMeta(itemmeta);
			item.setDurability(Utils.statusDurability(omp.isChatColorBold()));
			contents[13] = item;
		}
		contents[14] = getItem(omp, ChatColor.YELLOW);
		contents[15] = getItem(omp, ChatColor.WHITE);
		contents[16] = getItem(omp, ChatColor.LIGHT_BLUE);
		contents[17] = getItem(omp, ChatColor.PINK);
		contents[18] = getItem(omp, ChatColor.BLUE);
		contents[19] = getItem(omp, ChatColor.DARK_BLUE);
		contents[20] = getItem(omp, ChatColor.GRAY);
		contents[21] = getItem(omp, ChatColor.ORANGE);
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(omp.getChatColor().getColor() + "§oCursive ChatColor: " + Utils.statusString(omp.getLanguage(), omp.isChatColorCursive()));
			List<String> itemLore = new ArrayList<>();
			itemLore.add("");
			if(!omp.hasPerms(VIPRank.DIAMOND_VIP)){
				itemLore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b2000 VIP Points");
				itemLore.add("§c" + Messages.WORD_REQUIRED.get(omp) + ": §b§lDiamond VIP");
			}
			else{
				if(!omp.hasUnlockedChatColorCursive())
					itemLore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b2000 VIP Points");
				else
					itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
			}
			itemLore.add("");
			itemmeta.setLore(itemLore);
			item.setItemMeta(itemmeta);
			item.setDurability(Utils.statusDurability(omp.isChatColorCursive()));
			contents[22] = item;
		}
		contents[23] = getItem(omp, ChatColor.PURPLE);
		contents[24] = getItem(omp, ChatColor.CYAN);
		contents[25] = getItem(omp, ChatColor.GREEN);
		contents[26] = getItem(omp, ChatColor.BLACK);
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[39] = item;
		}
		{		
			ChatColor cc = omp.getChatColor();
			ItemStack item = new ItemStack(cc.getMaterial(), 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(cc.getName());
			item.setDurability(cc.getDurability());
			item.setItemMeta(itemmeta);
			contents[41] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, ChatColor chatcolor){
		ItemStack item = new ItemStack(chatcolor.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(chatcolor.getName());
		List<String> itemLore = new ArrayList<>();
		itemLore.add("");
		if(!chatcolor.hasChatColor(omp))
			itemLore.add(chatcolor.getPriceName(omp));
		else
			itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));

		itemLore.add("");
		itemmeta.setLore(itemLore);
		item.setDurability(chatcolor.getDurability());
		item.setItemMeta(itemmeta);
		
		return item;
	}
}

