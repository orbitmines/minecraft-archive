package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.enums.perks.Gadget;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GadgetInv extends OMInventory {

	public GadgetInv(){
		setInventory(Bukkit.createInventory(null, 45, "§0§lGadgets"));
	}
	
	@Override
	public void open(Player player) {
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
        if(item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains(Messages.INV_DISABLE_GADGET.get(omp))){
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 5, 1);
            omp.disableGadget();
        }
        else if(item.getType() == Material.ENDER_CHEST){
            new CosmeticPerksInv().open(p);
        }
        else{
            for(final Gadget gadget : Gadget.values()){
                if(item.getType() == gadget.getMaterial() && item.getItemMeta().getDisplayName().equals(gadget.getName())){
                    if(gadget.hasGadget(omp)){
                        p.closeInventory();
                        omp.enableGadget(gadget);
                    }
                    else{
                        if(omp.hasVIPPoints(gadget.getPrice()))
                            new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), gadget.getPrice(), new ConfirmInv.Action() {
                                @Override
                                public void confirmed(OMPlayer omp) {
                                    omp.removeVipPoints(gadget.getPrice());
                                    omp.addGadget(gadget);
                                    new GadgetInv().open(omp.getPlayer());
                                }

                                @Override
                                public void cancelled(OMPlayer omp) {
                                    new GadgetInv().open(omp.getPlayer());
                                }
                            }).open(p);
                        else
                            omp.requiredVIPPoints(gadget.getPrice());
                    }
                    break;
                }
            }
        }
    }

	private ItemStack[] getContents(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		contents[10] = getItem(omp, Gadget.STACKER);
		contents[11] = getItem(omp, Gadget.PAINTBALLS);
		contents[12] = getItem(omp, Gadget.CREEPER_LAUNCHER);
		contents[13] = getItem(omp, Gadget.PET_RIDE);
		contents[14] = getItem(omp, Gadget.BOOK_EXPLOSION);
		contents[15] = getItem(omp, Gadget.SWAP_TELEPORTER);
		contents[16] = getItem(omp, Gadget.MAGMACUBE_SOCCER);
		contents[19] = getItem(omp, Gadget.SNOWMAN_ATTACK);
		contents[20] = getItem(omp, Gadget.FLAME_THROWER);
		contents[21] = getItem(omp, Gadget.GRAPPLING_HOOK);
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[39] = item;
		}

        ItemStack itemInInv = omp.getPlayer().getInventory().getItem(5);
        if(itemInInv != null && itemInInv.getItemMeta() != null && omp.canReceiveVelocity()) {
            ItemStack item = new ItemStack(itemInInv.getType());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(itemInInv.getItemMeta().getDisplayName());
            List<String> lore = new ArrayList<>();
            lore.add(Messages.INV_DISABLE_GADGET.get(omp));
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[41] = item;
        }
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Gadget gadget){
		ItemStack item = new ItemStack(gadget.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(gadget.getName());
		List<String> itemLore = new ArrayList<String>();
        itemLore.add("");
		if(!gadget.hasGadget(omp))
            itemLore.add(gadget.getPriceName(omp));
		else
            itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));

        itemLore.add("");
		itemmeta.setLore(itemLore);
		item.setItemMeta(itemmeta);
		item.setDurability(gadget.getDurability());
		
		return item;
	}
}

