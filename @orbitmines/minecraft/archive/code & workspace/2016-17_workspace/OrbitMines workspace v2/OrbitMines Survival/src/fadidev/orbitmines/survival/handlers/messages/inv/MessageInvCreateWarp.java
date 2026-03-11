package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvCreateWarp extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Warp Gemaakt! (§6" + args[0] + "§7)";
            case ENGLISH:
                return "§7Created a Warp! (§6" + args[0] + "§7)";
            default:
                return null;
        }
    }
}
