package fadidev.orbitmines.prison.handlers;

import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Cell {

    private static OrbitMinesPrison prison;

	private int cellId;
	private Location location;
	private UUID ownerUuid;
	private List<UUID> memberUuids;
	private String createdDate;
	private boolean resetting;
	private long lastReset;
	
	public Cell(int cellId){
        prison = OrbitMinesPrison.getPrison();

		this.cellId = cellId;
		this.memberUuids = new ArrayList<>();
		this.lastReset = 0;
	}

	public int getCellId() {
		return cellId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		
		prison.getConfigManager().get("cells").set("cells." + getCellId() + ".Location", ConfigUtils.parseString(this.location));
		prison.getConfigManager().save("cells");
	}

	public UUID getOwnerUUID() {
		return ownerUuid;
	}

	public void setOwnerUUID(UUID ownerUuid) {
		this.ownerUuid = ownerUuid;
		
		prison.getConfigManager().get("cells").set("cells." + getCellId() + ".Players.Owner", this.ownerUuid.toString());
		prison.getConfigManager().save("cells");
	}

	public List<UUID> getMemberUUIDs() {
		return memberUuids;
	}

	public void setMemberUUIDs(List<UUID> memberUuids) {
		this.memberUuids = memberUuids;
		
		prison.getConfigManager().get("cells").set("cells." + getCellId() + ".Players.Members", ConfigUtils.parseStringList(this.memberUuids));
		prison.getConfigManager().save("cells");
	}

	public void addMemberUUID(UUID memberUuid) {
		this.memberUuids.add(memberUuid);
		
		prison.getConfigManager().get("cells").set("cells." + getCellId() + ".Players.Members", ConfigUtils.parseStringList(this.memberUuids));
		prison.getConfigManager().save("cells");
	}

	public void removeMemberUUID(UUID memberUuid) {
		this.memberUuids.remove(memberUuid);
		
		prison.getConfigManager().get("cells").set("cells." + getCellId() + ".Players.Members", ConfigUtils.parseStringList(this.memberUuids));
		prison.getConfigManager().save("cells");
	}

	public Location getHomeLocation() {
		Location l = WorldUtils.asNewLocation(getLocation(), +17.5, 0, 0);
        l.setYaw(90);
        return l;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate() {
		this.createdDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		
		prison.getConfigManager().get("cells").set("cells." + getCellId() + ".CreatedDate", this.createdDate);
		prison.getConfigManager().save("cells");
	}

	public boolean isResetting() {
		return resetting;
	}
	
	public void setResetting(boolean resetting) {
		this.resetting = resetting;
	}

	public long getLastReset() {
		return lastReset;
	}
	
	public void setLastReset(long lastReset) {
		this.lastReset = lastReset;
	}
	
	public void updateLastReset() {
		this.lastReset = System.currentTimeMillis();
	}
	
	public boolean canReset(){
		return System.currentTimeMillis() - this.lastReset >= 900000;
	}
	
	public void reset(){
		this.resetting = true;
		updateLastReset();
		resetLayer(0);
	}
	
	private void resetLayer(final int y){
		World w = prison.getCellWorld();
		final List<Block> blocks = WorldUtils.getBlocksBetween(new Location(w, this.location.getX() + 13.5, 51 + y, this.location.getZ() + 13.5), new Location(w, this.location.getX() - 14, 51 + y, this.location.getZ() - 14));

		new BukkitRunnable(){
			public void run(){

                Material m = (51 + y) > 70 ? Material.AIR : Material.STONE;
				for(Block b : blocks){
					b.setType(m);
				}
				
				Player p = PlayerUtils.getPlayer(getOwnerUUID());
				if(y != 29){
					if(p != null){
                        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);
						Title t = new Title(PrisonMessages.CLEARING_PLOT.get(omp), "§c" + (y +1) + "§7/§c30", 0, 20, 0);
						t.send(p);
					}
					resetLayer(y +1);
				}
				else{
					if(p != null){
                        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);
						Title t = new Title("", PrisonMessages.PLOT_CLEARED.get(omp), 0, 30, 40);
						t.send(p);
					}
				}
			}
		}.runTaskLater(prison, 2);
	}
	
	public void load(){
		FileConfiguration cells = prison.getConfigManager().get("cells");
		
		this.location = ConfigUtils.parseLocation(cells.getString("cells." + getCellId() + ".Location"));
		this.createdDate = cells.getString("cells." + getCellId() + ".CreatedDate");
		this.ownerUuid = UUID.fromString(cells.getString("cells." + getCellId() + ".Players.Owner"));
		this.memberUuids = ConfigUtils.parseUUIDList(cells.getStringList("cells." + getCellId() + ".Players.Members"));
	}
	
	public static Cell getCell(int cellId){
        if(prison == null)
            prison = OrbitMinesPrison.getPrison();

        for(Cell cell : prison.getCells()){
			if(cell.getCellId() == cellId)
				return cell;
		}
		return null;
	}
	
	public static Cell getCell(UUID uuid){
		if(prison == null)
			prison = OrbitMinesPrison.getPrison();

		for(Cell cell : prison.getCells()){
			if(cell.getOwnerUUID().toString().equals(uuid.toString())){
				return cell;
			}
		}
		return null;
	}
	
	public static List<Cell> getMemberOn(UUID uuid){
        if(prison == null)
            prison = OrbitMinesPrison.getPrison();

        List<Cell> cells = new ArrayList<>();
		for(Cell cell : prison.getCells()){
			if(ConfigUtils.parseStringList(cell.getMemberUUIDs()).contains(uuid.toString()))
				cells.add(cell);
		}
		return cells;
	}
	
	public static Cell nextCell(PrisonPlayer omp){
        if(prison == null)
            prison = OrbitMinesPrison.getPrison();

        int lastCellId = prison.getLastCellId();
		
		Cell cell = new Cell(lastCellId +1);
		if(lastCellId == 0)
			cell.setLocation(new Location(prison.getCellWorld(), -21, 71, 22));
		else
			cell.setLocation(nextCellLocation(getCell(lastCellId).getLocation()));

		cell.setCreatedDate();
		cell.setOwnerUUID(omp.getPlayer().getUniqueId());
		cell.setMemberUUIDs(new ArrayList<UUID>());

        omp.setCell(cell);
		prison.setLastCellId(lastCellId +1);
		prison.getCells().add(cell);
		
		return cell;
	}
	
	private static Location nextCellLocation(Location prevCell){
		final int x = (int) prevCell.getX();
		final int z = (int) prevCell.getZ();
		final Location nextCell = WorldUtils.asNewLocation(prevCell, 0, 0, 0);
		
		if(x < z){
			if(-1 * x < z){
				nextCell.setX(nextCell.getX() + (28 + 15));
				return nextCell;
			}
			nextCell.setZ(nextCell.getZ() + (28 + 15));
			return nextCell;
		}
		if(x > z){
			if(-1 * x >= z){
				nextCell.setX(nextCell.getX() - (28 + 15));
				return nextCell;
			}
			nextCell.setZ(nextCell.getZ() - (28 + 15));
			return nextCell;
		}
		if(x <= 0){
			nextCell.setZ(nextCell.getZ() + (28 + 15));
			return nextCell;
		}
		nextCell.setZ(nextCell.getZ() - (28 + 15));
		
		return nextCell;
	}
}
