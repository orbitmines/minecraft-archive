package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageKitSelected extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§f§l" + args[0] + "§7 geselecteerd.";
            case ENGLISH:
                return "§7Selected §f§l" + args[0] + "§7.";
        }
        return null;
    }
}
