package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCannotPerformCommands extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan geen commands gebruiken in de §2PvP Area§7. (Ga weg met §6/spawn§7)";
            case ENGLISH:
                return "§7You cannot perform commands in the §2PvP Area§7. (Leave with §6/spawn§7)";
            default:
                return null;
        }
    }
}
