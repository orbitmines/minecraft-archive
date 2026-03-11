package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCannotPerformCommandsJoining extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan geen commands gebruiken terwijl je de §2PvP Area§7 joint.";
            case ENGLISH:
                return "§7You cannot perform commands while joining the §2PvP Area§7.";
            default:
                return null;
        }
    }
}
