package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdInvSeeCantView extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan " + args[0] + "'s §7inventory niet bekijken.";
            case ENGLISH:
                return "§7You cannot view " + args[0] + "'s §7inventory.";
            default:
                return null;
        }
    }
}
