package om.fog.handlers;

import om.api.handlers.Title;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.ShieldLore;
import om.fog.utils.enums.Suit;
import om.fog.utils.enums.SwordLore;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Tutorial {

	private FoGPlayer omp;
	private int stage;
	private int lastStage;
	
	public Tutorial(FoGPlayer omp, int stage){
		this.omp = omp;
		this.stage = stage;
		this.lastStage = 12;
	}
	
	public void toNextStage(){
		omp.getPlayer().playSound(omp.getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);
		this.stage++;
		
		if(this.stage > lastStage){
			omp.setInTutorial(false);
			omp.getPlayer().sendMessage("§7You have completed the §e§lTutorial§7!");
			omp.getPlayer().sendMessage("§7Good luck on your adventure!");
			
			Title t = new Title("", "§7You have completed the §e§lTutorial§7!", 20, 100, 20);
			t.send(omp.getPlayer());
		}
		else{
			if(this.stage == 4){
				omp.getPlayer().getInventory().addItem(SwordLore.DAMAGE.getEnchantment(1));
			}
			else if(this.stage == 5){
				omp.getPlayer().getInventory().addItem(ShieldLore.SHIELD_REGEN.getEnchantment(1));
			}
			else{}
		}
		
		omp.updateTutorial(omp.getUUID().toString());
	}
	
	public int getStage() {
		return stage;
	}
	
	public void updateScoreboard(Scoreboard b, Objective o){
		switch(stage){
			case 1:
				setScore(b, o, "", 6);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 5);
				setScore(b, o, "§7Click on the Villager to choose", 4);
				setScore(b, o, "§7a Faction.", 3);
				setScore(b, o, " ", 2);
				setScore(b, o, "§cWARNING: §7This cannot be undone", 1);
				setScore(b, o, "  ", 0);
				break;
			case 2:
				setScore(b, o, "", 4);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 3);
				setScore(b, o, "§7Check out the hangar", 2);
				setScore(b, o, "§7to equip your suit!", 1);
				setScore(b, o, " ", 0);
				break;
			case 3:
				setScore(b, o, "", 4);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 3);
				setScore(b, o, "§7Click on the " + Suit.TRAINING_SUIT.getName(omp.getFaction()), 2);
				setScore(b, o, "§7NPC to equip your Suit.", 1);
				setScore(b, o, " ", 0);
				break;
			case 4:
				setScore(b, o, "", 9);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 8);
				setScore(b, o, "§7Apply your Damage I", 7);
				setScore(b, o, "§7Enchantment to your sword.", 6);
				setScore(b, o, " ", 5);
				setScore(b, o, "§7§o(This adds damage to your sword,", 4);
				setScore(b, o, "§7§oEnchantments like that can be bought", 3);
				setScore(b, o, "§7§oat our Shop. They also rarely drop", 2);
				setScore(b, o, "§7§owhen you kill a mob)", 1);
				setScore(b, o, "  ", 0);
				break;
			case 5:
				setScore(b, o, "", 4);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 3);
				setScore(b, o, "§7Do the same with your Repair I", 2);
				setScore(b, o, "§7Enchantment at the Shields tab.", 1);
				setScore(b, o, " ", 0);
				break;
			case 6:
				setScore(b, o, "", 4);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 3);
				setScore(b, o, "§7Click on your Tools tab,", 2);
				setScore(b, o, "§7this will equip your tools.", 1);
				setScore(b, o, " ", 0);
				break;
			case 7:
				setScore(b, o, "", 4);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 3);
				setScore(b, o, "§7Check out the Shop outside.", 2);
				setScore(b, o, "§7You can spend and earn silver there.", 1);
				setScore(b, o, "  ", 0);
				break;
			case 8:
				setScore(b, o, "", 4);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 3);
				setScore(b, o, "§7Look around for the Bank.", 2);
				setScore(b, o, "§7It's the savest place to store items!", 1);
				setScore(b, o, "  ", 0);
				break;
			case 9:
				setScore(b, o, "", 6);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 5);
				setScore(b, o, "§7Open your Inventory.", 4);
				setScore(b, o, "§7Click on the Quests menu.", 3);
				setScore(b, o, "§7Accept the 'First Training' and", 2);
				setScore(b, o, "§7the 'Copper Mining' quest.", 1);
				setScore(b, o, "  ", 0);
				break;
			case 10:
				setScore(b, o, "", 12);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 11);
				if(omp.onQuestCooldown(Quest.getByName("First Training"))){
					setScore(b, o, "§aKill 3 Zombies (LvL 1).", 10);
				}
				else{
					setScore(b, o, "§cKill 3 Zombies (LvL 1).", 10);
				}
				if(omp.onQuestCooldown(Quest.getByName("Copper Mining"))){
					setScore(b, o, "§aMine 10 Copper Ore", 9);
				}
				else{
					setScore(b, o, "§cMine 10 Copper Ore", 9);
				}
				setScore(b, o, " ", 8);
				setScore(b, o, "§7§o(The blue bar above your hotbar", 7);
				setScore(b, o, "§7§oindicates your shield, like an", 6);
				setScore(b, o, "§7§oextra health bar)", 5);
				setScore(b, o, "  ", 4);
				setScore(b, o, "§7§o(You can mine ores by taking your", 3);
				setScore(b, o, "§7§opickaxe in your hand and standing", 2);
				setScore(b, o, "§7§oon a wool block)", 1);
				setScore(b, o, "   ", 0);
				break;
			case 11:
				setScore(b, o, "", 9);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 8);
				setScore(b, o, "§7Open your Inventory.", 7);
				setScore(b, o, "§7Click on the Ore Crafting menu", 6);
				setScore(b, o, "§7and craft some §5Amethyst§7.", 5);
				setScore(b, o, " ", 4);
				setScore(b, o, "§7§o(Crafted ores give a small profit", 3);
				setScore(b, o, "§7§oinstead of just selling the less ", 2);
				setScore(b, o, "§7§ovalueable ores)", 1);
				setScore(b, o, "  ", 0);
				break;
			case 12:
				setScore(b, o, "", 4);
				setScore(b, o, "§e§lTutorial §7(" + stage + "/" + lastStage + ")", 3);
				setScore(b, o, "§7Go back to the Shop to sell", 2);
				setScore(b, o, "§7all ores you just collected.", 1);
				setScore(b, o, " ", 0);
				break;
		}
	}
	
	@SuppressWarnings("deprecation")
	private void setScore(Scoreboard b, Objective o, String s, int score){
		if(s.length() <= 16){
			Score sc = o.getScore(s);
			sc.setScore(score);
		}
		else{
			if(s.length() <= 32){
				String s1 = s.substring(0, 16);
				String s2 = s.substring(16);
				
				Team t = b.registerNewTeam("FoG-Tut" + stage + "-" + score);
				OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(s1);
				t.setSuffix(s2);
				t.addPlayer(op);
				
				Score sc = o.getScore(op.getName());
				sc.setScore(score);
			}
			else{
				String s1 = s.substring(0, 16);
				String s2 = s.substring(16, 32);
				String s3 = s.substring(32);
				
				Team t = b.registerNewTeam("FoG-Tut" + stage + "-" + score);
				OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(s2);
				t.setPrefix(s1);
				t.setSuffix(s3);
				t.addPlayer(op);
				
				Score sc = o.getScore(op.getName());
				sc.setScore(score);
			}
		}
	}
}
