package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageShopHighPrice extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan de prijs niet hoger dan §61.000.000$§7 zetten!";
            case ENGLISH:
                return "§7You cannot set the price higher than §61.000.000$§7!";
            default:
                return null;
        }
    }
}
