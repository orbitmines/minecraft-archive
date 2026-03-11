package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageServerRestarting extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 is aan het §8§lrestarten§7!";
            case ENGLISH:
                return args[0] + "§7 is §8§lrestarting§7!";
            default:
                return null;
        }
    }
}
