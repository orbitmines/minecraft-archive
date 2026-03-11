package fadidev.orbitmines.hub.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdCageGenerating extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§eCage§7 maken...";
            case ENGLISH:
                return "§7Generating §eCage§7...";
            default:
                return null;
        }
    }
}
