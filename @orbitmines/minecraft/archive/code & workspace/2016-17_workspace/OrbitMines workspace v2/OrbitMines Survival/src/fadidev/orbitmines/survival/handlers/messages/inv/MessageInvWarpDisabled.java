package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvWarpDisabled extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Die warp staat §c§lUIT§7!";
            case ENGLISH:
                return "§7That warp has been §c§lDISABLED§7!";
            default:
                return null;
        }
    }
}
