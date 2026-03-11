package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdFoodbarRestored extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt geen honger meer door " + args[0] + "§7.";
            case ENGLISH:
                return "§7" + args[0] + "§7 restored your §6Hungerbar§7!";
            default:
                return null;
        }
    }
}
