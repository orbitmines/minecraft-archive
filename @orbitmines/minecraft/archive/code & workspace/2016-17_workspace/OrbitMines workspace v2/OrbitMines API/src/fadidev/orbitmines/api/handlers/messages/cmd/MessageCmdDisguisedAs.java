package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdDisguisedAs extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Vermomd als: §6" + args[0] + "§7.";
            case ENGLISH:
                return "§7Disguised as: §6" + args[0] + "§7.";
            default:
                return null;
        }
    }
}
