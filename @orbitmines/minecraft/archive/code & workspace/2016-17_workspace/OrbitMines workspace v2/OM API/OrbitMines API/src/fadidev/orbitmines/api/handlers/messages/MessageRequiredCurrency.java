package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageRequiredCurrency extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt nog " + args[0] + args[1] + " " + args[0] + args[2] + "§7 nodig!";
            case ENGLISH:
                return "§7You need " + args[0] + args[1] + "§7 more " + args[0] + args[2] + "§7!";
            default:
                return null;
        }
    }
}
