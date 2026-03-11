package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvRemoveFavorite extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §3Linkermuisknop §7- §cVerwijder van favoriten";
            case ENGLISH:
                return " §3Left Click §7- §cRemove from favorites";
            default:
                return null;
        }
    }
}
