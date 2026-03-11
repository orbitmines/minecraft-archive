package fadidev.orbitmines.kitpvp.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.handlers.Masteries;
import fadidev.orbitmines.kitpvp.utils.enums.Mastery;
import org.bukkit.Bukkit;
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
public class MasteryInv extends OMInventory {

    private OrbitMinesKitPvP kitPvP;

    public MasteryInv(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();

        setInventory(Bukkit.createInventory(null, 54, "§0§lMasteries"));
    }

    @Override
    public void open(Player player) {
        getInventory().setContents(getContents(player));
        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        KitPvPPlayer omp = (KitPvPPlayer) omPlayer;
        Player p = omp.getPlayer();
        Masteries m = omp.getMasteries();

        if(item.getType() == Material.EMERALD){
            switch(item.getItemMeta().getDisplayName()){
                case "§a§l+2% Melee Damage":
                    if(m.getPoints() > 0){
                        m.setPoints(m.getPoints() -1);
                        m.setMelee(m.getMelee() +1);
                        m.update();
                        new MasteryInv().open(p);
                        p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
                        p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
                    }
                    break;
                case "§c§l-2% Melee Damage Taken":
                    if(m.getPoints() > 0){
                        m.setPoints(m.getPoints() -1);
                        m.setMeleeProtection(m.getMeleeProtection() +1);
                        m.update();
                        new MasteryInv().open(p);
                        p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
                        p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
                    }
                    break;
                case "§a§l+4% Range Damage":
                    if(m.getPoints() > 0){
                        m.setPoints(m.getPoints() -1);
                        m.setRange(m.getRange() +1);
                        m.update();
                        new MasteryInv().open(p);
                        p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
                        p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
                    }
                    break;
                case "§c§l-3% Range Damage Taken":
                    if(m.getPoints() > 0){
                        m.setPoints(m.getPoints() -1);
                        m.setRangeProtection(m.getRangeProtection() +1);
                        m.update();
                        new MasteryInv().open(p);
                        p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
                        p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
                    }
                    break;
            }
        }
        else if(item.getType() == Material.BLAZE_POWDER){
            switch(item.getItemMeta().getDisplayName()){
                case "§c§l-2% Melee Damage":
                    if(m.getMasteryLevel(Mastery.MELEE) > 0){
                        new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 20, new ConfirmInv.Action() {
                            @Override
                            public void confirmed(OMPlayer omPlayer) {
                                KitPvPPlayer omp = (KitPvPPlayer) omPlayer;
                                omp.removeVipPoints(20);
                                Masteries m = omp.getMasteries();
                                m.setMelee(m.getMelee() -1);
                                m.addPoints(1);
                                new MasteryInv().open(omp.getPlayer());
                            }

                            @Override
                            public void cancelled(OMPlayer omp) {
                                new MasteryInv().open(omp.getPlayer());
                            }
                        }).open(p);
                    }
                    break;
                case "§a§l+2% Melee Damage Taken":
                    if(m.getMasteryLevel(Mastery.MELEE_PROTECTION) > 0){
                        new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 20, new ConfirmInv.Action() {
                            @Override
                            public void confirmed(OMPlayer omPlayer) {
                                KitPvPPlayer omp = (KitPvPPlayer) omPlayer;
                                omp.removeVipPoints(20);
                                Masteries m = omp.getMasteries();
                                m.setMeleeProtection(m.getMeleeProtection() -1);
                                m.addPoints(1);
                                new MasteryInv().open(omp.getPlayer());
                            }

                            @Override
                            public void cancelled(OMPlayer omp) {
                                new MasteryInv().open(omp.getPlayer());
                            }
                        }).open(p);
                    }
                    break;
                case "§c§l-4% Range Damage":
                    if(m.getMasteryLevel(Mastery.RANGE) > 0){
                        new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 20, new ConfirmInv.Action() {
                            @Override
                            public void confirmed(OMPlayer omPlayer) {
                                KitPvPPlayer omp = (KitPvPPlayer) omPlayer;
                                omp.removeVipPoints(20);
                                Masteries m = omp.getMasteries();
                                m.setRange(m.getRange() -1);
                                m.addPoints(1);
                                new MasteryInv().open(omp.getPlayer());
                            }

                            @Override
                            public void cancelled(OMPlayer omp) {
                                new MasteryInv().open(omp.getPlayer());
                            }
                        }).open(p);
                    }
                    break;
                case "§a§l+3% Range Damage Taken":
                    if(m.getMasteryLevel(Mastery.RANGE_PROTECTION) > 0){
                        new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 20, new ConfirmInv.Action() {
                            @Override
                            public void confirmed(OMPlayer omPlayer) {
                                KitPvPPlayer omp = (KitPvPPlayer) omPlayer; 
                                omp.removeVipPoints(20);
                                Masteries m = omp.getMasteries();
                                m.setRangeProtection(m.getRangeProtection() -1);
                                m.addPoints(1);
                                new MasteryInv().open(omp.getPlayer());
                            }

                            @Override
                            public void cancelled(OMPlayer omp) {
                                new MasteryInv().open(omp.getPlayer());
                            }
                        }).open(p);
                    }
                    break;
            }
        }
    }

    private ItemStack[] getContents(Player player){
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        contents[1] = getUpgradeItem(omp, Mastery.MELEE);
        contents[3] = getUpgradeItem(omp, Mastery.MELEE_PROTECTION);
        contents[5] = getUpgradeItem(omp, Mastery.RANGE);
        contents[7] = getUpgradeItem(omp, Mastery.RANGE_PROTECTION);

        contents[19] = getItem(omp, Mastery.MELEE);
        contents[21] = getItem(omp, Mastery.MELEE_PROTECTION);
        contents[23] = getItem(omp, Mastery.RANGE);
        contents[25] = getItem(omp, Mastery.RANGE_PROTECTION);

        contents[37] = getDowngradeItem(omp, Mastery.MELEE);
        contents[39] = getDowngradeItem(omp, Mastery.MELEE_PROTECTION);
        contents[41] = getDowngradeItem(omp, Mastery.RANGE);
        contents[43] = getDowngradeItem(omp, Mastery.RANGE_PROTECTION);

        {
            int points = omp.getMasteries().getPoints();
            ItemStack item = new ItemStack(Material.EXP_BOTTLE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Mastery Points: §c" + points);
            item.setItemMeta(meta);
            item.setAmount(points > 64 ? 64 : points);
            contents[49] = item;
        }

        return contents;
    }

    private ItemStack getItem(KitPvPPlayer omp, Mastery mastery){
        Masteries masteries = omp.getMasteries();

        ItemStack item = new ItemStack(mastery.getMaterial(), masteries.getMasteryLevel(mastery));
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(mastery.getName());
        List<String> itemLore = new ArrayList<>();
        itemLore.add(" §7Level: §c" + masteries.getMasteryLevel(mastery));
        itemLore.add(" §7Effect: §c" + (int) ((masteries.getMasteryEffect(mastery) +1) * 100) + "% " + mastery.getEffectName());
        itemmeta.setLore(itemLore);
        item.setItemMeta(itemmeta);
        item = kitPvP.getApi().getNms().customItem().hideFlags(item, 2);

        return item;
    }

    private ItemStack getUpgradeItem(KitPvPPlayer omp, Mastery mastery){
        Masteries masteries = omp.getMasteries();

        if(masteries.getPoints() > 0){
            int nextLevel = masteries.getMasteryLevel(mastery) +1;
            if(nextLevel > 64)
                nextLevel = 64;

            ItemStack item = new ItemStack(Material.EMERALD, nextLevel);
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.setDisplayName(mastery.getColor() + (int) (mastery.getMultiplier() * 100) + "% " + mastery.getEffectName());
            List<String> itemLore = new ArrayList<>();
            itemLore.add(" §7" + Messages.WORD_PRICE.get(omp) + ": §c§l1 Mastery Point");
            itemmeta.setLore(itemLore);
            item.setItemMeta(itemmeta);

            return item;
        }
        return null;
    }

    private ItemStack getDowngradeItem(KitPvPPlayer omp, Mastery mastery){
        Masteries masteries = omp.getMasteries();

        if(masteries.getMasteryLevel(mastery) > 0){
            int nextLevel = masteries.getMasteryLevel(mastery) -1;
            if(nextLevel > 64)
                nextLevel = 64;

            ItemStack item = new ItemStack(Material.BLAZE_POWDER, nextLevel);
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.setDisplayName(mastery.getOppositeColor() + (int) (mastery.getMultiplier() * 100) + "% " + mastery.getEffectName());
            List<String> itemLore = new ArrayList<>();
            itemLore.add(" §7Price: §b§l20 VIP Points");
            itemLore.add(" §7(+ 1 Mastery Point)");
            itemmeta.setLore(itemLore);
            item.setItemMeta(itemmeta);

            return item;
        }
        return null;
    }
}
