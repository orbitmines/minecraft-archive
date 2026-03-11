package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCloseToRegion extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan dat niet zo dichtbij een Region doen!";
            case ENGLISH:
                return "§7You cannot do such things that close to a Region!";
            default:
                return null;
        }
    }
}
