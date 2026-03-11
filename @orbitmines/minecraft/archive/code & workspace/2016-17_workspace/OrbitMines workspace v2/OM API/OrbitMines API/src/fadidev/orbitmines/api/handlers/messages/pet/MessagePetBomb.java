package fadidev.orbitmines.api.handlers.messages.pet;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessagePetBomb extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Bom";
            case ENGLISH:
                return "Bomb";
            default:
                return null;
        }
    }
}
