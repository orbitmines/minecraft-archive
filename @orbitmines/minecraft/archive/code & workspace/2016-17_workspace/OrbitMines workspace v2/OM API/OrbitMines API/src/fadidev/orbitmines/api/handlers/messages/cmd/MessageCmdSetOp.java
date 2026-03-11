package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Language;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdSetOp extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §4§lOP Mode§7 staat nu " + Utils.statusString(Language.DUTCH, player.isOpMode()) + "§7!";
            case ENGLISH:
                return "§7Your §4§lOP Mode§7 has been " + Utils.statusString(Language.ENGLISH, player.isOpMode()) + "§7!";
            default:
                return null;
        }
    }
}
