package fadidev.orbitmines.minigames.utils.enums;

/**
 * Created by Fadi on 8-9-2016.
 */
public enum Uses {

    ONE_TIME("One time use."),
    PERMANENT("Permanent");

    private String name;

    Uses(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
