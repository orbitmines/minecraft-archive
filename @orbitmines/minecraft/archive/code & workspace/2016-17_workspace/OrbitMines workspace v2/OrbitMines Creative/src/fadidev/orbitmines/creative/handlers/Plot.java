package fadidev.orbitmines.creative.handlers;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.inventories.PlotKitInv;
import fadidev.orbitmines.creative.utils.enums.PlotType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;

public class Plot {

    private static OrbitMinesCreative creative;

	private int plotId;
	private Location location;
	private UUID ownerUuid;
	private List<UUID> memberUuids;
	private Location homeLocation;
	private String createdDate;
	private PlotType plottype;
	private boolean setupFinished;
	private boolean resetting;
	private long lastReset;
	private Location pvPLobbyLocation;
	private List<Location> pvPSpawns;
	private boolean pvPDrops;
	private boolean pvPArrows;
	private boolean pvPBuild;
	private int pvPMaxPlayers;
	private List<Kit> pvPKits;
	private Map<Kit, PlotKitInv> pvPInventories;
	private Map<Kit, Location> pvPNpcLocations;
	private Map<Kit, NPCArmorStand> pvPNpcs;
	
	public Plot(int plotId){
        creative = OrbitMinesCreative.getCreative();

		this.plotId = plotId;
		this.memberUuids = new ArrayList<>();
		this.pvPSpawns = new ArrayList<>();
		this.pvPKits = new ArrayList<>();
		this.pvPInventories = new HashMap<>();
		this.pvPNpcLocations = new HashMap<>();
		this.pvPNpcs = new HashMap<>();
		this.lastReset = 0;
	}

	public int getPlotId() {
		return plotId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".Location", ConfigUtils.parseString(this.location));
		creative.getConfigManager().save("plots");
	}

	public UUID getOwnerUUID() {
		return ownerUuid;
	}

	public void setOwnerUUID(UUID ownerUuid) {
		this.ownerUuid = ownerUuid;
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".Players.Owner", this.ownerUuid.toString());
		creative.getConfigManager().save("plots");
	}

	public List<UUID> getMemberUUIDs() {
		return memberUuids;
	}

	public void setMemberUUIDs(List<UUID> memberUuids) {
		this.memberUuids = memberUuids;
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".Players.Members", ConfigUtils.parseStringList(this.memberUuids));
		creative.getConfigManager().save("plots");
	}

	public void addMemberUUID(UUID memberUuid) {
		this.memberUuids.add(memberUuid);
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".Players.Members", ConfigUtils.parseStringList(this.memberUuids));
		creative.getConfigManager().save("plots");
	}

	public void removeMemberUUID(UUID memberUuid) {
		this.memberUuids.remove(memberUuid);
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".Players.Members", ConfigUtils.parseStringList(this.memberUuids));
		creative.getConfigManager().save("plots");
	}

	public Location getHomeLocation() {
		return homeLocation;
	}

	public void setHomeLocation(Location homeLocation) {
		this.homeLocation = homeLocation;
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".HomeLocation", ConfigUtils.parseString(this.homeLocation));
		creative.getConfigManager().save("plots");
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate() {
		this.createdDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".CreatedDate", this.createdDate);
		creative.getConfigManager().save("plots");
	}

	public PlotType getPlotType() {
		return plottype;
	}

	public void setPlotType(PlotType plottype) {
		this.plottype = plottype;
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".Type", this.plottype.toString());
		creative.getConfigManager().save("plots");
	}

	public boolean isSetupFinished() {
		return setupFinished;
	}
	
	public void setSetupFinished(boolean setupFinished) {
		this.setupFinished = setupFinished;
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.SetupFinished", this.setupFinished);
		creative.getConfigManager().save("plots");
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

	public Location getPvPLobbyLocation() {
		return pvPLobbyLocation;
	}
	
	public void setPvPLobbyLocation(Location pvPLobbyLocation) {
		this.pvPLobbyLocation = pvPLobbyLocation;

		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.Locations.Lobby", ConfigUtils.parseString(this.pvPLobbyLocation));
		creative.getConfigManager().save("plots");
	}

	public List<Location> getPvPSpawns() {
		return pvPSpawns;
	}
	
	public void setPvPSpawns(List<Location> pvPSpawns) {
		this.pvPSpawns = pvPSpawns;
		
		List<String> spawns = new ArrayList<>();
		for(Location pvpSpawn : this.pvPSpawns){
			spawns.add(ConfigUtils.parseString(pvpSpawn));
		}
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.Locations.Spawns", spawns);
		creative.getConfigManager().save("plots");
	}

	public boolean hasPvPDrops() {
		return pvPDrops;
	}
	
	public void setPvPDrops(boolean pvPDrops) {
		this.pvPDrops = pvPDrops;

		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.EnableDrops", this.pvPDrops);
		creative.getConfigManager().save("plots");
	}

	public boolean hasPvPArrows() {
		return pvPArrows;
	}
	
	public void setPvPArrows(boolean pvPArrows) {
		this.pvPArrows = pvPArrows;

		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.EnableArrows", this.pvPArrows);
		creative.getConfigManager().save("plots");
	}

	public boolean canPvPBuild() {
		return pvPBuild;
	}
	
	public void setPvPBuild(boolean pvPBuild) {
		this.pvPBuild = pvPBuild;

		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.EnableBuild", this.pvPBuild);
		creative.getConfigManager().save("plots");
	}

	public int getPvPMaxPlayers() {
		return pvPMaxPlayers;
	}
	
	public void setPvPMaxPlayers(int pvPMaxPlayers) {
		this.pvPMaxPlayers = pvPMaxPlayers;

		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.MaxPlayers", this.pvPMaxPlayers);
		creative.getConfigManager().save("plots");
	}

	public List<Kit> getPvPKits() {
		return pvPKits;
	}
	
	public void setPvPKits(List<Kit> pvPKits) {
		this.pvPKits = pvPKits;
		
		for(Kit kit : this.pvPKits){
			kit.saveToConfig(creative.getConfigManager().get("plots"), "plots." + getPlotId() + ".PvPInfo.Kits." + kit.getName(), Material.STAINED_GLASS_PANE);
		}
		creative.getConfigManager().save("plots");
	}
    
	public Map<Kit, PlotKitInv> getPvPInventories() {
		return pvPInventories;
	}

	public Map<Kit, Location> getPvPNPCLocations() {
		return pvPNpcLocations;
	}
	
	public void setPvPNPCLocations(Map<Kit, Location> pvPNpcLocations) {
		this.pvPNpcLocations = pvPNpcLocations;
		
		for(Kit kit : this.pvPKits){
			creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.Locations.NPCs." + kit.getName(), ConfigUtils.parseString(this.pvPNpcLocations.get(kit)));
		}
		creative.getConfigManager().save("plots");
	}

	public Map<Kit, NPCArmorStand> getPvPNPCs() {
		return pvPNpcs;
	}
	
	public void createKit(Location l, String kitName){
		Kit kit = new Kit(kitName);
		this.pvPKits.add(kit);
		this.pvPNpcLocations.put(kit, l);
		setPvPKits(this.pvPKits);
		setPvPNPCLocations(this.pvPNpcLocations);
		
		spawnNPC(kit);
		updateNPC(kit);
	}
	
	public void removeKit(Kit kit){
		this.pvPKits.remove(kit);
		this.pvPNpcLocations.remove(kit);
		this.pvPNpcs.get(kit).delete();
		this.pvPNpcs.remove(kit);
		setPvPKits(this.pvPKits);
		setPvPNPCLocations(this.pvPNpcLocations);
		
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.Kits." + kit.getName(), null);
		creative.getConfigManager().get("plots").set("plots." + getPlotId() + ".PvPInfo.Locations.NPCs." + kit.getName(), null);
		creative.getConfigManager().save("plots");
	}
	
	public void spawnNPCs(){
		for(Kit kit : this.pvPKits){
			spawnNPC(kit);
			updateNPC(kit);
		}
	}
	
	private void spawnNPC(final Kit kit){
		NPCArmorStand npc = new NPCArmorStand(getPvPNPCLocations().get(kit), new NPCArmorStand.InteractAction(){
            @Override
            public void click(Player player, NPCArmorStand npc) {
				CreativePlayer omp = CreativePlayer.getCreativePlayer(player);

                Plot plot = omp.getPlot();

				if(omp.isInPvPPlot())
					plot = omp.getPvPPlot();

				if(plot != null){
                    if(plot.getPlotType() == PlotType.PVP){
                        if(omp.getKitSelected() == null && omp.isInPvPPlot())
                            omp.selectKit(kit);
                    }
                    else{
                        if(getPlotId() == omp.getPlot().getPlotId())
                            omp.openKitInventory(kit);
                    }
				}
            }
        });
		npc.setGravity(false);
        npc.setCustomName("§7§lKit §8| §a" + kit.getName());
		npc.setCustomNameVisible(true);
		npc.spawn();
		getPvPNPCs().put(kit, npc);

        creative.getApi().registerNpcArmorStand(npc);
	}

	public void updateNPCs(){
		for(Kit kit : this.pvPKits){
			updateNPC(kit);
		}
	}

	public void updateNPC(Kit kit){
		NPCArmorStand npc = getPvPNPCs().get(kit);
		npc.setCustomName("§7§lKit §8| §a" + kit.getName());
		npc.setCustomNameVisible(true);

		ItemStack helmet = kit.getHelmet();
		ItemStack chestplate = kit.getChestplate();
		ItemStack leggings = kit.getLeggings();
		ItemStack boots = kit.getBoots();
		ItemStack firstItem = kit.getFirstItem();
		
		if(helmet == null || helmet.getType() != Material.STAINED_GLASS_PANE)
			npc.setHelmet(helmet);
		if(chestplate == null || chestplate.getType() != Material.STAINED_GLASS_PANE)
			npc.setChestplate(chestplate);
		if(leggings == null || leggings.getType() != Material.STAINED_GLASS_PANE)
			npc.setLeggings(leggings);
		if(boots == null || boots.getType() != Material.STAINED_GLASS_PANE)
			npc.setBoots(boots);
		if(firstItem == null || firstItem.getType() != Material.STAINED_GLASS_PANE)
			npc.setItemInHand(firstItem);
		else
			npc.setItemInHand(null);

		npc.update();
	}

	public void removeNPCs(){
		for(NPCArmorStand npc : this.pvPNpcs.values()){
			npc.delete();
		}
	}
	
	public void reset(){
		this.resetting = true;
		updateLastReset();
		resetLayer(0);
	}
	
	private void resetLayer(final int y){
		World w = creative.getPlotWorld();
		final List<Block> blocks = WorldUtils.getBlocksBetween(new Location(w, this.location.getX() + 43.5, y, this.location.getZ() + 43.5), new Location(w, this.location.getX() - 44, y, this.location.getZ() - 44));
	
		new BukkitRunnable(){
			public void run(){
				Material m = Material.BEDROCK;
				if(y == 0){}
				else if(y < 70){m = Material.DIRT;}
				else if(y == 70){m = Material.GRASS;}
				else{m = Material.AIR;}
				
				for(Block b : blocks){
					b.setType(m);
				}
				
				Player p = PlayerUtils.getPlayer(getOwnerUUID());
				if(y != 255){
					if(p != null){
                        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);
						Title t = new Title(CreativeMessages.CLEARING_PLOT.get(omp), "§d" + (y +1) + "§7/§d255", 0, 20, 0);
						t.send(p);
					}
					resetLayer(y +1);
				}
				else{
					if(p != null){
                        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);
						Title t = new Title("", CreativeMessages.PLOT_CLEARED.get(omp), 0, 30, 40);
						t.send(p);
					}
				}
			}
		}.runTaskLater(creative, 2);
	}
	
	public void generatePvPBorder(boolean border){
		Location l = this.location;
		World w = l.getWorld();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		Material m = border ? Material.BARRIER : Material.AIR;
		
		List<Block> blocks = new ArrayList<>();
		blocks.addAll(WorldUtils.getBlocksBetween(new Location(w, x -44, 255, z -44), new Location(w, x +43.5, 255, z +43.5)));
		blocks.addAll(WorldUtils.getBlocksBetween(new Location(w, x -45, y +1, z +44.5), new Location(w, x +44.5, 255, z +44.5)));
		blocks.addAll(WorldUtils.getBlocksBetween(new Location(w, x -45, y +1, z -45), new Location(w, x +44.5, 255, z -45)));
		blocks.addAll(WorldUtils.getBlocksBetween(new Location(w, x +44.5, y +1, z -45), new Location(w, x +44.5, 255, z +44.5)));
		blocks.addAll(WorldUtils.getBlocksBetween(new Location(w, x -45, y +1, z -45), new Location(w, x -45, 255, z +44.5)));
		
		for(Block b : blocks){
			 b.setType(m); 
		}
	}
	
	public void load(){
		FileConfiguration plots = creative.getConfigManager().get("plots");
		
		this.location = ConfigUtils.parseLocation(plots.getString("plots." + getPlotId() + ".Location"));
		this.homeLocation = ConfigUtils.parseLocation(plots.getString("plots." + getPlotId() + ".HomeLocation"));
		this.createdDate = plots.getString("plots." + getPlotId() + ".CreatedDate");
		this.plottype = PlotType.valueOf(plots.getString("plots." + getPlotId() + ".Type"));
		this.ownerUuid = UUID.fromString(plots.getString("plots." + getPlotId() + ".Players.Owner"));
		this.memberUuids = ConfigUtils.parseUUIDList(plots.getStringList("plots." + getPlotId() + ".Players.Members"));
		
		this.setupFinished = false;
		this.pvPDrops = false;
		this.pvPArrows = false;
		this.pvPBuild = false;
		this.pvPMaxPlayers = 50;
		
		if(plots.contains("plots." + getPlotId() + ".PvPInfo")){
			if(plots.contains("plots." + getPlotId() + ".PvPInfo.SetupFinished"))
				this.setupFinished = plots.getBoolean("plots." + getPlotId() + ".PvPInfo.SetupFinished");
			if(plots.contains("plots." + getPlotId() + ".PvPInfo.EnableDrops"))
				this.pvPDrops = plots.getBoolean("plots." + getPlotId() + ".PvPInfo.EnableDrops");
			if(plots.contains("plots." + getPlotId() + ".PvPInfo.EnableArrows"))
				this.pvPArrows = plots.getBoolean("plots." + getPlotId() + ".PvPInfo.EnableArrows");
			if(plots.contains("plots." + getPlotId() + ".PvPInfo.EnableBuild"))
				this.pvPBuild = plots.getBoolean("plots." + getPlotId() + ".PvPInfo.EnableBuild");
			if(plots.contains("plots." + getPlotId() + ".PvPInfo.MaxPlayers"))
				this.pvPMaxPlayers = plots.getInt("plots." + getPlotId() + ".PvPInfo.MaxPlayers");
			
			if(plots.contains("plots." + getPlotId() + ".PvPInfo.Kits")){
				List<Kit> pvPKits = new ArrayList<>();
				for(String kitName : plots.getConfigurationSection("plots." + getPlotId() + ".PvPInfo.Kits").getKeys(false)){
					Kit kit = Kit.getKitFromConfig(kitName, creative.getConfigManager().get("plots"), "plots." + getPlotId() + ".PvPInfo.Kits." + kitName);
					pvPKits.add(kit);
				}
				this.pvPKits = pvPKits;
			}
			
			this.pvPLobbyLocation = ConfigUtils.parseLocation(plots.getString("plots." + getPlotId() + ".PvPInfo.Locations.Lobby"));
			if(plots.contains("plots." + getPlotId() + ".PvPInfo.Locations.NPCs")){
				HashMap<Kit, Location> pvPNpcLocations = new HashMap<>();
				for(String kitName : plots.getConfigurationSection("plots." + getPlotId() + ".PvPInfo.Locations.NPCs").getKeys(false)){
					pvPNpcLocations.put(getKit(kitName), ConfigUtils.parseLocation(plots.getString("plots." + getPlotId() + ".PvPInfo.Locations.NPCs." + kitName)));
				}
				this.pvPNpcLocations = pvPNpcLocations;
			}
			this.pvPSpawns = ConfigUtils.parseLocationList(plots.getStringList("plots." + getPlotId() + ".PvPInfo.Locations.Spawns"));
			
			new BukkitRunnable(){
				public void run(){
					spawnNPCs();
				}
			}.runTaskLater(creative, 100);
		}
	}
	
	public Kit getKit(String kitName){
		for(Kit kit : getPvPKits()){
			if(kit.getName().equalsIgnoreCase(kitName))
				return kit;
		}
		return null;
	}
	
	public static Plot getPlot(int plotId){
		if(creative == null)
			creative = OrbitMinesCreative.getCreative();

		for(Plot plot : creative.getPlots()){
			if(plot.getPlotId() == plotId)
				return plot;
		}
		return null;
	}
	
	public static Plot getPlot(UUID uuid){
		if(creative == null)
			creative = OrbitMinesCreative.getCreative();

		for(Plot plot : creative.getPlots()){
			if(plot.getOwnerUUID().toString().equals(uuid.toString())){
				return plot;
			}
		}
		return null;
	}
	
	public static void removeAllPlotNPCs(){
		for(Plot plot : creative.getPlots()){
			plot.removeNPCs();
		}
	}
	
	public static List<Plot> getMemberOn(UUID uuid){
        if(creative == null)
            creative = OrbitMinesCreative.getCreative();

        List<Plot> plots = new ArrayList<>();
		for(Plot plot : creative.getPlots()){
			if(ConfigUtils.parseStringList(plot.getMemberUUIDs()).contains(uuid.toString()))
				plots.add(plot);
		}
		return plots;
	}
	
	public static List<CreativePlayer> getPvPPlayers(Plot plot){
		List<CreativePlayer> players = new ArrayList<>();
		for(CreativePlayer player : creative.getCreativePlayers()){
			if(player.getPvPPlot() != null && player.getPvPPlot().getPlotId() == plot.getPlotId())
                players.add(player);
		}
		return players;
	}
	
	public static Plot nextPlot(CreativePlayer omp){
		if(creative == null)
			creative = OrbitMinesCreative.getCreative();

		int lastPlotId = creative.getLastPlotId();
		
		Plot plot = new Plot(lastPlotId +1);
		if(lastPlotId == 0)
			plot.setLocation(new Location(creative.getPlotWorld(), -51, 71, 52));
		else
			plot.setLocation(nextPlotLocation(getPlot(lastPlotId).getLocation()));

		plot.setHomeLocation(plot.getLocation());
		plot.setCreatedDate();
		plot.setPlotType(PlotType.NORMAL);
		plot.setOwnerUUID(omp.getPlayer().getUniqueId());
		plot.setMemberUUIDs(new ArrayList<UUID>());

        omp.setPlot(plot);
		creative.setLastPlotId(lastPlotId +1);
		creative.getPlots().add(plot);
		
		return plot;
	}
	
	private static Location nextPlotLocation(Location prevPlot){
		final int x = (int) prevPlot.getX();
		final int z = (int) prevPlot.getZ();
		final Location nextPlot = WorldUtils.asNewLocation(prevPlot, 0, 0, 0);
		
		if(x < z){
			if(-1 * x < z){
				nextPlot.setX(nextPlot.getX() + 103);
				return nextPlot;
			}
			nextPlot.setZ(nextPlot.getZ() + 103);
			return nextPlot;
		}
		if(x > z){
			if(-1 * x >= z){
				nextPlot.setX(nextPlot.getX() - 103);
				return nextPlot;
			}
			nextPlot.setZ(nextPlot.getZ() - 103);
			return nextPlot;
		}
		if(x <= 0){
			nextPlot.setZ(nextPlot.getZ() + 103);
			return nextPlot;
		}
		nextPlot.setZ(nextPlot.getZ() - 103);
		
		return nextPlot;
	}
}
