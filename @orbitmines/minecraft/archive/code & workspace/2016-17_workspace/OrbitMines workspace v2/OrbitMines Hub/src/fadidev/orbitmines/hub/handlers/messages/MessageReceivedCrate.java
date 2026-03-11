package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageReceivedCrate extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt " + args[0] + " gekregen uit deze §6§lSurprise Crate§7!";
            case ENGLISH:
                return "§7You have opened a §6§lSurprise Crate§7! (" + args[0] + "§7)";
            default:
                return null;
        }
    }
}
