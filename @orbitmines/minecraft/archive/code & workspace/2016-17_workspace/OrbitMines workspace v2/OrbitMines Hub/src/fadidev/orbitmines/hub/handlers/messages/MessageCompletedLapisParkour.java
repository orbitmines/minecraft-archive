package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCompletedLapisParkour extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 heeft het §1Lapis Parkour§7 gehaald!";
            case ENGLISH:
                return args[0] + "§7 completed the §1Lapis Parkour§7!";
            default:
                return null;
        }
    }
}
