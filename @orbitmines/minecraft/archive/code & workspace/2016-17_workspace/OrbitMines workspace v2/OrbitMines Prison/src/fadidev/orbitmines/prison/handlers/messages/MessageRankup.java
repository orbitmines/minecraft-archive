package fadidev.orbitmines.prison.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageRankup extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 is nu rank §a§l" + args[1] + "§7!";
            case ENGLISH:
                return args[0] + "§7 ranked up to §a§l" + args[1] + "§7!";
            default:
                return null;
        }
    }
}
