package om.api.utils.enums;

public enum Currency {

	ORBITMINES_TOKENS("OMT", "OMT"),
	VIP_POINTS("VIP Point", "VIP Points"),
	MINIGAME_POINTS("MiniGame Point", "MiniGame Points"),
	KITPVP_MONEY("Coin", "Coins"),
	MG_TICKETS("Ticket", "Tickets"),
	PRISON_GOLD("Money", "Money");

	private String single;
	private String multiple;
	
	Currency(String single, String multiple){
		this.single = single;
		this.multiple = multiple;
	}
	
	public String getName(int amount){
		if(amount == 1) return single;
		return multiple;
	}
}

