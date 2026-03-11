package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvAchievedAtChristmas extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§aBehaalt met kerst 2014";
            case ENGLISH:
                return "§aAchieved at Christmas 2014";
            default:
                return null;
        }
    }
}
