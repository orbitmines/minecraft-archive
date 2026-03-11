package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MessageNewBestStreak extends Message {

    @Override
    public String get(OMPlayer omp, String... args) {
        switch(omp.getLanguage()){
            case DUTCH:
                return "§f§lNieuw Streak Record: §c§l" + args[0];
            case ENGLISH:
                return "§f§lNew Best Streak: §c§l" + args[0];
        }
        return null;
    }
}
