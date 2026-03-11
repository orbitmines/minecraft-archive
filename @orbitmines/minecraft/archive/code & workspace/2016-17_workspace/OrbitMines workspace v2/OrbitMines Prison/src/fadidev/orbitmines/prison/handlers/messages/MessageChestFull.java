package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageChestFull extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan hier niks meer verkopen, de chest zit vol§7!";
            case ENGLISH:
                return "§7You can no longer sell here, the chest is §cfull§7!";
            default:
                return null;
        }
    }
}
