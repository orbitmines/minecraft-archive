package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotCannotSetHome extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan jouw §dPlot Home§7 hier niet zetten!";
            case ENGLISH:
                return "§7You cannot set your §dPlot Home§7 here!";
            default:
                return null;
        }
    }
}
