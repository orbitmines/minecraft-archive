package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandRemoveMember extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§d" + args[0] + "§7 heeft §d" + args[1] + "§7 van jouw §dIsland§7 verwijderd!";
            case ENGLISH:
                return "§d" + args[0] + "§7 removed §d" + args[1] + "§7 from your §dIsland§7!";
            default:
                return null;
        }
    }
}
