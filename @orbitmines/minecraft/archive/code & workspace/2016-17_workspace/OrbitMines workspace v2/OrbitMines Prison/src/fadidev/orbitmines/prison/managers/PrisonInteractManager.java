package fadidev.orbitmines.prison.managers;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.managers.InteractManager;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.*;
import fadidev.orbitmines.prison.inventory.ChestShopViewerInv;
import fadidev.orbitmines.prison.utils.PrisonUtils;
import fadidev.orbitmines.prison.utils.enums.MineType;
import fadidev.orbitmines.prison.utils.enums.ShopType;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 10-9-2016.
 */
public class PrisonInteractManager extends InteractManager {

    /*
     * TODO
     * Not the best code: (Old Code)
     * Should edit this shortly.
     */

    private OrbitMinesPrison prison;
    private PrisonPlayer omp;

    public PrisonInteractManager(PlayerInteractEvent e) {
        super(e);

        prison = OrbitMinesPrison.getPrison();
        omp = PrisonPlayer.getPrisonPlayer(e.getPlayer());
    }

    public void handleMineSigns(){
        if((a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_BLOCK) && (b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST) && p.getWorld().getName().equals(prison.getPrisonWorld().getName())){
            for(Mine mine : prison.getMines()){
                if(mine.getType() == MineType.NORMAL && omp.hasPerms(mine.getRank())){
                    if(WorldUtils.equalsLoc(mine.getClockSign(), b.getLocation())){
                        if(!omp.onCooldown(Cooldowns.NPC_INTERACT)){
                            omp.setClockEnabled(!omp.isClockEnabled());

                            omp.resetCooldown(Cooldowns.NPC_INTERACT);
                        }
                    }
                }
            }
        }
    }

    public void handleShops(){
        if(omp.isOpMode() || b == null || item == null)
            return;

        if(item.getType() == Material.TNT || item.getType() == Material.EXPLOSIVE_MINECART){
            e.setCancelled(true);
            return;
        }

        if(p.getWorld().getName().equals(prison.getLobby().getName())){
            if(omp.getShop() == null || b.getType() == Material.TNT && item != null && item.getType() == Material.FLINT_AND_STEEL || item != null && (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET) || (b.getType() == Material.POWERED_RAIL || b.getType() == Material.DETECTOR_RAIL || b.getType() == Material.ACTIVATOR_RAIL || b.getType() == Material.RAILS) || (b.getType() == Material.CHEST || b.getType() == Material.ENDER_CHEST || b.getType() == Material.TRAPPED_CHEST || b.getType() == Material.WORKBENCH || b.getType() == Material.FURNACE || b.getType() == Material.ANVIL || b.getType() == Material.DROPPER || b.getType() == Material.HOPPER || b.getType() == Material.ENCHANTMENT_TABLE || b.getType() == Material.DISPENSER || b.getType() == Material.JUKEBOX || b.getType() == Material.BEACON) && !omp.getShop().isInShop(b.getLocation()))
                e.setCancelled(true);
        }
        else if(p.getWorld().getName().equals(prison.getCellWorld().getName())){
            if(!omp.isOnCell(p.getLocation())) {
                e.setCancelled(true);
                omp.updateInventory();
            }
        }
    }

    public void handlePhysical(){
        if(p.getWorld().getName().equals(prison.getPrisonWorld().getName()) && a == Action.PHYSICAL)
            e.setCancelled(true);
    }

    public void handleShopSigns(){
        if(b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)){
            Shop shop = Shop.getShop(b.getLocation());

            if(shop != null){
                if(omp.getShop() == null){
                    if(shop.canRent()){
                        if(!omp.onCooldown(Cooldowns.MESSAGE)){
                            if(omp.hasGold(15000)){
                                p.sendMessage(PrisonMessages.RENT_SHOP.get(omp));

                                shop.rent(omp);
                                omp.removeGold(15000);
                            }
                            else{
                                omp.requiredGold(15000);
                            }

                            omp.resetCooldown(Cooldowns.MESSAGE);
                        }
                    }
                }
                else{
                    if(shop.getShopId() == omp.getShop().getShopId()){
                        if(!omp.onCooldown(Cooldowns.MESSAGE)){
                            if(omp.hasGold(5000)){
                                p.sendMessage(PrisonMessages.SHOP_ADD.get(omp));

                                shop.addDays();
                                omp.removeGold(5000);
                            }
                            else{
                                omp.requiredGold(5000);
                            }

                            omp.resetCooldown(Cooldowns.MESSAGE);
                        }
                    }
                }
            }
        }
    }

    public void handleChestShops(){
        if(b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)){
            ShopSign sign = ShopSign.getShopSign(b.getLocation());

            if(sign != null){
                if(!omp.getShopSigns().contains(sign)){
                    if(a == Action.LEFT_CLICK_BLOCK){
                        Chest chest = sign.getChest();
                        new ChestShopViewerInv(chest).open(p);
                    }
                    else if(a == Action.RIGHT_CLICK_BLOCK){
                        if(sign.getShopType() == ShopType.BUY){
                            if(!sign.isSold()){
                                if(omp.hasGold(sign.getPrice())){
                                    if(PrisonUtils.getEmptySlots(p.getInventory()) >= PrisonUtils.getSlotsRequired(sign.getAmount(), Material.getMaterial(sign.getMaterialID()))){
                                        sign.buyItems(omp);
                                    }
                                    else{
                                        p.sendMessage(PrisonMessages.INVENTORY_FULL.get(omp));
                                    }
                                }
                                else{
                                    int needed = sign.getPrice() - omp.getGold();
                                    p.sendMessage(Messages.REQUIRED_CURRENCY.get(omp, "§6§l", needed + "", "Gold"));
                                }
                            }
                            else{
                                p.sendMessage(PrisonMessages.SOLD_OUT.get(omp));
                            }
                        }
                        else{
                            if(sign.canSell()){
                                if(sign.hasGold()){
                                    int amount = 0;
                                    for(ItemStack item : p.getInventory().getContents()){
                                        if(item != null && item.getType() == Material.getMaterial(sign.getMaterialID()) && item.getDurability() == sign.getDurability()){
                                            amount += item.getAmount();
                                        }
                                    }

                                    if(amount >= sign.getAmount()){
                                        sign.sellItems(omp);
                                    }
                                    else{
                                        p.sendMessage(PrisonMessages.NOT_ENOUGH_ITEMS.get(omp));
                                    }
                                }
                                else{
                                    p.sendMessage(PrisonMessages.SHOP_OWNER_NOT_ENOUGH.get(omp));
                                }
                            }
                            else{
                                p.sendMessage(PrisonMessages.CHEST_FULL.get(omp));
                            }
                        }
                    }
                    else{}
                }
                else{
                    if(a == Action.RIGHT_CLICK_BLOCK)
                        p.sendMessage(PrisonMessages.USE_OWN_SHOP.get(omp));
                }
            }
        }
    }
}
