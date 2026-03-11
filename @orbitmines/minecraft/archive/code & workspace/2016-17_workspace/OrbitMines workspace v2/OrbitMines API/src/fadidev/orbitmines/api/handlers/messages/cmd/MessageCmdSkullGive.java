package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdSkullGive extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt §6" + args[0] + "'s§7 skull ontvangen.";
            case ENGLISH:
                return "§7You've been given §6" + args[0] + "'s§7 skull.";
            default:
                return null;
        }
    }
}
