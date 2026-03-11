package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageDiedVoid extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§6" + args[0] + "§7 is in de void gevallen.";
            case ENGLISH:
                return "§6" + args[0] + "§7 fell into the void.";
        }
        return null;
    }
}
