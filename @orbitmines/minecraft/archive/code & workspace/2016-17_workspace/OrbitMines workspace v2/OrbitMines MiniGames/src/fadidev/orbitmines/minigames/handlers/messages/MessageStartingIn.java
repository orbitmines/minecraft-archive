package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageStartingIn extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Starten in §6" + args[0] + "§7...";
            case ENGLISH:
                return "§7Starting in §6" + args[0] + "§7...";
            default:
                return null;
        }
    }
}
