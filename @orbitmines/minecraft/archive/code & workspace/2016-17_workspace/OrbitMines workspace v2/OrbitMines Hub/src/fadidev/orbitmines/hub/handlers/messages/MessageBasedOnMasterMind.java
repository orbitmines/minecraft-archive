package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageBasedOnMasterMind extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Gebaseerd op het bordspel 'MasterMind'";
            case ENGLISH:
                return "§7Based on the boardgame 'MasterMind'";
            default:
                return null;
        }
    }
}
