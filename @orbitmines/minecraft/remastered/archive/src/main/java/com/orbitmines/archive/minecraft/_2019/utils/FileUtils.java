package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtils {

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            assert files != null;

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }

        return directory.delete();
    }

    public static void copyDirectory(File source, File dest) throws IOException {
        if (!source.exists())
            throw new IOException("Source directory does not exist: " + source);

        if (!dest.exists())
            dest.mkdirs();

        File[] files = source.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            File destFile = new File(dest, file.getName());
            if (file.isDirectory()) {
                copyDirectory(file, destFile);
            } else {
                try (InputStream in = new FileInputStream(file);
                     OutputStream out = new FileOutputStream(destFile)) {
                    byte[] buffer = new byte[16384];
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    public static void extractZip(File archive, File destDir) throws IOException {
        if (!destDir.exists())
            destDir.mkdirs();

        ZipFile zipFile = new ZipFile(archive);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        byte[] buffer = new byte[16384];
        int len;
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String entryFileName = entry.getName();
            File dir = buildDirectoryHierarchyFor(entryFileName, destDir);

            if (!dir.exists())
                dir.mkdirs();

            if (!entry.isDirectory()) {
                File file = new File(destDir, entryFileName);
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();

                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));

                while ((len = bis.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }

                bos.flush();
                bos.close();
                bis.close();
            }
        }
        zipFile.close();
    }
    private static File buildDirectoryHierarchyFor(String entryName, File destDir) {
        int lastIndex = entryName.lastIndexOf('/');
        String internalPathToEntry = entryName.substring(0, lastIndex + 1);
        return new File(destDir, internalPathToEntry);
    }
}
