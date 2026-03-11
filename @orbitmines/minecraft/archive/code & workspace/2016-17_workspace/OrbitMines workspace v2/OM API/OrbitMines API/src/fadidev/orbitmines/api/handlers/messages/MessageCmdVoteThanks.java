package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdVoteThanks extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§b§lVote §8| §7Dank je, §b§l" + args[0] + " §7voor je §b§lVote§7!";
            case ENGLISH:
                return "§b§lVote §8| §7Thank you, §b§l" + args[0] + " §7for your §b§lVote§7!";
            default:
                return null;
        }
    }
}
