package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.handlers.npc.NPCMoving;
import fadidev.orbitmines.api.runnables.OMRunnable;

import java.util.ArrayList;

public abstract class NPCRunnable extends OMRunnable {

	private OrbitMinesAPI api;

    public NPCRunnable() {
        super(TimeUnit.TICK, 3);

        api = OrbitMinesAPI.getApi();
    }

    protected abstract void run(NPCArmorStand npc);
	protected abstract void run(NPC npc);

	@Override
	public void run() {
		for(NPCArmorStand npc : new ArrayList<>(api.getNpcArmorStands())){
			npc.checkEntity();

			run(npc);
		}
		
		for(NPC npc : api.getNpcs()){
			npc.checkEntity();
			
			if(npc instanceof NPCMoving){
			    NPCMoving npcMoving = (NPCMoving) npc;
                npcMoving.moveToLocation(npcMoving.getMovingTo());
            }
			
			run(npc);
		}
	}
}
