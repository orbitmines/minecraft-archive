package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageUnknownCommand extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Die command bestaat niet! (§6" + args[0] + "§7). Gebruik §6/help§7 voor hulp.";
            case ENGLISH:
                return "§7Unknown command (§6" + args[0] + "§7). Use §6/help§7 for help.";
            default:
                return null;
        }
    }
}
