package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageUhcPvPEnabledIn extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§cPvP§7 staat aan over §6" + args[0] + "m " + args[1] + "s§7.";
            case ENGLISH:
                return "§cPvP§7 enabled in §6" + args[0] + "m " + args[1] + "s§7.";
            default:
                return null;
        }
    }
}
