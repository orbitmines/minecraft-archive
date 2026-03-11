package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotHasNoPlot extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§c" + args[0] + "§7 heeft geen §cCell§7!";
            case ENGLISH:
                return "§c" + args[0] + "§7 doesn't have a §cCell§7!";
            default:
                return null;
        }
    }
}
