package fadidev.orbitmines.kitpvp.handlers;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.chat.ComponentMessage;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class KitPvPMap {

    private static OrbitMinesKitPvP kitPvP;
	private String mapName;
	private String builders;
	private Location spectatorSpawn;
	private List<Location> spawns;
	private List<UUID> votes;
    private Location voteSign;
	private int maxy;
	private int minutes;
	private int seconds;
	
	public KitPvPMap(String mapName){
        kitPvP = OrbitMinesKitPvP.getKitPvP();
		this.mapName = mapName;
        this.votes = new ArrayList<>();
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getBuilders() {
		return builders;
	}

	public void setBuilders(String builders) {
		this.builders = builders;
	}

	public Location getSpectatorSpawn() {
		return spectatorSpawn;
	}

	public void setSpectatorSpawn(Location spectatorSpawn) {
		this.spectatorSpawn = spectatorSpawn;
	}

	public List<Location> getSpawns() {
		return spawns;
	}

	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}

    public List<UUID> getVotes() {
        return votes;
    }

    public Location getVoteSign() {
        return voteSign;
    }

    public void setVoteSign(Location voteSign) {
        this.voteSign = voteSign;
    }

    public int getMaxY(){
		return maxy;
	}
	
	public void setMaxY(int maxy){
		this.maxy = maxy;
	}
	
	public int getMinutes(){
		return minutes;
	}
	
	public int getSeconds(){
		return seconds;
	}
	
	public void resetTimer(){
		this.minutes = 30;
		this.seconds = 0;
	}
	
	public void tickTimer(){
		if(seconds != -1){
			seconds = seconds -1;
		}
		if(seconds == -1){
			minutes = minutes -1;
			seconds = 59;
		}
	}
	
	public boolean nextSwitch(){
		return this.minutes == 0 && this.seconds == 0;
	}
	
	public Location getRandomSpawn(){
		return this.spawns.get(new Random().nextInt(this.spawns.size()));
	}
	
	public void sendJoinMessage(KitPvPPlayer omp){
		Player p = omp.getPlayer();
		p.sendMessage("§7§m----------------------------------------");
		p.sendMessage(" §6§lOrbitMines§4§lNetwork §7- §c§lKitPvP");
		p.sendMessage(" ");
		p.sendMessage(" §7§lMap: §9§l§n" + getMapName());
		p.sendMessage(" ");

		ComponentMessage cm = new ComponentMessage();
		cm.addPart(" §7§l" + KitPvPMessages.WORD_BUILT_BY.get(omp) + ": ");
		cm.addPart("§e§l[" + Messages.WORD_VIEW.get(omp) + "]", Action.SHOW_TEXT, getBuilders());
		cm.send(p);
		
		p.sendMessage("§7§m----------------------------------------");
	}

	public void updateVoteSign(KitPvPPlayer omp){
        String[] lines = new String[4];

        lines[0] = "§l" + getMapName();
        lines[1] = getVotes().size() + " " + (getVotes().size() == 1 ? "Vote" : "Votes");
        lines[2] = "";
        if(omp.hasVoted())
            lines[3] = (getVotes().contains(omp.getUUID()) ? "§2" : "§4") + "§l" + KitPvPMessages.WORD_VOTED.get(omp);
        else
            lines[3] = "§n" + KitPvPMessages.CLICK_TO_VOTE.get(omp);

        omp.getPlayer().sendSignChange(getVoteSign(), lines);
    }

    public static KitPvPMap getKitPvPMap(Location signLocation){
        for(KitPvPMap map : kitPvP.getMaps()){
            if(WorldUtils.equalsLoc(map.getVoteSign(), signLocation))
                return map;
        }
        return null;
    }
}
