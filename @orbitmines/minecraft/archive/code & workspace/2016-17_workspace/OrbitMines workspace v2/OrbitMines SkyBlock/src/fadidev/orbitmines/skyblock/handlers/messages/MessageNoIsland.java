package fadidev.orbitmines.skyblock.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageNoIsland extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt geen §dIsland§7!";
            case ENGLISH:
                return "§7You don't have an §dIsland§7!";
            default:
                return null;
        }
    }
}
