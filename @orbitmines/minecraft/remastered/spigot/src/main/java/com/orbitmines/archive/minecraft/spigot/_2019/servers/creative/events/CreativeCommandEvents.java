package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.CommandEvents;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CreativeCommandEvents extends CommandEvents<Creative, CreativePlayer> {

    private final Set<String> worldAccessCommands = new HashSet<>();

    /*
     * After stripping the leading / from the message:
     *   //set  → /set   (double-slash commands keep one /)
     *   /brush → brush  (single-slash commands lose their /)
     */
    private final Set<String> faweCommands = new HashSet<>(Arrays.asList(
        /* WorldEdit core */
        "we", "fawe",

        /* Utility */
        "remove", "/fill", "/drain", "transforms", "/removenear", "/fixlava",
        "/removeabove", "masks", "/fillr", "patterns", "/replacenear", "/snow",
        "/thaw", "/removebelow", "/fixwater", "butcher", "/confirm", "/green",
        "/calc", "/ex", "heightmapinterface",

        /* Region */
        "/replace", "/stack", "/set", "/fall", "/faces", "/hollow", "/center",
        "/setskylight", "/nbtinfo", "/setblocklight", "/curve", "/overlay", "/lay",
        "/naturalize", "/walls", "/getlighting", "/removelight", "/fixlighting",
        "/smooth", "/line", "/regen", "/wea", "/move", "/forest", "/deform",
        "/flora", "/wer",

        /* Selection */
        "/count", "/size", "/expand", "/shift", "/sel", "/contract", "/pos2",
        "/pos1", "/chunk", "/hpos1", "/outset", "/wand", "toggleeditwand",
        "/hpos2", "/inset", "/distr",

        /* History */
        "/clearhistory", "/undo", "/redo", "/inspect", "/frb",

        /* Schematic */
        "schematic", "schem",

        /* Clipboard */
        "/copy", "/flip", "/rotate", "/lazycopy", "asset", "/cut", "download",
        "/paste", "/lazycut", "/place", "clearclipboard",

        /* Generation */
        "/image", "/generate", "/pyramid", "/sphere", "/cyl", "pumpkins",
        "/hsphere", "/hcyl", "/caves", "/ore", "forestgen", "/hpyramid",
        "/ores", "/generatebiome",

        /* Biome */
        "/setbiome", "biomelist", "biomeinfo",

        /* Super Pickaxe */
        "sp",

        /* Navigation */
        "unstuck", "thru", "jumpto", "up", "ascend", "ceil", "descend",

        /* Snapshot */
        "snapshot", "restore",

        /* Scripting */
        "cs", ".s",

        /* Chunk */
        "chunkinfo", "delchunks", "listchunks",

        /* Options */
        "/fast", "/gsmask", "/gtransform", "/toggleplace", "/searchitem",
        "/gmask", "/tips",

        /* Brush options */
        "target", "size", "/listbrush", "range", "mask", "transform", "mat",
        "loadbrush", "smask", "visualize", "/", "targetmask", "targetoffset",
        "primary", "none", "secondary", "savebrush", "scroll",

        /* Tool */
        "tool",

        /* Brush */
        "brush",

        /* CFI */
        "cfi",

        /* Misc */
        "/cancel"
    ));

    private final Set<String> otherAccessCommands = new HashSet<>(Arrays.asList(
        "time"
    ));

    public CreativeCommandEvents(Creative plugin) {
        super(plugin);

        worldAccessCommands.addAll(faweCommands);
        worldAccessCommands.addAll(otherAccessCommands);
    }

    private static final Set<String> SCHEMATIC_CMDS = Set.of("schematic", "schem");
    private static final Set<String> SCHEMATIC_NAME_SUBS = Set.of("save", "load", "delete", "show", "unload", "move");

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSchematicCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled())
            return;

        String msg = event.getMessage();
        String[] parts = msg.split("\\s+");
        /* /schematic or //schematic — after stripping one / we get "schematic" or "/schematic" */
        String cmd = parts[0].substring(1).toLowerCase();
        if (!SCHEMATIC_CMDS.contains(cmd) && !SCHEMATIC_CMDS.contains(cmd.substring(1)))
            return;

        if (parts.length < 2)
            return;

        String sub = parts[1].toLowerCase();
        String uuid = event.getPlayer().getUniqueId().toString();

        if (SCHEMATIC_NAME_SUBS.contains(sub)) {
            if (parts.length < 3)
                return;

            /* Prefix the last argument (the filename) with UUID_ */
            parts[parts.length - 1] = uuid + "_" + parts[parts.length - 1];
            event.setMessage(String.join(" ", parts));
            return;
        }

        if (sub.equals("list")) {
            event.setCancelled(true);
            CreativePlayer player = plugin.getPlayer(event.getPlayer());
            if (player == null)
                return;

            File schemDir = new File("plugins/FastAsyncWorldEdit/schematics");
            if (!schemDir.isDirectory()) {
                player.sendMessage("Creative", Color.RED, "creative", "player.schematic.none");
                return;
            }

            String prefix = uuid + "_";
            File[] files = schemDir.listFiles((dir, name) -> name.startsWith(prefix));
            if (files == null || files.length == 0) {
                player.sendMessage("Creative", Color.RED, "creative", "player.schematic.none");
                return;
            }

            Arrays.sort(files);
            player.sendMessage("Creative", Color.FUCHSIA, "creative", "player.schematic.list");
            for (File file : files) {
                String name = file.getName().substring(prefix.length());
                /* Strip extension (.schem, .schematic) */
                int dot = name.lastIndexOf('.');
                if (dot > 0)
                    name = name.substring(0, dot);

                player.bukkit().sendMessage("§8 - §d" + name);
            }
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onWorldAccessCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled())
            return;

        String cmd = event.getMessage().split(" ")[0].substring(1).toLowerCase();
        if (!worldAccessCommands.contains(cmd))
            return;

        CreativePlayer player = plugin.getPlayer(event.getPlayer());
        if (player != null && player.isOpMode())
            return;

        CreativeWorld world = plugin.getWorldByBukkitWorld(event.getPlayer().getWorld());

        if (world == null || !world.canBuild(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);

            if (player != null) {
                String ownerName = world != null ? world.getOwnerName() : "";
                new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("creative", "player.world.protection.cant_build", ownerName + "§c§l"), 60).send();
            }
        }
    }

    @Override
    protected Set<String> getAllCommands() {
        Set<String> allCommands = super.getAllCommands();
        allCommands.addAll(worldAccessCommands);

        return allCommands;
    }
}
