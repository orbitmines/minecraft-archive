package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotResetCooldown extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan je §cCell§7 alleen elke de §c15 minuten§7 resetten.";
            case ENGLISH:
                return "§7You may only clear your §cCell§7 once every §c15 minutes§7.";
            default:
                return null;
        }
    }
}
