package fadidev.orbitmines.creative.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageClearingPlot extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§dPlot§7 Resetten...";
            case ENGLISH:
                return "§7Clearing §dPlot§7...";
            default:
                return null;
        }
    }
}
