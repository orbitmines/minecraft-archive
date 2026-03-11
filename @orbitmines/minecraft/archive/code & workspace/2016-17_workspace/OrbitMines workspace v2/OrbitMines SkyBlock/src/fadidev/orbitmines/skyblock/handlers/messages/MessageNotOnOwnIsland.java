package fadidev.orbitmines.skyblock.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageNotOnOwnIsland extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent niet op je eigen §dIsland§7!";
            case ENGLISH:
                return "§7You're not on your own §dIsland§7!";
            default:
                return null;
        }
    }
}
