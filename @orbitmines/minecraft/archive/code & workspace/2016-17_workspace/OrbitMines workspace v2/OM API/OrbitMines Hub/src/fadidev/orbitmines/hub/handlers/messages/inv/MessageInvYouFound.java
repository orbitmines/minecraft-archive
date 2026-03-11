package fadidev.orbitmines.hub.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvYouFound extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt " + args[0] + "§7 gevonden! (" + args[1] + "§7)";
            case ENGLISH:
                return "§7You found " + args[0] + "§7! (" + args[1] + "§7)";
            default:
                return null;
        }
    }
}
