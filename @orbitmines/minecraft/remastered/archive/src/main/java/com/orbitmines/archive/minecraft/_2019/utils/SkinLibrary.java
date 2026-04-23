package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.w3c.dom.Node;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class SkinLibrary {

    private static long OUTDATED_SKIN_INTERVAL = TimeUnit.MINUTES.toMillis(4);

    @Getter
    private final String filePath;

    public SkinLibrary(String filePath) {
        this.filePath = filePath;
    }

    protected abstract void updateLibraryAsync(Runnable runnable);

    public void updateLibrary(UUID uuid) {
        updateLibraryAsync(
            () -> {
                if (!isOutdated(uuid))
                    return;

                try {
                    BufferedImage skin = fetchSkinTexture(uuid);
                    if (skin == null)
                        return;

                    checkDirs(uuid);

                    /* Save raw skin texture */
                    File rawFile = new File(getParentPath(uuid) + "/skin.png");
                    ImageIO.write(skin, "png", rawFile);

                    /* Render each type */
                    for (Type type : Type.values()) {
                        BufferedImage rendered = type.render(skin);
                        File file = new File(getParentPath(uuid) + "/" + type.fileName + ".png");
                        ImageIO.write(rendered, "png", file);
                    }

                    System.out.println("Updated skins for " + uuid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        );
    }

    private boolean isOutdated(UUID uuid) {
        File rawFile = new File(getParentPath(uuid) + "/skin.png");
        if (!rawFile.exists())
            return true;

        try {
            BasicFileAttributes attributes = Files.readAttributes(rawFile.toPath(), BasicFileAttributes.class);
            long lastModified = attributes.lastModifiedTime().toMillis();
            return (System.currentTimeMillis() - lastModified) > OUTDATED_SKIN_INTERVAL;
        } catch (IOException e) {
            return true;
        }
    }

    public File update(Type type, UUID uuid) {
        checkDirs(uuid);

        try {
            BufferedImage skin = fetchSkinTexture(uuid);
            if (skin == null)
                return null;

            /* Save raw skin texture */
            File rawFile = new File(getParentPath(uuid) + "/skin.png");
            ImageIO.write(skin, "png", rawFile);

            /* Render requested type */
            BufferedImage rendered = type.render(skin);
            File file = new File(getParentPath(uuid) + "/" + type.fileName + ".png");
            ImageIO.write(rendered, "png", file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final String SESSION_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";

    private BufferedImage fetchSkinTexture(UUID uuid) throws Exception {
        String skinUrl = fetchSkinUrl(uuid);
        if (skinUrl == null)
            return null;

        try {
            checkDirs(uuid);
            Files.writeString(new File(getParentPath(uuid) + "/texture.url").toPath(), skinUrl);
        } catch (IOException ignored) {}

        return ImageIO.read(new URL(skinUrl));
    }

    private String fetchSkinUrl(UUID uuid) throws Exception {
        String uuidStr = uuid.toString().replace("-", "");
        HttpURLConnection connection = (HttpURLConnection) new URL(SESSION_URL + uuidStr).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if (connection.getResponseCode() != 200)
            return null;

        String response;
        try (InputStream is = connection.getInputStream()) {
            response = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

        JsonObject profile = new JsonParser().parse(response).getAsJsonObject();
        JsonArray properties = profile.getAsJsonArray("properties");

        for (int i = 0; i < properties.size(); i++) {
            JsonObject property = properties.get(i).getAsJsonObject();
            if ("textures".equals(property.get("name").getAsString())) {
                String decoded = new String(Base64.getDecoder().decode(property.get("value").getAsString()), StandardCharsets.UTF_8);
                JsonObject textures = new JsonParser().parse(decoded).getAsJsonObject();
                return textures.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
            }
        }

        return null;
    }

    /** Returns the cached Mojang skin URL for a UUID, or null if not cached. */
    public String getCachedSkinUrl(UUID uuid) {
        File file = new File(getParentPath(uuid) + "/texture.url");
        if (!file.exists())
            return null;
        try {
            return Files.readString(file.toPath()).trim();
        } catch (IOException e) {
            return null;
        }
    }

    /** Resolves the skin URL from Mojang's session server asynchronously and caches it. */
    public void updateSkinUrlAsync(UUID uuid, Runnable afterUpdate) {
        updateLibraryAsync(() -> {
            try {
                String url = fetchSkinUrl(uuid);
                if (url != null) {
                    checkDirs(uuid);
                    Files.writeString(new File(getParentPath(uuid) + "/texture.url").toPath(), url);
                }
            } catch (Exception ignored) {
            } finally {
                if (afterUpdate != null) afterUpdate.run();
            }
        });
    }

    public File getSkin(Type type, UUID uuid) {
        File file = new File(getParentPath(uuid) + "/" + type.fileName + ".png");

        return file.exists() ? file : update(type, uuid);
    }

    public File getSkinAsGif(Type type, UUID uuid) {
        File skin = getSkin(type, uuid);
        File gifSkin = new File(getParentPath(uuid) + "/" + type.fileName + ".gif");

        try {
            BufferedImage skinImage = ImageIO.read(skin);

            ImageWriter writer = ImageIO.getImageWritersByFormatName("gif").next();

            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(gifSkin);
            writer.setOutput(imageOutputStream);
            writer.prepareWriteSequence(null);

            for (int i = 0; i < 2; i++) {
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                IIOMetadata meta = writer.getDefaultImageMetadata(new ImageTypeSpecifier(skinImage), iwp);

                // Meta configuration
                String metaFormat = meta.getNativeMetadataFormatName();

                Node root = meta.getAsTree(metaFormat);

                //find the GraphicControlExtension node
                Node child = root.getFirstChild();
                while (child != null) {
                    if ("GraphicControlExtension".equals(child.getNodeName())) {
                        break;
                    }
                    child = child.getNextSibling();
                }

                IIOMetadataNode gce = (IIOMetadataNode) child;
                gce.setAttribute("userDelay", "FALSE");
                gce.setAttribute("delayTime", "1");

                //only the first node needs the ApplicationExtensions node
                if (i == 0) {
                    IIOMetadataNode aes = new IIOMetadataNode("ApplicationExtensions");
                    IIOMetadataNode ae = new IIOMetadataNode("ApplicationExtension");
                    ae.setAttribute("applicationID", "NETSCAPE");
                    ae.setAttribute("authenticationCode", "2.0");
                    byte[] uo = new byte[]{
                        //last two bytes is an unsigned short (little endian) that
                        //indicates the the number of times to loop.
                        //0 means loop forever.
                        0x1, 0x0, 0x0
                    };
                    ae.setUserObject(uo);
                    aes.appendChild(ae);
                    root.appendChild(aes);
                }

                meta.setFromTree(metaFormat, root);

                IIOImage ii = new IIOImage(skinImage, null, meta);
                writer.writeToSequence(ii, null);
            }

            writer.endWriteSequence();

            imageOutputStream.close();
        } catch (Exception ex) {
            return null;
        }

        return gifSkin;
    }

    private void checkDirs(UUID uuid) {
        File playerDir = new File(getParentPath(uuid));

        if (!playerDir.exists())
            playerDir.mkdirs();
    }

    private String getParentPath(UUID uuid) {
        return this.filePath + "/" + uuid.toString();
    }

    public enum Type {

        /* 2D face: crop the 8x8 face from the skin, overlay the hat layer, scale to 128x128 */
        HEAD_FLAT("head_2d") {
            @Override
            public BufferedImage render(BufferedImage skin) {
                int size = 128;
                BufferedImage head = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = head.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                /* Base face layer (8,8) -> (16,16) */
                g.drawImage(skin.getSubimage(8, 8, 8, 8), 0, 0, size, size, null);
                /* Hat overlay layer (40,8) -> (48,16) */
                g.drawImage(skin.getSubimage(40, 8, 8, 8), 0, 0, size, size, null);
                g.dispose();
                return head;
            }
        };

        @Getter private final String fileName;

        Type(String fileName) {
            this.fileName = fileName;
        }

        public abstract BufferedImage render(BufferedImage skin);
    }
}
