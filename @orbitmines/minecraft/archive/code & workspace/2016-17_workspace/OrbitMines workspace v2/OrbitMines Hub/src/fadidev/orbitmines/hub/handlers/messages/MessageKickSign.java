package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageKickSign extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je vroeg er wel om. :)";
            case ENGLISH:
                return "§7Well that's unfortunate. :)";
            default:
                return null;
        }
    }
}
