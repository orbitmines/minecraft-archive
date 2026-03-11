package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotNoMoney extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7In moet §6§l25.000 Gold§7 betalen om een §ccell§7 te kopen!";
            case ENGLISH:
                return "§7In order to buy a §ccell§7 you have to pay §6§l25.000 Gold§7!";
            default:
                return null;
        }
    }
}
