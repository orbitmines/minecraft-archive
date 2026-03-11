package fadidev.orbitmines.minigames.handlers;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.Letters;
import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Direction;
import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.data.*;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.inventories.KitInv;
import fadidev.orbitmines.minigames.inventories.MiniGamesTeleporterInv;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {

    private static OrbitMinesMiniGames minigames;
	private MiniGameType type;
	private int arenaId;
	private State state;
	private Location lobby;
	private MiniGamesMap map;
	private List<MiniGamesPlayer> players;
	private List<MiniGamesPlayer> deadPlayers;
	private List<MiniGamesPlayer> spectators;
	private int minutes;
	private int seconds;
	private Map<Kit, NPC> npcs;
	private ArenaData data;

    private MiniGamesTeleporterInv teleporterInv;
	
	public Arena(MiniGameType type, int arenaId, Location lobby){
        minigames = OrbitMinesMiniGames.getMiniGames();

		this.type = type;
		this.arenaId = arenaId;
		this.state = State.WAITING;
		this.lobby = lobby;
		this.players = new ArrayList<>();
		this.deadPlayers = new ArrayList<>();
		this.spectators = new ArrayList<>();
		this.minutes = 1;
		this.seconds = 0;
		this.npcs = new HashMap<>();
	
		switch(type){
			case CHICKEN_FIGHT:
				this.data = new ChickenFightData(this);
				break;
			case GHOST_ATTACK:
				this.data = new GhostAttackData(this);
				break;
			case SKYWARS:
				this.data = new SkywarsData(this);
				break;
			case SPLATCRAFT:
				break;
			case SPLEEF:
				break;
			case SURVIVAL_GAMES:
				this.data = new SurvivalGamesData(this);
				break;
			case ULTRA_HARD_CORE:
				this.data = new UHCData(this);
				break;
		}

		teleporterInv = new MiniGamesTeleporterInv(this);
		
		setRandomMap();
	}

	public MiniGameType getType() {
		return type;
	}

	public int getArenaId() {
		return arenaId;
	}

	public Location getLobby() {
		return lobby;
	}

	public MiniGamesMap getMap() {
		return map;
	}

	public void setMap(MiniGamesMap map) {
		this.map = map;
	}

	public void setRandomMap(){
		List<MiniGamesMap> maps = minigames.getMaps().get(type);
		List<MiniGamesMap> availableMaps = new ArrayList<>();
		for(MiniGamesMap map : maps){
			if(!map.isInUse())
                availableMaps.add(map);
		}
		this.map = availableMaps.get(Utils.RANDOM.nextInt(availableMaps.size()));
		this.map.setInUse(true);
		
		generateWords();
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

    public List<MiniGamesPlayer> getPlayers() {
        return players;
    }

    public List<MiniGamesPlayer> getDeadPlayers() {
        return deadPlayers;
    }

    public List<MiniGamesPlayer> getSpectators() {
        return spectators;
    }

    public MiniGamesTeleporterInv getTeleporterInv() {
        return teleporterInv;
    }

    public List<MiniGamesPlayer> getAllPlayers(){
		List<MiniGamesPlayer> players = new ArrayList<>();
		players.addAll(getPlayers());
		players.addAll(getSpectators());
		
		return players;
	}
	
	public Player[] getAllPlayerEntities(){
		List<MiniGamesPlayer> omPlayers = getAllPlayers();
		Player[] players = new Player[omPlayers.size()];
		
		int index = 0;
		for(MiniGamesPlayer omp : omPlayers){
			players[index] = omp.getPlayer();
			index++;
		}
		return players;
	}

	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

    public ArenaData getData() {
        return data;
    }

    public void tickTimer(){
		if(getSeconds() != -1){
			setSeconds(getSeconds() -1);
		}
		if(getSeconds() == -1){
			setMinutes(getMinutes() -1);
			setSeconds(59);
		}
	}

	public void sendMessage(ArenaMessage message){
        for(MiniGamesPlayer omp : getAllPlayers()){
            omp.getPlayer().sendMessage(message.getMessage(omp));
        }
    }
	
	public void sendMessage(Message message, String... args){
		for(MiniGamesPlayer omp : getAllPlayers()){
			omp.getPlayer().sendMessage(message.get(omp, args));
		}
	}

	public void sendMessage(String message){
		for(MiniGamesPlayer omp : getAllPlayers()){
			omp.getPlayer().sendMessage(message);
		}
	}

	public void sendMessage(String[] messages){
		for(MiniGamesPlayer omp : getAllPlayers()){
			for(String message : messages){
				omp.getPlayer().sendMessage(message);
			}
		}
	}
	
	public void playSound(Sound sound, float arg2, float arg3){
		for(MiniGamesPlayer omp : getAllPlayers()){
			omp.getPlayer().playSound(omp.getPlayer().getLocation(), sound, arg2, arg3);
		}
	}
	
	public void showPlayers(MiniGamesPlayer omp){
		for(MiniGamesPlayer omplayer : getAllPlayers()){
			omplayer.getPlayer().showPlayer(omp.getPlayer());
			omp.getPlayer().showPlayer(omplayer.getPlayer());
		}
	}
	
	public boolean isSpectator(MiniGamesPlayer omp){
		return this.spectators.contains(omp);
	}
	public boolean isPlayer(MiniGamesPlayer omp){
		return this.players.contains(omp);
	}
	
	public void join(final MiniGamesPlayer omp){
		final String playername = omp.getPlayer().getName();
		
		omp.setArena(this);
		omp.getPlayer().setFoodLevel(20);
		omp.getPlayer().setHealth(20D);
		omp.clearLevels();
		omp.clearPotionEffects();
		showPlayers(omp);
		omp.getPlayer().getInventory().setHeldItemSlot(0);
		
		if(getState() != State.ENDING && getState() != State.RESTARTING){
			if(getState() != State.WARMUP && getState() != State.IN_GAME){
				if(getPlayers().size() != getType().getMaxPlayers()){
					getPlayers().add(omp);
					omp.getPlayer().teleport(getLobby());
					
					sendMessage(" §a» " + omp.getName() + " §a(§f§l" + getType().getSignName() + " " + getArenaId() + "§a) §a" + getPlayers().size() + "§7/§a" + getType().getMaxPlayers());
					minigames.getLobbyKit().get(omp.getLanguage()).setItems(omp.getPlayer());

					omp.getPlayer().setAllowFlight(false);
					omp.getPlayer().setFlying(false);
				}
				else{
					omp.toServer(Server.HUB);
					
					new BukkitRunnable(){
						public void run(){
							if(PlayerUtils.getPlayer(playername) != null)
								omp.getPlayer().kickPlayer(MiniGamesMessages.CANT_CONNECT_TO_HUB.get(omp));
						}
					}.runTaskLater(minigames, 100);
				}
			}
			else{
				getSpectators().add(omp);
				omp.getPlayer().teleport(getMap().getSpectatorLocation());
				
				sendMessage(" §a» " + omp.getName() + " §a(§f§l" + getType().getSignName() + " " + getArenaId() + "§a) §e[§lSpectator§e]");
                minigames.getSpectatorKit().get(omp.getLanguage()).setItems(omp.getPlayer());

				((CraftPlayer) omp.getPlayer()).getHandle().setInvisible(true);
				omp.getPlayer().setAllowFlight(true);
				omp.getPlayer().setFlying(true);
			}
		}
		else{
			omp.toServer(Server.HUB);
			
			new BukkitRunnable(){
				public void run(){
					if(PlayerUtils.getPlayer(playername) != null)
						omp.getPlayer().kickPlayer(MiniGamesMessages.CANT_CONNECT_TO_HUB.get(omp));
				}
			}.runTaskLater(minigames, 100);
		}
	}
	
	public void leave(final MiniGamesPlayer omp){
		final String playername = omp.getPlayer().getName();
		showPlayers(omp);
		
		if(omp.getPlayer() != null && isPlayer(omp)){
			getPlayers().remove(omp);
			sendMessage(" §c« " + omp.getName() + " §c(§f§l" + getType().getSignName() + " " + getArenaId() + "§c) §c" + getPlayers().size() + "§7/§c" + getType().getMaxPlayers());

            getData().leave(omp);
		}
		else{
			getSpectators().remove(omp);
			((CraftPlayer) omp.getPlayer()).getHandle().setInvisible(false);
			sendMessage(" §c« " + omp.getName() + " §c(§f§l" + getType().getSignName() + " " + getArenaId() + "§c) §e[§lSpectator§e]");

            getData().leaveSpectator(omp);
		}
		
		if(omp.getPlayer().getVehicle() != null){
			omp.getPlayer().leaveVehicle();
		}
		
		if(omp.getPlayer() != null && omp.getArena() != null){
			if(omp.getArena().getState() == State.ENDING){
				omp.clearInventory();
			}
			
			omp.getPlayer().teleport(omp.getArena().getLobby());
			omp.setArena(null);
			omp.toServer(Server.HUB);
			
			new BukkitRunnable(){
				public void run(){
					if(PlayerUtils.getPlayer(playername) != null)
						omp.getPlayer().kickPlayer(MiniGamesMessages.CANT_CONNECT_TO_HUB.get(omp));
				}
			}.runTaskLater(minigames, 100);
		}
	}
	
	public void generateWords(){
		Location l1 = new Location(getLobby().getWorld(), getLobby().getX() -14, getLobby().getY() +9, getLobby().getZ() -30);
		Location l2 = new Location(getLobby().getWorld(), getLobby().getX() -18, getLobby().getY() +3, getLobby().getZ() -30);
		
		//Clear Previous Words\\
		for(Block b : WorldUtils.getBlocksBetween(l1, new Location(getLobby().getWorld(), getLobby().getX() +50, getLobby().getY() +13, getLobby().getZ() -30))){
			if(b.getType() != Material.AIR)
				b.setType(Material.AIR);
		}
		for(Block b : WorldUtils.getBlocksBetween(l2, new Location(getLobby().getWorld(), getLobby().getX() +50, getLobby().getY() +7, getLobby().getZ() -30))){
			if(b.getType() != Material.AIR)
				b.setType(Material.AIR);
		}
		
		new Letters(getType().getName(), Direction.NORTH, l1).generate(Material.STAINED_CLAY, 0);
		new Letters(getMap().getMapName(), Direction.NORTH, l2).generate(Material.STAINED_CLAY, 0);
	}
	
	public void starting(){
		for(MiniGamesPlayer omp : getPlayers()){
			if(omp.hasPetEnabled())
				omp.disablePet();

			if(omp.hasWardrobeEnabled())
				omp.disableWardrobe();

			if(omp.isDisguised())
				omp.unDisguise();

			if(omp.hasHatEnabled())
				omp.disableHat();

			omp.disableGadget();
			
			Player p = omp.getPlayer();
			if(p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getName() != null){
				p.closeInventory();
			}
			if(p.getVehicle() != null){
				p.leaveVehicle();
			}

			getData().starting(omp);
		}
		
		setState(State.STARTING);
	}
	
	public void updateWarmup(){
		playSound(Sound.UI_BUTTON_CLICK, 5, 1);
		Color color = null;
		
		ItemStack item_ENG = new ItemStack(Material.STAINED_GLASS_PANE, getSeconds());
		ItemStack item_NL = new ItemStack(Material.STAINED_GLASS_PANE, getSeconds());
		ItemMeta meta_ENG = item_ENG.getItemMeta();
		ItemMeta meta_NL = item_NL.getItemMeta();
		if(getSeconds() <= 2){
            meta_ENG.setDisplayName("§7Starting in §4" + getSeconds() + "§7...");
            meta_NL.setDisplayName("§7Starten in §4" + getSeconds() + "§7...");
			item_ENG.setItemMeta(meta_ENG);
            item_NL.setItemMeta(meta_NL);
            item_ENG.setDurability((short) 14);
            item_NL.setDurability((short) 14);

			color = Color.RED;
		}
		else if(getSeconds() <= 4){
            meta_ENG.setDisplayName("§7Starting in §6" + getSeconds() + "§7...");
            meta_NL.setDisplayName("§7Starten in §6" + getSeconds() + "§7...");
            item_ENG.setItemMeta(meta_ENG);
            item_NL.setItemMeta(meta_NL);
            item_ENG.setDurability((short) 1);
            item_NL.setDurability((short) 1);
			
			color = Color.ORANGE;
		}
		else if(getSeconds() <= 6){
            meta_ENG.setDisplayName("§7Starting in §a" + getSeconds() + "§7...");
            meta_NL.setDisplayName("§7Starten in §a" + getSeconds() + "§7...");
            item_ENG.setItemMeta(meta_ENG);
            item_NL.setItemMeta(meta_NL);
            item_ENG.setDurability((short) 13);
            item_NL.setDurability((short) 13);
			
			color = Color.GREEN;
		}
		else{
            meta_ENG.setDisplayName("§7Starting in §2" + getSeconds() + "§7...");
            meta_NL.setDisplayName("§7Starten in §2" + getSeconds() + "§7...");
            item_ENG.setItemMeta(meta_ENG);
            item_NL.setItemMeta(meta_NL);
            item_ENG.setDurability((short) 5);
            item_NL.setDurability((short) 5);
			
			color = Color.LIME;
		}
		
		ItemStack helmet = ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), color);
		ItemStack chestplate = ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), color);
		ItemStack leggings = ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), color);
		ItemStack boots = ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), color);
		
		for(MiniGamesPlayer omp : getPlayers()){
			Player p = omp.getPlayer();
			p.getInventory().setHelmet(helmet);
			p.getInventory().setChestplate(chestplate);
			p.getInventory().setLeggings(leggings);
			p.getInventory().setBoots(boots);

            ItemStack item = omp.getLanguage() == Language.DUTCH ? item_NL : item_ENG;

			for(int i = 0; i < 9; i++)
				p.getInventory().setItem(i, item);
		}
	}

	public void clearScoreboards(){
        for(MiniGamesPlayer omp : getAllPlayers()){
            omp.clearScoreboard();
        }
	}

	public Map<Kit, NPC> getNPCs() {
		return npcs;
	}
	
	public void sendData(){
		if(Bukkit.getOnlinePlayers().size() > 0){
			ByteArrayOutputStream b = new ByteArrayOutputStream();
        	DataOutputStream out = new DataOutputStream(b);
 
        	try{
        		out.writeUTF("Forward");
        		out.writeUTF("ALL");
        		out.writeUTF(getType().getShortName());
            	
            	out.writeUTF(getArenaId() + "|" + getState().toString() + "|" + getPlayers().size() + "|" + getMinutes() + "|" + getSeconds());
        	}catch(IOException ex){
        		ex.printStackTrace();
        	}
        	
        	Player player = null;
        	for(Player p : Bukkit.getOnlinePlayers()){
                player = p;
                break;
        	}
    		player.sendPluginMessage(minigames, "BungeeCord", b.toByteArray());
    	}
	}
	
	public void spawnNPCs(){
        getData().spawnNpcs();
	}

	public NPC spawnNpc(Mob mob, Location location, String displayName, final TicketType kit){
        NPC npc = new NPC(mob, location, displayName, new NPC.InteractAction() {
            @Override
            public void click(Player player, NPC npc) {
                new KitInv(kit).open(player);
            }
        });
        getNPCs().put(kit.getKit(), npc);

        minigames.getApi().registerNpc(npc);

        return npc;
    }

    public MiniGamesPlayer getMostKills(List<MiniGamesPlayer> players){
        int highestKills = 0;
        MiniGamesPlayer highest = null;

        for(MiniGamesPlayer omp : players){
            int kills = omp.getChickenFightPlayer().getRoundKills();
            if(kills >= highestKills);{
                highestKills = kills;
                highest = omp;
            }
        }
        return highest;
    }
	
	public static Arena getArena(MiniGameType type, int arenaId){
		for(Arena arena : minigames.getArenas()){
			if(arena.getType() == type && arena.getArenaId() == arenaId)
				return arena;
		}
		return null;
	}

	public static abstract class ArenaMessage {

        public abstract String getMessage(MiniGamesPlayer omp);
    }
}
