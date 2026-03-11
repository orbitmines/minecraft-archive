package om.fog.handlers.quests;

import om.fog.handlers.Quest;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Mob.MobLevel;
import om.fog.utils.enums.Repeat;

public abstract class MobKillQuest extends Quest {
	
	public MobKillQuest(int id, String name, int level, int exp, Repeat repeat, boolean progressQuest) {
		super(id, name, level, exp, repeat, progressQuest);
	}
	
	public abstract MobLevel getMob();

	@Override
	protected boolean canComplete(FoGPlayer omp) {
		return omp.getQuestProgress(this) >= getAmount();
	}

	@Override
	protected void complete(FoGPlayer omp) {}
}
