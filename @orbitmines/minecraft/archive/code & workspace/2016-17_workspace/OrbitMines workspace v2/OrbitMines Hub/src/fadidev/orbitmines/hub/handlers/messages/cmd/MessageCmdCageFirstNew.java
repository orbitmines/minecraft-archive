package fadidev.orbitmines.hub.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdCageFirstNew extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je moet eerst een nieuwe Cage Builder maken!";
            case ENGLISH:
                return "§7You have to create a new Cage Builder first!";
            default:
                return null;
        }
    }
}
