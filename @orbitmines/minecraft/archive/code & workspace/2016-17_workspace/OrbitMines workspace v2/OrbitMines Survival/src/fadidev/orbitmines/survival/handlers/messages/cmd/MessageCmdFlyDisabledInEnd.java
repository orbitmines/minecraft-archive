package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdFlyDisabledInEnd extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Het is niet toegestaan om in de " + args[0] + "§7 te vliegen.";
            case ENGLISH:
                return "§7You are not allowed to fly in the " + args[0] + "§7.";
            default:
                return null;
        }
    }
}
