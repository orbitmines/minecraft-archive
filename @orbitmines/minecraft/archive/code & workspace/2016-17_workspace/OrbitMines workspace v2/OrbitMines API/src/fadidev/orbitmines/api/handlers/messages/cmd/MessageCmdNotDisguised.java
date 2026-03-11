package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdNotDisguised extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent niet vermomd.";
            case ENGLISH:
                return "§7You aren't §6disguised§7!";
            default:
                return null;
        }
    }
}
