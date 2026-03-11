package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdNoHomes extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Geen §6homes§7 om te laten zien. Maak er eentje met §6/sethome <naam>§7.";
            case ENGLISH:
                return "§7No §6homes§7 to display. Create one using §6/sethome <name>§7.";
            default:
                return null;
        }
    }
}
