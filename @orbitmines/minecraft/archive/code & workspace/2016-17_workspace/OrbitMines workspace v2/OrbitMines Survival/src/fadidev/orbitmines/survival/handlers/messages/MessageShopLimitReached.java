package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageShopLimitReached extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt al het maximale aantal shops! (§6" + args[0] + "§7)";
            case ENGLISH:
                return "§7You already reached the maximum amount of shops! (§6" + args[0] + "§7)";
            default:
                return null;
        }
    }
}
