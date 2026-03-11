package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageOpeningKitSelector extends Message {

    @Override
    public String get(OMPlayer omp, String... strings) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§bKit Selector§7 openen...";
            case ENGLISH:
                return "§7Opening the §bKit Selector§7...";
        }
        return null;
    }
}
