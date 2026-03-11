package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvItemBought extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Item Gekocht: " + args[0] + "§7.";
            case ENGLISH:
                return "§7Item Bought: " + args[0] + "§7.";
            default:
                return null;
        }
    }
}
