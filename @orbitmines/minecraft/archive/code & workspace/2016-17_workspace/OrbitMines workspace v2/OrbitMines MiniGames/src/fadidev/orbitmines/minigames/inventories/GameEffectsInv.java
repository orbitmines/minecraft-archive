package fadidev.orbitmines.minigames.inventories;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.Ticket;
import fadidev.orbitmines.minigames.handlers.data.ChickenFightData;
import fadidev.orbitmines.minigames.handlers.data.SurvivalGamesData;
import fadidev.orbitmines.minigames.handlers.data.UHCData;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import fadidev.orbitmines.minigames.utils.enums.Uses;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

import static fadidev.orbitmines.minigames.utils.enums.MiniGameType.SURVIVAL_GAMES;
import static fadidev.orbitmines.minigames.utils.enums.MiniGameType.ULTRA_HARD_CORE;

/**
 * Created by Fadi on 9-9-2016.
 */
public class GameEffectsInv extends OMInventory {

    private OrbitMinesMiniGames minigames;
    private MiniGameType type;

    public GameEffectsInv(MiniGameType type){
        this.minigames = OrbitMinesMiniGames.getMiniGames();
        this.type = type;

        setInventory(Bukkit.createInventory(null, type == MiniGameType.ULTRA_HARD_CORE || type == MiniGameType.CHICKEN_FIGHT ? 36 : 45, "§0§lGame Effects"));
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
        Arena arena = omp.getArena();
        Player p = omp.getPlayer();

        if(arena == null || item.getItemMeta().getLore() == null)
            return;

        List<String> lore = item.getItemMeta().getLore();

        switch(arena.getType()){
            case CHICKEN_FIGHT:
                for(TicketType type : Ticket.CF_PERKS){
                    if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals(type.getRarity().getColor() + type.getName())){
                        if(!lore.contains(" §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp))){
                            if(lore.contains(" §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp))){
                                if(type == TicketType.JUMP_BOOST){
                                    omp.removeTicket(TicketType.JUMP_BOOST);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    ((ChickenFightData) arena.getData()).setJumpBoost(true);

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(MiniGameType.CHICKEN_FIGHT).open(omplayer.getPlayer());
                                        }
                                    }
                                    p.closeInventory();
                                }
                                else if(type == TicketType.SPEED_BOOST){
                                    omp.removeTicket(TicketType.SPEED_BOOST);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    ((ChickenFightData) arena.getData()).setSpeedBoost(true);

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(MiniGameType.CHICKEN_FIGHT).open(omplayer.getPlayer());
                                        }
                                    }
                                    p.closeInventory();
                                }
                            }
                        }
                    }
                }
                break;
            case GHOST_ATTACK:
                break;
            case SKYWARS:
                for(TicketType type : Ticket.SKYWARS_PERKS){
                    if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals(type.getRarity().getColor() + type.getName())){
                        if(!lore.contains(" §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp))){
                            if(lore.contains(" §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp))){
                                omp.getSkywarsPlayer().setCage(type);

                                p.sendMessage(MiniGamesMessages.ACTIVATED_PLAYER.get(omp, item.getItemMeta().getDisplayName()));
                                p.closeInventory();
                            }
                        }
                    }
                }
                break;
            case SPLATCRAFT:
                break;
            case SPLEEF:
                break;
            case SURVIVAL_GAMES:
                for(TicketType type : Ticket.SG_PERKS){
                    if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals(type.getRarity().getColor() + type.getName())){
                        if(!lore.contains(" §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp))){
                            if(lore.contains(" §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp))){
                                if(type == TicketType.ENABLE_POTIONS){
                                    omp.removeTicket(TicketType.ENABLE_POTIONS);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    ((SurvivalGamesData) arena.getData()).setEnablePotions(true);

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getSurvivalGamesPlayer().isEnablePotions()){
                                            omplayer.getSurvivalGamesPlayer().setEnablePotions(false);
                                            omplayer.addTicketAmount(TicketType.ENABLE_POTIONS_PLAYER, 1);
                                        }
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(SURVIVAL_GAMES).open(omplayer.getPlayer());
                                        }
                                    }
                                    p.closeInventory();
                                }
                                else if(type == TicketType.ENABLE_POTIONS_PLAYER){
                                    omp.removeTicket(TicketType.ENABLE_POTIONS_PLAYER);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    omp.getSurvivalGamesPlayer().setEnablePotions(true);
                                    p.closeInventory();

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(SURVIVAL_GAMES).open(omplayer.getPlayer());
                                        }
                                    }
                                }
                                else if(type == TicketType.DOUBLE_LOOT){
                                    omp.removeTicket(TicketType.DOUBLE_LOOT);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    ((SurvivalGamesData) arena.getData()).setDoubleLoot(true);

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getSurvivalGamesPlayer().isDoubleLoot()){
                                            omplayer.getSurvivalGamesPlayer().setDoubleLoot(false);
                                            omplayer.addTicketAmount(TicketType.DOUBLE_LOOT_PLAYER, 1);
                                        }
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(SURVIVAL_GAMES).open(omplayer.getPlayer());
                                        }
                                    }
                                    p.closeInventory();
                                }
                                else if(type == TicketType.DOUBLE_LOOT_PLAYER){
                                    omp.removeTicket(TicketType.DOUBLE_LOOT_PLAYER);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    omp.getSurvivalGamesPlayer().setDoubleLoot(true);
                                    p.closeInventory();

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(SURVIVAL_GAMES).open(omplayer.getPlayer());
                                        }
                                    }
                                }
                                else{
                                    Color color = ((LeatherArmorMeta) type.getItemStack().getItemMeta()).getColor();
                                    omp.getSurvivalGamesPlayer().setLeatherColor(color);

                                    p.sendMessage(MiniGamesMessages.ACTIVATED_PLAYER.get(omp, item.getItemMeta().getDisplayName()));
                                    p.closeInventory();
                                }
                            }
                        }
                    }
                }
                break;
            case ULTRA_HARD_CORE:
                for(TicketType type : Ticket.UHC_PERKS){
                    if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals(type.getRarity().getColor() + type.getName())){
                        if(!lore.contains(" §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp))){
                            if(lore.contains(" §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp))){
                                if(type == TicketType.DOUBLE_IRON){
                                    omp.removeTicket(TicketType.DOUBLE_IRON);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    ((UHCData) arena.getData()).setDoubleIron(true);

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getUhcPlayer().isDoubleIron()){
                                            omplayer.getUhcPlayer().setDoubleIron(false);
                                            omplayer.addTicketAmount(TicketType.DOUBLE_IRON_PLAYER, 1);
                                        }
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(ULTRA_HARD_CORE).open(omplayer.getPlayer());
                                        }
                                    }
                                    p.closeInventory();
                                }
                                else if(type == TicketType.DOUBLE_IRON_PLAYER){
                                    omp.removeTicket(TicketType.DOUBLE_IRON_PLAYER);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    omp.getUhcPlayer().setDoubleIron(true);
                                    p.closeInventory();

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(ULTRA_HARD_CORE).open(omplayer.getPlayer());
                                        }
                                    }
                                }
                                else if(type == TicketType.GOLD_FROM_LAPIS){
                                    omp.removeTicket(TicketType.GOLD_FROM_LAPIS);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    omp.getUhcPlayer().setBlueGold(true);
                                    p.closeInventory();

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(ULTRA_HARD_CORE).open(omplayer.getPlayer());
                                        }
                                    }
                                }
                                else if(type == TicketType.GOLD_FROM_REDSTONE){
                                    omp.removeTicket(TicketType.GOLD_FROM_REDSTONE);
                                    arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    arena.sendMessage(MiniGamesMessages.ACTIVATED, omp.getName(), item.getItemMeta().getDisplayName());
                                    omp.getUhcPlayer().setRedGold(true);
                                    p.closeInventory();

                                    for(MiniGamesPlayer omplayer : arena.getPlayers()){
                                        if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
                                            new GameEffectsInv(ULTRA_HARD_CORE).open(omplayer.getPlayer());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    private ItemStack[] getContents(Player player){
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        switch(type){
            case CHICKEN_FIGHT:
                for(TicketType type : Ticket.CF_PERKS){
                    contents[type.getSlot()] = getItemStack(omp, type);
                }
                break;
            case GHOST_ATTACK:
                break;
            case SKYWARS:
                for(TicketType type : Ticket.SKYWARS_PERKS){
                    contents[type.getSlot()] = getItemStack(omp, type);
                }
                break;
            case SPLATCRAFT:
                break;
            case SPLEEF:
                break;
            case SURVIVAL_GAMES:
                for(TicketType type : Ticket.SG_PERKS){
                    contents[type.getSlot()] = getItemStack(omp, type);
                }
                break;
            case ULTRA_HARD_CORE:
                for(TicketType type : Ticket.UHC_PERKS){
                    contents[type.getSlot()] = getItemStack(omp, type);
                }
                break;
        }
        
        return contents;
    }

    private ItemStack getItemStack(MiniGamesPlayer omp, TicketType type){
        Arena arena = omp.getArena();
        String status = null;

        if(type == TicketType.ENABLE_POTIONS){
            if(((SurvivalGamesData) arena.getData()).isEnablePotions()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.ENABLE_POTIONS_PLAYER){
            if(((SurvivalGamesData) arena.getData()).isEnablePotions()){
                status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp) ;
            }
            else if(omp.getSurvivalGamesPlayer().isEnablePotions()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.DOUBLE_LOOT){
            if(((SurvivalGamesData) arena.getData()).isDoubleLoot()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.DOUBLE_LOOT_PLAYER){
            if(((SurvivalGamesData) arena.getData()).isDoubleLoot()){
                status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
            }
            else if(omp.getSurvivalGamesPlayer().isDoubleLoot()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.DOUBLE_IRON){
            if(((UHCData) arena.getData()).isDoubleIron()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.DOUBLE_IRON_PLAYER){
            if(((UHCData) arena.getData()).isDoubleIron()){
                status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
            }
            else if(omp.getUhcPlayer().isDoubleIron()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.GOLD_FROM_LAPIS){
            if(omp.getUhcPlayer().isBlueGold()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.GOLD_FROM_REDSTONE){
            if(omp.getUhcPlayer().isRedGold()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.JUMP_BOOST){
            if(((ChickenFightData) arena.getData()).isJumpBoost()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else if(type == TicketType.SPEED_BOOST){
            if(((ChickenFightData) arena.getData()).isSpeedBoost()){
                status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
            }
            else{
                if(omp.getTicketAmount(type) != 0){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                }
                else{
                    status = " §7Status: §4§l" + MiniGamesMessages.WORD_UNAVAILABLE.get(omp);
                }
            }
        }
        else{
            if(this.type == MiniGameType.SURVIVAL_GAMES){
                Color color = ((LeatherArmorMeta) type.getItemStack().getItemMeta()).getColor();
                Color activated = omp.getSurvivalGamesPlayer().getLeatherColor();

                if(activated != null && color == activated){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
                }
                else{
                    if(omp.getTicketAmount(type) != 0){
                        status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                    }
                }
            }
            else if(this.type == MiniGameType.SKYWARS){
                TicketType cage = omp.getSkywarsPlayer().getCage();

                if(cage != null && type == cage){
                    status = " §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp);
                }
                else{
                    if(omp.getTicketAmount(type) != 0){
                        status = " §7Status: §a§l" + MiniGamesMessages.WORD_CLICK_TO_ACTIVATE.get(omp);
                    }
                }
            }
        }

        ItemStack item = type.getItemStack();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(type.getRarity().getColor() + type.getName());
        List<String> lore = new ArrayList<>();
        if(type.getUses() == Uses.ONE_TIME){
            lore.add(" §7" + Messages.WORD_AMOUNT.get(omp) + ": " + type.getRarity().getColor() + omp.getTicketAmount(type) + " ");
        }
        else{
            if(omp.getTicketAmount(type) != 0){
                lore.add(" §a§l" + Messages.WORD_UNLOCKED.get(omp));
            }
            else{
                lore.add(" §4§l" + Messages.WORD_LOCKED.get(omp));
            }
        }
        lore.add(" §7" + MiniGamesMessages.WORD_RARITY.get(omp) + ": " + type.getRarity().getName() + " ");
        lore.add("");
        if(status != null){
            lore.add(status);
            lore.add("");
        }
        for(String s : type.getDescription(omp)){
            lore.add(" " + type.getRarity().getColor() + "- " + s);
        }
        if(type.getUses() == Uses.ONE_TIME){
            lore.add("");
            lore.add(" " + type.getRarity().getColor() + MiniGamesMessages.TICKET_USED_ON_START_1.get(omp));
            lore.add(" " + type.getRarity().getColor() + MiniGamesMessages.TICKET_USED_ON_START_2.get(omp));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        if(status != null && status.equals(" §7Status: §a§l" + MiniGamesMessages.WORD_ACTIVATED.get(omp))){
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            return minigames.getApi().getNms().customItem().hideFlags(item, 35);
        }
        return minigames.getApi().getNms().customItem().hideFlags(item, 34);
    }
}
