package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageReceiveRank extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent nu een " + args[1] + "§7!";
            case ENGLISH:
                return "§7You are now " + args[0] + " " + args[1] + "§7!";
            default:
                return null;
        }
    }
}
