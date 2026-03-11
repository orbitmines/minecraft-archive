package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageActivated extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return args[0] + " §7heeft " + args[1] + "§7 geactiveerd.";
            case ENGLISH:
                return args[0] + " §7activated " + args[1] + "§7.";
        }
        return null;
    }
}
