package fadidev.orbitmines.creative.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordEmpty extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Leeg";
            case ENGLISH:
                return "Empty";
            default:
                return null;
        }
    }
}
