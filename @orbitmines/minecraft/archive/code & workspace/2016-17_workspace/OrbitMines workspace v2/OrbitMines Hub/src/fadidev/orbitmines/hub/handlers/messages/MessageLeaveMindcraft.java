package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageLeaveMindcraft extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Kom later is terug om nog eens te spelen!";
            case ENGLISH:
                return "§7Come back an other time to play again!";
            default:
                return null;
        }
    }
}
