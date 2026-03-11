package fadidev.orbitmines.prison.handlers.messages;

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
                return "§7Je kan deze kit alleen elke §65 uur§7 gebruiken.";
            case ENGLISH:
                return "§7You can only use this Kit once every §65 hours§7.";
            default:
                return null;
        }
    }
}
