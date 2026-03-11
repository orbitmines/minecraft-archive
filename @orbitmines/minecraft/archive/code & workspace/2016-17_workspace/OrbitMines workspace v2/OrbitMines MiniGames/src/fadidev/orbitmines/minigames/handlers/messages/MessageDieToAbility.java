package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageDieToAbility extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan " + args[0] + "§7 niet gebruiken! (Je hebt te weinig health)";
            case ENGLISH:
                return "§7You cannot use " + args[0] + "§7! (It will kill you)";
            default:
                return null;
        }
    }
}
