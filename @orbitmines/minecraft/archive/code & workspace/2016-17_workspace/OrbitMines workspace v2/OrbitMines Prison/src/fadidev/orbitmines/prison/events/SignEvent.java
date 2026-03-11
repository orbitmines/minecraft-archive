package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.ShopSign;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.utils.PrisonUtils;
import fadidev.orbitmines.prison.utils.enums.ShopType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SignEvent implements Listener {

    private OrbitMinesPrison prison;

    public SignEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onSign(SignChangeEvent e){
        Player p = e.getPlayer();
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        if(!e.isCancelled()){
            String[] lines = e.getLines();

            if(lines[0] != null && (lines[0].equalsIgnoreCase("Shop:Buy") || lines[0].equalsIgnoreCase("Shop:Sell"))){
                if(PrisonUtils.getChestShop(e.getBlock()) != null){
                    boolean canCreate = true;

                    ShopType shoptype = ShopType.valueOf(lines[0].substring(5).toUpperCase());
                    int materialId = 1;
                    int durability = 0;
                    int amount = 1;
                    int price = 1;

                    if(lines[1] != null && !lines[1].equals("")){
                        String[] lineParts = lines[1].replace(" ", "").split("\\:");

                        boolean isNumeric = true;

                        for(int i = 0; i < lineParts[0].length(); i++){
                            if(!Character.isDigit(lineParts[0].charAt(i))){
                                isNumeric = false;
                            }
                        }

                        if(isNumeric){
                            materialId = Integer.parseInt(lineParts[0]);
                        }

                        if(lineParts.length == 2 && lineParts[1] != null){
                            boolean isNumeric2 = true;

                            for(int i = 0; i < lineParts[1].length(); i++){
                                if(!Character.isDigit(lineParts[1].charAt(i))){
                                    isNumeric2 = false;
                                }
                            }

                            if(isNumeric2){
                                durability = Integer.parseInt(lineParts[1]);
                            }
                        }
                        else{
                            canCreate = false;
                        }
                    }
                    else{
                        canCreate = false;
                    }

                    if(lines[2] != null && !lines[2].equals("")){
                        String[] lineParts = lines[2].replace(" ", "").replace("$", "").split("\\:");

                        boolean isNumeric = true;

                        for(int i = 0; i < lineParts[0].length(); i++){
                            if(!Character.isDigit(lineParts[0].charAt(i))){
                                isNumeric = false;
                            }
                        }

                        if(isNumeric){
                            amount = Integer.parseInt(lineParts[0]);

                            if(amount == 0)
                                canCreate = false;
                        }

                        if(lineParts.length == 2 && lineParts[1] != null){
                            boolean isNumeric2 = true;

                            for(int i = 0; i < lineParts[1].length(); i++){
                                if(!Character.isDigit(lineParts[1].charAt(i))){
                                    isNumeric2 = false;
                                }
                            }

                            if(isNumeric2){
                                price = Integer.parseInt(lineParts[1]);

                                if(price == 0){
                                    canCreate = false;
                                }
                            }
                        }
                        else{
                            canCreate = false;
                        }
                    }
                    else{
                        canCreate = false;
                    }

                    /* Test if valid material */
                    try{
                        if(canCreate)
                            Material.getMaterial(materialId);
                    }catch(IllegalArgumentException ex){
                        canCreate = false;
                    }

                    if(canCreate){
                        if(price <= 1000000){
                            final ShopSign sign = new ShopSign(p.getUniqueId(), e.getBlock().getLocation(), materialId, (short) durability, shoptype, amount, price);

                            prison.getShopSigns().add(sign);
                            p.sendMessage(PrisonMessages.SHOP_CREATE.get(omp));
                            omp.getShopSigns().add(sign);
                            ShopSign.saveToConfig();

                            new BukkitRunnable(){
                                public void run(){
                                    sign.update();
                                }
                            }.runTaskLater(prison, 1);
                        }
                        else{
                            e.getBlock().breakNaturally();
                            p.sendMessage(PrisonMessages.SHOP_HIGH_PRICE.get(omp));
                        }
                    }
                    else{
                        e.getBlock().breakNaturally();
                        p.sendMessage(PrisonMessages.SHOP_CREATE_ERROR.get(omp));
                    }
                }
                else{
                    e.getBlock().breakNaturally();
                    p.sendMessage(PrisonMessages.SHOP_CONNECT_TO_CHEST.get(omp));
                }
            }
        }
        
        if(omp.isLoaded() && omp.hasPerms(VIPRank.EMERALD_VIP)){
            e.setLine(0, ChatColor.translateAlternateColorCodes('&', e.getLine(0)));
            e.setLine(1, ChatColor.translateAlternateColorCodes('&', e.getLine(1)));
            e.setLine(2, ChatColor.translateAlternateColorCodes('&', e.getLine(2)));
            e.setLine(3, ChatColor.translateAlternateColorCodes('&', e.getLine(3)));
        }
    }
}
