package fadidev.orbitmines.hub.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.HubMessages;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.handlers.players.minigames.*;
import fadidev.orbitmines.hub.utils.enums.InvType;
import fadidev.orbitmines.hub.utils.enums.MiniGameType;
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
public class StatsInv extends OMInventory {

    public StatsInv(){
        setInventory(Bukkit.createInventory(null, 27, "§0§lStats"));
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

        for(MiniGameType type : MiniGameType.values()){
            if(item.getType() == type.getMaterial() && item.getItemMeta().getDisplayName().equals("§f§l" + type.getName()))
                new MiniGameTicketInv(type, InvType.KITS).open(omp.getPlayer());
        }
    }

    private ItemStack[] getContents(Player player){
        HubPlayer omp = HubPlayer.getHubPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        SurvivalGamesPlayer sgp = omp.getSurvivalGamesPlayer();
        SkywarsPlayer swp = omp.getSkywarsPlayer();
        GhostAttackPlayer gap = omp.getGhostAttackPlayer();
        SplatcraftPlayer scp = omp.getSplatcraftPlayer();
        UHCPlayer uhcp = omp.getUhcPlayer();
        ChickenFightPlayer cfp = omp.getChickenFightPlayer();
        SpleefPlayer spp = omp.getSpleefPlayer();

        String played = HubMessages.WORD_PLAYED.get(omp);
        String wins = HubMessages.WORD_WINS.get(omp);
        String best = HubMessages.WORD_BEST.get(omp);
        String details = HubMessages.INV_CLICK_FOR_DETAILS.get(omp);
        {
            ItemStack item = new ItemStack(Material.COMPASS, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Games " + played + ": §f" + (sgp.getWins() + sgp.getLoses() + swp.getWins() + swp.getLoses() + gap.getWins() + gap.getLoses() + gap.getGhostWins() + scp.getWins() + scp.getLoses() + uhcp.getWins() + uhcp.getLoses() + cfp.getWins() + cfp.getLoses() + spp.getWins()+ spp.getLoses()));
            item.setItemMeta(meta);
            contents[4] = item;
        }
        {
            ItemStack item = new ItemStack(MiniGameType.SURVIVAL_GAMES.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l" + MiniGameType.SURVIVAL_GAMES.getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(" §7Games " + played + ": §f" + (sgp.getWins() + sgp.getLoses()) + " ");
            lore.add(" §7" + wins + ": §f" + sgp.getWins() + " ");
            lore.add(" §7Kills: §f" + sgp.getKills() + " ");
            lore.add(" §7" + best + " Streak: §f" + sgp.getBestStreak() + " ");
            lore.add("");
            lore.add(" " + details + " ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability(MiniGameType.SURVIVAL_GAMES.getDurability());
            contents[10] = OrbitMinesHub.getHub().getApi().getNms().customItem().hideFlags(item, 2);
        }
        {
            ItemStack item = new ItemStack(MiniGameType.SKYWARS.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l" + MiniGameType.SKYWARS.getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(" §7Games " + played + ": §f" + (swp.getWins() + swp.getLoses()) + " ");
            lore.add(" §7" + wins + ": §f" + swp.getWins() + " ");
            lore.add(" §7Kills: §f" + swp.getKills() + " ");
            lore.add(" §7" + best + " Streak: §f" + swp.getBestStreak() + " ");
            lore.add("");
            lore.add(" " + details + " ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability(MiniGameType.SKYWARS.getDurability());
            contents[12] = item;
        }
        {
            ItemStack item = new ItemStack(MiniGameType.GHOST_ATTACK.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l" + MiniGameType.GHOST_ATTACK.getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(" §7Games " + played + ": §f" + (gap.getWins() + gap.getLoses() + gap.getGhostWins()) + " ");
            lore.add(" §7" + wins + " (Player): §f" + gap.getWins() + " ");
            lore.add(" §7Kills (Player): §f" + gap.getKills() + " ");
            lore.add(" §7" + wins + " (Ghost): §f" + gap.getGhostWins() + " ");
            lore.add(" §7Kills (Ghost): §f" + gap.getGhostKills() + " ");
            lore.add(" §7" + best + " Streak: §f" + gap.getBestStreak() + " ");
            lore.add("");
            lore.add(" " + details + " ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability(MiniGameType.GHOST_ATTACK.getDurability());
            contents[14] = item;
        }
        {
            ItemStack item = new ItemStack(MiniGameType.SPLATCRAFT.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l" + MiniGameType.SPLATCRAFT.getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(" §7Games " + played + ": §f" + (scp.getWins() + scp.getLoses()) + " ");
            lore.add(" §7" + wins + ": §f" + scp.getWins() + " ");
            lore.add(" §7Kills: §f" + scp.getKills() + " ");
            lore.add(" §7" + best + " Streak: §f" + scp.getBestStreak() + " ");
            lore.add("");
            lore.add(" " + details + " ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability(MiniGameType.SPLATCRAFT.getDurability());
            contents[16] = item;
        }
        {
            ItemStack item = new ItemStack(MiniGameType.ULTRA_HARD_CORE.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l" + MiniGameType.ULTRA_HARD_CORE.getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(" §7Games " + played + ": §f" + (uhcp.getWins() + uhcp.getLoses()) + " ");
            lore.add(" §7" + wins + ": §f" + uhcp.getWins() + " ");
            lore.add(" §7Kills: §f" + uhcp.getKills() + " ");
            lore.add(" §7" + best + " Streak: §f" + uhcp.getBestStreak());
            lore.add("");
            lore.add(" " + details + " ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability(MiniGameType.ULTRA_HARD_CORE.getDurability());
            contents[20] = item;
        }
        {
            ItemStack item = new ItemStack(MiniGameType.CHICKEN_FIGHT.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l" + MiniGameType.CHICKEN_FIGHT.getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(" §7Games " + played + ": §f" + (cfp.getWins() + cfp.getLoses()) + " ");
            lore.add(" §7" + wins + ": §f" + cfp.getWins() + " ");
            lore.add(" §7Kills: §f" + cfp.getKills() + " ");
            lore.add(" §7" + best + " Streak: §f" + cfp.getBestStreak() + " ");
            lore.add("");
            lore.add(" " + details + " ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability(MiniGameType.CHICKEN_FIGHT.getDurability());
            contents[22] = item;
        }
        {
            ItemStack item = new ItemStack(MiniGameType.SPLEEF.getMaterial(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l" + MiniGameType.SPLEEF.getName());
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(" §7Games " + played + ": §f" + (spp.getWins() + spp.getLoses()) + " ");
            lore.add(" §7" + wins + ": §f" + spp.getWins() + " ");
            lore.add(" §7Kills: §f" + spp.getKills() + " ");
            lore.add(" §7" + best + " Streak: §f" + spp.getBestStreak() + " ");
            lore.add("");
            lore.add(" " + details + " ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability(MiniGameType.SPLEEF.getDurability());
            contents[24] = OrbitMinesHub.getHub().getApi().getNms().customItem().hideFlags(item, 2);
        }

        return contents;
    }
}
