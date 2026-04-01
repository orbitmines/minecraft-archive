package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import org.w3c.dom.Node;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
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
                for (Type type : Type.values()) {
                    updateOutdated(type, uuid);
                }
            }
        );
    }

    public void updateOutdated(Type type, UUID uuid) {
        File file = new File(getParentPath(uuid) + "/" + type.fileName + ".png");

        if (!file.exists()) {
            update(type, uuid);
            return;
        }

        BasicFileAttributes attributes;
        try {
            attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            update(type, uuid);
            return;
        }

        long lastModified = attributes.lastModifiedTime().toMillis();

        if (OUTDATED_SKIN_INTERVAL > (System.currentTimeMillis() - lastModified))
            return;

        update(type, uuid);
    }

    public File update(Type type, UUID uuid) {
        String url = type.url + uuid.toString();
        String filePath = getParentPath(uuid) + "/" + type.fileName;

        checkDirs(uuid);

        try {
            System.out.println("Saving image '" + url + "' to '" + filePath + ".png'");
            return URLUtils.downloadImage(url, filePath, "png");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

        HEAD_FLAT("https://crafatar.com/avatars/", "head_2d"),
        HEAD_3D("https://crafatar.com/renders/head/", "head_3d"),
        BODY_3D("https://crafatar.com/renders/body/", "body_3d");

        @Getter private final String url;
        @Getter private final String fileName;

        Type(String url, String fileName) {
            this.url = url;
            this.fileName = fileName;
        }
    }
}
