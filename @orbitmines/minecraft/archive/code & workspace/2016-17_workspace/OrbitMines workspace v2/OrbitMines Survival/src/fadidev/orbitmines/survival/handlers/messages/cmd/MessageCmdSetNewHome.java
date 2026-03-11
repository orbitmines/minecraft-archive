package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdSetNewHome extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Nieuwe home §6neergezet§7! (§6" + args[0] + "§7)";
            case ENGLISH:
                return "§7New home §6set§7! (§6" + args[0] + "§7)";
            default:
                return null;
        }
    }
}
