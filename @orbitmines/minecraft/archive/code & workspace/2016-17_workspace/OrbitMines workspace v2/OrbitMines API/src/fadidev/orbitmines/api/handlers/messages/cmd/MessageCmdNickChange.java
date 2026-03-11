package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdNickChange extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §6nickname§7 is veranderd in '§a" + args[0] + "§7'.";
            case ENGLISH:
                return "§7Changed your §6nickname§7 to '§a" + args[0] + "§7'.";
            default:
                return null;
        }
    }
}
