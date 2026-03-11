package fadidev.orbitmines.skyblock.handlers;

import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;

import java.util.ArrayList;
import java.util.List;

public class Challenge {

    private static OrbitMinesSkyBlock skyBlock;
	private int challengeId;
	private ItemData item;
	private List<ItemData> requirements;
	private List<ItemData> rewards;
	private List<Challenge> required;
	private int[] requiredIds;
	
	public Challenge(int challengeId, ItemData item, List<ItemData> requirements, List<ItemData> rewards, int... required){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
		this.challengeId = challengeId;
		this.item = item;
		this.requirements = requirements;
		this.rewards = rewards;
		this.requiredIds = required;
		this.required = new ArrayList<>();
	}
	
	public int getChallengeID() {
		return challengeId;
	}
	
	public ItemData getItem() {
		return item;
	}

	public List<ItemData> getRequirements() {
		return requirements;
	}

	public List<ItemData> getRewards() {
		return rewards;
	}

	public List<Challenge> getRequired() {
		return required;
	}
	
	public void updateRequired(){
		if(requiredIds == null)
		    return;

        for(int i : requiredIds){
            Challenge c = Challenge.getChallenge(i);
            this.required.add(c);
        }
	}
	
	public boolean canComplete(SkyBlockPlayer omp){
		for(Challenge c : this.required){
			if(omp.getChallengeCompleted(c) == 0)
				return false;
		}
		return true;
	}
	
	public static Challenge getChallenge(int challengeId){
		for(Challenge c : skyBlock.getChallenges()){
			if(c.getChallengeID() == challengeId)
				return c;
		}
		return null;
	}
}
