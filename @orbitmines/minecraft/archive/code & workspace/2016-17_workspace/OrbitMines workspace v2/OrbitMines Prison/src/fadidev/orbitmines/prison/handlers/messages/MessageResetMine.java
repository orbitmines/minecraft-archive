package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageResetMine extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Mine " + args[0] + "§7 is gereset!";
            case ENGLISH:
                return "§7Mine " + args[0] + "§7 has been reset!";
            default:
                return null;
        }
    }
}
