package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdEditWarpNotAllowed extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt geen warp genaamd '§6" + args[0] + "§7'.";
            case ENGLISH:
                return "§7You don't possess a warp named '§6" + args[0] + "§7'.";
            default:
                return null;
        }
    }
}
