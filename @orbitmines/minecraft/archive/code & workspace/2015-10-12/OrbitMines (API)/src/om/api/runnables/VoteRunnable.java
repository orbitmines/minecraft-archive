package om.api.runnables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import om.api.API;
import om.api.handlers.Database;
import om.api.handlers.StringInt;
import om.api.utils.UUIDUtils;

public class VoteRunnable extends OMRunnable {

	private API api;
	
	public VoteRunnable(Duration duration) {
		super(duration);
		
		api = API.getInstance();
	}

	@Override
	protected void run() {
		HashMap<String, Integer> uuidvoters = Database.get().getIntEntries("Votes", "uuid", "votes");
		List<StringInt> playervotes = new ArrayList<StringInt>();
		
		StringInt voter1 = new StringInt(null, 0);
		StringInt voter2 = new StringInt(null, 0);
		StringInt voter3 = new StringInt(null, 0);
		StringInt voter4 = new StringInt(null, 0);
		StringInt voter5 = new StringInt(null, 0);
		
		for(String uuid : uuidvoters.keySet()){
			int votes = uuidvoters.get(uuid);
			if(votes >= voter1.getInteger()){
				voter5 = voter4;
				voter4 = voter3;
				voter3 = voter2;
				voter2 = voter1;
				voter1 = new StringInt(uuid, votes);
			}
			else if(votes >= voter2.getInteger()){
				voter5 = voter4;
				voter4 = voter3;
				voter3 = voter2;
				voter2 = new StringInt(uuid, votes);
			}
			else if(votes >= voter3.getInteger()){
				voter5 = voter4;
				voter4 = voter3;
				voter3 = new StringInt(uuid, votes);
			}
			else if(votes >= voter4.getInteger()){
				voter5 = voter4;
				voter4 = new StringInt(uuid, votes);
			}
			else if(votes >= voter5.getInteger()){
				voter5 = new StringInt(uuid, votes);
			}else{}
		}

		playervotes = Arrays.asList(voter1, voter2, voter3, voter4, voter5);
		
		for(StringInt voter : playervotes){
			if(voter.getString() != null){
				String playername = UUIDUtils.getName(UUID.fromString(voter.getString()));
				
				if(playername != null){
					voter.setString(playername);
				}
			}
		}
		
		api.setVoters(playervotes);
	}
}
