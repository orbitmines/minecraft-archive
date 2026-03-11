package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageSgDeathmatchEndingIn extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§c§lDeathmatch eindigt over §f§l" + args[0] + "m " + args[1] + "s§c§l!";
            case ENGLISH:
                return "§c§lDeathmatch ending in §f§l" + args[0] + "m " + args[1] + "s§c§l!";
            default:
                return null;
        }
    }
}
