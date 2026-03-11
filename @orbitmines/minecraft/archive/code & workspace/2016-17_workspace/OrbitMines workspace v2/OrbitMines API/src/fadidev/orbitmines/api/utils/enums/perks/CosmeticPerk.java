package fadidev.orbitmines.api.utils.enums.perks;
import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.inventory.perks.*;
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

	public static CosmeticPerk[] values = CosmeticPerk.values();
	private Material material;
	
	CosmeticPerk(Material material) {
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

	public boolean isEnabled(){
        OrbitMinesServer server = OrbitMinesAPI.getApi().getServerPlugin();

        switch(this){
            case CHATCOLORS:
                return server.chatcolorsEnabled();
            case DISGUISES:
                return server.disguisesEnabled();
            case GADGETS:
                return server.gadgetsEnabled();
            case HATS:
                return server.hatsEnabled();
            case PETS:
                return server.petsEnabled();
            case TRAILS:
                return server.trailsEnabled();
            case WARDROBE:
                return server.wardrobeEnabled();
            case FIREWORKS:
                return server.fireworksEnabled();
            default:
                return false;
        }
    }
}
