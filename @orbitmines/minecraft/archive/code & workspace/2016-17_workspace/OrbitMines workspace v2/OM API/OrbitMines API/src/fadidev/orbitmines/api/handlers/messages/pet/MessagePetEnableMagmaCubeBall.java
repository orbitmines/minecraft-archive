package fadidev.orbitmines.api.handlers.messages.pet;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessagePetEnableMagmaCubeBall extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§cMagmaCube Voetbal§7 staat nu §a§lAAN§7. §eRechtermuisknop§7 om het uit te zetten. §eLinkermuisknop§7 om het te schieten.";
            case ENGLISH:
                return "§a§lENABLED§7 your §cMagmaCube Ball§7. §eRight Click§7 to remove it, §eLeft Click§7 to shoot it.";
            default:
                return null;
        }
    }
}
