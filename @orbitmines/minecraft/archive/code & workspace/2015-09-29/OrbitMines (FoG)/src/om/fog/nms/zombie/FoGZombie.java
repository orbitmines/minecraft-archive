package om.fog.nms.zombie;

import om.fog.utils.enums.Mob;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

public class FoGZombie extends Zombie {

	private double maxHealth;
	private double attackDamage;
	
	public FoGZombie(World world, int level) {
		super(world);
		
		this.maxHealth = Mob.ZOMBIE.getMaxHealth(level);
		this.attackDamage = Mob.ZOMBIE.getAttackDamage(level);
	}
	
	protected void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(maxHealth);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(attackDamage);
	}
}
