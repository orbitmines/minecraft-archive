package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageSwitchingMaps extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§7Maps gaan wisselen in §6§l" + args[0] + "§7...";
            case ENGLISH:
                return "§7Switching Maps in §6§l" + args[0] + "§7...";
        }
        return null;
    }
}
