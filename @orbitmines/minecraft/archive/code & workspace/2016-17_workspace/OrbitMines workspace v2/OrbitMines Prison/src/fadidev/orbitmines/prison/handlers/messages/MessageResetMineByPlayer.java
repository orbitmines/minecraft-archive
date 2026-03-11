package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageResetMineByPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Mine " + args[0] + "§7 is gereset door " + args[1] + "§7!";
            case ENGLISH:
                return "§7Mine " + args[0] + "§7 has been reset by " + args[1] + "§7!";
            default:
                return null;
        }
    }
}
