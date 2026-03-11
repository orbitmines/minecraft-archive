package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageNoFireworkPasses extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt geen §6§lFirework Passes§7 meer!";
            case ENGLISH:
                return "§7You don't have any §6§lFirework Passes§7.";
            default:
                return null;
        }
    }
}
