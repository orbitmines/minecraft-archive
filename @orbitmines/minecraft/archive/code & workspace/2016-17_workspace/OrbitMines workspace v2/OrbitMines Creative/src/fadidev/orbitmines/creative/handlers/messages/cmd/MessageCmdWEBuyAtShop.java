package fadidev.orbitmines.creative.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdWEBuyAtShop extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan deze command kopen in de §eOMT Shop§7! (§e/spawn§7)";
            case ENGLISH:
                return "§7You can buy this command at the §eOMT Shop§7! (§e/spawn§7)";
            default:
                return null;
        }
    }
}
