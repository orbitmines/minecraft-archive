package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvResetSpawnpoints extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Alle §a§lSpawnpoints§7 zijn reset.";
            case ENGLISH:
                return "§7All §a§lSpawnpoints§7 have been reset.";
            default:
                return null;
        }
    }
}
