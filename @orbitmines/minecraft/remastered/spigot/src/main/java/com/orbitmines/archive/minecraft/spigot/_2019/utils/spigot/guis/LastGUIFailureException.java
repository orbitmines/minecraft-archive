package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

// Temp
public class LastGUIFailureException extends RuntimeException {

    public LastGUIFailureException(GUI lastGUI, GUI gui, String title) {
        super("LastGui: " + (lastGUI == null ? "null" : lastGUI.getClass().getSimpleName()) + ", " + "Gui: " + (gui == null ? "null" : gui.getClass().getSimpleName()) + ", Title: " + title);
    }
}
