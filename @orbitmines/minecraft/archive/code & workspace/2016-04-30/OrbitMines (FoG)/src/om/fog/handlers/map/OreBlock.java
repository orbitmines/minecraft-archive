package om.fog.handlers.map;

import om.api.handlers.FireWork;
import om.api.utils.WorldUtils;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.FoGUtils;
import om.fog.utils.enums.Ore;

import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OreBlock {

	private SpawnArea area;
	private Ore ore;
	private Block block;
	private Material prevMaterial;
	private byte prevData;
	
	public OreBlock(SpawnArea area, Ore ore, Block block){
		this.area = area;
		this.ore = ore;
		this.block = block;
		
		spawn();
		spawnFirework();
	}
	
	public SpawnArea getSpawnArea() {
		return area;
	}
	
	public Ore getOre() {
		return ore;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public void spawnFirework(){
		Location l = block.getRelative(BlockFace.UP).getLocation().clone();
		l.add(0.5, 0, 0.5);
		
		FireWork fw = new FireWork(l);
		fw.withColor(ore.getBColor());
		fw.withFade(ore.getBColor());
		fw.withTrail();
		fw.withFlicker();
		fw.with(Type.STAR);
		fw.build();
		fw.explode();
	}
	
	@SuppressWarnings("deprecation")
	private void spawn(){
		this.prevMaterial = getBlock().getType();
		this.prevData = getBlock().getData();
		
		getBlock().setType(Material.WOOL);
		getBlock().setData((byte) getOre().getBlockData()); 
	}
	
	@SuppressWarnings("deprecation")
	public void despawn(){
		getBlock().setType(prevMaterial);
		getBlock().setData(prevData);
		
		getSpawnArea().getOreBlocks(getOre()).remove(this);
	}
	
	public void dropItem(Player p){
		ItemStack[] items = FoGUtils.applyFortune(p.getItemInHand(), getOre().getItem(1));
		
		for(ItemStack item : items){
			getBlock().getWorld().dropItemNaturally(getBlock().getRelative(BlockFace.UP).getLocation(), item);
		}
	}
	
	public boolean isMining(FoGPlayer omp){
		return WorldUtils.equalsLoc(omp.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), getBlock().getLocation());
	}
}
