package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotHomeSet extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§dPlot Home§7 veranderd naar jouw locatie!";
            case ENGLISH:
                return "§dPlot Home§7 set!";
            default:
                return null;
        }
    }
}
