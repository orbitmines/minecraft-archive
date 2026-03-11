package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.nms.anvilgui.AnvilNms;
import fadidev.orbitmines.api.utils.enums.perks.Pet;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PetRenameGUI {

	private OrbitMinesAPI api;
	private AnvilNms anvilNms;

	public PetRenameGUI(final OMPlayer omp, final Pet pet){
	    this.api = OrbitMinesAPI.getApi();
		this.anvilNms = api.getNms().anvilgui(omp.getPlayer(), new AnvilNms.AnvilClickEventHandler(){

			@Override
			public void onAnvilClick(AnvilNms.AnvilClickEvent e){
				if(e.getSlot() == AnvilNms.AnvilSlot.OUTPUT){
					String petName = e.getName();
					Player p = omp.getPlayer();
					
					if(omp.hasVIPPoints(10)){
						e.setWillClose(true);
						e.setWillDestroy(true);
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
						
						if(omp.getPetEnabled() != null)
							omp.disablePet();

						p.sendMessage(Messages.INV_PET_RENAMED.get(omp, pet.getName(), petName));
						
						omp.setPetName(pet, petName);
						omp.removeVipPoints(10);
						omp.spawnPet(pet);
					}
					else{
						e.setWillClose(false);
						e.setWillDestroy(false);
						omp.requiredVIPPoints(10);
					}
				}
				else{
					e.setWillClose(false);
					e.setWillDestroy(false);
				}
			}
		});

        ItemStack item = new ItemStack(Material.MONSTER_EGG, 1);
        item.setDurability(pet.getDurability());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Messages.INV_INSERT_PET_NAME.get(omp));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §b10 VIP Points");
        lore.add("");
        meta.setLore(lore);
        item.setItemMeta(meta);

        this.anvilNms.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, item);
	}

    public AnvilNms getAnvilNms() {
        return anvilNms;
    }

    public void open(){
		anvilNms.open();
	}
}
