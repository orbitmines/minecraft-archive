package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.claim;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;

import java.util.List;

public class ClaimListGUI extends PaginatableGUI<SurvivalPlayer, PlayerInstance, Claim> {

    private Survival survival;

    public ClaimListGUI(Survival survival, SurvivalPlayer viewer, PlayerInstance key) {
        super(36, "§0§l" + key.getRawName() + "'s Claims", viewer, key, 9, 2);
        this.survival = survival;

        paginate(1, 0);

        setPreviousPage(3, 0, () -> new PlayerSkullBuilder("Lime Arrow Left", SkullTexture.LIME_ARROW_LEFT, 1, "§7« " + viewer.translate("survival", "player.claim.gui.list.paginate")));
        setPreviousPage(3, 8, () -> new PlayerSkullBuilder("Lime Arrow Right", SkullTexture.LIME_ARROW_RIGHT, 1, "§7" + viewer.translate("survival", "player.claim.gui.list.paginate") + " »"));
    }

    @Override
    public Item<SurvivalPlayer, MutableItemBuilder> getItem(PageItem<Claim> pageItem) {
        return new Item<>(() -> {
            Claim claim = pageItem.getObject();

            if (claim == null)
                return null;

            ItemBuilder item = ClaimGUI.getClaimIcon(survival, viewer, claim);

            if (!claim.isOwner(viewer.getUUID()) && !survival.canEditOtherClaims(viewer))
                return item;

            item.addLore("")
                .addLore("§a" + viewer.translate("player.claim.gui.list.hover"));

            return item;
        }, event -> {
            Claim claim = pageItem.getObject();

            if (claim == null)
                return;

            if (!claim.isOwner(viewer.getUUID()) && !survival.canEditOtherClaims(viewer))
                return;

            new ClaimGUI(viewer, claim, survival).open();
        });
    }

    @Override
    public List<Claim> getCollection() {
        return survival.getClaimHandler().getClaims(key.getUUID());
    }

    @Override
    public Class<Claim> getTypeClass() {
        return Claim.class;
    }
}
