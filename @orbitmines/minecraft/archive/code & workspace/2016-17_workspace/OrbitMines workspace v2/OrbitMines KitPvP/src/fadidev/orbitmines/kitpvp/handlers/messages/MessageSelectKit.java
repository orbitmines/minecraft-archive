package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageSelectKit extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§7Kit Geselecteerd: '§b§l" + args[0] + "§7' §7§o(§a§oLvL " + args[1] + "§7§o)";
            case ENGLISH:
                return "§7Selected Kit: '§b§l" + args[0] + "§7' §7§o(§a§oLvL " + args[1] + "§7§o)";
        }
        return null;
    }
}
