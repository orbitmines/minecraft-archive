package fadidev.orbitmines.kitpvp.runnables;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.StringInt;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TopKillsRunnable extends OMRunnable {

	private OrbitMinesKitPvP kitPvP;

	public TopKillsRunnable() {
		super(TimeUnit.HOUR, 1);

        kitPvP = OrbitMinesKitPvP.getKitPvP();
	}


	@Override
	public void run() {
		HashMap<String, Integer> uuidKills = Database.get().getIntEntries("KitPvP-Kills", "uuid", "kills");
		List<StringInt> topKills;
		
		StringInt top1 = new StringInt(null, 0);
		StringInt top2 = new StringInt(null, 0);
		StringInt top3 = new StringInt(null, 0);

		for(String uuid : uuidKills.keySet()){
			int kills = uuidKills.get(uuid);
			if(kills >= top1.getInteger()){
				top3 = top2;
				top2 = top1;
				top1 = new StringInt(uuid, kills);
			}
			else if(kills >= top2.getInteger()){
				top3 = top2;
				top2 = new StringInt(uuid, kills);
			}
			else if(kills >= top3.getInteger()){
				top3 = new StringInt(uuid, kills);
			}
		}

		topKills = Arrays.asList(top1, top2, top3);

		for(StringInt top : topKills){
			if(top.getString() != null){
				String playerName = UUIDUtils.getName(UUID.fromString(top.getString()));
				
				if(playerName != null)
					top.setString(playerName);
			}
		}
		
		kitPvP.setTopKills(topKills);
	}
}
