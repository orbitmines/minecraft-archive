package fadidev.orbitmines.minigames.inventories;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import fadidev.orbitmines.minigames.utils.enums.Uses;
import org.bukkit.Bukkit;
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
public class KitInv extends OMInventory {

    private TicketType type;

    public KitInv(TicketType type){
        this.type = type;

        setInventory(Bukkit.createInventory(null, 9, "§0§l" + type.getName()));
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

        MiniGamesPlayer omp = (MiniGamesPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals("§f§l" + MiniGamesMessages.WORD_SELECT.get(omp) + " " + type.getName())){
            Arena arena = omp.getArena();

            if(arena != null){
                if(omp.getTicketAmount(type) != 0){
                    switch(arena.getType()){
                        case CHICKEN_FIGHT:
                            omp.getChickenFightPlayer().setKitSelected(type.getKit());
                            break;
                        case GHOST_ATTACK:
                            omp.getGhostAttackPlayer().setKitSelected(type.getKit());
                            break;
                        case SKYWARS:
                            omp.getSkywarsPlayer().setKitSelected(type.getKit());
                            break;
                        case SPLATCRAFT:
                            omp.getSplatcraftPlayer().setKitSelected(type.getKit());
                            break;
                        case SPLEEF:
                            omp.getSpleefPlayer().setKitSelected(type.getKit());
                            break;
                        case SURVIVAL_GAMES:
                            omp.getSurvivalGamesPlayer().setKitSelected(type.getKit());
                            break;
                        case ULTRA_HARD_CORE:
                            omp.getUhcPlayer().setKitSelected(type.getKit());
                            break;
                    }

                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                    p.sendMessage(MiniGamesMessages.KIT_SELECTED.get(omp, type.getName()));
                }
                else{
                    p.sendMessage(MiniGamesMessages.CANNOT_SELECT_KIT.get(omp));
                }
            }
        }
    }

    private ItemStack[] getContents(Player player){
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        {
            ItemStack item = new ItemStack(type.getItemStack().getType(), 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l" + MiniGamesMessages.WORD_SELECT.get(omp) + " " + type.getName());
            List<String> lore = new ArrayList<>();

            if(type.getUses() == Uses.ONE_TIME){
                lore.add(" §7" + Messages.WORD_AMOUNT.get(omp) + ": " + type.getRarity().getColor() + omp.getTicketAmount(type) + " ");
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    lore.add(" §a§l" + Messages.WORD_UNLOCKED.get(omp) + " ");
                }
                else{
                    lore.add(" §4§l" + Messages.WORD_LOCKED.get(omp) + " ");
                }
            }
            lore.add(" §7" + MiniGamesMessages.WORD_RARITY.get(omp) + ": " + type.getRarity().getName() + " ");
            lore.add("");
            for(String s : type.getDescription(omp)){
                lore.add(" " + type.getRarity().getColor() + "- " + s);
            }
            lore.add("");
            lore.add(" " + type.getRarity().getColor() + MiniGamesMessages.TICKET_USED_ON_START_1.get(omp));
            lore.add(" " + type.getRarity().getColor() + MiniGamesMessages.TICKET_USED_ON_START_2.get(omp));
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability(type.getItemStack().getDurability());
            contents[4] = OrbitMinesMiniGames.getMiniGames().getApi().getNms().customItem().hideFlags(item, 34);
        }

        return contents;
    }
}
