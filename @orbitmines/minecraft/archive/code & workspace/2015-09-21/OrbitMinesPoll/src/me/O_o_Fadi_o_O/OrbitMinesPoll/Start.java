package me.O_o_Fadi_o_O.OrbitMinesPoll;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.utils.Database;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Currency;
import me.O_o_Fadi_o_O.OrbitMinesPoll.events.JoinEvent;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Start extends JavaPlugin {

	private static Start plugin;
	private me.O_o_Fadi_o_O.OrbitMines.Start om;
	private Poll poll;
	
	public void onEnable(){
		plugin = this;
		
		registerOrbitMines();
		registerPoll();
		registerEvents();
		registerVotes();
	}
	
	public static Start getInstance(){
		return plugin;
	}
	
	public me.O_o_Fadi_o_O.OrbitMines.Start getOM(){
		return om;
	}
	
	public Poll getPoll(){
		return poll;
	}
	
	private void registerVotes(){
		Map<String, String> votes = Database.get().getStringEntries("Poll", "uuid", "vote");
		
		for(String s : votes.keySet()){
			UUID uuid = UUID.fromString(s);
			Option o = Option.getOption(votes.get(s));
			
			if(o != null){
				o.getVotes().add(uuid);
			}
		}
	}
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
	}
	
	private void registerPoll(){
		this.poll = new Poll("§6Poll §7| §eWhich feature should be added next?", 
				new Reward(Currency.VIP_POINTS, 75), 
				Arrays.asList(
					new Option("kitpvp_creates", "§6§lCrates §7(§c§lKitPvP§7)"),
					new Option("kitpvp_skilltree", "§c§lAdvanced Skill Tree §7(§c§lKitPvP§7)"),
					new Option("trailtypes", "§7§lMore Trail Types"),
					new Option("achievements", "§d§lAchievements"),
					new Option("minigames_more", "§f§lMore Minigames"),
					new Option("creative_we", "§e§lMore WorldEdit Commands §7(§d§lCreative§7)"),
					new Option("prison_mines", "§7§lMore Mines §7(§4§lPrison§7)"),
					new Option("survival_regions", "§a§lMore Regions §7(§a§lSurvival§7)")
				
					));
	}
	
	private void registerOrbitMines(){
		om = (me.O_o_Fadi_o_O.OrbitMines.Start) getServer().getPluginManager().getPlugin("OrbitMines");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] a) {
		
		if(cmd.getName().equalsIgnoreCase("pollvote")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				OMPlayer omp = OMPlayer.getOMPlayer(p);
				
				if(omp.isOpMode()){
					if(a.length == 1){
						try{
							Option o = getPoll().getOptions().get(Integer.parseInt(a[0]) -1);
							int amount = o.getVotes().size();
							
							if(amount == 1){
								p.sendMessage(o.getOption() + " §ehas §l" + amount + " Vote§e.");
							}
							else{
								p.sendMessage(o.getOption() + " §ehas §l" + amount + " Votes§e.");
							}
						}catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
							p.sendMessage("§7Invalid Number.");
						}
					}
					else{
						for(Option o : getPoll().getOptions()){
							int amount = o.getVotes().size();
							
							if(amount == 1){
								p.sendMessage(o.getOption() + " §ehas §l" + amount + " Vote§e.");
							}
							else{
								p.sendMessage(o.getOption() + " §ehas §l" + amount + " Votes§e.");
							}
						}
					}
				}
				else{
					if(a.length == 1){
						if(!getPoll().hasVoted(p.getUniqueId())){
							Option o = Option.getOption(a[0]);
							
							if(o != null){
								o.vote(p);
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}
