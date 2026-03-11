package fadidev.orbitmines.hub.utils.enums;

import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.hub.handlers.MiniGameArena;

/**
 * Created by Fadi on 8-9-2016.
 */
public enum State {

    WAITING("§a"),
    STARTING("§a"),
    WARMUP("§6"),
    IN_GAME("§6"),
    ENDING("§8"),
    RESTARTING("§8"),
    CLOSED("§4");

    private String color;

    State(String color){
        this.color = color;
    }

    public String getStatus(MiniGameArena arena, Language language){
        switch(this){
            case CLOSED:
                switch (language){
                    case DUTCH:
                        return "Dicht";
                    case ENGLISH:
                        return "Closed";
                }
            case ENDING:
                switch (language){
                    case DUTCH:
                        return "Restarten";
                    case ENGLISH:
                        return "Restarting";
                }
            case IN_GAME:
                return "Spectate";
            case RESTARTING:
                switch (language){
                    case DUTCH:
                        return "Restarten";
                    case ENGLISH:
                        return "Restarting";
                }
            case STARTING:
                if(arena.getPlayers() == arena.getType().getMaxPlayers()) {
                    switch (language){
                        case DUTCH:
                            return "Vol";
                        case ENGLISH:
                            return "Full";
                    }
                }
                return "Join";
            case WAITING:
                if(arena.getPlayers() == arena.getType().getMaxPlayers()) {
                    switch (language){
                        case DUTCH:
                            return "Vol";
                        case ENGLISH:
                            return "Full";
                    }
                }
                return "Join";
            case WARMUP:
                return "Spectate";
            default:
                return null;
        }
    }

    public String getColor() {
        return color;
    }
}
