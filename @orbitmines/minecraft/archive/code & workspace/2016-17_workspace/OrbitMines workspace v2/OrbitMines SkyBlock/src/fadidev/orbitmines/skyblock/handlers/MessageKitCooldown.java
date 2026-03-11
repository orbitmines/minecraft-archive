package fadidev.orbitmines.skyblock.handlers;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageKitCooldown extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan deze kit alleen elke 24 uur gebruiken!";
            case ENGLISH:
                return "§7You can only use this kit once every 24 hours!";
            default:
                return null;
        }
    }
}
