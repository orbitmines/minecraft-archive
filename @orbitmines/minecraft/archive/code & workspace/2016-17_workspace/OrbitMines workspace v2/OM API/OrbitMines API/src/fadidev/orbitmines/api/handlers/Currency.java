package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.Material;

/**
 * Created by Fadi on 4-9-2016.
 */
public class Currency {

    private static OrbitMinesAPI api;
    private String singleName;
    private String multipleName;
    private String color;
    private Material material;

    public Currency(String singleName, String multipleName, String color, Material material){
        api = OrbitMinesAPI.getApi();
        this.singleName = singleName;
        this.multipleName = multipleName;
        this.color = color;
        this.material = material;
    }

    public String getSingleName() {
        return singleName;
    }

    public String getMultipleName() {
        return multipleName;
    }

    public String getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName(int amount){
        return amount == 1 ? singleName : multipleName;
    }

    public static Currency getCurrency(Material material){
        for(Currency currency : api.getCurrencies()){
            if(currency.getMaterial() == material)
                return currency;
        }
        return null;
    }
}
