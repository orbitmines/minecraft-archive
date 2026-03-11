package fadidev.orbitmines.hub.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdCageWand extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt de §eCage Builder Wand§7. gekregen";
            case ENGLISH:
                return "§7You've been given the §eCage Builder Wand§7.";
            default:
                return null;
        }
    }
}
