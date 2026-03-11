package fadidev.orbitmines.survival.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMTShopInv;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class SurvivalOMTShopInv extends OMTShopInv {

    @Override
    public void onClick(final OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        Player p = omp.getPlayer();

        if(item.getType() == Material.STONE_HOE && item.getItemMeta().getDisplayName().equals("§8§l+100 Claimblocks")){
            if(omp.hasOrbitMinesTokens(1)){
                new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 1, new ConfirmInv.Action() {
                    @Override
                    public void confirmed(OMPlayer omPlayer) {
                        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
                        omp.removeOrbitMinesTokens(2);
                        omp.addClaimBlocks(100);
                        new SurvivalOMTShopInv().open(omp.getPlayer());
                    }

                    @Override
                    public void cancelled(OMPlayer omp) {
                        new SurvivalOMTShopInv().open(omp.getPlayer());
                    }
                }).open(p);
            }
            else{
                omp.requiredOrbitMinesTokens(1);
            }
        }
        else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§2§l+400$")){
            if(omp.hasOrbitMinesTokens(3)){
                new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 3, new ConfirmInv.Action() {
                    @Override
                    public void confirmed(OMPlayer omPlayer) {
                        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
                        omp.removeOrbitMinesTokens(3);
                        omp.addMoney(400);
                        new SurvivalOMTShopInv().open(omp.getPlayer());
                    }

                    @Override
                    public void cancelled(OMPlayer omp) {
                        new SurvivalOMTShopInv().open(omp.getPlayer());
                    }
                }).open(p);
            }
            else{
                omp.requiredOrbitMinesTokens(3);
            }
        }
        else if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§7§l" + SurvivalMessages.WORD_YOUR.get(omp) + " Skull")){
            if(p.getInventory().firstEmpty() != -1){
                if(omp.hasOrbitMinesTokens(50)){
                    new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 50, new ConfirmInv.Action() {
                        @Override
                        public void confirmed(OMPlayer omp) {
                            omp.removeOrbitMinesTokens(50);

                            omp.getPlayer().getInventory().addItem(ItemUtils.getSkull(omp.getPlayer().getName()));

                            new SurvivalOMTShopInv().open(omp.getPlayer());
                        }

                        @Override
                        public void cancelled(OMPlayer omp) {
                            new SurvivalOMTShopInv().open(omp.getPlayer());
                        }
                    }).open(p);
                }
                else{
                    omp.requiredOrbitMinesTokens(50);
                }
            }
            else{
                p.sendMessage(SurvivalMessages.INVENTORY_FULL.get(omp));
            }
        }
        else if(item.getType() == Material.CHEST && item.getItemMeta().getDisplayName().equals("§6§l+1 Chest Shop")){
            if(omp.hasOrbitMinesTokens(30)){
                new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 30, new ConfirmInv.Action() {
                    @Override
                    public void confirmed(OMPlayer omPlayer) {
                        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
                        omp.removeOrbitMinesTokens(30);
                        omp.setExtraShops(omp.getExtraShops() +1);
                        new SurvivalOMTShopInv().open(omp.getPlayer());
                    }

                    @Override
                    public void cancelled(OMPlayer omp) {
                        new SurvivalOMTShopInv().open(omp.getPlayer());
                    }
                }).open(p);
            }
            else{
                omp.requiredOrbitMinesTokens(30);
            }
        }
    }

    protected ItemStack[] getContents(Player player){
        SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        {
            ItemStack item = new ItemStack(Material.STONE_HOE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§8§l+100 Claimblocks");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e2 OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[10] = OrbitMinesSurvival.getSurvival().getApi().getNms().customItem().hideFlags(item, 2, 32);
        }
        {
            ItemStack item = new ItemStack(Material.EMPTY_MAP, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§2§l+400$");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e3 OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[12] = item;
        }
        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            item.setDurability((short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName("§7§l" + SurvivalMessages.WORD_YOUR.get(omp) + " Skull");
            meta.setOwner(omp.getPlayer().getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e50 OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[14] = item;
        }
        {
            ItemStack item = new ItemStack(Material.CHEST, 1);
            item.setDurability((short) 3);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6§l+1 Chest Shop");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e30 OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[16] = item;
        }

        return contents;
    }
}
