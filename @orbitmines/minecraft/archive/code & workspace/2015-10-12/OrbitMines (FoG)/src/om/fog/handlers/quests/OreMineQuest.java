package om.fog.handlers.quests;

import om.fog.handlers.Quest;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Ore;
import om.fog.utils.enums.Repeat;

public abstract class OreMineQuest extends Quest {
	
	public OreMineQuest(int id, String name, int level, int exp, Repeat repeat, boolean progressQuest) {
		super(id, name, level, exp, repeat, progressQuest);
	}
	
	public abstract Ore getOre();

	@Override
	protected boolean canComplete(FoGPlayer omp) {
		return omp.getQuestProgress(this) >= getAmount();
	}

	@Override
	protected void complete(FoGPlayer omp) {}
}
