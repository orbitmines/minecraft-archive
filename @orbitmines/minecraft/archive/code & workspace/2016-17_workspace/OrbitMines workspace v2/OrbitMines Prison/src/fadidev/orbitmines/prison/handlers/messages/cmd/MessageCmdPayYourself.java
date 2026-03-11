package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPayYourself extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan jezelf geen §6§lGold§7 geven.";
            case ENGLISH:
                return "§7You cannot send §6§lGold§7 to yourself.";
            default:
                return null;
        }
    }
}
