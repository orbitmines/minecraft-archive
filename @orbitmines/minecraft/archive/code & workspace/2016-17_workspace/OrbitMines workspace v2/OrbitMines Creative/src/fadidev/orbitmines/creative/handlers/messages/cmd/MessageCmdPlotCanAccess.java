package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotCanAccess extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§d" + args[0] + "§7 heeft nu toegang tot jouw plot!";
            case ENGLISH:
                return "§d" + args[0] + "§7 can now access your plot!";
            default:
                return null;
        }
    }
}
