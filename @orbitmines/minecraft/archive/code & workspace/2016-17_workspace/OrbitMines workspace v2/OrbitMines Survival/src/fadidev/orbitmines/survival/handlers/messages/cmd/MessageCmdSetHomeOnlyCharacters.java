package fadidev.orbitmines.survival.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdSetHomeOnlyCharacters extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §6home naam§7 kan alleen maar bestaan uit §6letters§7 en §6nummers§7.";
            case ENGLISH:
                return "§7Your §6home name§7 can only contain §6alphabetic§7 and §6numeric§7 characters.";
            default:
                return null;
        }
    }
}
