package fadidev.orbitmines.api.handlers.messages.cmd;

import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 3-9-2016.
 */
public class MessageCmdLongAfkText extends Message {

    @Override
    public String get(OMPlayer player, String... args) {
        switch(player.getLanguage()){
            case DUTCH:
                return "§7Jouw §6afk tekst§7 kan niet langer dan §620 karakters§7 zijn!";
            case ENGLISH:
                return "§7Your §6afk text§7 can't be longer than §620 characters§7!";
            default:
                return null;
        }
    }
}
