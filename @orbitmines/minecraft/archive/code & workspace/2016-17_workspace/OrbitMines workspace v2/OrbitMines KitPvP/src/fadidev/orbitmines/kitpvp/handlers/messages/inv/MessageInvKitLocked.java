package fadidev.orbitmines.kitpvp.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageInvKitLocked extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§7Deze Kit is voor jouw §4§lVergrendeld§7! Koop het met §6Linkermuisklik§7!";
            case ENGLISH:
                return "§7This kit is §4§lLocked§7 for you! Buy it with §6Left Click§7!";
        }
        return null;
    }
}
