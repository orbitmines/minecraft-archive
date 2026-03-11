package fadidev.orbitmines.kitpvp.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.ActiveBooster;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.utils.enums.Booster;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class BoosterInv extends OMInventory {

    private OrbitMinesKitPvP kitPvP;

    public BoosterInv(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();

        setInventory(Bukkit.createInventory(null, 9, "§0§lBoosters"));
    }

    @Override
    public void open(Player player) {
        getInventory().setContents(getContents(player));
        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        Player p = omp.getPlayer();

        for(final Booster booster : Booster.values()){
            if(item.getType() == booster.getMaterial() && item.getItemMeta().getDisplayName().equals(booster.getName())){
                if(!booster.hasPerms(omp)){
                    p.closeInventory();
                    omp.requiredVIPRank(booster.getVIPRank());
                }
                else{
                    if(omp.hasVIPPoints(booster.getPrice())){
                        if(kitPvP.getBooster() == null){
                            new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), booster.getPrice(), new ConfirmInv.Action() {
                                @Override
                                public void confirmed(OMPlayer omp) {
                                    omp.removeVipPoints(booster.getPrice());

                                    Player p = omp.getPlayer();
                                    p.closeInventory();

                                    kitPvP.setBooster(new ActiveBooster(booster, p.getName(), 30, 0));
                                    KitPvPMessages.BOOSTER_ACTIVATE.broadcast(omp.getName(), booster.getMultiplier() + "");
                                }

                                @Override
                                public void cancelled(OMPlayer omp) {
                                    new BoosterInv().open(omp.getPlayer());
                                }
                            }).open(p);
                        }
                        else{
                            p.sendMessage(KitPvPMessages.BOOSTER_ALREADY_ACTIVE.get(omp, kitPvP.getBooster().getPlayer()));
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
                        }
                    }
                    else{
                        omp.requiredVIPPoints(booster.getPrice());
                    }
                }
                break;
            }
        }
    }

    private ItemStack[] getContents(Player player){
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];


        contents[1] = getItem(omp, Booster.IRON_BOOSTER);
        contents[3] = getItem(omp, Booster.GOLD_BOOSTER);
        contents[5] = getItem(omp, Booster.DIAMOND_BOOSTER);
        contents[7] = getItem(omp, Booster.EMERALD_BOOSTER);

        return contents;
    }

    private ItemStack getItem(OMPlayer omp, Booster booster){
        ItemStack item = new ItemStack(booster.getMaterial(), 1);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(booster.getName());
        List<String> itemLore = new ArrayList<>();
        itemLore.add("");
        itemLore.add("§7Multiplier: §ax" + booster.getMultiplier());
        itemLore.add(KitPvPMessages.BOOSTER_DURATION.get(omp));
        itemLore.add("");
        itemLore.add(booster.getPriceName(omp));
        itemLore.add("");
        itemmeta.setLore(itemLore);
        item.setItemMeta(itemmeta);

        return item;
    }
}
