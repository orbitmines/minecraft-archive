package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCommandInPvPArea extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Je kan die command niet gebruiken in de §c§lPvP Area§7!";
            case ENGLISH:
                return "You cannot use that command in the §c§lPvP Area§7!";
            default:
                return null;
        }
    }
}
