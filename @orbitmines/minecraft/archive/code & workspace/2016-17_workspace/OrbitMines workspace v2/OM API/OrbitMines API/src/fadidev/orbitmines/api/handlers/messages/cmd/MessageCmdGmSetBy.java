package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdGmSetBy extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §6GameMode§7 is nu " + args[0] + "§7 door " + args[1] + "§7!";
            case ENGLISH:
                return args[1] + " §7Set your §6GameMode§7 to " + args[0] + "§7!";
            default:
                return null;
        }
    }
}
