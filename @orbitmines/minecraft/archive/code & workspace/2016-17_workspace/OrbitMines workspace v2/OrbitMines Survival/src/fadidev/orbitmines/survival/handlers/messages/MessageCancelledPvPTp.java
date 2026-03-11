package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCancelledPvPTp extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7PvP Teleportatie §c§lGeannuleerd.";
            case ENGLISH:
                return "§c§lCancelled§7 PvP Teleportation.";
            default:
                return null;
        }
    }
}
