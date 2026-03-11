package fadidev.orbitmines.creative.inventories;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.nms.anvilgui.AnvilNms;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlotKitNameGUI {

	private OrbitMinesAPI api;
	private AnvilNms anvilNms;

	public PlotKitNameGUI(final CreativePlayer omp){
	    this.api = OrbitMinesAPI.getApi();
		this.anvilNms = api.getNms().anvilgui(omp.getPlayer(), new AnvilNms.AnvilClickEventHandler(){

			@Override
			public void onAnvilClick(AnvilNms.AnvilClickEvent e){
				if(e.getSlot() == AnvilNms.AnvilSlot.OUTPUT){
					String kitName = e.getName();
                    Player p = omp.getPlayer();
					Plot plot = omp.getPlot();

					Kit kit = null;
					for(Kit k : plot.getPvPKits()){
						if(k.getName().equals(kitName))
							kit = k;
					}

					if(kit == null){
						if(kitName.length() <= 20){
							boolean cancreate = true;
							for(int i = 0; i < kitName.length(); i++){
								int c = kitName.charAt(i);
								if(!Character.isAlphabetic(c) && !Character.isDigit(c))
									cancreate = false;
							}

							e.setWillClose(cancreate);
							e.setWillDestroy(cancreate);

							if(cancreate){
								p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
								p.sendMessage(CreativeMessages.INV_CREATE_KIT.get(omp, kitName));
								plot.createKit(p.getLocation(), kitName);
							}
							else{
								p.sendMessage(CreativeMessages.INV_ONLY_CHARACTERS.get(omp));
							}
						}
						else{
							e.setWillClose(false);
							e.setWillDestroy(false);

							p.sendMessage(CreativeMessages.INV_TOO_LONG_NAME.get(omp));
						}
					}
					else{
						e.setWillClose(false);
						e.setWillDestroy(false);

						p.sendMessage(CreativeMessages.INV_KIT_NAME_USED.get(omp, kitName));
					}
				}
				else{
					e.setWillClose(false);
					e.setWillDestroy(false);
				}
			}
		});

        ItemStack item = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CreativeMessages.INV_INSERT_KIT_NAME.get(omp));
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
