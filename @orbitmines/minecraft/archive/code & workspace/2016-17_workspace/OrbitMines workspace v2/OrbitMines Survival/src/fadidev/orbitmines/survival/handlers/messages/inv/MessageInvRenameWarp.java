package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvRenameWarp extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§e§lHernoem Warp";
            case ENGLISH:
                return "§e§lRename Warp";
            default:
                return null;
        }
    }
}
