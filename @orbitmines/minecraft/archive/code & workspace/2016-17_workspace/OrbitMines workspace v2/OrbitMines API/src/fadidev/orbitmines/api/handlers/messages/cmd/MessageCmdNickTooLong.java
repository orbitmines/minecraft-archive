package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdNickTooLong extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §6nickname§7 kan niet langer dan §630 karakters§7 zijn!";
            case ENGLISH:
                return "§7Your §6nickname§7 cannot be longer than §630 characters§7!";
            default:
                return null;
        }
    }
}
