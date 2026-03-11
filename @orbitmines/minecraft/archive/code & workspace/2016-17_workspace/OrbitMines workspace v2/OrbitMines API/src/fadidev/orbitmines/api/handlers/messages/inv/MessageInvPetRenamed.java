package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvPetRenamed extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt de naam van " + args[0] + "§7 veranderd in §f" + args[1] + "§7!";
            case ENGLISH:
                return "§7Changed " + args[0] + "§7's name to §f" + args[1] + "§7!";
            default:
                return null;
        }
    }
}
