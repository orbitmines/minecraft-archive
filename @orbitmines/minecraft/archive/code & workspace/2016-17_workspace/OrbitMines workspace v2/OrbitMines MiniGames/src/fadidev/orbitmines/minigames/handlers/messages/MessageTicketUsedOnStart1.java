package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageTicketUsedOnStart1 extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Perk Ticket wordt gebruikt ";
            case ENGLISH:
                return "Perk Ticket will be used ";
            default:
                return null;
        }
    }
}
