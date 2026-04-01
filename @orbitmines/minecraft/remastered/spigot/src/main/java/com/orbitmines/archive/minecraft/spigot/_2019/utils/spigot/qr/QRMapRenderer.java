package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.qr;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

import java.awt.*;

public abstract class QRMapRenderer extends MapRenderer {

    private final String title;
    private final String subTitle;
    private final String otpTitle;
    private final String otpDomain;
    private final String secret;

    private final BitMatrix bitMatrix;

    public QRMapRenderer(String title, String subTitle, String otpTitle, String otpDomain, String secret) throws WriterException {
        this.title = title;
        this.subTitle = subTitle;
        this.otpTitle = otpTitle;
        this.otpDomain = otpDomain;
        this.secret = secret;
        this.bitMatrix = new QRCodeWriter().encode(otpAuth(), BarcodeFormat.QR_CODE, 128, 128);
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        for (int x = 0; x < 128; x++) {
            for (int z = 0; z < 128; z++) {
                if (z >= 14)
                    mapCanvas.setPixel(x, z, bitMatrix.get(x, z - 14) ? MapPalette.matchColor(Color.BLACK) : MapPalette.WHITE);
                else
                    mapCanvas.setPixel(x, z, MapPalette.WHITE);
            }
        }

        mapCanvas.drawText(5, 5, MinecraftFont.Font, this.title);
        mapCanvas.drawText(15, 15, MinecraftFont.Font, this.subTitle);
        mapCanvas.drawText(15, 25, MinecraftFont.Font, this.secret);
    }

    private String otpAuth() {
        return String.format("otpauth://totp/%s@%s?secret=%s", this.otpTitle, this.otpDomain, this.secret);
    }
}
