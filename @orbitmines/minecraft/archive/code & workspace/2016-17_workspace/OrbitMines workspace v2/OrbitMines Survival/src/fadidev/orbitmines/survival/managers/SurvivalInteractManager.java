package fadidev.orbitmines.survival.managers;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.managers.InteractManager;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.ShopSign;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.inventories.ChestShopViewerInv;
import fadidev.orbitmines.survival.utils.SurvivalUtils;
import fadidev.orbitmines.survival.utils.enums.ShopType;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SurvivalInteractManager extends InteractManager {

    /*
     * TODO
     * Not the best code: (Old Code)
     * Should edit this shortly.
     */

    private OrbitMinesSurvival survival;
    private SurvivalPlayer omp;

    public SurvivalInteractManager(PlayerInteractEvent e) {
        super(e);

        survival = OrbitMinesSurvival.getSurvival();
        omp = SurvivalPlayer.getSurvivalPlayer(e.getPlayer());
    }

    public void handleClaimHoe(){
        if(item != null && item.getType() == Material.STONE_HOE && p.getWorld().getName().equals(survival.getLobby().getName())){
            e.setCancelled(true);
            omp.updateInventory();
        }
    }

    public void handleSpawnInteract(){
        if(!omp.isOpMode() && p.getWorld().getName().equals(survival.getLobby().getName()) && e.getClickedBlock() != null){
            Material m = e.getClickedBlock().getType();

            if(item != null && (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET) || (m == Material.TRAP_DOOR || m == Material.CHEST || m == Material.TRAPPED_CHEST || m == Material.WORKBENCH || m == Material.FURNACE || m == Material.ENDER_CHEST || m == Material.POWERED_RAIL || m == Material.DETECTOR_RAIL || m == Material.ACTIVATOR_RAIL || m == Material.RAILS)){
                e.setCancelled(true);
                omp.updateInventory();
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
                                if(omp.hasMoney(sign.getPrice())){
                                    if(SurvivalUtils.getEmptySlots(p.getInventory()) >= SurvivalUtils.getSlotsRequired(sign.getAmount(), Material.getMaterial(sign.getMaterialID()))){
                                        sign.buyItems(omp);
                                    }
                                    else{
                                        p.sendMessage(SurvivalMessages.INVENTORY_FULL.get(omp));
                                    }
                                }
                                else{
                                    int needed = sign.getPrice() - omp.getMoney();
                                    p.sendMessage(Messages.REQUIRED_CURRENCY.get(omp, "§2§l", needed + "", needed == 1 ? "$" : "$"));
                                }
                            }
                            else{
                                p.sendMessage(SurvivalMessages.SOLD_OUT.get(omp));
                            }
                        }
                        else{
                            if(sign.canSell()){
                                if(sign.hasMoney()){
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
                                        p.sendMessage(SurvivalMessages.NOT_ENOUGH_ITEMS.get(omp));
                                    }
                                }
                                else{
                                    p.sendMessage(SurvivalMessages.SHOP_OWNER_NOT_ENOUGH.get(omp));
                                }
                            }
                            else{
                                p.sendMessage(SurvivalMessages.CHEST_FULL.get(omp));
                            }
                        }
                    }
                    else{}
                }
                else{
                    if(a == Action.RIGHT_CLICK_BLOCK)
                        p.sendMessage(SurvivalMessages.USE_OWN_SHOP.get(omp));
                }
            }
        }
    }
}
