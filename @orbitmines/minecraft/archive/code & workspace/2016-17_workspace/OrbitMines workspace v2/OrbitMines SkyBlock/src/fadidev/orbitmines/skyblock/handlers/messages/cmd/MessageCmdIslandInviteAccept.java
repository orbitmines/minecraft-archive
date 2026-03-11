package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandInviteAccept extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Type §d/is accept§7 om te §aaccepteren§7 of §d/is deny§7 om te §cweigeren§7.";
            case ENGLISH:
                return "§7Type §d/is accept§7 to §aAccept§7 or §d/is deny§7 to §cDeny§7.";
            default:
                return null;
        }
    }
}
