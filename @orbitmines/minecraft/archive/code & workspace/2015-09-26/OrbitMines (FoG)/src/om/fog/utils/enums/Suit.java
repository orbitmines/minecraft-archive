package om.fog.utils.enums;

public enum Suit {

	TRAINING_SUIT(-1, "Training Suit", "I.P.U.", "Mymari", 10.0, 1.00, 1.00, 1.00),
	DEFENDER_SUIT(100000, "Defender Suit", "E.R.P.U.", "Prophysa", 24.0, 1.15, 1.20, 2.00),
	SHIELD_SUIT(325000, "Shield Suit", "I.U.P.U.", "Euryco", 50.0, 1.30, 0.70, 0.90),
	SPEEDSTER_SUIT(600000, "Speedster Suit", "Q.M.A.P.U.", "Paratar", 32.0, 1.50, 1.50, 1.40),
	EXO_SUIT(1500000, "Exo Suit", "U.D.P.U.", "Disrapter", 40.0, 1.75, 1.35, 1.50);
	
	private int price;
	private String alphaName;
	private String betaName;
	private String omegaName;
	private double baseHealth;
	private double extraDamage;
	private double extraSpeed;
	private double extraToolSpeed;
	
	Suit(int price, String alphaName, String betaName, String omegaName, double baseHealth, double extraDamage, double extraSpeed, double extraToolSpeed){
		this.price = price;
		this.alphaName = alphaName;
		this.betaName = betaName;
		this.omegaName = omegaName;
		this.baseHealth = baseHealth;
		this.extraDamage = extraDamage;
		this.extraSpeed = extraSpeed;
		this.extraToolSpeed = extraToolSpeed;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getAlphaName() {
		return alphaName;
	}
	
	public String getBetaName() {
		return betaName;
	}
	
	public String getOmegaName() {
		return omegaName;
	}
	
	public double getBaseHealth() {
		return baseHealth;
	}
	
	public double getExtraDamage() {
		return extraDamage;
	}
	
	public double getExtraSpeed() {
		return extraSpeed;
	}
	
	public double getExtraToolSpeed() {
		return extraToolSpeed;
	}
}
