package fadidev.orbitmines.minigames.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvAppleTreeKit extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "+1 Apple elke 4m.";
            case ENGLISH:
                return "+1 Apple every 4m.";
            default:
                return null;
        }
    }
}
