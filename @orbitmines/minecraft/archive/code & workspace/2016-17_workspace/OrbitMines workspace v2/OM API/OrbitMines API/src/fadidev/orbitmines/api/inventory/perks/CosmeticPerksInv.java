package fadidev.orbitmines.api.inventory.perks;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.api.utils.enums.perks.CosmeticPerk;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CosmeticPerksInv extends OMInventory {
	
	private OrbitMinesServer server;
	
	public CosmeticPerksInv(){
		setInventory(Bukkit.createInventory(null, 36, "§0§lCosmetic Perks"));
		this.server = OrbitMinesAPI.getApi().getServerPlugin();
	}
	
	@Override
	public void open(Player player){
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());
		player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 5, 1);

		registerLast(player);
	}

	@Override
	public void onClick(OMPlayer omp, InventoryClickEvent e) {
		e.setCancelled(true);

		ItemStack item = e.getCurrentItem();

		if(ItemUtils.isNull(item))
		    return;

        for(CosmeticPerk perk : CosmeticPerk.values){
            if(perk.isEnabled() && item.getType() == perk.getMaterial()){
                perk.openInv(omp.getPlayer());
                break;
            }
        }
	}

	private ItemStack[] getContents(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
        OMPlayer omp = OMPlayer.getOMPlayer(player);

		{
			ItemStack item = new ItemStack(Material.MONSTER_EGG, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Pets" + (server.petsEnabled() ? "" : " §8| " + Utils.statusString(omp.getLanguage(), false)));
			item.setItemMeta(itemmeta);
			contents[10] = OrbitMinesAPI.getApi().getNms().customItem().setEggId(item, Mob.WOLF);
		}
		{
			ItemStack item = new ItemStack(Material.INK_SACK, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§4ChatColors" + (server.chatcolorsEnabled() ? "" : " §8| " + Utils.statusString(omp.getLanguage(), false)));
			item.setDurability((short) 1);
			item.setItemMeta(itemmeta);
			contents[12] = item;
		}
		{
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§2Disguises" + (server.disguisesEnabled() ? "" : " §8| " + Utils.statusString(omp.getLanguage(), false)));
			item.setDurability((short) 2);
			item.setItemMeta(itemmeta);
			contents[14] = item;
		}
		{
			ItemStack item = new ItemStack(Material.COMPASS, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§bGadgets" + (server.gadgetsEnabled() ? "" : " §8| " + Utils.statusString(omp.getLanguage(), false)));
			item.setItemMeta(itemmeta);
			contents[16] = item;
		}
		{
			ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
			itemmeta.setDisplayName("§1Wardrobe" + (server.wardrobeEnabled() ? "" : " §8| " + Utils.statusString(omp.getLanguage(), false)));
			itemmeta.setColor(Color.fromBGR(204, 100, 2));
			item.setItemMeta(itemmeta);
			contents[19] = OrbitMinesAPI.getApi().getNms().customItem().hideFlags(item, 2);
		}
		{
			ItemStack item = new ItemStack(Material.STRING, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§fTrails" + (server.trailsEnabled() ? "" : " §8| " + Utils.statusString(omp.getLanguage(), false)));
			item.setItemMeta(itemmeta);
			contents[21] = item;
		}
		{
			ItemStack item = new ItemStack(Material.PUMPKIN, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§6Hats" + (server.hatsEnabled() ? "" : " §8| " + Utils.statusString(omp.getLanguage(), false)));
			item.setItemMeta(itemmeta);
			contents[23] = item;
		}
		{
			ItemStack item = new ItemStack(Material.FIREWORK, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§cFireworks" + (server.fireworksEnabled() ? "" : " §8| " + Utils.statusString(omp.getLanguage(), false)));
			item.setItemMeta(itemmeta);
			contents[25] = item;
		}
		
		return contents;
	}
}
