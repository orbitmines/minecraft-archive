package fadidev.orbitmines.hub.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageNotUsedAllSlots extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§c§lMindCraft §8| §7Je hebt niet alle kleur slots gebruikt!";
            case ENGLISH:
                return "§c§lMindCraft §8| §7You didn't use all color slots!";
            default:
                return null;
        }
    }
}
