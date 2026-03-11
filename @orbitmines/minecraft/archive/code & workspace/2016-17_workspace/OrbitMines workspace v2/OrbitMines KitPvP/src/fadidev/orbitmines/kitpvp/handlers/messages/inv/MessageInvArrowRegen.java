package fadidev.orbitmines.kitpvp.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageInvArrowRegen extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return " §c+1 Arrow: §6Elke " + args[0] + " seconden";
            case ENGLISH:
                return " §c+1 Arrow: §6Every " + args[0] + " seconds";
        }
        return null;
    }
}
