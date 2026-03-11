package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotCanAccessPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt nu toegang tot §c" + args[0] + "'s Cell§7!";
            case ENGLISH:
                return "§7You can now access §c" + args[0] + "'s Cell§7!";
            default:
                return null;
        }
    }
}
