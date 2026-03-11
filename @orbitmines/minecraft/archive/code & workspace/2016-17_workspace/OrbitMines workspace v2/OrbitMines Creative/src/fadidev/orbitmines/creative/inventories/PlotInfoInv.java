package fadidev.orbitmines.creative.inventories;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import fadidev.orbitmines.creative.utils.enums.PlotType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
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
public class PlotInfoInv extends OMInventory {

    private OrbitMinesCreative creative;

    public PlotInfoInv(){
        creative = OrbitMinesCreative.getCreative();

        setInventory(Bukkit.createInventory(null, 9, "§0§lPlot Info"));
    }

    @Override
    public void open(Player player) {
        CreativePlayer omp = CreativePlayer.getCreativePlayer(player);

        if(omp.hasPlot()) {
            getInventory().setContents(getContents(player));
            player.openInventory(getInventory());

            registerLast(player);
        }
        else{
            player.sendMessage(CreativeMessages.CMD_PLOT_NO_PLOT.get(omp));
        }
    }

    @Override
    public void onClick(OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        CreativePlayer omp = (CreativePlayer) omPlayer;
        Player p = omp.getPlayer();

        if((item.getType() == PlotType.NORMAL.getMaterial() || item.getType() == PlotType.PVP.getMaterial()) && item.getItemMeta().getDisplayName().startsWith("§7§lPlot Mode")){
            if(omp.hasPerms(VIPRank.EMERALD_VIP)){
                Plot plot = omp.getPlot();

                if(omp.hasPlot()){
                    if(item.getItemMeta().getDisplayName().endsWith("Build Mode")){
                        if(plot.isSetupFinished()){
                            plot.generatePvPBorder(true);
                            plot.setPlotType(PlotType.PVP);

                            p.sendMessage(CreativeMessages.INV_SET_PVP_MODE.get(omp));
                            p.sendMessage(CreativeMessages.INV_BROADCAST_INFO.get(omp));
                        }
                        else{
                            p.sendMessage(CreativeMessages.INV_SETUP_NOT_FINISHED.get(omp));
                        }
                    }
                    else{
                        plot.generatePvPBorder(false);
                        plot.setPlotType(PlotType.NORMAL);
                        p.sendMessage(CreativeMessages.INV_SET_BUILD_MODE.get(omp));

                        for(CreativePlayer cplayer : Plot.getPvPPlayers(plot)){
                            Player player = cplayer.getPlayer();
                            OMPlayer omplayer = OMPlayer.getOMPlayer(player);

                            omplayer.clearInventory();
                            omplayer.clearPotionEffects();
                            player.setGameMode(GameMode.CREATIVE);
                            cplayer.setPvPPlot(null);
                            cplayer.setKitSelected(null);

                            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

                            if(p != player){
                                player.sendMessage("");
                                player.sendMessage(CreativeMessages.INV_SET_BUILD_MODE_PLAYER.get(cplayer, p.getName()));
                            }
                        }
                    }

                    new PlotInfoInv().open(p);
                }
            }
            else{
                omp.requiredVIPRank(VIPRank.EMERALD_VIP);
            }
        }
        else if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().startsWith("§6§lPlot Members")){
            new PlotMembersInv().open(p);
        }
    }

    private ItemStack[] getContents(Player player){
        CreativePlayer omp = CreativePlayer.getCreativePlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        Plot plot = omp.getPlot();

        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            item.setDurability((short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName("§6§lPlot Members §6(§7" + (plot.getMemberUUIDs().size() +1) + "§6/§7" + omp.getMaxMembers() + "§6)");
            meta.setOwner(player.getName());
            item.setItemMeta(meta);

            contents[2] = item;
        }
        {
            ItemStack item = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§d§lPlot " + plot.getPlotId());
            List<String> lore = new ArrayList<>();
            lore.add("  §dOwner: §7" + player.getName());
            lore.add("  §d" + CreativeMessages.WORD_CREATED.get(omp) + ": §7" + plot.getCreatedDate());
            meta.setLore(lore);
            item.setItemMeta(meta);

            contents[4] = item;
        }
        {
            ItemStack item = new ItemStack(plot.getPlotType().getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7§lPlot Mode §8| " + plot.getPlotType().getName());
            if(!omp.hasPerms(VIPRank.EMERALD_VIP)){
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add("§7" + Messages.WORD_REQUIRED.get(omp) + ": §a§lEmerald VIP");
                lore.add("");
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
            contents[6] = creative.getApi().getNms().customItem().hideFlags(item, 2);
        }

        return contents;
    }
}
