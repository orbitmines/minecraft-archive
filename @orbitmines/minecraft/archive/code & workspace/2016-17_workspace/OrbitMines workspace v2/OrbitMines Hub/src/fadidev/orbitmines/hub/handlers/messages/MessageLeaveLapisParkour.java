package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageLeaveLapisParkour extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent weggegaan van het Parkour. Probeer het later nog eens!";
            case ENGLISH:
                return "§7You left the Parkour. Try again an other time!";
            default:
                return null;
        }
    }
}
