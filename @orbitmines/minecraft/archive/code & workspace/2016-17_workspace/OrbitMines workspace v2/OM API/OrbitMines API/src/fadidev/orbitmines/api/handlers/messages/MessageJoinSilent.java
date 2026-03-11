package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageJoinSilent extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §a» " + args[0] + "§a is gejoind. §6[Silent Mode]";
            case ENGLISH:
                return " §a» " + args[0] + "§a joined. §6[Silent Mode]";
            default:
                return null;
        }
    }
}
