package fadidev.orbitmines.skyblock.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageHaveToFinish extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je moet de volgende challenges voltooid hebben: §d" + args[0] + "§7.";
            case ENGLISH:
                return "§7You have to complete the following challenges: §d" + args[0] + "§7.";
            default:
                return null;
        }
    }
}
