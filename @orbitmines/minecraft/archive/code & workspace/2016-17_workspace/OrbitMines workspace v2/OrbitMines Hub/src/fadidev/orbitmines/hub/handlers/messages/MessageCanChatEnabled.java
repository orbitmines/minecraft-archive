package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCanChatEnabled extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Chat staat nu §a§lAAN§7!";
            case ENGLISH:
                return "§7Chat §a§lENABLED§7!";
            default:
                return null;
        }
    }
}
