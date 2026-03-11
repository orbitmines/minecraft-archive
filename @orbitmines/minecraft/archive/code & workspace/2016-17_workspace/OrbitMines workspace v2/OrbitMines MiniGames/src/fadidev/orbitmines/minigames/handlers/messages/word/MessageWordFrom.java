package fadidev.orbitmines.minigames.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordFrom extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "van";
            case ENGLISH:
                return "from";
            default:
                return null;
        }
    }
}
