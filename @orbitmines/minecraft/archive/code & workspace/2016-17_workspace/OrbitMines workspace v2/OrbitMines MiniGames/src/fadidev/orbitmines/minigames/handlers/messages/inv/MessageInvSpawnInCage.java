package fadidev.orbitmines.minigames.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvSpawnInCage extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "Spawn in de " + args[0] + " Cage.";
            case ENGLISH:
                return "Spawn in the " + args[0] + " Cage.";
            default:
                return null;
        }
    }
}
