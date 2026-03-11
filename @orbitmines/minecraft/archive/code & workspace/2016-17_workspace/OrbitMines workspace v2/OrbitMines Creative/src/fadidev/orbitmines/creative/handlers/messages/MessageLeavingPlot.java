package fadidev.orbitmines.creative.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageLeavingPlot extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§dPlot§7 verlaten in §d" + args[0] + "§7...";
            case ENGLISH:
                return "§7Leaving §dPlot§7 in §d" + args[0] + "§7...";
            default:
                return null;
        }
    }
}
