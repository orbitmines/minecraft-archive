package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageEnablePet extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je " + args[0] + "§7 staat nu §a§lAAN§7.";
            case ENGLISH:
                return "§a§lENABLED §7" + args[0] +"§7!";
            default:
                return null;
        }
    }
}
