package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageTimeRemaining extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return  "§7Tijd over: §6" + args[0] + "§7.";
            case ENGLISH:
                return  "§7Time remaining: §6" + args[0] + "§7.";
            default:
                return null;
        }
    }
}
