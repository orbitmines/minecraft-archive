package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageStackPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7" + args[0] + " §7heeft je op zijn/haar hoofd gezet!";
            case ENGLISH:
                return "§7" + args[0] + " §6§lstacked§7 you on their Head!";
            default:
                return null;
        }
    }
}
