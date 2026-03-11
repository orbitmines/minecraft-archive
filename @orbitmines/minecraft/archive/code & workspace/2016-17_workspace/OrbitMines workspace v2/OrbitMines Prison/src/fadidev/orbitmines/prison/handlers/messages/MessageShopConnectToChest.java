package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageShopConnectToChest extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7De sign moet tegen een chest aan!";
            case ENGLISH:
                return "§7The sign has to be connected to a chest!";
            default:
                return null;
        }
    }
}
