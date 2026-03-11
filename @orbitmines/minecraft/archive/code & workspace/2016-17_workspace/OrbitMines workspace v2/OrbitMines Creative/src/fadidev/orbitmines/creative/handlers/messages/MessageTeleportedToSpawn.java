package fadidev.orbitmines.creative.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageTeleportedToSpawn extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Geteleporteerd naar de §6Spawn§7!";
            case ENGLISH:
                return "§7Teleported to §6Spawn§7!";
            default:
                return null;
        }
    }
}
