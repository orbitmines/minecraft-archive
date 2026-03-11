package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvDisablePet extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§4§nZet Pet UIT";
            case ENGLISH:
                return "§4§nDisable Pet";
            default:
                return null;
        }
    }
}
