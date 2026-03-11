package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvDisguiseNotEnabled extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt geen Disguise §a§lAAN§7 staan!";
            case ENGLISH:
                return "§7You don't have a Disguise §a§lENABLED§7!";
            default:
                return null;
        }
    }
}
