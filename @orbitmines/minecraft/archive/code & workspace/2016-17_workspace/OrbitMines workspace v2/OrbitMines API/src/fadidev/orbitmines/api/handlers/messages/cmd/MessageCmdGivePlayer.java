package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdGivePlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt " + args[0] + " §6§l" + args[1] + " §6" + args[2] + "§7 gegeven!";
            case ENGLISH:
                return "§7You gave " + args[0] + " §6§l" + args[1] + " §6" + args[2] + "§7!";
            default:
                return null;
        }
    }
}
