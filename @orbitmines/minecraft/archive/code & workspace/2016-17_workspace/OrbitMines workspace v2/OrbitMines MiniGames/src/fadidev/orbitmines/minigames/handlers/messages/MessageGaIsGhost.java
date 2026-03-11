package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageGaIsGhost extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 is de §7§lGhost§7!";
            case ENGLISH:
                return args[0] + "§7 is the §7§lGhost§7!";
            default:
                return null;
        }
    }
}
