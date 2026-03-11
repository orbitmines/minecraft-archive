package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdInvalidAfkUsage extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Ongeldige Command. (§6/afk §7of §6/afk <reden>§7)";
            case ENGLISH:
                return "§7Invalid Usage. (§6/afk §7or §6/afk <reason>§7)";
            default:
                return null;
        }
    }
}
