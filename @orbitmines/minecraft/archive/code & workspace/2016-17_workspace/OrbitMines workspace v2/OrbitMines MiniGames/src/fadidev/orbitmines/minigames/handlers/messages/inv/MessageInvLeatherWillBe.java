package fadidev.orbitmines.minigames.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvLeatherWillBe extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Je Leather Armor wordt " + args[0] + "!";
            case ENGLISH:
                return "Your Leather Armor will be " + args[0] + "!";
            default:
                return null;
        }
    }
}
