package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageResetMineCooldown extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan een mine alleen resetten elke §6" + args[0] + " minuten§7!";
            case ENGLISH:
                return "§7You can only reset a mine once every §6" + args[0] + " minutes§7!";
            default:
                return null;
        }
    }
}
