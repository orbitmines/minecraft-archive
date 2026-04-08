package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalFavoriteWarp;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalHome;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.claim.Visualization;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.ChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Home;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Warp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items.ClaimTool;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region.Region;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.scoreboard.SurvivalScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.teleportable.Teleportable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.Title;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotTimer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SurvivalPlayer extends OMPlayer<Survival, SurvivalPlayer> {

    @Getter protected SurvivalPlayerModel survivalModel;
    private List<Home> homes;
    private List<Warp> warps;
    private List<SurvivalFavoriteWarp> favoriteWarps;

    @Getter @Setter protected Teleportable teleportingTo;
    @Getter @Setter protected SpigotTimer teleportingTimer;

    @Getter @Setter private Region lastRegion;

    @Getter @Setter private Visualization visualization;
    @Getter @Setter private Claim lastClaim;
    @Getter @Setter private Claim resizingClaim;
    @Getter @Setter private Location lastClaimToolLocation;

    public SurvivalPlayer(Player player, Survival server) {
        super(player, server);

        setGameMode(GameMode.SURVIVAL);
    }

    @Override
    public boolean onJoin() {
        super.onJoin();

        this.survivalModel = SurvivalPlayerModel.findOrInitializeBy(getUUID());

        if (!this.survivalModel.isInserted()) {
            this.survivalModel.insert();

            /* First login after world was reset */
            if (!isFirstLogin())
                onFirstLogin();
        }

        for (ChestShop shop : server.getChestShops()) {
            if (shop.getUuid() != null && getUUID().equals(shop.getUuid()))
                shop.setOfflineOwnerModel(survivalModel);
        }

//      TODO ON RESET
//        Location logOutLocation = getData().getLogoutLocation();
//        if (logOutLocation != null && (logOutLocation.getWorld().getName().equals(survival.getWorld_nether().getName()) || logOutLocation.getWorld().getName().equals(survival.getWorld_the_end().getName())))
//            player.teleport(logOutLocation);

        if (isLogoutFly()) {
            setAllowFlight(true);
            setFlying(true);
        }

        reloadHomes();

        server.runSync(() -> {
            resetScoreboard();
            setScoreboard(new SurvivalScoreboard(server, this));

            /* Check if the player logged out in a lobby world (default or custom).
               The world reference may be null after restart (custom lobby not loaded yet). */
            Location logout = getLogoutLocation();
            boolean wasInLobby = logout != null && logout.getWorld() != null && logout.getWorld().equals(server.getLobby().getWorld());
            boolean wasInCustomLobby = logout != null && logout.getWorld() == null && getLobbyPreferenceMap() != null;

            if (wasInLobby) {
                /* Survival Lobby gets deleted and after restart it's not recognized as the same world due to no session.lock, so we teleport them to their logoutlocation */
                this.bukkit().teleport(logout);
            }

            /* Custom lobby teleport — only if they were in a lobby world when they logged out */
            if ((wasInLobby || wasInCustomLobby) && getLobbyPreferenceMap() != null)
                server.teleportToPlayerLobby(this, null);
        });

        return true;
    }

    @Override
    public void beforeQuitSync() {
        if (teleportingTo != null)
            teleportingTimer.cancel();

        setLogoutLocation(getLocation());
        setLogoutFly(isFlying());
        update(SurvivalPlayerModel.column.LOGOUT_LOCATION, SurvivalPlayerModel.column.LOGOUT_FLY);

        super.beforeQuitSync();
    }

    @Override
    protected void register() {
        server.registerPlayer(this);
    }

    @Override
    protected void unregister() {
        server.unregisterPlayer(this);
    }

    @Override
    public SurvivalPlayer getInstance() {
        return this;
    }

    @Override
    public void onFirstLogin() {
        server.runSync(() -> {
            if (player.getInventory().getItem(8) == null)
                player.getInventory().setItem(8, ClaimTool.ITEM.build());

            if (player.getInventory().getItem(7) == null)
                player.getInventory().setItem(7, new ItemStack(Material.COOKED_BEEF, 5));
        });

        Title<SurvivalPlayer> title = new Title<>(p -> "", p -> "§7§l" + translate("survival", "player.first_join", "§a§l/region random§7§l", "§a§l/region§7§l"), 20, 20 * 60 * 60, 20);
        title.send(this);
        /* Action Bars so New Players know how to join the world */
        new ActionBar(bukkit(), () -> "§7§l" + translate("survival", "player.first_join", "§a§l/region random§7§l", "§a§l/region§7§l"), Integer.MAX_VALUE) {
            @Override
            public void onRun() {
                if (player.getWorld().getName().equals(server.getSpawn().getWorld().getName()))
                    return;

                forceStop();
                player.resetTitle();
            }
        }.send();
    }

    public boolean canSpawnPhantom() {
        if (getLastBedEnter() == null)
            return true;

        return (System.currentTimeMillis() - getLastBedEnter().getTime()) >= Survival.PHANTOM_DELAY_AFTER_BED_ENTER;
    }

    public int getRemainingClaimBlocks() {
        return server.getClaimHandler().getRemaining(getUUID(), getClaimBlocks());
    }

    public int getHomesAllowed() {
        if (isEligible(VipRank.EMERALD))
            return 100 + getExtraHomes();
        else if (isEligible(VipRank.DIAMOND))
            return 50 + getExtraHomes();
        else if (isEligible(VipRank.GOLD))
            return 25 + getExtraHomes();
        else if (isEligible(VipRank.IRON))
            return 10 + getExtraHomes();
        else
            return 3 + getExtraHomes();
    }

    public int getWarpsAllowed() {
        return (isEligible(VipRank.EMERALD) ? 1 : 0) + (isWarpSlotShop() ? 1 : 0) + (isWarpSlotPrisms() ? 1 : 0);
    }

    /*

        Models

     */

    public void reloadHomes() {
        this.homes = new ArrayList<>();

        for (SurvivalHome home : SurvivalHome.getAll(SurvivalHome.class, SurvivalHome.column.UUID.is(getUUID()))) {
            this.homes.add(new Home(home));
        }
    }

    public List<Home> getHomes(boolean reload) {
        if (this.homes == null && !reload)
            return Collections.emptyList();

        if (reload || this.homes == null)
            reloadHomes();

        return homes;
    }

    public Home getHome(String name) {
        for (Home home : this.homes) {
            if (home.getName().equalsIgnoreCase(name))
                return home;
        }
        return null;
    }

    public void reloadWarps() {
        this.warps = new ArrayList<>();

        for (Warp warp : server.getWarps()) {
            if (getUUID().equals(warp.getOwner()))
                this.warps.add(warp);
        }
    }

    public List<Warp> getWarps() {
        if (this.warps == null)
            reloadWarps();

        return this.warps;
    }

    public List<SurvivalFavoriteWarp> getFavoriteWarps(boolean reload) {
        if (reload || this.favoriteWarps == null)
            this.favoriteWarps = SurvivalFavoriteWarp.getAll(SurvivalFavoriteWarp.class, SurvivalFavoriteWarp.column.UUID.is(getUUID()));

        return favoriteWarps;
    }

    public SurvivalFavoriteWarp getFavoriteWarp(Warp warp, boolean reload) {
        for (SurvivalFavoriteWarp favorite : getFavoriteWarps(reload)) {
            if (favorite.getWarpId().equals(warp.getId()))
                return favorite;
        }
        return null;
    }

    /*

        SurvivalPlayerModel Delegates

     */

    public void setWarpSlotPrisms(boolean warpSlotPrisms) {
        survivalModel.setWarpSlotPrisms(warpSlotPrisms);
    }

    public void setBackLocation(Location backLocation) {
        survivalModel.setBackLocation(backLocation);

        /* TODO: async? */
        update(SurvivalPlayerModel.column.BACK_LOCATION);
    }

    public void setLogoutLocation(Location logoutLocation) {
        survivalModel.setLogoutLocation(logoutLocation);
    }

    public void setLogoutFly(boolean logoutFly) {
        survivalModel.setLogoutFly(logoutFly);
    }

    public void setWarpSlotShop(boolean warpSlotShop) {
        survivalModel.setWarpSlotShop(warpSlotShop);
    }

    public int getCredits() {
        return survivalModel.getCredits();
    }

    public int getClaimBlocks() {
        return survivalModel.getClaimBlocks();
    }

    public Location getBackLocation() {
        return survivalModel.getBackLocation();
    }

    public int getBackCharges() {
        return survivalModel.getBackCharges();
    }

    public Location getLogoutLocation() {
        return survivalModel.getLogoutLocation();
    }

    public boolean isLogoutFly() {
        return survivalModel.isLogoutFly();
    }

    public int getExtraHomes() {
        return survivalModel.getExtraHomes();
    }

    public boolean isWarpSlotShop() {
        return survivalModel.isWarpSlotShop();
    }

    public boolean isWarpSlotPrisms() {
        return survivalModel.isWarpSlotPrisms();
    }

    public void addCredits(int amount) {
        survivalModel.addCredits(amount);
    }

    public void removeCredits(int amount) {
        survivalModel.removeCredits(amount);
    }

    public void addClaimBlocks(int amount) {
        survivalModel.addClaimBlocks(amount);
    }

    public void addBackCharges(int amount) {
        survivalModel.addBackCharges(amount);
    }

    public void removeBackCharges(int amount) {
        survivalModel.removeBackCharges(amount);
    }

    public void addExtraHomes(int amount) {
        survivalModel.addExtraHomes(amount);
    }

    public Date getLastBedEnter() {
        return survivalModel.getLastBedEnter();
    }

    public void setLastBedEnter(Date lastBedEnter) {
        survivalModel.setLastBedEnter(lastBedEnter);
    }

    public void update(SurvivalPlayerModel.column... columns) {
        survivalModel.update(columns);
    }
}
