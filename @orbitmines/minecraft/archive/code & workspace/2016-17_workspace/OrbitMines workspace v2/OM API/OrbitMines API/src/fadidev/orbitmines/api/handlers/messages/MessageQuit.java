package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageQuit extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §c« " + args[0] + "§c is weggegaan.";
            case ENGLISH:
                return " §c« " + args[0] + "§c left.";
            default:
                return null;
        }
    }
}
