package fadidev.orbitmines.skyblock.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordTeleportingTo extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Teleporteren naar";
            case ENGLISH:
                return "Teleporting to";
            default:
                return null;
        }
    }
}
