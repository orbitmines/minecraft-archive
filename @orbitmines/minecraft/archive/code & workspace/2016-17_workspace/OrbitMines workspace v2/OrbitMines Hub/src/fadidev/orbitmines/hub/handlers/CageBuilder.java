package fadidev.orbitmines.hub.handlers;

import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.hub.OrbitMinesHub;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CageBuilder {

    private OrbitMinesHub hub;
	private String name;
	private Location spawn;
	private Location l1;
	private Location l2;
	
	public CageBuilder(String name, Location spawn){
        this.hub = OrbitMinesHub.getHub();
		this.name = name;
		this.spawn = spawn;
	}

	public String getName() {
		return name;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getL1() {
		return l1;
	}

	public void setL1(Location l1) {
		this.l1 = l1;
	}

	public Location getL2() {
		return l2;
	}

	public void setL2(Location l2) {
		this.l2 = l2;
	}

	public void generate(){
		List<String> list = new ArrayList<>();
		for(Block b : WorldUtils.getBlocksBetween(getL1(), getL2())){
			if(!b.isEmpty()){
				Location l1 = getSpawn();
				Location l2 = b.getLocation();
				
				list.add("setBlock(blocks, b.getRelative(" + (l2.getBlockX() - l1.getBlockX()) + ", " + (l2.getBlockY() - l1.getBlockY()) + ", " + (l2.getBlockZ() - l1.getBlockZ()) + "), Material." + b.getType().toString() + ", " + b.getData() + ");");
			}
		}
		
		hub.getConfigManager().get("cages").set(getName(), list);
		hub.getConfigManager().save("cages");
	}
	
	public static ItemStack getWand(){
		return ItemUtils.itemstack(Material.GOLD_AXE, 1, "§eCage Builder");
	}
}
