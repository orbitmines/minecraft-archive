package me.O_o_Fadi_o_O.OrbitMines.utils.skyblock;

import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;

public class Challenge {

	private int challengeid;
	private ItemData item;
	private List<ItemData> requirements;
	private List<ItemData> rewards;
	
	public Challenge(int challengeid, ItemData item, List<ItemData> requirements, List<ItemData> rewards){
		this.challengeid = challengeid;
		this.item = item;
		this.requirements = requirements;
		this.rewards = rewards;
	}
	
	public int getChallengeID() {
		return challengeid;
	}
	
	public ItemData getItem() {
		return item;
	}
	public void setItem(ItemData item) {
		this.item = item;
	}

	public List<ItemData> getRequirements() {
		return requirements;
	}
	public void setRequirements(List<ItemData> requirements) {
		this.requirements = requirements;
	}

	public List<ItemData> getRewards() {
		return rewards;
	}
	public void setRewards(List<ItemData> rewards) {
		this.rewards = rewards;
	}
	
	public static List<Challenge> getChallenges(){
		return ServerData.getSkyBlock().getChallenges();
	}
	
	public static Challenge getChallenge(int challengeid){
		for(Challenge c : ServerData.getSkyBlock().getChallenges()){
			if(c.getChallengeID() == challengeid){
				return c;
			}
		}
		return null;
	}
	
	public static Challenge addChallenge(int challengeid, ItemData item, List<ItemData> requirements, List<ItemData> rewards){
		Challenge c = new Challenge(challengeid, item, requirements, rewards);
		ServerData.getSkyBlock().getChallenges().add(c);
		return c;
	}
}
