package om.fog.managers;

import om.api.invs.cp.ChatColorInv;
import om.api.invs.cp.PetInv;
import om.api.invs.cp.TrailInv;
import om.api.invs.cp.TrailSettingsInv;
import om.api.managers.ClickManager;
import om.api.utils.enums.cp.ChatColor;
import om.api.utils.enums.cp.Trail;
import om.api.utils.enums.cp.TrailType;
import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;
import om.fog.invs.FactionSelectInv;
import om.fog.utils.enums.Faction;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FoGClickManager extends ClickManager {

	private FoG fog;
	private FoGPlayer omp;
	
	public FoGClickManager(InventoryClickEvent e) {
		super(e);
		
		fog = FoG.getInstance();
		omp = FoGPlayer.getFoGPlayer(p);
	}

	@Override
	protected void checkCancelItems(ItemStack bitem) {
		if(bitem.getItemMeta().getDisplayName().contains("Trail") && !bitem.getItemMeta().getDisplayName().equals("§7Hat Block Trail") && !bitem.getItemMeta().getDisplayName().startsWith("§7§l")){
			new TrailInv().open(p);	
		}
		else if(bitem.getItemMeta().getDisplayName().endsWith("ChatColor")){
			new ChatColorInv().open(p);	
		}
		else if(bitem.getItemMeta().getDisplayName().endsWith("Pet")){
			new PetInv().open(p);	
		}
		else if(bitem.getItemMeta().getDisplayName().endsWith("Trail") && bitem.getItemMeta().getDisplayName().startsWith("§7§l")){
			new TrailSettingsInv().open(p);	
		}
		else{}
	}

	@Override
	protected void checkConfirmItems(ItemStack bitem, ItemStack pitem) {
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		p.sendMessage("§7Item Bought: " + bitem.getItemMeta().getDisplayName() + "§7.");
		p.sendMessage("§7Price: " + pitem.getItemMeta().getDisplayName().substring(9) + "§7.");
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		
		if(bitem.getItemMeta().getDisplayName().contains("Bold ChatColor")){
			omp.removeVIPPoints(3000);
			omp.setUnlockedBold(true);
			new ChatColorInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().contains("Cursive ChatColor")){
			omp.removeVIPPoints(2000);
			omp.setUnlockedCursive(true);
			new ChatColorInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§7§lSpecial Trail")){
			omp.removeVIPPoints(750);
			omp.setUnlockedSpecialTrail(true);
			new TrailSettingsInv().open(p);
		}
		else{
			for(Trail trail : Trail.values()){
				if(bitem.getItemMeta().getDisplayName().equals(trail.getName())){
					omp.removeVIPPoints(trail.getPrice());
					omp.addTrail(trail);
					new TrailInv().open(p);
					return;
				}
			}
			for(ChatColor chatcolor : ChatColor.values()){
				if(bitem.getItemMeta().getDisplayName().equals(chatcolor.getName())){
					omp.removeVIPPoints(chatcolor.getPrice());
					omp.addChatColor(chatcolor);
					new ChatColorInv().open(p);
					return;
				}
			}
			for(TrailType trailtype : TrailType.values()){
				if(bitem.getItemMeta().getDisplayName().equals(trailtype.getName())){
					omp.removeVIPPoints(trailtype.getPrice());
					omp.addTrailType(trailtype);
					new TrailSettingsInv().open(p);
					return;
				}
			}
		}
	}
	
	public boolean handleFactionSelector(){
		if(e.getInventory().getName().equals(new FactionSelectInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull() && item.getType() == Material.STAINED_CLAY){
				switch(item.getDurability()){
					case 4:
						omp.joinFaction(Faction.ALPHA);
						break;
					case 3:
						omp.joinFaction(Faction.BETA);
						break;
					case 14:
						omp.joinFaction(Faction.OMEGA);
						break;
				}
			}
			
			return true;
		}
		return false;
	}
}
