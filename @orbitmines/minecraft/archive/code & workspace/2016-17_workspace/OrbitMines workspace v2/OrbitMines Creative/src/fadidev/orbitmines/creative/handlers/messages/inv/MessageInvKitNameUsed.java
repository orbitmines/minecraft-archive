package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvKitNameUsed extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt al een kit met de naam '§a§l" + args[0] + "§7'.";
            case ENGLISH:
                return "§7You already have a Kit named '§a§l" + args[0] + "§7'.";
            default:
                return null;
        }
    }
}
