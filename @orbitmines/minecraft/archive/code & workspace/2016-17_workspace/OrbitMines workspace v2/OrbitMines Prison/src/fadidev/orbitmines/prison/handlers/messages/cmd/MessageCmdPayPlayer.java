package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPayPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 heeft je §6§l" + args[1] + " Gold§7 gegeven!";
            case ENGLISH:
                return args[0] + "§7 paid you §6§l" + args[1] + " Gold§7!";
            default:
                return null;
        }
    }
}
