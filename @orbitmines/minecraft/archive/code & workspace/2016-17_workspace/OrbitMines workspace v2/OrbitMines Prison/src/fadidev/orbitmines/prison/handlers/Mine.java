package fadidev.orbitmines.prison.handlers;

import fadidev.orbitmines.api.handlers.Letters;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Direction;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.inventory.GoldShopInv;
import fadidev.orbitmines.prison.utils.PrisonUtils;
import fadidev.orbitmines.prison.utils.enums.MineType;
import fadidev.orbitmines.prison.utils.enums.Rank;
import fadidev.orbitmines.prison.utils.enums.WoodType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mine {

	private static OrbitMinesPrison prison;
	private Rank rank;
	private MineType type;
	private List<Block> blocks;
	private List<MineBlock> mineBlocks;
	private List<Block> timerBlocks;
	private Location spawn;
	private Location timer;
	private Location resetLocation;
	private Location timerSign;
	private Location clockSign;
	private Location goldSign;
	private Location rankSign;
	private Location rankupSign;
	private int minutes;
	private int seconds;
    private Location shopNpc;
    private Location mineResetNpc;
	private Location lumberjackNpc;
	private Material shopMaterial;
	private short shopDurability;
	private String shopName;
	private WoodType woodType;
    private Direction timerDirection;

	public Mine(Rank rank, List<Block> blocks, List<MineBlock> mineBlocks,
                Location spawn,
                Location timer,
                Location resetLocation,
                Location timerSign,
                Location clockSign,
                Location goldSign,
                Location rankSign,
                Location rankupSign,
                Location shopNpc,
                Location mineResetNpc, Material shopMaterial, int shopDurability, String shopName, WoodType woodType, Direction timerDirection){
		prison = OrbitMinesPrison.getPrison();
        this.rank = rank;
		this.type = MineType.NORMAL;
		this.blocks = blocks;
		this.mineBlocks = mineBlocks;
		this.spawn = spawn;
		this.timer = timer;
        this.timerDirection = timerDirection;

       switch(getTimerDirection()){
            case NORTH:
                this.timerBlocks = WorldUtils.getBlocksBetween(getTimer(), new Location(getTimer().getWorld(), getTimer().getX() +16, getTimer().getY() +4, getTimer().getZ()));
                break;
            case EAST:
                this.timerBlocks = WorldUtils.getBlocksBetween(getTimer(), new Location(getTimer().getWorld(), getTimer().getX(), getTimer().getY() +4, getTimer().getZ() +16));
                break;
            case SOUTH:
                this.timerBlocks = WorldUtils.getBlocksBetween(getTimer(), new Location(getTimer().getWorld(), getTimer().getX() -16, getTimer().getY() +4, getTimer().getZ()));
                break;
            case WEST:
                this.timerBlocks = WorldUtils.getBlocksBetween(getTimer(), new Location(getTimer().getWorld(), getTimer().getX(), getTimer().getY() +4, getTimer().getZ() -16));
                break;
        }
        this.resetLocation = resetLocation;
		this.timerSign = timerSign;
		this.clockSign = clockSign;
		this.goldSign = goldSign;
		this.rankSign = rankSign;
		this.rankupSign = rankupSign;
        this.shopNpc = shopNpc;
        this.mineResetNpc = mineResetNpc;
        this.shopMaterial = shopMaterial;
        this.shopDurability = (short) shopDurability;
        this.shopName = shopName;
		this.woodType = woodType;
        this.timerDirection = timerDirection;

        //TODO REMOVE
        Utils.consoleSucces("Mine " + getRank().getName());
        double total = 0;
        for(MineBlock mineBlock : getMineBlocks()){
            double reward = (PrisonUtils.getReward(mineBlock.getDropped(), mineBlock.getDurability()) * (128 * (mineBlock.getPercentage() / 100))) * 1.5; //Fortune 1-3, gemiddeld: 1.5
            Utils.consoleSucces(" - " + reward + " Gold (" + mineBlock.getPercentage() + "% " + mineBlock.getMaterial().toString() + ")");
            total += reward;
        }
        Utils.consoleSucces("Average " + total + " G/m");
        Utils.consoleSucces("Average Rankup time: " + ((rank.getRankupPrice() / total) / 60) + "h");
        Utils.consoleEmpty();

		reset(null);
	}
	
	public Mine(Rank rank, List<Block> blocks, Location lumberjackNpc, WoodType woodType){
		this.rank = rank;
		this.type = MineType.WOOD;
		this.blocks = blocks;
        this.lumberjackNpc = lumberjackNpc;
		this.woodType = woodType;

        /* Cleanup */
        for(Block b : blocks){
            if(b.getType() == Material.STAINED_CLAY && b.getData() == 15){
                b.setType(woodType.getMaterial());
                b.setData(woodType.getData());
            }
        }
	}

	public Rank getRank() {
		return rank;
	}

	public MineType getType() {
		return type;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public List<MineBlock> getMineBlocks() {
		return mineBlocks;
	}

	public Location getSpawn() {
		return spawn;
	}

	public Location getTimer() {
		return timer;
	}

	public Location getResetLocation() {
		return resetLocation;
	}

	public Location getTimerSign() {
		return timerSign;
	}

	public Location getClockSign() {
		return clockSign;
	}

	public Location getGoldSign() {
		return goldSign;
	}

	public Location getRankSign() {
		return rankSign;
	}

	public Location getRankupSign() {
		return rankupSign;
	}

    public Material getShopMaterial() {
        return shopMaterial;
    }

    public short getShopDurability() {
        return shopDurability;
    }

    public String getShopName() {
        return shopName;
    }

    public Location getLumberjackNpc() {
        return lumberjackNpc;
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public Direction getTimerDirection() {
        return timerDirection;
    }

    public void newLumberJack(final Block block){
        final Material material = block.getType();
        final byte data = block.getData();

        new BukkitRunnable(){
            @Override
            public void run() {
                block.setType(material);
                block.setData(data);
            }
        }.runTaskLater(prison, Utils.random(60, 160));
    }

    public void updateSigns(){
		String[] timerSign = new String[4];
		
		for(PrisonPlayer omp : prison.getPrisonPlayers()){
			Player p = omp.getPlayer();
			
			if(p.getWorld().getName().equals(prison.getPrisonWorld().getName()) && omp.hasPerms(getRank())){
                String[] clockSign = new String[4];
                String[] goldSign = new String[4];
                String[] rankSign = new String[4];
                String[] rankupSign = new String[4];

                if(p.getLocation().distance(getTimerSign()) <= 16){
                    String minutes = getMinutes() + "";
                    String seconds = getSeconds() + "";

                    if(getMinutes() < 10){
                        minutes = "0" + minutes;
                    }
                    if(getSeconds() < 10){
                        seconds = "0" + seconds;
                    }

                    timerSign[0] = "";
                    timerSign[1] = "§lReset Timer";
                    timerSign[2] = "§o" + minutes + ":" + seconds;
                    timerSign[3] = "";
                    p.sendSignChange(getTimerSign(), timerSign);

                    clockSign[0] = "";
                    if(omp.isClockEnabled()){
                        clockSign[0] = "Lagg " + PrisonMessages.WORD_PROBLEMS.get(omp) + "?";
                    }

                    clockSign[1] = "§lToggle " + PrisonMessages.WORD_CLOCK.get(omp);
                    clockSign[2] = "";

                    clockSign[3] = "§l" + PrisonMessages.WORD_CLOCK.get(omp) + " " + PrisonUtils.clockStatusString(omp.getLanguage(), omp.isClockEnabled());

                    p.sendSignChange(getClockSign(), clockSign);
                }

                if(p.getLocation().distance(getRankSign()) <= 16){
                    goldSign[0] = "";
                    goldSign[1] = "§l" + PrisonMessages.WORD_YOUR.get(omp) + " Gold";
                    goldSign[2] = "§6§l" + omp.getGold();
                    goldSign[3] = "";
                    p.sendSignChange(getGoldSign(), goldSign);

                    rankSign[0] = "";
                    rankSign[1] = "§l" + PrisonMessages.WORD_YOUR.get(omp) + " Rank";
                    rankSign[2] = "§a§l" + omp.getRank().toString();
                    rankSign[3] = "";
                    p.sendSignChange(getRankSign(), rankSign);

                    rankupSign[0] = "";
                    rankupSign[1] = "§lRankup " + Messages.WORD_PRICE.get(omp);
                    if(omp.getRank().getNextRank() != null){
                        rankupSign[2] = "§6§l" + omp.getRank().getRankupPrice();
                        rankupSign[3] = "§o(" + PrisonMessages.WORD_PRICE_TO.get(omp) + " " + omp.getRank().getNextRank().toString() + ")";
                    }
                    else{
                        rankupSign[2] = "§4" + PrisonMessages.WORD_HIGHEST_RANK.get(omp);
                        rankupSign[3] = "";
                    }
                    p.sendSignChange(getRankupSign(), rankupSign);
                }
			}
		}
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
	
	public void tickTimer(){
		if(getSeconds() != -1){
			setSeconds(getSeconds() -1);
		}
		if(getSeconds() == -1){
			setMinutes(getMinutes() -1);
			setSeconds(59);
		}
	}
	
	public boolean isInMine(Location l){
		for(Block b : getBlocks()){
			if(WorldUtils.equalsLoc(l, b.getLocation()))
				return true;
		}
		return false;
	}

	public void reset(PrisonPlayer omp){
		if(omp == null){
			setMinutes(20);
			setSeconds(0);
		}

		for(PrisonPlayer omp2 : prison.getPrisonPlayers()){
            Player p = omp2.getPlayer();
			if(isInMine(p.getLocation())){
				p.teleport(getResetLocation());
				if(omp != null)
					p.sendMessage(PrisonMessages.RESET_MINE_PLAYER.get(omp2, getRank().getName(), omp.getName()));
				else
					p.sendMessage(PrisonMessages.RESET_MINE.get(omp2, getRank().getName()));
			}
		}
		
		List<MineBlock> mineBlocks = new ArrayList<>();
		for(MineBlock mb : getMineBlocks()){
			for(int amount = 0; amount < mb.getPercentage(); amount++){
				mineBlocks.add(mb.copy());
			}
		}
		
		for(Block b : getBlocks()){
			MineBlock mb = mineBlocks.get(new Random().nextInt(mineBlocks.size()));
			b.setType(mb.getMaterial());
			b.setData(mb.getDurability());
		}
	}

	public void spawnNpcs(){
        if(shopNpc != null){
            final Mine mine = this;
            NPC npc = new NPC(Mob.SKELETON, shopNpc, "§6§lGold Shop", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new GoldShopInv(null, mine).open(player);
                }
            });
            npc.setItemInHand(new ItemStack(Material.GOLD_INGOT));

            prison.getApi().registerNpc(npc);
        }

        if(mineResetNpc != null){
            NPC npc = new NPC(Mob.SKELETON, mineResetNpc, "§7§lReset Mine " + getRank().getName(), new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(player);
                    if(omp.hasPerms(VIPRank.GOLD_VIP)){
                        if(!omp.onCooldown(PrisonCooldowns.RESET_MINE)){
                            try{
                                Mine mine = Mine.getMine(getRank(), MineType.NORMAL);
                                mine.reset(omp);

								player.sendMessage(PrisonMessages.RESET_MINE.get(omp, rank.getName()));

                                omp.resetCooldown(PrisonCooldowns.RESET_MINE);
                            }catch(IllegalArgumentException ex){}
                        }
                        else{
                            player.sendMessage(PrisonMessages.RESET_MINE_COOLDOWN.get(omp, omp.hasPerms(VIPRank.DIAMOND_VIP) ? "5" : "10"));
                        }
                    }
                    else{
                        omp.requiredVIPRank(VIPRank.GOLD_VIP);
                    }
                }
            });
            npc.setItemInHand(ItemUtils.addEnchantment(new ItemStack(Material.DIAMOND_PICKAXE), Enchantment.DURABILITY, 1));

            prison.getApi().registerNpc(npc);
        }

        if(lumberjackNpc != null){
            NPC npc = new NPC(Mob.ZOMBIE, lumberjackNpc, "§e§lLumberjack");
            npc.setItemInHand(new ItemStack(Material.IRON_AXE));
            npc.setHelmet(new ItemStack(Material.IRON_HELMET));
            npc.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            npc.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            npc.setBoots(new ItemStack(Material.IRON_BOOTS));

            prison.getApi().registerNpc(npc);
        }
    }

	public void hideTimer(Player p){
		for(Block b : timerBlocks){
			p.sendBlockChange(b.getLocation(), Material.AIR, (byte) 0);
		}
	}
	
	public void updateTimer(){
		List<Player> players = new ArrayList<>();
        for(PrisonPlayer omp : prison.getPrisonPlayers()){
			if(omp.getPlayer().getWorld().getName().equals(prison.getPrisonWorld().getName())){
				if(omp.hasPerms(getRank())){
					hideTimer(omp.getPlayer());
					
					if(omp.isClockEnabled())
						players.add(omp.getPlayer());
				}
			}
		}
		
		String minutes = getMinutes() + "";
		String seconds = getSeconds() + "";
		
		if(getMinutes() < 10){
			minutes = "0" + minutes;
		}
		if(getSeconds() < 10){
			seconds = "0" + seconds;
		}
		
		if(players.size() > 0){
			new Letters(minutes + ":" + seconds, getTimerDirection(), timer).generate(Material.REDSTONE_BLOCK, 0, players);
		}
	}
	
	public static Mine getMine(Rank rank, MineType type){
		for(Mine mine : prison.getMines()){
			if(mine.getRank() == rank && mine.getType() == type){
				return mine;
			}
		}
		return null;
	}
}
