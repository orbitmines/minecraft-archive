package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageSetParticleAmount extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §fTrail's §7§lParticle Amount§7 is nu §f§l" + args[0] + "§7";
            case ENGLISH:
                return "§7Set your §fTrail's §7§lParticle Amount§7 to §f§l" + args[0] + "§7.";
            default:
                return null;
        }
    }
}
