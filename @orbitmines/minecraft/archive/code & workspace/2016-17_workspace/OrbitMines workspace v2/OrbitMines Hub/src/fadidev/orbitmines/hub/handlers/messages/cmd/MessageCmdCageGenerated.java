package fadidev.orbitmines.hub.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdCageGenerated extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§eCage§7 gemaakt!";
            case ENGLISH:
                return "§eCage§7 generated!";
            default:
                return null;
        }
    }
}
