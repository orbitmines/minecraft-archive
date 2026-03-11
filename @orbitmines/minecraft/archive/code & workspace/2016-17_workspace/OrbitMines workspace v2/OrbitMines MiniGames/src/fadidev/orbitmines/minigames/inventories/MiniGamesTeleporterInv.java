package fadidev.orbitmines.minigames.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.TeleporterInv;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.handlers.players.minigames.ChickenFightPlayer;
import fadidev.orbitmines.minigames.handlers.players.minigames.SkywarsPlayer;
import fadidev.orbitmines.minigames.handlers.players.minigames.SurvivalGamesPlayer;
import fadidev.orbitmines.minigames.handlers.players.minigames.UHCPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 13-9-2016.
 */
public class MiniGamesTeleporterInv extends TeleporterInv {

    private OrbitMinesMiniGames miniGames;
    private Arena arena;

    public MiniGamesTeleporterInv(Arena arena){
        miniGames = OrbitMinesMiniGames.getMiniGames();
        this.arena = arena;
    }

    @Override
    protected List<OMPlayer> getPlayers() {
        List<OMPlayer> players = new ArrayList<>();
        if(arena != null)
            players.addAll(arena.getPlayers());

        return players;
    }

    @Override
    protected ItemStack getItem(OMPlayer omPlayer) {
        MiniGamesPlayer omp = (MiniGamesPlayer) omPlayer;

        ItemStack item = ItemUtils.getSkull(omp.getPlayer().getName());
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(omp.getName());
        List<String> itemLore = new ArrayList<>();
        itemLore.add("");
        
        Arena arena = omp.getArena();

        switch(arena.getType()){
            case CHICKEN_FIGHT:
                ChickenFightPlayer cfp = omp.getChickenFightPlayer();

                itemLore.add("§cHealth: §f" + String.format("%.1f", omp.getPlayer().getHealth() / 2).replaceAll(",", ".") + "/10.0");
                itemLore.add("§7Kills: §f" + cfp.getRoundKills());
                itemLore.add("");
                itemLore.add("§2§lChicken Fight Stats:");
                itemLore.add("§cGames " + MiniGamesMessages.WORD_PLAYED.get(omp) + ": §f" + (cfp.getWins() + cfp.getLoses()));
                itemLore.add("§a" + MiniGamesMessages.WORD_WINS.get(omp) + ": §f" + cfp.getWins());
                itemLore.add("§7" + MiniGamesMessages.WORD_TOTAL_KILLS.get(omp) + ": §f" + cfp.getKills());
                itemLore.add("§b" + MiniGamesMessages.WORD_BEST.get(omp) + " Streak: §f" + cfp.getBestStreak());
                break;
            case GHOST_ATTACK:
                break;
            case SKYWARS:
                SkywarsPlayer swp = omp.getSkywarsPlayer();

                itemLore.add("§cHealth: §f" + String.format("%.1f", omp.getPlayer().getHealth() / 2).replaceAll(",", ".") + "/10.0");
                itemLore.add("§6Food: §f" + String.format("%.1f", (double) omp.getPlayer().getFoodLevel() / 2).replaceAll(",", ".") + "/10.0");
                itemLore.add("§7Kills: §f" + swp.getRoundKills());
                itemLore.add("");
                itemLore.add("§2§lSkywars Stats:");
                itemLore.add("§cGames " + MiniGamesMessages.WORD_PLAYED.get(omp) + ": §f" + (swp.getWins() + swp.getLoses()));
                itemLore.add("§a" + MiniGamesMessages.WORD_WINS.get(omp) + ": §f" + swp.getWins());
                itemLore.add("§7" + MiniGamesMessages.WORD_TOTAL_KILLS.get(omp) + ": §f" + swp.getKills());
                itemLore.add("§b" + MiniGamesMessages.WORD_BEST.get(omp) + " Streak: §f" + swp.getBestStreak());
                break;
            case SPLATCRAFT:
                break;
            case SPLEEF:
                break;
            case SURVIVAL_GAMES:
                SurvivalGamesPlayer sgp = omp.getSurvivalGamesPlayer();

                itemLore.add("§cHealth: §f" + String.format("%.1f", omp.getPlayer().getHealth() / 2).replaceAll(",", ".") + "/10.0");
                itemLore.add("§6Food: §f" + String.format("%.1f", (double) omp.getPlayer().getFoodLevel() / 2).replaceAll(",", ".") + "/10.0");
                itemLore.add("§7Kills: §f" + sgp.getRoundKills());
                itemLore.add("");
                itemLore.add("§2§lSurvival Games Stats:");
                itemLore.add("§cGames " + MiniGamesMessages.WORD_PLAYED.get(omp) + ": §f" + (sgp.getWins() + sgp.getLoses()));
                itemLore.add("§a" + MiniGamesMessages.WORD_WINS.get(omp) + ": §f" + sgp.getWins());
                itemLore.add("§7" + MiniGamesMessages.WORD_TOTAL_KILLS.get(omp) + ": §f" + sgp.getKills());
                itemLore.add("§b" + MiniGamesMessages.WORD_BEST.get(omp) + " Streak: §f" + sgp.getBestStreak());
                break;
            case ULTRA_HARD_CORE:
                UHCPlayer uhcp = omp.getUhcPlayer();

                itemLore.add("§cHealth: §f" + String.format("%.1f", omp.getPlayer().getHealth() / 2).replaceAll(",", ".") + "/10.0");
                itemLore.add("§6Food: §f" + String.format("%.1f", (double) omp.getPlayer().getFoodLevel() / 2).replaceAll(",", ".") + "/10.0");
                itemLore.add("§7Kills: §f" + uhcp.getRoundKills());
                itemLore.add("");
                itemLore.add("§2§lUHC Stats:");
                itemLore.add("§cGames " + MiniGamesMessages.WORD_PLAYED.get(omp) + ": §f" + (uhcp.getWins() + uhcp.getLoses()));
                itemLore.add("§a" + MiniGamesMessages.WORD_WINS.get(omp) + ": §f" + uhcp.getWins());
                itemLore.add("§7" + MiniGamesMessages.WORD_TOTAL_KILLS.get(omp) + ": §f" + uhcp.getKills());
                itemLore.add("§b" + MiniGamesMessages.WORD_BEST.get(omp) + " Streak: §f" + uhcp.getBestStreak());
                break;
        }
        
        itemLore.add("");
        itemLore.add(MiniGamesMessages.INV_CLICK_TO_TELEPORT.get(omp));
        itemLore.add("");
        itemmeta.setLore(itemLore);
        item.setItemMeta(itemmeta);

        return item;
    }
}
