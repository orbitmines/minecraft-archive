package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.claim;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaimMember;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;

public class ClaimPlayerDetailsGUI extends GUI<SurvivalPlayer> {

    private final Survival survival;
    private final Claim claim;
    private final SurvivalClaimMember playerMember;
    private final PlayerInstance player;

    public ClaimPlayerDetailsGUI(Survival survival, SurvivalPlayer viewer, Claim claim, SurvivalClaimMember playerMember, PlayerInstance player) {
        super(27, "§0§lClaim - " + player.getRawName(), viewer);
        this.survival = survival;
        this.claim = claim;
        this.playerMember = playerMember;
        this.player = player;


        int index = 0;
        for (SurvivalClaim.Permission permission : SurvivalClaim.Permission.values()) {
            set(1, (playerMember.getPermission() != null ? 1 : 2) + index, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
                SurvivalClaim.Permission current = playerMember.getPermission();

                String color = current == permission ? "§a" : "§7";
                ItemBuilder item = permission.getIcon().setDisplayName(color + "§l" + permission.getName(viewer));

                if (current == permission)
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
                SurvivalClaim.Permission current = playerMember.getPermission();

                playerMember.setPermission(permission);

                if (!playerMember.isInserted()) {
                    claim.getMembers(false).add(playerMember);
                    playerMember.insert();
                } else {
                    playerMember.update(SurvivalClaimMember.column.PERMISSION);
                }

                if (current != null)
                    update();
                else
                    /* Added for the first time, so back to claim */
                    new ClaimGUI(viewer, claim, survival).open();
            }));

            index++;
        }

        set(1, playerMember.getPermission() != null ? 5 : 6, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder(player.getUUID(), 1, "§a§l" + viewer.translate("survival", "player.claim.gui.back_to_claim", claim.getName()));
        }, event -> {
            new ClaimGUI(viewer, claim, survival).open();
        }));

        if (playerMember.getPermission() != null)
            set(1, 7, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
                return new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("survival", "player.claim.gui.remove_from_claim"));
            }, event -> {
                playerMember.delete();
                claim.getMembers(false).remove(playerMember);

                new ClaimGUI(viewer, claim, survival).open();
            }));
    }
}
