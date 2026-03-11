package fadidev.orbitmines.hub.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordStaanNu extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7" + args[0] + " nu ";
            case ENGLISH:
                return "";
            default:
                return null;
        }
    }
}
