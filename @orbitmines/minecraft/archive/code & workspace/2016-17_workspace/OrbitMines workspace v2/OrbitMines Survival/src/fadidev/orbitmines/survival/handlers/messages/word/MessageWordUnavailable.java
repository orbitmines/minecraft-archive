package fadidev.orbitmines.survival.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordUnavailable extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Onbeschikbaar";
            case ENGLISH:
                return "Unavailable";
            default:
                return null;
        }
    }
}
