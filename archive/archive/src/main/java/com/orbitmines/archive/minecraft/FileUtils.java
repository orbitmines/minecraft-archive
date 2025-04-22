package com.orbitmines.archive.minecraft;

import java.io.File;

public class FileUtils {

    public static void delete(File directory) {
        directory.delete();

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) { delete(file); }
    }
}
