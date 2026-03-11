package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvChangeWarpName extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt de naam van '§6" + args[0] + "§7' veranderd in '§6" + args[1] + "§7'.";
            case ENGLISH:
                return "§7Changed the name of '§6" + args[0] + "§7' to '§6" + args[1] + "§7'.";
            default:
                return null;
        }
    }
}
