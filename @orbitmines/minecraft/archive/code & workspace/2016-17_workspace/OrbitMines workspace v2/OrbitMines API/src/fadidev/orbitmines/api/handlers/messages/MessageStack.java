package fadidev.orbitmines.api.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageStack extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je hebt " + args[0] + "§7 op je hoofd gezet!";
            case ENGLISH:
                return "§7You've §6§lstacked§f " + args[0] + "§7 on your Head!";
            default:
                return null;
        }
    }
}
