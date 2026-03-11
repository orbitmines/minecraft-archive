package fadidev.orbitmines.api.handlers.messages.pet;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessagePetChangeAgeBaby extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§f" + args[0] +"'s" + args[1] + " Leeftijd§7 veranderd in een " + args[1] + "§lBaby§7!";
            case ENGLISH:
                return "§7Changed §f" + args[0] +"'s" + args[1] + " Age§7 to a " + args[1] + "§lBaby§7!";
            default:
                return null;
        }
    }
}
