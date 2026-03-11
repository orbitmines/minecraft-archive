package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdHealed extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent §6geheeld§7 door " + args[0] + "§7.";
            case ENGLISH:
                return "§7You were §6healed§7 by " + args[0] + "§7.";
            default:
                return null;
        }
    }
}
