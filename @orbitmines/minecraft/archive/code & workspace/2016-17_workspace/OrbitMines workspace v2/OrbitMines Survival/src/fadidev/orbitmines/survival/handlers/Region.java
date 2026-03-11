package fadidev.orbitmines.survival.handlers;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.utils.SurvivalUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Region {

    private static OrbitMinesSurvival survival;
	private int regionId;
	private Location location;
	private Biome biome;
	private List<Block> blocks;
	private ItemStack itemstack;
	private int slot;
	
	public Region(int regionId, Location location, Biome biome, int slot){
        survival = OrbitMinesSurvival.getSurvival();
		this.regionId = regionId;
		this.location = location;
		this.biome = biome;
		this.slot = slot;
		this.blocks = WorldUtils.getBlocksBetween(new Location(location.getWorld(), location.getX() +8, 70, location.getZ() +8), new Location(location.getWorld(), location.getX() -8, 70, location.getZ() -8));
		this.itemstack = SurvivalUtils.itemstack(this);
	}

	public int getRegionId() {
		return regionId;
	}

	public Location getLocation() {
		return location;
	}

	public Biome getBiome() {
		return biome;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public int getSlot() {
		return slot;
	}
	
	public static boolean isInRegion(SurvivalPlayer omp, Location l){
		if(!omp.isLoaded())
		    return true;
        if(omp.isOpMode())
            return false;

        for(Region r : survival.getRegions()){
            for(Block b : r.getBlocks()){
                if(l.getBlockX() == b.getLocation().getBlockX() && l.getBlockZ() == b.getLocation().getBlockZ()){

                    if(!omp.onCooldown(Cooldowns.MESSAGE)){
                        omp.getPlayer().sendMessage(SurvivalMessages.CLOSE_TO_REGION.get(omp));

                        omp.resetCooldown(Cooldowns.MESSAGE);
                    }

                    return true;
                }
            }
        }

        return false;
	}
	
	public void teleport(SurvivalPlayer omp){
		Player p = omp.getPlayer();
		
		p.teleport(this.location);
		p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);

		Title t = new Title("", "§7" + SurvivalMessages.WORD_TELEPORTED_TO.get(omp) + " §aRegion " + this.regionId + "§7.", 20, 40, 20);
		t.send(p);
	}
	
	public ItemStack getItemStack(){
		return itemstack;
	}

	public static Region getRegion(int regionId){
		for(Region r : survival.getRegions()){
			if(r.getRegionId() == regionId)
				return r;
		}
		return null;
	}

	public static Region random(){
		List<Region> regions = survival.getRegions();
		return regions.get(Utils.RANDOM.nextInt(regions.size()));
	}
}
