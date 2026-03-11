package fadidev.orbitmines.prison.handlers;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.utils.PrisonUtils;
import fadidev.orbitmines.prison.utils.enums.ShopType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopSign {

	private static OrbitMinesPrison prison;
	private UUID uuid;
	private Location location;
	private int materialId;
	private short durability;
	private ShopType shoptype;
	private int amount;
	private int price;
	
	public ShopSign(UUID uuid, Location location, int materialId, short durability, ShopType shoptype, int amount, int price){
		this.uuid = uuid;
		this.location = location;
		this.materialId = materialId;
		this.durability = durability;
		this.shoptype = shoptype;
		this.amount = amount;
		this.price = price;
	}

	public UUID getUUID() {
		return uuid;
	}

	public Location getLocation() {
		return location;
	}

    public int getMaterialID() {
		return materialId;
	}

	public short getDurability() {
		return durability;
	}

	public ShopType getShopType() {
		return shoptype;
	}
	
	public int getAmount() {
		return amount;
	}

	public int getPrice() {
		return price;
	}

	public void buyItems(PrisonPlayer omp){
		Player p = omp.getPlayer();
		Material m = Material.getMaterial(this.materialId);
		Chest chest = getChest();

		List<ItemStack> items = new ArrayList<>();
		for(ItemStack item : chest.getInventory().getContents()){
			if(item != null && item.getType() == m && item.getDurability() == this.durability){
				chest.getInventory().remove(item);
				items.add(item);
			}
		}
		List<ItemStack> itemsToRemove = new ArrayList<>();
		List<ItemStack> itemsToAdd = new ArrayList<>();
		
		int amount = 0;
		for(ItemStack item : items){
			if(amount != this.amount){
				if(amount + item.getAmount() <= this.amount){
					p.getInventory().addItem(item);
					itemsToRemove.add(item);
					
					amount += item.getAmount();
				}
				else{
					ItemStack item2 = new ItemStack(item);
					item2.setAmount(this.amount - amount);
					p.getInventory().addItem(item2);
					itemsToRemove.add(item);
					
					ItemStack item3 = new ItemStack(item);
					item3.setAmount(item.getAmount() - (this.amount - amount));
					itemsToAdd.add(item3);
					
					amount = this.amount;
				}
			}
		}
		
		for(ItemStack item : itemsToRemove){
			items.remove(item);
		}
		for(ItemStack item : itemsToAdd){
			items.add(item);
		}
		
		chest.getInventory().addItem(items.toArray(new ItemStack[items.size()]));
		
		omp.updateInventory();
		omp.removeGold(this.price);
		
		String currency = " Gold";
		
		Player p2 = PlayerUtils.getPlayer(this.uuid);
		if(p2 != null){
			PrisonPlayer omp2 = PrisonPlayer.getPrisonPlayer(p2);
			omp2.addGold(this.price);

            p.sendMessage(PrisonMessages.SHOP_BUY.get(omp, PrisonUtils.getMaterialName(m), getAmount() + "", p2.getName(), getPrice() + ""));
            p2.sendMessage(PrisonMessages.SHOP_BUY_PLAYER.get(omp2, p.getName(), PrisonUtils.getMaterialName(m), getAmount() + "", getPrice() + ""));
        }
        else{
            p.sendMessage(PrisonMessages.SHOP_BUY.get(omp, PrisonUtils.getMaterialName(m), getAmount() + "", UUIDUtils.getName(getUUID()), getPrice() + ""));

            Database.get().update("Prison-Gold", "gold", "" + (getGoldOffline() + this.price), "uuid", this.uuid.toString());
		}
	}
	public void sellItems(PrisonPlayer omp){
		Player p = omp.getPlayer();
		Material m = Material.getMaterial(this.materialId);
		Chest chest = getChest();

		List<ItemStack> items = new ArrayList<>();
		for(ItemStack item : p.getInventory().getContents()){
			if(item != null && item.getType() == m && item.getDurability() == this.durability){
				p.getInventory().remove(item);
				items.add(item);
			}
		}
		List<ItemStack> itemsToRemove = new ArrayList<>();
		List<ItemStack> itemsToAdd = new ArrayList<>();
		
		int amount = 0;
		for(ItemStack item : items){
			if(amount != this.amount){
				if(amount + item.getAmount() <= this.amount){
					chest.getInventory().addItem(item);
					itemsToRemove.add(item);
					
					amount += item.getAmount();
				}
				else{
					ItemStack item2 = new ItemStack(item);
					item2.setAmount(this.amount - amount);
					chest.getInventory().addItem(item2);
					itemsToRemove.add(item);
					
					ItemStack item3 = new ItemStack(item);
					item3.setAmount(item.getAmount() - (this.amount - amount));
					itemsToAdd.add(item3);
					
					amount = this.amount;
				}
			}
		}
		
		for(ItemStack item : itemsToRemove){
			items.remove(item);
		}
		for(ItemStack item : itemsToAdd){
			items.add(item);
		}
		
		p.getInventory().addItem(items.toArray(new ItemStack[items.size()]));
		
		omp.updateInventory();
		omp.addGold(getPrice());
		
		String currency = " Gold";
		
		Player p2 = PlayerUtils.getPlayer(this.uuid);
		if(p2 != null){
			PrisonPlayer omp2 = PrisonPlayer.getPrisonPlayer(p);
			omp2.removeGold(this.price);

            p.sendMessage(PrisonMessages.SHOP_SELL.get(omp, PrisonUtils.getMaterialName(m), getAmount() + "", p2.getName(), getPrice() + ""));
            p2.sendMessage(PrisonMessages.SHOP_SELL_PLAYER.get(omp2, p.getName(), PrisonUtils.getMaterialName(m), getAmount() + "", getPrice() + ""));
        }
        else{
            p.sendMessage(PrisonMessages.SHOP_SELL.get(omp, PrisonUtils.getMaterialName(m), getAmount() + "", UUIDUtils.getName(getUUID()), getPrice() + ""));

            Database.get().update("Prison-Gold", "gold", "" + (getGoldOffline() - this.price), "uuid", this.uuid.toString());
		}
	}

	public boolean isSold(){
		Chest chest = getChest();
		int amount = 0;
		
		for(ItemStack item : chest.getInventory().getContents()){
			if(item != null && item.getType() == Material.getMaterial(this.materialId) && item.getDurability() == this.durability)
				amount += item.getAmount();
		}
		
		return amount < this.amount;
	}

	public boolean canSell(){
		return PrisonUtils.getEmptySlots(getChest().getInventory()) >= PrisonUtils.getSlotsRequired(this.amount, Material.getMaterial(this.materialId));
	}
	
	public Chest getChest(){
		return PrisonUtils.getChestShop(this.location.getWorld().getBlockAt(this.location));
	}

	public void update(){
		Block b = this.location.getWorld().getBlockAt(this.location);
		
		if(b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)){
			String currency = " G";
			
			String[] lines = new String[4];
			if(this.shoptype == ShopType.BUY){
				lines[0] = this.shoptype.getStatusName(isSold());
			}
			else{
				lines[0] = this.shoptype.getStatusName(!canSell());
			}
			lines[1] = PrisonUtils.getMaterialName(Material.getMaterial(this.materialId));
			lines[2] = this.amount + " : " + this.price + currency;
			lines[3] = UUIDUtils.getName(getUUID());
			
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getWorld().getName().equals(this.location.getWorld().getName()) && this.location.distance(p.getLocation()) <= 16)
					p.sendSignChange(this.location, lines);
			}
		}
		else{
			delete();
		}
	}
	
	public boolean hasGold(){
		Player p = PlayerUtils.getPlayer(this.uuid);
		
		if(p != null)
			return PrisonPlayer.getPrisonPlayer(p).hasGold(this.price);
		return getGoldOffline() >= this.price;
	}
	
	public int getGoldOffline(){
		return Database.get().getInt("Prison-Gold", "gold", "uuid", this.uuid.toString());
	}
	
	public void delete(){
		final ShopSign sign = this;
		
		new BukkitRunnable(){
			public void run(){
				Player p = PlayerUtils.getPlayer(getUUID());
				
				if(p != null)
					PrisonPlayer.getPrisonPlayer(p).getShopSigns().remove(sign);

				prison.getShopSigns().remove(sign);
				saveToConfig();
			}
		}.runTaskLater(prison, 1);
	}

    public static List<ShopSign> getShopSigns(Player player){
        List<ShopSign> signs = new ArrayList<>();

        for(ShopSign sign : prison.getShopSigns()){
            if(sign.getUUID().toString().equals(player.getUniqueId().toString())){
                signs.add(sign);
            }
        }
        return signs;
    }

    public static List<ShopSign> readFromConfig(){
        prison = OrbitMinesPrison.getPrison();

        List<ShopSign> signs = new ArrayList<>();

        for(String signstring : prison.getConfigManager().get("chestshops").getStringList("signs")){
            String[] signparts = signstring.split("\\;");

            signs.add(new ShopSign(UUID.fromString(signparts[6]), ConfigUtils.parseLocation(signparts[0]), Integer.parseInt(signparts[1]), Short.parseShort(signparts[2]), ShopType.valueOf(signparts[3]), Integer.parseInt(signparts[4]), Integer.parseInt(signparts[5])));
        }
        return signs;
    }

    public static void saveToConfig(){
        List<String> signs = new ArrayList<>();

        for(ShopSign sign : prison.getShopSigns()){
            signs.add(ConfigUtils.parseString(sign.getLocation()) + ";" + sign.getMaterialID() + ";" + sign.getDurability() + ";" + sign.getShopType().toString() + ";" + sign.getAmount() + ";" + sign.getPrice() + ";" + sign.getUUID().toString());
        }

        prison.getConfigManager().get("chestshops").set("signs", signs);
        prison.getConfigManager().save("chestshops");
    }

    public static ShopSign getShopSign(Location location){
        for(ShopSign sign : prison.getShopSigns()){
            Location l = sign.getLocation();
            if(l.getBlockX() == location.getBlockX() && l.getBlockY() == location.getBlockY() && l.getBlockZ() == location.getBlockZ())
                return sign;
        }
        return null;
    }
}
