package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotPreparing extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Nieuw §cCell§7 voorbereiden...";
            case ENGLISH:
                return "§7Preparing new §cCell§7...";
            default:
                return null;
        }
    }
}
