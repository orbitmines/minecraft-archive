package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.enums.perks.Trail;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TrailInv extends OMInventory {

    private OrbitMinesAPI api;

	public TrailInv(){
	    this.api = OrbitMinesAPI.getApi();

		setInventory(Bukkit.createInventory(null, 45, "§0§lTrails"));
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

        if(item.getType() == Material.REDSTONE_COMPARATOR && item.getItemMeta().getDisplayName().equals("§f§nTrail " + Messages.WORD_SETTINGS.get(omp))){
            if(omp.hasTrail()){
                new TrailSettingsInv().open(p);
            }
            else{
                p.playSound(p.getLocation(), Sound.BLOCK_LAVA_POP, 5, 1);
                p.sendMessage("§7" + Messages.WORD_REQUIRED.get(omp) + ": " + Messages.INV_A_TRAIL.get(omp) + "§7.");
            }
        }
        else if(item.getType() == Material.ENDER_CHEST){
            new CosmeticPerksInv().open(p);
        }
        else if(item.getType() == Material.LAVA_BUCKET && !item.getItemMeta().getDisplayName().equals("§6Lava Trail")){
            if(omp.hasTrailEnabled()){
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 5, 1);
                omp.disableTrail();
            }
            else{
                p.sendMessage(Messages.INV_TRAIL_NOT_ENABLED.get(omp));
            }
        }
        else{
            for(final Trail trail : Trail.values()){
                if(item.getType() == trail.getMaterial() && item.getItemMeta().getDisplayName().equals(trail.getName())){
                    if(trail.hasTrail(omp)){
                        p.closeInventory();
                        omp.setTrail(trail);
                    }
                    else{
                        if(trail.getVIPRank() != null){
                            omp.requiredVIPRank(trail.getVIPRank());
                        }
                        else{
                            if(omp.hasVIPPoints(trail.getPrice()))
                                new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), trail.getPrice(), new ConfirmInv.Action() {
                                    @Override
                                    public void confirmed(OMPlayer omp) {
                                        omp.removeVipPoints(trail.getPrice());
                                        omp.addTrail(trail);
                                        new TrailInv().open(omp.getPlayer());
                                    }

                                    @Override
                                    public void cancelled(OMPlayer omp) {
                                        new TrailInv().open(omp.getPlayer());
                                    }
                                }).open(p);
                            else
                                omp.requiredVIPPoints(trail.getPrice());
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
		
		contents[9] = getItem(omp, Trail.FIREWORK_SPARK);
		contents[10] = getItem(omp, Trail.HAPPY_VILLAGER);
		contents[11] = getItem(omp, Trail.HEART);
		contents[12] = getItem(omp, Trail.TNT);
		contents[13] = getItem(omp, Trail.MAGIC);
		contents[14] = getItem(omp, Trail.ANGRY_VILLAGER);
		contents[15] = getItem(omp, Trail.LAVA);
		contents[16] = getItem(omp, Trail.SLIME);
		contents[17] = getItem(omp, Trail.SMOKE);
		contents[18] = getItem(omp, Trail.WITCH);
		contents[19] = getItem(omp, Trail.CRIT);
		contents[20] = getItem(omp, Trail.WATER);
		contents[21] = getItem(omp, Trail.MUSIC);
		contents[22] = getItem(omp, Trail.SNOW);
		contents[23] = getItem(omp, Trail.ENCHANTMENT_TABLE);
		contents[24] = getItem(omp, Trail.RAINBOW);
		contents[25] = getItem(omp, Trail.MOB_SPAWNER);
		contents[26] = getItem(omp, Trail.BUBBLE);

		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[39] = item;
		}
		{
			ItemStack item = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§f§nTrail " + Messages.WORD_SETTINGS.get(omp));
			item.setItemMeta(itemmeta);
			contents[40] = item;
		}
		{
			ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(Messages.INV_DISABLE_TRAIL.get(omp));
			item.setItemMeta(itemmeta);
			contents[41] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Trail trail){
		ItemStack item = new ItemStack(trail.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(trail.getName());
		List<String> itemLore = new ArrayList<>();
		itemLore.add("");
		if(!trail.hasTrail(omp)){
			itemLore.add(trail.getPriceName(omp));
		}
		else{
			itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
		}
		itemLore.add("");
		itemmeta.setLore(itemLore);
		item.setItemMeta(itemmeta);
		item.setDurability(trail.getDurability());
		
		if(trail == Trail.CRIT)
			item = api.getNms().customItem().hideFlags(item, 2);
		
		return item;
	}
}
