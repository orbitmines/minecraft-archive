package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandNowOwner extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent nu de §deigenaar§7 van jouw §dIsland§7!";
            case ENGLISH:
                return "§7You are now the §downer§7 of your §dIsland§7!";
            default:
                return null;
        }
    }
}
