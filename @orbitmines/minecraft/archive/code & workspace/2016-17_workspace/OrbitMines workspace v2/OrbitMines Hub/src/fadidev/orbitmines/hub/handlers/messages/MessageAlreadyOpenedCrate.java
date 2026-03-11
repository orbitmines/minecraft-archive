package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageAlreadyOpenedCrate extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt deze §6§lSurprise Crate§7 al geopend!";
            case ENGLISH:
                return "§7You already opened this §6§lSurprise Crate§7!";
            default:
                return null;
        }
    }
}
