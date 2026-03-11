package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotNoLongerAccess extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§d" + args[0] + "§7 heeft geen toegang meer tot jouw §dPlot§7!";
            case ENGLISH:
                return "§d" + args[0] + "§7 can no longer access your §dPlot§7!";
            default:
                return null;
        }
    }
}
