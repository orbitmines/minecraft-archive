package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.handlers.npc.NPCMoving;
import fadidev.orbitmines.api.runnables.OMRunnable;

public class NPCMovingRunnable extends OMRunnable {

	private OrbitMinesAPI api;

	public NPCMovingRunnable() {
		super(TimeUnit.SECOND, 1);

		api = OrbitMinesAPI.getApi();
	}

	@Override
	public void run() {
		for(NPC npc : api.getNpcs()){
			if(!(npc instanceof NPCMoving))
			    continue;

            NPCMoving npcMoving = (NPCMoving) npc;
            
            if(npcMoving.getMoveLocations().size() > 0){
                if(npcMoving.getMovingTo() != null){
                    if(npcMoving.isAtLocation(npcMoving.getMovingTo())){
                        int index = npcMoving.getMovingToIndex();
                        npcMoving.setSecondsToStay(npcMoving.getSecondsToStay() -1);

                        if(npcMoving.getSecondsToStay() == 0){
                            npcMoving.setMovingTo(npcMoving.getNextLocation());
                            npcMoving.setSecondsToStay(npcMoving.getSecondsToStay(npcMoving.getMovingTo()));

                            if(npcMoving.getSecondsToStay() == 0){
                                npcMoving.setMovingTo(npcMoving.getNextLocation());
                                npcMoving.setSecondsToStay(npcMoving.getSecondsToStay(npcMoving.getMovingTo()));
                            }
                        }
                        else{
                            int seconds = npcMoving.getSecondsToStay();

                            npcMoving.arrive(index, seconds);
                        }
                    }
                }
                else{
                    npcMoving.setMovingTo(npcMoving.getNextLocation());
                }
            }
		}
	}
}
