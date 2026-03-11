package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageUhcInfo1 extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §a§oVerzamel items.";
            case ENGLISH:
                return " §a§oGather recources.";
            default:
                return null;
        }
    }
}
