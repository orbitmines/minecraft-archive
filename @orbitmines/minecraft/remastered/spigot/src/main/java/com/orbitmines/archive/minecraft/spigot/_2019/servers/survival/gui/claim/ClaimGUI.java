package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.claim;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.IPEntry;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaimMember;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ClaimGUI extends PaginatableGUI<SurvivalPlayer, Claim, SurvivalClaimMember> {

    private Survival survival;
    private SurvivalClaim.Permission permission;

    private Map<UUID, OnlinePlayer> onlineMembers;

    public ClaimGUI(SurvivalPlayer viewer, Claim key, Survival survival) {
        this(viewer, key, survival, null);
    }

    public ClaimGUI(SurvivalPlayer viewer, Claim key, Survival survival, SurvivalClaim.Permission permission) {
        super(54, "§0§l" + key.getName(), viewer, key, 9, 2);
        this.survival = survival;
        this.permission = permission;

        this.onlineMembers = new HashMap<>();

        set(0, 4, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return getClaimIcon(survival, viewer, key).
                addLore("").
                addLore("§a" + viewer.translate("survival", "player.claim.gui.rename"));
        }, event -> {
            openNamePicker();
        }));

        if (survival.canEditOtherClaims(viewer)) {
            set(0, 5, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
                return new ItemBuilder(Material.ENDER_PEARL, 1, "§3§l" + viewer.translate("survival", "player.claim.gui.teleport_to"));
            }, event -> {
                int x1 = key.getCorner1().getBlockX();
                int x2 = key.getCorner2().getBlockX();
                int z1 = key.getCorner1().getBlockZ();
                int z2 = key.getCorner2().getBlockZ();

                int width = (Math.abs(x1 - x2) + 1);
                int height = (Math.abs(z1 - z2) + 1);

                int x = x1 > x2 ? x1 - (width / 2) : x1 + (width / 2);
                int z = z1 > z2 ? z1 - (height / 2) : z1 + (height / 2);

                survival.runSync(() -> {
                    int y = key.getCorner1().getWorld().getHighestBlockYAt(x, z) + 1;

                    viewer.teleport(new Location(key.getCorner1().getWorld(), x, y, z));
                });

                viewer.sendMessage("Claim", Color.LIME, "survival", "player.teleported_to", "§a§lClaim #" + key.getId());

                viewer.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);
            }));
        }

        set(0, 1, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.WRITABLE_BOOK, 1, "§a§l" + viewer.translate("survival", "player.claim.gui.add_player"));
        }, event -> {
            openMemberPicker();
        }));

        set(0, 7, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("survival", "player.claim.gui.remove"));
        }, event -> {
            new ClaimRemovalGUI(survival, viewer, key).open();
        }));

        setPermissions();

        paginate(3, 0);

        setAddFriends(5, 3, false);
        setAddFriends(5, 5, true);
        
        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Lime Arrow Left", SkullTexture.LIME_ARROW_LEFT, 1, "§7« " + viewer.translate("survival", "player.claim.gui.paginate")));
        setPreviousPage(5, 8, () -> new PlayerSkullBuilder("Lime Arrow Right", SkullTexture.LIME_ARROW_RIGHT, 1, "§7" + viewer.translate("survival", "player.claim.gui.paginate") + " »"));
    }

    @Override
    public void beforeUpdateAsync() {
        updateOnlineMembers();
    }

    private void setPermissions() {
        int index = 0;
        for (SurvivalClaim.Permission permission : SurvivalClaim.Permission.values()) {
            set(2, 3 + index, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
                String color = this.permission == permission ? "§a" : "§7";
                ItemBuilder item = permission.getIcon().setDisplayName(color + "§l" + permission.getName(viewer));

                if (this.permission == permission)
                    item.glow();

                for (String desc : permission.getDescription(viewer)) {
                    item.addLore("§7- " + color + desc);
                }

                for (SurvivalClaim.Permission perm : SurvivalClaim.Permission.values()) {
                    if (permission.ordinal() > perm.ordinal())
                        item.addLore("§7- " + color + "§l" + perm.getName(viewer));
                }

                return item;
            }, event -> {

                if (ClaimGUI.this.permission == permission)
                    ClaimGUI.this.permission = null;
                else
                    ClaimGUI.this.permission = permission;

                viewer.playSound(Sound.UI_BUTTON_CLICK);

                update();
            }));

            index++;
        }
    }

    @Override
    public Item<SurvivalPlayer, MutableItemBuilder> getItem(PageItem<SurvivalClaimMember> pageItem) {
        return new Item<>(() -> {
            SurvivalClaimMember member = pageItem.getObject();

            if (member == null)
                return null;

            OnlinePlayer onlineMember = getOnlineFriend(member.getUuid());
            PlayerInstance memberInstance = onlineMember != null ? onlineMember : OfflinePlayer.get(member.getUuid());

            ItemBuilderInstance builder;
            if (onlineMember != null)
                builder = new PlayerSkullBuilder(memberInstance.getUUID(), 1, memberInstance.getName(Name.RAW_COLORED));
            else
                builder = new ItemBuilder(Material.WITHER_SKELETON_SKULL, 1, memberInstance.getName(Name.RAW_COLORED));

            SurvivalClaim.Permission permission = member.getPermission();

            if (permission != null)
                builder.addLore("§7Trust: §a§l" + permission.getName(viewer));

            builder.addLore("");

            if (onlineMember != null) {
                Server server = onlineMember.getServer();

                builder.addLore("§7Status: " + Server.Status.ONLINE.getDisplayName());
                builder.addLore("§7Server: " + server.getDisplayName());
            } else {
                IPEntry entry = memberInstance.getLastIPEntry();

                if (entry != null) {
                    Date lastSeen = entry.getLogoutAt() != null ? entry.getLogoutAt() : entry.getLoginAt();

                    builder.addLore("§7" + viewer.translate("spigot", "player.friends.last_seen", "§b§l" + TimeUtils.humanFriendlyTimer(viewer.getLanguage(), System.currentTimeMillis() - lastSeen.getTime()) + "§7"));
                }
            }
            builder.addLore("");
            builder.addLore("§a" + viewer.translate("survival", "player.claim.gui.player.hover"));

            return builder;
        }, event -> {
            SurvivalClaimMember member = pageItem.getObject();

            if (member == null)
                return;
            
            new ClaimPlayerDetailsGUI(survival, viewer, key, member, OfflinePlayer.get(member.getUuid())).open();
        });
    }

    @Override
    public List<SurvivalClaimMember> getCollection() {
        List<SurvivalClaimMember> sorted = new ArrayList<>();
        for (UUID uuid : this.onlineMembers.keySet()) {
            sorted.add(this.key.getMember(uuid, false));
        }

        for (SurvivalClaimMember member : this.key.getMembers(false)) {
            if (!sorted.contains(member))
                sorted.add(member);
        }
        return sorted;
    }
    
    private OnlinePlayer getOnlineFriend(UUID member) {
        return this.onlineMembers.get(member);
    }

    @Override
    public Class<SurvivalClaimMember> getTypeClass() {
        return SurvivalClaimMember.class;
    }

    private void setAddFriends(int row, int slot, boolean favorite) {
        set(row, slot, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            if (this.permission == null)
                return null;

            List<Friend> friends = new ArrayList<>(favorite ? viewer.getFavoriteFriends(false) : viewer.getFriends(false));

            for (Friend friend : friends) {
                if (this.key.hasPermission(friend.getFriendUuid(), this.permission))
                    friends.remove(friend);
            }

            if (friends.size() == 0)
                return null;

            ItemBuilder item = new ItemBuilder(favorite ? Material.ORANGE_STAINED_GLASS_PANE : Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, (favorite ? "§6§l" + viewer.translate("player.claim.gui.add_friends.favorite") : "§b" + viewer.translate("player.claim.gui.add_friends.all")));

            int showCount = 5;
            for (int i = 0; i < showCount; i++) {
                if (friends.size() < i + 1)
                    continue;

                OfflinePlayer player = OfflinePlayer.get(friends.get(i).getFriendUuid());
                item.addLore("§7- " + player.getName(Name.RAW_COLORED));
            }

            if (friends.size() > showCount)
                item.addLore("§7" + viewer.translate("survival", "player.claim.gui.and_more", (friends.size() - showCount)));

            return item;
        }, event -> {
            if (this.permission == null)
                return;

            List<Friend> friends = new ArrayList<>(favorite ? viewer.getFavoriteFriends(false) : viewer.getFriends(false));

            for (Friend friend : friends) {
                if (this.key.hasPermission(friend.getFriendUuid(), this.permission))
                    friends.remove(friend);
            }

            if (friends.size() == 0)
                return;

            new ClaimFriendsAdditionGUI(survival, viewer, this.key, ClaimGUI.this.permission, favorite, friends).open();
        }));
    }

    private void openNamePicker() {
        AnvilNms anvil = survival.getNms().anvilGui(viewer.bukkit(), (event) -> {
            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT) {
                return;
            }

            String claimName = event.getName();

            if (claimName.length() > 20) {
                viewer.sendMessage("Claim", Color.RED, "survival", "player.claim.gui.max_characters");
                return;
            }

            for (int i = 0; i < claimName.length(); i++) {
                char c = claimName.charAt(i);
                if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != '_' && c != ' ') {
                    viewer.sendRawMessage("Claim", Color.RED, "You are only allowed to use normal letters, numbers, underscores and spaces.");
                    return;
                }
            }

            event.getAnvilNms().destroy();
            close();

            key.setName(claimName);
            key.update(SurvivalClaim.column.NAME);

            viewer.sendRawMessage("Claim", Color.SUCCESS, "Successfully changed claim name to '§a" + claimName + "'.");
        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        open();
                    }
                }.runTaskLater(survival.getPlugin(),1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new ItemBuilder(Material.STONE_HOE, 1, key.getName()).build());

        SpigotServer.getInstance().runSync(anvil::open);
    }

    private void openMemberPicker() {
        AnvilNms anvil = survival.getNms().anvilGui(viewer.bukkit(), (event) -> {
            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT) {
                return;
            }

            String playerName = event.getName();
            UUID uuid = UUIDUtils.getUUID(playerName);

            if (uuid == null) {
                viewer.sendMessage("Claim", Color.RED, "survival", "player.claim.gui.cannot_find_player");
                return;
            }

            if (uuid.equals(viewer.getUniqueId())) {
                viewer.sendMessage("Claim", Color.RED, "survival", "player.claim.gui.cannot_add_self");
                return;
            }

            OfflinePlayer player = OfflinePlayer.get(uuid);
            event.getAnvilNms().destroy();
            close();

            new BukkitRunnable() {
                @Override
                public void run() {
                    SurvivalClaimMember member = key.getMember(uuid, false);
                    if (member == null)
                        member = new SurvivalClaimMember(key.getModel(), uuid, null);

                    new ClaimPlayerDetailsGUI(survival, viewer, key, member, player).open();
                }
            }.runTaskLater(survival.getPlugin(),2);
        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        open();
                    }
                }.runTaskLater(survival.getPlugin(),1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new PlayerSkullBuilder(viewer.getUUID(), 1, viewer.translate("spigot", "player.friends.anvil_gui.name")).build());

        SpigotServer.getInstance().runSync(anvil::open);
    }

    private void updateOnlineMembers() {
        SurvivalClaimMember[][] members = getPage();

        List<UUID> memberUUIDs = new ArrayList<>();
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                SurvivalClaimMember member = members[row][column];

                if (member != null)
                    memberUUIDs.add(member.getUuid());
            }
        }

        this.onlineMembers = OnlinePlayer.getFromUUIDList(memberUUIDs);
    }

    public static ItemBuilder getClaimIcon(Survival survival, SurvivalPlayer viewer, Claim claim) {
        int x1 = claim.getCorner1().getBlockX();
        int x2 = claim.getCorner2().getBlockX();
        int z1 = claim.getCorner1().getBlockZ();
        int z2 = claim.getCorner2().getBlockZ();

        int width = (Math.abs(x1 - x2) + 1);
        int height = (Math.abs(z1 - z2) + 1);
        int area = width * height;

        int x = x1 > x2 ? x1 - (width / 2) : x1 + (width / 2);
        int z = z1 > z2 ? z1 - (height / 2) : z1 + (height / 2);

        World world = claim.getCorner1().getWorld();
        String worldName;
        if (world.equals(survival.getWorld()))
            worldName = "§a§lOverworld";
        else if (world.equals(survival.getWorldNether()))
            worldName = "§c§lThe Nether";
        else if (world.equals(survival.getWorldTheEnd()))
            worldName = "§8§lThe End";
        else
            worldName = "§f§lUnknown";

        String color = ChatColor.getLastColors(worldName);

        return new ItemBuilder(Material.STONE_HOE, 1, "§a§l" + claim.getName(),
                "§7Claim Id: §a§l#" + NumberUtils.locale(claim.getId()),
                "§7" + viewer.translate("survival", "player.claim.gui.icon.created_by", claim.getOwnerName()),
                "§7" + viewer.translate("survival", "player.claim.gui.icon.created_on", "§a§l" + DateUtils.format(claim.getCreatedOn(), DateUtils.DATE_FORMAT)),
                "",
                "§7" + viewer.translate("survival", "player.claim.gui.icon.area", "§9§l" + NumberUtils.locale(width), NumberUtils.locale(height)),
                "§7" + viewer.translate("survival", "player.claim.gui.icon.blocks", "§9§l" + NumberUtils.locale(area)),
                "§7" + viewer.translate("survival", "player.claim.gui.icon.world", worldName),
                "§7XZ: " + color + NumberUtils.locale(x) + " §7/ " + color + NumberUtils.locale(z)
        ).addFlag(ItemFlag.HIDE_ATTRIBUTES);
    }
}
