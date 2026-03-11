package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotTpToPlot extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Geteleporteerd naar §d" + args[0] + "'s Plot§7.";
            case ENGLISH:
                return "§7Teleported to §d" + args[0] + " Plot§7.";
            default:
                return null;
        }
    }
}
