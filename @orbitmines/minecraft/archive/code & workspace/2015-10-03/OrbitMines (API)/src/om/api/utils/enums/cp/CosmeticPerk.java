package om.api.utils.enums.cp;

import om.api.invs.cp.ChatColorInv;
import om.api.invs.cp.DisguiseInv;
import om.api.invs.cp.FireworkInv;
import om.api.invs.cp.GadgetInv;
import om.api.invs.cp.HatInv;
import om.api.invs.cp.PetInv;
import om.api.invs.cp.TrailInv;
import om.api.invs.cp.WardrobeInv;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum CosmeticPerk {

	CHATCOLORS(Material.INK_SACK),
	DISGUISES(Material.SKULL_ITEM),
	GADGETS(Material.COMPASS),
	HATS(Material.PUMPKIN),
	PETS(Material.MONSTER_EGG),
	TRAILS(Material.STRING),
	WARDROBE(Material.LEATHER_CHESTPLATE),
	FIREWORKS(Material.FIREWORK);
	
	private Material material;
	
	private CosmeticPerk(Material material) {
		this.material = material;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void openInv(Player p){
		switch(this){
			case CHATCOLORS: 
				new ChatColorInv().open(p); 
				break;
			case DISGUISES: 
				new DisguiseInv().open(p);
				break;
			case FIREWORKS: 
				new FireworkInv().open(p);
				break;
			case GADGETS: 
				new GadgetInv().open(p);
				break;
			case HATS: 
				new HatInv().open(p);
				break;
			case PETS: 
				new PetInv().open(p);
				break;
			case TRAILS: 
				new TrailInv().open(p);
				break;
			case WARDROBE: 
				new WardrobeInv().open(p);
				break;
		}
	}
}
