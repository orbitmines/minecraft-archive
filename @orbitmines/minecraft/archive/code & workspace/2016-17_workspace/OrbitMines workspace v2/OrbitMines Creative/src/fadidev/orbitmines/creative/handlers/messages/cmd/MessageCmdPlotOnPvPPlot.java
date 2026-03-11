package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotOnPvPPlot extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent op een §c§lPvP Plot§7! Gebruik §d/plot leave§7 om weg te gaan!";
            case ENGLISH:
                return "§7You are in a §c§lPvP Plot§7! Use §d/plot leave§7 to leave!";
            default:
                return null;
        }
    }
}
