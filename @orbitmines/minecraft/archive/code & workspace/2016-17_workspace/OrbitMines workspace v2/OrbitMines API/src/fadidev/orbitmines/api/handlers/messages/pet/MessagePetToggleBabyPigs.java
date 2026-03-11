package fadidev.orbitmines.api.handlers.messages.pet;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Language;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessagePetToggleBabyPigs extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je §dBaby Pigs§7 staat nu " + Utils.statusString(Language.ENGLISH, !player.hasPetBabyPigs()) + "§7.";
            case ENGLISH:
                return Utils.statusString(Language.ENGLISH, !player.hasPetBabyPigs()) + "§7 the §dBaby Pigs§7!";
            default:
                return null;
        }
    }
}
