package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdVoteTotal extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§b§lVote §8| §7Jouw votes deze maand: §b§l" + args[0];
            case ENGLISH:
                return "§b§lVote §8| §7Your total Votes this Month: §b§l" + args[0];
            default:
                return null;
        }
    }
}
