package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvTooLongName extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je mag maximaal §d20 karakters§7 gebruiken in de naam van een kit!";
            case ENGLISH:
                return "§7You may only use §d20 characters§7 in the name of a kit!";
            default:
                return null;
        }
    }
}
