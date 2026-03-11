package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandRemove extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt §d" + args[0] + "§7 van je §dIsland§7 verwijderd!";
            case ENGLISH:
                return "§7You removed §d" + args[0] + "§7 from your §dIsland§7!";
            default:
                return null;
        }
    }
}
