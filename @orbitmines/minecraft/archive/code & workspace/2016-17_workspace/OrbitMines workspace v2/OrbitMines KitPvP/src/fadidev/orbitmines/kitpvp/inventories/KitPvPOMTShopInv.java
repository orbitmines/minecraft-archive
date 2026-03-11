package fadidev.orbitmines.kitpvp.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMTShopInv;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class KitPvPOMTShopInv extends OMTShopInv {

    @Override
    public void onClick(final OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item) || item.getType() != Material.GOLD_NUGGET)
            return;

        switch(item.getItemMeta().getDisplayName()){
            case "§6§l+200 Coins":
                confirm(omp, item, 1, 200);
                break;
            case "§6§l+1000 Coins":
                confirm(omp, item, 4, 1000);
                break;
            case "§6§l+2500 Coins":
                confirm(omp, item, 9, 2500);
                break;
            case "§6§l+5000 Coins":
                confirm(omp, item, 16, 5000);
                break;
        }
    }

    private void confirm(OMPlayer omp, ItemStack item, final int price, final int coins){
        if(omp.hasOrbitMinesTokens(price)){
            new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), price, new ConfirmInv.Action() {
                @Override
                public void confirmed(OMPlayer omp) {
                    omp.removeOrbitMinesTokens(price);
                    ((KitPvPPlayer) omp).addMoney(coins);
                    new KitPvPOMTShopInv().open(omp.getPlayer());
                }

                @Override
                public void cancelled(OMPlayer omp) {
                    new KitPvPOMTShopInv().open(omp.getPlayer());
                }
            }).open(omp.getPlayer());
        }
        else{
            omp.requiredOrbitMinesTokens(price);
        }
    }

    protected ItemStack[] getContents(Player player){
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        contents[10] = getItem(omp, 200, 1);
        contents[12] = getItem(omp, 1000, 4);
        contents[14] = getItem(omp, 2500, 9);
        contents[16] = getItem(omp, 5000, 16);

        return contents;
    }

    private ItemStack getItem(KitPvPPlayer omp, int coins, int price){
        ItemStack item = new ItemStack(Material.GOLD_NUGGET, coins / 100);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§l+" + coins + " Coins");
        List<String> lore = new ArrayList<>();
        lore.add("");
        if(price == 1)
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e" + price + " OrbitMines Token");
        else
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e" + price + " OrbitMines Tokens");

        lore.add("");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
