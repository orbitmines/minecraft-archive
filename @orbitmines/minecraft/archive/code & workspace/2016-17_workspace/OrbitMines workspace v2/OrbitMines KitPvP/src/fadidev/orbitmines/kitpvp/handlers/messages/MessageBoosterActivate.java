package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageBoosterActivate extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return omp.getName() + " §7heeft een §aBooster§7 geactiveerd! (§ax" + args[0] + "§7)";
            case ENGLISH:
                return omp.getName() + " §7activated a §aBooster§7! (§ax" + args[0] + "§7)";
        }
        return null;
    }
}
