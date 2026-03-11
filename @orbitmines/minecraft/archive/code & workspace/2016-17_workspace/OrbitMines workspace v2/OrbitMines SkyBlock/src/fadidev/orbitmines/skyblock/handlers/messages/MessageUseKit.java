package fadidev.orbitmines.skyblock.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageUseKit extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt de " + args[0] + " VIP Kit§7 gebruikt!";
            case ENGLISH:
                return "§7You used the " + args[0] + " VIP Kit§7!";
            default:
                return null;
        }
    }
}
