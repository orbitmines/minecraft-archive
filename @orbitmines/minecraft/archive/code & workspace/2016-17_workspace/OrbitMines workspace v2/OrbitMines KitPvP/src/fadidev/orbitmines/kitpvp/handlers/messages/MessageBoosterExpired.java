package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageBoosterExpired extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§a" + args[0] + "'s Booster§7 (§ax" + args[1] + "§7) is verlopen.";
            case ENGLISH:
                return "§a" + args[0] + "'s Booster§7 (§ax" + args[1] + "§7) has been expired.";
        }
        return null;
    }
}
