package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotRemoveYourself extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan jezelf niet van je eigen cell verwijderen!";
            case ENGLISH:
                return "§7You cannot remove yourself from your own cell!";
            default:
                return null;
        }
    }
}
