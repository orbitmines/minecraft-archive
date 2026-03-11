package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvTeleportingTo extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§eTeleporten naar " + args[0] + "§e...";
            case ENGLISH:
                return "§eTeleporting to " + args[0] + "§e...";
            default:
                return null;
        }
    }
}
