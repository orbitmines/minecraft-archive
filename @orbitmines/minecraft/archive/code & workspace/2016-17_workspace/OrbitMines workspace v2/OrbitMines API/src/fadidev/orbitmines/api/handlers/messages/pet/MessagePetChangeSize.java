package fadidev.orbitmines.api.handlers.messages.pet;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessagePetChangeSize extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Verander Grootte";
            case ENGLISH:
                return "Change Size";
            default:
                return null;
        }
    }
}
