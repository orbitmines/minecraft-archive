package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvMaxCharacters extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan maximaal §620 karakters§7 gebruiken in de naam van een warp!";
            case ENGLISH:
                return "§7You may only use §620 characters§7 in the name of a warp!";
            default:
                return null;
        }
    }
}
