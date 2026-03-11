package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageJoin extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §a» " + args[0] + "§a is gejoind.";
            case ENGLISH:
                return " §a» " + args[0] + "§a joined.";
            default:
                return null;
        }
    }
}
