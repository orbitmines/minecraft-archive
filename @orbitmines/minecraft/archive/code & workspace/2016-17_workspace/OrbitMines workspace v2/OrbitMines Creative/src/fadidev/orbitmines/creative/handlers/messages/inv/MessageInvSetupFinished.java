package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvSetupFinished extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt de §dPlot§7 setup §a§lbeëindigd§7!";
            case ENGLISH:
                return "§7You have §a§lfinished§7 the §dPlot§7 setup!";
            default:
                return null;
        }
    }
}
