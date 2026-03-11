package fadidev.orbitmines.hub.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvRunnerKit extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Kan geen schade doen/ontvangen in de eerste 30s.";
            case ENGLISH:
                return "Can't deal/take damage in the first 30s.";
            default:
                return null;
        }
    }
}
