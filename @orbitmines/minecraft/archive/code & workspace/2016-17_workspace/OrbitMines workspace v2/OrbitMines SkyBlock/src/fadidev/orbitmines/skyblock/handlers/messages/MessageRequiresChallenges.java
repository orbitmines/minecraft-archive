package fadidev.orbitmines.skyblock.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageRequiresChallenges extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §4§lJe moet deze Challenges voltooid hebben: ";
            case ENGLISH:
                return " §4§lRequires the following Challenges: ";
            default:
                return null;
        }
    }
}
