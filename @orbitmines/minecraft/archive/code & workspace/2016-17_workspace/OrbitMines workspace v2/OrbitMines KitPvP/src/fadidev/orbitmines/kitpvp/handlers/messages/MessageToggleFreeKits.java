package fadidev.orbitmines.kitpvp.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageToggleFreeKits extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§d§lFree Kits§7 zijn " + Utils.statusString(Language.DUTCH, OrbitMinesKitPvP.getKitPvP().isFreeKitEnabled()) + " §7gezet door " + args[0] + "§7.";
            case ENGLISH:
                return "§d§lFree Kits§7 have been " + Utils.statusString(Language.ENGLISH, OrbitMinesKitPvP.getKitPvP().isFreeKitEnabled()) + " §7by " + args[0] + "§7.";
        }
        return null;
    }
}
