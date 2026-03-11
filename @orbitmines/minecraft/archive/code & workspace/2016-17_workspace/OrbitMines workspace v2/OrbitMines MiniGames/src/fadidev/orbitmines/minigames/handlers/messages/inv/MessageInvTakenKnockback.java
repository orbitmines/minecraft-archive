package fadidev.orbitmines.minigames.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvTakenKnockback extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Knockback ontvangen";
            case ENGLISH:
                return "Taken Knockback";
            default:
                return null;
        }
    }
}
