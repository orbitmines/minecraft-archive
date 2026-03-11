package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandRemoveYourself extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan jezelf niet verwijderen van je eigen §dIsland§7!";
            case ENGLISH:
                return "§7You can't remove yourself from your own §dIsland§7!";
            default:
                return null;
        }
    }
}
