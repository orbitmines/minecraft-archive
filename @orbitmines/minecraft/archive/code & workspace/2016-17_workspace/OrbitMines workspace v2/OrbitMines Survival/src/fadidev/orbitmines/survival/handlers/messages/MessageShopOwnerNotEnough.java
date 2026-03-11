package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageShopOwnerNotEnough extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7De eigenaar van die shop kan dat niet meer betalen!";
            case ENGLISH:
                return "§7The owner of that shop can no longer efford that payment!";
            default:
                return null;
        }
    }
}
