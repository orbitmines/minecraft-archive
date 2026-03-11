package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.Mine;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.handlers.ShopSign;
import fadidev.orbitmines.prison.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 19-9-2016.
 */
public class BreakEvent implements Listener {

    private OrbitMinesPrison prison;

    public BreakEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);
        ItemStack i = p.getItemInHand();

        if(omp.isLoaded()){
            if(omp.isOpMode())
                return;

            if(p.getWorld().getName().equals(prison.getPrisonWorld().getName())){
                e.setCancelled(true);

                Mine mine = omp.inMine(e.getBlock().getLocation(), true);
                if(mine != null){
                    switch(mine.getType()){
                        case NORMAL:
                            PrisonUtils.reduceDurability(omp, i);
                            p.setTotalExperience(p.getTotalExperience() + e.getExpToDrop());

                            if(e.getBlock().getType() == Material.IRON_ORE){
                                p.getInventory().addItem(PrisonUtils.applyFortune(i, new ItemStack(Material.IRON_INGOT)));
                            }
                            else if(e.getBlock().getType() == Material.GOLD_ORE){
                                p.getInventory().addItem(PrisonUtils.applyFortune(i, new ItemStack(Material.GOLD_INGOT)));
                            }
                            else{
                                for(ItemStack item : e.getBlock().getDrops()){
                                    p.getInventory().addItem(PrisonUtils.applyFortune(i, item));
                                }
                            }

                            e.getBlock().setType(Material.AIR);
                            break;
                        case WOOD:
                            if(e.getBlock().getType() == Material.LOG || e.getBlock().getType() == Material.LOG_2){
                                p.getInventory().addItem(ItemUtils.itemstack(mine.getWoodType().getMaterial(), 1, mine.getWoodType().getDurability()));

                                mine.newLumberJack(e.getBlock());

                                e.getBlock().setType(Material.STAINED_CLAY);
                                e.getBlock().setData((byte) 15);
                            }
                            break;
                    }
                }
            }
            else if(p.getWorld().getName().equals(prison.getCellWorld().getName())){
                e.setCancelled(!omp.isOnCell(e.getBlock().getLocation()));
            }
            else if(p.getWorld().getName().equals(prison.getLobby().getName())){
                if(omp.getShop() == null || !omp.getShop().isInShop(e.getBlock().getLocation()))
                    e.setCancelled(true);
            }

            if(!e.isCancelled()){
                ShopSign sign = ShopSign.getShopSign(e.getBlock().getLocation());

                if(sign != null && omp.getShopSigns().contains(sign)){
                    sign.delete();
                    p.sendMessage(PrisonMessages.REMOVE_CHESTSHOP.get(omp));
                }
            }
        }
        else{
            omp.notLoaded();
            e.setCancelled(true);
        }
    }
}
