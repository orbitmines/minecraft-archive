package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdNotNumber extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§6" + args[0] + "§7 is geen nummer.";
            case ENGLISH:
                return "§6" + args[0] + "§7 isn't a number.";
            default:
                return null;
        }
    }
}
