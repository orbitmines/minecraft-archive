package om.api.utils.enums.ranks;

public enum StaffRank {

	User,
	Builder,
	Moderator,
	Owner;
	
	public String getChatPrefix(){
		switch(this){
			case Builder:
				return "§d§lBuilder §d";
			case Moderator:
				return "§b§lMod §b";
			case Owner:
				return "§4§lOwner §4";
			case User:
				return "§8";
			default:
				return "§8";
		}
	}
	
	public String getColor(){
		switch(this){
			case Builder:
				return "§d";
			case Moderator:
				return "§b";
			case Owner:
				return "§4";
			case User:
				return "§8";
			default:
				return "§8";
		}
	}
	
	public String getScoreboardPrefix(){
		switch(this){
			case Builder:
				return "§dBuilder §f";
			case Moderator:
				return "§bMod §f";
			case Owner:
				return "§4Owner §f";
			case User:
				return "§8";
			default:
				return "§8";
		}
	}
	
	public String getRankString(){
		switch(this){
			case Builder:
				return "§d§lBuilder";
			case Moderator:
				return "§b§lModerator";
			case Owner:
				return "§4§lOwner";
			case User:
				return "§fNo Rank";
			default:
				return "§fNo Rank";
		}
	}
}

