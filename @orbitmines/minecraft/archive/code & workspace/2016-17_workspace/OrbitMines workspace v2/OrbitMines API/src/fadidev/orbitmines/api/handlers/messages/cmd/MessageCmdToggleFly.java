package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Language;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdToggleFly extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §fFly§7 mode staat nu " + Utils.statusString(Language.DUTCH, player.getPlayer().getAllowFlight()) + "§7.";
            case ENGLISH:
                return Utils.statusString(Language.ENGLISH, player.getPlayer().getAllowFlight()) + " §7your §fFly§7 mode!";
            default:
                return null;
        }
    }
}
