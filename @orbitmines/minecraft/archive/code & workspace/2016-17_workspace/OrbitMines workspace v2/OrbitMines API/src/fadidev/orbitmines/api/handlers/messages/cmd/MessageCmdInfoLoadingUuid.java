package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdInfoLoadingUuid extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§6" + args[0] + "'s §7UUID info laden...";
            case ENGLISH:
                return "§7Loading §6" + args[0] + "'s §7UUID info...";
            default:
                return null;
        }
    }
}
