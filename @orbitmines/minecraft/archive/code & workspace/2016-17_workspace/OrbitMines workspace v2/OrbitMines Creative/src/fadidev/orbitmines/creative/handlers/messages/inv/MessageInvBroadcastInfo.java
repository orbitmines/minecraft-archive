package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvBroadcastInfo extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Gebruik §d/plot pvpbroadcast§7 om je §dPlot§7 te broadcasten!";
            case ENGLISH:
                return "§7Use §d/plot pvpbroadcast§7 to broadcast your §dPlot§7!";
            default:
                return null;
        }
    }
}
