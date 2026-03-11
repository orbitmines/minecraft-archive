package fadidev.orbitmines.survival.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvOnlyCharacters extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Alleen §6letters§7 en §6cijfers§7 zijn toegestaan!";
            case ENGLISH:
                return "§7Only §6alphabetic§7 and §6numeric§7 characters are allowed!";
            default:
                return null;
        }
    }
}
