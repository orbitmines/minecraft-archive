package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageSgInfo extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return " §a§oVerzamel items van chesten en versla al je tegenstanders!";
            case ENGLISH:
                return " §a§oGather loot from chests and kill all opponents!";
            default:
                return null;
        }
    }
}
