package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Language;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdToggleFlyPlayer extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "'s §fFly§7 mode staat nu " + Utils.statusString(Language.DUTCH, Boolean.parseBoolean(args[1])) + "§7.";
            case ENGLISH:
                return Utils.statusString(Language.ENGLISH, Boolean.parseBoolean(args[1])) + " " + args[0] + "'s §fFly§7 mode!";
            default:
                return null;
        }
    }
}
