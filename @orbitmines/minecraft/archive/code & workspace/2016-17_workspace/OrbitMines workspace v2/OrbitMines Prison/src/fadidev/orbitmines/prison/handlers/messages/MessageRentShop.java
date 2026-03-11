package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageRentShop extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt deze Shop voor 2 dagen gehuurd!";
            case ENGLISH:
                return "§7This Shop is yours for 2 days!";
            default:
                return null;
        }
    }
}
