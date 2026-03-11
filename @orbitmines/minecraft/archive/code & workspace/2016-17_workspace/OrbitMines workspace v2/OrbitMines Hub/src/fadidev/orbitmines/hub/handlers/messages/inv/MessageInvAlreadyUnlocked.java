package fadidev.orbitmines.hub.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvAlreadyUnlocked extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Al §a§lOntgrendeld§7! (§f§l+" + args[0] + " Coins§7)";
            case ENGLISH:
                return "§7Already §a§lUnlocked§7! (§f§l+" + args[0] + " Coins§7)";
            default:
                return null;
        }
    }
}
