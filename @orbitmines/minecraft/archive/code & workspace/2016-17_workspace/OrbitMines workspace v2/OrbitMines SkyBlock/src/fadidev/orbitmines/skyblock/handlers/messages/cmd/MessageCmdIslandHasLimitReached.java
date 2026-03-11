package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandHasLimitReached extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Dat §dIsland§7 heeft al het maximum aantal members!";
            case ENGLISH:
                return "§7That §dIsland§7 already reached the maximum amount of members!";
            default:
                return null;
        }
    }
}
