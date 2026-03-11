package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvCreateKit extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt een Kit gemaakt! (§a§l" + args[0] + "§7)";
            case ENGLISH:
                return "§7Created a Kit! (§a§l" + args[0] + "§7)";
            default:
                return null;
        }
    }
}
