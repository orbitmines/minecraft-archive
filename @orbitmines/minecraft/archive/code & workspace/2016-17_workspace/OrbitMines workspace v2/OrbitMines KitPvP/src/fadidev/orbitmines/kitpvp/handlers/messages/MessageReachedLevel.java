package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageReachedLevel extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§6" + args[0] + " §7is nu level §6" + args[1] + "§7!";
            case ENGLISH:
                return "§6" + args[0] + " §7reached level §6" + args[1] + "§7!";
        }
        return null;
    }
}
