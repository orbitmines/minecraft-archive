package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.enums.perks.Pet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PetInv extends OMInventory {

	private OrbitMinesAPI api;

	public PetInv(){
		api = OrbitMinesAPI.getApi();

		setInventory(Bukkit.createInventory(null, 45, "§0§lPets"));
	}
	
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

        if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().startsWith(Messages.INV_RENAME_PET_EMPTY.get(omp).split(" ")[0])){
            if(omp.getPetEnabled() != null){
                for(Pet pet : Pet.values()){
                    if(item.getItemMeta().getDisplayName().endsWith(pet.getName())){
                        new PetRenameGUI(omp, pet).open();
                        break;
                    }
                }
            }
            else{
                p.sendMessage(Messages.INV_PET_NOT_ENABLED.get(omp));
            }
        }
        else if(item.getType() == Material.ENDER_CHEST){
            new CosmeticPerksInv().open(p);
        }
        else if(item.getType() == Material.LAVA_BUCKET){
            if(omp.getPetEnabled() != null){
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 5, 1);
                omp.disablePet();
            }
            else{
                p.sendMessage(Messages.INV_PET_NOT_ENABLED.get(omp));
            }
        }
        else{
            for(final Pet pet : Pet.values()){
                if(item.getType() == pet.getMaterial() && item.getItemMeta().getDisplayName().equals(pet.getName())){
                    if(pet.hasPet(omp)){
                        p.closeInventory();

                        if(omp.getPetEnabled() != null){
                            omp.disablePet();
                        }
                        omp.spawnPet(pet);
                    }
                    else{
                        if(omp.hasVIPPoints(pet.getPrice())){
                            new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), pet.getPrice(), new ConfirmInv.Action() {
                                @Override
                                public void confirmed(OMPlayer omp) {
                                    omp.removeVipPoints(pet.getPrice());
                                    omp.addPet(pet);
                                    new PetInv().open(omp.getPlayer());
                                }

                                @Override
                                public void cancelled(OMPlayer omp) {
                                    new PetInv().open(omp.getPlayer());
                                }
                            }).open(p);
                        }
                        else{
                            omp.requiredVIPPoints(pet.getPrice());
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
		
		contents[10] = getItem(omp, Pet.SQUID);
		contents[11] = getItem(omp, Pet.MUSHROOM_COW);
		contents[12] = getItem(omp, Pet.PIG);
		contents[13] = getItem(omp, Pet.WOLF);
		contents[14] = getItem(omp, Pet.SHEEP);
		contents[15] = getItem(omp, Pet.HORSE);
		contents[16] = getItem(omp, Pet.CREEPER);
		contents[19] = getItem(omp, Pet.SPIDER);
		contents[20] = getItem(omp, Pet.MAGMA_CUBE);
		contents[21] = getItem(omp, Pet.SLIME);
		contents[22] = getItem(omp, Pet.COW);
		contents[23] = getItem(omp, Pet.SILVERFISH);
		contents[24] = getItem(omp, Pet.OCELOT);
		contents[25] = getItem(omp, Pet.CHICKEN);
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[39] = item;
		}
		{
			ItemStack item = new ItemStack(Material.NAME_TAG, 1);
			ItemMeta itemmeta = item.getItemMeta();
			if(omp.getPetEnabled() != null)
				itemmeta.setDisplayName(Messages.INV_RENAME_PET.get(omp, omp.getPetEnabled().getName()));
			else
				itemmeta.setDisplayName(Messages.INV_RENAME_PET_EMPTY.get(omp));
			
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b10 VIP Points");
			lore.add("");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			contents[40] = item;
		}
		{
			ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(Messages.INV_DISABLE_PET.get(omp));
			item.setItemMeta(itemmeta);
			contents[41] = item;
		}

		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Pet pet){
		ItemStack item = new ItemStack(pet.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(pet.getName());
		List<String> itemLore = new ArrayList<>();
		itemLore.add("");
		if(!pet.hasPet(omp))
			itemLore.add(pet.getPriceName(omp));
		else
			itemLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
			
		itemLore.add("");
		itemmeta.setLore(itemLore);
		item.setItemMeta(itemmeta);

		return api.getNms().customItem().setEggId(item, pet.getMob());
	}
}
