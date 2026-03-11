package fadidev.orbitmines.skyblock.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMTShopInv;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockMessages;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
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
public class SkyBlockOMTShopInv extends OMTShopInv {

    private OrbitMinesSkyBlock skyBlock;

    public SkyBlockOMTShopInv(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @Override
    public void onClick(final OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        Player p = omp.getPlayer();

        if(item.getType() == Material.COBBLESTONE && item.getItemMeta().getDisplayName().equals("§7§l+32 Cobblestone")){
            if(p.getInventory().firstEmpty() != -2){
                if(omp.hasOrbitMinesTokens(2)){
                    new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 2, new ConfirmInv.Action() {
                        @Override
                        public void confirmed(OMPlayer omp) {
                            omp.removeOrbitMinesTokens(1);
                            omp.getPlayer().getInventory().addItem(new ItemStack(Material.COBBLESTONE, 32));
                            new SkyBlockOMTShopInv().open(omp.getPlayer());
                        }

                        @Override
                        public void cancelled(OMPlayer omp) {
                            new SkyBlockOMTShopInv().open(omp.getPlayer());
                        }
                    }).open(p);
                }
                else{
                    omp.requiredOrbitMinesTokens(2);
                }
            }
            else{
                p.sendMessage(SkyBlockMessages.INVENTORY_FULL.get(omp));
            }
        }
        else if(item.getType() == Material.SAPLING && item.getItemMeta().getDisplayName().equals("§2§l+1 Oak Sapling")){
            if(p.getInventory().firstEmpty() != -1){
                if(omp.hasOrbitMinesTokens(5)){
                    new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 5, new ConfirmInv.Action() {
                        @Override
                        public void confirmed(OMPlayer omp) {
                            omp.removeOrbitMinesTokens(5);
                            omp.getPlayer().getInventory().addItem(new ItemStack(Material.SAPLING, 1));
                            new SkyBlockOMTShopInv().open(omp.getPlayer());
                        }

                        @Override
                        public void cancelled(OMPlayer omp) {
                            new SkyBlockOMTShopInv().open(omp.getPlayer());
                        }
                    }).open(p);
                }
                else{
                    omp.requiredOrbitMinesTokens(5);
                }
            }
            else{
                p.sendMessage(SkyBlockMessages.INVENTORY_FULL.get(omp));
            }
        }
        else if(item.getType() == Material.LAVA_BUCKET && item.getItemMeta().getDisplayName().equals("§6§l+1 Lava Bucket")){
            if(p.getInventory().firstEmpty() != -1){
                if(omp.hasOrbitMinesTokens(20)){
                    new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 20, new ConfirmInv.Action() {
                        @Override
                        public void confirmed(OMPlayer omp) {
                            omp.removeOrbitMinesTokens(20);
                            omp.getPlayer().getInventory().addItem(new ItemStack(Material.LAVA_BUCKET, 1));
                            new SkyBlockOMTShopInv().open(omp.getPlayer());
                        }

                        @Override
                        public void cancelled(OMPlayer omp) {
                            new SkyBlockOMTShopInv().open(omp.getPlayer());
                        }
                    }).open(p);
                }
                else{
                    omp.requiredOrbitMinesTokens(20);
                }
            }
            else{
                p.sendMessage(SkyBlockMessages.INVENTORY_FULL.get(omp));
            }
        }
        else if(item.getType() == Material.MONSTER_EGG && item.getItemMeta().getDisplayName().equals("§e§l+1 Horse Egg")){
            if(p.getInventory().firstEmpty() != -1){
                if(omp.hasOrbitMinesTokens(100)){
                    new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 100, new ConfirmInv.Action() {
                        @Override
                        public void confirmed(OMPlayer omp) {
                            omp.removeOrbitMinesTokens(100);
                            omp.getPlayer().getInventory().addItem(skyBlock.getApi().getNms().customItem().setEggId(new ItemStack(Material.MONSTER_EGG, 1), Mob.HORSE));
                            new SkyBlockOMTShopInv().open(omp.getPlayer());
                        }

                        @Override
                        public void cancelled(OMPlayer omp) {
                            new SkyBlockOMTShopInv().open(omp.getPlayer());
                        }
                    }).open(p);
                }
                else{
                    omp.requiredOrbitMinesTokens(100);
                }
            }
            else{
                p.sendMessage(SkyBlockMessages.INVENTORY_FULL.get(omp));
            }
        }
    }

    protected ItemStack[] getContents(Player player){
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        {
            ItemStack item = new ItemStack(Material.COBBLESTONE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7§l+32 Cobblestone");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e2 OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[10] = item;
        }

        {
            ItemStack item = new ItemStack(Material.SAPLING, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§2§l+1 Oak Sapling");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e5 OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[12] = item;
        }

        {
            ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6§l+1 Lava Bucket");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e20 OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[14] = item;
        }

        {
            ItemStack item = new ItemStack(Material.MONSTER_EGG, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§e§l+1 Horse Egg");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("§c" + Messages.WORD_PRICE.get(omp) + ": §e100 OrbitMines Tokens");
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[16] = skyBlock.getApi().getNms().customItem().setEggId(item, Mob.HORSE);
        }

        return contents;
    }
}
