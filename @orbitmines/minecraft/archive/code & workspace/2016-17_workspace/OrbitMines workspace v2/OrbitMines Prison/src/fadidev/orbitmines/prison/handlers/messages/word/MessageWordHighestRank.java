package fadidev.orbitmines.prison.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordHighestRank extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Hoogste Rank";
            case ENGLISH:
                return "Highest Rank";
            default:
                return null;
        }
    }
}
