package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageClickToVote extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "Klik om te Voten";
            case ENGLISH:
                return "Click to Vote";
        }
        return null;
    }
}
