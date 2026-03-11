package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdRestoreFoodbarPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 heeft geen honger meer.";
            case ENGLISH:
                return "§7Restored " + args[0] + "'s §6Hungerbar§7!";
            default:
                return null;
        }
    }
}
