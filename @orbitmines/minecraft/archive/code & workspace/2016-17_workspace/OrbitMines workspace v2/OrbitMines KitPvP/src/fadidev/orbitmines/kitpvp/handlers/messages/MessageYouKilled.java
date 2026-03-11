package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageYouKilled extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§7You hebt §6" + args[0] + "§7 gedood!";
            case ENGLISH:
                return "§7You killed §6" + args[0] + "§7!";
        }
        return null;
    }
}
