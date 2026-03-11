package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdTotalVipPoints extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Jouw VIP Points: §b§l" + args[0];
            case ENGLISH:
                return "§7Your VIP Points §b§l" + args[0];
            default:
                return null;
        }
    }
}
