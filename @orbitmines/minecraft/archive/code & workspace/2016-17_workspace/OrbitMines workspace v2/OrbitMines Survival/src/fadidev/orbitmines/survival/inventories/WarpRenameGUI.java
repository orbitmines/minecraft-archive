package fadidev.orbitmines.survival.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.nms.anvilgui.AnvilNms;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.handlers.Warp;
import fadidev.orbitmines.survival.utils.SurvivalUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WarpRenameGUI {

	private OrbitMinesSurvival survival;
	private AnvilNms anvilNms;

	public WarpRenameGUI(OMPlayer omPlayer, final Warp warp){
	    this.survival = OrbitMinesSurvival.getSurvival();
		final SurvivalPlayer omp = (SurvivalPlayer) omPlayer;

		this.anvilNms = survival.getApi().getNms().anvilgui(omp.getPlayer(), new AnvilNms.AnvilClickEventHandler(){

			@Override
			public void onAnvilClick(AnvilNms.AnvilClickEvent e){
				if(e.getSlot() == AnvilNms.AnvilSlot.OUTPUT){
					String warpName = e.getName();
					Player p = omp.getPlayer();
					List<Warp> warps = survival.getWarps();

					if(Warp.getWarp(warpName) == null){
						if(warpName.length() <= 20){
							boolean canCreate = true;
							for(int i = 0; i < warpName.length(); i++){
								int c = warpName.charAt(i);
								if(!Character.isAlphabetic(c) && !Character.isDigit(c)){
									canCreate = false;
                                    break;
								}
							}

							e.setWillClose(canCreate);
							e.setWillDestroy(canCreate);
							if(canCreate){
								if(warp == null){
									Biome b = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
									Warp w = new Warp(warps.size() +1, p.getUniqueId(), warpName, p.getLocation(), true, SurvivalUtils.getMaterial(b), SurvivalUtils.getDurability(b));
									warps.add(w);
									omp.getWarps().add(w);

									p.sendMessage(SurvivalMessages.INV_CREATE_WARP.get(omp, warpName));
								}
								else{
									p.sendMessage(SurvivalMessages.INV_CHANGE_WARP_NAME.get(omp, warp.getName(), warpName));
									warp.setName(warpName);
									warp.updateItemStack(warp.getItemStack().getType(), warp.getItemStack().getDurability());
								}

								p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
								Warp.saveToConfig();
							}
							else{
								p.sendMessage(SurvivalMessages.INV_ONLY_CHARACTERS.get(omp));
							}
						}
						else{
							e.setWillClose(false);
							e.setWillDestroy(false);

							p.sendMessage(SurvivalMessages.INV_MAX_CHARACTERS.get(omp));
						}
					}
					else{
						e.setWillClose(false);
						e.setWillDestroy(false);

						p.sendMessage(SurvivalMessages.INV_WARP_EXISTS.get(omp, warpName));
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
        meta.setDisplayName("Insert Warp name");
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
