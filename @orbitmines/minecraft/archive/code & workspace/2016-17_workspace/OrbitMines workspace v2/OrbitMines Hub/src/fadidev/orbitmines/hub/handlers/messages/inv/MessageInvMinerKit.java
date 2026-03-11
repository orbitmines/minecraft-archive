package fadidev.orbitmines.hub.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvMinerKit extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Haste I de eerste 15m.";
            case ENGLISH:
                return "Haste I the first 15m.";
            default:
                return null;
        }
    }
}
