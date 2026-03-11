package fadidev.orbitmines.minigames.handlers.messages;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.minigames.utils.enums.TicketType;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageSgRunnerAttack extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Je kan " + args[0] + "§7 niet aanvallen de eerste 30s! (§f§l" + TicketType.RUNNER_KIT.getName() + "§7)";
            case ENGLISH:
                return "§7You can't attack " + args[0] + "§7 the first 30s! (§f§l" + TicketType.RUNNER_KIT.getName() + "§7)";
            default:
                return null;
        }
    }
}
