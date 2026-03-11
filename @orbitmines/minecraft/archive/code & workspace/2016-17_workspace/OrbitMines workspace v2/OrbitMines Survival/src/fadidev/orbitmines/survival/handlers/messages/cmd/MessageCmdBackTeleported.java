package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdBackTeleported extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Geteleporteerd naar je vorige locatie.";
            case ENGLISH:
                return "§7Teleported to your previous location.";
            default:
                return null;
        }
    }
}
