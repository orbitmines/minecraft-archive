package fadidev.orbitmines.hub.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvPlayerFound extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + " §7heeft " + args[1] + "§7 gevonden! (" + args[2] + "§7)";
            case ENGLISH:
                return args[0] + " §7found " + args[1] + "§7! (" + args[2] + "§7)";
            default:
                return null;
        }
    }
}
