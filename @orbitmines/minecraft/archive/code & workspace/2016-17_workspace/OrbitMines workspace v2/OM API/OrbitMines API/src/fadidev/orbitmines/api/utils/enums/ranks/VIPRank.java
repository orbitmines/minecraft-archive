package fadidev.orbitmines.api.utils.enums.ranks;

import org.bukkit.Material;

public enum VIPRank {

	USER(null, 0, "§8", "§8", "§f", "§fNo Rank"),
	IRON_VIP(Material.IRON_INGOT,250, "§7§lIron §7", "§7", "§7Iron §f", "§7§lIron"),
	GOLD_VIP(Material.GOLD_INGOT,500, "§6§lGold §6", "§6", "§6Gold §f", "§6§lGold"),
	DIAMOND_VIP(Material.DIAMOND,1250, "§9§lDiamond §9", "§9", "§9Diamond §f", "§9§lDiamond"),
	EMERALD_VIP(Material.EMERALD,2500, "§a§lEmerald §a", "§a", "§aEmerald §f", "§a§lEmerald");

	private Material material;
    private int monthlyBonus;
    private String chatPrefix;
    private String color;
    private String scoreboardPrefix;
    private String rankString;

    VIPRank(Material material, int monthlyBonus, String chatPrefix, String color, String scoreboardPrefix, String rankString){
        this.material = material;
        this.monthlyBonus = monthlyBonus;
        this.chatPrefix = chatPrefix;
        this.color = color;
        this.scoreboardPrefix = scoreboardPrefix;
        this.rankString = rankString;
    }

    public Material getMaterial() {
        return material;
    }

    public int getMonthlyBonus() {
        return monthlyBonus;
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getColor() {
        return color;
    }

    public String getScoreboardPrefix() {
        return scoreboardPrefix;
    }

    public String getRankString() {
        return rankString;
    }
}

