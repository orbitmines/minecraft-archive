package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region.Region;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Collections;

public class RegionGUI extends GUI<SurvivalPlayer> {

    private int x;
    private int y;

    public RegionGUI(Survival survival, SurvivalPlayer viewer) {
        this(survival, viewer, 0, 0);
    }

    public RegionGUI(Survival survival, SurvivalPlayer viewer, int x, int y) {
        super(45, "§0§lRegion Teleporter", viewer);

        this.x = x;
        this.y = y;

        for (int slot = 0; slot < 9; slot++) {
            for (int row = 0; row < 5; row++) {
                int sX = slot;
                int sY = row;

                set(row, slot, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
                    int inventoryX = sX - 4 + this.x;
                    int inventoryY = sY - 2 + this.y;

                    Region region = Region.getRegion(inventoryX, inventoryY);

                    if (region == null)
                        return new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, viewer.translate("survival", "player.region.unknown"));

                    Navigator navigator = Navigator.from(sY, sX);

                    if (navigator != null && navigator.canExpand(inventoryX, inventoryY))
                        return new PlayerSkullBuilder("Arrow " + navigator.toString(), navigator.texture, 1, "§a§l" + viewer.translate("survival", "player.region.paginate"));

                    if (region.getId() > Region.TELEPORTABLE)
                        return region.getIcon().
                            setMaterial(Material.BLACK_STAINED_GLASS_PANE).
                            setDisplayName("§8§lRegion §a§l" + region.getId()).
                            setLore(Collections.singletonList(" §8§l" + viewer.translate("survival", "player.region.unknown")));

                    return region.getIcon();
                }, event -> {
                    int inventoryX = sX - 4 + this.x;
                    int inventoryY = sY - 2 + this.y;

                    Region region = Region.getRegion(inventoryX, inventoryY);
                    Navigator navigator = Navigator.from(sY, sX);

                    if (navigator != null && navigator.canExpand(inventoryX, inventoryY)) {
                        this.x -= navigator.getXOff();
                        this.y -= navigator.getYOff();
                        update();
                        return;
                    }

                    if (region.getId() > Region.TELEPORTABLE)
                        return;

                    close();

                    if (viewer.getWorld() == survival.getLobby().getWorld())
                        survival.runSync(() -> viewer.teleport(region.getLocation()));
                    else
                        region.teleport(viewer);
                }));
            }
        }
    }

    private enum Navigator {

        UP(0, 1, 0, 4, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRhMDI3NDc3MTk3YzZmZDdhZDMzMDE0NTQ2ZGUzOTJiNGE1MWM2MzRlYTY4YzhiN2JjYzAxMzFjODNlM2YifX19"),
        DOWN(0, -1, 4, 4, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY3NDE2Y2U5ZTgyNmU0ODk5YjI4NGJiMGFiOTQ4NDNhOGY3NTg2ZTUyYjcxZmMzMTI1ZTAyODZmOTI2YSJ9fX0="),
        LEFT(1, 0, 2, 0, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU1MGI3Zjc0ZTllZDc2MzNhYTI3NGVhMzBjYzNkMmU4N2FiYjM2ZDRkMWY0Y2E2MDhjZDQ0NTkwY2NlMGIifX19"),
        RIGHT(-1, 0, 2, 8, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTYzMzlmZjJlNTM0MmJhMThiZGM0OGE5OWNjYTY1ZDEyM2NlNzgxZDg3ODI3MmY5ZDk2NGVhZDNiOGFkMzcwIn19fQ=="),
        UP_LEFT(1, 1, 0, 0, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MyNGVjYTIwOGY3ZGQ2ZWVlZGNiOGI4MWE0MmZkY2Q4NmMxMWEyZWIzNzdmZWJjZDZkNTQ1MGFkNmJiMCJ9fX0="),
        UP_RIGHT(-1, 1, 0, 8, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY2Yjg1ZjYyNjQ0NGRiZDViZGRmN2E1MjFmZTUyNzQ4ZmU0MzU2NGUwM2ZiZDM1YjZiNWU3OTdkZTk0MmQifX19"),
        DOWN_LEFT(1, -1, 4, 0, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM2NThmOWVkMjE0NWVmODMyM2VjOGRjMjY4ODE5N2M1ODk2NDUxMGYzZDI5OTM5MjM4Y2UxYjZlNDVhZjBmZiJ9fX0="),
        DOWN_RIGHT(-1, -1, 4, 8, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjVhM2U1Mjg1NjU3NGE3YzgzY2QxMThiMWExMzYwYTdhOGJlODVjOGE0YzZmODZlMDY4ZGM0ZjRiNTA2YjY2In19fQ==");

        @Getter private final int xOff;
        @Getter private final int yOff;

        @Getter private final int row;
        @Getter private final int slot;

        @Getter private final String texture;

        Navigator(int xOff, int yOff, int row, int slot, String texture) {
            this.xOff = xOff;
            this.yOff = yOff;
            this.row = row;
            this.slot = slot;
            this.texture = texture;
        }

        public boolean canExpand(int inventoryX, int inventoryY) {
            return Region.getRegion(inventoryX - xOff, inventoryY - yOff) != null;
        }

        public static Navigator from(int row, int slot) {
            for (Navigator navigator : Navigator.values()) {
                if (navigator.row == row && navigator.slot == slot)
                    return navigator;
            }
            return null;
        }
    }
}
