package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvDeleteKit extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§c§lVerwijder Kit";
            case ENGLISH:
                return "§c§lDelete Kit";
            default:
                return null;
        }
    }
}
