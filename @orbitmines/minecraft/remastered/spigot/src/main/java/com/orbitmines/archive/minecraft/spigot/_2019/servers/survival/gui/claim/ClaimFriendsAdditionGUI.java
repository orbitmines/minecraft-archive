package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.claim;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaimMember;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;

import java.util.List;

public class ClaimFriendsAdditionGUI extends GUI<SurvivalPlayer> {

    public ClaimFriendsAdditionGUI(Survival survival, SurvivalPlayer viewer, Claim claim, SurvivalClaim.Permission permission, boolean favorites, List<Friend> friends) {
        super(54, "§0§l" + viewer.translate("survival", "player.claim.gui.add_friends.title"), viewer);

        set(2, 4, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(favorites ? Material.ORANGE_STAINED_GLASS_PANE : Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, (favorites ? "§6§l" + viewer.translate("player.claim.gui.add_friends.favorite") : "§b" + viewer.translate("player.claim.gui.add_friends.all")));
        }));

        set(3, 4, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            ItemBuilder item = permission.getIcon().setDisplayName("§7§l" + permission.getName(viewer));

            for (String desc : permission.getDescription(viewer)) {
                item.addLore("§7- " + desc);
            }

            for (SurvivalClaim.Permission perm : SurvivalClaim.Permission.values()) {
                if (permission.ordinal() > perm.ordinal())
                    item.addLore("§7- §l" + perm.getName(viewer));
            }

            return item;
        }));

        Item<SurvivalPlayer, MutableItemBuilder> confirm = new Item<>(() -> {
            return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1, "§a§l" + viewer.translate("survival", "player.claim.gui.confirm"));
        }, event -> {
            for (Friend friend : friends) {
                SurvivalClaimMember member = claim.getMember(friend.getUuid(), false);
                if (member == null) {
                    member = new SurvivalClaimMember(claim.getModel(), friend.getUuid(), permission);
                    claim.getMembers(false).add(member);
                }

                member.insertOrUpdate(SurvivalClaimMember.column.PERMISSION);

                new ClaimGUI(viewer, claim, survival).open();
            }
        });

        Item<SurvivalPlayer, MutableItemBuilder> cancel = new Item<>(() -> {
            return new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1, "§c§l" + viewer.translate("survival", "player.claim.gui.cancel"));
        }, event -> {
            new ClaimGUI(viewer, claim, survival).open();
        });

        for (int i = 0; i < 9; i++) {
            if (i > 2 && i < 6)
                continue;

            for (int j = 1; j < 5; j++) {
                set(j, i, i <= 2 ? confirm : cancel);
            }
        }
    }
}
