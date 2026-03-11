package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdNotOnline extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Player §6" + args[0] + " §7is niet §aOnline§7!";
            case ENGLISH:
                return "§7Player §6" + args[0] + " §7isn't §aOnline§7!";
            default:
                return null;
        }
    }
}
