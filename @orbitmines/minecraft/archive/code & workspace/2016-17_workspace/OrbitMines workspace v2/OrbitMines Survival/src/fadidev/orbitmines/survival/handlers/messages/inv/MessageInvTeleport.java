package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvTeleport extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §3Rechtermuisknop §7- Teleporteren";
            case ENGLISH:
                return " §3Right Click §7- Teleport";
            default:
                return null;
        }
    }
}
