package fadidev.orbitmines.hub.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdCageCreate extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Nieuwe Cage  gemaakt!";
            case ENGLISH:
                return "§7Created new Cage Builder!";
            default:
                return null;
        }
    }
}
