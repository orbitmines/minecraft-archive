package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageRequiredVipRank extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je moet een " + args[1] + " VIP§7 zijn om dit te doen!";
            case ENGLISH:
                return "§7You have to be " + args[0] + " " + args[0] + " VIP§7 to do this!";
            default:
                return null;
        }
    }
}
