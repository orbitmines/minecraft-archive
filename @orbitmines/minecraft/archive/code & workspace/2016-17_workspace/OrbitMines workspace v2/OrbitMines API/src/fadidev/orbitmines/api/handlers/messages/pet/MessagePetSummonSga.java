package fadidev.orbitmines.api.handlers.messages.pet;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessagePetSummonSga extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return args[0] + "§7 heeft een §6§lSnowman Attack§7 gespawned!";
            case ENGLISH:
                return args[0] + "§7 summoned a §6§lSnowman Attack§7!";
            default:
                return null;
        }
    }
}
