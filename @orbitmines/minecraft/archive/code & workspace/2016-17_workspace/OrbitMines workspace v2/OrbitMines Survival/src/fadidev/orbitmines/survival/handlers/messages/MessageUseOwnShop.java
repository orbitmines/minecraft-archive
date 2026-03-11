package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageUseOwnShop extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan je eigen Chest Shop niet gebruiken!";
            case ENGLISH:
                return "§7You cannot use your own Chest Shop!";
            default:
                return null;
        }
    }
}
