package fadidev.orbitmines.creative.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCantDoThatHere extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan dat hier niet doen!";
            case ENGLISH:
                return "§7You cannot do that here!";
            default:
                return null;
        }
    }
}
