package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageCantConnectToHub extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§7Kan geen verbining maken met de §3§lHub§7.";
            case ENGLISH:
                return "§7Couldn't connect to the §3§lHub§7.";
        }
        return null;
    }
}
