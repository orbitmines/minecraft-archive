package fadidev.orbitmines.creative.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMTShopInv;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class CreativeOMTShopInv extends OMTShopInv {

    private OrbitMinesCreative creative;

    public CreativeOMTShopInv(){
        creative = OrbitMinesCreative.getCreative();
    }

    @Override
    public void onClick(final OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item) || item.getType() != Material.WOOD_AXE || !item.getItemMeta().getDisplayName().startsWith("§7WorldEdit Command:") || item.getItemMeta().getLore().contains("§a§lUnlocked"))
            return;

        switch(item.getItemMeta().getDisplayName().substring(25)){
            case "//set":
                confirm(omp, item, 75, "//set");
                break;
            case "//walls":
                confirm(omp, item, 75, "//walls");
                break;
            case "//line":
                confirm(omp, item, 50, "//line");
                break;
            case "//replace":
                confirm(omp, item, 175, "//replace");
                break;
        }
    }

    private void confirm(OMPlayer omp, ItemStack item, final int price, final String command){
        if(omp.hasOrbitMinesTokens(price)){
            new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), price, new ConfirmInv.Action() {
                @Override
                public void confirmed(OMPlayer omp) {
                    omp.removeOrbitMinesTokens(price);
                    ((CreativePlayer) omp).addWECommand(command);
                    new CreativeOMTShopInv().open(omp.getPlayer());
                }

                @Override
                public void cancelled(OMPlayer omp) {
                    new CreativeOMTShopInv().open(omp.getPlayer());
                }
            }).open(omp.getPlayer());
        }
        else{
            omp.requiredOrbitMinesTokens(price);
        }
    }

    protected ItemStack[] getContents(Player player){
        CreativePlayer omp = CreativePlayer.getCreativePlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        contents[10] = getItem(omp, "//set", 75, 1);
        contents[12] = getItem(omp, "//walls", 75, 2);
        contents[14] = getItem(omp, "//line", 50, 3);
        contents[16] = getItem(omp, "//replace", 175, 4);

        return contents;
    }

    private ItemStack getItem(CreativePlayer omp, String cmd, int price, int index){
        ItemStack item = new ItemStack(Material.WOOD_AXE, index);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§7WorldEdit Command: §d§l" + cmd);
        List<String> lore = new ArrayList<>();
        if(!omp.hasWEAccess(cmd)){
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) +": §e" + price + " OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        else{
            lore.add("");
            lore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }

        return creative.getApi().getNms().customItem().hideFlags(item, 3);
    }
}
