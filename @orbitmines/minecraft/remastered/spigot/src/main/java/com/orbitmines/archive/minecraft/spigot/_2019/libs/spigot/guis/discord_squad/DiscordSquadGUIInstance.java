package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadInvite;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadMember;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerMessagePublisher;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public interface DiscordSquadGUIInstance<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

    OMDiscordBot getBot();

    default Map<DiscordSquadMember, OnlinePlayer> getOnlineMembers(DiscordSquad squad) {
        Map<DiscordSquadMember, OnlinePlayer> onlineMembers = new HashMap<>();

        for (DiscordSquadMember member : squad.getMembers()) {
            OnlinePlayer player = OnlinePlayer.get(member.getUuid());

            if (player != null)
                onlineMembers.put(member, player);
        }

        return onlineMembers;
    }

    default Map<DiscordSquadMember, OfflinePlayer> getOfflineMembers(DiscordSquad squad) {
        Map<DiscordSquadMember, OfflinePlayer> offlineMembers = new HashMap<>();

        for (DiscordSquadMember member : squad.getMembers()) {
            OnlinePlayer player = OnlinePlayer.get(member.getUuid());

            if (player == null)
                offlineMembers.put(member, OfflinePlayer.get(member.getUuid()));
        }

        return offlineMembers;
    }

    default ItemBuilderInstance asIcon(DiscordSquad squad, ItemBuilderInstance item) {
        Map<DiscordSquadMember, OnlinePlayer> online = getOnlineMembers(squad);
        Map<DiscordSquadMember, OfflinePlayer> offline = getOfflineMembers(squad);

        Map<Server, List<DiscordSquadMember>> onlineMembers = new HashMap<>();
        
        for (DiscordSquadMember member : online.keySet()) {
            onlineMembers.computeIfAbsent(online.get(member).getServer(), k -> new ArrayList<>()).add(member);
        }

        OnlinePlayer onlineOwner = squad.getOnlineOwner();

        if (onlineOwner != null && !onlineMembers.containsKey(onlineOwner.getServer()))
            onlineMembers.put(onlineOwner.getServer(), new ArrayList<>());

        int onlineCount = online.size() + (onlineOwner != null ? 1 : 0);

        /* Add lore */
        item.addLore("§7§o" + onlineCount + " / " + (onlineCount + offline.size()) + " Players Online");
        item.addLore("");

        for (Server server : onlineMembers.keySet()) {
            item.addLore(server.getDisplayName() + "§7(" + onlineMembers.get(server).size() + "):");

            if (onlineOwner != null && onlineOwner.getServer() == server)
                item.addLore(" §7-  §e§l+ " + onlineOwner.getName(Name.RAW_PREFIXED));

            for (DiscordSquadMember member : onlineMembers.get(server)) {
                item.addLore(" §7- " + online.get(member).getName(Name.RAW_PREFIXED));
            }
        }

        if (offline.size() > 0) {
            item.addLore("");
            item.addLore(Server.Status.OFFLINE.getDisplayName() + "§7(" + offline.size() + "):");

            if (onlineOwner == null)
                item.addLore(" §7-  §e§l+ " + squad.getOfflineOwner().getName(Name.RAW_PREFIXED));

            for (DiscordSquadMember member : offline.keySet()) {
                item.addLore(" §7- " + offline.get(member).getName(Name.RAW_PREFIXED));
            }
        }
        return item;
    }

    default boolean isOutgoing(DiscordSquad squad, DiscordSquadInvite invite) {
        return invite.getDiscordSquadId() == squad.getId();
    }

    default boolean anyOutgoingRequests(DiscordSquad squad) {
        return squad.getInvites().size() != 0;
    }

    default void openNamePicker(GUI<P> gui, DiscordSquad squad) {
        AnvilNms anvil = gui.getViewer().server().getNms().anvilGui(gui.getViewer().bukkit(), (event) -> {
            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT)
                return;

            String name = event.getName();

            if (name.length() > DiscordSquad.MAX_NAME_CHARACTERS) {
                gui.getViewer().sendRawMessage("Discord", Color.RED, "You are only allowed to use " + DiscordSquad.MAX_NAME_CHARACTERS + " characters.");
                return;
            }

            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != '_') {
                    gui.getViewer().sendRawMessage("Discord", Color.RED, "You are only allowed to use normal letters, numbers and underscores.");
                    return;
                }
            }

            if (DiscordSquad.exists(getBot(), name)) {
                gui.getViewer().sendMessage("Discord", Color.RED, "spigot", "player.discord_squad.gui.name_taken");
                return;
            }

            event.getAnvilNms().destroy();
            gui.close();

            squad.setName(getBot(), name);
            squad.update(DiscordSquad.column.NAME);

            gui.getViewer().sendMessage("Discord", Color.SUCCESS, "spigot", "player.discord_squad.renamed", squad.getDisplayName() + "§7");

        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        gui.open();
                    }
                }.runTaskLater(gui.getViewer().server().getPlugin(), 1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new PlayerSkullBuilder(squad.getUuid(), 1, squad.getName()).build());

        SpigotServer.getInstance().runSync(anvil::open);
    }

    default void openMemberPicker(GUI<P> gui, DiscordSquad squad) {
        AnvilNms anvil = gui.getViewer().server().getNms().anvilGui(gui.getViewer().bukkit(), (event) -> {
            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT)
                return;

            String playerName = event.getName();
            UUID uuid = UUIDUtils.getUUID(playerName);

            if (uuid == null) {
                gui.getViewer().sendMessage("Discord", Color.RED, "spigot", "player.anvil_gui.unknown_player");
                return;
            }

            PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(uuid));

            if (model == null) {
                gui.getViewer().sendMessage("Discord", Color.RED, "spigot", "player.anvil_gui.unknown_player");
                return;
            }

            event.getAnvilNms().destroy();
            gui.close();

            invite(gui.getViewer(), squad, uuid);
        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        gui.open();
                    }
                }.runTaskLater(gui.getViewer().server().getPlugin(), 1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new PlayerSkullBuilder(squad.getUuid(), 1, gui.getViewer().translate("spigot", "player.friends.anvil_gui.name")).build());

        SpigotServer.getInstance().runSync(anvil::open);
    }
    
    default void invite(P viewer, DiscordSquad squad, UUID uuid) {
        if (viewer.getUUID().equals(uuid)) {
            viewer.sendMessage("Discord", Color.ERROR, "spigot", "player.discord_squad.invite.self");
        } else if (squad.hasInvited(uuid)) {
            String name = OfflinePlayer.get(uuid).getName(Name.RAW_COLORED);

            viewer.sendMessage("Discord", Color.ERROR, "spigot", "player.discord_squad.invite.already_invited", name + "§7");
        } else if (squad.isMember(uuid)) {
            String name = OfflinePlayer.get(uuid).getName(Name.RAW_COLORED);

            viewer.sendMessage("Discord", Color.ERROR, "spigot", "player.discord_squad.invite.already_member", name + "§7");
        } else {
            DiscordSquadInvite invite = new DiscordSquadInvite(uuid, squad.getId(), DateUtils.now());
            invite.insert();

            String name = OfflinePlayer.get(uuid).getName(Name.RAW_COLORED);
            viewer.sendMessage("Discord", Color.LIME, "spigot", "player.discord_squad.invite.sent", name + "§7");

            OnlinePlayer onlinePlayer = OnlinePlayer.get(uuid);
            if (onlinePlayer == null)
                return;

            new PlayerMessagePublisher().publish(
                uuid,
                Message.format("Discord", Color.BLUE, onlinePlayer.getLanguage().getString("spigot", "player.discord_squad.invite.receive", viewer.getName(Name.RAW_COLORED) + "§7", squad.getDisplayName() + "§7"))
            );
        }
    }

    default void revertInvite(DiscordSquad squad, DiscordSquadInvite invite) {
        if (invite.isDestroyed())
            return;

        invite.delete();
    }

    default void acceptInvite(DiscordSquad squad, DiscordSquadInvite invite, P member) {
        if (invite.isDestroyed())
            return;

        invite.delete();

        member.sendMessage("Discord", Color.LIME, "spigot", "player.discord_squad.invite.accept.self", squad.getDisplayName() + "§7");
        squad.sendMessage("Discord", Color.LIME, "spigot", "player.discord_squad.invite.accept", member.getName(Name.RAW_COLORED) + "§7", squad.getDisplayName() + "§7");

        DiscordSquadMember squadMember = new DiscordSquadMember(invite.getUuid(), invite.getDiscordSquadId(), DiscordSquad.getSelected(invite.getUuid()) == null);
        squadMember.insert();

        DiscordUser user = member.getDiscordUser();
        OMDiscordBot bot = getBot();
        String name = member.getRawName() + (user != null && bot != null ?  " (" + user.getDiscordUser(bot).getAsMention() + ")" : "");

        if (bot != null) {
            bot.withPlayerEmote(invite.getUuid(), member.getRawName(), false, emote -> {
                squad.getTextChannel(bot).sendMessage(bot.getRole(CustomRole.JOIN).getAsMention() + " " + bot.getPlayerDisplay(member, emote, name) + " has joined " + squad.getRole(bot).getAsMention() + ".").queue();
            });
        }
    }

    default void leave(DiscordSquad squad, DiscordSquadMember member) {
        if (member.isDestroyed())
            return;

        member.delete();

        PlayerInstance memberPlayer = getMemberPlayer(member);

        squad.sendMessage("Discord", Color.LIME, "spigot", "player.discord_squad.left", memberPlayer.getName(Name.RAW_COLORED) + "§7", squad.getDisplayName() + "§7");

        if (memberPlayer instanceof OnlinePlayer)
            ((OnlinePlayer) memberPlayer).sendMessage("Discord", Color.RED, "player.discord_squad.left.self", squad.getDisplayName() + "§7");

        OMDiscordBot bot = getBot();
        if (bot != null) {
            DiscordUser user = memberPlayer.getDiscordUser();
            String name = memberPlayer.getRawName() + (user != null ?  " (" + user.getDiscordUser(bot).getAsMention() + ")" : "");

            bot.withPlayerEmote(memberPlayer.getUUID(), memberPlayer.getRawName(), false, emote -> {
                squad.getTextChannel(bot).sendMessage(bot.getRole(CustomRole.LEAVE).getAsMention() + " " + bot.getPlayerDisplay(memberPlayer, emote, name) + " has left " + squad.getRole(bot).getAsMention() + ".").queue();
            });
        }
    }

    default void removeMember(DiscordSquad squad, DiscordSquadMember member) {
        if (member.isDestroyed())
            return;

        member.delete();

        PlayerInstance memberPlayer = getMemberPlayer(member);

        squad.sendMessage("Discord", Color.LIME, "spigot", "player.discord_squad.removed", memberPlayer.getName(Name.RAW_COLORED) + "§7", squad.getDisplayName() + "§7");

        if (memberPlayer instanceof OnlinePlayer)
            ((OnlinePlayer) memberPlayer).sendMessage("Discord", Color.RED, "player.discord_squad.removed.self", squad.getDisplayName() + "§7");

        OMDiscordBot bot = getBot();
        if (bot != null) {
            DiscordUser user = memberPlayer.getDiscordUser();
            String name = memberPlayer.getRawName() + (user != null ?  " (" + user.getDiscordUser(bot).getAsMention() + ")" : "");

            bot.withPlayerEmote(memberPlayer.getUUID(), memberPlayer.getRawName(), false, emote -> {
                squad.getTextChannel(bot).sendMessage(bot.getRole(CustomRole.LEAVE).getAsMention() + " " + bot.getPlayerDisplay(memberPlayer, emote, name) + " has been removed from " + squad.getRole(bot).getAsMention() + ".").queue();
            });
        }
    }

    default PlayerInstance getMemberPlayer(DiscordSquadMember member) {
        PlayerInstance memberPlayer = OnlinePlayer.get(member.getUuid());
        if (memberPlayer == null)
            memberPlayer = OfflinePlayer.get(member.getUuid());

        return memberPlayer;
    }

    default void rejectInvite(DiscordSquad squad, DiscordSquadInvite invite, P member) {
        if (invite.isDestroyed())
            return;

        invite.delete();

        OnlinePlayer onlineOwner = squad.getOnlineOwner();
        if (onlineOwner == null)
            return;

        onlineOwner.sendMessage("Discord", Color.INFO, "spigot", "player.discord_squad.invite.rejected", member.getName(Name.RAW_COLORED) + "§7");
    }
}
