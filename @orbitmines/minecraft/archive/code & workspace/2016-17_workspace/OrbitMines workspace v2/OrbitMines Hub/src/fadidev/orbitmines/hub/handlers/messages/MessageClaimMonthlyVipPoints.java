package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageClaimMonthlyVipPoints extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan nu je §bMaandelijkse VIP Points§7 claimen.";
            case ENGLISH:
                return "§7You can now claim your §bMonthly VIP Points§7.";
            default:
                return null;
        }
    }
}
