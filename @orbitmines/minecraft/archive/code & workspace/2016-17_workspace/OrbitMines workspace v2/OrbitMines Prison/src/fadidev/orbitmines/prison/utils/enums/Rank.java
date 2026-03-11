package fadidev.orbitmines.prison.utils.enums;

import fadidev.orbitmines.api.handlers.Kit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Fadi on 19-9-2016.
 */
public enum Rank {

    Z(10000, 1.00, Material.IRON_PICKAXE, 3, 3, 3, Material.IRON_AXE, 3, 3),  //25m
    Y(35000, 1.10, Material.IRON_PICKAXE, 4, 4, 3, Material.IRON_AXE, 3, 3),  //50m
    X(65000, 1.20, Material.IRON_PICKAXE, 4, 4, 4, Material.IRON_AXE, 4, 4),  //1h30m
    W(100000, 1.30, Material.IRON_PICKAXE, 5, 5, 4, Material.IRON_AXE, 4, 4),  //2h
    V(150000, 1.40, Material.IRON_PICKAXE, 5, 5, 4, Material.IRON_AXE, 5, 5),  //3h
    U(250000, 1.50, Material.IRON_PICKAXE, 5, 5, 5, Material.IRON_AXE, 5, 5),  //5h
    T(400000, 1.60, Material.DIAMOND_PICKAXE, 3, 3, 3, Material.DIAMOND_AXE, 3, 3),  //8h
    S(800000, 1.70, Material.DIAMOND_PICKAXE, 4, 4, 3, Material.DIAMOND_AXE, 3, 3),  //15h
    R(1300000, 1.80, Material.DIAMOND_PICKAXE, 4, 4, 4, Material.DIAMOND_AXE, 4, 4),  //30h
    Q(2500000, 1.90, Material.DIAMOND_PICKAXE, 5, 5, 4, Material.DIAMOND_AXE, 4, 4),  //50h
    P(3800000, 2.00, Material.DIAMOND_PICKAXE, 5, 5, 5, Material.DIAMOND_AXE, 4, 4),  //75h
    O(5500000, 2.10, Material.DIAMOND_PICKAXE, 5, 5, 5, Material.DIAMOND_AXE, 5, 5),  //110h
    N(7500000, 2.20, Material.DIAMOND_PICKAXE, 6, 6, 5, Material.DIAMOND_AXE, 5, 5);
  /*  M(0, Material.DIAMOND_PICKAXE, 6, 6, 6, Material.DIAMOND_AXE, 6, 6),
    L(0, Material.DIAMOND_PICKAXE, 7, 7, 6, Material.DIAMOND_AXE, 6, 6),
    K(0, Material.DIAMOND_PICKAXE, 7, 7, 7, Material.DIAMOND_AXE, 7, 7),
    J(0, Material.DIAMOND_PICKAXE, 8, 8, 7, Material.DIAMOND_AXE, 7, 7),
    I(0, Material.DIAMOND_PICKAXE, 8, 8, 7, Material.DIAMOND_AXE, 8, 8),
    H(0, Material.DIAMOND_PICKAXE, 8, 8, 8, Material.DIAMOND_AXE, 8, 8),
    G(0, Material.DIAMOND_PICKAXE, 9, 9, 8, Material.DIAMOND_AXE, 8, 8),
    F(0, Material.DIAMOND_PICKAXE, 9, 9, 8, Material.DIAMOND_AXE, 9, 9),
    E(0, Material.DIAMOND_PICKAXE, 9, 9, 9, Material.DIAMOND_AXE, 9, 9),
    D(0, Material.DIAMOND_PICKAXE, 10, 10, 9, Material.DIAMOND_AXE, 9, 9),
    C(0, Material.DIAMOND_PICKAXE, 10, 10, 9, Material.DIAMOND_AXE, 10, 10),
    B(0, Material.DIAMOND_PICKAXE, 10, 10, 10, Material.DIAMOND_AXE, 10, 10),
    A(0, Material.DIAMOND_PICKAXE, 11, 11, 11, Material.DIAMOND_AXE, 11, 11);*/

    private int rankupPrice;
    private double multiplier;
    private ItemStack pickaxe;
    private ItemStack axe;
    private Kit kit;

    Rank(int rankupPrice, double multiplier, Material pickaxe_material, int pickaxe_dig_speed, int pickaxe_durability, int pickaxe_loot_bonus_block, Material axe_material, int axe_dig_speed, int axe_durability){
        this.rankupPrice = rankupPrice;
        this.multiplier = multiplier;

        this.pickaxe = new ItemStack(pickaxe_material, 1);
        ItemMeta pickaxe_meta = pickaxe.getItemMeta();
        pickaxe_meta.setDisplayName("§9Pickaxe (" + getName() + "§9)");
        pickaxe.setItemMeta(pickaxe_meta);
        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, pickaxe_dig_speed);
        pickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, pickaxe_durability);
        pickaxe.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, pickaxe_loot_bonus_block);

        this.axe = new ItemStack(axe_material, 1);
        ItemMeta axe_meta = axe.getItemMeta();
        axe_meta.setDisplayName("§9Axe (" + getName() + "§9)");
        axe.setItemMeta(axe_meta);
        axe.addUnsafeEnchantment(Enchantment.DIG_SPEED, axe_dig_speed);
        axe.addUnsafeEnchantment(Enchantment.DURABILITY, axe_durability);
    }

    public int getRankupPrice() {
        return rankupPrice;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public String getName(){
        return "§a§l" + toString();
    }

    public ItemStack getPickaxe() {
        return new ItemStack(pickaxe);
    }

    public ItemStack getAxe() {
        return new ItemStack(axe);
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Rank getNextRank(){
        return fromId(ordinal() +1);
    }

    public ItemStack getBanner(boolean unlocked){
        DyeColor letter = DyeColor.WHITE;
        DyeColor background = unlocked ? DyeColor.LIME : DyeColor.RED;

        ItemStack item = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta) item.getItemMeta();

        switch(this){
            case Z:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_DOWNLEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case Y:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_DOWNRIGHT));
                meta.addPattern(new Pattern(background, PatternType.HALF_HORIZONTAL_MIRROR));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_DOWNLEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case X:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.CROSS));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case W:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.TRIANGLE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(background, PatternType.TRIANGLES_BOTTOM));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case V:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_DOWNLEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case U:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case T:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_CENTER));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case S:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_DOWNRIGHT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case R:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.HALF_HORIZONTAL));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_DOWNRIGHT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case Q:
                meta.setBaseColor(letter);
                meta.addPattern(new Pattern(background, PatternType.RHOMBUS_MIDDLE));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(letter, PatternType.SQUARE_BOTTOM_RIGHT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case P:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(background, PatternType.HALF_HORIZONTAL_MIRROR));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_MIDDLE));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case O:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case N:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_DOWNRIGHT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            /*case M:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.TRIANGLES_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(background, PatternType.TRIANGLE_TOP));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case L:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case K:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.CROSS));
                meta.addPattern(new Pattern(letter, PatternType.HALF_VERTICAL));
                meta.addPattern(new Pattern(background, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case J:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(background, PatternType.HALF_HORIZONTAL));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case I:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRAIGHT_CROSS));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case H:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_MIDDLE));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case G:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(background, PatternType.HALF_HORIZONTAL));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_MIDDLE));
                meta.addPattern(new Pattern(background, PatternType.HALF_VERTICAL));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case F:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_MIDDLE));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case E:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_MIDDLE));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case D:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(background, PatternType.CURLY_BORDER));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case C:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case B:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_BOTTOM));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_MIDDLE));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(background, PatternType.BORDER));
                break;
            case A:
                meta.setBaseColor(background);
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_LEFT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_RIGHT));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_MIDDLE));
                meta.addPattern(new Pattern(letter, PatternType.STRIPE_TOP));
                meta.addPattern(new Pattern(background, PatternType.STRIPE_LEFT));
                break;*/
        }

        item.setItemMeta(meta);

        return item;
    }

    public static Rank fromId(int rankId){
        for(Rank rank : Rank.values()){
            if(rank.ordinal() == rankId)
                return rank;
        }
        return null;
    }
}
