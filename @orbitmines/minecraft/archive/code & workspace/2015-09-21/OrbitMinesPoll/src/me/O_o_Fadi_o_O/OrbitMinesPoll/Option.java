package me.O_o_Fadi_o_O.OrbitMinesPoll;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.utils.Database;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Option {

	private String cmdoption;
	private String option;
	private List<UUID> votes;
	
	public Option(String cmdoption, String option){
		this.cmdoption = cmdoption;
		this.option = option;
		this.votes = new ArrayList<UUID>();
	}
	
	public Option(String cmdoption, String option, List<UUID> votes){
		this.cmdoption = cmdoption;
		this.option = option;
		this.votes = votes;
	}
	
	public String getCMDOption(){
		return cmdoption;
	}
	
	public String getOption(){
		return option;
	}
	
	public List<UUID> getVotes(){
		return votes;
	}
	
	public void vote(Player p){
		getVotes().add(p.getUniqueId());
		Reward reward = Start.getInstance().getPoll().getReward();
		
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		p.sendMessage("");
		p.sendMessage("§eThanks you for your vote!");
		p.sendMessage("§eYou voted for '" + getOption() + "§e'.");
		reward.sendMessage(p);
		p.sendMessage("");
		
		Database.get().insert("Poll", "uuid`, `vote", p.getUniqueId().toString() + "', '" + getCMDOption());
		reward.give(p);
	}
	
	public static Option getOption(String cmdoption){
		for(Option o : Start.getInstance().getPoll().getOptions()){
			if(o.getCMDOption().equalsIgnoreCase(cmdoption)){
				return o;
			}
		}
		return null;
	}
}
