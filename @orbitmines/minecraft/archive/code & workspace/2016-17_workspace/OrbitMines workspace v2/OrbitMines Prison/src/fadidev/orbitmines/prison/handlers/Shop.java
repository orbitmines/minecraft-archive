package fadidev.orbitmines.prison.handlers;

import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Shop {

	private static OrbitMinesPrison prison;
	private int shopId;
	private Location signLocation;
	private List<Block> blocks;
	private UUID owner;
	private String ownerName;
	private String ownerUntil;
	
	public Shop(int shopId, Location signLocation, List<Block> blocks, List<Block> noInteract1, List<Block> noInteract2){
        prison = OrbitMinesPrison.getPrison();
		this.shopId = shopId;
		this.signLocation = signLocation;
		this.blocks = blocks;
		this.blocks.removeAll(noInteract1);
		this.blocks.removeAll(noInteract2);

        FileConfiguration c = prison.getConfigManager().get("shops");
		if(c.contains("shops." + getShopId())){
			this.owner = UUID.fromString(c.getString("shops." + getShopId() + ".Owner"));
			this.ownerUntil = c.getString("shops." + getShopId() + ".Until");
			
			if(!canRent() && expired())
				expire();
		}
	}

	public int getShopId() {
		return shopId;
	}

	public Location getSignLocation() {
		return signLocation;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;

        prison.getConfigManager().get("shops").set("shops." + getShopId() + ".Owner", getOwner().toString());
        prison.getConfigManager().save("shops");
	}

	public String getOwnerUntil() {
		return ownerUntil;
	}

	public void setOwnerUntil(String ownerUntil) {
		this.ownerUntil = ownerUntil;

        prison.getConfigManager().get("shops").set("shops." + getShopId() + ".Until", getOwnerUntil());
        prison.getConfigManager().save("shops");
	}

	public String getOwnerUntilForSign(){
		return getOwnerUntil().substring(0, 5) + getOwnerUntil().substring(10);
	}

	public String getOwnerName() {
		if(owner != null && ownerName == null)
			ownerName = UUIDUtils.getName(getOwner());
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public boolean isInShop(Location l){
		for(Block b : getBlocks()){
			if(WorldUtils.equalsLoc(l, b.getLocation()))
				return true;
		}
		return false;
	}
	
	public boolean canRent(){
		return getOwner() == null;
	}
	
	public void rent(PrisonPlayer omp){
		Calendar until = Calendar.getInstance();
		until.add(Calendar.DATE, 2);
		
        setOwnerUntil(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(until.getTimeInMillis())));
		setOwner(omp.getUUID());
		
		omp.setShop(this);
		updateSign();
	}
	
	public boolean expired(){
		try{
			return new Date(Calendar.getInstance().getTimeInMillis()).compareTo(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(getOwnerUntil())) >= 0;
		}catch(ParseException ex){
			return false;
		}
	}
	
	public void expire(){
		Player p = PlayerUtils.getPlayer(getOwner());
		
		if(p != null){
            PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);
			omp.setShop(null);
			p.sendMessage(PrisonMessages.SHOP_EXPIRED.get(omp));
		}
		
		this.owner = null;
		this.ownerUntil = null;

        prison.getConfigManager().get("shops").set("shops." + getShopId(), null);
        prison.getConfigManager().save("shops");
		
		for(Block b : getBlocks()){
			if(b.getState() instanceof Chest){
				Chest c = (Chest) b.getState();
				c.getInventory().clear();
			}
			
			for(Entity en : b.getWorld().getEntitiesByClass(ItemFrame.class)){
				if(en.getLocation().distance(b.getLocation().add(0.5, 0.5, 0.5)) <= 0.5)
					en.remove();
			}
			
			b.setType(Material.AIR);
		}
	}
	
	public void addDays() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date d = null;
		try{
			d = df.parse(getOwnerUntil());
		}catch(ParseException ex){}
        
		Calendar until = Calendar.getInstance();
		until.setTimeInMillis(d.getTime());
		until.add(Calendar.DATE, 2);
        
        setOwnerUntil(df.format(new Date(until.getTimeInMillis())));
        updateSign();
	}
	
	public void updateSign(){
		for(PrisonPlayer omp : prison.getPrisonPlayers()){
            Player p = omp.getPlayer();
			if(p.getWorld().getName().equals(getSignLocation().getWorld().getName()) && p.getLocation().distance(getSignLocation()) <= 16){
				String[] lines = new String[4];
				lines[0] = "§lShop " + getShopId();

				if(canRent()){
					lines[1] = "§o2 " + PrisonMessages.WORD_DAYS.get(omp);
					lines[2] = "5000 Gold";
					if(omp.hasGold(5000)){
						lines[3] = "§2§l" + PrisonMessages.CLICK_TO_RENT.get(omp);
					}
					else{
						lines[3] = "§4§l" + PrisonMessages.CLICK_TO_RENT.get(omp);
					}
				}
				else{
					if(getOwner().toString().equals(p.getUniqueId().toString())){
						lines[1] = "§o+2 " + PrisonMessages.WORD_DAYS.get(omp);
						if(omp.hasGold(5000)){
							lines[2] = "§2§l" + PrisonMessages.CLICK_TO_ADD.get(omp);
						}
						else{
							lines[2] = "§4§l" + PrisonMessages.CLICK_TO_ADD.get(omp);
						}
						lines[3] = "§o" + getOwnerUntilForSign();
					}
					else{
						lines[1] = getOwnerName();
						lines[2] = "";
						lines[3] = "§o" + getOwnerUntilForSign();
					}
				}
				
				p.sendSignChange(getSignLocation(), lines);
			}
		}
	}
	
	public static Shop getShop(UUID owner){
		if(prison == null)
			prison = OrbitMinesPrison.getPrison();

		for(Shop shop : prison.getShops()){
			if(shop.getOwner() != null && shop.getOwner().toString().equals(owner.toString()))
				return shop;
		}
		return null;
	}
	public static Shop getShop(Location signLocation){
		for(Shop shop : prison.getShops()){
			if(WorldUtils.equalsLoc(shop.getSignLocation(), signLocation))
				return shop;
		}
		return null;
	}
}
