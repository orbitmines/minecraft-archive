package fadidev.orbitmines.kitpvp.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageInvClickToTeleport extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§e§lKlik hier om te Teleporteren";
            case ENGLISH:
                return "§e§lClick Here to Teleport";
        }
        return null;
    }
}
