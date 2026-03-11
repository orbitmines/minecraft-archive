package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvSetPvPMode extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Jouw §dPlot§7 is nu in §c§lPvP Mode§7!";
            case ENGLISH:
                return "§7Your §dPlot§7 has been set to §c§lPvP Mode§7!";
            default:
                return null;
        }
    }
}
