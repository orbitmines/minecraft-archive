package com.orbitmines.minecraft.spigot.servers.fog.enchant;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import com.orbitmines.minecraft.spigot.servers.fog.choice.ChoiceState;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum CustomEnchant {

    AUTO_REPLANT(Choice.ENCHANT_AUTO_REPLANT,  Color.LIME),
    HOMING_ARROW(Choice.ENCHANT_HOMING_ARROW,  Color.AQUA),
    RAIN_ARROWS (Choice.ENCHANT_RAIN_ARROWS,   Color.ORANGE),
    ABSORPTION  (Choice.ENCHANT_ABSORPTION,    Color.BLUE),
    TREEFELLER  (Choice.ENCHANT_CHOP_TREE,     Color.GREEN);

    @Getter private final Choice unlockChoice;
    @Getter private final Color color;

    CustomEnchant(Choice unlockChoice, Color color) {
        this.unlockChoice = unlockChoice;
        this.color = color;
    }

    public String nameKey()          { return "enchant." + name().toLowerCase() + ".name"; }
    public String damagedSuffixKey() { return "enchant.damaged_suffix"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }

    /** Format enchant name for lore, applying strikethrough/red when DAMAGED. */
    public String loreLine(Languageable viewer, ChoiceState state, int level) {
        String displayName = getDisplayName(viewer);
        String levelStr = level <= 1 ? "" : " " + roman(level);
        if (state == ChoiceState.DAMAGED) {
            return ChatColor.DARK_RED + "§m" + displayName + levelStr + "§r " + ChatColor.RED + "§l" + viewer.translate("fog", damagedSuffixKey());
        }
        return color.getCc() + displayName + levelStr;
    }

    public static List<String> describe(ItemStack stack) {
        List<String> out = new ArrayList<>();
        if (stack == null) return out;
        ItemMeta meta = stack.getItemMeta();
        if (meta == null || !meta.hasLore()) return out;
        out.addAll(meta.getLore());
        return out;
    }

    private static String roman(int n) {
        return switch (n) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            default -> Integer.toString(n);
        };
    }
}
