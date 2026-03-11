package om.api.nms.pets;

import om.api.nms.PetInstance;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.World;

public class PetMagmaCube extends EntityMagmaCube {

	private PetInstance pI;
	
	public PetMagmaCube(World world) {
		super(world);
		
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
}
