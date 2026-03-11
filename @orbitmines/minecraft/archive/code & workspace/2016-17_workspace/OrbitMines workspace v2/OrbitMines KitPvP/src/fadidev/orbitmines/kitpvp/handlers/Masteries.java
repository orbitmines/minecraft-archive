package fadidev.orbitmines.kitpvp.handlers;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.kitpvp.utils.enums.Mastery;
import org.bukkit.entity.Player;

public class Masteries {

	private Player player;
	private int points;
	private int melee;
	private int meleeProtection;
	private int range;
	private int rangeProtection;
	
	public Masteries(Player player, int points, int melee, int meleeProtection, int range, int rangeProtection){
		this.player = player;
		this.points = points;
		this.melee = melee;
		this.meleeProtection = meleeProtection;
		this.range = range;
		this.rangeProtection = rangeProtection;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public int getMelee() {
		return melee;
	}

	public void setMelee(int melee) {
		this.melee = melee;
	}

	public int getMeleeProtection() {
		return meleeProtection;
	}

	public void setMeleeProtection(int meleeProtection) {
		this.meleeProtection = meleeProtection;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getRangeProtection() {
		return rangeProtection;
	}

	public void setRangeProtection(int rangeProtection) {
		this.rangeProtection = rangeProtection;
	}
	
	public int getMasteryLevel(Mastery mastery){
		switch(mastery){
			case MELEE:
				return melee;
			case MELEE_PROTECTION:
				return meleeProtection;
			case RANGE:
				return range;
			case RANGE_PROTECTION:
				return rangeProtection;
			default:
				return 0;
		}
	}

	public double getMasteryEffect(Mastery mastery){
		switch(mastery){
			case MELEE:
				return melee * 0.02;
			case MELEE_PROTECTION:
				return -meleeProtection * 0.02;
			case RANGE:
				return range * 0.04;
			case RANGE_PROTECTION:
				return -rangeProtection * 0.03;
			default:
				return 0.0;
		}
	}
	
	public String toString(){
		return getPoints() + "|" + getMelee() + "|" + getMeleeProtection() + "|" + getRange() + "|" + getRangeProtection();
	}
	
	public void update(){
		Database.get().update("KitPvP-Masteries", "masteries", toString(), "uuid", getPlayer().getUniqueId().toString());
	}
	
	public static Masteries fromString(Player p, String masteries){
		String[] mastery = masteries.split("\\|");
		return new Masteries(p, Integer.parseInt(mastery[0]), Integer.parseInt(mastery[1]), Integer.parseInt(mastery[2]), Integer.parseInt(mastery[3]), Integer.parseInt(mastery[4]));
	}
}
