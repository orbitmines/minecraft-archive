package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCfInfo extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §a§oVersla al je tegenstanders en blijf als laatste over.";
            case ENGLISH:
                return " §a§oKill other opponents and be the last player alive.";
            default:
                return null;
        }
    }
}
