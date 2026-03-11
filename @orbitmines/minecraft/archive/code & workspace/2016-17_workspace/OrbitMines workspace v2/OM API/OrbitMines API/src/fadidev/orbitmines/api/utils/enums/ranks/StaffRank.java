package fadidev.orbitmines.api.utils.enums.ranks;

public enum StaffRank {

	USER("§8", "§8", "§8", "§fNo Rank"),
	BUILDER("§d§lBuilder §d", "§d", "§dBuilder §f", "§d§lBuilder"),
	MODERATOR("§b§lMod §b", "§b", "§bMod §f", "§b§lModerator"),
	OWNER("§4§lOwner §4", "§4", "§4Owner §f", "§4§lOwner");

    private String chatPrefix;
    private String color;
    private String scoreboardPrefix;
    private String rankString;

    StaffRank(String chatPrefix, String color, String scoreboardPrefix, String rankString){
        this.chatPrefix = chatPrefix;
        this.color = color;
        this.scoreboardPrefix = scoreboardPrefix;
        this.rankString = rankString;
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

