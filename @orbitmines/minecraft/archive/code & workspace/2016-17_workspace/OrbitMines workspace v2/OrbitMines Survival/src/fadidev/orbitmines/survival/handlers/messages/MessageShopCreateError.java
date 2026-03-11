package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageShopCreateError extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Kan de Chest Shop niet maken. Ga naar §6/spawn§7 voor een tutorial.";
            case ENGLISH:
                return "§7Unable to create Chest Shop. Go to §6/spawn§7 for a tutorial.";
            default:
                return null;
        }
    }
}
