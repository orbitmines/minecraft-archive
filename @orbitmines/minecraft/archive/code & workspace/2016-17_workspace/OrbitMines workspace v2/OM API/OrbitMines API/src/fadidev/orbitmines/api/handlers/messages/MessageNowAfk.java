package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageNowAfk extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7 " + args[0] + "§7 is nu §6AFK§7. (§7" + args[1] + "§7)";
            case ENGLISH:
                return "§7 " + args[0] + "§7 is now §6AFK§7. (§7" + args[1] + "§7)";
            default:
                return null;
        }
    }
}
