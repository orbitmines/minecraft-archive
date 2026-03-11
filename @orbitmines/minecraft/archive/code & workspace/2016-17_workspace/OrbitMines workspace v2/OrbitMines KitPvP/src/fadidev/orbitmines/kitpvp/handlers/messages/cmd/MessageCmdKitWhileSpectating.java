package fadidev.orbitmines.kitpvp.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageCmdKitWhileSpectating extends Message {

    @Override
    public String get(OMPlayer omp, String... strings) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§7Je kan geen §6/kit§7 gebruiken terwijl je aan het §espectaten§7 bent!";
            case ENGLISH:
                return "§7You can't use §6/kit§7 while §espectating§7!";
        }
        return null;
    }
}
