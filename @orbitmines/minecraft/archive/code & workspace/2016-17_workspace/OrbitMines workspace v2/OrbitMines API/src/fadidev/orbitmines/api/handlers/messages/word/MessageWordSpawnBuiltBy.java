package fadidev.orbitmines.api.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordSpawnBuiltBy extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Spawn gebouwd door";
            case ENGLISH:
                return "Spawn built by";
            default:
                return null;
        }
    }
}
