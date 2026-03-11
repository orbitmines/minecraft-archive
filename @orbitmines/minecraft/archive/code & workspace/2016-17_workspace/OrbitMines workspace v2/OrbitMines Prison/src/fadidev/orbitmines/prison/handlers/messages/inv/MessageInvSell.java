package fadidev.orbitmines.prison.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvSell extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 vekocht voor §6§l" + args[1] + " Gold§7!";
            case ENGLISH:
                return "§7Sold " + args[0] + "§7 for §6§l" + args[1] + " Gold§7!";
            default:
                return null;
        }
    }
}
