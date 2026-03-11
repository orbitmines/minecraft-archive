package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvSetSpawnpoint extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§a§lSpawnpoint #" + args[0] + "§7 op jouw locatie neergezet.";
            case ENGLISH:
                return "§7Set §a§lSpawnpoint #" + args[0] + "§7 to your location.";
            default:
                return null;
        }
    }
}
