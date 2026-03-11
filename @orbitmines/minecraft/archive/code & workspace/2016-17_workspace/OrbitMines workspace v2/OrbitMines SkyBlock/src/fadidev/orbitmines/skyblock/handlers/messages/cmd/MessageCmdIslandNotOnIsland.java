package fadidev.orbitmines.skyblock.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdIslandNotOnIsland extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Speler §d" + args[0] + "§7 is niet op jouw §dIsland§7!";
            case ENGLISH:
                return "§7Player §d" + args[0] + "§7 isn't on your §dIsland§7!";
            default:
                return null;
        }
    }
}
