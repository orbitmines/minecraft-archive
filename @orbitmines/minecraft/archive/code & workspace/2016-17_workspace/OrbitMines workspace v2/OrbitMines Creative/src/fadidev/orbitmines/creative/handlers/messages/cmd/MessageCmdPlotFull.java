package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotFull extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Dat Plot is vol! (§d" + args[0] + "§7/§d" + args[1] + "§7)";
            case ENGLISH:
                return "§7That Plot is full! (§d" + args[0] + "§7/§d" + args[1] + "§7)";
            default:
                return null;
        }
    }
}
