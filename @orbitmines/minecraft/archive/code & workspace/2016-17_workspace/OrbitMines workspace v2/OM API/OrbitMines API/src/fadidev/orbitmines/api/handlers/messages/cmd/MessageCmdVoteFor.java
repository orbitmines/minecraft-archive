package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdVoteFor extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§b§lVote §8| §7Vote voor §b§lBeloningen§7!";
            case ENGLISH:
                return "§b§lVote §8| §7Vote for §b§lRewards§7!";
            default:
                return null;
        }
    }
}
