package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLUtils {

    public static void writeRequest(HttpURLConnection connection, String body) throws Exception {
        OutputStream stream = connection.getOutputStream();
        stream.write(body.getBytes());
        stream.flush();
        stream.close();
    }

    public static HttpURLConnection createJsonConnection(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/org.json");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        return connection;
    }

    public static InputStream readInputStream(String urlString) throws Exception {
        return new URL(urlString).openConnection().getInputStream();
    }

    public static File downloadImage(String urlString, String filePath, String format) throws Exception {
        URL url = new URL(urlString);
        BufferedImage image = ImageIO.read(url);
        File file = new File(filePath + "." + format);
        ImageIO.write(image, format, file);

        return file;
    }

    public static String humanReadableLink(String url) {
        return url.replaceAll("https?://(www\\.)?", "");
    }
}
