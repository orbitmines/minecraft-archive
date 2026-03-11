package fadidev.orbitmines.creative.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageTeleportingToSpawn extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Teleporteren naar §6Spawn§7 in §6" + args[0] + "§7...";
            case ENGLISH:
                return "§7Teleporting to §6Spawn§7 in §6" + args[0] + "§7...";
            default:
                return null;
        }
    }
}
