package fadidev.orbitmines.minigames.handlers.messages.word;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageWordTotalKills extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Kills in totaal";
            case ENGLISH:
                return "Total Kills";
            default:
                return null;
        }
    }
}
