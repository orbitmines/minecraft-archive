package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageShopSell extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt §6" + args[0] + "§7(§6" + args[1] + "§7) verkocht aan §6" + args[2] + " §7voor §6" + args[3] + "$§7.";
            case ENGLISH:
                return "§7You've sold §6" + args[0] + "§7(§6" + args[1] + "§7) to §6" + args[2] + " §7for §6" + args[3] + "$§7.";
            default:
                return null;
        }
    }
}
