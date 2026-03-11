package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdSetWarpCantCreate extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan geen warps neerzetten! Type §3/donate§7 om erachter te komen hoe je er eentje krijgt!";
            case ENGLISH:
                return "§7You cannot set any warps! Type §3/donate§7 to discover how to create one!";
            default:
                return null;
        }
    }
}
