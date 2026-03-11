package fadidev.orbitmines.skyblock.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordTeleportedTo extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Geteleporteerd naar";
            case ENGLISH:
                return "Teleported to";
            default:
                return null;
        }
    }
}
