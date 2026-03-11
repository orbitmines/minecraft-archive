package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandLimitReached extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Jouw §dIsland§7 heeft al het maximum aantal members! (§6" + args[0] + "§7)";
            case ENGLISH:
                return "§7Your §dIsland§7 already reached the maximum amount of members! (§6" + args[0] + "§7)";
            default:
                return null;
        }
    }
}
