package fadidev.orbitmines.kitpvp.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordLeftClick extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Linkermuisknop";
            case ENGLISH:
                return "Left Click";
            default:
                return null;
        }
    }
}
