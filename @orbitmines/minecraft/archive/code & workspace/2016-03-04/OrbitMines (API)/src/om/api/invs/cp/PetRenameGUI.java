package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.AnvilGUI;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.cp.Pet;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PetRenameGUI {
	
	private AnvilGUI anvilgui;
	
	public PetRenameGUI(final OMPlayer omp, final Pet pet){
		this.anvilgui = new AnvilGUI(omp.getPlayer(), new AnvilGUI.AnvilClickEventHandler(){
			
			@Override
			public void onAnvilClick(AnvilGUI.AnvilClickEvent e){
				if(e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
					String petname = e.getName();
					Player p = omp.getPlayer();
					
					if(omp.hasVIPPoints(10)){
						
						e.setWillClose(true);
						e.setWillDestroy(true);
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
						
						if(omp.getPetEnabled() != null){
							omp.disablePet();
						}

						p.sendMessage("§9Cosmetic Perks §8| §7Changed your " + pet.getName() + "§7 name to §f" + petname + "§7!");
						
						omp.setPetName(pet, petname);
						omp.removeVIPPoints(10);
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
		
		{
			ItemStack item = new ItemStack(Material.MONSTER_EGG, 1);
			item.setDurability((short) pet.getDurability());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("Insert Petname");
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("§cPrice: §b10 VIP Points");
			lore.add("");
			meta.setLore(lore);
			item.setItemMeta(meta);
			this.anvilgui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, item);
		}
	}
	
	public AnvilGUI getAnvilGUI(){
		return anvilgui;
	}
	public void setAnvilGUI(AnvilGUI anvilgui){
		this.anvilgui = anvilgui;
	}
	
	public void open(){
		anvilgui.open();
	}
}
