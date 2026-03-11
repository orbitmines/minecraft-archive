package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageUhcInfo2 extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §a§oPvP staat aan over 15 minuten.";
            case ENGLISH:
                return " §a§oPvP Enabled after 15 minutes.";
            default:
                return null;
        }
    }
}
