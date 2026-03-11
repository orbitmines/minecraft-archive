package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandNotInvited extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent niet uitgenodigd om een §dIsland§7 te joinen.";
            case ENGLISH:
                return "§7You haven't been invited to an §dIsland§7.";
            default:
                return null;
        }
    }
}
