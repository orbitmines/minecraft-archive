package noteinclude;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class NPCArmorStand {


	public boolean update(Entity en) {
		if (en instanceof ArmorStand && Hologram.getHologram((ArmorStand) en) == null && en.getWorld().getName().equals(getLocation().getWorld().getName()) && getLocation().distance(en.getLocation()) <= 0.1) {
			this.armorstand = (ArmorStand) en;
			return true;//ook voor NPC
		}
		return false;
	}
}
