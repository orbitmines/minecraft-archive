package fadidev.orbitmines.survival.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageJoinedPvPArea extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent de in §cPvP Area§7 gegaan.";
            case ENGLISH:
                return "§7You've entered the §cPvP Area§7.";
            default:
                return null;
        }
    }
}
