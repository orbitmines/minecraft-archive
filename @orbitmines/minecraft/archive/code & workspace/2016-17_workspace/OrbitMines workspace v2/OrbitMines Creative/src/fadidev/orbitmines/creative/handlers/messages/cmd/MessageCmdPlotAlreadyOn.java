package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdPlotAlreadyOn extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je bent al op een §c§lPvP Plot§7! Ga weg met §d/p leave§7.";
            case ENGLISH:
                return "§7You already are on a §c§lPvP Plot§7! Leave with §d/p leave§7.";
            default:
                return null;
        }
    }
}
