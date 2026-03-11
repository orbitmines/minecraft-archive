package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageServerClosed extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 is §4§ldicht§7!";
            case ENGLISH:
                return args[0] + "§7 is §4§lclosed§7!";
            default:
                return null;
        }
    }
}
