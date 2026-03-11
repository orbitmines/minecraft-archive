package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvSetBuildModePlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§d" + args[0] + "§7 heeft zijn/haar §7§lPlot Mode§7 veranderd in §d§lBuild Mode§7!";
            case ENGLISH:
                return "§d" + args[0] + "§7 set their §7§lPlot Mode§7 to §d§lBuild Mode§7!";
            default:
                return null;
        }
    }
}
