package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvChangeWarpItem extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Warp item verandert in " + args[0] + "§7.";
            case ENGLISH:
                return "§7Changed the warp item to " + args[0] + "§7.";
            default:
                return null;
        }
    }
}
