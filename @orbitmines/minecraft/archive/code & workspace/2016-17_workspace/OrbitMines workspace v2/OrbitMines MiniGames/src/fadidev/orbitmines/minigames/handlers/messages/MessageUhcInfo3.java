package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageUhcInfo3 extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §a§oDe Border krimpt 1.5 Blocks per seconde na 15 minutes.";
            case ENGLISH:
                return " §a§oThe Border will shrink 1.5 Blocks per second after 15 minutes.";
            default:
                return null;
        }
    }
}
