package me.O_o_Fadi_o_O.OrbitMinesPoll;

import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.ComponentMessage;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Poll {

	private String poll;
	private Reward reward;
	private List<Option> options;
	
	public Poll(String poll, Reward reward, List<Option> options){
		this.poll = poll;
		this.reward = reward;
		this.options = options;
	}
	
	public String getPoll(){
		return poll;
	}
	
	public Reward getReward(){
		return reward;
	}
	
	public List<Option> getOptions(){
		return options;
	}
	
	public boolean hasVoted(UUID uuid){
		for(Option o : getOptions()){
			if(o.getVotes().contains(uuid)){
				return true;
			}
		}
		return false;
	}
	
	public Option getHighestVotes(){
		Option o = null;
		for(Option op : getOptions()){
			if(o == null || op.getVotes().size() >= o.getVotes().size()){
				o = op;
			}
		}
		return o;
	}
	
	public void sendMessage(Player p){
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		p.sendMessage("");
		p.sendMessage(getPoll());
		
		int index = 1;
		for(Option o : getOptions()){
			ComponentMessage cm = new ComponentMessage();
			cm.addPart("  §7" + index + ". ", null, null, null, null);
			cm.addPart(o.getOption(), Action.RUN_COMMAND, "/pollvote " + o.getCMDOption(), HoverEvent.Action.SHOW_TEXT, "§eClick here to vote for '" + o.getOption() + "§e'");
			cm.send(p);
			
			index++;
		}
		
		p.sendMessage("");
		p.sendMessage("§eYou'll receive " + getReward().getRewardString() + "§e when voting in this Poll.");
	}
}
