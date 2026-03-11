package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotInBuildMode extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Jouw §dPlot§7 moet in §d§lBuild Mode§7 zijn om dit te doen!";
            case ENGLISH:
                return "§7Your §dPlot§7 has to be in §d§lBuild Mode§7!";
            default:
                return null;
        }
    }
}
