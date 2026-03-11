package om.api.runnables;

import om.api.API;
import om.api.handlers.NPC;
import om.api.handlers.NPCArmorStand;
import om.api.utils.enums.NPCType;

public abstract class NPCRunnable extends OMRunnable {

	private API api;
	
	public NPCRunnable(Duration duration) {
		super(duration);
		
		this.api = API.getInstance();
	}
	
	protected abstract void run(NPCArmorStand npc);
	protected abstract void run(NPC npc);

	@Override
	protected void run() {
		for(NPCArmorStand npc : api.getNPCArmorStands()){
		    npc.checkEntity();

			run(npc);
		}
		
		for(NPC npc : api.getNPCs()){
			npc.checkEntity();
			
			if(npc.getMovingTo() != null){
				npc.moveToLocation(npc.getMovingTo());
			}
			
			if(npc.getNPCType() != NPCType.HOT_WING_KIT){
				npc.getEntity().setFireTicks(0);
			}
			
			run(npc);
		}
	}
}
