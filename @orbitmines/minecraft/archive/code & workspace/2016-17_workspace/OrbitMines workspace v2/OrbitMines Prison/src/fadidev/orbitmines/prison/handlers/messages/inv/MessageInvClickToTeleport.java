package fadidev.orbitmines.prison.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvClickToTeleport extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §7§lKlik om te teleporteren";
            case ENGLISH:
                return " §7§lClick to Teleport";
            default:
                return null;
        }
    }
}
