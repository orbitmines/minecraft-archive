package om.fog.utils.enums;

public enum Repeat {

	DAILY(86400000L),
	WEEKLY(604800000L),
	MONTHLY(2592000000L),
	NEVER(-1);
	
	private long cooldown;
	
	Repeat(long cooldown){
		this.cooldown = cooldown;
	}
	
	public long getCooldown() {
		return cooldown;
	}
}
