package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageSgRestockChests extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§a§l§oAlle chesten zijn weer bijgevuld!";
            case ENGLISH:
                return "§a§l§oAll Chests have been restocked!";
            default:
                return null;
        }
    }
}
