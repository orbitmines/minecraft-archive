package om.api.nms.pets;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import om.api.nms.PetInstance;

public class PetSpider extends EntitySpider {

	private PetInstance pI;

	@SuppressWarnings("rawtypes")
	public PetSpider(World world) {
		super(world);
		
        List goalB = (List) getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List targetB = (List) getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
		
		pI = new PetInstance(){
			
			@Override
			protected void setYawPitchPet(float yaw, float pitch) {
				setYawPitch(yaw, pitch);
			}
		};
	}

	@Override
	public void g(float sideMot, float forMot) {
	    if(this.passenger == null || !(this.passenger instanceof EntityHuman)){
	        super.g(sideMot, forMot);
	        this.S = 0.5F;
	        return;
	    }
	 
	    pI.handleMovement(this, sideMot, forMot);
	    super.g(sideMot, forMot);
	 
	    pI.handleJump(this, sideMot, forMot);
	}
	
	@SuppressWarnings("rawtypes")
	public Object getPrivateField(String fieldName, Class clazz, Object object){
        Field field;
        Object o = null;

        try{
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }catch(NoSuchFieldException e){e.printStackTrace();}catch(IllegalAccessException e){e.printStackTrace();}

        return o;
    }
}
