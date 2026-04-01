package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints;

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.PatchNotes;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.FloatingItem;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointSign;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class DataPointPatchNotes<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends DataPointSign {

    private S server;

    private List<FloatingItem> items;

    public DataPointPatchNotes(S server) {
        super("PATCH_NOTES", Type.IRON_PLATE, Material.BROWN_WOOL);

        this.server = server;
        items = new ArrayList<>();
    }

    @Override
    public boolean buildAt(DataPointLoader loader, Location location, String[] data) {
        new BukkitRunnable() {
            @Override
            public void run() {
                FloatingItem<P> item = new FloatingItem<>(() -> new ItemBuilder(Material.WRITABLE_BOOK).build(), location.add(0.5, 0, 0.5));

                item.addLine(() -> "§c§lPATCH NOTES", false);

                Server server = DataPointPatchNotes.this.server.getType();
                PatchNotes patchNotes = DataPointPatchNotes.this.server.getPatchNotes();

                List<PatchNotes.Instance> latest = server == Server.HUB ? patchNotes.getLatest() : Collections.singletonList(patchNotes.getLatest(server));

                for (PatchNotes.Instance instance : latest) {
                    item.addLine(() -> (instance.isNew() ? "§c§lNew!§r " : "") + "§7" + instance.getVersion() + " \"" + instance.getName() + "\"" + (server == Server.HUB && instance.getServer() != Server.HUB ? " §7(" + instance.getServer().getDisplayName() + "§r§7)" : ""), false);
                }

//                if (server == Server.HUB)
//                    item.setInteractAction(((event, omp) -> patchNotes.getHubInstance().open(patchNotes, omp)));
//                else
                    item.setInteractAction((event, omp) -> patchNotes.getLatest(server).open(patchNotes, omp));

                item.create();

                items.add(item);
            }
        }.runTaskLater(server.getPlugin(), 1);

        return true;
    }

    @Override
    public boolean setup() {
        return true;
    }

    public List<FloatingItem> getItems() {
        return items;
    }
}
