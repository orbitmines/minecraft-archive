package fadidev.orbitmines.hub.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvClickForDetails extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§aKlik voor Details";
            case ENGLISH:
                return "§aClick for Details";
            default:
                return null;
        }
    }
}
