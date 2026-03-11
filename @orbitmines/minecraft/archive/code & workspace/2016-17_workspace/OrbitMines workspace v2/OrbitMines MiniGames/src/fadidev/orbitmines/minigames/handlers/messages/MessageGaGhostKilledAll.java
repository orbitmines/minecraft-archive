package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageGaGhostKilledAll extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §7§lDe Ghost heeft alle spelers geëlimineerd!";
            case ENGLISH:
                return " §7§lThe Ghost elimated all players!";
            default:
                return null;
        }
    }
}
