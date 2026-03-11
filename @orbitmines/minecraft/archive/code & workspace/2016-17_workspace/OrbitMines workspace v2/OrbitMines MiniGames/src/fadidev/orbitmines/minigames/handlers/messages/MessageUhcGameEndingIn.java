package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageUhcGameEndingIn extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Game eindigt over §6" + args[0] + "m " + args[1] + "s§7.";
            case ENGLISH:
                return "§7Game ending in §6" + args[0] + "m " + args[1] + "s§7.";
            default:
                return null;
        }
    }
}
