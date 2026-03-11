package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageSelectKit extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt de §9" + args[0] + " Kit§7 ontvangen.";
            case ENGLISH:
                return "§7You've been given the §9" + args[0] + " Kit§7.";
            default:
                return null;
        }
    }
}
