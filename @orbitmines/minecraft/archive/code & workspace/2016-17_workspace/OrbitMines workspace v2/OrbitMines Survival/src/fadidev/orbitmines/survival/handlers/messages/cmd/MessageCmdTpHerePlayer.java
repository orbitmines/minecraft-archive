package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdTpHerePlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 wilt jouw naar hem/haar teleporteren. Type §a/accept§7 om het te accepteren.";
            case ENGLISH:
                return args[0] + "§7 wants to teleport you to them. Type §a/accept§7 to accept.";
            default:
                return null;
        }
    }
}
