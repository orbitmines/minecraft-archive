package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdAcceptNotOnline extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§6" + args[0] + "§7 is niet meer §aonline§7!";
            case ENGLISH:
                return "§6" + args[0] + "§7 is no longer §aonline§7!";
            default:
                return null;
        }
    }
}
