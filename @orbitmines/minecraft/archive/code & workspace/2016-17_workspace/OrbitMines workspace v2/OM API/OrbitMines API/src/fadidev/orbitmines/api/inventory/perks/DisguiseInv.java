package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.enums.perks.Disguise;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DisguiseInv extends OMInventory {

    private OrbitMinesAPI api;

	public DisguiseInv(){
        api = OrbitMinesAPI.getApi();

		setInventory(Bukkit.createInventory(null, 54, "§0§lDisguises"));
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

        if(item.getType() == Material.ENDER_CHEST){
            new CosmeticPerksInv().open(p);
        }
        else if(item.getType() == Material.LAVA_BUCKET){
            if(omp.isDisguised()){
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 5, 1);
                omp.unDisguise();
            }
            else{
                p.sendMessage(Messages.INV_DISGUISE_NOT_ENABLED.get(omp));
            }
        }
        else{
            for(final Disguise disguise : Disguise.values()){
                if(item.getType() == disguise.getMaterial() && item.getItemMeta().getDisplayName().equals(disguise.getName())){
                    if(disguise.hasDisguise(omp)){
                        p.closeInventory();
                        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                        p.sendMessage(Messages.ENABLE_DISGUISE.get(omp, disguise.getName()));

                        omp.disguiseAsMob(disguise.getEntityType(), false, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
                    }
                    else{
                        if(disguise.getVIPRank() != null){
                            omp.requiredVIPRank(disguise.getVIPRank());
                        }
                        else{
                        	if(disguise == Disguise.ARMOR_STAND || disguise == Disguise.SNOWMAN)
                        		continue;

                            if(omp.hasVIPPoints(disguise.getPrice())){
                                new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), disguise.getPrice(), new ConfirmInv.Action() {
                                    @Override
                                    public void confirmed(OMPlayer omp) {
                                        omp.removeVipPoints(disguise.getPrice());
                                        omp.addDisguise(disguise);
                                        new DisguiseInv().open(omp.getPlayer());
                                    }

                                    @Override
                                    public void cancelled(OMPlayer omp) {
                                        new DisguiseInv().open(omp.getPlayer());
                                    }
                                }).open(p);
                            }
                            else{
                                omp.requiredVIPPoints(disguise.getPrice());
                            }
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

        contents[0] = getItem(omp, Disguise.ENDERMAN);
        contents[1] = getItem(omp, Disguise.WITCH);
        contents[2] = getItem(omp, Disguise.BAT);
        contents[3] = getItem(omp, Disguise.CHICKEN);
        contents[4] = getItem(omp, Disguise.OCELOT);
        contents[5] = getItem(omp, Disguise.MUSHROOM_COW);
        contents[6] = getItem(omp, Disguise.SQUID);
        contents[7] = getItem(omp, Disguise.PIG);
        contents[8] = getItem(omp, Disguise.IRON_GOLEM);
        contents[9] = getItem(omp, Disguise.GHAST);
        contents[10] = getItem(omp, Disguise.BLAZE);
        contents[11] = getItem(omp, Disguise.SLIME);
        contents[12] = getItem(omp, Disguise.ZOMBIE_PIGMAN);
        contents[13] = getItem(omp, Disguise.MAGMA_CUBE);
        contents[14] = getItem(omp, Disguise.SKELETON);
        contents[15] = getItem(omp, Disguise.ZOMBIE);
        contents[16] = getItem(omp, Disguise.VILLAGER);
        contents[17] = getItem(omp, Disguise.HORSE);
        contents[18] = getItem(omp, Disguise.RABBIT);
        contents[19] = getItem(omp, Disguise.WOLF);
        contents[20] = getItem(omp, Disguise.SPIDER);
        contents[21] = getItem(omp, Disguise.SILVERFISH);
        contents[22] = getItem(omp, Disguise.SHEEP);
        contents[23] = getItem(omp, Disguise.CAVE_SPIDER);
        contents[24] = getItem(omp, Disguise.CREEPER);
        contents[25] = getItem(omp, Disguise.COW);
        contents[26] = getItem(omp, Disguise.SNOWMAN);
        contents[31] = getItem(omp, Disguise.ARMOR_STAND);
		
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
			itemmeta.setDisplayName(Messages.INV_DISABLE_DISGUISE.get(omp));
			item.setItemMeta(itemmeta);
			contents[50] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Disguise disguise){
		ItemStack item = new ItemStack(disguise.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(disguise.getName());
		List<String> itemLore = new ArrayList<>();
		itemLore.add("");
		if(!disguise.hasDisguise(omp))
			itemLore.add(disguise.getPriceName(omp));
		else
			itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));

		itemLore.add("");
		itemmeta.setLore(itemLore);
		item.setItemMeta(itemmeta);

        if(disguise.getMob() == null)
            return item;

		return api.getNms().customItem().setEggId(item, disguise.getMob());
	}
}
