package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageJoinedOrbitMines extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§d" + args[0] + " §7heeft §6§lOrbitMines§4§lNetwork§7 gejoind! §d#" + args[1];
            case ENGLISH:
                return "§d" + args[0] + " §7joined §6§lOrbitMines§4§lNetwork§7! §d#" + args[1];
            default:
                return null;
        }
    }
}
