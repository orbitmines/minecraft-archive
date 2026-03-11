package fadidev.orbitmines.hub.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.HubMessages;
import fadidev.orbitmines.hub.handlers.Ticket;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.utils.enums.Rarity;
import fadidev.orbitmines.hub.utils.enums.TicketType;
import fadidev.orbitmines.hub.utils.enums.Uses;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Fadi on 9-9-2016.
 */
public class TicketInv extends OMInventory {

    private OrbitMinesHub hub;
    private boolean gambling;
    private int index;
    private int longIndex;

    public TicketInv(boolean gambling){
        hub = OrbitMinesHub.getHub();
        setInventory(Bukkit.createInventory(null, gambling ? 45 : 9, "§0§lTicket Gamble"));

        this.gambling = gambling;
    }

    @Override
    public void open(Player player) {
        HubPlayer omp = HubPlayer.getHubPlayer(player);
        if(!gambling)
            getInventory().setContents(getContents(omp));

        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        HubPlayer ompHub = (HubPlayer) omp;

        if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§a§lStart Ticket Gamble")){
            if(ompHub.hasMiniGameTickets(3)){
                ompHub.removeMiniGameTickets(3);
                TicketInv ticketinv = new TicketInv(true);
                ticketinv.update(ompHub.getPlayer());
            }
            else{
                ompHub.requiredMiniGameTickets(3);
            }
        }
        else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§lCancel")){
            new MiniGamesInv().open(omp.getPlayer());
        }
        else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§f§l+5 Tickets")){
            if(ompHub.hasVIPPoints(40)){
                new ConfirmInv(item, Currency.getCurrency(Material.DIAMOND), 40, new ConfirmInv.Action() {
                    @Override
                    public void confirmed(OMPlayer omp) {
                        omp.removeVipPoints(40);
                        ((HubPlayer) omp).addMiniGameTickets(5);
                        new TicketInv(false).open(omp.getPlayer());
                    }

                    @Override
                    public void cancelled(OMPlayer omp) {
                        new TicketInv(false).open(omp.getPlayer());
                    }
                }).open(omp.getPlayer());
            }
            else{
                ompHub.requiredVIPPoints(40);
            }
        }
        else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§0§f§l+3 Tickets")){
            if(ompHub.hasOrbitMinesTokens(3)){
                new ConfirmInv(item, Currency.getCurrency(Material.GOLD_NUGGET), 3, new ConfirmInv.Action() {
                    @Override
                    public void confirmed(OMPlayer omp) {
                        omp.removeOrbitMinesTokens(3);
                        ((HubPlayer) omp).addMiniGameTickets(3);
                        new TicketInv(false).open(omp.getPlayer());
                    }

                    @Override
                    public void cancelled(OMPlayer omp) {
                        new TicketInv(false).open(omp.getPlayer());
                    }
                }).open(omp.getPlayer());
            }
            else{
                ompHub.requiredOrbitMinesTokens(3);
            }
        }
        else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§f§l+3 Tickets")){
            if(ompHub.hasMiniGameCoins(100)){
                new ConfirmInv(item, Currency.getCurrency(Material.SNOW_BALL), 100, new ConfirmInv.Action() {
                    @Override
                    public void confirmed(OMPlayer omp) {
                        HubPlayer hubPlayer = (HubPlayer) omp;
                        hubPlayer.removeMiniGameCoins(100);
                        hubPlayer.addMiniGameTickets(3);
                        new TicketInv(false).open(omp.getPlayer());
                    }

                    @Override
                    public void cancelled(OMPlayer omp) {
                        new TicketInv(false).open(omp.getPlayer());
                    }
                }).open(omp.getPlayer());
            }
            else{
                ompHub.requiredMiniGameCoins(100);
            }
        }
    }

    public void update(final Player player){
        getInventory().setContents(getGambleContents());
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 5, 1);
        open(player);

        new BukkitRunnable(){
            public void run(){
                index++;
                if(index == 25)
                    longIndex = 5;
                else if(index == 35)
                    longIndex = 7;
                else if(index == 42)
                    longIndex = 10;
                else if(index == 47)
                    longIndex = 13;
                else if(index == 51)
                    longIndex = 16;
                else if(index == 54)
                    longIndex = 20;
                else if(index == 56){
                    index = -1;

                    new BukkitRunnable(){
                        public void run(){
                            HubPlayer omp = HubPlayer.getHubPlayer(player);
                            ItemStack item = getInventory().getItem(22);
                            Ticket ticket = TicketType.getTicket(item);

                            if(omp != null){
                                player.sendMessage(HubMessages.INV_YOU_FOUND.get(omp, item.getItemMeta().getDisplayName(), ticket.getType().getRarity().getName()));
                                if(ticket.getType().getUses() == Uses.ONE_TIME || omp.getTicketAmount(ticket.getType()) == 0){
                                    for(HubPlayer p : hub.getHubPlayers()){
                                        if(p != omp)
                                            p.getPlayer().sendMessage(HubMessages.INV_PLAYER_FOUND.get(p, omp.getName(), item.getItemMeta().getDisplayName(), ticket.getType().getRarity().getName()));
                                    }

                                    ticket.getType().getRarity().firework(player.getLocation());
                                }
                                else{
                                    player.sendMessage(HubMessages.INV_ALREADY_UNLOCKED.get(omp, ticket.getType().getRarity().getRefund() + ""));

                                    omp.addMiniGameCoins(ticket.getType().getRarity().getRefund());
                                }
                                omp.getPlayer().closeInventory();
                                omp.addTicketAmount(ticket.getType(), ticket.getAmount());

                                if(ticket.getType().getRarity() == Rarity.LEGENDARY){
                                    for(Player p : Bukkit.getOnlinePlayers()){
                                        p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 5, 1);
                                    }
                                }
                                else{
                                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
                                }
                            }
                        }
                    }.runTaskLater(hub, 40);
                }
                else{}

                if(index != -1)
                    update(player);
            }
        }.runTaskLater(hub, longIndex);
    }

    private ItemStack[] getContents(HubPlayer omp){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§a§lStart Ticket Gamble");
            List<String> lore = new ArrayList<>();
            lore.add(" §c" + Messages.WORD_PRICE.get(omp) + ": §f§l3 Tickets ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setDurability((short) 5);
            contents[1] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§c§l" + Messages.WORD_CANCEL.get(omp));
            item.setItemMeta(meta);
            item.setDurability((short) 14);
            contents[2] = item;
        }
        {
            ItemStack item = new ItemStack(Material.EMPTY_MAP, 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l+5 Tickets");
            List<String> lore = new ArrayList<>();
            lore.add(" §c" + Messages.WORD_PRICE.get(omp) + ": §b40 VIP Points ");
            meta.setLore(lore);
            item.setItemMeta(meta);;
            contents[5] = item;
        }
        {
            ItemStack item = new ItemStack(Material.EMPTY_MAP, 3);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§0§f§l+3 Tickets");
            List<String> lore = new ArrayList<>();
            lore.add(" §c" + Messages.WORD_PRICE.get(omp) + ": §e3 OMT ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[6] = item;
        }
        {
            ItemStack item = new ItemStack(Material.EMPTY_MAP, 3);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§l+3 Tickets");
            List<String> lore = new ArrayList<>();
            lore.add(" §c" + Messages.WORD_PRICE.get(omp) + ": §f§l100 Coins ");
            meta.setLore(lore);
            item.setItemMeta(meta);
            contents[7] = item;
        }

        return contents;
    }

    private ItemStack[] getGambleContents(){
        ItemStack[] contents = getInventory().getContents();

        ItemStack slot1 = contents[4];
        ItemStack slot2 = contents[13];
        ItemStack slot3 = contents[22];
        ItemStack slot4 = contents[31];
        if(slot1 == null){
            contents[4] = TicketType.getRandom();
            contents[13] = TicketType.getRandom();
            contents[22] = TicketType.getRandom();
            contents[31] = TicketType.getRandom();
            contents[40] = TicketType.getRandom();
        }
        else{
            contents[4] = TicketType.getRandom();
            contents[13] = slot1;
            contents[22] = slot2;
            contents[31] = slot3;
            contents[40] = slot4;
        }

        contents[21] = getBlackRollingItemStack();
        contents[23] = getBlackRollingItemStack();

        for(int i = 0; i < 45; i++){
            if(i != 4 && i != 13 && i != 22 && i != 31 && i != 40 && i != 21 && i != 23){
                contents[i] = getRollingItemStack();
            }
        }

        return contents;
    }

    private ItemStack getRollingItemStack(){
        List<Short> durabilities = Arrays.asList((short) 0, (short) 1, (short) 3, (short) 4, (short) 5, (short) 6, (short) 14);
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        item.setDurability(durabilities.get(new Random().nextInt(durabilities.size())));

        return item;
    }
    private ItemStack getBlackRollingItemStack(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        item.setDurability((short) 15);

        return item;
    }
}
