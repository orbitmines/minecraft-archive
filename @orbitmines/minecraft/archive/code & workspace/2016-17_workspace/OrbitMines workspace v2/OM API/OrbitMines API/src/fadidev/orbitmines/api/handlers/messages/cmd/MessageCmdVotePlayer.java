package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdVotePlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§b§l" + args[0] + "§7 heeft gevoten met §b§l/vote§7.";
            case ENGLISH:
                return "§b§l" + args[0] + "§7 has voted with §b§l/vote§7.";
            default:
                return null;
        }
    }
}
