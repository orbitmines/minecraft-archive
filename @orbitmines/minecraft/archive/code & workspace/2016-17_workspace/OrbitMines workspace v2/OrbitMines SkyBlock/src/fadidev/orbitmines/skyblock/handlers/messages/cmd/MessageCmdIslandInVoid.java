package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandInVoid extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan dat niet in de Void doen§7!";
            case ENGLISH:
                return "§7You cannot do that in the Void§7!";
            default:
                return null;
        }
    }
}
