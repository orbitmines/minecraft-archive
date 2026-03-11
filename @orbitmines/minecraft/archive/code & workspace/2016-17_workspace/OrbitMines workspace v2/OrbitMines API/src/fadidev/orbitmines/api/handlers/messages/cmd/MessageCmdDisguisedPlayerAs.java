package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdDisguisedPlayerAs extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt " + args[0] + " §7vermomd als: §6" + args[1] + "§7.";
            case ENGLISH:
                return "§7Disguised " + args[0] + " §7as: §6" + args[1] + "§7.";
            default:
                return null;
        }
    }
}
