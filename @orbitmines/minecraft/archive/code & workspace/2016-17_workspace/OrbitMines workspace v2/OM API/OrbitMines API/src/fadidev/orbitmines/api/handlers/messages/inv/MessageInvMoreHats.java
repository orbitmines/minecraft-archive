package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvMoreHats extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Meer Hats";
            case ENGLISH:
                return "More Hats";
            default:
                return null;
        }
    }
}
