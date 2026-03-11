package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdInvSeeAccept extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + " §7is nu jouw inventory aan het bekijken.";
            case ENGLISH:
                return args[0] + " §7is now viewing your inventory.";
            default:
                return null;
        }
    }
}
