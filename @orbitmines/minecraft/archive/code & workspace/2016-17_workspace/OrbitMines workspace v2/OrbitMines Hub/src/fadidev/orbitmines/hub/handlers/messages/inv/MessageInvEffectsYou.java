package fadidev.orbitmines.hub.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvEffectsYou extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Effect op jouw";
            case ENGLISH:
                return "Effects you";
            default:
                return null;
        }
    }
}
