package om.api.nms;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R3.EntityLiving;

public abstract class PetInstance {

	protected abstract void setYawPitchPet(float yaw, float pitch);
	
	public void handleMovement(EntityLiving pet, float sideMot, float forMot){
	    pet.lastYaw = pet.yaw = pet.passenger.yaw;
	    pet.pitch = pet.passenger.pitch * 0.5F;
	 
	    setYawPitchPet(pet.yaw, pet.pitch);
	    pet.aI = pet.aG = pet.yaw;
	 
	    pet.S = 1.0F;
	 
	    sideMot = (float) (((EntityLiving) pet.passenger).aZ * 0.5F);
	    forMot = (float) ((EntityLiving) pet.passenger).ba;
	    
	    if (forMot <= 0.0F) {
	        forMot *= 0.25F;
	    }
	    sideMot *= 0.75F;
	 
	    float speed = 0.35F;
	    pet.aF = speed;
	}
	
	public void handleJump(EntityLiving pet, float sideMot, float forMot){
	    Field jump = null;
	    try{
			jump = EntityLiving.class.getDeclaredField("aY");
		}catch(NoSuchFieldException ex){
			ex.printStackTrace();
		}catch(SecurityException ex){
			ex.printStackTrace();
		}
	    jump.setAccessible(true);
	 
	    if(jump != null && pet.onGround){
	        try{
	            if(jump.getBoolean(pet.passenger)){
	                double jumpHeight = 0.5D;
	                pet.motY = jumpHeight;
	            }
	        }catch(IllegalAccessException ex){
	            ex.printStackTrace();
	        }
	    }
	}
}
