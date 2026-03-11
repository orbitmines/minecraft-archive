package fadidev.orbitmines.creative.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCancelPlotTp extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Geteleporteerd naar jouw §dPlot§7!";
            case ENGLISH:
                return "§7Teleported to your §dPlot§7.";
            default:
                return null;
        }
    }
}
