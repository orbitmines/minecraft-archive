package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessagePlayersDisabled extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7heeft §3§lSpelers§7 §c§lUIT§7 staan.";
            case ENGLISH:
                return args[0] + "§7 has §c§lDISABLED §3§lPlayers§7!";
            default:
                return null;
        }
    }
}
