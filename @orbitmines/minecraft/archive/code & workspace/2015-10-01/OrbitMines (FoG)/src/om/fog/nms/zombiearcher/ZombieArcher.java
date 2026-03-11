package om.fog.nms.zombiearcher;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustEvent;

public class ZombieArcher extends EntityZombie implements IRangedEntity {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZombieArcher(World world) {
		super(world);
		
        List goalB = (List)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        //this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
        //this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));
        //this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        //this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));
	}

    protected void initAttributes() {
		super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
    }
	
	@SuppressWarnings("rawtypes")
	public static Object getPrivateField(String fieldName, Class clazz, Object object){
        Field field;
        Object o = null;

        try{
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }catch(NoSuchFieldException e){e.printStackTrace();}catch(IllegalAccessException e){e.printStackTrace();}

        return o;
    }

	@Override
	public void a(EntityLiving entityliving, float f) {
	       EntityArrow entityarrow = new EntityArrow(this.world, this, entityliving, 1.6F, (float) (14 - this.world.getDifficulty().a() * 4));
	        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, this.bA());
	        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, this.bA());

	        entityarrow.b((double) (f * 2.0F) + this.random.nextGaussian() * 0.25D + (double) ((float) this.world.getDifficulty().a() * 0.11F));
	        if (i > 0) {
	            entityarrow.b(entityarrow.j() + (double) i * 0.5D + 0.5D);
	        }

	        if (j > 0) {
	            entityarrow.setKnockbackStrength(j);
	        }

	        if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bA()) > 0) {
	            // CraftBukkit start - call EntityCombustEvent
	            EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
	            this.world.getServer().getPluginManager().callEvent(event);

	            if (!event.isCancelled()) {
	                entityarrow.setOnFire(event.getDuration());
	            }
	            // CraftBukkit end
	        }

	        // CraftBukkit start
	        org.bukkit.event.entity.EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(this, this.bA(), entityarrow, 0.8F);
	        if (event.isCancelled()) {
	            event.getProjectile().remove();
	            return;
	        }

	        if (event.getProjectile() == entityarrow.getBukkitEntity()) {
	            world.addEntity(entityarrow);
	        }
	        // CraftBukkit end

	        this.makeSound("random.bow", 1.0F, 1.0F / (this.bc().nextFloat() * 0.4F + 0.8F));
	        // this.world.addEntity(entityarrow); // CraftBukkit - moved up
	}
}
