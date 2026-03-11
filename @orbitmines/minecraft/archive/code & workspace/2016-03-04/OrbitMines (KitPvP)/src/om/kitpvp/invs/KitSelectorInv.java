package om.kitpvp.invs;

import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.utils.enums.KitPvPKit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitSelectorInv extends InventoryInstance {
	
	private KitPvP kitpvp;
	
	public KitSelectorInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lKit Selector");
		this.inventory = inventory;
		this.kitpvp = KitPvP.getInstance();
	}
	
	@Override
	public void open(Player player){
		player.sendMessage("§7Opening the §bKit Selector§7...");
		inventory.setContents(getContects(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContects(Player player){
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		contents[9] = getItem(omp, KitPvPKit.KNIGHT);
		contents[10] = getItem(omp, KitPvPKit.ARCHER);
		contents[11] = getItem(omp, KitPvPKit.SOLDIER);
		contents[12] = getItem(omp, KitPvPKit.WIZARD);
		contents[13] = getItem(omp, KitPvPKit.TANK);
		contents[14] = getItem(omp, KitPvPKit.DRUNK);
		contents[15] = getItem(omp, KitPvPKit.PYRO);
		contents[16] = getItem(omp, KitPvPKit.BUNNY);
		contents[17] = getItem(omp, KitPvPKit.NECROMANCER);
		contents[18] = getItem(omp, KitPvPKit.KING);
		contents[19] = getItem(omp, KitPvPKit.TREE);
		contents[20] = getItem(omp, KitPvPKit.BLAZE);
		contents[21] = getItem(omp, KitPvPKit.TNT);
		contents[22] = getItem(omp, KitPvPKit.FISHERMAN);
		contents[23] = getItem(omp, KitPvPKit.SNOWGOLEM);
		contents[24] = getItem(omp, KitPvPKit.LIBRARIAN);
		contents[25] = getItem(omp, KitPvPKit.SPIDER);
		contents[26] = getItem(omp, KitPvPKit.VILLAGER);
		contents[27] = getItem(omp, KitPvPKit.ASSASSIN);
		contents[28] = getItem(omp, KitPvPKit.LORD);
		contents[29] = getItem(omp, KitPvPKit.VAMPIRE);
		contents[30] = getItem(omp, KitPvPKit.DARKMAGE);
		contents[31] = getItem(omp, KitPvPKit.BEAST);
		contents[32] = getItem(omp, KitPvPKit.FISH);
		contents[33] = getItem(omp, KitPvPKit.HEAVY);
		contents[34] = getItem(omp, KitPvPKit.GRIMREAPER);
		contents[35] = getItem(omp, KitPvPKit.MINER);
		contents[36] = getItem(omp, KitPvPKit.FARMER);
		contents[37] = getItem(omp, KitPvPKit.UNDEATH_KING);
		contents[38] = getItem(omp, KitPvPKit.ENGINEER);
		
		{
			ItemStack item = ItemUtils.setDisplayname(new ItemStack(Material.STAINED_GLASS_PANE), "§oComing Soon...");
			contents[39] = item;
			contents[40] = item;
			contents[41] = item;
			contents[42] = item;
			contents[43] = item;
			contents[44] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(KitPvPPlayer omp, KitPvPKit kit){
		int kitlevel = omp.getUnlockedLevel(kit);
		if(kit.getVIPRank() != null){
			if(omp.hasPerms(kit.getVIPRank())){
				kitlevel = 1;
			}
		}
		
		ItemStack item = new ItemStack(kit.getMaterial(), kitlevel);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(kit.getKitName(kitlevel));
		itemmeta.setLore(kit.getKitLore(kitlevel));
		item.setItemMeta(itemmeta);
		item.setDurability(kit.getDurability());
		if(kitpvp.isFreeKitEnabled() || kitlevel != 0){
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}
		
		return ItemUtils.hideFlags(item, 3);
	}
}

