package fadidev.orbitmines.api.handlers.messages.inv;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageInvAchievedAtOMBirthday2016 extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§aBehaalt met de verjaardag van OrbitMines 2016";
            case ENGLISH:
                return "§aAchieved at the birthday of OrbitMines 2016";
            default:
                return null;
        }
    }
}
