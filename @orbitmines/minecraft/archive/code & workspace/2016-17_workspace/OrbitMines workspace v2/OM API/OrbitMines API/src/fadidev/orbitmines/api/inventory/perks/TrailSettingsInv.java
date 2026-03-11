package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.perks.TrailType;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TrailSettingsInv extends OMInventory {
	
	public TrailSettingsInv(){
		setInventory(Bukkit.createInventory(null, 54, "§0§lTrail Settings"));
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

        if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().startsWith("§7§lSpecial Trail")){
            if(omp.hasUnlockedSpecialTrail()){
                omp.setSpecialTrail(!omp.hasSpecialTrail());
                new TrailSettingsInv().open(p);
            }
            else{
                if(omp.hasVIPPoints(750))
                    new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 750, new ConfirmInv.Action() {
                        @Override
                        public void confirmed(OMPlayer omp) {
                            omp.removeVipPoints(750);
                            omp.setUnlockedSpecialTrail(true);
                            new TrailSettingsInv().open(omp.getPlayer());
                        }

                        @Override
                        public void cancelled(OMPlayer omp) {
                            new TrailSettingsInv().open(omp.getPlayer());
                        }
                    }).open(p);
                else
                    omp.requiredVIPPoints(750);
            }
        }
        else if(item.getType() == Material.NETHER_STAR && item.getItemMeta().getDisplayName().startsWith("§7§lParticle Amount")){
            if(omp.hasPerms(VIPRank.GOLD_VIP)){
                omp.addTrailParticleAmount();

                item.setAmount(omp.getTrailParticleAmount());
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§7§lParticle Amount: §f§l" + omp.getTrailParticleAmount());
                item.setItemMeta(meta);
            }
            else{
                omp.requiredVIPRank(VIPRank.GOLD_VIP);
            }
        }
        else if(item.getType() == Material.ENDER_CHEST){
            new CosmeticPerksInv().open(p);
        }
        else{
            for(final TrailType trailtype : TrailType.values()){
                if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().startsWith(trailtype.getName())){
                    if(trailtype.hasTrailType(omp)){
                        if(omp.getTrailType() != trailtype){
                            omp.setTrailType(trailtype);
                            new TrailSettingsInv().open(p);
                        }
                    }
                    else{
                        if(omp.hasVIPPoints(trailtype.getPrice()))
                            new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), trailtype.getPrice(), new ConfirmInv.Action() {
                                @Override
                                public void confirmed(OMPlayer omp) {
                                    omp.removeVipPoints(trailtype.getPrice());
                                    omp.addTrailType(trailtype);
                                    new TrailSettingsInv().open(omp.getPlayer());
                                }

                                @Override
                                public void cancelled(OMPlayer omp) {
                                    new TrailSettingsInv().open(omp.getPlayer());
                                }
                            }).open(p);
                        else
                            omp.requiredVIPPoints(trailtype.getPrice());
                    }
                    break;
                }
            }
        }
    }
	
	private ItemStack[] getContents(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		contents[4] = ItemUtils.itemstack(Material.COMPASS, 1, "§7Trail Types");
		contents[9] = getItem(omp, TrailType.BASIC_TRAIL);
		contents[10] = getItem(omp, TrailType.GROUND_TRAIL);
		contents[11] = getItem(omp, TrailType.HEAD_TRAIL);
		contents[12] = getItem(omp, TrailType.BODY_TRAIL);
		contents[13] = getItem(omp, TrailType.BIG_TRAIL);
		contents[14] = getItem(omp, TrailType.VERTICAL_TRAIL);
		contents[15] = getItem(omp, TrailType.ORBIT_TRAIL);
		contents[16] = getItem(omp, TrailType.CYLINDER_TRAIL);
		contents[17] = getItem(omp, TrailType.SNAKE_TRAIL);
		
		{
			ItemStack item = ItemUtils.itemstack(Material.STAINED_GLASS_PANE, 1, "§fComing Soon...");
			
			contents[18] = item;
			contents[19] = item;
			contents[20] = item;
			contents[21] = item;
			contents[22] = item;
			contents[23] = item;
			contents[24] = item;
			contents[25] = item;
			contents[26] = item;
		}
		
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7§lSpecial Trail: " + Utils.statusString(omp.getLanguage(), omp.hasSpecialTrail()));
			List<String> itemLore = new ArrayList<>();
			itemLore.add("");
			if(omp.hasUnlockedSpecialTrail())
				itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
			else
				itemLore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b750 VIP Points");

			itemLore.add("");
			itemmeta.setLore(itemLore);
			item.setItemMeta(itemmeta);
			item.setDurability(Utils.statusDurability(omp.hasSpecialTrail()));
			contents[37] = item;
		}
		{
			ItemStack item = new ItemStack(Material.NETHER_STAR, omp.getTrailParticleAmount());
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7§lParticle Amount: §f§l" + omp.getTrailParticleAmount());
			List<String> itemLore = new ArrayList<>();
			itemLore.add("");
			if(omp.hasPerms(VIPRank.GOLD_VIP))
                itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
			else
				itemLore.add("§c§o" + Messages.WORD_REQUIRED.get(omp) + ": §6§lGold VIP");

			itemLore.add("");
			itemmeta.setLore(itemLore);
			item.setItemMeta(itemmeta);
			contents[43] = item;
		}
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[49] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, TrailType trailtype){
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(trailtype.getName() + ": " + Utils.statusString(omp.getLanguage(), omp.getTrailType() == trailtype));
		List<String> itemLore = new ArrayList<>();
		itemLore.add("");
		if(trailtype.hasTrailType(omp))
            itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
		else
			itemLore.add(trailtype.getPriceString(omp));

		itemLore.add("");
		itemmeta.setLore(itemLore);
		item.setItemMeta(itemmeta);
		item.setDurability(Utils.statusDurability(omp.getTrailType() == trailtype));
		
		return item;
	}
}
