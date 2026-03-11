package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandTpDisabled extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§d" + args[0] + "'s Island§7 heeft teleportatie §c§lUIT§7 staan.";
            case ENGLISH:
                return "§d" + args[0] + "'s Island§7 has teleporting §c§lDISABLED§7.";
            default:
                return null;
        }
    }
}
