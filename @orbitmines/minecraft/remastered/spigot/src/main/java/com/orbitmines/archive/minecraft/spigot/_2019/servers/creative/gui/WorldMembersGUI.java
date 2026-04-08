package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorldMember;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class WorldMembersGUI extends PaginatableGUI<CreativePlayer, CreativeWorld, CreativeWorldMember> {

    private final Creative creative;
    private final CreativeWorld world;

    public WorldMembersGUI(Creative creative, CreativeWorld world, CreativePlayer viewer) {
        super(54, "§0§l" + viewer.translate("creative", "player.world.members.title") + " - " + world.getName(), viewer, world, 9, 3);
        this.creative = creative;
        this.world = world;

        /* Back button */
        set(0, 0, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1, "§3« " + viewer.translate("creative", "player.world.editor.back"));
        }, event -> {
            new WorldEditorGUI(creative, world, viewer).open();
        }));

        /* Add Member button */
        set(0, 4, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.LIME_CONCRETE, 1,
                "§a§l" + viewer.translate("creative", "player.world.members.add"),
                "§7" + viewer.translate("creative", "player.world.members.add.description"));
        }, event -> {
            openAddMember();
        }));

        paginate(1, 0);

        setPreviousPage(4, 0, () -> new PlayerSkullBuilder("Lime Arrow Left", SkullTexture.LIME_ARROW_LEFT, 1, "§7« " + viewer.translate("creative", "player.world.list.paginate")));
        setNextPage(4, 8, () -> new PlayerSkullBuilder("Lime Arrow Right", SkullTexture.LIME_ARROW_RIGHT, 1, "§7" + viewer.translate("creative", "player.world.list.paginate") + " »"));
    }

    @Override
    public Item<CreativePlayer, MutableItemBuilder> getItem(PageItem<CreativeWorldMember> pageItem) {
        return new Item<>(() -> {
            CreativeWorldMember member = pageItem.getObject();

            if (member == null)
                return null;

            UUID uuid = member.getUuid();
            OfflinePlayer offline = OfflinePlayer.get(uuid);
            String name = offline != null ? offline.getName(Name.RAW_COLORED) : uuid.toString();

            return new ItemBuilder(Material.PLAYER_HEAD, 1, name, "", "§c" + viewer.translate("creative", "player.world.members.remove"));
        }, event -> {
            CreativeWorldMember member = pageItem.getObject();

            if (member == null)
                return;

            UUID uuid = member.getUuid();

            world.removeMember(uuid);

            OfflinePlayer offline = OfflinePlayer.get(uuid);
            String name = offline != null ? offline.getName(Name.RAW_COLORED) : uuid.toString();

            viewer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
            viewer.sendMessage("Creative", Color.RED, "creative", "player.world.members.removed", name + "§7");

            update();
        });
    }

    @Override
    public List<CreativeWorldMember> getCollection() {
        return world.getMembers(false);
    }

    @Override
    public Class<CreativeWorldMember> getTypeClass() {
        return CreativeWorldMember.class;
    }

    private void openAddMember() {
        AnvilNms anvil = creative.getNms().anvilGui(viewer.bukkit(), (event) -> {
            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT)
                return;

            String playerName = event.getName();

            PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.NAME.is(playerName));
            if (model == null) {
                viewer.sendMessage("Creative", Color.RED, "creative", "player.world.members.not_found");
                return;
            }

            UUID targetUuid = model.getUUID();

            if (targetUuid.equals(viewer.getUUID())) {
                viewer.sendMessage("Creative", Color.RED, "creative", "player.world.members.self");
                return;
            }

            if (world.getMember(targetUuid, false) != null) {
                viewer.sendMessage("Creative", Color.RED, "creative", "player.world.members.already");
                return;
            }

            event.getAnvilNms().destroy();
            close();

            world.addMember(targetUuid);

            OfflinePlayer offline = OfflinePlayer.get(targetUuid);
            String displayName = offline != null ? offline.getName(Name.RAW_COLORED) : playerName;

            viewer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
            viewer.sendMessage("Creative", Color.FUCHSIA, "creative", "player.world.members.added", displayName + "§7");
        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        WorldMembersGUI.this.open();
                    }
                }.runTaskLater(creative.getPlugin(), 1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new ItemBuilder(Material.PLAYER_HEAD, 1, "Player Name").build());

        SpigotServer.getInstance().runSync(anvil::open);
    }
}
