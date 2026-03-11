package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdTopMoneyRichest extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§2§lTop 10 rijkste spelers§7:";
            case ENGLISH:
                return "§2§lTop 10 richest Players§7:";
            default:
                return null;
        }
    }
}
