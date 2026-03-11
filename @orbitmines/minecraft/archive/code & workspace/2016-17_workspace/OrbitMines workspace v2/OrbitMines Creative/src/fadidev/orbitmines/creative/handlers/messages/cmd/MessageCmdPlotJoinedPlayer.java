package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotJoinedPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§d" + args[0] + "§7 heeft §dPlot #" + args[1] + "§7 gejoind. (§d" + args[2] + "§7/§d" + args[3] + "§7)";
            case ENGLISH:
                return "§d" + args[0] + "§7 joined §dPlot #" + args[1] + "§7. (§d" + args[2] + "§7/§d" + args[3] + "§7)";
            default:
                return null;
        }
    }
}
