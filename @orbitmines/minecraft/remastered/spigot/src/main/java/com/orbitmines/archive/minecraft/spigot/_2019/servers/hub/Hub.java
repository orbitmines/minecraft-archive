package com.orbitmines.archive.minecraft.spigot._2019.servers.hub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.TabListHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.EnterVoidEvent;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.SpawnLocationEvent;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.instantiators.LeaderBoardDonations;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.instantiators.LeaderBoardTopVoters;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.datapoints.HubDataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.datapoints.HubDataPointSpawnpoint;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.datapoints.HubDataPointStaffHologram;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.kit.HubKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.kit.NewPlayerKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.loot.HubLootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.player.HubPlayer;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.Hologram;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.Prevention;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Hub extends OMServer<Hub, HubPlayer> {

    public Hub(JavaPlugin plugin) {
        super(plugin);
    }

    @Getter private HubKit kit;
    @Getter private NewPlayerKit newPlayerKit;

    @Override
    protected Hub instance() {
        return this;
    }

    @Override
    public HubPlayer newPlayerInstance(Player player) {
        return new HubPlayer(player, this);
    }

    @Override
    public void afterStartupSync() {
        this.kit = new HubKit(this);
        this.newPlayerKit = new NewPlayerKit(this);

        Prevention.prevent(plugin, getLobby().getWorld(),
            Prevention.BLOCK_BREAK,
            Prevention.BLOCK_INTERACTING,
            Prevention.BLOCK_PLACE,
            Prevention.MONSTER_EGG_USAGE,
            Prevention.BUCKET_USAGE,
//            Prevention.CHUNK_UNLOAD,
            Prevention.CLICK_PLAYER_INVENTORY,
            Prevention.ENTITY_INTERACTING,
            Prevention.FOOD_CHANGE,
            Prevention.ITEM_DROP,
            Prevention.LEAF_DECAY,
            Prevention.PLAYER_DAMAGE,
            Prevention.ITEM_PICKUP,
            Prevention.SWAP_HAND_ITEMS,
            Prevention.WEATHER_CHANGE
        );

        /* DataPoints */
        List<Location> spawns = ((HubDataPointSpawnpoint) (getLobby().getDataPointHandler().getDataPoint(HubDataPointHandler.Type.SPAWNPOINT))).getSpawns();

        registerEvents(
            new SpawnLocationEvent() {
                @Override
                public Location getSpawnLocation(Player player) {
                    return RandomUtils.randomFrom(spawns);
                }
            },
            new EnterVoidEvent<Hub, HubPlayer>(this, getLobby().getWorld()) {
                @Override
                public Location getRespawnLocation(HubPlayer player) {
                    return RandomUtils.randomFrom(spawns);
                }
            }
        );
    }

    @Override
    public void afterStartupAsync() {
        super.afterStartupAsync();

        Map<Location, UUID> staffHolos = ((HubDataPointStaffHologram) (getLobby().getDataPointHandler().getDataPoint(HubDataPointHandler.Type.STAFF_HOLO))).getStaffHolograms();
        for (Location location : staffHolos.keySet()) {
            UUID uuid = staffHolos.get(location);
            OfflinePlayer player = OfflinePlayer.get(uuid);

            String playerName = player.getName(Name.RAW);
            StaffRank staffRank = player.getStaffRank();

            runSync(() -> {
                Hologram hologram = new Hologram(location, 0, Hologram.Face.UP);
                hologram.addLine(staffRank::getDisplayName, false);
                hologram.addLine(() -> staffRank.getPrefixColor().getCc() + playerName, false);
                hologram.create();
            });
        }
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public Server getType() {
        return Server.HUB;
    }

    @Override
    public ChatHandler newChatHandler(PlayerInstance sender, ChatHandler.Type type, String message) {
        return new ChatHandler<>(this, type, sender, message);
    }

    @Override
    public TabListHandler newTabListHandler(HubPlayer player) {
        return new TabListHandler<>(this, player);
    }

    @Override
    public LootHandler newLootHandler(HubPlayer player) {
        return new HubLootHandler(player);
    }

    @Override
    public boolean clearPlayerData() {
        return true;
    }

    @Override
    public boolean saveChunksOnRestart() {
        return false;
    }

    @Override
    public boolean broadcastWhenSaving() {
        return false;
    }

    @Override
    public boolean shouldSetupLobby() {
        return true;
    }

    @Override
    public DataPointHandler createDataPointHandler(OMMap.Type type) {
        return new HubDataPointHandler();
    }

    @Override
    public Prevention[] getLobbyPreventions() {
        return new Prevention[]{
            Prevention.BLOCK_BREAK,
            Prevention.BLOCK_INTERACTING,
            Prevention.BLOCK_PLACE,
            Prevention.MONSTER_EGG_USAGE,
            Prevention.BUCKET_USAGE,
            Prevention.CLICK_PLAYER_INVENTORY,
            Prevention.ENTITY_INTERACTING,
            Prevention.FOOD_CHANGE,
            Prevention.ITEM_DROP,
            Prevention.LEAF_DECAY,
            Prevention.PLAYER_DAMAGE,
            Prevention.ITEM_PICKUP,
            Prevention.SWAP_HAND_ITEMS,
            Prevention.WEATHER_CHANGE
        };
    }

    @Override
    public void setupNpc(String string, Location location) {

    }

    @Override
    protected void instantiateLeaderBoards() {
        new LeaderBoardTopVoters();
        new LeaderBoardDonations();
    }
}
