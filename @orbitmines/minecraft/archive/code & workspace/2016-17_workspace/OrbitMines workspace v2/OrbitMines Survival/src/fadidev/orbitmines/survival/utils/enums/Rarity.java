package fadidev.orbitmines.survival.utils.enums;

/**
 * Created by Fadi on 10-10-2016.
 */
public enum Rarity {

    COMMON(1),
    UNCOMMON(2),
    RARE(3),
    LEGENDARY(4);

    private int percentage;

    Rarity(int percentage){
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }

    public static Rarity from(int percentage){
        for(Rarity rarity : Rarity.values()){
            if(rarity.getPercentage() == percentage)
                return rarity;
        }

        return Rarity.COMMON;
    }
}
