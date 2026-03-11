package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageSwapTpPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent van positie §2§lgeswapt§7 met " + args[0] + "§7!";
            case ENGLISH:
                return args[0] + " §2§lswapped§7 positions with you!";
            default:
                return null;
        }
    }
}
