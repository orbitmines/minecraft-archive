package fadidev.orbitmines.prison.utils.enums;

/**
 * Created by Fadi on 15-9-2016.
 */
public enum ShopType {

    BUY("Buy"),
    SELL("Sell");

    private String name;

    ShopType(String name){
        this.name = name;
    }

    public String getStatusName(boolean sold){
        if(sold)
            return "§4§l" + name;
        else
            return "§2§l" + name;
    }
}
