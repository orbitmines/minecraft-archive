package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvUsesUnlockedArmor extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7§o(Gebruikt je vergrendelde Armor)";
            case ENGLISH:
                return "§7§o(Uses your unlocked Armor)";
            default:
                return null;
        }
    }
}
