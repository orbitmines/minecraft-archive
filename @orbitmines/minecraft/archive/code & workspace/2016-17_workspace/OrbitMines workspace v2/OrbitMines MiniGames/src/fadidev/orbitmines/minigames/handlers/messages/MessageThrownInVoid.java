package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageThrownInVoid extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return args[0] + "§7 was in de void geduwd door " + args[1] + "§7.";
            case ENGLISH:
                return args[0] + "§7 was thrown in the void by " + args[1] + "§7.";
        }
        return null;
    }
}
