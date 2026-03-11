package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdTpPlayerCords extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt " + args[0] + "§7 naar §6" + args[1] + "§7, §6" + args[2] + "§7, §6" + args[3] + "§7 geteleporteerd!";
            case ENGLISH:
                return "§7Teleported " + args[0] + "§7 to §6" + args[1] + "§7, §6" + args[2] + "§7, §6" + args[3] + "§7!";
            default:
                return null;
        }
    }
}
