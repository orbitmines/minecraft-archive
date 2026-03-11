package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCooldownLeftClick extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§e§lLinkermuisknop";
            case ENGLISH:
                return "§e§lLeft Click";
            default:
                return null;
        }
    }
}
