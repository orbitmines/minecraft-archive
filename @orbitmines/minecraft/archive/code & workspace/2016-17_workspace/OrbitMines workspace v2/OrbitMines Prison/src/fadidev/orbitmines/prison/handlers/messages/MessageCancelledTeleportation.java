package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCancelledTeleportation extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + " §7Teleportatie §c§lGeannuleerd§7.";
            case ENGLISH:
                return "§c§lCancelled " + args[0] + " §7Teleportation.";
            default:
                return null;
        }
    }
}
