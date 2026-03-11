package om.fog.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.invs.InventoryInstance;
import om.fog.FoG;
import om.fog.handlers.Quest;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Repeat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestInv extends InventoryInstance {
	
	private FoG fog;
	
	public QuestInv(){
		this.fog = FoG.getInstance();
		
		int size = 9;
		int quests = fog.getQuests().size();
		for(int i = 9; i <= 54; i += 9){
			if(quests <= i){
				size = i;
				break;
			}
		}
		
		Inventory inventory = Bukkit.createInventory(null, size, "§0§lQuests");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player) {
		FoGPlayer omp = FoGPlayer.getFoGPlayer(player);
		if(!omp.isInTutorial() || omp.getTutorial().getStage() >= 9){
			getInventory().setContents(getContents(player));
			player.openInventory(getInventory());
		}
	}
	
	private ItemStack[] getContents(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		FoGPlayer omp = FoGPlayer.getFoGPlayer(player);
		
		List<Quest> allQuests = fog.getQuests();
		List<Quest> currentQuests = omp.getCurrentQuestsAsNewList();
		List<Quest> notActive = new ArrayList<Quest>();
		List<Quest> cannotActivate = new ArrayList<Quest>();
		List<Quest> completed = new ArrayList<Quest>();

		omp.checkQuestCooldowns();
		for(Quest quest : allQuests){
			if(quest.isLevel(omp)){
				if(omp.onQuestCooldown(quest)){
					completed.add(quest);
				}
				else{
					if(!currentQuests.contains(quest)){
						notActive.add(quest);
					}
				}
			}
			else{
				cannotActivate.add(quest);
			}
		}
		
		int slot = 0;
		for(Quest quest : currentQuests){
			ItemStack item = new ItemStack(Material.MAP);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f§l" + quest.getName());
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(" §7Status: §a§lActive ");
			if(quest.isProgressQuest()){
				lore.add(" §7Progress: §a§l" + omp.getQuestProgress(quest) + "§7/§a§l" + quest.getAmount() + " ");
			}
			lore.addAll(quest.getReward());
			lore.add("");
			lore.addAll(quest.getDiscription(omp));
			lore.add("");
			if(quest.canCompleteQuest(omp)){
				lore.add(" §7Click here to complete this quest. ");
			}
			else{
				if(quest.isProgressQuest()){
					lore.add(" §7You have not completed this quest yet. ");
				}
				else{
					lore.add(" §7You cannot complete this quest yet. ");
				}
			}
			lore.add("");
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			contents[slot] = item;
			
			slot++;
		}
		for(Quest quest : notActive){
			ItemStack item = new ItemStack(Material.EMPTY_MAP);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f§l" + quest.getName());
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(" §7Status: §c§lInactive ");
			lore.addAll(quest.getReward());
			lore.add("");
			lore.addAll(quest.getDiscription(omp));
			lore.add("");
			lore.add(" §7Click here to activate this quest. ");
			lore.add("");
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			contents[slot] = item;
			
			slot++;
		}
		for(Quest quest : cannotActivate){
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f§l" + quest.getName());
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(" §7Status: §4§lLocked ");
			lore.addAll(quest.getReward());
			lore.add("");
			lore.addAll(quest.getDiscription(omp));
			lore.add("");
			lore.add(" §7You have to be level " + quest.getLevel() + " for this quest. ");
			lore.add("");
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			contents[slot] = item;
			
			slot++;
		}
		for(Quest quest : completed){
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f§l" + quest.getName());
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(" §7Status: §a§lCompleted ");
			lore.addAll(quest.getReward());
			lore.add("");
			lore.addAll(quest.getDiscription(omp));
			lore.add("");
			if(quest.getRepeat() != Repeat.NEVER){
				lore.add(" §7Available at " + omp.getQuestCooldownDate(quest) + ". ");
			}
			else{
				lore.add(" §7This quest can only be done once. ");
			}
			lore.add("");
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			contents[slot] = item;
			
			slot++;
		}
		
		return contents;
	}
}
