package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvRenamePetEmpty extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§fVerander de naam van je §fPet";
            case ENGLISH:
                return "§fRename §fPet";
            default:
                return null;
        }
    }
}
