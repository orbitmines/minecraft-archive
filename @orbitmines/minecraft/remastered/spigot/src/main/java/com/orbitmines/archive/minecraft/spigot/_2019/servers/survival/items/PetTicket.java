package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.*;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemHoverActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Tameable;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PetTicket extends ItemHoverActionBar<SurvivalPlayer> {

    private final Survival server;

    public PetTicket(Survival server) {
        super(server, Survival.PET_TICKET, true, (player, item) -> {
            UUID storedUuid = server.getNms().customItem().getMetaData(item).getAsUUID("orbitmines_survival", "stored_uuid");

            if (storedUuid == null)
                return "§e§l" + player.translate("survival", "player.pet_ticket.linking");

            Tameable entity = EntityUtils.getEntityByUUID(storedUuid, Tameable.class);

            if (entity == null)
                return "";

            int blocks = (int) VectorUtils.distance2D(player.getLocation().toVector(), entity.getLocation().toVector());

            if (blocks <= 5)
                return "§e§l" + player.translate("survival", "player.pet_ticket.close");

            BlockFace face = BlockUtils.getFacing(player.getLocation(), entity.getLocation());

            StringBuilder stringBuilder = new StringBuilder();
            String[] parts = face.toString().split("_");

            for (int i = 0; i < parts.length; i++) {
                if (i != 0)
                    stringBuilder.append(" ");

                stringBuilder.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
            }

            return player.getWorld().equals(entity.getWorld()) ? "§6§l" + NumberUtils.locale(blocks) + " Blocks§e§l, §6§l" + stringBuilder.toString() : "§c§l" + player.translate("survival", "player.pet_ticket.not_in_your_world");
        });

        this.server = server;
    }

    @Override
    public void onEnter(SurvivalPlayer player, ItemStack item, int slot) {
        super.onEnter(player, item, slot);

        ItemStackNms nms = server.getNms().customItem();

        UUID storedUuid = nms.getMetaData(item).getAsUUID("orbitmines_survival", "stored_uuid");

        if (storedUuid == null)
            return;

        Tameable entity = EntityUtils.getEntityByUUID(storedUuid, Tameable.class);

        if (entity != null && !entity.getLocation().getChunk().isLoaded())
            entity.getLocation().getChunk().load();

        if (entity == null || entity.isDead() || !nms.getMetaData(item).getAsString("orbitmines_survival", "owner_uuid").equals(entity.getOwner().getUniqueId().toString())) {
            server.runSync(() -> player.getInventory().setItem(player.getInventory().first(item), null));
            PlayerUtils.updateInventory(player.bukkit());

            player.sendMessage("Pet Ticket", Color.LIME, "player.pet_ticket.expired", Survival.PET_TICKET.getDisplayName() + "§7");

            leave(player);
        }
    }
}
