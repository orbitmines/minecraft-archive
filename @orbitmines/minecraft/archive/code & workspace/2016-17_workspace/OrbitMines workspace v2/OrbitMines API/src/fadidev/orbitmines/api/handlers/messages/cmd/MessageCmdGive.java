package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdGive extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt jezelf §6§l" + args[0] + " §6" + args[1] + "§7 gegeven!";
            case ENGLISH:
                return "§7You gave yourself §6§l" + args[0] + " §6" + args[1] + "§7!";
            default:
                return null;
        }
    }
}
