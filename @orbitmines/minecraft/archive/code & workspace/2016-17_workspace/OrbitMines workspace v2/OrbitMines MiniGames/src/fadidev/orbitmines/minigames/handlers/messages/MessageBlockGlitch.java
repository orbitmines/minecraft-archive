package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageBlockGlitch extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7§lHet is niet toegestaan te blockglitchen!";
            case ENGLISH:
                return "§7§lDo not try to block glitch!";
            default:
                return null;
        }
    }
}
