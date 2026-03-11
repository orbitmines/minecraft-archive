package fadidev.orbitmines.creative.handlers.messages.inv;

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
                return "§7Alleen §dletters§7 en §dgetallen§7 zijn toegestaan!";
            case ENGLISH:
                return "§7Only §dalphabetic§7 and §dnumeric§7 characters are allowed!";
            default:
                return null;
        }
    }
}
