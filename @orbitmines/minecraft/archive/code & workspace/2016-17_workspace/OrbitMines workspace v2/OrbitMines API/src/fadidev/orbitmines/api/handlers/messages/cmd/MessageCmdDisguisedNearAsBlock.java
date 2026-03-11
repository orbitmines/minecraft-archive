package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdDisguisedNearAsBlock extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Spelers in de buurt vermomd (§a" + args[0] + "§7) §7als: §aBlock§7. (§a" + args[1] + "§7)";
            case ENGLISH:
                return "§7Disguised near players (§a" + args[0] + "§7) §7as: §aBlock§7. (§a" + args[1] + "§7)";
            default:
                return null;
        }
    }
}
