package fadidev.orbitmines.prison.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdRankupHighest extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt de hoogste rank al bereikt. Meer ranks komen binnenkort!";
            case ENGLISH:
                return "§7You've reached the highest rank. More ranks are coming soon!";
            default:
                return null;
        }
    }
}
