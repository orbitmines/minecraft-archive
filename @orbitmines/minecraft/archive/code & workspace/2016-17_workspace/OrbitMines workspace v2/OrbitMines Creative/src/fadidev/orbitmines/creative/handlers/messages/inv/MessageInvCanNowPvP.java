package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvCanNowPvP extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan nu je §dPlot§7 naar de §c§lPvP Mode§7 doen met §d/plot info§7.";
            case ENGLISH:
                return "§7You can now set your §dPlot§7 to §c§lPvP Mode§7 using §d/plot info§7.";
            default:
                return null;
        }
    }
}
