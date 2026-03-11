package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.StringInt;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.UUIDUtils;

import java.util.*;

public class VoteRunnable extends OMRunnable {

	private OrbitMinesAPI api;

	public VoteRunnable() {
		super(TimeUnit.HOUR, 1);

        api = OrbitMinesAPI.getApi();
	}


	@Override
	public void run() {
		HashMap<String, Integer> uuidVoters = Database.get().getIntEntries("Votes", "uuid", "votes");
		List<StringInt> playerVotes;
		
		StringInt voter1 = new StringInt(null, 0);
		StringInt voter2 = new StringInt(null, 0);
		StringInt voter3 = new StringInt(null, 0);
		StringInt voter4 = new StringInt(null, 0);
		StringInt voter5 = new StringInt(null, 0);

		for(String uuid : uuidVoters.keySet()){
			int votes = uuidVoters.get(uuid);
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
			}
		}

		playerVotes = Arrays.asList(voter1, voter2, voter3, voter4, voter5);
		
		for(StringInt voter : playerVotes){
			if(voter.getString() != null){
				String playerName = UUIDUtils.getName(UUID.fromString(voter.getString()));
				
				if(playerName != null)
					voter.setString(playerName);
			}
		}
		
		api.setVoters(playerVotes);
	}
}
