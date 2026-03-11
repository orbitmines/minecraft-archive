package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdVoteServer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§b§lVote §8| §7Beloningen in de " + args[0] + "§7 Server:";
            case ENGLISH:
                return "§b§lVote §8| §7Reward in the " + args[0] + "§7 Server:";
            default:
                return null;
        }
    }
}
