package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageStackDisabled extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt §6§lStacking§7 §c§lUIT§7 staan! Zet het aan in je §c§nInstellingen§7!";
            case ENGLISH:
                return "§7You §c§lDISABLED§6§l stacking§7! Enable it in your §c§nSettings§7!";
            default:
                return null;
        }
    }
}
