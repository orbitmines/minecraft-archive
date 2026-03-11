package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageSoldOut extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan hier niks meer kopen, het is §cuitverkocht§7!";
            case ENGLISH:
                return "§7You can no longer buy here, it's §csold out§7!";
            default:
                return null;
        }
    }
}
