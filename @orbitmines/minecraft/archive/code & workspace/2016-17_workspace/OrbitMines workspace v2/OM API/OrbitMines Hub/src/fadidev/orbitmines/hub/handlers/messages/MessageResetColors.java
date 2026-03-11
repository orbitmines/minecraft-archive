package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageResetColors extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§4§lReset Kleuren";
            case ENGLISH:
                return "§4§lReset Colors";
            default:
                return null;
        }
    }
}
