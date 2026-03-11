package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageRestartingIn extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Server gaat restarten in §6" + args[0] + "§7...";
            case ENGLISH:
                return "§7Restarting server in §6" + args[0] + "§7...";
            default:
                return null;
        }
    }
}
