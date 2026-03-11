package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdMyWarpsNone extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Geen §6warps§7 om te laten zien. Maak er eentje met §6/setwarp§7.";
            case ENGLISH:
                return "§7No §6warps§7 to display. Create one using §6/setwarp§7.";
            default:
                return null;
        }
    }
}
