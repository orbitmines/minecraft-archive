package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotJoinPlot extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Plot §d#" + args[0] + "§7 gejoind!";
            case ENGLISH:
                return "§7Joined Plot §d#" + args[0] + "§7!";
            default:
                return null;
        }
    }
}
