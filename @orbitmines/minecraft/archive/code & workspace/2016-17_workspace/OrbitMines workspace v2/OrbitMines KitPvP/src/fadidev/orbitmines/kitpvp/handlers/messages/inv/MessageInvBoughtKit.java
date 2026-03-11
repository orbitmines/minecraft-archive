package fadidev.orbitmines.kitpvp.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageInvBoughtKit extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§7Je hebt de '§b§l" + args[0] + "§7' kit gekocht! §7§o(§a§oLvL " + args[1] + "§7§o)";
            case ENGLISH:
                return "§7You have bought the '§b§l" + args[0] + "§7' kit! §7§o(§a§oLvL " + args[1] + "§7§o)";
        }
        return null;
    }
}
