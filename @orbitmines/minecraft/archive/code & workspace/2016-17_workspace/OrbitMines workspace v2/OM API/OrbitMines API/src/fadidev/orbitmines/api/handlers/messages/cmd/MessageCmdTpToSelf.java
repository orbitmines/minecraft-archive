package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdTpToSelf extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan niet naar jezelf toe §6teleporten§7!";
            case ENGLISH:
                return "§7You can't §6teleport§7 to yourself!";
            default:
                return null;
        }
    }
}
