package com.orbitmines.archive.minecraft.spigot._2019.servers.build.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.CommandEvents;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BuildCommandEvents extends CommandEvents<Build, BuildPlayer> {

    private Set<String> worldEditCommands = new HashSet<>(Arrays.asList(
        "/limit",
        "/undo",
        "/redo",
        "clearhistory",
        "/wand",
        "toggleeditwand",
        "/sel",
        "/desel",
        "/pos1",
        "/pos2",
        "/hpos1",
        "/hpos2",
        "/chunk",
        "/expand",
        "/contract",
        "/outset",
        "/inset",
        "/shift",
        "/size",
        "/count",
        "/distr",
        "/set",
        "/replace",
        "/overlay",
        "/walls",
        "/outline",
        "/center",
        "/smooth",
        "/deform",
        "/hollow",
        "/regen",
        "/move",
        "/stack",
        "/naturalize",
        "/line",
        "/curve",
        "/forest",
        "/flora",
        "/copy",
        "/cut",
        "/paste",
        "/rotate",
        "/flip",
        "/schematic",
        "/schem",
        "clearclipboard",
        "/generate",
        "/generatebiome",
        "/hcyl",
        "/cyl",
        "/sphere",
        "/hsphere",
        "/pyramid",
        "/hpyramid",
        "forestgen",
        "pumpskins",
        "toggleplace",
        "/fill",
        "/fillr",
        "/drain",
        "fixwater",
        "fixlava",
        "removeabove",
        "removebelow",
        "replacenear",
        "removenear",
        "snow",
        "thaw",
        "ex",
        "butcher",
        "remove",
        "green",
        "/calc",
        "chunkinfo",
        "listchunks",
        "delchunks",
        "/",
        "sp",
        "tool",
        "none",
        "info",
        "farwand",
        "lrbuild",
        "tree",
        "deltree",
        "repl",
        "cycler",
        "flood",
        "brush",
        "size",
        "size",
        "range",
        "mask",
        "/gmask",
        "unstuck",
        "ascend",
        "descend",
        "/ceil",
        "thru",
        "jumpto",
        "up",
        "/restore",
        "/snapshot",
        "cs",
        ".s",
        "searchitem",
        "worldedit",
        "/fast",
        "biomelist",
        "biomeinfo",
        "/setbiome"
    ));

    public BuildCommandEvents(Build plugin) {
        super(plugin);
    }

    @Override
    protected Set<String> getAllCommands() {
        Set<String> allCommands = super.getAllCommands();
        allCommands.addAll(worldEditCommands);

        return allCommands;
    }
}
