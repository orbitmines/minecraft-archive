package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdAcceptToThem extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent geteleporteerd naar " + args[0] + "§7.";
            case ENGLISH:
                return "§7You have been teleported to " + args[0] + "§7.";
            default:
                return null;
        }
    }
}
