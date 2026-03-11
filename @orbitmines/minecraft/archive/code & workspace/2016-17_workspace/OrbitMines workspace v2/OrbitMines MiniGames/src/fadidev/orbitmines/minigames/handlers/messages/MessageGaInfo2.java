package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageGaInfo2 extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §a§oSpelers: Dood de Ghost, je kan het af en toe zien.";
            case ENGLISH:
                return " §a§oPlayers: Kill the Ghost, you'll be able to see it every once in a while.";
            default:
                return null;
        }
    }
}
