package fadidev.orbitmines.minigames.handlers;

import com.google.common.io.Files;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.DataException;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiniGamesMap {

	private String mapName;
	private String builder;
    private MiniGameType type;
	private String worldName;
	private World world;
	private Location spectatorLocation;
	private List<Location> spawns;
	private boolean inUse;
	private Map<MiniGamesPlayer, Location> playerSpawns;
	private List<Location> swTierTwoChests;
	
	public MiniGamesMap(String mapName, String builder, MiniGameType type, String worldName){
		this.mapName = mapName;
		this.builder = builder;
        this.type = type;
		this.worldName = worldName;
		this.inUse = false;
		this.playerSpawns = new HashMap<>();
		this.swTierTwoChests = new ArrayList<>();
		
		restoreWorld();
	}

	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getBuilder() {
		return builder;
	}
	public void setBuilder(String builder) {
		this.builder = builder;
	}

    public MiniGameType getType() {
        return type;
    }

    public void setType(MiniGameType type) {
        this.type = type;
    }

    public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}

	public String getWorldName() {
		return worldName;
	}
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public Location getSpectatorLocation() {
		return spectatorLocation;
	}
	public void setSpectatorLocation(Location spectatorLocation) {
		this.spectatorLocation = spectatorLocation;
	}

	public List<Location> getSpawns() {
		return spawns;
	}
	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}

	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
		
		if(!isInUse())
			this.playerSpawns.clear();
	}

	public Map<MiniGamesPlayer, Location> getPlayerSpawns() {
		return playerSpawns;
	}
	public void setPlayerSpawns(Map<MiniGamesPlayer, Location> playerSpawns) {
		this.playerSpawns = playerSpawns;
	}

	public List<Location> getSWTier2Chests() {
		return swTierTwoChests;
	}
	public void setSWTier2Chests(List<Location> swTierTwoChests) {
		this.swTierTwoChests = swTierTwoChests;
	}
	
	public void restoreWorld(){
		File file = new File(getWorldName());
			
		if(getType() != MiniGameType.ULTRA_HARD_CORE){
			if(file.exists()){
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv remove " + getWorldName());
				for(String filename : file.list()){
					File f = new File(file.getPath() + "/" + filename);
					f.delete();
				}
			}
			
			try{
				File file2 = new File("plugins/MiniGame Worlds", getWorldName());
				new File(getWorldName() + "/data").mkdirs();
				new File(getWorldName() + "/region").mkdirs();
				for(String filename : file2.list()){
					try{
						Files.copy(new File(file2.getPath() + "/" + filename), new File(getWorldName() + "/" + filename));
					}catch(FileNotFoundException ex){
						File file3 = new File(file2.getPath() + "/" + filename);
						for(String filename2 : file3.list()){
							Files.copy(new File(file3.getPath() + "/" + filename2), new File(getWorldName() + "/" + filename + "/" + filename2));
						}
					}
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv import " + getWorldName() + " normal -g OrbitMinesEmptyWorldGenerator");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv modify set weather false " + getWorldName());
		}
		else{
			if(file != null){
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv remove " + getWorldName());
				try{
					FileUtils.deleteDirectory(file);
				}catch(IOException e){
					e.printStackTrace();
				}
			}

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv create " + getWorldName() + " normal");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv modify set weather false " + getWorldName());
		}
		setWorld(Bukkit.getWorld(getWorldName()));
		
		if(getType() == MiniGameType.ULTRA_HARD_CORE){
			getWorld().setGameRuleValue("keepInventory", "false");
			getWorld().setGameRuleValue("naturalRegeneration", "false");
			getWorld().setDifficulty(Difficulty.HARD);
			
		    EditSession es = new EditSession(new BukkitWorld(getWorld()), 999999999);
		    CuboidClipboard cc = null;
		    
			try{
				cc = CuboidClipboard.loadSchematic(new File("plugins/WorldEdit/schematics", "UHCSpawn.schematic"));
			}catch(DataException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
			
		    try {
		    	int y = getHighestYPos(new Location(getWorld(), 0.5, 100, 0.5)) +1;
		    	setSpectatorLocation(new Location(getWorld(), 0.5, y, 0.5));
		    	
				cc.paste(es, new Vector(0.5, y, 0.5), true);
			}catch(MaxChangedBlocksException e){
				e.printStackTrace();
			}
		}
		else{
			getWorld().setGameRuleValue("keepInventory", "false");
			getWorld().setGameRuleValue("doMobSpawning", "false");

            if(type == MiniGameType.CHICKEN_FIGHT)
                getWorld().setGameRuleValue("naturalRegeneration", "false");
		}
	}
	
	public int getHighestYPos(Location l){
		for(int i = 250; i > 0; i--){
			Block b = l.getWorld().getBlockAt(l.getBlockX(), i, l.getBlockZ());
			
			if(b != null && !b.isEmpty()){
				return i +1;
			}
		}
		return 100;
	}
}
