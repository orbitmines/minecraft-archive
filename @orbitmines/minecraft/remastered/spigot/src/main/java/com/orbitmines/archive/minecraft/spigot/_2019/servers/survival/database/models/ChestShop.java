package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.InventoryUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChestShop {

    public static final int MAX_AMOUNT = 999;
    public static final int MAX_PRICE = 999999;

    private SurvivalChestShop model;

    private String itemName;
    private int index;
    private boolean scroll;

    private OfflinePlayer offlineOwner;
    @Setter private SurvivalPlayerModel offlineOwnerModel;

    public ChestShop(SurvivalChestShop model) {
        this.model = model;

        setupScroller();

        if (getUuid() != null)
            offlineOwner = OfflinePlayer.get(getUuid());

        offlineOwnerModel = SurvivalPlayerModel.findBy(SurvivalPlayerModel.class, SurvivalPlayerModel.column.UUID.is(getUuid()));
    }

    public ChestShop(SurvivalPlayerModel model, UUID uuid, Location location, Material material, SurvivalChestShop.PurchaseType purchaseType, int amount, int price) {
        this.model = new SurvivalChestShop(uuid, location, material, purchaseType, amount, price);
        this.offlineOwnerModel = model;

        setupScroller();

        SpigotServer.getInstance().runAsync(() -> {
            if (getUuid() != null)
                offlineOwner = OfflinePlayer.get(getUuid());
        });
    }

    public void delete() {
        survival().getChestShops().remove(this);

        model.delete();
    }

    public void setMaterial(Material material) {
        model.setMaterial(material);

        setupScroller();
    }

    public boolean canBuy() {
        return InventoryUtils.getAmount(getInventory(), getMaterial()) >= getAmount();
    }

    public boolean canSell() {
        return InventoryUtils.getEmptySlotCount(getInventory()) >= InventoryUtils.getSlotsRequired(getMaterial(), getAmount());
    }

    public Chest getChest() {
        return BlockUtils.getChestAtSign(getLocation());
    }

    public Inventory getInventory() {
        return getChest().getInventory();
    }

    public boolean hasCredits() {
        return getUuid() == null || getOwnerModel().getCredits() >= getPrice();
    }

    private SurvivalPlayerModel getOwnerModel() {
        if (getUuid() == null)
            return null;

        return isOwnerOnline() ? getOnlineOwner().getSurvivalModel() : offlineOwnerModel;
    }

    private boolean isOwnerOnline() {
        return getOnlineOwner() != null;
    }

    public SurvivalPlayer getOnlineOwner() {
        if (getUuid() == null)
            return null;

        return survival().getPlayer(getUuid());
    }

    public PlayerInstance getOwnerInstance() {
        if (getUuid() == null)
            return null;

        return isOwnerOnline() ? getOnlineOwner() : offlineOwner;
    }

    private Survival survival() {
        return (Survival) Survival.getInstance();
    }

    private void setupScroller() {
        this.itemName = ItemUtils.getName(getMaterial());
        this.index = 0;
        this.scroll = itemName.length() > 16;
    }

    public void tick() {
        if (!scroll || itemName.length() <= 16) {
            update();
            return;
        }

        int maxIndex = itemName.length() - 16;
        index++;

        if (index > maxIndex) {
            index = maxIndex;
            scroll = false;

            new BukkitRunnable() {
                @Override
                public void run() {
                    index = 0;

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            scroll = true;
                        }
                    }.runTaskLaterAsynchronously(survival().getPlugin(), 60);
                }
            }.runTaskLaterAsynchronously(survival().getPlugin(), 20);
        }

        update();
    }

    public void update() {
        List<Player> players = PlayerUtils.getNearbyPlayers(getLocation(), 16);

        if (players.size() == 0)
            return;

        SpigotServer.getInstance().runSync(() -> {
            if (isDestroyed())
                return;

            Block block = getLocation().getBlock();

            if (!ItemUtils.isSign(block.getType())) {
                delete();
                return;
            }

            for (Language language : Language.values()) {
                String[] lines = new String[4];
                lines[0] = getPurchaseTypeDisplay(language);
                lines[1] = this.itemName.length() <= 16 ? itemName : itemName.substring(index, index + 16);
                lines[2] = NumberUtils.locale(getAmount()) + " : " + NumberUtils.locale(getPrice()) + "C";
                lines[3] = getUuid() != null ? getOwnerInstance().getName(Name.RAW) : survival().getType().getDisplayName();

                for (Player player : players) {
                    SurvivalPlayer survivalPlayer = survival().getPlayer(player);

                    if (survivalPlayer.getLanguage() != language)
                        continue;

                    player.sendSignChange(getLocation(), lines);
                }
            }
        });
    }

    private String getPurchaseTypeDisplay(Language language) {
        return (usable() ? Color.GREEN : Color.MAROON).getCc() + "§l" + getPurchaseType().getName(language);
    }

    private boolean usable() {
        if (getPurchaseType() == SurvivalChestShop.PurchaseType.BUY)
            return canBuy();
        else
            return canSell();
    }

    public void buy(SurvivalPlayer player) {
        List<ItemStack> bought = InventoryUtils.removeItems(getInventory(), getMaterial(), getAmount());
        player.getInventory().addItem(bought.toArray(new ItemStack[bought.size()]));
        player.updateInventory();

        player.removeCredits(getPrice());
        player.update(SurvivalPlayerModel.column.CREDITS);

        if (getUuid() != null) {
            SurvivalPlayerModel model = getOwnerModel();
            model.addCredits(getPrice());
            model.update(SurvivalPlayerModel.column.CREDITS);

            addTimeUsed();
            update(SurvivalChestShop.column.TIMES_USED);
        }

        PlayerInstance owner = getOwnerInstance();
        String ownerName = getUuid() != null ? owner.getName(Name.RAW_COLORED) : survival().getType().getDisplayName();

        player.sendMessage("Shop", Color.LIME, "survival", "player.chest_shop.buy.buyer", getItemDisplayName(), ownerName + "§7", getPriceDisplay() + "§7");

        if (owner == null)
            return;

        SurvivalPlayer onlineOwner = getOnlineOwner();
        if (onlineOwner == null)
            return;

        onlineOwner.sendMessage("Shop", Color.LIME, "survival", "player.chest_shop.buy.owner", player.getName(Name.RAW_COLORED) + "§7", getItemDisplayName(), getPriceDisplay() + "§7");
    }

    public void sell(SurvivalPlayer player) {
        if (getUuid() == null)
            throw new IllegalStateException();

        List<ItemStack> sold = InventoryUtils.removeItems(player.getInventory(), getMaterial(), getAmount());
        getInventory().addItem(sold.toArray(new ItemStack[sold.size()]));
        player.updateInventory();

        if (getUuid() != null) {
            SurvivalPlayerModel model = getOwnerModel();
            model.removeCredits(getPrice());
            model.update(SurvivalPlayerModel.column.CREDITS);

            addTimeUsed();
            update(SurvivalChestShop.column.TIMES_USED);
        }

        player.addCredits(getPrice());
        player.update(SurvivalPlayerModel.column.CREDITS);

        PlayerInstance owner = getOwnerInstance();
        String ownerName = getUuid() != null ? owner.getName(Name.RAW_COLORED) : survival().getType().getDisplayName();

        player.sendMessage("Shop", Color.LIME, "survival", "player.chest_shop.sell.seller", getItemDisplayName(), ownerName + "§7", getPriceDisplay() + "§7");

        SurvivalPlayer onlineOwner = getOnlineOwner();
        if (onlineOwner == null)
            return;

        onlineOwner.sendMessage("Shop", Color.LIME, "survival", "player.chest_shop.sell.owner", player.getName(Name.RAW_COLORED) + "§7", getItemDisplayName(), getPriceDisplay() + "§7");
    }
    
    private String getItemDisplayName() {
        return "§a§l" + itemName + " §7(§a§l" + getAmount() + "x§7)";
    }

    public String getPriceDisplay() {
        return getPriceDisplay(getPrice());
    }

    public String getPriceDisplay(int count) {
        return "§2§l" + NumberUtils.locale(count) + " " + (count == 1 ? "Credit" : "Credits");
    }

    /*

        SurvivalChestShop Delegates

     */

    public Date getCreatedAt() {
        return model.getCreatedAt();
    }

    public void setAmount(int amount) {
        model.setAmount(amount);
    }

    public void setPrice(int price) {
        model.setPrice(price);
    }

    public Long getId() {
        return model.getId();
    }

    public UUID getUuid() {
        return model.getUuid();
    }

    public Location getLocation() {
        return model.getLocation();
    }

    public Material getMaterial() {
        return model.getMaterial();
    }

    public SurvivalChestShop.PurchaseType getPurchaseType() {
        return model.getPurchaseType();
    }

    public void setPurchaseType(SurvivalChestShop.PurchaseType purchaseType) {
        model.setPurchaseType(purchaseType);
    }

    public int getAmount() {
        return model.getAmount();
    }

    public int getPrice() {
        return model.getPrice();
    }

    public int getTimesUsed() {
        return model.getTimesUsed();
    }

    public void addTimeUsed() {
        model.addTimeUsed();
    }

    public void insert() {
        model.insert();
    }

    public void update(SurvivalChestShop.column... columns) {
        model.update(columns);
    }

    public boolean isInserted() {
        return model.isInserted();
    }

    public boolean isDestroyed() {
        return model.isDestroyed();
    }

    public void reload() {
        model.reload();
    }

    public void insertOrUpdate(SurvivalChestShop.column... columns) {
        model.insertOrUpdate(columns);
    }

    public static ArrayList<ChestShop> getAll() {
        ArrayList<ChestShop> chestShops = new ArrayList<>();

        for (SurvivalChestShop model : SurvivalChestShop.getAll(SurvivalChestShop.class)) {
            chestShops.add(new ChestShop(model));
        }

        return chestShops;
    }
}
