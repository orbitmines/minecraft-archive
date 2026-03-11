package fadidev.orbitmines.minigames.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordNewBalance extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Nieuw Balans";
            case ENGLISH:
                return "New Balance";
            default:
                return null;
        }
    }
}
