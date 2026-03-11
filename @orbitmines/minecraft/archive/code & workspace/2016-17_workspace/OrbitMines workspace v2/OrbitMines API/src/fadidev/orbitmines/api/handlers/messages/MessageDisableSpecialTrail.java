package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageDisableSpecialTrail extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §7§lSpecial Trail§7 staat nu §c§lUIT§7.";
            case ENGLISH:
                return "§a§lDISABLED §7your §7§lSpecial Trail§7.";
            default:
                return null;
        }
    }
}
