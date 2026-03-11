package om.fog.handlers;

import java.util.List;

import org.bukkit.Sound;

import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Repeat;

public abstract class Quest {

	private static FoG fog;
	private int id;
	private String name;
	private int level;
	private int exp;
	private Repeat repeat;
	private boolean progressQuest;
	
	protected abstract boolean canComplete(FoGPlayer omp);
	protected abstract void complete(FoGPlayer omp);
	public abstract void giveReward(FoGPlayer omp);
	public abstract List<String> getDiscription(FoGPlayer omp);
	public abstract List<String> getReward();
	public abstract int getAmount();
	
	public Quest(int id, String name, int level, int exp, Repeat repeat, boolean progressQuest){
		fog = FoG.getInstance();
		this.id = id;
		this.name = name;
		this.level = level;
		this.exp = exp;
		this.repeat = repeat;
		this.progressQuest = progressQuest;
		
		fog.getQuests().add(this);
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	public boolean isLevel(FoGPlayer omp){
		return omp.getLevels() >= level;
	}
	
	public int getExp() {
		return exp;
	}
	
	public Repeat getRepeat() {
		return repeat;
	}
	
	public boolean isProgressQuest() {
		return progressQuest;
	}
	
	public boolean canCompleteQuest(FoGPlayer omp){
		return canComplete(omp);
	}
	
	public void completeQuest(FoGPlayer omp){
		complete(omp);
		
		omp.getQuestProgress().remove(this);
		omp.resetQuestCooldown(this);
		sendCompleteMessage(omp);
		omp.addExp(exp);
		giveReward(omp);
		
		if(omp.isInTutorial() && omp.getTutorial().getStage() == 10 && omp.onQuestCooldown(Quest.getByName("First Training")) && omp.onQuestCooldown(Quest.getByName("Copper Mining"))){
			omp.getTutorial().toNextStage();
		}
	}
	
	public void sendCompleteMessage(FoGPlayer omp){
		omp.getPlayer().playSound(omp.getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);
		omp.getPlayer().sendMessage("§7You have successfully completed the §f" + getName() + " §7Quest.");
	}
	
	public void addProgress(FoGPlayer omp){
		omp.addQuestProgress(this);
	}
	
	public static Quest getByName(String name){
		for(Quest quest : fog.getQuests()){
			if(quest.getName().equals(name)){
				return quest;
			}
		}
		return null;
	}
	
	public static Quest getById(int id){
		for(Quest quest : fog.getQuests()){
			if(quest.getId() == id){
				return quest;
			}
		}
		return null;
	}
}
