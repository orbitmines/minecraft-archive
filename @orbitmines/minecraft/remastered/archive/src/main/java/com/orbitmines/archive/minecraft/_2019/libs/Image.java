package com.orbitmines.archive.minecraft._2019.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public enum Image {

    ORBITMINES_ICON("https://i.imgur.com/E1oDT11.png"),
    ORBITMINES_ICON_COMPRESSED("https://i.imgur.com/ejRfzwa.png"),
    ORBITMINES_LOGO("https://i.imgur.com/SX8L7Qi.png"),

    SURVIVAL_ICON("https://i.imgur.com/lRqhPy1.png"),
    SURVIVAL_ICON_COMPRESSED("https://i.imgur.com/mmPu17X.png"),
    KITPVP_ICON("https://i.imgur.com/P2ytvwc.png"),
    KITPVP_ICON_COMPRESSED("https://i.imgur.com/BBD1VWu.png"),
    CREATIVE_ICON("https://i.imgur.com/b1MACP9.png"),
    CREATIVE_ICON_COMPRESSED("https://i.imgur.com/5p1Y3oK.png"),
    MINIGAMES_ICON("https://i.imgur.com/2xTgLAg.png"),
    MINIGAMES_ICON_COMPRESSED("https://i.imgur.com/F9M5UWg.png"),
    SKYBLOCK_ICON("https://i.imgur.com/OI1mz7Z.png"),
    SKYBLOCK_ICON_COMPRESSED("https://i.imgur.com/FvPsGU6.png"),
    PRISON_ICON("https://i.imgur.com/GhGTIDH.png"),
    PRISON_ICON_COMPRESSED("https://i.imgur.com/gwUyp8s.png"),
    FOG_ICON("https://i.imgur.com/YruBbXy.png"),
    FOG_ICON_COMPRESSED("https://i.imgur.com/iEdANXJ.png"),

    KITPVP_LOGO("https://i.imgur.com/0oX5Bmd.png"),
    PRISON_LOGO("https://i.imgur.com/XSuJ1Tw.png"),
    MINIGAMES_LOGO("https://i.imgur.com/YguYCaS.png"),
    SKYBLOCK_LOGO("https://i.imgur.com/hBmypoW.png"),
    SURVIVAL_LOGO("https://i.imgur.com/3E17kYH.png"),
    FOG_LOGO("https://i.imgur.com/MG6Sw2C.png"),
    CREATIVE_LOGO("https://i.imgur.com/kyj9TSf.png"),

    VIP_IRON("https://i.imgur.com/YGr0ahP.png"),
    VIP_GOLD("https://i.imgur.com/UzUGCWn.png"),
    VIP_DIAMOND("https://i.imgur.com/uNavArR.png"),
    VIP_EMERALD("https://i.imgur.com/4po6vti.png"),
    VIP_IRON_COMPRESSED("https://i.imgur.com/NMVIcyp.png"),
    VIP_GOLD_COMPRESSED("https://i.imgur.com/Wie3d7m.png"),
    VIP_DIAMOND_COMPRESSED("https://i.imgur.com/ORymvKQ.png"),
    VIP_EMERALD_COMPRESSED("https://i.imgur.com/pXPoDaJ.png"),

    VIP_UPGRADE_IRON_GOLD("https://i.imgur.com/7KHWiX2.png"),
    VIP_UPGRADE_IRON_DIAMOND("https://i.imgur.com/XnQmsc3.png"),
    VIP_UPGRADE_IRON_EMERALD("https://i.imgur.com/ZK4LnwH.png"),
    VIP_UPGRADE_GOLD_DIAMOND("https://i.imgur.com/Kp9rGfy.png"),
    VIP_UPGRADE_GOLD_EMERALD("https://i.imgur.com/nNhHa04.png"),
    VIP_UPGRADE_DIAMOND_EMERALD("https://i.imgur.com/EASmD0X.png"),

    IRON_INGOT("https://i.imgur.com/dUvZsW6.png"),
    GOLD_INGOT("https://i.imgur.com/pg0kK9R.png"),
    DIAMOND("https://i.imgur.com/wasK7pf.png"),
    BARRIER("https://i.imgur.com/bS9hBJY.png"),
    SIGN("https://i.imgur.com/bw8AOr8.png"),
    PRISMARINE_SHARD("https://i.imgur.com/NVGeZkz.png"),
    STEVE("https://i.imgur.com/t3BI1qA.png");

    @Getter private final String url;

    Image(String url) {
        this.url = url;
    }

    public File getFile(String fileName) {
        if (url == null)
            return null;

        try {
            BufferedImage img = ImageIO.read(new URL(url));
            File file = new File(fileName + ".png");
            ImageIO.write(img, "png", file);

            return file;
        } catch (IOException e) {
            return null;
        }
    }

    public static Image icon(Server server) {
        try {
            return Image.valueOf(server.toString() + "_ICON");
        } catch(NullPointerException | IllegalArgumentException ex) {
            return ORBITMINES_ICON;
        }
    }

    public static Image logoFrom(Server server) {
        try {
            return Image.valueOf(server.toString() + "_LOGO");
        } catch(NullPointerException | IllegalArgumentException ex) {
            return ORBITMINES_LOGO;
        }
    }
}
