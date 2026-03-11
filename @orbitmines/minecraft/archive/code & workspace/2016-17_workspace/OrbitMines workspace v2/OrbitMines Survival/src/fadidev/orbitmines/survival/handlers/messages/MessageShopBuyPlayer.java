package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageShopBuyPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§6" + args[0] + "§7 heeft §6" + args[1] + "§7(§6" + args[2] + "x§7) gekocht van jouw voor §6" + args[3] + "$§7.";
            case ENGLISH:
                return "§6" + args[0] + "§7 bought §6" + args[1] + "§7(§6" + args[2] + "x§7) from you for §6" + args[3] + "$§7.";
            default:
                return null;
        }
    }
}
