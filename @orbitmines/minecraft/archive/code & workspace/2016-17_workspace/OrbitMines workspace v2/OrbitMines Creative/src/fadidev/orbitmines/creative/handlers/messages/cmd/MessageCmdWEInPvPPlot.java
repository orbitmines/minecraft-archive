package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdWEInPvPPlot extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent op een §c§lPvP Plot§7. Je ken hier geen §dWorldEdit§7 gebruiken!";
            case ENGLISH:
                return "§7You're in a §c§lPvP Plot§7. You cannot use §dWorldEdit§7 here!";
            default:
                return null;
        }
    }
}
