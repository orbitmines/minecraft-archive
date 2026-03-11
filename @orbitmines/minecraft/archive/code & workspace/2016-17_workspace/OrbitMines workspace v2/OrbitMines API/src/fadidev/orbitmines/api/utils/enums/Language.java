package fadidev.orbitmines.api.utils.enums;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 3-9-2016.
 */
public enum Language {

    DUTCH,
    ENGLISH;

    public ItemStack getFlag(String displayName, String... lore){
        ItemStack item = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta) item.getItemMeta();
        meta.setDisplayName(displayName);

        switch(this){
            case DUTCH:
                meta.setBaseColor(DyeColor.WHITE);
                meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
                break;
            case ENGLISH:
                meta.setBaseColor(DyeColor.BLUE);
                meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
                meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT));
                meta.addPattern(new Pattern(DyeColor.RED, PatternType.CROSS));
                meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
                meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
                meta.addPattern(new Pattern(DyeColor.RED, PatternType.STRAIGHT_CROSS));
                break;
        }

        if(lore != null){
            List<String> list = new ArrayList<>();
            for(String s : lore){
                list.add(s);
            }
            meta.setLore(list);
        }

        item.setItemMeta(meta);

        return item;
    }

}
