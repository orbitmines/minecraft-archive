package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdSetWarpOverworld extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan alleen maar warps maken in de overworld!";
            case ENGLISH:
                return "§7You're only allowed to create warps in the overworld!";
            default:
                return null;
        }
    }
}
