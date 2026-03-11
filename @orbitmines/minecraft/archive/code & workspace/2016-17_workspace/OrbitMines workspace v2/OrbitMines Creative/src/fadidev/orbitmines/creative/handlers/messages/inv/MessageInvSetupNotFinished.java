package fadidev.orbitmines.creative.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvSetupNotFinished extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je moet de PvP Setup beëindigen om de §c§lPvP Mode§7 aan te zetten!";
            case ENGLISH:
                return "§7You have to finish the PvP Setup to enable §c§lPvP Mode§7!";
            default:
                return null;
        }
    }
}
