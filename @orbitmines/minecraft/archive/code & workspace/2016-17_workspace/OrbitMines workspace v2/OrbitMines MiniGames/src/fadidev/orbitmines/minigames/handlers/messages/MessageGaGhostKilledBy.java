package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageGaGhostKilledBy extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §7§lDe Ghost is gedood door " + args[0] + "§7§l!";
            case ENGLISH:
                return " §7§lThe Ghost has been killed by " + args[0] + "§7§l!";
            default:
                return null;
        }
    }
}
