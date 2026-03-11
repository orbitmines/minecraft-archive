package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotNotAdded extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Speler §c" + args[0] +" §7heeft geen toegang tot jouw §cCell§7!";
            case ENGLISH:
                return "§7Player §c" + args[0] +" §7hasn't been added to your §cCell§7!";
            default:
                return null;
        }
    }
}
