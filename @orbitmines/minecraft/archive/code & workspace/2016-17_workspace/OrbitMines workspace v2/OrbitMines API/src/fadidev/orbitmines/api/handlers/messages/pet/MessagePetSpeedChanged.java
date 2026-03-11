package fadidev.orbitmines.api.handlers.messages.pet;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessagePetSpeedChanged extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§f" + args[0] +"'s§6 Snelheid§7 veranderd in §6§l" + args[1] + "§7!";
            case ENGLISH:
                return "§7Changed §f" + args[0] +"'s§6 Speed§7 to §6§l" + args[1] + "§7!";
            default:
                return null;
        }
    }
}
