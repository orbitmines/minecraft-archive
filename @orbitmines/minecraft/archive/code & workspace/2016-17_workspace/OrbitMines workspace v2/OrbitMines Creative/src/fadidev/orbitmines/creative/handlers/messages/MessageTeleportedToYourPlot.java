package fadidev.orbitmines.creative.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageTeleportedToYourPlot extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§dPlot §7Teleportatie §c§lGeannuleerd§7.";
            case ENGLISH:
                return "§c§lCancelled§d Plot §7Teleportation.";
            default:
                return null;
        }
    }
}
