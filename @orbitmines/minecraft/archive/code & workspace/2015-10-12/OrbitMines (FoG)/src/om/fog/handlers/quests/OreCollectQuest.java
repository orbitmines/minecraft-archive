package om.fog.handlers.quests;

import om.fog.handlers.Quest;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Ore;
import om.fog.utils.enums.Repeat;

import org.bukkit.Material;

public abstract class OreCollectQuest extends Quest {
	
	public OreCollectQuest(int id, String name, int level, int exp, Repeat repeat, boolean progressQuest) {
		super(id, name, level, exp, repeat, progressQuest);
	}
	
	public abstract Ore getOre();

	@Override
	protected boolean canComplete(FoGPlayer omp) {
		return omp.getAmount(Material.INK_SACK, getOre().getDurability()) >= getAmount();
	}

	@Override
	protected void complete(FoGPlayer omp) {
		omp.removeItems(Material.INK_SACK, getOre().getDurability(), getAmount());
	}
}
