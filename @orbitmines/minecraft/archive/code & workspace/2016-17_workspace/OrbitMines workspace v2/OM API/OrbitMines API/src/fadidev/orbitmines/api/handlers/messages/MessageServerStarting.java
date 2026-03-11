package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageServerStarting extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + " §8| §7Deze server is zojuist " + args[1] + "gestart§7! Wacht even een paar " + args[1] + "seconden§7.";
            case ENGLISH:
                return args[0] + " §8| §7This Server just " + args[1] + "restarted§7! Wait a few " + args[1] + "seconds§7.";
            default:
                return null;
        }
    }
}
